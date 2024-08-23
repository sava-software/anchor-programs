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
                                  final PublicKey outputMintKey,
                                  final long amount,
                                  final Instruction swapInstruction) {
//    AccountMeta[publicKey=G8NKLJ2Y3TFrjXpfkpGJQZLXvbKKyvNDzc84C8P3DDU8, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=5SVdBngSHNGtYaQxkzJxso4S7ggaVb3vaKy5LSqbrFpZ, isFeePayer=false, isSigner=false, isWritable=true, invoked=false]
//    AccountMeta[publicKey=DLNBFTZwUWfSSophxasbvA9e9BEvrHFP1XLGGnkQB7P1, isFeePayer=false, isSigner=false, isWritable=true, invoked=false]
//    AccountMeta[publicKey=864DgJCAMKuJrMk2Tf9P8R9oix7Mf13rtzn7RbdtiJ2c, isFeePayer=false, isSigner=false, isWritable=true, invoked=false]
//    AccountMeta[publicKey=BJqhkcnEmZUbyDn3WNNBF9QJDBs1RuE7ZB9GhT6MEeDH, isFeePayer=false, isSigner=false, isWritable=true, invoked=false]
//    AccountMeta[publicKey=ApAZ5291cCG1xQMQ4dysN2wx1RzpvynAK31K4WVE8Ge4, isFeePayer=false, isSigner=false, isWritable=true, invoked=false]
//    AccountMeta[publicKey=So11111111111111111111111111111111111111112, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=mSoLzYCxHdYgdzU16g5QSh3i5K3z3KZK7ytfqcJm7So, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=gLJHKPrZLGBiBZ33hFgZh6YnsEhTVxuRT17UCqNp6ff, isFeePayer=false, isSigner=true, isWritable=true, invoked=false]
//    AccountMeta[publicKey=11111111111111111111111111111111, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=JUP6LkbZbjS1jKKwapdHNy74zcZ3tLUZoi5QNyVTaV4, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=ATokenGPvbdGVxr1b2hvZbsiqW5xWH25efTNsLJA8knL, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=TokenkegQfeZyiNwAJbNbGKPFXCWuBvf9Ss623VQ5DA, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
//    AccountMeta[publicKey=TokenzQdBNbLqP5VEhdkAS6EPFLC1PHnBqCXEpPxuEb, isFeePayer=false, isSigner=false, isWritable=false, invoked=false]
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
        outputMintKey,
        amount,
        swapInstruction
    );

    if (wrapSOL && inputMintKey.equals(solanaAccounts.wrappedSolTokenMint())) {
      return List.of(
//          glamProgramAccountClient.createATAForOwnerFundedByFeePayer(
//              true, inputTreasuryATA, inputMintKey, inputTokenProgram
//          ),
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
