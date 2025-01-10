package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.drift.*;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.SettlePnlMode;
import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.anchor.programs.glam.anchor.GlamProgram;
import software.sava.anchor.programs.glam.anchor.types.*;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;

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
  public CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient) {
    return fetchUser(rpcClient, user);
  }


  @Override
  public Instruction deposit(final PublicKey userTokenAccountKey,
                             final PublicKey tokenProgramKey,
                             final SpotMarketConfig spotMarketConfig,
                             final long amount) {
    return deposit(user, authority(), userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount);
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
    return GlamProgram.driftDeposit(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        userStatsKey,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        spotMarketConfig.vaultPDA(),
        userTokenAccountKey,
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        spotMarketConfig.marketIndex(),
        amount
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
  public Instruction withdraw(final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount) {
    return withdraw(
        user,
        authority(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig,
        amount
    );
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
    return GlamProgram.driftWithdraw(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        userStatsKey,
        driftAccounts.stateKey(),
        driftAccounts.driftSignerPDA(),
        glamVaultAccounts.vaultPublicKey(),
        userTokenAccountKey,
        spotMarketConfig.vaultPDA(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        spotMarketConfig.marketIndex(),
        amount
    );
  }

  @Override
  public Instruction settlePnl(final int marketIndex) {
    return delegatedDriftClient.settlePnl(user, feePayer.publicKey(), marketIndex);
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
  public Instruction settlePnl(final PublicKey user, final PublicKey authority, final short[] marketIndexes, final SettlePnlMode mode) {
    return delegatedDriftClient.settlePnl(user, authority, marketIndexes, mode);
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
        switch (orderParams.direction()) {
          case Long -> PositionDirection.Long;
          case Short -> PositionDirection.Short;
        },
        orderParams.userOrderId(),
        orderParams.baseAssetAmount(),
        orderParams.price(),
        orderParams.marketIndex(),
        orderParams.reduceOnly(),
        switch (orderParams.postOnly()) {
          case None -> PostOnlyParam.None;
          case MustPostOnly -> PostOnlyParam.MustPostOnly;
          case TryPostOnly -> PostOnlyParam.TryPostOnly;
          case Slide -> PostOnlyParam.Slide;
        },
        orderParams.immediateOrCancel(),
        orderParams.maxTs(),
        orderParams.triggerPrice(),
        switch (orderParams.triggerCondition()) {
          case Above -> OrderTriggerCondition.Above;
          case Below -> OrderTriggerCondition.Below;
          case TriggeredAbove -> OrderTriggerCondition.TriggeredAbove;
          case TriggeredBelow -> OrderTriggerCondition.TriggeredBelow;
        },
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
  public Instruction placeOrder(final OrderParams orderParams) {
    return placeOrder(orderParams, authority(), user);
  }

  @Override
  public Instruction placeOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return placeOrders(new OrderParams[]{orderParams}, authority, user);
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams) {
    return placePerpOrder(orderParams, authority(), user);
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return placeOrders(new OrderParams[]{orderParams}, authority, user);
  }

  @Override
  public Instruction placeOrders(final OrderParams[] orderParams) {
    return placeOrders(orderParams, authority(), user);
  }

  @Override
  public Instruction placeOrders(final OrderParams[] orderParams, final PublicKey authority, final PublicKey user) {
    return GlamProgram.driftPlaceOrders(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        toGlam(orderParams)
    );
  }

  @Override
  public Instruction cancelOrder(final int orderId) {
    return cancelOrder(authority(), user, orderId);
  }

  @Override
  public Instruction cancelOrder(final PublicKey authority, final PublicKey user, final int orderId) {
    throw new UnsupportedOperationException("TODO: cancelOrder");
  }

  @Override
  public Instruction cancelOrders(final int[] orderIds) {
    return cancelOrders(authority(), user, orderIds);
  }

  @Override
  public Instruction cancelOrders(final PublicKey authority, final PublicKey user, final int[] orderIds) {
    throw new UnsupportedOperationException("TODO: cancel by orderIds");
  }

  @Override
  public Instruction cancelOrderByUserOrderId(final int orderId) {
    return cancelOrderByUserOrderId(authority(), user, orderId);
  }

  @Override
  public Instruction cancelOrderByUserOrderId(final PublicKey authority, final PublicKey user, final int orderId) {
    throw new UnsupportedOperationException("TODO: cancelOrderByUserOrderId");
  }

  @Override
  public Instruction cancelAllOrders() {
    return cancelAllOrders(authority(), user);
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority, final PublicKey user) {
    return GlamProgram.driftCancelOrders(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        null,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllSpotOrders() {
    return cancelAllSpotOrders(authority(), user);
  }

  @Override
  public Instruction cancelAllSpotOrders(final PublicKey authority, final PublicKey user) {
    return GlamProgram.driftCancelOrders(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        MarketType.Spot,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllPerpOrders() {
    return cancelAllPerpOrders(authority(), user);
  }

  @Override
  public Instruction cancelAllPerpOrders(final PublicKey authority, final PublicKey user) {
    return GlamProgram.driftCancelOrders(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        MarketType.Perp,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllOrders(final MarketConfig marketConfig) {
    return cancelAllOrders(authority(), user, marketConfig);
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority, final PublicKey user, final MarketConfig marketConfig) {
    return GlamProgram.driftCancelOrders(
        invokedProgram,
        solanaAccounts,
        glamVaultAccounts.glamPublicKey(),
        user,
        driftAccounts.stateKey(),
        glamVaultAccounts.vaultPublicKey(),
        feePayer.publicKey(),
        driftAccounts.driftProgram(),
        marketConfig instanceof PerpMarketConfig ? MarketType.Perp : MarketType.Spot,
        OptionalInt.of(marketConfig.marketIndex()),
        null
    );
  }
}
