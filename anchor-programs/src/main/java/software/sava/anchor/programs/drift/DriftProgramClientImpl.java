package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.ModifyOrderParams;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.PositionDirection;
import software.sava.anchor.programs.drift.anchor.types.SettlePnlMode;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.OptionalInt;
import java.util.OptionalLong;

import static software.sava.anchor.programs.drift.DriftPDAs.deriveSpotMarketVaultAccount;
import static software.sava.anchor.programs.drift.DriftPDAs.deriveUserStatsAccount;
import static software.sava.anchor.programs.drift.anchor.types.MarketType.Perp;
import static software.sava.anchor.programs.drift.anchor.types.MarketType.Spot;

final class DriftProgramClientImpl implements DriftProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts accounts;
  private final PublicKey feePayer;
  private final PublicKey authority;
  private final PublicKey user;
  private final PublicKey quoteSpotMarketVaultAccountKey;

  DriftProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                         final DriftAccounts accounts) {
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.accounts = accounts;
    this.feePayer = nativeProgramAccountClient.feePayer().publicKey();
    this.authority = nativeProgramAccountClient.ownerPublicKey();
    this.user = DriftPDAs.deriveMainUserAccount(accounts, authority).publicKey();
    this.quoteSpotMarketVaultAccountKey = deriveSpotMarketVaultAccount(accounts, 0).publicKey();
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
  public PublicKey feePayer() {
    return feePayer;
  }

  @Override
  public Instruction initializeUser(final PublicKey user,
                                    final PublicKey authority,
                                    final PublicKey payerKey,
                                    final int subAccountId,
                                    final byte[] name) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.initializeUser(
        accounts.invokedDriftProgram(),
        user,
        userStatsPDA.publicKey(),
        accounts.stateKey(),
        authority,
        payerKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        subAccountId,
        name
    );
  }

  @Override
  public Instruction initializeUserStats(final PublicKey authority, final PublicKey payerKey) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.initializeUserStats(
        accounts.invokedDriftProgram(),
        userStatsPDA.publicKey(),
        accounts.stateKey(),
        authority,
        payerKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram()
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
  public Instruction transferPools(final PublicKey authority,
                                   final PublicKey fromUser,
                                   final PublicKey toUser,
                                   final SpotMarketConfig depositFromSpotMarketConfig,
                                   final SpotMarketConfig depositToSpotMarketConfig,
                                   final SpotMarketConfig borrowFromSpotMarketConfig,
                                   final SpotMarketConfig borrowToSpotMarketConfig,
                                   final OptionalLong depositAmount,
                                   final OptionalLong borrowAmount) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.transferPools(
        accounts.invokedDriftProgram(),
        fromUser,
        toUser,
        userStatsPDA.publicKey(),
        authority,
        accounts.stateKey(),
        depositFromSpotMarketConfig.vaultPDA(),
        depositToSpotMarketConfig.vaultPDA(),
        borrowFromSpotMarketConfig.vaultPDA(),
        borrowToSpotMarketConfig.vaultPDA(),
        accounts.driftSignerPDA(),
        depositFromSpotMarketConfig.marketIndex(),
        depositToSpotMarketConfig.marketIndex(),
        borrowFromSpotMarketConfig.marketIndex(),
        borrowToSpotMarketConfig.marketIndex(),
        depositAmount,
        borrowAmount
    );
  }

  @Override
  public Instruction transferPerpPosition(final PublicKey authority,
                                          final PublicKey fromUser,
                                          final PublicKey toUser,
                                          final PerpMarketConfig perpMarketConfig,
                                          final OptionalLong amount) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authority);
    return DriftProgram.transferPerpPosition(
        accounts.invokedDriftProgram(),
        fromUser,
        toUser,
        userStatsPDA.publicKey(),
        authority,
        accounts.stateKey(),
        perpMarketConfig.marketIndex(),
        amount
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
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

  @Override
  public Instruction disableUserHighLeverageMode(final PublicKey highLeverageModeConfigKey,
                                                 final boolean disableMaintenance) {
    return DriftProgram.disableUserHighLeverageMode(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        authority,
        user,
        highLeverageModeConfigKey,
        disableMaintenance
    );
  }

  @Override
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

  @Override
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

  @Override
  public Instruction updateUserPoolId(final int subAccountId, final int poolId) {
    return DriftProgram.updateUserPoolId(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        poolId
    );
  }

  @Override
  public Instruction updateUserName(final int subAccountId, final byte[] name) {
    return DriftProgram.updateUserName(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        name
    );
  }

  @Override
  public Instruction updateUserCustomMarginRatio(final int subAccountId, final int marginRatio) {
    return DriftProgram.updateUserCustomMarginRatio(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        marginRatio
    );
  }

  @Override
  public Instruction updateUserMarginTradingEnabled(final int subAccountId, final boolean marginTradingEnabled) {
    return DriftProgram.updateUserMarginTradingEnabled(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        marginTradingEnabled
    );
  }

  @Override
  public Instruction updateUserDelegate(final int subAccountId, final PublicKey delegate) {
    return DriftProgram.updateUserDelegate(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        delegate
    );
  }

  @Override
  public Instruction updateUserReduceOnly(final int subAccountId, final boolean reduceOnly) {
    return DriftProgram.updateUserReduceOnly(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        reduceOnly
    );
  }

  @Override
  public Instruction updateUserAdvancedLp(final int subAccountId, final boolean advancedLp) {
    return DriftProgram.updateUserAdvancedLp(
        accounts.invokedDriftProgram(),
        user,
        authority,
        subAccountId,
        advancedLp
    );
  }

  @Override
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

  @Override
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

  @Override
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

  @Override
  public Instruction initializeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                                   final PublicKey authorityKey,
                                                   final PublicKey payerKey,
                                                   final int numOrders) {
    return DriftProgram.initializeSignedMsgUserOrders(
        accounts.invokedDriftProgram(),
        signedMsgUserOrdersKey,
        authorityKey,
        payerKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        numOrders
    );
  }

  @Override
  public Instruction resizeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                               final PublicKey authorityKey,
                                               final PublicKey userKey,
                                               final PublicKey payerKey,
                                               final int numOrders) {
    return DriftProgram.resizeSignedMsgUserOrders(
        accounts.invokedDriftProgram(),
        signedMsgUserOrdersKey,
        authorityKey,
        userKey,
        payerKey,
        solanaAccounts.systemProgram(),
        numOrders
    );
  }

  @Override
  public Instruction initializeSignedMsgWsDelegates(final PublicKey signedMsgUserOrdersKey,
                                                    final PublicKey authorityKey,
                                                    final PublicKey[] delegates) {
    return DriftProgram.initializeSignedMsgWsDelegates(
        accounts.invokedDriftProgram(),
        signedMsgUserOrdersKey,
        authorityKey,
        solanaAccounts.rentSysVar(),
        solanaAccounts.systemProgram(),
        delegates
    );
  }

  @Override
  public Instruction changeSignedMsgWsDelegateStatus(final PublicKey signedMsgUserOrdersKey,
                                                     final PublicKey authorityKey,
                                                     final PublicKey delegate,
                                                     final boolean add) {
    return DriftProgram.changeSignedMsgWsDelegateStatus(
        accounts.invokedDriftProgram(),
        signedMsgUserOrdersKey,
        authorityKey,
        solanaAccounts.systemProgram(),
        delegate,
        add
    );
  }

  @Override
  public Instruction placeAndMakeSignedMsgPerpOrder(final PublicKey userKey,
                                                    final PublicKey takerKey,
                                                    final PublicKey takerStatsKey,
                                                    final PublicKey takerSignedMsgUserOrdersKey,
                                                    final PublicKey authorityKey,
                                                    final OrderParams params,
                                                    final byte[] signedMsgOrderUuid) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authorityKey).publicKey();
    return DriftProgram.placeAndMakeSignedMsgPerpOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        userKey,
        userStatsPDA,
        takerKey,
        takerStatsKey,
        takerSignedMsgUserOrdersKey,
        authorityKey,
        params,
        signedMsgOrderUuid
    );
  }

  @Override
  public Instruction placeSignedMsgTakerOrder(final PublicKey userKey,
                                              final PublicKey signedMsgUserOrdersKey,
                                              final PublicKey authorityKey,
                                              final byte[] signedMsgOrderParamsMessageBytes,
                                              final boolean isDelegateSigner) {
    final var userStatsPDA = deriveUserStatsAccount(accounts, authorityKey).publicKey();
    return DriftProgram.placeSignedMsgTakerOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        userKey,
        userStatsPDA,
        signedMsgUserOrdersKey,
        authorityKey,
        solanaAccounts.systemProgram(),
        signedMsgOrderParamsMessageBytes,
        isDelegateSigner
    );
  }

  @Override
  public Instruction deleteSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                               final PublicKey authorityKey) {
    return DriftProgram.deleteSignedMsgUserOrders(
        accounts.invokedDriftProgram(),
        signedMsgUserOrdersKey,
        accounts.stateKey(),
        authorityKey
    );
  }
}
