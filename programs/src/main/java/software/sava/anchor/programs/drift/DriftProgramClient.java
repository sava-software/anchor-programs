package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
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

  DriftAccounts accounts();

  DriftExtraAccounts extraAccounts();

  PerpMarketConfig perpMarket(final DriftProduct product);

  PublicKey authority();

  default ProgramDerivedAddress deriveUserAccount() {
    return deriveUserAccount(0);
  }

  default ProgramDerivedAddress deriveUserAccount(final int subAccountId) {
    return deriveUserAccount(authority(), subAccountId);
  }

  default ProgramDerivedAddress deriveUserAccount(final PublicKey authority, final int subAccountId) {
    return DriftPDAs.deriveUserAccount(accounts(), authority, subAccountId);
  }

  default ProgramDerivedAddress deriveSpotMarketAccount(final int marketIndex) {
    return DriftPDAs.deriveSpotMarketAccount(accounts(), marketIndex);
  }

  default ProgramDerivedAddress derivePerpMarketAccount(final int marketIndex) {
    return DriftPDAs.derivePerpMarketAccount(accounts(), marketIndex);
  }

  CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient);

  default CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient, final User user) {
    return rpcClient.getAccountInfo(user._address(), User.FACTORY);
  }

  default CompletableFuture<AccountInfo<User>> fetchUser(final SolanaRpcClient rpcClient, final PublicKey user) {
    return rpcClient.getAccountInfo(user, User.FACTORY);
  }

  CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient);

  CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient,
                                                                   final PublicKey authority);

  Instruction placeOrder(final OrderParams orderParams);

  Instruction placeOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user);

  Instruction placePerpOrder(final OrderParams orderParams);

  default Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority) {
    return placePerpOrder(orderParams, authority, DriftPDAs.deriveMainUserAccount(accounts(), authority).publicKey());
  }

  Instruction placePerpOrder(final OrderParams orderParams, final PublicKey authority, final PublicKey user);

  Instruction placeOrders(final OrderParams[] orderParams);

  Instruction placeOrders(final OrderParams[] orderParams, final PublicKey authority, final PublicKey user);

  Instruction cancelOrder(final int orderId);

  default Instruction cancelOrder(final int orderId, final PublicKey authority) {
    return cancelOrder(authority, DriftPDAs.deriveMainUserAccount(accounts(), authority).publicKey(), orderId);
  }

  Instruction cancelOrder(final PublicKey authority, final PublicKey user, final int orderId);

  Instruction cancelOrders(final int[] orderIds);

  Instruction cancelOrders(final PublicKey authority, final PublicKey user, final int[] orderIds);

  Instruction cancelOrderByUserOrderId(final int orderId);

  Instruction cancelOrderByUserOrderId(final PublicKey authority, final PublicKey user, final int orderId);

  Instruction cancelAllOrders();

  Instruction cancelAllOrders(final PublicKey authority, final PublicKey user);

  Instruction cancelAllSpotOrders();

  Instruction cancelAllSpotOrders(final PublicKey authority, final PublicKey user);

  Instruction cancelAllPerpOrders();

  Instruction cancelAllPerpOrders(final PublicKey authority, final PublicKey user);

  Instruction cancelAllOrders(final MarketConfig marketConfig);

  Instruction cancelAllOrders(final PublicKey authority, final PublicKey user, final MarketConfig marketConfig);
}
