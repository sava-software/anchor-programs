package software.sava.anchor.programs.jupiter.limit.anchor;

import java.util.List;
import java.util.OptionalLong;

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

public final class LimitOrderProgram {

  public static final Discriminator INITIALIZE_ORDER_DISCRIMINATOR = toDiscriminator(133, 110, 74, 175, 112, 159, 245, 159);

  public static Instruction initializeOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                            final PublicKey baseKey,
                                            final PublicKey makerKey,
                                            final PublicKey orderKey,
                                            // CHECK
                                            final PublicKey reserveKey,
                                            final PublicKey makerInputAccountKey,
                                            final PublicKey inputMintKey,
                                            final PublicKey makerOutputAccountKey,
                                            final PublicKey referralKey,
                                            final PublicKey outputMintKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey rentKey,
                                            final long makingAmount,
                                            final long takingAmount,
                                            final OptionalLong expiredAt) {
    final var keys = List.of(
      createReadOnlySigner(baseKey),
      createWritableSigner(makerKey),
      createWrite(orderKey),
      createWrite(reserveKey),
      createWrite(makerInputAccountKey),
      createRead(inputMintKey),
      createRead(makerOutputAccountKey),
      createRead(referralKey),
      createRead(outputMintKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[33];
    int i = writeDiscriminator(INITIALIZE_ORDER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, makingAmount);
    i += 8;
    putInt64LE(_data, i, takingAmount);
    i += 8;
    Borsh.writeOptional(expiredAt, _data, i);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator FILL_ORDER_DISCRIMINATOR = toDiscriminator(232, 122, 115, 25, 199, 143, 136, 162);

  public static Instruction fillOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                      final PublicKey orderKey,
                                      final PublicKey reserveKey,
                                      final PublicKey makerKey,
                                      final PublicKey takerKey,
                                      final PublicKey takerOutputAccountKey,
                                      final PublicKey makerOutputAccountKey,
                                      final PublicKey takerInputAccountKey,
                                      final PublicKey feeAuthorityKey,
                                      final PublicKey programFeeAccountKey,
                                      final PublicKey referralKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey systemProgramKey,
                                      final long makingAmount,
                                      final long maxTakingAmount) {
    final var keys = List.of(
      createWrite(orderKey),
      createWrite(reserveKey),
      createWrite(makerKey),
      createReadOnlySigner(takerKey),
      createWrite(takerOutputAccountKey),
      createWrite(makerOutputAccountKey),
      createWrite(takerInputAccountKey),
      createRead(feeAuthorityKey),
      createWrite(programFeeAccountKey),
      createWrite(referralKey),
      createRead(tokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(FILL_ORDER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, makingAmount);
    i += 8;
    putInt64LE(_data, i, maxTakingAmount);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator PRE_FLASH_FILL_ORDER_DISCRIMINATOR = toDiscriminator(240, 47, 153, 68, 13, 190, 225, 42);

  public static Instruction preFlashFillOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                              final PublicKey orderKey,
                                              final PublicKey reserveKey,
                                              final PublicKey takerKey,
                                              final PublicKey takerOutputAccountKey,
                                              final PublicKey inputMintKey,
                                              final PublicKey inputMintTokenProgramKey,
                                              final PublicKey instructionKey,
                                              final PublicKey systemProgramKey,
                                              final long makingAmount) {
    final var keys = List.of(
      createWrite(orderKey),
      createWrite(reserveKey),
      createReadOnlySigner(takerKey),
      createWrite(takerOutputAccountKey),
      createRead(inputMintKey),
      createRead(inputMintTokenProgramKey),
      createRead(instructionKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(PRE_FLASH_FILL_ORDER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, makingAmount);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator FLASH_FILL_ORDER_DISCRIMINATOR = toDiscriminator(252, 104, 18, 134, 164, 78, 18, 140);

  public static Instruction flashFillOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                           final PublicKey orderKey,
                                           final PublicKey reserveKey,
                                           final PublicKey makerKey,
                                           final PublicKey takerKey,
                                           final PublicKey makerOutputAccountKey,
                                           final PublicKey takerInputAccountKey,
                                           final PublicKey feeAuthorityKey,
                                           final PublicKey programFeeAccountKey,
                                           final PublicKey referralKey,
                                           final PublicKey inputMintKey,
                                           final PublicKey inputMintTokenProgramKey,
                                           final PublicKey outputMintKey,
                                           final PublicKey outputMintTokenProgramKey,
                                           final PublicKey systemProgramKey,
                                           final long maxTakingAmount) {
    final var keys = List.of(
      createWrite(orderKey),
      createWrite(reserveKey),
      createWrite(makerKey),
      createReadOnlySigner(takerKey),
      createWrite(makerOutputAccountKey),
      createWrite(takerInputAccountKey),
      createRead(feeAuthorityKey),
      createWrite(programFeeAccountKey),
      createWrite(referralKey),
      createRead(inputMintKey),
      createRead(inputMintTokenProgramKey),
      createRead(outputMintKey),
      createRead(outputMintTokenProgramKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(FLASH_FILL_ORDER_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxTakingAmount);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_ORDER_DISCRIMINATOR = toDiscriminator(95, 129, 237, 240, 8, 49, 223, 132);

  public static Instruction cancelOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                        final PublicKey orderKey,
                                        // CHECK
                                        final PublicKey reserveKey,
                                        final PublicKey makerKey,
                                        // CHECK, it is not important if it is sol input mint
                                        final PublicKey makerInputAccountKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey inputMintKey) {
    final var keys = List.of(
      createWrite(orderKey),
      createWrite(reserveKey),
      createWritableSigner(makerKey),
      createWrite(makerInputAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(inputMintKey)
    );

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, CANCEL_ORDER_DISCRIMINATOR);
  }

  public static final Discriminator CANCEL_EXPIRED_ORDER_DISCRIMINATOR = toDiscriminator(216, 120, 64, 235, 155, 19, 229, 99);

  public static Instruction cancelExpiredOrder(final AccountMeta invokedLimitOrderProgramMeta,
                                               final PublicKey orderKey,
                                               // CHECK
                                               final PublicKey reserveKey,
                                               final PublicKey makerKey,
                                               // CHECK, it is not important if it is sol input mint
                                               final PublicKey makerInputAccountKey,
                                               final PublicKey systemProgramKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey inputMintKey) {
    final var keys = List.of(
      createWrite(orderKey),
      createWrite(reserveKey),
      createWrite(makerKey),
      createWrite(makerInputAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(inputMintKey)
    );

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, CANCEL_EXPIRED_ORDER_DISCRIMINATOR);
  }

  public static final Discriminator WITHDRAW_FEE_DISCRIMINATOR = toDiscriminator(14, 122, 231, 218, 31, 238, 223, 150);

  public static Instruction withdrawFee(final AccountMeta invokedLimitOrderProgramMeta,
                                        final PublicKey adminKey,
                                        // CHECK
                                        final PublicKey feeAuthorityKey,
                                        final PublicKey programFeeAccountKey,
                                        final PublicKey adminTokenAcocuntKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey mintKey,
                                        final long amount) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(feeAuthorityKey),
      createWrite(programFeeAccountKey),
      createWrite(adminTokenAcocuntKey),
      createRead(tokenProgramKey),
      createRead(mintKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(WITHDRAW_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator INIT_FEE_DISCRIMINATOR = toDiscriminator(13, 9, 211, 107, 62, 172, 224, 67);

  public static Instruction initFee(final AccountMeta invokedLimitOrderProgramMeta,
                                    final PublicKey keeperKey,
                                    final PublicKey feeAuthorityKey,
                                    final PublicKey systemProgramKey,
                                    final long makerFee,
                                    final long makerStableFee,
                                    final long takerFee,
                                    final long takerStableFee) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(feeAuthorityKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(INIT_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, makerFee);
    i += 8;
    putInt64LE(_data, i, makerStableFee);
    i += 8;
    putInt64LE(_data, i, takerFee);
    i += 8;
    putInt64LE(_data, i, takerStableFee);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_FEE_DISCRIMINATOR = toDiscriminator(232, 253, 195, 247, 148, 212, 73, 222);

  public static Instruction updateFee(final AccountMeta invokedLimitOrderProgramMeta,
                                      final PublicKey keeperKey,
                                      final PublicKey feeAuthorityKey,
                                      final long makerFee,
                                      final long makerStableFee,
                                      final long takerFee,
                                      final long takerStableFee) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createWrite(feeAuthorityKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_FEE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, makerFee);
    i += 8;
    putInt64LE(_data, i, makerStableFee);
    i += 8;
    putInt64LE(_data, i, takerFee);
    i += 8;
    putInt64LE(_data, i, takerStableFee);

    return Instruction.createInstruction(invokedLimitOrderProgramMeta, keys, _data);
  }

  private LimitOrderProgram() {
  }
}