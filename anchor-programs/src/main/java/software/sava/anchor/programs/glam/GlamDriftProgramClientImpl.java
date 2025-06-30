package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.drift.*;
import software.sava.anchor.programs.drift.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.SettlePnlMode;
import software.sava.anchor.programs.glam.anchor.GlamProtocolProgram;
import software.sava.anchor.programs.glam.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;

import java.nio.charset.StandardCharsets;
import java.util.OptionalInt;
import java.util.OptionalLong;

import static software.sava.anchor.programs.drift.DriftPDAs.deriveUserStatsAccount;

final class GlamDriftProgramClientImpl implements GlamDriftProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts driftAccounts;
  private final DriftProgramClient delegatedDriftClient;
  private final GlamVaultAccounts glamVaultAccounts;
  private final AccountMeta invokedProgram;
  private final AccountMeta feePayer;
  private final PublicKey user;

  GlamDriftProgramClientImpl(final GlamProgramAccountClient glamClient,
                             final DriftAccounts driftAccounts) {
    this.solanaAccounts = glamClient.solanaAccounts();
    this.driftAccounts = driftAccounts;
    this.glamVaultAccounts = glamClient.vaultAccounts();
    this.delegatedDriftClient = DriftProgramClient.createClient(glamClient, driftAccounts);
    this.invokedProgram = glamVaultAccounts.glamAccounts().invokedProgram();
    this.feePayer = glamClient.feePayer();
    this.user = DriftPDAs.deriveMainUserAccount(driftAccounts, glamVaultAccounts.vaultPublicKey()).publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public DriftAccounts driftAccounts() {
    return driftAccounts;
  }

  @Override
  public PublicKey mainUserAccount() {
    return user;
  }

  @Override
  public PublicKey authority() {
    return glamVaultAccounts.vaultPublicKey();
  }

  @Override
  public PublicKey feePayer() {
    return feePayer.publicKey();
  }

  @Override
  public Instruction initializeUser(final PublicKey user,
                                    final PublicKey authority,
                                    final PublicKey payerKey,
                                    final int subAccountId,
                                    final String name) {
    final var userStatsKey = deriveUserStatsAccount(driftAccounts, authority).publicKey();
    return GlamProtocolProgram.driftInitializeUser(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        user,
        userStatsKey,
        driftAccounts.stateKey(),
        payerKey,
        subAccountId,
        name.getBytes(StandardCharsets.UTF_8)
    );
  }

  @Override
  public Instruction initializeUserStats(final PublicKey authority, final PublicKey payerKey) {
    final var userStatsKey = deriveUserStatsAccount(driftAccounts, authority).publicKey();
    return GlamProtocolProgram.driftInitializeUserStats(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        userStatsKey,
        driftAccounts.stateKey(),
        payerKey
    );
  }

  @Override
  public Instruction deposit(final PublicKey user,
                             final PublicKey authority,
                             final PublicKey userTokenAccountKey,
                             final PublicKey tokenProgramKey,
                             final SpotMarketConfig spotMarketConfig,
                             final long amount,
                             final boolean reduceOnly) {
    final var userStatsKey = deriveUserStatsAccount(driftAccounts, authority).publicKey();
    return GlamProtocolProgram.driftDeposit(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        userStatsKey,
        spotMarketConfig.vaultPDA(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig.marketIndex(),
        amount,
        reduceOnly
    );
  }

  @Override
  public Instruction transferDeposit(final PublicKey fromUser,
                                     final PublicKey toUser,
                                     final PublicKey authority,
                                     final SpotMarketConfig spotMarketConfig,
                                     final long amount) {
    throw new UnsupportedOperationException("TODO: transferDeposit");
  }

  @Override
  public Instruction withdraw(final PublicKey user,
                              final PublicKey authority,
                              final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount,
                              final boolean reduceOnly) {
    final var userStatsKey = deriveUserStatsAccount(driftAccounts, authority).publicKey();
    return GlamProtocolProgram.driftWithdraw(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        userStatsKey,
        spotMarketConfig.vaultPDA(),
        driftAccounts.driftSignerPDA(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig.marketIndex(),
        amount,
        reduceOnly
    );
  }

  @Override
  public Instruction settlePnl(final int marketIndex) {
    throw new UnsupportedOperationException("TODO: settlePNL");
  }

  @Override
  public Instruction transferPools(final PublicKey authority,
                                   final PublicKey fromUser,
                                   final PublicKey toUser,
                                   final SpotMarketConfig depositFromSpotMarketConfig,
                                   final SpotMarketConfig depositToSpotMarketConfig,
                                   final SpotMarketConfig borrowFromSpotMarketConfig,
                                   final SpotMarketConfig borrowToSpotMarketConfig,
                                   final OptionalLong depositAmount,
                                   final OptionalLong borrowAmount) {
    throw new UnsupportedOperationException("TODO: transferPools");
  }

  @Override
  public Instruction transferPerpPosition(final PublicKey authority,
                                          final PublicKey fromUser,
                                          final PublicKey toUser,
                                          final PerpMarketConfig perpMarketConfig,
                                          final OptionalLong amount) {
    throw new UnsupportedOperationException("TODO: transferPerpPosition");
  }

  @Override
  public Instruction settlePnl(final PublicKey user, final PublicKey authority, final int marketIndex) {
    return delegatedDriftClient.settlePnl(user, authority, marketIndex);
  }

  @Override
  public Instruction settlePnl(final short[] marketIndexes, final SettlePnlMode mode) {
    return delegatedDriftClient.settlePnl(user, feePayer.publicKey(), marketIndexes, mode);
  }

  @Override
  public Instruction settlePnl(final PublicKey user,
                               final PublicKey authority,
                               final short[] marketIndexes,
                               final SettlePnlMode mode) {
    return delegatedDriftClient.settlePnl(user, authority, marketIndexes, mode);
  }

  private static PostOnlyParam mapPostOnlyParam(final software.sava.anchor.programs.drift.anchor.types.PostOnlyParam postOnlyParam) {
    return switch (postOnlyParam) {
      case None -> PostOnlyParam.None;
      case MustPostOnly -> PostOnlyParam.MustPostOnly;
      case TryPostOnly -> PostOnlyParam.TryPostOnly;
      case Slide -> PostOnlyParam.Slide;
    };
  }

  private static OrderTriggerCondition mapOrderTriggerCondition(final software.sava.anchor.programs.drift.anchor.types.OrderTriggerCondition orderTriggerCondition) {
    return switch (orderTriggerCondition) {
      case Above -> OrderTriggerCondition.Above;
      case Below -> OrderTriggerCondition.Below;
      case TriggeredAbove -> OrderTriggerCondition.TriggeredAbove;
      case TriggeredBelow -> OrderTriggerCondition.TriggeredBelow;
      case null -> null;
    };
  }

  private static PositionDirection mapDirection(final software.sava.anchor.programs.drift.anchor.types.PositionDirection direction) {
    return switch (direction) {
      case Long -> PositionDirection.Long;
      case Short -> PositionDirection.Short;
      case null -> null;
    };
  }

  private static software.sava.anchor.programs.glam.anchor.types.OrderParams toGlam(final OrderParams orderParams) {
    return new software.sava.anchor.programs.glam.anchor.types.OrderParams(
        switch (orderParams.orderType()) {
          case Market -> OrderType.Market;
          case Limit -> OrderType.Limit;
          case TriggerMarket -> OrderType.TriggerMarket;
          case TriggerLimit -> OrderType.TriggerLimit;
          case Oracle -> OrderType.Oracle;
        },
        switch (orderParams.marketType()) {
          case Spot -> MarketType.Spot;
          case Perp -> MarketType.Perp;
        },
        mapDirection(orderParams.direction()),
        orderParams.userOrderId(),
        orderParams.baseAssetAmount(),
        orderParams.price(),
        orderParams.marketIndex(),
        orderParams.reduceOnly(),
        mapPostOnlyParam(orderParams.postOnly()),
        orderParams.bitFlags(),
        orderParams.maxTs(),
        orderParams.triggerPrice(),
        mapOrderTriggerCondition(orderParams.triggerCondition()),
        orderParams.oraclePriceOffset(),
        orderParams.auctionDuration(),
        orderParams.auctionStartPrice(),
        orderParams.auctionEndPrice()
    );
  }

  private static software.sava.anchor.programs.glam.anchor.types.OrderParams[] toGlam(final OrderParams[] orderParamsArray) {
    final var glamOrderParams = new software.sava.anchor.programs.glam.anchor.types.OrderParams[orderParamsArray.length];
    for (int i = 0; i < orderParamsArray.length; ++i) {
      glamOrderParams[i] = toGlam(orderParamsArray[i]);
    }
    return glamOrderParams;
  }

  @Override
  public Instruction placeOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return placeOrders(new OrderParams[]{orderParams}, authority, user);
  }

  @Override
  public Instruction placeSpotOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return placeOrder(orderParams, authority, user);
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return placeOrders(new OrderParams[]{orderParams}, authority, user);
  }

  @Override
  public Instruction placeOrders(final OrderParams[] orderParams, final PublicKey authority, final PublicKey user) {
    return GlamProtocolProgram.driftPlaceOrders(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        toGlam(orderParams)
    );
  }

  @Override
  public Instruction cancelOrders(final PublicKey authority, final PublicKey user, final int[] orderIds) {
    return GlamProtocolProgram.driftCancelOrdersByIds(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        orderIds
    );
  }

  @Override
  public Instruction cancelOrderByUserOrderId(final PublicKey authority, final PublicKey user, final int orderId) {
    return cancelOrders(authority, user, new int[]{orderId});
  }

  @Override
  public Instruction cancelOrder(final PublicKey authority, final PublicKey user, final int orderId) {
    return cancelOrderByUserOrderId(authority, user, orderId);
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority,
                                     final PublicKey user,
                                     final software.sava.anchor.programs.drift.anchor.types.PositionDirection direction) {
    return GlamProtocolProgram.driftCancelOrders(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        null,
        OptionalInt.empty(),
        mapDirection(direction)
    );
  }

  @Override
  public Instruction cancelAllSpotOrders(final PublicKey authority,
                                         final PublicKey user,
                                         final software.sava.anchor.programs.drift.anchor.types.PositionDirection direction) {
    return GlamProtocolProgram.driftCancelOrders(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        MarketType.Spot,
        OptionalInt.empty(),
        mapDirection(direction)
    );
  }

  @Override
  public Instruction cancelAllPerpOrders(final PublicKey authority,
                                         final PublicKey user,
                                         final software.sava.anchor.programs.drift.anchor.types.PositionDirection direction) {
    return GlamProtocolProgram.driftCancelOrders(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        MarketType.Perp,
        OptionalInt.empty(),
        mapDirection(direction)
    );
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority,
                                     final PublicKey user,
                                     final MarketConfig marketConfig,
                                     final software.sava.anchor.programs.drift.anchor.types.PositionDirection direction) {
    return GlamProtocolProgram.driftCancelOrders(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        marketConfig instanceof PerpMarketConfig ? MarketType.Perp : MarketType.Spot,
        OptionalInt.of(marketConfig.marketIndex()),
        mapDirection(direction)
    );
  }

  private static software.sava.anchor.programs.glam.anchor.types.ModifyOrderParams mapModifyOrderParams(final ModifyOrderParams modifyOrderParams) {
    return new software.sava.anchor.programs.glam.anchor.types.ModifyOrderParams(
        mapDirection(modifyOrderParams.direction()),
        modifyOrderParams.baseAssetAmount(),
        modifyOrderParams.price(),
        modifyOrderParams.reduceOnly(),
        mapPostOnlyParam(modifyOrderParams.postOnly()),
        modifyOrderParams.bitFlags(),
        modifyOrderParams.maxTs(),
        modifyOrderParams.triggerPrice(),
        mapOrderTriggerCondition(modifyOrderParams.triggerCondition()),
        modifyOrderParams.oraclePriceOffset(),
        modifyOrderParams.auctionDuration(),
        modifyOrderParams.auctionStartPrice(),
        modifyOrderParams.auctionEndPrice(),
        modifyOrderParams.policy()
    );
  }

  @Override
  public Instruction modifyOrder(final PublicKey authority,
                                 final PublicKey user,
                                 final OptionalInt orderId,
                                 final software.sava.anchor.programs.drift.anchor.types.ModifyOrderParams modifyOrderParams) {
    return GlamProtocolProgram.driftModifyOrder(
        invokedProgram,
        glamVaultAccounts.glamPublicKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        driftAccounts.stateKey(),
        user,
        orderId,
        mapModifyOrderParams(modifyOrderParams)
    );
  }

  @Override
  public Instruction modifyOrderByUserId(final PublicKey authority,
                                         final PublicKey user,
                                         final int userOrderId,
                                         final software.sava.anchor.programs.drift.anchor.types.ModifyOrderParams modifyOrderParams) {
    return modifyOrder(authority, user, OptionalInt.of(userOrderId), modifyOrderParams);
  }

  @Override
  public Instruction placeAndTakePerpOrder(final PublicKey authority,
                                           final PublicKey user,
                                           final OrderParams params,
                                           final OptionalInt successCondition) {
    throw new UnsupportedOperationException("TODO: placeAndTakePerpOrder");
  }

  @Override
  public Instruction initializeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey payerKey,
                                                   final int numOrders) {
    throw new UnsupportedOperationException("TODO: initializeSignedMsgUserOrders");
  }

  @Override
  public Instruction resizeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                               final PublicKey authorityKey,
                                               final PublicKey userKey,
                                               final PublicKey payerKey,
                                               final int numOrders) {
    throw new UnsupportedOperationException("TODO: resizeSignedMsgUserOrders");
  }

  @Override
  public Instruction initializeSignedMsgWsDelegates(final PublicKey signedMsgUserOrdersKey,
                                                    final PublicKey authorityKey,
                                                    final PublicKey[] delegates) {
    throw new UnsupportedOperationException("TODO: initializeSignedMsgWsDelegates");
  }

  @Override
  public Instruction changeSignedMsgWsDelegateStatus(final PublicKey signedMsgUserOrdersKey,
                                                     final PublicKey authorityKey,
                                                     final PublicKey delegate,
                                                     final boolean add) {
    throw new UnsupportedOperationException("TODO: changeSignedMsgWsDelegateStatus");
  }

  @Override
  public Instruction placeAndMakeSignedMsgPerpOrder(final PublicKey userKey,
                                                    final PublicKey takerKey,
                                                    final PublicKey takerStatsKey,
                                                    final PublicKey takerSignedMsgUserOrdersKey,
                                                    final PublicKey authorityKey,
                                                    final OrderParams params,
                                                    final byte[] signedMsgOrderUuid) {
    throw new UnsupportedOperationException("TODO: placeAndMakeSignedMsgPerpOrder");
  }

  @Override
  public Instruction placeSignedMsgTakerOrder(final PublicKey userKey,
                                              final PublicKey signedMsgUserOrdersKey,
                                              final PublicKey authorityKey,
                                              final byte[] signedMsgOrderParamsMessageBytes,
                                              final boolean isDelegateSigner) {
    throw new UnsupportedOperationException("TODO: placeSignedMsgTakerOrder");
  }

  @Override
  public Instruction deleteSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey, final PublicKey authorityKey) {
    throw new UnsupportedOperationException("TODO: deleteSignedMsgUserOrders");
  }
}
