package software.sava.anchor.program.examples;

import software.sava.anchor.programs.pump.anchor.PumpProgram;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.rpc.json.http.SolanaNetwork;
import software.sava.rpc.json.http.client.SolanaRpcClient;

import java.net.http.HttpClient;
import java.util.Arrays;

public final class HelloPumpDotFun {

  public static void main(final String[] args) {
    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcClient = SolanaRpcClient.createClient(SolanaNetwork.MAIN_NET.getEndpoint(), httpClient);
      // Create Mint Transaction.
      final var sig = "3c7h7tvqP88mUQjzdvJ6m9Y3Ppr6XviYpWXZXgcuLqtt1rhgJVnPk1B15ZSfiUusXSniWZEhfP9otNsUuCi168XV";
      final var tx = rpcClient.getTransaction(sig).join();
      final var skeleton = TransactionSkeleton.deserializeSkeleton(tx.data());
      final var instructions = skeleton.parseLegacyInstructions();
      final var createIx = Arrays.stream(instructions)
          .filter(PumpProgram.CREATE_DISCRIMINATOR)
          .findFirst().orElseThrow();
      final var ixData = PumpProgram.CreateIxData.read(createIx);
      System.out.println(ixData);


      final var accounts = createIx.accounts();
      System.out.format("""
              New pump.fun token:
               * name: %s
               * symbol: %s
               * uri: %s
               * creator: %s
               * mint: %s
              """,
          ixData.name(), ixData.symbol(), ixData.uri(),
          accounts.get(7).publicKey(),
          tx.meta().postTokenBalances().getFirst().mint()
      );
    }
  }

  private HelloPumpDotFun() {
  }
}
