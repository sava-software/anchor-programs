![](https://github.com/sava-software/sava/blob/003cf88b3cd2a05279027557f23f7698662d2999/assets/images/solana_java_cup.svg)

# Anchor Programs [![Build](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/gradle.yml) [![Release](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml/badge.svg)](https://github.com/sava-software/anchor-programs/actions/workflows/release.yml)

Generated programs can be found in
the [root source package directory](programs/src/main/java/software/sava/anchor/programs). For each project generated
code is under the `anchor` package, and manually written code is directly under the project package.

Code is generated using [sava-software/anchor-src-gen](https://github.com/sava-software/anchor-src-gen), see that
project for more context on which features are provided.

## Requirements

- The latest generally available JDK. This project will continue to move to the latest and will not maintain
  versions released against previous JDK's.

## [Dependencies](programs/src/main/java/module-info.java)

- [JSON Iterator](https://github.com/comodal/json-iterator?tab=readme-ov-file#json-iterator)
- [sava-core](https://github.com/sava-software/sava)
- [sava-rpc](https://github.com/sava-software/sava)
- [solana-programs](https://github.com/sava-software/solana-programs)
- [anchor-src-gen](https://github.com/sava-software/anchor-src-gen)

## Contribution

Unit tests are needed and welcomed. Otherwise, please open an issue or send an email before working on a pull request.

## Warning

Young project, under active development, breaking changes and bugs are to be expected.

## Examples

### [Retrieve and Parse Drift placeOrders Transaction](examples/src/main/java/software/sava/anchor/program/examples/ParseDriftPlaceOrdersTransaction.java)

Prints the following at the end: 
> Limit Long 0.1 @ 111 on SOL-PERP [reduceOnly=false] [postOnly=MustPostOnly]

```java
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
  final var placeOrdersIx = Arrays.stream(instructions)
      .filter(DriftProgram.PLACE_ORDERS_DISCRIMINATOR)
      .findFirst().orElseThrow();
  final var placeOrdersIxData = DriftProgram.PlaceOrdersIxData
      .read(placeOrdersIx.data(), placeOrdersIx.offset());
  final OrderParams[] orderParams = placeOrdersIxData.params();
  final var order = orderParams[0];

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
  if (order.marketType() == MarketType.Perp) {
    final var perpMarketConfig = driftAccounts.perpMarketConfig(order.marketIndex());
    final var spotConfig = driftClient.spotMarket(perpMarketConfig.baseAssetSymbol());
    baseTokenContext = verifiedTokens.get(spotConfig.mint());
    marketConfig = perpMarketConfig;
  } else {
    final var spotConfig = driftAccounts.spotMarketConfig(order.marketIndex());
    baseTokenContext = verifiedTokens.get(spotConfig.mint());
    marketConfig = spotConfig;
  }

  // Assume all Drift markets are priced in USDC
  final var usdcTokenMint = driftClient.spotMarket(DriftAsset.USDC).mint();
  final var usdcTokenContext = verifiedTokens.get(usdcTokenMint);

  System.out.format("""
          %s %s %s @ %s on %s [reduceOnly=%b] [postOnly=%s]
          """,
      order.orderType(),
      order.direction(),
      baseTokenContext.toDecimal(order.baseAssetAmount()).toPlainString(),
      usdcTokenContext.toDecimal(order.price()).toPlainString(),
      marketConfig.symbol(),
      order.reduceOnly(),
      order.postOnly()
  );
}
```
