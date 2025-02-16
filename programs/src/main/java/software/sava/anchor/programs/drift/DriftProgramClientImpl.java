package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.*;
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
    this.quoteSpotMarketVaultAccountKey = deriveSpotMarketVaultAccount(
        accounts,
        accounts.defaultQuoteMarket().marketIndex()
    ).publicKey();
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
  public Instruction cancelAllOrders(final PublicKey authority,
                                     final PublicKey user,
                                     final PositionDirection direction) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        null,
        OptionalInt.empty(),
        direction
    );
  }

  @Override
  public Instruction cancelAllSpotOrders(final PublicKey authority,
                                         final PublicKey user,
                                         final PositionDirection direction) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        Spot,
        OptionalInt.empty(),
        direction
    );
  }

  @Override
  public Instruction cancelAllPerpOrders(final PublicKey authority,
                                         final PublicKey user,
                                         final PositionDirection direction) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        Perp,
        OptionalInt.empty(),
        direction
    );
  }

  @Override
  public Instruction cancelAllOrders(final PublicKey authority,
                                     final PublicKey user,
                                     final MarketConfig marketConfig,
                                     final PositionDirection direction) {
    return DriftProgram.cancelOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        marketConfig instanceof PerpMarketConfig ? Perp : Spot,
        OptionalInt.of(marketConfig.marketIndex()),
        direction
    );
  }

  @Override
  public Instruction modifyOrder(final PublicKey authority,
                                 final PublicKey user,
                                 final OptionalInt orderId,
                                 final ModifyOrderParams modifyOrderParams) {
    return DriftProgram.modifyOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderId,
        modifyOrderParams
    );
  }

  @Override
  public Instruction modifyOrderByUserId(final PublicKey authority,
                                         final PublicKey user,
                                         final int userOrderId,
                                         final ModifyOrderParams modifyOrderParams) {
    return DriftProgram.modifyOrderByUserId(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        userOrderId,
        modifyOrderParams
    );
  }


  @Override
  public Instruction placeAndTakePerpOrder(final PublicKey authority,
                                           final PublicKey user,
                                           final OrderParams params,
                                           final OptionalInt successCondition) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.placeAndTakePerpOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        userStatsPDA.publicKey(),
        authority,
        params,
        successCondition
    );
  }

  public Instruction initializeInsuranceFundStake(final SpotMarketConfig spotMarketConfig,
                                                  final PublicKey insuranceFundStakeKey,
                                                  final PublicKey payerKey,
                                                  final PublicKey systemProgramKey,
                                                  final PublicKey rentSysvar) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.initializeInsuranceFundStake(
        accounts.invokedDriftProgram(),
        spotMarketConfig.vaultPDA(),
        insuranceFundStakeKey,
        userStatsPDA.publicKey(),
        accounts.stateKey(),
        authority,
        payerKey,
        rentSysvar,
        systemProgramKey,
        spotMarketConfig.marketIndex()
    );
  }

  public Instruction addInsuranceFundStake(final SpotMarketConfig spotMarketConfig,
                                           final PublicKey insuranceFundStakeKey,
                                           final PublicKey insuranceFundVaultKey,
                                           final PublicKey userTokenAccountKey,
                                           final PublicKey tokenProgramKey,
                                           final long amount) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.addInsuranceFundStake(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        spotMarketConfig.vaultPDA(),
        insuranceFundStakeKey,
        userStatsPDA.publicKey(),
        authority,
        spotMarketConfig.vaultPDA(),
        insuranceFundVaultKey,
        accounts.driftSignerPDA(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig.marketIndex(),
        amount
    );
  }

  public Instruction requestRemoveInsuranceFundStake(final SpotMarketConfig spotMarketConfig,
                                                     final PublicKey insuranceFundStakeKey,
                                                     final PublicKey insuranceFundVaultKey,
                                                     final long amount) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.requestRemoveInsuranceFundStake(
        accounts.invokedDriftProgram(),
        spotMarketConfig.vaultPDA(),
        insuranceFundStakeKey,
        userStatsPDA.publicKey(),
        authority,
        insuranceFundVaultKey,
        spotMarketConfig.marketIndex(),
        amount
    );
  }

  public Instruction cancelRequestRemoveInsuranceFundStake(final SpotMarketConfig spotMarketConfig,
                                                           final PublicKey insuranceFundStakeKey,
                                                           final PublicKey insuranceFundVaultKey) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.cancelRequestRemoveInsuranceFundStake(
        accounts.invokedDriftProgram(),
        spotMarketConfig.vaultPDA(),
        insuranceFundStakeKey,
        userStatsPDA.publicKey(),
        authority,
        insuranceFundVaultKey,
        spotMarketConfig.marketIndex()
    );
  }

  public Instruction removeInsuranceFundStake(final SpotMarketConfig spotMarketConfig,
                                              final PublicKey insuranceFundStakeKey,
                                              final PublicKey insuranceFundVaultKey,
                                              final PublicKey userTokenAccountKey,
                                              final PublicKey tokenProgramKey) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.removeInsuranceFundStake(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        spotMarketConfig.vaultPDA(),
        insuranceFundStakeKey,
        userStatsPDA.publicKey(),
        authority,
        insuranceFundVaultKey,
        accounts.driftSignerPDA(),
        userTokenAccountKey,
        tokenProgramKey,
        spotMarketConfig.marketIndex()
    );
  }

  public Instruction enableUserHighLeverageMode(final PublicKey highLeverageModeConfigKey, final int subAccountId) {
    return DriftProgram.enableUserHighLeverageMode(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        highLeverageModeConfigKey,
        subAccountId
    );
  }

  public Instruction disableUserHighLeverageMode(final PublicKey highLeverageModeConfigKey) {
    return DriftProgram.disableUserHighLeverageMode(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        authority,
        user,
        highLeverageModeConfigKey
    );
  }

  public Instruction reclaimRent(final PublicKey rentKey) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.reclaimRent(
        accounts.invokedDriftProgram(),
        user,
        userStatsPDA.publicKey(),
        accounts.stateKey(),
        authority,
        rentKey
    );
  }

  public Instruction deleteUser(final PublicKey userKey, final PublicKey userStatsKey, final PublicKey stateKey) {
    return DriftProgram.deleteUser(
        accounts.invokedDriftProgram(),
        userKey,
        userStatsKey,
        stateKey,
        authority
    );
  }

  public Instruction forceDeleteUser(final PublicKey userKey,
                                     final PublicKey userStatsKey,
                                     final PublicKey stateKey,
                                     final PublicKey keeperKey) {
    return DriftProgram.forceDeleteUser(
        accounts.invokedDriftProgram(),
        userKey,
        userStatsKey,
        stateKey,
        authority,
        keeperKey,
        accounts.driftSignerPDA()
    );
  }

  public Instruction deleteSwiftUserOrders(final PublicKey swiftUserOrdersKey,
                                           final PublicKey stateKey) {
    return DriftProgram.deleteSwiftUserOrders(
        accounts.invokedDriftProgram(),
        swiftUserOrdersKey,
        stateKey,
        authority
    );
  }

  public Instruction updateUserPoolId(final int subAccountId, final int poolId) {
    return DriftProgram.updateUserPoolId(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        poolId
    );
  }

  public Instruction updateUserName(final int subAccountId, final byte[] name) {
    return DriftProgram.updateUserName(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        name
    );
  }

  public Instruction updateUserCustomMarginRatio(final int subAccountId, final int marginRatio) {
    return DriftProgram.updateUserCustomMarginRatio(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        marginRatio
    );
  }

  public Instruction updateUserMarginTradingEnabled(final int subAccountId, final boolean marginTradingEnabled) {
    return DriftProgram.updateUserMarginTradingEnabled(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        marginTradingEnabled
    );
  }

  public Instruction updateUserDelegate(final int subAccountId, final PublicKey delegate) {
    return DriftProgram.updateUserDelegate(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        delegate
    );
  }

  public Instruction updateUserReduceOnly(final int subAccountId, final boolean reduceOnly) {
    return DriftProgram.updateUserReduceOnly(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        reduceOnly
    );
  }

  public Instruction updateUserAdvancedLp(final int subAccountId, final boolean advancedLp) {
    return DriftProgram.updateUserAdvancedLp(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        advancedLp
    );
  }

  public Instruction updateUserProtectedMakerOrders(final PublicKey protectedMakerModeConfigKey,
                                                    final int subAccountId,
                                                    final boolean protectedMakerOrders) {
    return DriftProgram.updateUserProtectedMakerOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        protectedMakerModeConfigKey,
        subAccountId,
        protectedMakerOrders
    );
  }

  public Instruction removePerpLpShares(final PublicKey userKey,
                                        final PublicKey authorityKey,
                                        final long sharesToBurn,
                                        final int marketIndex) {
    return DriftProgram.removePerpLpShares(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        userKey,
        authorityKey,
        sharesToBurn,
        marketIndex
    );
  }

  public Instruction removePerpLpSharesInExpiringMarket(final PublicKey userKey,
                                                        final long sharesToBurn,
                                                        final int marketIndex) {
    return DriftProgram.removePerpLpSharesInExpiringMarket(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        userKey,
        sharesToBurn,
        marketIndex
    );
  }

  public Instruction addPerpLpShares(final PublicKey userKey,
                                     final PublicKey authorityKey,
                                     final long nShares,
                                     final int marketIndex) {
    return DriftProgram.addPerpLpShares(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        userKey,
        authorityKey,
        nShares,
        marketIndex
    );
  }
}
