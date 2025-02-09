package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.jupiter.JupiterAccounts;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;

import java.util.List;
import java.util.Map;
import java.util.Objects;

final class GlamJupiterProgramClientImpl implements GlamJupiterProgramClient {

  private final GlamProgramAccountClient glamProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta manager;
  private final JupiterAccounts jupiterAccounts;
  private final PublicKey swapProgram;

  GlamJupiterProgramClientImpl(final GlamProgramAccountClient glamProgramAccountClient,
                               final JupiterAccounts jupiterAccounts) {
    this.glamProgramAccountClient = glamProgramAccountClient;
    this.solanaAccounts = glamProgramAccountClient.solanaAccounts();
    this.glamVaultAccounts = glamProgramAccountClient.vaultAccounts();
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.manager = glamProgramAccountClient.feePayer();
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

  private Instruction jupiterSwap(final PublicKey inputVaultATA,
                                  final PublicKey outputVaultATA,
                                  final PublicKey inputProgramStateKey,
                                  final PublicKey inputMintKey,
                                  final PublicKey inputTokenProgram,
                                  final PublicKey outputProgramStateKey,
                                  final PublicKey outputMintKey,
                                  final PublicKey outputTokenProgram,
                                  final long amount,
                                  final Instruction swapInstruction) {
    return GlamProgram.jupiterSwap(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        inputVaultATA, outputVaultATA,
        inputMintKey, outputMintKey,
        manager.publicKey(),
        Objects.requireNonNullElse(inputProgramStateKey, swapProgram),
        Objects.requireNonNullElse(outputProgramStateKey, swapProgram),
        swapProgram,
        inputTokenProgram, outputTokenProgram,
        amount,
        swapInstruction.data()
    ).extraAccounts(swapInstruction.accounts());
  }

  @Override
  public Map<PublicKey, Instruction> createSwapTokenAccountsIdempotent(final AccountMeta inputTokenProgram,
                                                                       final PublicKey inputMintKey,
                                                                       final AccountMeta outputTokenProgram,
                                                                       final PublicKey outputMintKey) {
    final var inputTokenProgramKey = inputTokenProgram.publicKey();
//    final var inputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(inputTokenProgramKey, inputMintKey).publicKey();
//    final var createFeePayerInputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
//        true, inputFeePayerATA, inputMintKey, inputTokenProgram
//    );

    final var outputTokenProgramKey = outputTokenProgram.publicKey();
//    final var outputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(outputTokenProgramKey, outputMintKey).publicKey();
//    final var createFeePayerOutputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
//        true, outputFeePayerATA, outputMintKey, outputTokenProgram
//    );

    final var outputVaultATA = glamProgramAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();
    final var createVaultOutputATA = glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
        true, outputVaultATA, outputMintKey, outputTokenProgram
    );

    if (inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      final var inputVaultATA = glamProgramAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();
      final var createVaultInputATA = glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
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

    final var inputVaultATA = glamProgramAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();

//    final var inputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(inputTokenProgramKey, inputMintKey).publicKey();
//    final var createFeePayerInputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
//        true, inputFeePayerATA, inputMintKey, inputTokenProgram
//    );
//
    final var outputTokenProgramKey = outputTokenProgram.publicKey();
//    final var outputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(outputTokenProgramKey, outputMintKey).publicKey();
//    final var createFeePayerOutputATA = glamProgramAccountClient.createATAForFeePayerFundedByFeePayer(
//        true, outputFeePayerATA, outputMintKey, outputTokenProgram
//    );

    final var outputVaultATA = glamProgramAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();
    final var createVaultOutputATA = glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
        true, outputVaultATA, outputMintKey, outputTokenProgram
    );

    final var glamJupiterSwap = jupiterSwap(
        inputVaultATA,
        outputVaultATA,
        inputProgramStateKey,
        inputMintKey,
        inputTokenProgramKey,
        outputProgramStateKey,
        outputMintKey,
        outputTokenProgramKey,
        amount,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
          glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
              true, inputVaultATA, inputMintKey, inputTokenProgram
          ),
          glamProgramAccountClient.transferLamportsAndSyncNative(amount),
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
                                            final long amount,
                                            final Instruction swapInstruction) {
    final var inputTokenProgramKey = inputTokenProgram.publicKey();
    final var inputVaultATA = glamProgramAccountClient.findATA(inputTokenProgramKey, inputMintKey).publicKey();
//    final var inputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(inputTokenProgramKey, inputMintKey).publicKey();

    final var outputTokenProgramKey = outputTokenProgram.publicKey();
//    final var outputFeePayerATA = glamProgramAccountClient.findATAForFeePayer(outputTokenProgramKey, outputMintKey).publicKey();
    final var outputVaultATA = glamProgramAccountClient.findATA(outputTokenProgramKey, outputMintKey).publicKey();

    return jupiterSwap(
        inputVaultATA,
        outputVaultATA,
        inputProgramStateKey,
        inputMintKey,
        inputTokenProgram.publicKey(),
        outputProgramStateKey,
        outputMintKey,
        outputTokenProgram.publicKey(),
        amount,
        swapInstruction
    );
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
        amount,
        swapInstruction
    );
    return wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())
        ? List.of(glamProgramAccountClient.transferLamportsAndSyncNative(amount), glamJupiterSwap)
        : List.of(glamJupiterSwap);
  }
}
