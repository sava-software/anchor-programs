package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.SettlePnlMode;
import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.OptionalInt;
import java.util.concurrent.CompletableFuture;

import static software.sava.anchor.programs.drift.DriftPDAs.deriveSpotMarketVaultAccount;
import static software.sava.anchor.programs.drift.DriftPDAs.deriveUserStatsAccount;
import static software.sava.anchor.programs.drift.anchor.types.MarketType.Perp;
import static software.sava.anchor.programs.drift.anchor.types.MarketType.Spot;

final class DriftProgramClientImpl implements DriftProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts accounts;
  private final PublicKey authority;
  private final PublicKey user;
  private final PublicKey quoteSpotMarketVaultAccountKey;

  DriftProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                         final DriftAccounts accounts) {
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.accounts = accounts;
    this.authority = nativeProgramAccountClient.ownerPublicKey();
    this.user = DriftPDAs.deriveMainUserAccount(accounts, authority).publicKey();
    this.quoteSpotMarketVaultAccountKey = deriveSpotMarketVaultAccount(accounts, accounts.defaultQuoteMarket().marketIndex()).publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public DriftAccounts driftAccounts() {
    return accounts;
  }

  @Override
  public PublicKey mainUserAccount() {
    return user;
  }

  @Override
  public PublicKey authority() {
    return authority;
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
    return deposit(user, authority, userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount);
  }

  @Override
  public Instruction deposit(final PublicKey user,
                             final PublicKey authority,
                             final PublicKey userTokenAccountKey,
                             final PublicKey tokenProgramKey,
                             final SpotMarketConfig spotMarketConfig,
                             final long amount,
                             final boolean reduceOnly) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.deposit(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        userStatsPDA.publicKey(),
        authority,
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
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.transferDeposit(
        accounts.invokedDriftProgram(),
        fromUser,
        toUser,
        userStatsPDA.publicKey(),
        authority,
        accounts.stateKey(),
        spotMarketConfig.vaultPDA(),
        spotMarketConfig.marketIndex(),
        amount
    );
  }

  @Override
  public Instruction withdraw(final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount) {
    return withdraw(user, authority, userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount);
  }

  @Override
  public Instruction withdraw(final PublicKey user,
                              final PublicKey authority,
                              final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount,
                              final boolean reduceOnly) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.withdraw(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        userStatsPDA.publicKey(),
        authority,
        spotMarketConfig.vaultPDA(),
        accounts.driftSignerPDA(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig.marketIndex(),
        amount,
        reduceOnly
    );
  }

  @Override
  public Instruction settlePnl(final int marketIndex) {
    return settlePnl(user, authority(), marketIndex);
  }

  @Override
  public Instruction settlePnl(final PublicKey user,
                               final PublicKey authority,
                               final int marketIndex) {
    return DriftProgram.settlePnl(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        quoteSpotMarketVaultAccountKey,
        marketIndex
    );
  }

  @Override
  public Instruction settlePnl(final short[] marketIndexes, final SettlePnlMode mode) {
    return settlePnl(user, authority(), marketIndexes, mode);
  }

  @Override
  public Instruction settlePnl(final PublicKey user,
                               final PublicKey authority,
                               final short[] marketIndexes,
                               final SettlePnlMode mode) {
    return DriftProgram.settleMultiplePnls(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        quoteSpotMarketVaultAccountKey,
        marketIndexes,
        mode
    );
  }

  @Override
  public Instruction placeOrder(final OrderParams orderParams) {
    return placeOrder(orderParams, authority, user);
  }

  @Override
  public Instruction placeOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return switch (orderParams.marketType()) {
      case Spot -> placeSpotOrder(orderParams, authority, user);
      case Perp -> placePerpOrder(orderParams, authority, user);
    };
  }

  public Instruction placeSpotOrder(final OrderParams orderParams) {
    return placeSpotOrder(orderParams, authority, user);
  }

  public Instruction placeSpotOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return DriftProgram.placeSpotOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderParams
    );
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams) {
    return placePerpOrder(orderParams, authority, user);
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return DriftProgram.placePerpOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderParams
    );
  }

  @Override
  public Instruction placeOrders(final OrderParams[] orderParams) {
    return placeOrders(orderParams, authority, user);
  }

  @Override
  public Instruction placeOrders(final OrderParams[] orderParams, final PublicKey authority, final PublicKey user) {
    return DriftProgram.placeOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderParams
    );
  }

  @Override
  public Instruction cancelOrder(final int orderId) {
    return cancelOrder(authority, user, orderId);
  }

  @Override
  public Instruction cancelOrder(final PublicKey authority, final PublicKey user, final int orderId) {
    return DriftProgram.cancelOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        OptionalInt.of(orderId)
    );
  }

  @Override
  public Instruction cancelOrders(final int[] orderIds) {
    return cancelOrders(authority, user, orderIds);
  }

  @Override
  public Instruction cancelOrders(final PublicKey authority, final PublicKey user, final int[] orderIds) {
    return DriftProgram.cancelOrdersByIds(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderIds
    );
  }

  @Override
  public Instruction cancelOrderByUserOrderId(final int orderId) {
    return cancelOrderByUserOrderId(authority, user, orderId);
  }

  @Override
  public Instruction cancelOrderByUserOrderId(final PublicKey authority, final PublicKey user, final int orderId) {
    return DriftProgram.cancelOrderByUserId(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderId
    );
  }

  @Override
  public Instruction cancelAllOrders() {
    return cancelAllOrders(authority, user);
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority, final PublicKey user) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        null,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllSpotOrders() {
    return cancelAllSpotOrders(authority, user);
  }

  @Override
  public Instruction cancelAllSpotOrders(final PublicKey authority, final PublicKey user) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        Spot,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllPerpOrders() {
    return cancelAllPerpOrders(authority, user);
  }

  @Override
  public Instruction cancelAllPerpOrders(final PublicKey authority, final PublicKey user) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        Perp,
        OptionalInt.empty(),
        null
    );
  }

  @Override
  public Instruction cancelAllOrders(final MarketConfig marketConfig) {
    return cancelAllOrders(authority, user, marketConfig);
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority,
                                     final PublicKey user,
                                     final MarketConfig marketConfig) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        marketConfig instanceof PerpMarketConfig ? Perp : Spot,
        OptionalInt.of(marketConfig.marketIndex()),
        null
    );
  }
}
