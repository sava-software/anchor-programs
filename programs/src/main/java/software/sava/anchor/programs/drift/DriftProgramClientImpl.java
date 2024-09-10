package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.anchor.programs.drift.anchor.types.User;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.util.List;
import java.util.concurrent.Executors;

final class DriftProgramClientImpl implements DriftProgramClient {

  private final NativeProgramAccountClient nativeProgramAccountClient;
  private final SolanaAccounts solanaAccounts;
  private final DriftAccounts accounts;
  private final PublicKey authority;

  DriftProgramClientImpl(final NativeProgramAccountClient nativeProgramAccountClient,
                         final DriftAccounts accounts) {
    this.nativeProgramAccountClient = nativeProgramAccountClient;
    this.solanaAccounts = nativeProgramAccountClient.solanaAccounts();
    this.accounts = accounts;
    this.authority = nativeProgramAccountClient.ownerPublicKey();
  }

  @Override
  public SolanaAccounts solanaAccounts() {
    return solanaAccounts;
  }

  @Override
  public DriftAccounts accounts() {
    return accounts;
  }

  public Instruction placePerpOrder(final OrderParams orderParams) {
    return DriftProgram.placePerpOrder(
        accounts.invokedDriftProgram(),
        accounts.stateKey(),
        null,
        authority,
        orderParams
    );
  }

  public static void main(final String[] args) {
    try (final var executor = Executors.newVirtualThreadPerTaskExecutor()) {
      try (final var httpClient = HttpClient.newBuilder()
          .executor(executor)
          .proxy(HttpClient.Builder.NO_PROXY)
          .build()) {
        final var rpcClient = SolanaRpcClient.createClient(URI.create("https://solana-mainnet.rpc.extrnode.com/"), httpClient);
        final var user = rpcClient.getProgramAccounts(
            DriftAccounts.MAIN_NET.invokedDriftProgram().publicKey(),
            List.of(
                User.createAuthorityFilter(PublicKey.fromBase58Encoded(""))
            ),
            User.FACTORY
        ).join();
        System.out.println(user);
      }
    }
  }
}
