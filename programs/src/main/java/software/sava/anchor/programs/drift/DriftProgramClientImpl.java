package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.concurrent.CompletableFuture;

final class DriftProgramClientImpl implements DriftProgramClient {

  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts accounts;
  private final PublicKey authority;
  private final PublicKey user;

  DriftProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                         final DriftAccounts accounts) {
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.accounts = accounts;
    this.authority = nativeProgramAccountClient.ownerPublicKey();
    this.user = DriftPDAs.deriveMainUserAccount(accounts, authority).publicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public DriftAccounts accounts() {
    return accounts;
  }

  @Override
  public PublicKey authority() {
    return authority;
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams) {
    return placePerpOrder(orderParams, authority, user);
  }

  @Override
  public Instruction placePerpOrder(final OrderParams orderParams,
                                    final PublicKey authority,
                                    final PublicKey user) {
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
  public Instruction placeOrders(final OrderParams[] orderParams,
                                 final PublicKey authority,
                                 final PublicKey user) {
    return DriftProgram.placeOrders(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        user,
        authority,
        orderParams
    );
  }

  @Override
  public CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient) {
    return fetchUsersByAuthority(rpcClient, authority);
  }

  @Override
  public CompletableFuture<List<AccountInfo<User>>> fetchUsersByAuthority(final SolanaRpcClient rpcClient,
                                                                          final PublicKey authority) {
    return rpcClient.getProgramAccounts(
        accounts.driftProgram(),
        List.of(
            User.SIZE_FILTER,
            User.createAuthorityFilter(authority)
        ),
        User.FACTORY
    );
  }
}
