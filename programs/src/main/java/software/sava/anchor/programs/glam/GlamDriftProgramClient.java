package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.drift.DriftAccounts;
import software.sava.anchor.programs.drift.DriftProgramClient;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.util.concurrent.CompletableFuture;

public interface GlamDriftProgramClient extends DriftProgramClient {

  static GlamDriftProgramClient createClient(final GlamProgramAccountClient glamClient,
                                             final DriftAccounts marinadeAccounts) {
    return new GlamDriftProgramClientImpl(glamClient, marinadeAccounts);
  }

  static GlamDriftProgramClient createClient(final GlamProgramAccountClient glamClient) {
    return createClient(glamClient, DriftAccounts.MAIN_NET);
  }

  default CompletableFuture<AccountInfo<AddressLookupTable>> fetchMarketsLookupTable(final SolanaRpcClient rpcClient) {
    return rpcClient.getAccountInfo(driftAccounts().marketLookupTable(), AddressLookupTable.FACTORY);
  }
}
