package software.sava.anchor.programs.drift.anchor;

import java.math.BigInteger;

import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.anchor.programs.drift.anchor.types.AssetTier;
import software.sava.anchor.programs.drift.anchor.types.ContractTier;
import software.sava.anchor.programs.drift.anchor.types.FeeStructure;
import software.sava.anchor.programs.drift.anchor.types.MarketStatus;
import software.sava.anchor.programs.drift.anchor.types.MarketType;
import software.sava.anchor.programs.drift.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.drift.anchor.types.OracleGuardRails;
import software.sava.anchor.programs.drift.anchor.types.OracleSource;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.PositionDirection;
import software.sava.anchor.programs.drift.anchor.types.PrelaunchOracleParams;
import software.sava.anchor.programs.drift.anchor.types.SettlePnlMode;
import software.sava.anchor.programs.drift.anchor.types.SpotFulfillmentConfigStatus;
import software.sava.anchor.programs.drift.anchor.types.SpotFulfillmentType;
import software.sava.anchor.programs.drift.anchor.types.SwapReduceOnly;
import software.sava.anchor.programs.drift.anchor.types.UpdatePerpMarketSummaryStatsParams;
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
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class DriftProgram {

  public static final Discriminator INITIALIZE_USER_DISCRIMINATOR = toDiscriminator(111, 17, 185, 250, 60, 122, 38, 254);

  public static Instruction initializeUser(final AccountMeta invokedDriftProgramMeta,
                                           final PublicKey userKey,
                                           final PublicKey userStatsKey,
                                           final PublicKey stateKey,
                                           final PublicKey authorityKey,
                                           final PublicKey payerKey,
                                           final PublicKey rentKey,
                                           final PublicKey systemProgramKey,
                                           final int subAccountId,
                                           final int[] name) {
    final var keys = List.of(
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10 + Borsh.fixedLen(name)];
    int i = writeDiscriminator(INITIALIZE_USER_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_USER_STATS_DISCRIMINATOR = toDiscriminator(254, 243, 72, 98, 251, 130, 168, 213);

  public static Instruction initializeUserStats(final AccountMeta invokedDriftProgramMeta,
                                                final PublicKey userStatsKey,
                                                final PublicKey stateKey,
                                                final PublicKey authorityKey,
                                                final PublicKey payerKey,
                                                final PublicKey rentKey,
                                                final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWrite(userStatsKey),
      createWrite(stateKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, INITIALIZE_USER_STATS_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_REFERRER_NAME_DISCRIMINATOR = toDiscriminator(235, 126, 231, 10, 42, 164, 26, 61);

  public static Instruction initializeReferrerName(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey referrerNameKey,
                                                   final PublicKey userKey,
                                                   final PublicKey userStatsKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey payerKey,
                                                   final PublicKey rentKey,
                                                   final PublicKey systemProgramKey,
                                                   final int[] name) {
    final var keys = List.of(
      createWrite(referrerNameKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.fixedLen(name)];
    int i = writeDiscriminator(INITIALIZE_REFERRER_NAME_DISCRIMINATOR, _data, 0);
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DEPOSIT_DISCRIMINATOR = toDiscriminator(242, 35, 198, 137, 82, 225, 242, 182);

  public static Instruction deposit(final AccountMeta invokedDriftProgramMeta,
                                    final PublicKey stateKey,
                                    final PublicKey userKey,
                                    final PublicKey userStatsKey,
                                    final PublicKey authorityKey,
                                    final PublicKey spotMarketVaultKey,
                                    final PublicKey userTokenAccountKey,
                                    final PublicKey tokenProgramKey,
                                    final int marketIndex,
                                    final long amount,
                                    final boolean reduceOnly) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(spotMarketVaultKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[19];
    int i = writeDiscriminator(DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (reduceOnly ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator WITHDRAW_DISCRIMINATOR = toDiscriminator(183, 18, 70, 156, 148, 109, 161, 34);

  public static Instruction withdraw(final AccountMeta invokedDriftProgramMeta,
                                     final PublicKey stateKey,
                                     final PublicKey userKey,
                                     final PublicKey userStatsKey,
                                     final PublicKey authorityKey,
                                     final PublicKey spotMarketVaultKey,
                                     final PublicKey driftSignerKey,
                                     final PublicKey userTokenAccountKey,
                                     final PublicKey tokenProgramKey,
                                     final int marketIndex,
                                     final long amount,
                                     final boolean reduceOnly) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(spotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[19];
    int i = writeDiscriminator(WITHDRAW_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) (reduceOnly ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator TRANSFER_DEPOSIT_DISCRIMINATOR = toDiscriminator(20, 20, 147, 223, 41, 63, 204, 111);

  public static Instruction transferDeposit(final AccountMeta invokedDriftProgramMeta,
                                            final PublicKey fromUserKey,
                                            final PublicKey toUserKey,
                                            final PublicKey userStatsKey,
                                            final PublicKey authorityKey,
                                            final PublicKey stateKey,
                                            final PublicKey spotMarketVaultKey,
                                            final int marketIndex,
                                            final long amount) {
    final var keys = List.of(
      createWrite(fromUserKey),
      createWrite(toUserKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createRead(stateKey),
      createRead(spotMarketVaultKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(TRANSFER_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_PERP_ORDER_DISCRIMINATOR = toDiscriminator(69, 161, 93, 202, 120, 126, 76, 185);

  public static Instruction placePerpOrder(final AccountMeta invokedDriftProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey userKey,
                                           final PublicKey authorityKey,
                                           final OrderParams params) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(PLACE_PERP_ORDER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_ORDER_DISCRIMINATOR = toDiscriminator(95, 129, 237, 240, 8, 49, 223, 132);

  public static Instruction cancelOrder(final AccountMeta invokedDriftProgramMeta,
                                        final PublicKey stateKey,
                                        final PublicKey userKey,
                                        final PublicKey authorityKey,
                                        final OptionalInt orderId) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[13];
    int i = writeDiscriminator(CANCEL_ORDER_DISCRIMINATOR, _data, 0);
    Borsh.writeOptional(orderId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_ORDER_BY_USER_ID_DISCRIMINATOR = toDiscriminator(107, 211, 250, 133, 18, 37, 57, 100);

  public static Instruction cancelOrderByUserId(final AccountMeta invokedDriftProgramMeta,
                                                final PublicKey stateKey,
                                                final PublicKey userKey,
                                                final PublicKey authorityKey,
                                                final int userOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(CANCEL_ORDER_BY_USER_ID_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) userOrderId;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_ORDERS_DISCRIMINATOR = toDiscriminator(238, 225, 95, 158, 227, 103, 8, 194);

  public static Instruction cancelOrders(final AccountMeta invokedDriftProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey userKey,
                                         final PublicKey authorityKey,
                                         final MarketType marketType,
                                         final OptionalInt marketIndex,
                                         final PositionDirection direction) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[13 + (marketType == null ? 0 : Borsh.len(marketType)) + (direction == null ? 0 : Borsh.len(direction))];
    int i = writeDiscriminator(CANCEL_ORDERS_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(marketType, _data, i);
    i += Borsh.writeOptional(marketIndex, _data, i);
    Borsh.writeOptional(direction, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_ORDERS_BY_IDS_DISCRIMINATOR = toDiscriminator(134, 19, 144, 165, 94, 240, 210, 94);

  public static Instruction cancelOrdersByIds(final AccountMeta invokedDriftProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey userKey,
                                              final PublicKey authorityKey,
                                              final int[] orderIds) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(orderIds)];
    int i = writeDiscriminator(CANCEL_ORDERS_BY_IDS_DISCRIMINATOR, _data, 0);
    Borsh.write(orderIds, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator MODIFY_ORDER_DISCRIMINATOR = toDiscriminator(47, 124, 117, 255, 201, 197, 130, 94);

  public static Instruction modifyOrder(final AccountMeta invokedDriftProgramMeta,
                                        final PublicKey stateKey,
                                        final PublicKey userKey,
                                        final PublicKey authorityKey,
                                        final OptionalInt orderId,
                                        final ModifyOrderParams modifyOrderParams) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[13 + Borsh.len(modifyOrderParams)];
    int i = writeDiscriminator(MODIFY_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(orderId, _data, i);
    Borsh.write(modifyOrderParams, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator MODIFY_ORDER_BY_USER_ID_DISCRIMINATOR = toDiscriminator(158, 77, 4, 253, 252, 194, 161, 179);

  public static Instruction modifyOrderByUserId(final AccountMeta invokedDriftProgramMeta,
                                                final PublicKey stateKey,
                                                final PublicKey userKey,
                                                final PublicKey authorityKey,
                                                final int userOrderId,
                                                final ModifyOrderParams modifyOrderParams) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[9 + Borsh.len(modifyOrderParams)];
    int i = writeDiscriminator(MODIFY_ORDER_BY_USER_ID_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) userOrderId;
    ++i;
    Borsh.write(modifyOrderParams, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_AND_TAKE_PERP_ORDER_DISCRIMINATOR = toDiscriminator(213, 51, 1, 187, 108, 220, 230, 224);

  public static Instruction placeAndTakePerpOrder(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey authorityKey,
                                                  final OrderParams params,
                                                  final OptionalInt makerOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[13 + Borsh.len(params)];
    int i = writeDiscriminator(PLACE_AND_TAKE_PERP_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(params, _data, i);
    Borsh.writeOptional(makerOrderId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_AND_MAKE_PERP_ORDER_DISCRIMINATOR = toDiscriminator(149, 117, 11, 237, 47, 95, 89, 237);

  public static Instruction placeAndMakePerpOrder(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey takerKey,
                                                  final PublicKey takerStatsKey,
                                                  final PublicKey authorityKey,
                                                  final OrderParams params,
                                                  final int takerOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(takerKey),
      createWrite(takerStatsKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[12 + Borsh.len(params)];
    int i = writeDiscriminator(PLACE_AND_MAKE_PERP_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(params, _data, i);
    putInt32LE(_data, i, takerOrderId);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_SPOT_ORDER_DISCRIMINATOR = toDiscriminator(45, 79, 81, 160, 248, 90, 91, 220);

  public static Instruction placeSpotOrder(final AccountMeta invokedDriftProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey userKey,
                                           final PublicKey authorityKey,
                                           final OrderParams params) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(PLACE_SPOT_ORDER_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_AND_TAKE_SPOT_ORDER_DISCRIMINATOR = toDiscriminator(191, 3, 138, 71, 114, 198, 202, 100);

  public static Instruction placeAndTakeSpotOrder(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey authorityKey,
                                                  final OrderParams params,
                                                  final SpotFulfillmentType fulfillmentType,
                                                  final OptionalInt makerOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[14 + Borsh.len(params) + (fulfillmentType == null ? 0 : Borsh.len(fulfillmentType))];
    int i = writeDiscriminator(PLACE_AND_TAKE_SPOT_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(params, _data, i);
    i += Borsh.writeOptional(fulfillmentType, _data, i);
    Borsh.writeOptional(makerOrderId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_AND_MAKE_SPOT_ORDER_DISCRIMINATOR = toDiscriminator(149, 158, 85, 66, 239, 9, 243, 98);

  public static Instruction placeAndMakeSpotOrder(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey takerKey,
                                                  final PublicKey takerStatsKey,
                                                  final PublicKey authorityKey,
                                                  final OrderParams params,
                                                  final int takerOrderId,
                                                  final SpotFulfillmentType fulfillmentType) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(takerKey),
      createWrite(takerStatsKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[13 + Borsh.len(params) + (fulfillmentType == null ? 0 : Borsh.len(fulfillmentType))];
    int i = writeDiscriminator(PLACE_AND_MAKE_SPOT_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.write(params, _data, i);
    putInt32LE(_data, i, takerOrderId);
    i += 4;
    Borsh.writeOptional(fulfillmentType, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PLACE_ORDERS_DISCRIMINATOR = toDiscriminator(60, 63, 50, 123, 12, 197, 60, 190);

  public static Instruction placeOrders(final AccountMeta invokedDriftProgramMeta,
                                        final PublicKey stateKey,
                                        final PublicKey userKey,
                                        final PublicKey authorityKey,
                                        final OrderParams[] params) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(PLACE_ORDERS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator BEGIN_SWAP_DISCRIMINATOR = toDiscriminator(174, 109, 228, 1, 242, 105, 232, 105);

  public static Instruction beginSwap(final AccountMeta invokedDriftProgramMeta,
                                      final PublicKey stateKey,
                                      final PublicKey userKey,
                                      final PublicKey userStatsKey,
                                      final PublicKey authorityKey,
                                      final PublicKey outSpotMarketVaultKey,
                                      final PublicKey inSpotMarketVaultKey,
                                      final PublicKey outTokenAccountKey,
                                      final PublicKey inTokenAccountKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey driftSignerKey,
                                      // Instructions Sysvar for instruction introspection
                                      final PublicKey instructionsKey,
                                      final int inMarketIndex,
                                      final int outMarketIndex,
                                      final long amountIn) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(outSpotMarketVaultKey),
      createWrite(inSpotMarketVaultKey),
      createWrite(outTokenAccountKey),
      createWrite(inTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(driftSignerKey),
      createRead(instructionsKey)
    );

    final byte[] _data = new byte[20];
    int i = writeDiscriminator(BEGIN_SWAP_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, inMarketIndex);
    i += 2;
    putInt16LE(_data, i, outMarketIndex);
    i += 2;
    putInt64LE(_data, i, amountIn);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator END_SWAP_DISCRIMINATOR = toDiscriminator(177, 184, 27, 193, 34, 13, 210, 145);

  public static Instruction endSwap(final AccountMeta invokedDriftProgramMeta,
                                    final PublicKey stateKey,
                                    final PublicKey userKey,
                                    final PublicKey userStatsKey,
                                    final PublicKey authorityKey,
                                    final PublicKey outSpotMarketVaultKey,
                                    final PublicKey inSpotMarketVaultKey,
                                    final PublicKey outTokenAccountKey,
                                    final PublicKey inTokenAccountKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey driftSignerKey,
                                    // Instructions Sysvar for instruction introspection
                                    final PublicKey instructionsKey,
                                    final int inMarketIndex,
                                    final int outMarketIndex,
                                    final OptionalLong limitPrice,
                                    final SwapReduceOnly reduceOnly) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(outSpotMarketVaultKey),
      createWrite(inSpotMarketVaultKey),
      createWrite(outTokenAccountKey),
      createWrite(inTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(driftSignerKey),
      createRead(instructionsKey)
    );

    final byte[] _data = new byte[22 + (reduceOnly == null ? 0 : Borsh.len(reduceOnly))];
    int i = writeDiscriminator(END_SWAP_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, inMarketIndex);
    i += 2;
    putInt16LE(_data, i, outMarketIndex);
    i += 2;
    i += Borsh.writeOptional(limitPrice, _data, i);
    Borsh.writeOptional(reduceOnly, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator ADD_PERP_LP_SHARES_DISCRIMINATOR = toDiscriminator(56, 209, 56, 197, 119, 254, 188, 117);

  public static Instruction addPerpLpShares(final AccountMeta invokedDriftProgramMeta,
                                            final PublicKey stateKey,
                                            final PublicKey userKey,
                                            final PublicKey authorityKey,
                                            final long nShares,
                                            final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(ADD_PERP_LP_SHARES_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, nShares);
    i += 8;
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REMOVE_PERP_LP_SHARES_DISCRIMINATOR = toDiscriminator(213, 89, 217, 18, 160, 55, 53, 141);

  public static Instruction removePerpLpShares(final AccountMeta invokedDriftProgramMeta,
                                               final PublicKey stateKey,
                                               final PublicKey userKey,
                                               final PublicKey authorityKey,
                                               final long sharesToBurn,
                                               final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(REMOVE_PERP_LP_SHARES_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, sharesToBurn);
    i += 8;
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REMOVE_PERP_LP_SHARES_IN_EXPIRING_MARKET_DISCRIMINATOR = toDiscriminator(83, 254, 253, 137, 59, 122, 68, 156);

  public static Instruction removePerpLpSharesInExpiringMarket(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey stateKey,
                                                               final PublicKey userKey,
                                                               final long sharesToBurn,
                                                               final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(REMOVE_PERP_LP_SHARES_IN_EXPIRING_MARKET_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, sharesToBurn);
    i += 8;
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_NAME_DISCRIMINATOR = toDiscriminator(135, 25, 185, 56, 165, 53, 34, 136);

  public static Instruction updateUserName(final AccountMeta invokedDriftProgramMeta,
                                           final PublicKey userKey,
                                           final PublicKey authorityKey,
                                           final int subAccountId,
                                           final int[] name) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[10 + Borsh.fixedLen(name)];
    int i = writeDiscriminator(UPDATE_USER_NAME_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_CUSTOM_MARGIN_RATIO_DISCRIMINATOR = toDiscriminator(21, 221, 140, 187, 32, 129, 11, 123);

  public static Instruction updateUserCustomMarginRatio(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey userKey,
                                                        final PublicKey authorityKey,
                                                        final int subAccountId,
                                                        final int marginRatio) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[14];
    int i = writeDiscriminator(UPDATE_USER_CUSTOM_MARGIN_RATIO_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    putInt32LE(_data, i, marginRatio);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_MARGIN_TRADING_ENABLED_DISCRIMINATOR = toDiscriminator(194, 92, 204, 223, 246, 188, 31, 203);

  public static Instruction updateUserMarginTradingEnabled(final AccountMeta invokedDriftProgramMeta,
                                                           final PublicKey userKey,
                                                           final PublicKey authorityKey,
                                                           final int subAccountId,
                                                           final boolean marginTradingEnabled) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[11];
    int i = writeDiscriminator(UPDATE_USER_MARGIN_TRADING_ENABLED_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) (marginTradingEnabled ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_DELEGATE_DISCRIMINATOR = toDiscriminator(139, 205, 141, 141, 113, 36, 94, 187);

  public static Instruction updateUserDelegate(final AccountMeta invokedDriftProgramMeta,
                                               final PublicKey userKey,
                                               final PublicKey authorityKey,
                                               final int subAccountId,
                                               final PublicKey delegate) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[42];
    int i = writeDiscriminator(UPDATE_USER_DELEGATE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    delegate.write(_data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_REDUCE_ONLY_DISCRIMINATOR = toDiscriminator(199, 71, 42, 67, 144, 19, 86, 109);

  public static Instruction updateUserReduceOnly(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey userKey,
                                                 final PublicKey authorityKey,
                                                 final int subAccountId,
                                                 final boolean reduceOnly) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[11];
    int i = writeDiscriminator(UPDATE_USER_REDUCE_ONLY_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) (reduceOnly ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_ADVANCED_LP_DISCRIMINATOR = toDiscriminator(66, 80, 107, 186, 27, 242, 66, 95);

  public static Instruction updateUserAdvancedLp(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey userKey,
                                                 final PublicKey authorityKey,
                                                 final int subAccountId,
                                                 final boolean advancedLp) {
    final var keys = List.of(
      createWrite(userKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[11];
    int i = writeDiscriminator(UPDATE_USER_ADVANCED_LP_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) (advancedLp ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DELETE_USER_DISCRIMINATOR = toDiscriminator(186, 85, 17, 249, 219, 231, 98, 251);

  public static Instruction deleteUser(final AccountMeta invokedDriftProgramMeta,
                                       final PublicKey userKey,
                                       final PublicKey userStatsKey,
                                       final PublicKey stateKey,
                                       final PublicKey authorityKey) {
    final var keys = List.of(
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(stateKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, DELETE_USER_DISCRIMINATOR);
  }

  public static final Discriminator RECLAIM_RENT_DISCRIMINATOR = toDiscriminator(218, 200, 19, 197, 227, 89, 192, 22);

  public static Instruction reclaimRent(final AccountMeta invokedDriftProgramMeta,
                                        final PublicKey userKey,
                                        final PublicKey userStatsKey,
                                        final PublicKey stateKey,
                                        final PublicKey authorityKey,
                                        final PublicKey rentKey) {
    final var keys = List.of(
      createWrite(userKey),
      createWrite(userStatsKey),
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createRead(rentKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, RECLAIM_RENT_DISCRIMINATOR);
  }

  public static final Discriminator FILL_PERP_ORDER_DISCRIMINATOR = toDiscriminator(13, 188, 248, 103, 134, 217, 106, 240);

  public static Instruction fillPerpOrder(final AccountMeta invokedDriftProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey fillerKey,
                                          final PublicKey fillerStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey userStatsKey,
                                          final OptionalInt orderId,
                                          final OptionalInt makerOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(fillerStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(FILL_PERP_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(orderId, _data, i);
    Borsh.writeOptional(makerOrderId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REVERT_FILL_DISCRIMINATOR = toDiscriminator(236, 238, 176, 69, 239, 10, 181, 193);

  public static Instruction revertFill(final AccountMeta invokedDriftProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey authorityKey,
                                       final PublicKey fillerKey,
                                       final PublicKey fillerStatsKey) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(fillerStatsKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, REVERT_FILL_DISCRIMINATOR);
  }

  public static final Discriminator FILL_SPOT_ORDER_DISCRIMINATOR = toDiscriminator(212, 206, 130, 173, 21, 34, 199, 40);

  public static Instruction fillSpotOrder(final AccountMeta invokedDriftProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey fillerKey,
                                          final PublicKey fillerStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey userStatsKey,
                                          final OptionalInt orderId,
                                          final SpotFulfillmentType fulfillmentType,
                                          final OptionalInt makerOrderId) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(fillerStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[19 + (fulfillmentType == null ? 0 : Borsh.len(fulfillmentType))];
    int i = writeDiscriminator(FILL_SPOT_ORDER_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(orderId, _data, i);
    i += Borsh.writeOptional(fulfillmentType, _data, i);
    Borsh.writeOptional(makerOrderId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator TRIGGER_ORDER_DISCRIMINATOR = toDiscriminator(63, 112, 51, 233, 232, 47, 240, 199);

  public static Instruction triggerOrder(final AccountMeta invokedDriftProgramMeta,
                                         final PublicKey stateKey,
                                         final PublicKey authorityKey,
                                         final PublicKey fillerKey,
                                         final PublicKey userKey,
                                         final int orderId) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(TRIGGER_ORDER_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, orderId);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator FORCE_CANCEL_ORDERS_DISCRIMINATOR = toDiscriminator(64, 181, 196, 63, 222, 72, 64, 232);

  public static Instruction forceCancelOrders(final AccountMeta invokedDriftProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey authorityKey,
                                              final PublicKey fillerKey,
                                              final PublicKey userKey) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(userKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, FORCE_CANCEL_ORDERS_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_USER_IDLE_DISCRIMINATOR = toDiscriminator(253, 133, 67, 22, 103, 161, 20, 100);

  public static Instruction updateUserIdle(final AccountMeta invokedDriftProgramMeta,
                                           final PublicKey stateKey,
                                           final PublicKey authorityKey,
                                           final PublicKey fillerKey,
                                           final PublicKey userKey) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(userKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_USER_IDLE_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_USER_OPEN_ORDERS_COUNT_DISCRIMINATOR = toDiscriminator(104, 39, 65, 210, 250, 163, 100, 134);

  public static Instruction updateUserOpenOrdersCount(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey stateKey,
                                                      final PublicKey authorityKey,
                                                      final PublicKey fillerKey,
                                                      final PublicKey userKey) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(fillerKey),
      createWrite(userKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_USER_OPEN_ORDERS_COUNT_DISCRIMINATOR);
  }

  public static final Discriminator ADMIN_DISABLE_UPDATE_PERP_BID_ASK_TWAP_DISCRIMINATOR = toDiscriminator(17, 164, 82, 45, 183, 86, 191, 199);

  public static Instruction adminDisableUpdatePerpBidAskTwap(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey adminKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey userStatsKey,
                                                             final boolean disable) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(ADMIN_DISABLE_UPDATE_PERP_BID_ASK_TWAP_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (disable ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_PNL_DISCRIMINATOR = toDiscriminator(43, 61, 234, 45, 15, 95, 152, 153);

  public static Instruction settlePnl(final AccountMeta invokedDriftProgramMeta,
                                      final PublicKey stateKey,
                                      final PublicKey userKey,
                                      final PublicKey authorityKey,
                                      final PublicKey spotMarketVaultKey,
                                      final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey),
      createRead(spotMarketVaultKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(SETTLE_PNL_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_MULTIPLE_PNLS_DISCRIMINATOR = toDiscriminator(127, 66, 117, 57, 40, 50, 152, 127);

  public static Instruction settleMultiplePnls(final AccountMeta invokedDriftProgramMeta,
                                               final PublicKey stateKey,
                                               final PublicKey userKey,
                                               final PublicKey authorityKey,
                                               final PublicKey spotMarketVaultKey,
                                               final int[] marketIndexes,
                                               final SettlePnlMode mode) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey),
      createReadOnlySigner(authorityKey),
      createRead(spotMarketVaultKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(marketIndexes) + Borsh.len(mode)];
    int i = writeDiscriminator(SETTLE_MULTIPLE_PNLS_DISCRIMINATOR, _data, 0);
    i += Borsh.write(marketIndexes, _data, i);
    Borsh.write(mode, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_FUNDING_PAYMENT_DISCRIMINATOR = toDiscriminator(222, 90, 202, 94, 28, 45, 115, 183);

  public static Instruction settleFundingPayment(final AccountMeta invokedDriftProgramMeta, final PublicKey stateKey, final PublicKey userKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, SETTLE_FUNDING_PAYMENT_DISCRIMINATOR);
  }

  public static final Discriminator SETTLE_LP_DISCRIMINATOR = toDiscriminator(155, 231, 116, 113, 97, 229, 139, 141);

  public static Instruction settleLp(final AccountMeta invokedDriftProgramMeta,
                                     final PublicKey stateKey,
                                     final PublicKey userKey,
                                     final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(SETTLE_LP_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_EXPIRED_MARKET_DISCRIMINATOR = toDiscriminator(120, 89, 11, 25, 122, 77, 72, 193);

  public static Instruction settleExpiredMarket(final AccountMeta invokedDriftProgramMeta,
                                                final PublicKey stateKey,
                                                final PublicKey authorityKey,
                                                final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(SETTLE_EXPIRED_MARKET_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator LIQUIDATE_PERP_DISCRIMINATOR = toDiscriminator(75, 35, 119, 247, 191, 18, 139, 2);

  public static Instruction liquidatePerp(final AccountMeta invokedDriftProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey liquidatorKey,
                                          final PublicKey liquidatorStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey userStatsKey,
                                          final int marketIndex,
                                          final long liquidatorMaxBaseAssetAmount,
                                          final OptionalLong limitPrice) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[27];
    int i = writeDiscriminator(LIQUIDATE_PERP_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, liquidatorMaxBaseAssetAmount);
    i += 8;
    Borsh.writeOptional(limitPrice, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator LIQUIDATE_PERP_WITH_FILL_DISCRIMINATOR = toDiscriminator(95, 111, 124, 105, 86, 169, 187, 34);

  public static Instruction liquidatePerpWithFill(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey liquidatorKey,
                                                  final PublicKey liquidatorStatsKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(LIQUIDATE_PERP_WITH_FILL_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator LIQUIDATE_SPOT_DISCRIMINATOR = toDiscriminator(107, 0, 128, 41, 35, 229, 251, 18);

  public static Instruction liquidateSpot(final AccountMeta invokedDriftProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey authorityKey,
                                          final PublicKey liquidatorKey,
                                          final PublicKey liquidatorStatsKey,
                                          final PublicKey userKey,
                                          final PublicKey userStatsKey,
                                          final int assetMarketIndex,
                                          final int liabilityMarketIndex,
                                          final BigInteger liquidatorMaxLiabilityTransfer,
                                          final OptionalLong limitPrice) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[37];
    int i = writeDiscriminator(LIQUIDATE_SPOT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, assetMarketIndex);
    i += 2;
    putInt16LE(_data, i, liabilityMarketIndex);
    i += 2;
    putInt128LE(_data, i, liquidatorMaxLiabilityTransfer);
    i += 16;
    Borsh.writeOptional(limitPrice, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator LIQUIDATE_BORROW_FOR_PERP_PNL_DISCRIMINATOR = toDiscriminator(169, 17, 32, 90, 207, 148, 209, 27);

  public static Instruction liquidateBorrowForPerpPnl(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey stateKey,
                                                      final PublicKey authorityKey,
                                                      final PublicKey liquidatorKey,
                                                      final PublicKey liquidatorStatsKey,
                                                      final PublicKey userKey,
                                                      final PublicKey userStatsKey,
                                                      final int perpMarketIndex,
                                                      final int spotMarketIndex,
                                                      final BigInteger liquidatorMaxLiabilityTransfer,
                                                      final OptionalLong limitPrice) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[37];
    int i = writeDiscriminator(LIQUIDATE_BORROW_FOR_PERP_PNL_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt128LE(_data, i, liquidatorMaxLiabilityTransfer);
    i += 16;
    Borsh.writeOptional(limitPrice, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator LIQUIDATE_PERP_PNL_FOR_DEPOSIT_DISCRIMINATOR = toDiscriminator(237, 75, 198, 235, 233, 186, 75, 35);

  public static Instruction liquidatePerpPnlForDeposit(final AccountMeta invokedDriftProgramMeta,
                                                       final PublicKey stateKey,
                                                       final PublicKey authorityKey,
                                                       final PublicKey liquidatorKey,
                                                       final PublicKey liquidatorStatsKey,
                                                       final PublicKey userKey,
                                                       final PublicKey userStatsKey,
                                                       final int perpMarketIndex,
                                                       final int spotMarketIndex,
                                                       final BigInteger liquidatorMaxPnlTransfer,
                                                       final OptionalLong limitPrice) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[37];
    int i = writeDiscriminator(LIQUIDATE_PERP_PNL_FOR_DEPOSIT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt128LE(_data, i, liquidatorMaxPnlTransfer);
    i += 16;
    Borsh.writeOptional(limitPrice, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SET_USER_STATUS_TO_BEING_LIQUIDATED_DISCRIMINATOR = toDiscriminator(106, 133, 160, 206, 193, 171, 192, 194);

  public static Instruction setUserStatusToBeingLiquidated(final AccountMeta invokedDriftProgramMeta, final PublicKey stateKey, final PublicKey userKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(userKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, SET_USER_STATUS_TO_BEING_LIQUIDATED_DISCRIMINATOR);
  }

  public static final Discriminator RESOLVE_PERP_PNL_DEFICIT_DISCRIMINATOR = toDiscriminator(168, 204, 68, 150, 159, 126, 95, 148);

  public static Instruction resolvePerpPnlDeficit(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey spotMarketVaultKey,
                                                  final PublicKey insuranceFundVaultKey,
                                                  final PublicKey driftSignerKey,
                                                  final PublicKey tokenProgramKey,
                                                  final int spotMarketIndex,
                                                  final int perpMarketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(RESOLVE_PERP_PNL_DEFICIT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt16LE(_data, i, perpMarketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator RESOLVE_PERP_BANKRUPTCY_DISCRIMINATOR = toDiscriminator(224, 16, 176, 214, 162, 213, 183, 222);

  public static Instruction resolvePerpBankruptcy(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey liquidatorKey,
                                                  final PublicKey liquidatorStatsKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey spotMarketVaultKey,
                                                  final PublicKey insuranceFundVaultKey,
                                                  final PublicKey driftSignerKey,
                                                  final PublicKey tokenProgramKey,
                                                  final int quoteSpotMarketIndex,
                                                  final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(RESOLVE_PERP_BANKRUPTCY_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, quoteSpotMarketIndex);
    i += 2;
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator RESOLVE_SPOT_BANKRUPTCY_DISCRIMINATOR = toDiscriminator(124, 194, 240, 254, 198, 213, 52, 122);

  public static Instruction resolveSpotBankruptcy(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey liquidatorKey,
                                                  final PublicKey liquidatorStatsKey,
                                                  final PublicKey userKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey spotMarketVaultKey,
                                                  final PublicKey insuranceFundVaultKey,
                                                  final PublicKey driftSignerKey,
                                                  final PublicKey tokenProgramKey,
                                                  final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWrite(liquidatorKey),
      createWrite(liquidatorStatsKey),
      createWrite(userKey),
      createWrite(userStatsKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(RESOLVE_SPOT_BANKRUPTCY_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_REVENUE_TO_INSURANCE_FUND_DISCRIMINATOR = toDiscriminator(200, 120, 93, 136, 69, 38, 199, 159);

  public static Instruction settleRevenueToInsuranceFund(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey stateKey,
                                                         final PublicKey spotMarketKey,
                                                         final PublicKey spotMarketVaultKey,
                                                         final PublicKey driftSignerKey,
                                                         final PublicKey insuranceFundVaultKey,
                                                         final PublicKey tokenProgramKey,
                                                         final int spotMarketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(spotMarketVaultKey),
      createRead(driftSignerKey),
      createWrite(insuranceFundVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(SETTLE_REVENUE_TO_INSURANCE_FUND_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, spotMarketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_FUNDING_RATE_DISCRIMINATOR = toDiscriminator(201, 178, 116, 212, 166, 144, 72, 238);

  public static Instruction updateFundingRate(final AccountMeta invokedDriftProgramMeta,
                                              final PublicKey stateKey,
                                              final PublicKey perpMarketKey,
                                              final PublicKey oracleKey,
                                              final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_FUNDING_RATE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PRELAUNCH_ORACLE_DISCRIMINATOR = toDiscriminator(220, 132, 27, 27, 233, 220, 61, 219);

  public static Instruction updatePrelaunchOracle(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey perpMarketKey,
                                                  final PublicKey oracleKey) {
    final var keys = List.of(
      createRead(stateKey),
      createRead(perpMarketKey),
      createWrite(oracleKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_PRELAUNCH_ORACLE_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_PERP_BID_ASK_TWAP_DISCRIMINATOR = toDiscriminator(247, 23, 255, 65, 212, 90, 221, 194);

  public static Instruction updatePerpBidAskTwap(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey stateKey,
                                                 final PublicKey perpMarketKey,
                                                 final PublicKey oracleKey,
                                                 final PublicKey keeperStatsKey,
                                                 final PublicKey authorityKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createRead(keeperStatsKey),
      createReadOnlySigner(authorityKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_PERP_BID_ASK_TWAP_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_CUMULATIVE_INTEREST_DISCRIMINATOR = toDiscriminator(39, 166, 139, 243, 158, 165, 155, 225);

  public static Instruction updateSpotMarketCumulativeInterest(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey stateKey,
                                                               final PublicKey spotMarketKey,
                                                               final PublicKey oracleKey,
                                                               final PublicKey spotMarketVaultKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createRead(oracleKey),
      createRead(spotMarketVaultKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_SPOT_MARKET_CUMULATIVE_INTEREST_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_AMMS_DISCRIMINATOR = toDiscriminator(201, 106, 217, 253, 4, 175, 228, 97);

  public static Instruction updateAmms(final AccountMeta invokedDriftProgramMeta,
                                       final PublicKey stateKey,
                                       final PublicKey authorityKey,
                                       final int[] marketIndexes) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.fixedLen(marketIndexes)];
    int i = writeDiscriminator(UPDATE_AMMS_DISCRIMINATOR, _data, 0);
    Borsh.fixedWrite(marketIndexes, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_EXPIRY_DISCRIMINATOR = toDiscriminator(208, 11, 211, 159, 226, 24, 11, 247);

  public static Instruction updateSpotMarketExpiry(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey spotMarketKey,
                                                   final long expiryTs) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_EXPIRY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, expiryTs);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_USER_QUOTE_ASSET_INSURANCE_STAKE_DISCRIMINATOR = toDiscriminator(251, 101, 156, 7, 2, 63, 30, 23);

  public static Instruction updateUserQuoteAssetInsuranceStake(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey stateKey,
                                                               final PublicKey spotMarketKey,
                                                               final PublicKey insuranceFundStakeKey,
                                                               final PublicKey userStatsKey,
                                                               final PublicKey signerKey,
                                                               final PublicKey insuranceFundVaultKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(signerKey),
      createWrite(insuranceFundVaultKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_USER_QUOTE_ASSET_INSURANCE_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_USER_GOV_TOKEN_INSURANCE_STAKE_DISCRIMINATOR = toDiscriminator(143, 99, 235, 187, 20, 159, 184, 84);

  public static Instruction updateUserGovTokenInsuranceStake(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey stateKey,
                                                             final PublicKey spotMarketKey,
                                                             final PublicKey insuranceFundStakeKey,
                                                             final PublicKey userStatsKey,
                                                             final PublicKey signerKey,
                                                             final PublicKey insuranceFundVaultKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(signerKey),
      createWrite(insuranceFundVaultKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_USER_GOV_TOKEN_INSURANCE_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(187, 179, 243, 70, 248, 90, 92, 147);

  public static Instruction initializeInsuranceFundStake(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey spotMarketKey,
                                                         final PublicKey insuranceFundStakeKey,
                                                         final PublicKey userStatsKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey authorityKey,
                                                         final PublicKey payerKey,
                                                         final PublicKey rentKey,
                                                         final PublicKey systemProgramKey,
                                                         final int marketIndex) {
    final var keys = List.of(
      createRead(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createRead(stateKey),
      createReadOnlySigner(authorityKey),
      createWritableSigner(payerKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(INITIALIZE_INSURANCE_FUND_STAKE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator ADD_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(251, 144, 115, 11, 222, 47, 62, 236);

  public static Instruction addInsuranceFundStake(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey stateKey,
                                                  final PublicKey spotMarketKey,
                                                  final PublicKey insuranceFundStakeKey,
                                                  final PublicKey userStatsKey,
                                                  final PublicKey authorityKey,
                                                  final PublicKey spotMarketVaultKey,
                                                  final PublicKey insuranceFundVaultKey,
                                                  final PublicKey driftSignerKey,
                                                  final PublicKey userTokenAccountKey,
                                                  final PublicKey tokenProgramKey,
                                                  final int marketIndex,
                                                  final long amount) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(ADD_INSURANCE_FUND_STAKE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(142, 70, 204, 92, 73, 106, 180, 52);

  public static Instruction requestRemoveInsuranceFundStake(final AccountMeta invokedDriftProgramMeta,
                                                            final PublicKey spotMarketKey,
                                                            final PublicKey insuranceFundStakeKey,
                                                            final PublicKey userStatsKey,
                                                            final PublicKey authorityKey,
                                                            final PublicKey insuranceFundVaultKey,
                                                            final int marketIndex,
                                                            final long amount) {
    final var keys = List.of(
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(insuranceFundVaultKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator CANCEL_REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(97, 235, 78, 62, 212, 42, 241, 127);

  public static Instruction cancelRequestRemoveInsuranceFundStake(final AccountMeta invokedDriftProgramMeta,
                                                                  final PublicKey spotMarketKey,
                                                                  final PublicKey insuranceFundStakeKey,
                                                                  final PublicKey userStatsKey,
                                                                  final PublicKey authorityKey,
                                                                  final PublicKey insuranceFundVaultKey,
                                                                  final int marketIndex) {
    final var keys = List.of(
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(insuranceFundVaultKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(CANCEL_REQUEST_REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR = toDiscriminator(128, 166, 142, 9, 254, 187, 143, 174);

  public static Instruction removeInsuranceFundStake(final AccountMeta invokedDriftProgramMeta,
                                                     final PublicKey stateKey,
                                                     final PublicKey spotMarketKey,
                                                     final PublicKey insuranceFundStakeKey,
                                                     final PublicKey userStatsKey,
                                                     final PublicKey authorityKey,
                                                     final PublicKey insuranceFundVaultKey,
                                                     final PublicKey driftSignerKey,
                                                     final PublicKey userTokenAccountKey,
                                                     final PublicKey tokenProgramKey,
                                                     final int marketIndex) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(REMOVE_INSURANCE_FUND_STAKE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator TRANSFER_PROTOCOL_IF_SHARES_DISCRIMINATOR = toDiscriminator(94, 93, 226, 240, 195, 201, 184, 109);

  public static Instruction transferProtocolIfShares(final AccountMeta invokedDriftProgramMeta,
                                                     final PublicKey signerKey,
                                                     final PublicKey transferConfigKey,
                                                     final PublicKey stateKey,
                                                     final PublicKey spotMarketKey,
                                                     final PublicKey insuranceFundStakeKey,
                                                     final PublicKey userStatsKey,
                                                     final PublicKey authorityKey,
                                                     final PublicKey insuranceFundVaultKey,
                                                     final int marketIndex,
                                                     final BigInteger shares) {
    final var keys = List.of(
      createReadOnlySigner(signerKey),
      createWrite(transferConfigKey),
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWrite(insuranceFundStakeKey),
      createWrite(userStatsKey),
      createReadOnlySigner(authorityKey),
      createRead(insuranceFundVaultKey)
    );

    final byte[] _data = new byte[26];
    int i = writeDiscriminator(TRANSFER_PROTOCOL_IF_SHARES_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, shares);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PYTH_PULL_ORACLE_DISCRIMINATOR = toDiscriminator(230, 191, 189, 94, 108, 59, 74, 197);

  public static Instruction updatePythPullOracle(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey keeperKey,
                                                 final PublicKey pythSolanaReceiverKey,
                                                 final PublicKey encodedVaaKey,
                                                 final PublicKey priceFeedKey,
                                                 final int[] feedId,
                                                 final byte[] params) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createRead(pythSolanaReceiverKey),
      createRead(encodedVaaKey),
      createWrite(priceFeedKey)
    );

    final byte[] _data = new byte[12 + Borsh.fixedLen(feedId) + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_PYTH_PULL_ORACLE_DISCRIMINATOR, _data, 0);
    i += Borsh.fixedWrite(feedId, _data, i);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator POST_PYTH_PULL_ORACLE_UPDATE_ATOMIC_DISCRIMINATOR = toDiscriminator(116, 122, 137, 158, 224, 195, 173, 119);

  public static Instruction postPythPullOracleUpdateAtomic(final AccountMeta invokedDriftProgramMeta,
                                                           final PublicKey keeperKey,
                                                           final PublicKey pythSolanaReceiverKey,
                                                           final PublicKey guardianSetKey,
                                                           final PublicKey priceFeedKey,
                                                           final int[] feedId,
                                                           final byte[] params) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createRead(pythSolanaReceiverKey),
      createRead(guardianSetKey),
      createWrite(priceFeedKey)
    );

    final byte[] _data = new byte[12 + Borsh.fixedLen(feedId) + Borsh.len(params)];
    int i = writeDiscriminator(POST_PYTH_PULL_ORACLE_UPDATE_ATOMIC_DISCRIMINATOR, _data, 0);
    i += Borsh.fixedWrite(feedId, _data, i);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator POST_MULTI_PYTH_PULL_ORACLE_UPDATES_ATOMIC_DISCRIMINATOR = toDiscriminator(243, 79, 204, 228, 227, 208, 100, 244);

  public static Instruction postMultiPythPullOracleUpdatesAtomic(final AccountMeta invokedDriftProgramMeta,
                                                                 final PublicKey keeperKey,
                                                                 final PublicKey pythSolanaReceiverKey,
                                                                 final PublicKey guardianSetKey,
                                                                 final byte[] params) {
    final var keys = List.of(
      createWritableSigner(keeperKey),
      createRead(pythSolanaReceiverKey),
      createRead(guardianSetKey)
    );

    final byte[] _data = new byte[12 + Borsh.len(params)];
    int i = writeDiscriminator(POST_MULTI_PYTH_PULL_ORACLE_UPDATES_ATOMIC_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  public static Instruction initialize(final AccountMeta invokedDriftProgramMeta,
                                       final PublicKey adminKey,
                                       final PublicKey stateKey,
                                       final PublicKey quoteAssetMintKey,
                                       final PublicKey driftSignerKey,
                                       final PublicKey rentKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey tokenProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(stateKey),
      createRead(quoteAssetMintKey),
      createRead(driftSignerKey),
      createRead(rentKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_SPOT_MARKET_DISCRIMINATOR = toDiscriminator(234, 196, 128, 44, 94, 15, 48, 201);

  public static Instruction initializeSpotMarket(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey spotMarketKey,
                                                 final PublicKey spotMarketMintKey,
                                                 final PublicKey spotMarketVaultKey,
                                                 final PublicKey insuranceFundVaultKey,
                                                 final PublicKey driftSignerKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey oracleKey,
                                                 final PublicKey adminKey,
                                                 final PublicKey rentKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final int optimalUtilization,
                                                 final int optimalBorrowRate,
                                                 final int maxBorrowRate,
                                                 final OracleSource oracleSource,
                                                 final int initialAssetWeight,
                                                 final int maintenanceAssetWeight,
                                                 final int initialLiabilityWeight,
                                                 final int maintenanceLiabilityWeight,
                                                 final int imfFactor,
                                                 final int liquidatorFee,
                                                 final int ifLiquidationFee,
                                                 final boolean activeStatus,
                                                 final AssetTier assetTier,
                                                 final long scaleInitialAssetWeightStart,
                                                 final long withdrawGuardThreshold,
                                                 final long orderTickSize,
                                                 final long orderStepSize,
                                                 final int ifTotalFactor,
                                                 final int[] name) {
    final var keys = List.of(
      createWrite(spotMarketKey),
      createRead(spotMarketMintKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createWrite(stateKey),
      createRead(oracleKey),
      createWritableSigner(adminKey),
      createRead(rentKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[85 + Borsh.len(oracleSource) + Borsh.len(assetTier) + Borsh.fixedLen(name)];
    int i = writeDiscriminator(INITIALIZE_SPOT_MARKET_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, optimalUtilization);
    i += 4;
    putInt32LE(_data, i, optimalBorrowRate);
    i += 4;
    putInt32LE(_data, i, maxBorrowRate);
    i += 4;
    i += Borsh.write(oracleSource, _data, i);
    putInt32LE(_data, i, initialAssetWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceAssetWeight);
    i += 4;
    putInt32LE(_data, i, initialLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, imfFactor);
    i += 4;
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);
    i += 4;
    _data[i] = (byte) (activeStatus ? 1 : 0);
    ++i;
    i += Borsh.write(assetTier, _data, i);
    putInt64LE(_data, i, scaleInitialAssetWeightStart);
    i += 8;
    putInt64LE(_data, i, withdrawGuardThreshold);
    i += 8;
    putInt64LE(_data, i, orderTickSize);
    i += 8;
    putInt64LE(_data, i, orderStepSize);
    i += 8;
    putInt32LE(_data, i, ifTotalFactor);
    i += 4;
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DELETE_INITIALIZED_SPOT_MARKET_DISCRIMINATOR = toDiscriminator(31, 140, 67, 191, 189, 20, 101, 221);

  public static Instruction deleteInitializedSpotMarket(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey adminKey,
                                                        final PublicKey stateKey,
                                                        final PublicKey spotMarketKey,
                                                        final PublicKey spotMarketVaultKey,
                                                        final PublicKey insuranceFundVaultKey,
                                                        final PublicKey driftSignerKey,
                                                        final PublicKey tokenProgramKey,
                                                        final int marketIndex) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(stateKey),
      createWrite(spotMarketKey),
      createWrite(spotMarketVaultKey),
      createWrite(insuranceFundVaultKey),
      createRead(driftSignerKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(DELETE_INITIALIZED_SPOT_MARKET_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_SERUM_FULFILLMENT_CONFIG_DISCRIMINATOR = toDiscriminator(193, 211, 132, 172, 70, 171, 7, 94);

  public static Instruction initializeSerumFulfillmentConfig(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey baseSpotMarketKey,
                                                             final PublicKey quoteSpotMarketKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey serumProgramKey,
                                                             final PublicKey serumMarketKey,
                                                             final PublicKey serumOpenOrdersKey,
                                                             final PublicKey driftSignerKey,
                                                             final PublicKey serumFulfillmentConfigKey,
                                                             final PublicKey adminKey,
                                                             final PublicKey rentKey,
                                                             final PublicKey systemProgramKey,
                                                             final int marketIndex) {
    final var keys = List.of(
      createRead(baseSpotMarketKey),
      createRead(quoteSpotMarketKey),
      createWrite(stateKey),
      createRead(serumProgramKey),
      createRead(serumMarketKey),
      createWrite(serumOpenOrdersKey),
      createRead(driftSignerKey),
      createWrite(serumFulfillmentConfigKey),
      createWritableSigner(adminKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(INITIALIZE_SERUM_FULFILLMENT_CONFIG_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SERUM_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR = toDiscriminator(171, 109, 240, 251, 95, 1, 149, 89);

  public static Instruction updateSerumFulfillmentConfigStatus(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey stateKey,
                                                               final PublicKey serumFulfillmentConfigKey,
                                                               final PublicKey adminKey,
                                                               final SpotFulfillmentConfigStatus status) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(serumFulfillmentConfigKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(status)];
    int i = writeDiscriminator(UPDATE_SERUM_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR, _data, 0);
    Borsh.write(status, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_OPENBOOK_V2_FULFILLMENT_CONFIG_DISCRIMINATOR = toDiscriminator(7, 221, 103, 153, 107, 57, 27, 197);

  public static Instruction initializeOpenbookV2FulfillmentConfig(final AccountMeta invokedDriftProgramMeta,
                                                                  final PublicKey baseSpotMarketKey,
                                                                  final PublicKey quoteSpotMarketKey,
                                                                  final PublicKey stateKey,
                                                                  final PublicKey openbookV2ProgramKey,
                                                                  final PublicKey openbookV2MarketKey,
                                                                  final PublicKey driftSignerKey,
                                                                  final PublicKey openbookV2FulfillmentConfigKey,
                                                                  final PublicKey adminKey,
                                                                  final PublicKey rentKey,
                                                                  final PublicKey systemProgramKey,
                                                                  final int marketIndex) {
    final var keys = List.of(
      createRead(baseSpotMarketKey),
      createRead(quoteSpotMarketKey),
      createWrite(stateKey),
      createRead(openbookV2ProgramKey),
      createRead(openbookV2MarketKey),
      createRead(driftSignerKey),
      createWrite(openbookV2FulfillmentConfigKey),
      createWritableSigner(adminKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(INITIALIZE_OPENBOOK_V2_FULFILLMENT_CONFIG_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator OPENBOOK_V2_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR = toDiscriminator(25, 173, 19, 189, 4, 211, 64, 238);

  public static Instruction openbookV2FulfillmentConfigStatus(final AccountMeta invokedDriftProgramMeta,
                                                              final PublicKey stateKey,
                                                              final PublicKey openbookV2FulfillmentConfigKey,
                                                              final PublicKey adminKey,
                                                              final SpotFulfillmentConfigStatus status) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(openbookV2FulfillmentConfigKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(status)];
    int i = writeDiscriminator(OPENBOOK_V2_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR, _data, 0);
    Borsh.write(status, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_PHOENIX_FULFILLMENT_CONFIG_DISCRIMINATOR = toDiscriminator(135, 132, 110, 107, 185, 160, 169, 154);

  public static Instruction initializePhoenixFulfillmentConfig(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey baseSpotMarketKey,
                                                               final PublicKey quoteSpotMarketKey,
                                                               final PublicKey stateKey,
                                                               final PublicKey phoenixProgramKey,
                                                               final PublicKey phoenixMarketKey,
                                                               final PublicKey driftSignerKey,
                                                               final PublicKey phoenixFulfillmentConfigKey,
                                                               final PublicKey adminKey,
                                                               final PublicKey rentKey,
                                                               final PublicKey systemProgramKey,
                                                               final int marketIndex) {
    final var keys = List.of(
      createRead(baseSpotMarketKey),
      createRead(quoteSpotMarketKey),
      createWrite(stateKey),
      createRead(phoenixProgramKey),
      createRead(phoenixMarketKey),
      createRead(driftSignerKey),
      createWrite(phoenixFulfillmentConfigKey),
      createWritableSigner(adminKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(INITIALIZE_PHOENIX_FULFILLMENT_CONFIG_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator PHOENIX_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR = toDiscriminator(96, 31, 113, 32, 12, 203, 7, 154);

  public static Instruction phoenixFulfillmentConfigStatus(final AccountMeta invokedDriftProgramMeta,
                                                           final PublicKey stateKey,
                                                           final PublicKey phoenixFulfillmentConfigKey,
                                                           final PublicKey adminKey,
                                                           final SpotFulfillmentConfigStatus status) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(phoenixFulfillmentConfigKey),
      createWritableSigner(adminKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(status)];
    int i = writeDiscriminator(PHOENIX_FULFILLMENT_CONFIG_STATUS_DISCRIMINATOR, _data, 0);
    Borsh.write(status, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SERUM_VAULT_DISCRIMINATOR = toDiscriminator(219, 8, 246, 96, 169, 121, 91, 110);

  public static Instruction updateSerumVault(final AccountMeta invokedDriftProgramMeta,
                                             final PublicKey stateKey,
                                             final PublicKey adminKey,
                                             final PublicKey srmVaultKey) {
    final var keys = List.of(
      createWrite(stateKey),
      createWritableSigner(adminKey),
      createRead(srmVaultKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_SERUM_VAULT_DISCRIMINATOR);
  }

  public static final Discriminator INITIALIZE_PERP_MARKET_DISCRIMINATOR = toDiscriminator(132, 9, 229, 118, 117, 118, 117, 62);

  public static Instruction initializePerpMarket(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey perpMarketKey,
                                                 final PublicKey oracleKey,
                                                 final PublicKey rentKey,
                                                 final PublicKey systemProgramKey,
                                                 final int marketIndex,
                                                 final BigInteger ammBaseAssetReserve,
                                                 final BigInteger ammQuoteAssetReserve,
                                                 final long ammPeriodicity,
                                                 final BigInteger ammPegMultiplier,
                                                 final OracleSource oracleSource,
                                                 final ContractTier contractTier,
                                                 final int marginRatioInitial,
                                                 final int marginRatioMaintenance,
                                                 final int liquidatorFee,
                                                 final int ifLiquidationFee,
                                                 final int imfFactor,
                                                 final boolean activeStatus,
                                                 final int baseSpread,
                                                 final int maxSpread,
                                                 final BigInteger maxOpenInterest,
                                                 final long maxRevenueWithdrawPerPeriod,
                                                 final long quoteMaxInsurance,
                                                 final long orderStepSize,
                                                 final long orderTickSize,
                                                 final long minOrderSize,
                                                 final BigInteger concentrationCoefScale,
                                                 final int curveUpdateIntensity,
                                                 final int ammJitIntensity,
                                                 final int[] name) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[169 + Borsh.len(oracleSource) + Borsh.len(contractTier) + Borsh.fixedLen(name)];
    int i = writeDiscriminator(INITIALIZE_PERP_MARKET_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, ammBaseAssetReserve);
    i += 16;
    putInt128LE(_data, i, ammQuoteAssetReserve);
    i += 16;
    putInt64LE(_data, i, ammPeriodicity);
    i += 8;
    putInt128LE(_data, i, ammPegMultiplier);
    i += 16;
    i += Borsh.write(oracleSource, _data, i);
    i += Borsh.write(contractTier, _data, i);
    putInt32LE(_data, i, marginRatioInitial);
    i += 4;
    putInt32LE(_data, i, marginRatioMaintenance);
    i += 4;
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);
    i += 4;
    putInt32LE(_data, i, imfFactor);
    i += 4;
    _data[i] = (byte) (activeStatus ? 1 : 0);
    ++i;
    putInt32LE(_data, i, baseSpread);
    i += 4;
    putInt32LE(_data, i, maxSpread);
    i += 4;
    putInt128LE(_data, i, maxOpenInterest);
    i += 16;
    putInt64LE(_data, i, maxRevenueWithdrawPerPeriod);
    i += 8;
    putInt64LE(_data, i, quoteMaxInsurance);
    i += 8;
    putInt64LE(_data, i, orderStepSize);
    i += 8;
    putInt64LE(_data, i, orderTickSize);
    i += 8;
    putInt64LE(_data, i, minOrderSize);
    i += 8;
    putInt128LE(_data, i, concentrationCoefScale);
    i += 16;
    _data[i] = (byte) curveUpdateIntensity;
    ++i;
    _data[i] = (byte) ammJitIntensity;
    ++i;
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_PREDICTION_MARKET_DISCRIMINATOR = toDiscriminator(248, 70, 198, 224, 224, 105, 125, 195);

  public static Instruction initializePredictionMarket(final AccountMeta invokedDriftProgramMeta,
                                                       final PublicKey adminKey,
                                                       final PublicKey stateKey,
                                                       final PublicKey perpMarketKey) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, INITIALIZE_PREDICTION_MARKET_DISCRIMINATOR);
  }

  public static final Discriminator DELETE_INITIALIZED_PERP_MARKET_DISCRIMINATOR = toDiscriminator(91, 154, 24, 87, 106, 59, 190, 66);

  public static Instruction deleteInitializedPerpMarket(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey adminKey,
                                                        final PublicKey stateKey,
                                                        final PublicKey perpMarketKey,
                                                        final int marketIndex) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(DELETE_INITIALIZED_PERP_MARKET_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, marketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator MOVE_AMM_PRICE_DISCRIMINATOR = toDiscriminator(235, 109, 2, 82, 219, 118, 6, 159);

  public static Instruction moveAmmPrice(final AccountMeta invokedDriftProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey stateKey,
                                         final PublicKey perpMarketKey,
                                         final BigInteger baseAssetReserve,
                                         final BigInteger quoteAssetReserve,
                                         final BigInteger sqrtK) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[56];
    int i = writeDiscriminator(MOVE_AMM_PRICE_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, baseAssetReserve);
    i += 16;
    putInt128LE(_data, i, quoteAssetReserve);
    i += 16;
    putInt128LE(_data, i, sqrtK);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator RECENTER_PERP_MARKET_AMM_DISCRIMINATOR = toDiscriminator(24, 87, 10, 115, 165, 190, 80, 139);

  public static Instruction recenterPerpMarketAmm(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey adminKey,
                                                  final PublicKey stateKey,
                                                  final PublicKey perpMarketKey,
                                                  final BigInteger pegMultiplier,
                                                  final BigInteger sqrtK) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(RECENTER_PERP_MARKET_AMM_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, pegMultiplier);
    i += 16;
    putInt128LE(_data, i, sqrtK);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_AMM_SUMMARY_STATS_DISCRIMINATOR = toDiscriminator(122, 101, 249, 238, 209, 9, 241, 245);

  public static Instruction updatePerpMarketAmmSummaryStats(final AccountMeta invokedDriftProgramMeta,
                                                            final PublicKey adminKey,
                                                            final PublicKey stateKey,
                                                            final PublicKey perpMarketKey,
                                                            final PublicKey spotMarketKey,
                                                            final PublicKey oracleKey,
                                                            final UpdatePerpMarketSummaryStatsParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(spotMarketKey),
      createRead(oracleKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_AMM_SUMMARY_STATS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_EXPIRY_DISCRIMINATOR = toDiscriminator(44, 221, 227, 151, 131, 140, 22, 110);

  public static Instruction updatePerpMarketExpiry(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey perpMarketKey,
                                                   final long expiryTs) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_EXPIRY_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, expiryTs);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator SETTLE_EXPIRED_MARKET_POOLS_TO_REVENUE_POOL_DISCRIMINATOR = toDiscriminator(55, 19, 238, 169, 227, 90, 200, 184);

  public static Instruction settleExpiredMarketPoolsToRevenuePool(final AccountMeta invokedDriftProgramMeta,
                                                                  final PublicKey stateKey,
                                                                  final PublicKey adminKey,
                                                                  final PublicKey spotMarketKey,
                                                                  final PublicKey perpMarketKey) {
    final var keys = List.of(
      createRead(stateKey),
      createReadOnlySigner(adminKey),
      createWrite(spotMarketKey),
      createWrite(perpMarketKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, SETTLE_EXPIRED_MARKET_POOLS_TO_REVENUE_POOL_DISCRIMINATOR);
  }

  public static final Discriminator DEPOSIT_INTO_PERP_MARKET_FEE_POOL_DISCRIMINATOR = toDiscriminator(34, 58, 57, 68, 97, 80, 244, 6);

  public static Instruction depositIntoPerpMarketFeePool(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey stateKey,
                                                         final PublicKey perpMarketKey,
                                                         final PublicKey adminKey,
                                                         final PublicKey sourceVaultKey,
                                                         final PublicKey driftSignerKey,
                                                         final PublicKey quoteSpotMarketKey,
                                                         final PublicKey spotMarketVaultKey,
                                                         final PublicKey tokenProgramKey,
                                                         final long amount) {
    final var keys = List.of(
      createWrite(stateKey),
      createWrite(perpMarketKey),
      createReadOnlySigner(adminKey),
      createWrite(sourceVaultKey),
      createRead(driftSignerKey),
      createWrite(quoteSpotMarketKey),
      createWrite(spotMarketVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_INTO_PERP_MARKET_FEE_POOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DEPOSIT_INTO_SPOT_MARKET_VAULT_DISCRIMINATOR = toDiscriminator(48, 252, 119, 73, 255, 205, 174, 247);

  public static Instruction depositIntoSpotMarketVault(final AccountMeta invokedDriftProgramMeta,
                                                       final PublicKey stateKey,
                                                       final PublicKey spotMarketKey,
                                                       final PublicKey adminKey,
                                                       final PublicKey sourceVaultKey,
                                                       final PublicKey spotMarketVaultKey,
                                                       final PublicKey tokenProgramKey,
                                                       final long amount) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createReadOnlySigner(adminKey),
      createWrite(sourceVaultKey),
      createWrite(spotMarketVaultKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_INTO_SPOT_MARKET_VAULT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DEPOSIT_INTO_SPOT_MARKET_REVENUE_POOL_DISCRIMINATOR = toDiscriminator(92, 40, 151, 42, 122, 254, 139, 246);

  public static Instruction depositIntoSpotMarketRevenuePool(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey stateKey,
                                                             final PublicKey spotMarketKey,
                                                             final PublicKey authorityKey,
                                                             final PublicKey spotMarketVaultKey,
                                                             final PublicKey userTokenAccountKey,
                                                             final PublicKey tokenProgramKey,
                                                             final long amount) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(spotMarketKey),
      createWritableSigner(authorityKey),
      createWrite(spotMarketVaultKey),
      createWrite(userTokenAccountKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(DEPOSIT_INTO_SPOT_MARKET_REVENUE_POOL_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amount);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator REPEG_AMM_CURVE_DISCRIMINATOR = toDiscriminator(3, 36, 102, 89, 180, 128, 120, 213);

  public static Instruction repegAmmCurve(final AccountMeta invokedDriftProgramMeta,
                                          final PublicKey stateKey,
                                          final PublicKey perpMarketKey,
                                          final PublicKey oracleKey,
                                          final PublicKey adminKey,
                                          final BigInteger newPegCandidate) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createReadOnlySigner(adminKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(REPEG_AMM_CURVE_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, newPegCandidate);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_AMM_ORACLE_TWAP_DISCRIMINATOR = toDiscriminator(241, 74, 114, 123, 206, 153, 24, 202);

  public static Instruction updatePerpMarketAmmOracleTwap(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey stateKey,
                                                          final PublicKey perpMarketKey,
                                                          final PublicKey oracleKey,
                                                          final PublicKey adminKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, UPDATE_PERP_MARKET_AMM_ORACLE_TWAP_DISCRIMINATOR);
  }

  public static final Discriminator RESET_PERP_MARKET_AMM_ORACLE_TWAP_DISCRIMINATOR = toDiscriminator(127, 10, 55, 164, 123, 226, 47, 24);

  public static Instruction resetPerpMarketAmmOracleTwap(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey stateKey,
                                                         final PublicKey perpMarketKey,
                                                         final PublicKey oracleKey,
                                                         final PublicKey adminKey) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createReadOnlySigner(adminKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, RESET_PERP_MARKET_AMM_ORACLE_TWAP_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_K_DISCRIMINATOR = toDiscriminator(72, 98, 9, 139, 129, 229, 172, 56);

  public static Instruction updateK(final AccountMeta invokedDriftProgramMeta,
                                    final PublicKey adminKey,
                                    final PublicKey stateKey,
                                    final PublicKey perpMarketKey,
                                    final PublicKey oracleKey,
                                    final BigInteger sqrtK) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_K_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, sqrtK);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MARGIN_RATIO_DISCRIMINATOR = toDiscriminator(130, 173, 107, 45, 119, 105, 26, 113);

  public static Instruction updatePerpMarketMarginRatio(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey adminKey,
                                                        final PublicKey stateKey,
                                                        final PublicKey perpMarketKey,
                                                        final int marginRatioInitial,
                                                        final int marginRatioMaintenance) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MARGIN_RATIO_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, marginRatioInitial);
    i += 4;
    putInt32LE(_data, i, marginRatioMaintenance);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_FUNDING_PERIOD_DISCRIMINATOR = toDiscriminator(171, 161, 69, 91, 129, 139, 161, 28);

  public static Instruction updatePerpMarketFundingPeriod(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey perpMarketKey,
                                                          final long fundingPeriod) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_FUNDING_PERIOD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, fundingPeriod);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MAX_IMBALANCES_DISCRIMINATOR = toDiscriminator(15, 206, 73, 133, 60, 8, 86, 89);

  public static Instruction updatePerpMarketMaxImbalances(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey perpMarketKey,
                                                          final long unrealizedMaxImbalance,
                                                          final long maxRevenueWithdrawPerPeriod,
                                                          final long quoteMaxInsurance) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MAX_IMBALANCES_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, unrealizedMaxImbalance);
    i += 8;
    putInt64LE(_data, i, maxRevenueWithdrawPerPeriod);
    i += 8;
    putInt64LE(_data, i, quoteMaxInsurance);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_LIQUIDATION_FEE_DISCRIMINATOR = toDiscriminator(90, 137, 9, 145, 41, 8, 148, 117);

  public static Instruction updatePerpMarketLiquidationFee(final AccountMeta invokedDriftProgramMeta,
                                                           final PublicKey adminKey,
                                                           final PublicKey stateKey,
                                                           final PublicKey perpMarketKey,
                                                           final int liquidatorFee,
                                                           final int ifLiquidationFee) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_LIQUIDATION_FEE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_INSURANCE_FUND_UNSTAKING_PERIOD_DISCRIMINATOR = toDiscriminator(44, 69, 43, 226, 204, 223, 202, 52);

  public static Instruction updateInsuranceFundUnstakingPeriod(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey adminKey,
                                                               final PublicKey stateKey,
                                                               final PublicKey spotMarketKey,
                                                               final long insuranceFundUnstakingPeriod) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_INSURANCE_FUND_UNSTAKING_PERIOD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, insuranceFundUnstakingPeriod);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_LIQUIDATION_FEE_DISCRIMINATOR = toDiscriminator(11, 13, 255, 53, 56, 136, 104, 177);

  public static Instruction updateSpotMarketLiquidationFee(final AccountMeta invokedDriftProgramMeta,
                                                           final PublicKey adminKey,
                                                           final PublicKey stateKey,
                                                           final PublicKey spotMarketKey,
                                                           final int liquidatorFee,
                                                           final int ifLiquidationFee) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_LIQUIDATION_FEE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_WITHDRAW_GUARD_THRESHOLD_DISCRIMINATOR = toDiscriminator(56, 18, 39, 61, 155, 211, 44, 133);

  public static Instruction updateWithdrawGuardThreshold(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey adminKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey spotMarketKey,
                                                         final long withdrawGuardThreshold) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_WITHDRAW_GUARD_THRESHOLD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, withdrawGuardThreshold);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_IF_FACTOR_DISCRIMINATOR = toDiscriminator(147, 30, 224, 34, 18, 230, 105, 4);

  public static Instruction updateSpotMarketIfFactor(final AccountMeta invokedDriftProgramMeta,
                                                     final PublicKey adminKey,
                                                     final PublicKey stateKey,
                                                     final PublicKey spotMarketKey,
                                                     final int spotMarketIndex,
                                                     final int userIfFactor,
                                                     final int totalIfFactor) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_IF_FACTOR_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt32LE(_data, i, userIfFactor);
    i += 4;
    putInt32LE(_data, i, totalIfFactor);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_REVENUE_SETTLE_PERIOD_DISCRIMINATOR = toDiscriminator(81, 92, 126, 41, 250, 225, 156, 219);

  public static Instruction updateSpotMarketRevenueSettlePeriod(final AccountMeta invokedDriftProgramMeta,
                                                                final PublicKey adminKey,
                                                                final PublicKey stateKey,
                                                                final PublicKey spotMarketKey,
                                                                final long revenueSettlePeriod) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_REVENUE_SETTLE_PERIOD_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, revenueSettlePeriod);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_STATUS_DISCRIMINATOR = toDiscriminator(78, 94, 16, 188, 193, 110, 231, 31);

  public static Instruction updateSpotMarketStatus(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey spotMarketKey,
                                                   final MarketStatus status) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(status)];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_STATUS_DISCRIMINATOR, _data, 0);
    Borsh.write(status, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_PAUSED_OPERATIONS_DISCRIMINATOR = toDiscriminator(100, 61, 153, 81, 180, 12, 6, 248);

  public static Instruction updateSpotMarketPausedOperations(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey adminKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey spotMarketKey,
                                                             final int pausedOperations) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_PAUSED_OPERATIONS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) pausedOperations;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_ASSET_TIER_DISCRIMINATOR = toDiscriminator(253, 209, 231, 14, 242, 208, 243, 130);

  public static Instruction updateSpotMarketAssetTier(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final PublicKey spotMarketKey,
                                                      final AssetTier assetTier) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(assetTier)];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_ASSET_TIER_DISCRIMINATOR, _data, 0);
    Borsh.write(assetTier, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_MARGIN_WEIGHTS_DISCRIMINATOR = toDiscriminator(109, 33, 87, 195, 255, 36, 6, 81);

  public static Instruction updateSpotMarketMarginWeights(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey spotMarketKey,
                                                          final int initialAssetWeight,
                                                          final int maintenanceAssetWeight,
                                                          final int initialLiabilityWeight,
                                                          final int maintenanceLiabilityWeight,
                                                          final int imfFactor) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[28];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_MARGIN_WEIGHTS_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, initialAssetWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceAssetWeight);
    i += 4;
    putInt32LE(_data, i, initialLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, imfFactor);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_BORROW_RATE_DISCRIMINATOR = toDiscriminator(71, 239, 236, 153, 210, 62, 254, 76);

  public static Instruction updateSpotMarketBorrowRate(final AccountMeta invokedDriftProgramMeta,
                                                       final PublicKey adminKey,
                                                       final PublicKey stateKey,
                                                       final PublicKey spotMarketKey,
                                                       final int optimalUtilization,
                                                       final int optimalBorrowRate,
                                                       final int maxBorrowRate,
                                                       final OptionalInt minBorrowRate) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[22];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_BORROW_RATE_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, optimalUtilization);
    i += 4;
    putInt32LE(_data, i, optimalBorrowRate);
    i += 4;
    putInt32LE(_data, i, maxBorrowRate);
    i += 4;
    Borsh.writeOptional(minBorrowRate, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_MAX_TOKEN_DEPOSITS_DISCRIMINATOR = toDiscriminator(56, 191, 79, 18, 26, 121, 80, 208);

  public static Instruction updateSpotMarketMaxTokenDeposits(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey adminKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey spotMarketKey,
                                                             final long maxTokenDeposits) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_MAX_TOKEN_DEPOSITS_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, maxTokenDeposits);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_MAX_TOKEN_BORROWS_DISCRIMINATOR = toDiscriminator(57, 102, 204, 212, 253, 95, 13, 199);

  public static Instruction updateSpotMarketMaxTokenBorrows(final AccountMeta invokedDriftProgramMeta,
                                                            final PublicKey adminKey,
                                                            final PublicKey stateKey,
                                                            final PublicKey spotMarketKey,
                                                            final int maxTokenBorrowsFraction) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_MAX_TOKEN_BORROWS_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, maxTokenBorrowsFraction);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_SCALE_INITIAL_ASSET_WEIGHT_START_DISCRIMINATOR = toDiscriminator(217, 204, 204, 118, 204, 130, 225, 147);

  public static Instruction updateSpotMarketScaleInitialAssetWeightStart(final AccountMeta invokedDriftProgramMeta,
                                                                         final PublicKey adminKey,
                                                                         final PublicKey stateKey,
                                                                         final PublicKey spotMarketKey,
                                                                         final long scaleInitialAssetWeightStart) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_SCALE_INITIAL_ASSET_WEIGHT_START_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, scaleInitialAssetWeightStart);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_ORACLE_DISCRIMINATOR = toDiscriminator(114, 184, 102, 37, 246, 186, 180, 99);

  public static Instruction updateSpotMarketOracle(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey spotMarketKey,
                                                   final PublicKey oracleKey,
                                                   final PublicKey oracle,
                                                   final OracleSource oracleSource) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey),
      createRead(oracleKey)
    );

    final byte[] _data = new byte[40 + Borsh.len(oracleSource)];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_ORACLE_DISCRIMINATOR, _data, 0);
    oracle.write(_data, i);
    i += 32;
    Borsh.write(oracleSource, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_STEP_SIZE_AND_TICK_SIZE_DISCRIMINATOR = toDiscriminator(238, 153, 137, 80, 206, 59, 250, 61);

  public static Instruction updateSpotMarketStepSizeAndTickSize(final AccountMeta invokedDriftProgramMeta,
                                                                final PublicKey adminKey,
                                                                final PublicKey stateKey,
                                                                final PublicKey spotMarketKey,
                                                                final long stepSize,
                                                                final long tickSize) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_STEP_SIZE_AND_TICK_SIZE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, stepSize);
    i += 8;
    putInt64LE(_data, i, tickSize);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_MIN_ORDER_SIZE_DISCRIMINATOR = toDiscriminator(93, 128, 11, 119, 26, 20, 181, 50);

  public static Instruction updateSpotMarketMinOrderSize(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey adminKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey spotMarketKey,
                                                         final long orderSize) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_MIN_ORDER_SIZE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, orderSize);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_ORDERS_ENABLED_DISCRIMINATOR = toDiscriminator(190, 79, 206, 15, 26, 229, 229, 43);

  public static Instruction updateSpotMarketOrdersEnabled(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey spotMarketKey,
                                                          final boolean ordersEnabled) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_ORDERS_ENABLED_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) (ordersEnabled ? 1 : 0);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_IF_PAUSED_OPERATIONS_DISCRIMINATOR = toDiscriminator(101, 215, 79, 74, 59, 41, 79, 12);

  public static Instruction updateSpotMarketIfPausedOperations(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey adminKey,
                                                               final PublicKey stateKey,
                                                               final PublicKey spotMarketKey,
                                                               final int pausedOperations) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_IF_PAUSED_OPERATIONS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) pausedOperations;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_NAME_DISCRIMINATOR = toDiscriminator(17, 208, 1, 1, 162, 211, 188, 224);

  public static Instruction updateSpotMarketName(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey spotMarketKey,
                                                 final int[] name) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.fixedLen(name)];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_NAME_DISCRIMINATOR, _data, 0);
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_STATUS_DISCRIMINATOR = toDiscriminator(71, 201, 175, 122, 255, 207, 196, 207);

  public static Instruction updatePerpMarketStatus(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final PublicKey perpMarketKey,
                                                   final MarketStatus status) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(status)];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_STATUS_DISCRIMINATOR, _data, 0);
    Borsh.write(status, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_PAUSED_OPERATIONS_DISCRIMINATOR = toDiscriminator(53, 16, 136, 132, 30, 220, 121, 85);

  public static Instruction updatePerpMarketPausedOperations(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey adminKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey perpMarketKey,
                                                             final int pausedOperations) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_PAUSED_OPERATIONS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) pausedOperations;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_CONTRACT_TIER_DISCRIMINATOR = toDiscriminator(236, 128, 15, 95, 203, 214, 68, 117);

  public static Instruction updatePerpMarketContractTier(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey adminKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey perpMarketKey,
                                                         final ContractTier contractTier) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(contractTier)];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_CONTRACT_TIER_DISCRIMINATOR, _data, 0);
    Borsh.write(contractTier, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_IMF_FACTOR_DISCRIMINATOR = toDiscriminator(207, 194, 56, 132, 35, 67, 71, 244);

  public static Instruction updatePerpMarketImfFactor(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final PublicKey perpMarketKey,
                                                      final int imfFactor,
                                                      final int unrealizedPnlImfFactor) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_IMF_FACTOR_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, imfFactor);
    i += 4;
    putInt32LE(_data, i, unrealizedPnlImfFactor);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_UNREALIZED_ASSET_WEIGHT_DISCRIMINATOR = toDiscriminator(135, 132, 205, 165, 109, 150, 166, 106);

  public static Instruction updatePerpMarketUnrealizedAssetWeight(final AccountMeta invokedDriftProgramMeta,
                                                                  final PublicKey adminKey,
                                                                  final PublicKey stateKey,
                                                                  final PublicKey perpMarketKey,
                                                                  final int unrealizedInitialAssetWeight,
                                                                  final int unrealizedMaintenanceAssetWeight) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_UNREALIZED_ASSET_WEIGHT_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, unrealizedInitialAssetWeight);
    i += 4;
    putInt32LE(_data, i, unrealizedMaintenanceAssetWeight);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_CONCENTRATION_COEF_DISCRIMINATOR = toDiscriminator(24, 78, 232, 126, 169, 176, 230, 16);

  public static Instruction updatePerpMarketConcentrationCoef(final AccountMeta invokedDriftProgramMeta,
                                                              final PublicKey adminKey,
                                                              final PublicKey stateKey,
                                                              final PublicKey perpMarketKey,
                                                              final BigInteger concentrationScale) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_CONCENTRATION_COEF_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, concentrationScale);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_CURVE_UPDATE_INTENSITY_DISCRIMINATOR = toDiscriminator(50, 131, 6, 156, 226, 231, 189, 72);

  public static Instruction updatePerpMarketCurveUpdateIntensity(final AccountMeta invokedDriftProgramMeta,
                                                                 final PublicKey adminKey,
                                                                 final PublicKey stateKey,
                                                                 final PublicKey perpMarketKey,
                                                                 final int curveUpdateIntensity) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_CURVE_UPDATE_INTENSITY_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) curveUpdateIntensity;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_TARGET_BASE_ASSET_AMOUNT_PER_LP_DISCRIMINATOR = toDiscriminator(62, 87, 68, 115, 29, 150, 150, 165);

  public static Instruction updatePerpMarketTargetBaseAssetAmountPerLp(final AccountMeta invokedDriftProgramMeta,
                                                                       final PublicKey adminKey,
                                                                       final PublicKey stateKey,
                                                                       final PublicKey perpMarketKey,
                                                                       final int targetBaseAssetAmountPerLp) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_TARGET_BASE_ASSET_AMOUNT_PER_LP_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, targetBaseAssetAmountPerLp);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_PER_LP_BASE_DISCRIMINATOR = toDiscriminator(103, 152, 103, 102, 89, 144, 193, 71);

  public static Instruction updatePerpMarketPerLpBase(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final PublicKey perpMarketKey,
                                                      final int perLpBase) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_PER_LP_BASE_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) perLpBase;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_LP_COOLDOWN_TIME_DISCRIMINATOR = toDiscriminator(198, 133, 88, 41, 241, 119, 61, 14);

  public static Instruction updateLpCooldownTime(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final long lpCooldownTime) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_LP_COOLDOWN_TIME_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, lpCooldownTime);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_FEE_STRUCTURE_DISCRIMINATOR = toDiscriminator(23, 178, 111, 203, 73, 22, 140, 75);

  public static Instruction updatePerpFeeStructure(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final FeeStructure feeStructure) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(feeStructure)];
    int i = writeDiscriminator(UPDATE_PERP_FEE_STRUCTURE_DISCRIMINATOR, _data, 0);
    Borsh.write(feeStructure, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_FEE_STRUCTURE_DISCRIMINATOR = toDiscriminator(97, 216, 105, 131, 113, 246, 142, 141);

  public static Instruction updateSpotFeeStructure(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final FeeStructure feeStructure) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(feeStructure)];
    int i = writeDiscriminator(UPDATE_SPOT_FEE_STRUCTURE_DISCRIMINATOR, _data, 0);
    Borsh.write(feeStructure, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_INITIAL_PCT_TO_LIQUIDATE_DISCRIMINATOR = toDiscriminator(210, 133, 225, 128, 194, 50, 13, 109);

  public static Instruction updateInitialPctToLiquidate(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey adminKey,
                                                        final PublicKey stateKey,
                                                        final int initialPctToLiquidate) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_INITIAL_PCT_TO_LIQUIDATE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, initialPctToLiquidate);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_LIQUIDATION_DURATION_DISCRIMINATOR = toDiscriminator(28, 154, 20, 249, 102, 192, 73, 71);

  public static Instruction updateLiquidationDuration(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final int liquidationDuration) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_LIQUIDATION_DURATION_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) liquidationDuration;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_LIQUIDATION_MARGIN_BUFFER_RATIO_DISCRIMINATOR = toDiscriminator(132, 224, 243, 160, 154, 82, 97, 215);

  public static Instruction updateLiquidationMarginBufferRatio(final AccountMeta invokedDriftProgramMeta,
                                                               final PublicKey adminKey,
                                                               final PublicKey stateKey,
                                                               final int liquidationMarginBufferRatio) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_LIQUIDATION_MARGIN_BUFFER_RATIO_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, liquidationMarginBufferRatio);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_ORACLE_GUARD_RAILS_DISCRIMINATOR = toDiscriminator(131, 112, 10, 59, 32, 54, 40, 164);

  public static Instruction updateOracleGuardRails(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey adminKey,
                                                   final PublicKey stateKey,
                                                   final OracleGuardRails oracleGuardRails) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(oracleGuardRails)];
    int i = writeDiscriminator(UPDATE_ORACLE_GUARD_RAILS_DISCRIMINATOR, _data, 0);
    Borsh.write(oracleGuardRails, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_STATE_SETTLEMENT_DURATION_DISCRIMINATOR = toDiscriminator(97, 68, 199, 235, 131, 80, 61, 173);

  public static Instruction updateStateSettlementDuration(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final int settlementDuration) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_STATE_SETTLEMENT_DURATION_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, settlementDuration);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_STATE_MAX_NUMBER_OF_SUB_ACCOUNTS_DISCRIMINATOR = toDiscriminator(155, 123, 214, 2, 221, 166, 204, 85);

  public static Instruction updateStateMaxNumberOfSubAccounts(final AccountMeta invokedDriftProgramMeta,
                                                              final PublicKey adminKey,
                                                              final PublicKey stateKey,
                                                              final int maxNumberOfSubAccounts) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_STATE_MAX_NUMBER_OF_SUB_ACCOUNTS_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, maxNumberOfSubAccounts);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_STATE_MAX_INITIALIZE_USER_FEE_DISCRIMINATOR = toDiscriminator(237, 225, 25, 237, 193, 45, 77, 97);

  public static Instruction updateStateMaxInitializeUserFee(final AccountMeta invokedDriftProgramMeta,
                                                            final PublicKey adminKey,
                                                            final PublicKey stateKey,
                                                            final int maxInitializeUserFee) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_STATE_MAX_INITIALIZE_USER_FEE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, maxInitializeUserFee);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_ORACLE_DISCRIMINATOR = toDiscriminator(182, 113, 111, 160, 67, 174, 89, 191);

  public static Instruction updatePerpMarketOracle(final AccountMeta invokedDriftProgramMeta,
                                                   final PublicKey stateKey,
                                                   final PublicKey perpMarketKey,
                                                   final PublicKey oracleKey,
                                                   final PublicKey adminKey,
                                                   final PublicKey oracle,
                                                   final OracleSource oracleSource) {
    final var keys = List.of(
      createRead(stateKey),
      createWrite(perpMarketKey),
      createRead(oracleKey),
      createReadOnlySigner(adminKey)
    );

    final byte[] _data = new byte[40 + Borsh.len(oracleSource)];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_ORACLE_DISCRIMINATOR, _data, 0);
    oracle.write(_data, i);
    i += 32;
    Borsh.write(oracleSource, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_BASE_SPREAD_DISCRIMINATOR = toDiscriminator(71, 95, 84, 168, 9, 157, 198, 65);

  public static Instruction updatePerpMarketBaseSpread(final AccountMeta invokedDriftProgramMeta,
                                                       final PublicKey adminKey,
                                                       final PublicKey stateKey,
                                                       final PublicKey perpMarketKey,
                                                       final int baseSpread) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_BASE_SPREAD_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, baseSpread);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_AMM_JIT_INTENSITY_DISCRIMINATOR = toDiscriminator(181, 191, 53, 109, 166, 249, 55, 142);

  public static Instruction updateAmmJitIntensity(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey adminKey,
                                                  final PublicKey stateKey,
                                                  final PublicKey perpMarketKey,
                                                  final int ammJitIntensity) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_AMM_JIT_INTENSITY_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) ammJitIntensity;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MAX_SPREAD_DISCRIMINATOR = toDiscriminator(80, 252, 122, 62, 40, 218, 91, 100);

  public static Instruction updatePerpMarketMaxSpread(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final PublicKey perpMarketKey,
                                                      final int maxSpread) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[12];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MAX_SPREAD_DISCRIMINATOR, _data, 0);
    putInt32LE(_data, i, maxSpread);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_STEP_SIZE_AND_TICK_SIZE_DISCRIMINATOR = toDiscriminator(231, 255, 97, 25, 146, 139, 174, 4);

  public static Instruction updatePerpMarketStepSizeAndTickSize(final AccountMeta invokedDriftProgramMeta,
                                                                final PublicKey adminKey,
                                                                final PublicKey stateKey,
                                                                final PublicKey perpMarketKey,
                                                                final long stepSize,
                                                                final long tickSize) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_STEP_SIZE_AND_TICK_SIZE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, stepSize);
    i += 8;
    putInt64LE(_data, i, tickSize);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_NAME_DISCRIMINATOR = toDiscriminator(211, 31, 21, 210, 64, 108, 66, 201);

  public static Instruction updatePerpMarketName(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey perpMarketKey,
                                                 final int[] name) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[8 + Borsh.fixedLen(name)];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_NAME_DISCRIMINATOR, _data, 0);
    Borsh.fixedWrite(name, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MIN_ORDER_SIZE_DISCRIMINATOR = toDiscriminator(226, 74, 5, 89, 108, 223, 46, 141);

  public static Instruction updatePerpMarketMinOrderSize(final AccountMeta invokedDriftProgramMeta,
                                                         final PublicKey adminKey,
                                                         final PublicKey stateKey,
                                                         final PublicKey perpMarketKey,
                                                         final long orderSize) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MIN_ORDER_SIZE_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, orderSize);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MAX_SLIPPAGE_RATIO_DISCRIMINATOR = toDiscriminator(235, 37, 40, 196, 70, 146, 54, 201);

  public static Instruction updatePerpMarketMaxSlippageRatio(final AccountMeta invokedDriftProgramMeta,
                                                             final PublicKey adminKey,
                                                             final PublicKey stateKey,
                                                             final PublicKey perpMarketKey,
                                                             final int maxSlippageRatio) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MAX_SLIPPAGE_RATIO_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, maxSlippageRatio);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MAX_FILL_RESERVE_FRACTION_DISCRIMINATOR = toDiscriminator(19, 172, 114, 154, 42, 135, 161, 133);

  public static Instruction updatePerpMarketMaxFillReserveFraction(final AccountMeta invokedDriftProgramMeta,
                                                                   final PublicKey adminKey,
                                                                   final PublicKey stateKey,
                                                                   final PublicKey perpMarketKey,
                                                                   final int maxFillReserveFraction) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MAX_FILL_RESERVE_FRACTION_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, maxFillReserveFraction);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_MAX_OPEN_INTEREST_DISCRIMINATOR = toDiscriminator(194, 79, 149, 224, 246, 102, 186, 140);

  public static Instruction updatePerpMarketMaxOpenInterest(final AccountMeta invokedDriftProgramMeta,
                                                            final PublicKey adminKey,
                                                            final PublicKey stateKey,
                                                            final PublicKey perpMarketKey,
                                                            final BigInteger maxOpenInterest) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[24];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_MAX_OPEN_INTEREST_DISCRIMINATOR, _data, 0);
    putInt128LE(_data, i, maxOpenInterest);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_NUMBER_OF_USERS_DISCRIMINATOR = toDiscriminator(35, 62, 144, 177, 180, 62, 215, 196);

  public static Instruction updatePerpMarketNumberOfUsers(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey perpMarketKey,
                                                          final OptionalInt numberOfUsers,
                                                          final OptionalInt numberOfUsersWithBase) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_NUMBER_OF_USERS_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(numberOfUsers, _data, i);
    Borsh.writeOptional(numberOfUsersWithBase, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_FEE_ADJUSTMENT_DISCRIMINATOR = toDiscriminator(194, 174, 87, 102, 43, 148, 32, 112);

  public static Instruction updatePerpMarketFeeAdjustment(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey perpMarketKey,
                                                          final int feeAdjustment) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_FEE_ADJUSTMENT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, feeAdjustment);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_FEE_ADJUSTMENT_DISCRIMINATOR = toDiscriminator(148, 182, 3, 126, 157, 114, 220, 99);

  public static Instruction updateSpotMarketFeeAdjustment(final AccountMeta invokedDriftProgramMeta,
                                                          final PublicKey adminKey,
                                                          final PublicKey stateKey,
                                                          final PublicKey spotMarketKey,
                                                          final int feeAdjustment) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_FEE_ADJUSTMENT_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, feeAdjustment);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_MARKET_FUEL_DISCRIMINATOR = toDiscriminator(252, 141, 110, 101, 27, 99, 182, 21);

  public static Instruction updatePerpMarketFuel(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey perpMarketKey,
                                                 final OptionalInt fuelBoostTaker,
                                                 final OptionalInt fuelBoostMaker,
                                                 final OptionalInt fuelBoostPosition) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(perpMarketKey)
    );

    final byte[] _data = new byte[14];
    int i = writeDiscriminator(UPDATE_PERP_MARKET_FUEL_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(fuelBoostTaker, _data, i);
    i += Borsh.writeOptional(fuelBoostMaker, _data, i);
    Borsh.writeOptional(fuelBoostPosition, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_MARKET_FUEL_DISCRIMINATOR = toDiscriminator(226, 253, 76, 71, 17, 2, 171, 169);

  public static Instruction updateSpotMarketFuel(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final PublicKey spotMarketKey,
                                                 final OptionalInt fuelBoostDeposits,
                                                 final OptionalInt fuelBoostBorrows,
                                                 final OptionalInt fuelBoostTaker,
                                                 final OptionalInt fuelBoostMaker,
                                                 final OptionalInt fuelBoostInsurance) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(spotMarketKey)
    );

    final byte[] _data = new byte[18];
    int i = writeDiscriminator(UPDATE_SPOT_MARKET_FUEL_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(fuelBoostDeposits, _data, i);
    i += Borsh.writeOptional(fuelBoostBorrows, _data, i);
    i += Borsh.writeOptional(fuelBoostTaker, _data, i);
    i += Borsh.writeOptional(fuelBoostMaker, _data, i);
    Borsh.writeOptional(fuelBoostInsurance, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INIT_USER_FUEL_DISCRIMINATOR = toDiscriminator(132, 191, 228, 141, 201, 138, 60, 48);

  public static Instruction initUserFuel(final AccountMeta invokedDriftProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey stateKey,
                                         final PublicKey userKey,
                                         final PublicKey userStatsKey,
                                         final OptionalInt fuelBoostDeposits,
                                         final OptionalInt fuelBoostBorrows,
                                         final OptionalInt fuelBoostTaker,
                                         final OptionalInt fuelBoostMaker,
                                         final OptionalInt fuelBoostInsurance) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(stateKey),
      createWrite(userKey),
      createWrite(userStatsKey)
    );

    final byte[] _data = new byte[33];
    int i = writeDiscriminator(INIT_USER_FUEL_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(fuelBoostDeposits, _data, i);
    i += Borsh.writeOptional(fuelBoostBorrows, _data, i);
    i += Borsh.writeOptional(fuelBoostTaker, _data, i);
    i += Borsh.writeOptional(fuelBoostMaker, _data, i);
    Borsh.writeOptional(fuelBoostInsurance, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_ADMIN_DISCRIMINATOR = toDiscriminator(161, 176, 40, 213, 60, 184, 179, 228);

  public static Instruction updateAdmin(final AccountMeta invokedDriftProgramMeta,
                                        final PublicKey adminKey,
                                        final PublicKey stateKey,
                                        final PublicKey admin) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_ADMIN_DISCRIMINATOR, _data, 0);
    admin.write(_data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_WHITELIST_MINT_DISCRIMINATOR = toDiscriminator(161, 15, 162, 19, 148, 120, 144, 151);

  public static Instruction updateWhitelistMint(final AccountMeta invokedDriftProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey stateKey,
                                                final PublicKey whitelistMint) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_WHITELIST_MINT_DISCRIMINATOR, _data, 0);
    whitelistMint.write(_data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_DISCOUNT_MINT_DISCRIMINATOR = toDiscriminator(32, 252, 122, 211, 66, 31, 47, 241);

  public static Instruction updateDiscountMint(final AccountMeta invokedDriftProgramMeta,
                                               final PublicKey adminKey,
                                               final PublicKey stateKey,
                                               final PublicKey discountMint) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[40];
    int i = writeDiscriminator(UPDATE_DISCOUNT_MINT_DISCRIMINATOR, _data, 0);
    discountMint.write(_data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_EXCHANGE_STATUS_DISCRIMINATOR = toDiscriminator(83, 160, 252, 250, 129, 116, 49, 223);

  public static Instruction updateExchangeStatus(final AccountMeta invokedDriftProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey stateKey,
                                                 final int exchangeStatus) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_EXCHANGE_STATUS_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) exchangeStatus;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PERP_AUCTION_DURATION_DISCRIMINATOR = toDiscriminator(126, 110, 52, 174, 30, 206, 215, 90);

  public static Instruction updatePerpAuctionDuration(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final int minPerpAuctionDuration) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_PERP_AUCTION_DURATION_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) minPerpAuctionDuration;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_SPOT_AUCTION_DURATION_DISCRIMINATOR = toDiscriminator(182, 178, 203, 72, 187, 143, 157, 107);

  public static Instruction updateSpotAuctionDuration(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey stateKey,
                                                      final int defaultSpotAuctionDuration) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(stateKey)
    );

    final byte[] _data = new byte[9];
    int i = writeDiscriminator(UPDATE_SPOT_AUCTION_DURATION_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) defaultSpotAuctionDuration;

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_PROTOCOL_IF_SHARES_TRANSFER_CONFIG_DISCRIMINATOR = toDiscriminator(89, 131, 239, 200, 178, 141, 106, 194);

  public static Instruction initializeProtocolIfSharesTransferConfig(final AccountMeta invokedDriftProgramMeta,
                                                                     final PublicKey adminKey,
                                                                     final PublicKey protocolIfSharesTransferConfigKey,
                                                                     final PublicKey stateKey,
                                                                     final PublicKey rentKey,
                                                                     final PublicKey systemProgramKey) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(protocolIfSharesTransferConfigKey),
      createRead(stateKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, INITIALIZE_PROTOCOL_IF_SHARES_TRANSFER_CONFIG_DISCRIMINATOR);
  }

  public static final Discriminator UPDATE_PROTOCOL_IF_SHARES_TRANSFER_CONFIG_DISCRIMINATOR = toDiscriminator(34, 135, 47, 91, 220, 24, 212, 53);

  public static Instruction updateProtocolIfSharesTransferConfig(final AccountMeta invokedDriftProgramMeta,
                                                                 final PublicKey adminKey,
                                                                 final PublicKey protocolIfSharesTransferConfigKey,
                                                                 final PublicKey stateKey,
                                                                 final PublicKey[] whitelistedSigners,
                                                                 final BigInteger maxTransferPerEpoch) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(protocolIfSharesTransferConfigKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[26 + (whitelistedSigners == null || whitelistedSigners.length == 0 ? 0 : Borsh.fixedLen(whitelistedSigners))];
    int i = writeDiscriminator(UPDATE_PROTOCOL_IF_SHARES_TRANSFER_CONFIG_DISCRIMINATOR, _data, 0);
    i += Borsh.writeOptional(whitelistedSigners, _data, i);
    Borsh.writeOptional(maxTransferPerEpoch, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_PRELAUNCH_ORACLE_DISCRIMINATOR = toDiscriminator(169, 178, 84, 25, 175, 62, 29, 247);

  public static Instruction initializePrelaunchOracle(final AccountMeta invokedDriftProgramMeta,
                                                      final PublicKey adminKey,
                                                      final PublicKey prelaunchOracleKey,
                                                      final PublicKey stateKey,
                                                      final PublicKey rentKey,
                                                      final PublicKey systemProgramKey,
                                                      final PrelaunchOracleParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(prelaunchOracleKey),
      createRead(stateKey),
      createRead(rentKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(INITIALIZE_PRELAUNCH_ORACLE_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator UPDATE_PRELAUNCH_ORACLE_PARAMS_DISCRIMINATOR = toDiscriminator(98, 205, 147, 243, 18, 75, 83, 207);

  public static Instruction updatePrelaunchOracleParams(final AccountMeta invokedDriftProgramMeta,
                                                        final PublicKey adminKey,
                                                        final PublicKey prelaunchOracleKey,
                                                        final PublicKey perpMarketKey,
                                                        final PublicKey stateKey,
                                                        final PrelaunchOracleParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(prelaunchOracleKey),
      createWrite(perpMarketKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = writeDiscriminator(UPDATE_PRELAUNCH_ORACLE_PARAMS_DISCRIMINATOR, _data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator DELETE_PRELAUNCH_ORACLE_DISCRIMINATOR = toDiscriminator(59, 169, 100, 49, 69, 17, 173, 253);

  public static Instruction deletePrelaunchOracle(final AccountMeta invokedDriftProgramMeta,
                                                  final PublicKey adminKey,
                                                  final PublicKey prelaunchOracleKey,
                                                  final PublicKey perpMarketKey,
                                                  final PublicKey stateKey,
                                                  final int perpMarketIndex) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(prelaunchOracleKey),
      createRead(perpMarketKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[10];
    int i = writeDiscriminator(DELETE_PRELAUNCH_ORACLE_DISCRIMINATOR, _data, 0);
    putInt16LE(_data, i, perpMarketIndex);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }

  public static final Discriminator INITIALIZE_PYTH_PULL_ORACLE_DISCRIMINATOR = toDiscriminator(249, 140, 253, 243, 248, 74, 240, 238);

  public static Instruction initializePythPullOracle(final AccountMeta invokedDriftProgramMeta,
                                                     final PublicKey adminKey,
                                                     final PublicKey pythSolanaReceiverKey,
                                                     final PublicKey priceFeedKey,
                                                     final PublicKey systemProgramKey,
                                                     final PublicKey stateKey,
                                                     final int[] feedId) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(pythSolanaReceiverKey),
      createWrite(priceFeedKey),
      createRead(systemProgramKey),
      createRead(stateKey)
    );

    final byte[] _data = new byte[8 + Borsh.fixedLen(feedId)];
    int i = writeDiscriminator(INITIALIZE_PYTH_PULL_ORACLE_DISCRIMINATOR, _data, 0);
    Borsh.fixedWrite(feedId, _data, i);

    return Instruction.createInstruction(invokedDriftProgramMeta, keys, _data);
  }
  
  private DriftProgram() {
  }
}