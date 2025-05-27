package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.*;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.concurrent.CompletableFuture;

public interface DriftProgramClient {

  BigInteger FUNDING_RATE_BUFFER = BigInteger.valueOf(10).pow(3);
  BigDecimal FUNDING_RATE_BUFFER_BD = new BigDecimal(FUNDING_RATE_BUFFER);

  static DriftProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient,
                                         final DriftAccounts accounts) {
    return new DriftProgramClientImpl(nativeProgramAccountClient, accounts);
  }

  static DriftProgramClient createClient(final NativeProgramAccountClient nativeProgramAccountClient) {
    return createClient(nativeProgramAccountClient, DriftAccounts.MAIN_NET);
  }

  SolanaAccounts solanaAccounts();

  DriftAccounts driftAccounts();

  @Deprecated
  default CompletableFuture<AccountInfo<AddressLookupTable>> fetchMarketsLookupTable(final SolanaRpcClient rpcClient) {
    return rpcClient.getAccountInfo(driftAccounts().marketLookupTable(), AddressLookupTable.FACTORY);
  }

  default CompletableFuture<List<AccountInfo<AddressLookupTable>>> fetchMarketLookupTables(final SolanaRpcClient rpcClient) {
    return rpcClient.getAccounts(driftAccounts().marketLookupTables(), AddressLookupTable.FACTORY);
  }

  PublicKey mainUserAccount();

  PublicKey authority();

  PublicKey feePayer();

  default ProgramDerivedAddress deriveUserAccount() {
    return deriveUserAccount(0);
  }

  default ProgramDerivedAddress deriveUserAccount(final int subAccountId) {
    return deriveUserAccount(authority(), subAccountId);
  }

  default ProgramDerivedAddress deriveUserAccount(final PublicKey authority, final int subAccountId) {
    return DriftPDAs.deriveUserAccount(driftAccounts(), authority, subAccountId);
  }

  default ProgramDerivedAddress deriveSpotMarketAccount(final int marketIndex) {
    return DriftPDAs.deriveSpotMarketAccount(driftAccounts(), marketIndex);
  }

  default ProgramDerivedAddress derivePerpMarketAccount(final int marketIndex) {
    return DriftPDAs.derivePerpMarketAccount(driftAccounts(), marketIndex);
  }

  default CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient) {
    return fetchUser(rpcClient, mainUserAccount());
  }

  default CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient, final User user) {
    return rpcClient.getAccountInfo(user._address(), User.FACTORY);
  }

  default CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient, final PublicKey user) {
    return rpcClient.getAccountInfo(user, User.FACTORY);
  }

  default CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient) {
    return fetchUsersByAuthority(rpcClient, authority());
  }

  default CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient,
                                                                           final PublicKey authority) {
    return rpcClient.getProgramAccounts(
        driftAccounts().driftProgram(),
        List.of(
            User.SIZE_FILTER,
            User.createAuthorityFilter(authority)
        ),
        User.FACTORY
    );
  }

  Instruction initializeUser(final PublicKey user,
                             final PublicKey authority,
                             final PublicKey payerKey,
                             final int subAccountId,
                             final String name);

  default Instruction initializeUser(final PublicKey payerKey,
                                     final int subAccountId,
                                     final String name) {
    return initializeUser(mainUserAccount(), authority(), payerKey, subAccountId, name);
  }

  default Instruction initializeUser(final PublicKey user,
                                     final PublicKey authority,
                                     final PublicKey payerKey,
                                     final int subAccountId,
                                     final String name,
                                     final PublicKey referrer,
                                     final PublicKey referrerStats) {
    final var initUserIx = initializeUser(user, authority, payerKey, subAccountId, name);
    return initUserIx.extraAccounts(List.of(
        AccountMeta.createWrite(referrer),
        AccountMeta.createWrite(referrerStats)
    ));
  }

  default Instruction initializeUser(final PublicKey payerKey,
                                     final int subAccountId,
                                     final String name,
                                     final PublicKey referrer,
                                     final PublicKey referrerStats) {
    return initializeUser(mainUserAccount(), authority(), payerKey, subAccountId, name, referrer, referrerStats);
  }

  Instruction initializeUserStats(final PublicKey authority, final PublicKey payerKey);

  default Instruction initializeUserStats(final PublicKey payerKey) {
    return initializeUserStats(authority(), payerKey);
  }

  default Instruction deposit(final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount) {
    return deposit(mainUserAccount(), authority(), userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount);
  }

  Instruction deposit(final PublicKey user,
                      final PublicKey authority,
                      final PublicKey userTokenAccountKey,
                      final PublicKey tokenProgramKey,
                      final SpotMarketConfig spotMarketConfig,
                      final long amount,
                      final boolean reduceOnly);

  default Instruction deposit(final PublicKey user,
                              final PublicKey authority,
                              final PublicKey userTokenAccountKey,
                              final PublicKey tokenProgramKey,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount) {
    return deposit(user, authority, userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount, false);
  }

  Instruction transferDeposit(final PublicKey fromUser,
                              final PublicKey toUser,
                              final PublicKey authority,
                              final SpotMarketConfig spotMarketConfig,
                              final long amount);

  default Instruction withdraw(final PublicKey userTokenAccountKey,
                               final PublicKey tokenProgramKey,
                               final SpotMarketConfig spotMarketConfig,
                               final long amount) {
    return withdraw(mainUserAccount(), authority(), userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount);
  }

  Instruction withdraw(final PublicKey user,
                       final PublicKey authority,
                       final PublicKey userTokenAccountKey,
                       final PublicKey tokenProgramKey,
                       final SpotMarketConfig spotMarketConfig,
                       final long amount,
                       final boolean reduceOnly);

  default Instruction withdraw(final PublicKey user,
                               final PublicKey authority,
                               final PublicKey userTokenAccountKey,
                               final PublicKey tokenProgramKey,
                               final SpotMarketConfig spotMarketConfig,
                               final long amount) {
    return withdraw(user, authority, userTokenAccountKey, tokenProgramKey, spotMarketConfig, amount, false);
  }

  default Instruction settlePnl(final int marketIndex) {
    return settlePnl(mainUserAccount(), authority(), marketIndex);
  }

  Instruction transferPools(final PublicKey authority,
                            final PublicKey fromUser,
                            final PublicKey toUser,
                            final SpotMarketConfig depositFromSpotMarketConfig,
                            final SpotMarketConfig depositToSpotMarketConfig,
                            final SpotMarketConfig borrowFromSpotMarketConfig,
                            final SpotMarketConfig borrowToSpotMarketConfig,
                            final OptionalLong depositAmount,
                            final OptionalLong borrowAmount);

  Instruction transferPerpPosition(final PublicKey authority,
                                   final PublicKey fromUser,
                                   final PublicKey toUser,
                                   final PerpMarketConfig perpMarketConfig,
                                   final OptionalLong amount);

  default Instruction transferPerpPosition(PublicKey fromUser,
                                           PublicKey toUser,
                                           PerpMarketConfig perpMarketConfig,
                                           OptionalLong amount) {
    return transferPerpPosition(authority(), fromUser, toUser, perpMarketConfig, amount);
  }

  Instruction settlePnl(final PublicKey user,
                        final PublicKey authority,
                        final int marketIndex);

  default Instruction settlePnl(short[] marketIndexes, SettlePnlMode mode) {
    return settlePnl(mainUserAccount(), authority(), marketIndexes, mode);
  }

  Instruction settlePnl(final PublicKey user,
                        final PublicKey authority,
                        final short[] marketIndexes,
                        final SettlePnlMode mode);

  default Instruction settleFundingPayment() {
    return settleFundingPayment(mainUserAccount());
  }

  default Instruction settleFundingPayment(final PublicKey user) {
    final var accounts = driftAccounts();
    return DriftProgram.settleFundingPayment(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user
    );
  }

  default Instruction placeOrder(final OrderParams orderParams) {
    return placeOrder(orderParams, authority(), mainUserAccount());
  }

  default Instruction placeOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user) {
    return switch (orderParams.marketType()) {
      case Spot -> placeSpotOrder(orderParams, authority, user);
      case Perp -> placePerpOrder(orderParams, authority, user);
    };
  }

  default Instruction placeSpotOrder(final OrderParams orderParams) {
    return placeSpotOrder(orderParams, authority(), mainUserAccount());
  }

  Instruction placeSpotOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user);

  default Instruction placePerpOrder(final OrderParams orderParams) {
    return placePerpOrder(orderParams, authority(), mainUserAccount());
  }

  default Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority) {
    return placePerpOrder(
        orderParams,
        authority,
        DriftPDAs.deriveMainUserAccount(driftAccounts(), authority).publicKey()
    );
  }

  Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user);

  default Instruction placeOrders(final OrderParams[] orderParams) {
    return placeOrders(orderParams, authority(), mainUserAccount());
  }

  Instruction placeOrders(final OrderParams[] orderParams, final PublicKey authority, final PublicKey user);

  default Instruction cancelOrder(int orderId) {
    return cancelOrder(authority(), mainUserAccount(), orderId);
  }

  default Instruction cancelOrder(final int orderId, final PublicKey authority) {
    return cancelOrder(authority, DriftPDAs.deriveMainUserAccount(driftAccounts(), authority).publicKey(), orderId);
  }

  Instruction cancelOrder(final PublicKey authority, final PublicKey user, final int orderId);

  default Instruction cancelOrders(final int[] orderIds) {
    return cancelOrders(authority(), mainUserAccount(), orderIds);
  }

  Instruction cancelOrders(final PublicKey authority, final PublicKey user, final int[] orderIds);

  default Instruction cancelOrderByUserOrderId(final int orderId) {
    return cancelOrderByUserOrderId(authority(), mainUserAccount(), orderId);
  }

  Instruction cancelOrderByUserOrderId(final PublicKey authority, final PublicKey user, final int orderId);

  default Instruction cancelAllOrders() {
    return cancelAllOrders(authority(), mainUserAccount());
  }

  default Instruction cancelAllOrders(final PublicKey authority, final PublicKey user) {
    return cancelAllOrders(
        authority,
        user,
        (PositionDirection) null
    );
  }

  Instruction cancelAllOrders(final PublicKey authority,
                              final PublicKey user,
                              final PositionDirection direction);

  default Instruction cancelAllOrders(final MarketConfig marketConfig) {
    return cancelAllOrders(marketConfig, null);
  }

  default Instruction cancelAllOrders(final MarketConfig marketConfig, final PositionDirection direction) {
    return cancelAllOrders(authority(), mainUserAccount(), marketConfig, direction);
  }

  default Instruction cancelAllOrders(final PublicKey authority,
                                      final PublicKey user,
                                      final MarketConfig marketConfig) {
    return cancelAllOrders(
        authority,
        user,
        marketConfig,
        null
    );
  }

  Instruction cancelAllOrders(final PublicKey authority,
                              final PublicKey user,
                              final MarketConfig marketConfig,
                              final PositionDirection direction);

  default Instruction cancelAllSpotOrders() {
    return cancelAllSpotOrders(null);
  }

  default Instruction cancelAllSpotOrders(final PositionDirection direction) {
    return cancelAllSpotOrders(authority(), mainUserAccount(), direction);
  }

  default Instruction cancelAllSpotOrders(final PublicKey authority, final PublicKey user) {
    return cancelAllSpotOrders(
        authority,
        user,
        null
    );
  }

  Instruction cancelAllSpotOrders(final PublicKey authority,
                                  final PublicKey user,
                                  final PositionDirection direction);

  default Instruction cancelAllPerpOrders() {
    return cancelAllPerpOrders(null);
  }

  default Instruction cancelAllPerpOrders(final PositionDirection direction) {
    return cancelAllPerpOrders(authority(), mainUserAccount(), direction);
  }

  default Instruction cancelAllPerpOrders(final PublicKey authority, final PublicKey user) {
    return cancelAllPerpOrders(
        authority,
        user,
        null
    );
  }

  Instruction cancelAllPerpOrders(final PublicKey authority,
                                  final PublicKey user,
                                  final PositionDirection direction);

  Instruction modifyOrder(final PublicKey authority,
                          final PublicKey user,
                          final OptionalInt orderId,
                          final ModifyOrderParams modifyOrderParams);

  Instruction modifyOrderByUserId(final PublicKey authority,
                                  final PublicKey user,
                                  final int userOrderId,
                                  final ModifyOrderParams modifyOrderParams);

  Instruction placeAndTakePerpOrder(final PublicKey authority,
                                    final PublicKey user,
                                    final OrderParams params,
                                    final OptionalInt successCondition);

  Instruction initializeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                            final PublicKey authorityKey,
                                            final PublicKey payerKey,
                                            final int numOrders);

  default Instruction initializeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey, final int numOrders) {
    return initializeSignedMsgUserOrders(signedMsgUserOrdersKey, authority(), feePayer(), numOrders);
  }

  Instruction resizeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey,
                                        final PublicKey authorityKey,
                                        final PublicKey userKey,
                                        final PublicKey payerKey,
                                        final int numOrders);

  default Instruction resizeSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey, final int numOrders) {
    return resizeSignedMsgUserOrders(signedMsgUserOrdersKey, authority(), mainUserAccount(), feePayer(), numOrders);
  }

  Instruction initializeSignedMsgWsDelegates(final PublicKey signedMsgUserOrdersKey,
                                             final PublicKey authorityKey,
                                             final PublicKey[] delegates);

  default Instruction initializeSignedMsgWsDelegates(final PublicKey signedMsgUserOrdersKey,
                                                     final PublicKey[] delegates) {
    return initializeSignedMsgWsDelegates(signedMsgUserOrdersKey, authority(), delegates);
  }

  Instruction changeSignedMsgWsDelegateStatus(final PublicKey signedMsgUserOrdersKey,
                                              final PublicKey authorityKey,
                                              final PublicKey delegate,
                                              final boolean add);

  default Instruction changeSignedMsgWsDelegateStatus(final PublicKey signedMsgUserOrdersKey,
                                                      final PublicKey delegate,
                                                      final boolean add) {
    return changeSignedMsgWsDelegateStatus(signedMsgUserOrdersKey, authority(), delegate, add);
  }

  Instruction placeAndMakeSignedMsgPerpOrder(final PublicKey userKey,
                                             final PublicKey takerKey,
                                             final PublicKey takerStatsKey,
                                             final PublicKey takerSignedMsgUserOrdersKey,
                                             final PublicKey authorityKey,
                                             final OrderParams params,
                                             final byte[] signedMsgOrderUuid);

  default Instruction placeAndMakeSignedMsgPerpOrder(final PublicKey takerKey,
                                                     final PublicKey takerStatsKey,
                                                     final PublicKey takerSignedMsgUserOrdersKey,
                                                     final OrderParams params,
                                                     final byte[] signedMsgOrderUuid) {
    return placeAndMakeSignedMsgPerpOrder(
        mainUserAccount(),
        takerKey,
        takerStatsKey,
        takerSignedMsgUserOrdersKey,
        authority(),
        params,
        signedMsgOrderUuid
    );
  }

  Instruction placeSignedMsgTakerOrder(final PublicKey userKey,
                                       final PublicKey signedMsgUserOrdersKey,
                                       final PublicKey authorityKey,
                                       final byte[] signedMsgOrderParamsMessageBytes,
                                       final boolean isDelegateSigner);

  default Instruction placeSignedMsgTakerOrder(final PublicKey signedMsgUserOrdersKey,
                                               final byte[] signedMsgOrderParamsMessageBytes,
                                               final boolean isDelegateSigner) {
    return placeSignedMsgTakerOrder(
        mainUserAccount(),
        signedMsgUserOrdersKey,
        authority(),
        signedMsgOrderParamsMessageBytes,
        isDelegateSigner
    );
  }

  Instruction deleteSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey, final PublicKey authorityKey);

  default Instruction deleteSignedMsgUserOrders(final PublicKey signedMsgUserOrdersKey) {
    return deleteSignedMsgUserOrders(signedMsgUserOrdersKey, authority());
  }
}
