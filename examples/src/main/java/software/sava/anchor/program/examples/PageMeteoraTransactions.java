package software.sava.anchor.program.examples;

import software.sava.anchor.programs.meteora.dlmm.anchor.LbClmmProgram;
import software.sava.core.accounts.PublicKey;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.rpc.json.http.client.SolanaRpcClient;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Base64;

import static software.sava.rpc.json.http.SolanaNetwork.MAIN_NET;

public final class PageMeteoraTransactions {

  public static void main(final String[] args) throws InterruptedException {
    final var meteoraProgramId = PublicKey.fromBase58Encoded("LBUZKhRxPF3XUpBCjp4YzTKgLccjZhTSDM9YuVaPwxo");

    try (final var httpClient = HttpClient.newHttpClient()) {
      final var rpcEndpoint = MAIN_NET.getEndpoint();
      final var rpcClient = SolanaRpcClient.createClient(rpcEndpoint, httpClient);

      final int pageLimit = 100;
      long beginRequest = System.currentTimeMillis();
      var signaturesFuture = rpcClient.getSignaturesForAddress(
          meteoraProgramId, pageLimit
      );

      for (long minMillisBetweenRequests = 5_000; ; ) {
        final var signatures = signaturesFuture.join();
        System.out.format("Fetched %d signatures%n", signatures.size());

        if (signatures.isEmpty()) {
          break;
        }

        for (final var txSig : signatures) {
          final var signature = txSig.signature();
          System.out.format("""
              ------------------------------------------------------------------------------------
              %s
              
              """, signature);

          delay(minMillisBetweenRequests, beginRequest);
          beginRequest = System.currentTimeMillis();
          final var transactionFuture = rpcClient.getTransaction(signature);
          final var transaction = transactionFuture.join();
          final var txData = transaction.data();

          try {
            final var skeleton = TransactionSkeleton.deserializeSkeleton(txData);

            final var instructions = skeleton.isLegacy()
                ? skeleton.parseLegacyInstructions()
                : skeleton.parseInstructionsWithoutAccounts();

            final var addLiquidityIxParams = Arrays.stream(instructions)
                .filter(LbClmmProgram.ADD_LIQUIDITY_BY_STRATEGY_DISCRIMINATOR)
                .map(LbClmmProgram.AddLiquidityByStrategyIxData::read)
                .map(LbClmmProgram.AddLiquidityByStrategyIxData::liquidityParameter)
                .toList();
            for (final var addLiquidityIxParam : addLiquidityIxParams) {
              System.out.format("""
                      Found add-liquidity-by-strategy with the following params on block %d:
                      AmountX: %d
                      AmountY: %d
                      ActiveId: %d
                      MaxActiveBinSlippage: %d
                      MinBinId: %d
                      MaxBinId: %d
                      StrategyType: %s
                      Transaction: %s
                      
                      """,
                  transaction.blockTime().orElse(-1),
                  addLiquidityIxParam.amountX(),
                  addLiquidityIxParam.amountY(),
                  addLiquidityIxParam.activeId(),
                  addLiquidityIxParam.maxActiveBinSlippage(),
                  addLiquidityIxParam.strategyParameters().minBinId(),
                  addLiquidityIxParam.strategyParameters().maxBinId(),
                  addLiquidityIxParam.strategyParameters().strategyType(),
                  transaction);
            }
            System.out.format("Contained %d add liquidity instructions.%n", addLiquidityIxParams.size());
          } catch (final RuntimeException ex) {
            System.out.println(Base64.getEncoder().encodeToString(txData));
            throw ex;
          }
        }

        delay(minMillisBetweenRequests, beginRequest);
        beginRequest = System.currentTimeMillis();
        signaturesFuture = rpcClient.getSignaturesForAddressBefore(
            meteoraProgramId, pageLimit, signatures.getLast().signature()
        );
      }
    }
  }

  private static void delay(final long minMillisBetweenRequests, final long beginRequest) throws InterruptedException {
    final long millisBetweenRequests = System.currentTimeMillis() - beginRequest;
    final long sleepMillis = minMillisBetweenRequests - millisBetweenRequests;
    if (sleepMillis > 0) {
      Thread.sleep(sleepMillis);
    }
  }

  private PageMeteoraTransactions() {
  }
}
