package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.protocol.anchor.GlamProtocolProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;
import software.sava.solana.programs.clients.NativeProgramClient;

import java.util.List;
import java.util.Map;

final class GlamJupiterProgramClientImpl implements GlamJupiterProgramClient {

  private final NativeProgramAccountClient programAccountClient;
  private final NativeProgramClient nativeProgramClient;
  private final SolanaAccounts solanaAccounts;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final JupiterAccounts jupiterAccounts;
  private final PublicKey swapProgram;

  GlamJupiterProgramClientImpl(final GlamProgramAccountClient programAccountClient,
                               final JupiterAccounts jupiterAccounts) {
    this.programAccountClient = programAccountClient;
    this.nativeProgramClient = programAccountClient.nativeProgramClient();
    this.solanaAccounts = programAccountClient.solanaAccounts();
    this.glamVaultAccounts = programAccountClient.vaultAccounts();
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProtocolProgram();
    this.feePayer = programAccountClient.feePayer();
    this.jupiterAccounts = jupiterAccounts;
    this.swapProgram = jupiterAccounts.swapProgram();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public GlamVaultAccounts glamVaultAccounts() {
    return glamVaultAccounts;
  }

  @Override
  public JupiterAccounts jupiterAccounts() {
    return jupiterAccounts;
  }

  @Override
  public NativeProgramAccountClient nativeProgramAccountClient() {
    return programAccountClient;
  }

  private Instruction jupiterSwap(final PublicKey inputProgramStateKey,
                                  final PublicKey outputProgramStateKey,
                                  final Instruction swapInstruction) {
    final var fixedIx = GlamJupiterProgramClient.fixCPICallerRights(swapInstruction);
    return GlamProtocolProgram.jupiterSwap(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        swapProgram,
        inputProgramStateKey, outputProgramStateKey,
        swapInstruction.data()
    ).extraAccounts(fixedIx.accounts());
  }

  @Override
  public Map<PublicKey, Instruction> createSwapTokenAccountsIdempotent(final AccountMeta inputTokenProgram,
                                                                       final PublicKey inputMintKey,
                                                                       final AccountMeta outputTokenProgram,
                                                                       final PublicKey outputMintKey) {

    final var outputTokenProgramKey = outputTokenProgram.publicKey();
    final var outputVaultATA = programAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();
    final var createVaultOutputATA = programAccountClient.createATAForOwnerFundedByFeePayer(
        true, outputVaultATA, outputMintKey, outputTokenProgram
    );

    if (inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      final var inputTokenProgramKey = inputTokenProgram.publicKey();
      final var inputVaultATA = programAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();
      final var createVaultInputATA = programAccountClient.createATAForOwnerFundedByFeePayer(
          true, inputVaultATA, inputMintKey, inputTokenProgram
      );
      return Map.of(
          inputVaultATA, createVaultInputATA,
          outputVaultATA, createVaultOutputATA
      );
    } else {
      return Map.of(outputVaultATA, createVaultOutputATA);
    }
  }

  @Override
  public List<Instruction> swapChecked(final PublicKey inputProgramStateKey,
                                       final PublicKey inputMintKey,
                                       final AccountMeta inputTokenProgram,
                                       final PublicKey outputProgramStateKey,
                                       final PublicKey outputMintKey,
                                       final AccountMeta outputTokenProgram,
                                       final long amount,
                                       final Instruction swapInstruction,
                                       final boolean wrapSOL) {
    final var inputTokenProgramKey = inputTokenProgram.publicKey();
    final var inputVaultATA = programAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();

    final var outputTokenProgramKey = outputTokenProgram.publicKey();
    final var outputVaultATA = programAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();
    final var createVaultOutputATA = programAccountClient.createATAForOwnerFundedByFeePayer(
        true, outputVaultATA, outputMintKey, outputTokenProgram
    );

    final var glamJupiterSwap = jupiterSwap(
        inputProgramStateKey,
        outputProgramStateKey,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
          programAccountClient.createATAForOwnerFundedByFeePayer(
              true, inputVaultATA, inputMintKey, inputTokenProgram
          ),
          programAccountClient.transferSolLamports(inputVaultATA, amount),
          nativeProgramClient.syncNative(inputVaultATA),
          createVaultOutputATA,
          glamJupiterSwap
      );
    } else {
      return List.of(createVaultOutputATA, glamJupiterSwap);
    }
  }

  @Override
  public Instruction swapUncheckedAndNoWrap(final PublicKey inputProgramStateKey,
                                            final PublicKey inputMintKey,
                                            final AccountMeta inputTokenProgram,
                                            final PublicKey outputProgramStateKey,
                                            final PublicKey outputMintKey,
                                            final AccountMeta outputTokenProgram,
                                            final Instruction swapInstruction) {
    return jupiterSwap(inputProgramStateKey, outputProgramStateKey, swapInstruction);
  }

  @Override
  public List<Instruction> swapUnchecked(final PublicKey inputProgramStateKey,
                                         final PublicKey inputMintKey,
                                         final AccountMeta inputTokenProgram,
                                         final PublicKey outputProgramStateKey,
                                         final PublicKey outputMintKey,
                                         final AccountMeta outputTokenProgram,
                                         final long amount,
                                         final Instruction swapInstruction,
                                         final boolean wrapSOL) {
    final var glamJupiterSwap = swapUncheckedAndNoWrap(
        inputProgramStateKey, inputMintKey, inputTokenProgram,
        outputProgramStateKey, outputMintKey, outputTokenProgram,
        swapInstruction
    );
    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      final var wrappedSolPDA = programAccountClient.wrappedSolPDA().publicKey();
      return List.of(
          programAccountClient.transferSolLamports(wrappedSolPDA, amount),
          nativeProgramClient.syncNative(wrappedSolPDA),
          glamJupiterSwap
      );
    } else {
      return List.of(glamJupiterSwap);
    }
  }
}
