package software.sava.anchor.programs.jupiter.dca.anchor;

import java.lang.Boolean;

import java.util.List;
import java.util.OptionalLong;

import software.sava.anchor.programs.jupiter.dca.anchor.types.WithdrawParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class DcaProgram {

  public static final Discriminator OPEN_DCA_DISCRIMINATOR = toDiscriminator(36, 65, 185, 54, 1, 210, 100, 163);

  public static Instruction openDca(final AccountMeta invokedDcaProgramMeta,
                                    final PublicKey dcaKey,
                                    final PublicKey userKey,
                                    final PublicKey inputMintKey,
                                    final PublicKey outputMintKey,
                                    final PublicKey userAtaKey,
                                    final PublicKey inAtaKey,
                                    final PublicKey outAtaKey,
                                    final PublicKey systemProgramKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey associatedTokenProgramKey,
                                    final PublicKey eventAuthorityKey,
                                    final PublicKey programKey,
                                    final long applicationIdx,
                                    final long inAmount,
                                    final long inAmountPerCycle,
                                    final long cycleFrequency,
                                    final OptionalLong minOutAmount,
                                    final OptionalLong maxOutAmount,
                                    final OptionalLong startAt,
                                    final Boolean closeWsolInAta) {
    final var keys = List.of(
      createWrite(dcaKey),
      createWritableSigner(userKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWrite(userAtaKey),
      createWrite(inAtaKey),
      createWrite(outAtaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[69];
    int i = writeDiscriminator(OPEN_DCA_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, applicationIdx);
    i += 8;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, inAmountPerCycle);
    i += 8;
    putInt64LE(_data, i, cycleFrequency);
    i += 8;
    i += Borsh.writeOptional(minOutAmount, _data, i);
    i += Borsh.writeOptional(maxOutAmount, _data, i);
    i += Borsh.writeOptional(startAt, _data, i);
    Borsh.writeOptional(closeWsolInAta, _data, i);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator OPEN_DCA_V2_DISCRIMINATOR = toDiscriminator(142, 119, 43, 109, 162, 52, 11, 177);

  public static Instruction openDcaV2(final AccountMeta invokedDcaProgramMeta,
                                      final PublicKey dcaKey,
                                      final PublicKey userKey,
                                      final PublicKey payerKey,
                                      final PublicKey inputMintKey,
                                      final PublicKey outputMintKey,
                                      final PublicKey userAtaKey,
                                      final PublicKey inAtaKey,
                                      final PublicKey outAtaKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey associatedTokenProgramKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final long applicationIdx,
                                      final long inAmount,
                                      final long inAmountPerCycle,
                                      final long cycleFrequency,
                                      final OptionalLong minOutAmount,
                                      final OptionalLong maxOutAmount,
                                      final OptionalLong startAt) {
    final var keys = List.of(
      createWrite(dcaKey),
      createReadOnlySigner(userKey),
      createWritableSigner(payerKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWrite(userAtaKey),
      createWrite(inAtaKey),
      createWrite(outAtaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[67];
    int i = writeDiscriminator(OPEN_DCA_V2_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, applicationIdx);
    i += 8;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, inAmountPerCycle);
    i += 8;
    putInt64LE(_data, i, cycleFrequency);
    i += 8;
    i += Borsh.writeOptional(minOutAmount, _data, i);
    i += Borsh.writeOptional(maxOutAmount, _data, i);
    Borsh.writeOptional(startAt, _data, i);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator CLOSE_DCA_DISCRIMINATOR = toDiscriminator(22, 7, 33, 98, 168, 183, 34, 243);

  public static Instruction closeDca(final AccountMeta invokedDcaProgramMeta,
                                     final PublicKey userKey,
                                     final PublicKey dcaKey,
                                     final PublicKey inputMintKey,
                                     final PublicKey outputMintKey,
                                     final PublicKey inAtaKey,
                                     final PublicKey outAtaKey,
                                     final PublicKey userInAtaKey,
                                     final PublicKey userOutAtaKey,
                                     final PublicKey systemProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey associatedTokenProgramKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWrite(inAtaKey),
      createWrite(outAtaKey),
      createWrite(userInAtaKey),
      createWrite(userOutAtaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, CLOSE_DCA_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedDcaProgramMeta,
                                     final PublicKey userKey,
                                     final PublicKey dcaKey,
                                     final PublicKey inputMintKey,
                                     final PublicKey outputMintKey,
                                     final PublicKey dcaAtaKey,
                                     final PublicKey userInAtaKey,
                                     final PublicKey userOutAtaKey,
                                     final PublicKey systemProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey associatedTokenProgramKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey,
                                     final WithdrawParams withdrawParams) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWrite(dcaAtaKey),
      createWrite(userInAtaKey),
      createWrite(userOutAtaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(withdrawParams)];
    int i = writeDiscriminator(WITHDRAW_DISCRIMINATOR, _data, 0);
    Borsh.write(withdrawParams, _data, i);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedDcaProgramMeta,
                                    final PublicKey userKey,
                                    final PublicKey dcaKey,
                                    final PublicKey inAtaKey,
                                    final PublicKey userInAtaKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey eventAuthorityKey,
                                    final PublicKey programKey,
                                    final long depositIn) {
    final var keys = List.of(
      createWritableSigner(userKey),
      createWrite(dcaKey),
      createWrite(inAtaKey),
      createWrite(userInAtaKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, depositIn);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator WITHDRAW_FEES_DISCRIMINATOR = toDiscriminator(198, 212, 171, 109, 144, 215, 174, 89);

  public static Instruction withdrawFees(final AccountMeta invokedDcaProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey mintKey,
                                         // CHECK
                                         final PublicKey feeAuthorityKey,
                                         final PublicKey programFeeAtaKey,
                                         final PublicKey adminFeeAtaKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey associatedTokenProgramKey,
                                         final long amount) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(mintKey),
      createRead(feeAuthorityKey),
      createWrite(programFeeAtaKey),
      createWrite(adminFeeAtaKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_FEES_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator INITIATE_FLASH_FILL_DISCRIMINATOR = toDiscriminator(143, 205, 3, 191, 162, 215, 245, 49);

  public static Instruction initiateFlashFill(final AccountMeta invokedDcaProgramMeta,
                                              final PublicKey keeperKey,
                                              final PublicKey dcaKey,
                                              // The token to borrow
                                              final PublicKey inputMintKey,
                                              // The account to send borrowed tokens to
                                              final PublicKey keeperInAtaKey,
                                              // The account to borrow from
                                              final PublicKey inAtaKey,
                                              // The account to repay to
                                              final PublicKey outAtaKey,
                                              // Solana Instructions Sysvar
                                              final PublicKey instructionsSysvarKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey associatedTokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createWrite(keeperInAtaKey),
      createWrite(inAtaKey),
      createRead(outAtaKey),
      createRead(instructionsSysvarKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, INITIATE_FLASH_FILL_DISCRIMINATOR);
  }

  public static final Discriminator FULFILL_FLASH_FILL_DISCRIMINATOR = toDiscriminator(115, 64, 226, 78, 33, 211, 105, 162);

  public static Instruction fulfillFlashFill(final AccountMeta invokedDcaProgramMeta,
                                             final PublicKey keeperKey,
                                             final PublicKey dcaKey,
                                             final PublicKey inputMintKey,
                                             final PublicKey outputMintKey,
                                             final PublicKey keeperInAtaKey,
                                             final PublicKey inAtaKey,
                                             final PublicKey outAtaKey,
                                             // CHECK
                                             final PublicKey feeAuthorityKey,
                                             final PublicKey feeAtaKey,
                                             // Solana Instructions Sysvar
                                             final PublicKey instructionsSysvarKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey associatedTokenProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final long repayAmount) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createRead(keeperInAtaKey),
      createRead(inAtaKey),
      createRead(outAtaKey),
      createRead(feeAuthorityKey),
      createWrite(feeAtaKey),
      createRead(instructionsSysvarKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FULFILL_FLASH_FILL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, repayAmount);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator INITIATE_DLMM_FILL_DISCRIMINATOR = toDiscriminator(155, 193, 80, 121, 91, 147, 254, 187);

  public static Instruction initiateDlmmFill(final AccountMeta invokedDcaProgramMeta,
                                             final PublicKey keeperKey,
                                             final PublicKey dcaKey,
                                             // The token to borrow
                                             final PublicKey inputMintKey,
                                             // The account to send borrowed tokens to
                                             final PublicKey keeperInAtaKey,
                                             // The account to borrow from
                                             final PublicKey inAtaKey,
                                             // The account to repay to
                                             final PublicKey outAtaKey,
                                             // Solana Instructions Sysvar
                                             final PublicKey instructionsSysvarKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey associatedTokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createWrite(keeperInAtaKey),
      createWrite(inAtaKey),
      createRead(outAtaKey),
      createRead(instructionsSysvarKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey)
    );

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, INITIATE_DLMM_FILL_DISCRIMINATOR);
  }

  public static final Discriminator FULFILL_DLMM_FILL_DISCRIMINATOR = toDiscriminator(1, 230, 118, 251, 45, 177, 101, 187);

  public static Instruction fulfillDlmmFill(final AccountMeta invokedDcaProgramMeta,
                                            final PublicKey keeperKey,
                                            final PublicKey dcaKey,
                                            final PublicKey inputMintKey,
                                            final PublicKey outputMintKey,
                                            final PublicKey keeperInAtaKey,
                                            final PublicKey inAtaKey,
                                            final PublicKey outAtaKey,
                                            // CHECK
                                            final PublicKey feeAuthorityKey,
                                            final PublicKey feeAtaKey,
                                            // Solana Instructions Sysvar
                                            final PublicKey instructionsSysvarKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey associatedTokenProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final long repayAmount) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createRead(keeperInAtaKey),
      createRead(inAtaKey),
      createRead(outAtaKey),
      createRead(feeAuthorityKey),
      createWrite(feeAtaKey),
      createRead(instructionsSysvarKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FULFILL_DLMM_FILL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, repayAmount);

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, _data);
  }

  public static final Discriminator TRANSFER_DISCRIMINATOR = toDiscriminator(163, 52, 200, 231, 140, 3, 69, 186);

  public static Instruction transfer(final AccountMeta invokedDcaProgramMeta,
                                     final PublicKey keeperKey,
                                     final PublicKey dcaKey,
                                     final PublicKey userKey,
                                     final PublicKey outputMintKey,
                                     final PublicKey dcaOutAtaKey,
                                     final PublicKey userOutAtaKey,
                                     final PublicKey intermediateAccountKey,
                                     final PublicKey systemProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final PublicKey associatedTokenProgramKey,
                                     final PublicKey eventAuthorityKey,
                                     final PublicKey programKey) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createWrite(userKey),
      createRead(outputMintKey),
      createWrite(dcaOutAtaKey),
      createWrite(userOutAtaKey),
      createWrite(intermediateAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, TRANSFER_DISCRIMINATOR);
  }

  public static final Discriminator END_AND_CLOSE_DISCRIMINATOR = toDiscriminator(83, 125, 166, 69, 247, 252, 103, 133);

  public static Instruction endAndClose(final AccountMeta invokedDcaProgramMeta,
                                        final PublicKey keeperKey,
                                        final PublicKey dcaKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final PublicKey inAtaKey,
                                        final PublicKey outAtaKey,
                                        final PublicKey userKey,
                                        final PublicKey userOutAtaKey,
                                        final PublicKey initUserOutAtaKey,
                                        final PublicKey intermediateAccountKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey associatedTokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(dcaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createWrite(inAtaKey),
      createWrite(outAtaKey),
      createWrite(userKey),
      createWrite(userOutAtaKey),
      createWrite(initUserOutAtaKey),
      createWrite(intermediateAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(associatedTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedDcaProgramMeta, keys, END_AND_CLOSE_DISCRIMINATOR);
  }

  private DcaProgram() {
  }
}
