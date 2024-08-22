package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;

import static software.sava.solana.programs.token.AssociatedTokenProgram.findAssociatedTokenProgramAddress;

final class GlamJupiterProgramClientImpl implements GlamJupiterProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;
  private final JupiterAccounts jupiterAccounts;

  GlamJupiterProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient, final JupiterAccounts jupiterAccounts) {
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

  private Instruction jupiterSwap(final PublicKey inputTreasuryAtaKey,
                                  final PublicKey inputSignerAtaKey,
                                  final PublicKey outputSignerAtaKey,
                                  final PublicKey outputTreasuryAtaKey,
                                  final PublicKey inputMintKey,
                                  final PublicKey outputMintKey,
                                  final long amount,
                                  final Instruction swapInstruction) {
    return GlamProgram.jupiterSwap(
        invokedProgram,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        inputTreasuryAtaKey,
        inputSignerAtaKey,
        outputSignerAtaKey,
        outputTreasuryAtaKey,
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
  public List<Instruction> jupiterSwapChecked(final PublicKey inputMintKey,
                                              final AccountMeta inputTokenProgram,
                                              final PublicKey outputMintKey,
                                              final AccountMeta outputTokenProgram,
                                              final long amount,
                                              final Instruction swapInstruction,
                                              final boolean wrapSOL) {
    final var inputTreasuryAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, inputTokenProgram.publicKey(), glamFundAccounts.treasuryPublicKey(), inputMintKey).publicKey();

    final var inputSignerAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, inputTokenProgram.publicKey(), manager.publicKey(), inputMintKey).publicKey();
    final var createManagerInputATA = nativeProgramAccountClient.createATAForFeePayerFundedByFeePayer(true, inputTokenProgram, inputSignerAtaKey, inputMintKey);

    final var outputSignerAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, outputTokenProgram.publicKey(), manager.publicKey(), outputMintKey).publicKey();
    final var createManagerOutputATA = nativeProgramAccountClient.createATAForFeePayerFundedByFeePayer(true, outputTokenProgram, manager.publicKey(), outputMintKey);

    final var outputTreasuryAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, outputTokenProgram.publicKey(), glamFundAccounts.treasuryPublicKey(), outputMintKey).publicKey();
    final var createTreasuryOutputATA = nativeProgramAccountClient.createATAForOwnerFundedByFeePayer(true, outputTokenProgram, outputMintKey);

    final var glamJupiterSwap = jupiterSwap(
        inputTreasuryAtaKey,
        inputSignerAtaKey,
        outputSignerAtaKey,
        outputTreasuryAtaKey,
        inputMintKey,
        outputMintKey,
        amount,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
          nativeProgramAccountClient.createATAForOwnerFundedByFeePayer(true, inputMintKey),
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
  public Instruction jupiterSwapUncheckedAndNoWrap(final PublicKey inputMintKey,
                                                   final AccountMeta inputTokenProgram,
                                                   final PublicKey outputMintKey,
                                                   final AccountMeta outputTokenProgram,
                                                   final long amount,
                                                   final Instruction swapInstruction) {
    final var inputTreasuryAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, inputTokenProgram.publicKey(), glamFundAccounts.treasuryPublicKey(), inputMintKey).publicKey();
    final var inputSignerAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, inputTokenProgram.publicKey(), manager.publicKey(), inputMintKey).publicKey();
    final var outputSignerAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, outputTokenProgram.publicKey(), manager.publicKey(), outputMintKey).publicKey();
    final var outputTreasuryAtaKey = findAssociatedTokenProgramAddress(solanaAccounts, outputTokenProgram.publicKey(), glamFundAccounts.treasuryPublicKey(), outputMintKey).publicKey();
    return jupiterSwap(
        inputTreasuryAtaKey,
        inputSignerAtaKey,
        outputSignerAtaKey,
        outputTreasuryAtaKey,
        inputMintKey,
        outputMintKey,
        amount,
        swapInstruction
    );
  }

  @Override
  public List<Instruction> jupiterSwapUnchecked(final PublicKey inputMintKey,
                                                final AccountMeta inputTokenProgram,
                                                final PublicKey outputMintKey,
                                                final AccountMeta outputTokenProgram,
                                                final long amount,
                                                final Instruction swapInstruction,
                                                final boolean wrapSOL) {
    final var glamJupiterSwap = jupiterSwapUncheckedAndNoWrap(
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
