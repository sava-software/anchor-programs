package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;

import java.util.List;

final class GlamJupiterProgramClientImpl implements GlamJupiterProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final GlamFundAccounts glamFundAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;
  private final JupiterAccounts jupiterAccounts;

  GlamJupiterProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient,
                               final JupiterAccounts jupiterAccounts) {
    this.glamProgramAccountClient = glamProgramAccountClient;
    this.solanaAccounts = glamProgramAccountClient.solanaAccounts();
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
                                  final PublicKey inputTokenProgram,
                                  final PublicKey outputMintKey,
                                  final PublicKey outputTokenProgram,
                                  final long amount,
                                  final Instruction swapInstruction) {
    return GlamProgram.jupiterSwap(
        invokedProgram,
        solanaAccounts,
        glamFundAccounts.fundPublicKey(),
        glamFundAccounts.treasuryPublicKey(),
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey, outputMintKey,
        manager.publicKey(),
        jupiterAccounts.swapProgram(),
        inputTokenProgram,
        outputTokenProgram,
        amount,
        swapInstruction.data()
    ).extraAccounts(swapInstruction.accounts());
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

    final var inputTreasuryATA = glamProgramAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();

    final var inputSignerATA = glamProgramAccountClient.findATAForFeePayer(inputTokenProgramKey, inputMintKey).publicKey();
    final var createManagerInputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
        true, inputSignerATA, inputMintKey, inputTokenProgram
    );

    final var outputSignerATA = glamProgramAccountClient.findATAForFeePayer(outputTokenProgramKey, outputMintKey).publicKey();
    final var createManagerOutputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
        true, outputSignerATA, outputMintKey, outputTokenProgram
    );

    final var outputTreasuryATA = glamProgramAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();
    final var createTreasuryOutputATA = glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
        true, outputTreasuryATA, outputMintKey, outputTokenProgram
    );

    final var glamJupiterSwap = jupiterSwap(
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey,
        inputTokenProgram.publicKey(),
        outputMintKey,
        outputTokenProgram.publicKey(),
        amount,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
          glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
              true, inputTreasuryATA, inputMintKey
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

    final var inputTreasuryATA = glamProgramAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();
    final var inputSignerATA = glamProgramAccountClient.findATAForFeePayer(inputTokenProgramKey, inputMintKey).publicKey();
    final var outputSignerATA = glamProgramAccountClient.findATAForFeePayer(outputTokenProgramKey, outputMintKey).publicKey();
    final var outputTreasuryATA = glamProgramAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();

    return jupiterSwap(
        inputTreasuryATA,
        inputSignerATA,
        outputSignerATA,
        outputTreasuryATA,
        inputMintKey,
        inputTokenProgram.publicKey(),
        outputMintKey,
        outputTokenProgram.publicKey(),
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
