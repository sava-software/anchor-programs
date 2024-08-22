package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;

final class GlamJupiterProgramClientImpl implements GlamJupiterProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;
  private final JupiterAccounts jupiterAccounts;

  GlamJupiterProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient,
                               final JupiterAccounts jupiterAccounts) {
    this.glamProgramAccountClient = glamProgramAccountClient;
    this.solanaAccounts = glamProgramAccountClient.solanaAccounts();
    this.nativeProgramAccountClient = glamProgramAccountClient.delegatedNativeProgramAccountClient();
    this.glamFundAccounts = glamProgramAccountClient.fundAccounts();
    this.invokedProgram = glamFundAccounts.glamAccounts().invokedProgram();
    this.manager = glamProgramAccountClient.feePayer();
    this.jupiterAccounts = jupiterAccounts;
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public JupiterAccounts jupiterAccounts() {
    return jupiterAccounts;
  }

  private Instruction jupiterSwap(final PublicKey inputTreasuryATA,
                                  final PublicKey inputSignerATA,
                                  final PublicKey outputSignerATA,
                                  final PublicKey outputTreasuryATA,
                                  final PublicKey inputMintKey,
                                  final PublicKey outputMintKey,
                                  final long amount,
                                  final Instruction swapInstruction) {
    return GlamProgram.jupiterSwap(
        invokedProgram,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey, outputMintKey,
        manager.publicKey(),
        solanaAccounts.systemProgram(),
        jupiterAccounts.swapProgram(),
        solanaAccounts.associatedTokenAccountProgram(),
        solanaAccounts.tokenProgram(),
        solanaAccounts.token2022Program(),
        amount,
        swapInstruction.data()
    );
  }

  @Override
  public List<Instruction> swapChecked(final PublicKey inputMintKey,
                                       final AccountMeta inputTokenProgram,
                                       final PublicKey outputMintKey,
                                       final AccountMeta outputTokenProgram,
                                       final long amount,
                                       final Instruction swapInstruction,
                                       final boolean wrapSOL) {
    final var inputTokenProgramKey = inputTokenProgram.publicKey();
    final var outputTokenProgramKey = outputTokenProgram.publicKey();

    final var inputTreasuryATA = nativeProgramAccountClient.findAssociatedTokenProgramAddress(
        inputMintKey,
        inputTokenProgramKey
    ).publicKey();

    final var inputSignerATA = nativeProgramAccountClient.findAssociatedTokenProgramAddressForFeePayer(
        inputMintKey,
        inputTokenProgramKey
    ).publicKey();
    final var createManagerInputATA = nativeProgramAccountClient.createATAForFeePayerFundedByFeePayer(
        true,
        inputTokenProgram,
        inputSignerATA,
        inputMintKey
    );

    final var outputSignerATA = nativeProgramAccountClient.findAssociatedTokenProgramAddressForFeePayer(
        outputMintKey,
        outputTokenProgramKey
    ).publicKey();
    final var createManagerOutputATA = nativeProgramAccountClient.createATAForFeePayerFundedByFeePayer(
        true,
        outputTokenProgram,
        outputSignerATA,
        outputMintKey
    );

    final var outputTreasuryATA = nativeProgramAccountClient.findAssociatedTokenProgramAddress(
        outputMintKey,
        outputTokenProgramKey
    ).publicKey();
    final var createTreasuryOutputATA = nativeProgramAccountClient.createATAForOwnerFundedByFeePayer(
        true,
        outputTokenProgram,
        outputTreasuryATA,
        outputMintKey
    );

    final var glamJupiterSwap = jupiterSwap(
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey,
        outputMintKey,
        amount,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
          nativeProgramAccountClient.createATAForOwnerFundedByFeePayer(
              true,
              inputTokenProgram,
              inputTreasuryATA,
              inputMintKey
          ),
          glamProgramAccountClient.transferLamportsAndSyncNative(amount),
          createManagerInputATA,
          createManagerOutputATA,
          createTreasuryOutputATA,
          glamJupiterSwap
      );
    } else {
      return List.of(
          createManagerInputATA,
          createManagerOutputATA,
          createTreasuryOutputATA,
          glamJupiterSwap
      );
    }
  }

  @Override
  public Instruction swapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                            final AccountMeta inputTokenProgram,
                                            final PublicKey outputMintKey,
                                            final AccountMeta outputTokenProgram,
                                            final long amount,
                                            final Instruction swapInstruction) {
    final var inputTokenProgramKey = inputTokenProgram.publicKey();
    final var outputTokenProgramKey = outputTokenProgram.publicKey();

    final var inputTreasuryATA = nativeProgramAccountClient.findAssociatedTokenProgramAddress(
        inputMintKey,
        inputTokenProgramKey
    ).publicKey();
    final var inputSignerATA = nativeProgramAccountClient.findAssociatedTokenProgramAddressForFeePayer(
        inputMintKey,
        inputTokenProgramKey
    ).publicKey();
    final var outputSignerATA = nativeProgramAccountClient.findAssociatedTokenProgramAddressForFeePayer(
        outputMintKey,
        outputTokenProgramKey
    ).publicKey();
    final var outputTreasuryATA = nativeProgramAccountClient.findAssociatedTokenProgramAddress(
        outputMintKey,
        outputTokenProgramKey
    ).publicKey();

    return jupiterSwap(
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey,
        outputMintKey,
        amount,
        swapInstruction
    );
  }

  @Override
  public List<Instruction> swapUnchecked(final PublicKey inputMintKey,
                                         final AccountMeta inputTokenProgram,
                                         final PublicKey outputMintKey,
                                         final AccountMeta outputTokenProgram,
                                         final long amount,
                                         final Instruction swapInstruction,
                                         final boolean wrapSOL) {
    final var glamJupiterSwap = swapUncheckedAndNoWrap(
        inputMintKey, inputTokenProgram,
        outputMintKey, outputTokenProgram,
        amount,
        swapInstruction
    );

    return wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())
        ? List.of(glamProgramAccountClient.transferLamportsAndSyncNative(amount), glamJupiterSwap)
        : List.of(glamJupiterSwap);
  }

}
