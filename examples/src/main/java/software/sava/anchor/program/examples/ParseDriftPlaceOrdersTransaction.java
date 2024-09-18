package software.sava.anchor.program.examples;

import software.sava.anchor.programs.drift.DriftAsset;
import software.sava.anchor.programs.drift.DriftProgramClient;
import software.sava.anchor.programs.drift.MarketConfig;
import software.sava.anchor.programs.drift.anchor.DriftProgram;
import software.sava.anchor.programs.drift.anchor.types.MarketType;
import software.sava.anchor.programs.drift.anchor.types.OrderParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.lookup.AddressLookupTable;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.tx.Instruction;
import software.sava.core.tx.TransactionSkeleton;
import software.sava.rpc.json.http.SolanaNetwork;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramClient;
import software.sava.solana.web2.jupiter.client.http.JupiterClient;
import software.sava.solana.web2.jupiter.client.http.response.TokenContext;

import java.net.http.HttpClient;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ParseDriftPlaceOrdersTransaction {

  public static void main(final String[] args) {
    try (final var httpClient = HttpClient.newHttpClient()) {

      final var rpcClient = SolanaRpcClient.createClient(SolanaNetwork.MAIN_NET.getEndpoint(), httpClient);

      // Fetch a Drift placeOrders Transaction
      final var txFuture = rpcClient.getTransaction(
          "36Wnn99Y49mJ5GKiNiT3ja2q8gzSvMNrN5A3Bcn2YfCyrwY7kgQGVAu9VNzXqmWSbgzX76oUGxYNuPGM7tpPoJJS"
      );
      final var tx = txFuture.join();
      final byte[] txData = tx.data();

      final var skeleton = TransactionSkeleton.deserializeSkeleton(txData);

      final Instruction[] instructions;
      if (skeleton.isLegacy()) {
        instructions = skeleton.parseInstructions(skeleton.parseAccounts());
      } else {
        // Fetch Lookup tables to allow parsing of versioned transactions.
        final int txVersion = skeleton.version();
        if (txVersion == 0) {
          final var tableAccountInfos = rpcClient.getMultipleAccounts(
              Arrays.asList(skeleton.lookupTableAccounts()),
              AddressLookupTable.FACTORY
          ).join();

          final var lookupTables = tableAccountInfos.stream()
              .map(AccountInfo::data)
              .collect(Collectors.toUnmodifiableMap(AddressLookupTable::address, Function.identity()));

          instructions = skeleton.parseInstructions(skeleton.parseAccounts(lookupTables));
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

      // Fetch token contexts to make use of convenient scaled value conversions.
      final var jupiterClient = JupiterClient.createClient(httpClient);
      final var verifiedTokens = jupiterClient.verifiedTokenMap().join();

      // Create Drift Client to map market indexes from the order to its configuration.
      final var nativeProgramClient = NativeProgramClient.createClient();
      final var nativeProgramAccountClient = nativeProgramClient
          .createAccountClient(AccountMeta.createFeePayer(PublicKey.NONE));
      final var driftClient = DriftProgramClient.createClient(nativeProgramAccountClient);

      final var driftAccounts = driftClient.accounts();
      final MarketConfig marketConfig;
      final TokenContext baseTokenContext;
      if (orderParams.marketType() == MarketType.Perp) {
        final var perpMarketConfig = driftAccounts.perpMarketConfig(orderParams.marketIndex());
        final var spotConfig = driftClient.spotMarket(perpMarketConfig.baseAssetSymbol());
        baseTokenContext = verifiedTokens.get(spotConfig.mint());
        marketConfig = perpMarketConfig;
      } else {
        final var spotConfig = driftAccounts.spotMarketConfig(orderParams.marketIndex());
        baseTokenContext = verifiedTokens.get(spotConfig.mint());
        marketConfig = spotConfig;
      }

      // Assume all Drift markets are priced in USDC
      final var usdcTokenMint = driftClient.spotMarket(DriftAsset.USDC).mint();
      final var usdcTokenContext = verifiedTokens.get(usdcTokenMint);

      System.out.format("""
              %s %s %s @ %s on %s [reduceOnly=%b] [postOnly=%s]
              """,
          orderParams.orderType(),
          orderParams.direction(),
          baseTokenContext.toDecimal(orderParams.baseAssetAmount()).toPlainString(),
          usdcTokenContext.toDecimal(orderParams.price()).toPlainString(),
          marketConfig.symbol(),
          orderParams.reduceOnly(),
          orderParams.postOnly()
      );
    }
  }
}
