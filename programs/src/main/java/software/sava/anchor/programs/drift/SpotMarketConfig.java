package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.ContextFieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static software.sava.anchor.programs.drift.SpotMarkets.MAIN_NET;
import static systems.comodal.jsoniter.JsonIterator.fieldEquals;

public record SpotMarketConfig(String symbol,
                               int marketIndex,
                               Instant launchTs,
                               PublicKey oracle,
                               PublicKey mint,
                               PublicKey serumMarket,
                               PublicKey phoenixMarket,
                               PublicKey openbookMarket,
                               PublicKey pythFeedId,
                               PublicKey pythPullOraclePDA,
                               PublicKey marketPDA) implements SrcGen {

  static SpotMarketConfig createConfig(final String symbol,
                                       final int marketIndex,
                                       final long launchTs,
                                       final String oracle,
                                       final String mint,
                                       final String serumMarket,
                                       final String phoenixMarket,
                                       final String openbookMarket,
                                       final String pythFeedId) {
    return new SpotMarketConfig(
        symbol,
        marketIndex,
        launchTs <= 0 ? null : Instant.ofEpochMilli(launchTs),
        SrcGen.fromBase58Encoded(oracle),
        SrcGen.fromBase58Encoded(mint),
        SrcGen.fromBase58Encoded(serumMarket),
        SrcGen.fromBase58Encoded(phoenixMarket),
        SrcGen.fromBase58Encoded(openbookMarket),
        SrcGen.fromBase58Encoded(pythFeedId),
        null,
        null
    );
  }


  static SpotMarketConfig createConfig(final String symbol,
                                       final int marketIndex,
                                       final long launchTs,
                                       final String oracle,
                                       final String mint,
                                       final String serumMarket,
                                       final String phoenixMarket,
                                       final String openbookMarket,
                                       final String pythFeedId,
                                       final String pythPullOraclePDA,
                                       final String marketPDA) {
    return new SpotMarketConfig(
        symbol,
        marketIndex,
        launchTs <= 0 ? null : Instant.ofEpochMilli(launchTs),
        SrcGen.fromBase58Encoded(oracle),
        SrcGen.fromBase58Encoded(mint),
        SrcGen.fromBase58Encoded(serumMarket),
        SrcGen.fromBase58Encoded(phoenixMarket),
        SrcGen.fromBase58Encoded(openbookMarket),
        SrcGen.fromBase58Encoded(pythFeedId),
        SrcGen.fromBase58Encoded(pythPullOraclePDA),
        SrcGen.fromBase58Encoded(marketPDA)
    );
  }

  @Override
  public String toSrc(final DriftAccounts driftAccounts) {
    return String.format("""
            createConfig(
                "%s",
                %d,
                %dL,
                %s,
                %s,
                %s,
                %s,
                %s,
                %s,
                %s,
                %s
            )""",
        symbol,
        marketIndex,
        launchTs == null ? Long.MIN_VALUE : launchTs.toEpochMilli(),
        SrcGen.pubKeyConstant(oracle),
        SrcGen.pubKeyConstant(mint),
        SrcGen.pubKeyConstant(serumMarket),
        SrcGen.pubKeyConstant(phoenixMarket),
        SrcGen.pubKeyConstant(openbookMarket),
        SrcGen.pubKeyConstant(pythFeedId),
        pythFeedId == null ? null : SrcGen.pubKeyConstant(DriftPDAs
            .derivePythPullOracleAccount(driftAccounts.driftProgram(), pythFeedId.toByteArray()).publicKey()),
        SrcGen.pubKeyConstant(DriftPDAs.deriveSpotMarketAccount(driftAccounts, marketIndex).publicKey())
    );
  }

  public static List<SpotMarketConfig> parseConfigs(final JsonIterator ji) {
    final var configs = new ArrayList<SpotMarketConfig>();
    while (ji.readArray()) {
      final var config = ji.testObject(new SpotMarketConfig.Builder(), PARSER).create();
      configs.add(config);
    }
    return configs;
  }

  private static final ContextFieldBufferPredicate<SpotMarketConfig.Builder> PARSER = (builder, buf, offset, len, ji) -> {
    if (fieldEquals("symbol", buf, offset, len)) {
      builder.symbol = ji.readString();
    } else if (fieldEquals("symbol", buf, offset, len)) {
      builder.symbol = ji.readString();
    } else if (fieldEquals("marketIndex", buf, offset, len)) {
      builder.marketIndex = ji.readInt();
    } else if (fieldEquals("launchTs", buf, offset, len)) {
      builder.launchTs = Instant.ofEpochMilli(ji.readLong());
    } else if (fieldEquals("oracle", buf, offset, len)) {
      builder.oracle = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("mint", buf, offset, len)) {
      builder.mint = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("serumMarket", buf, offset, len)) {
      builder.serumMarket = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("phoenixMarket", buf, offset, len)) {
      builder.phoenixMarket = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("openbookMarket", buf, offset, len)) {
      builder.openbookMarket = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("pythFeedId", buf, offset, len)) {
      builder.pythFeedId = ji.applyChars(SrcGen.DECODE_HEX);
    } else {
      ji.skip();
    }
    return true;
  };

  private static final class Builder {

    private String symbol;
    private int marketIndex;
    private Instant launchTs;
    private PublicKey oracle;
    private PublicKey mint;
    private PublicKey serumMarket;
    private PublicKey phoenixMarket;
    private PublicKey openbookMarket;
    private PublicKey pythFeedId;

    private SpotMarketConfig create() {
      return new SpotMarketConfig(
          symbol,
          marketIndex,
          launchTs,
          oracle,
          mint,
          serumMarket,
          phoenixMarket,
          openbookMarket,
          pythFeedId,
          null,
          null
      );
    }
  }

  public static void main(final String[] args) {
    final var accounts = DriftAccounts.MAIN_NET;
    final var byAsset = Arrays.stream(MAIN_NET)
        .collect(Collectors.toUnmodifiableMap(SpotMarketConfig::symbol, Function.identity()));

    for (final var c : MAIN_NET) {
      System.out.format("%s: %d: %s%n", c.symbol, c.marketIndex, DriftPDAs.deriveSpotMarketAccount(accounts, c.marketIndex).publicKey());
    }
  }
}
