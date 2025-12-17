package software.sava.anchor.program.examples;

import software.sava.anchor.programs.drift.DynamicPerpMarkets;
import software.sava.anchor.programs.drift.DynamicSpotMarkets;
import software.sava.anchor.programs.drift.MarketConfig;
import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.MarketType;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.core.tx.Instruction;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import static software.sava.rpc.json.http.SolanaNetwork.MAIN_NET;

public final class ParseDriftPlaceOrdersTransaction {

  public static void main(final String[] args) {
    try (final var httpClient = HttpClient.newHttpClient()) {

      final var perpMarketsFuture = DynamicPerpMarkets.fetchMarkets(httpClient);
      final var spotMarketsFuture = DynamicSpotMarkets.fetchMarkets(httpClient);

      final var rpcClient = SolanaRpcClient.createClient(MAIN_NET.getEndpoint(), httpClient);

      // Fetch a Drift placeOrders Transaction
      final var txFuture = rpcClient.getTransaction(
          "36Wnn99Y49mJ5GKiNiT3ja2q8gzSvMNrN5A3Bcn2YfCyrwY7kgQGVAu9VNzXqmWSbgzX76oUGxYNuPGM7tpPoJJS"
      );
      final var tx = txFuture.join();
      final byte[] txData = tx.data();

      final var skeleton = TransactionSkeleton.deserializeSkeleton(txData);

      final Instruction[] instructions;
      if (skeleton.isLegacy()) {
        instructions = skeleton.parseLegacyInstructions();
      } else {
        // Fetch Lookup tables to allow parsing of versioned transactions.
        final int txVersion = skeleton.version();
        if (txVersion == 0) {
          final var tableAccounts = skeleton.lookupTableAccounts();
          if (tableAccounts.length == 0) {
            instructions = skeleton.parseInstructionsWithoutTableAccounts();
          } else {
            final var tableAccountInfos = rpcClient.getAccounts(
                Arrays.asList(tableAccounts),
                AddressLookupTable.FACTORY
            ).join();

            final var lookupTables = tableAccountInfos.stream()
                .filter(Objects::nonNull)
                .map(AccountInfo::data)
                .collect(Collectors.toUnmodifiableMap(AddressLookupTable::address, Function.identity()));
            final var accounts = skeleton.parseAccounts(lookupTables);

            instructions = skeleton.parseInstructions(accounts);
          }
        } else {
          throw new IllegalStateException("Unhandled tx version " + txVersion);
        }
      }

      // instructions[0]; // Compute Budget Limit
      // instructions[1]; // Compute Unit Price
      // instructions[2]; // Drift Place Orders
      final var placeOrdersIxData = Arrays.stream(instructions)
          .filter(DriftProgram.PLACE_ORDERS_DISCRIMINATOR)
          .map(DriftProgram.PlaceOrdersIxData::read)
          .findFirst().orElseThrow();

      final OrderParams[] orderParamsArray = placeOrdersIxData.params();
      final OrderParams orderParams = orderParamsArray[0];

      final MarketConfig marketConfig;
      final var spotMarketConfigs = spotMarketsFuture.join().mainNet();
      if (orderParams.marketType() == MarketType.Perp) {
        final var perpMarketConfigs = perpMarketsFuture.join().mainNet();
        marketConfig = perpMarketConfigs.marketConfig(orderParams.marketIndex());
      } else {
        marketConfig = spotMarketConfigs.marketConfig(orderParams.marketIndex());
      }

      // Limit Long [amount] @ [price] on SOL-PERP [reduceOnly=false] [postOnly=MustPostOnly]
      System.out.format("""
              %s %s %d @ %d on %s [reduceOnly=%b] [postOnly=%s]
              """,
          orderParams.orderType(),
          orderParams.direction(),
          orderParams.baseAssetAmount(),
          orderParams.price(),
          marketConfig.symbol(),
          orderParams.reduceOnly(),
          orderParams.postOnly()
      );
    }
  }
}
