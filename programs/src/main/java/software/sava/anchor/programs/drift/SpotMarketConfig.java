package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.ContextFieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static systems.comodal.jsoniter.JsonIterator.fieldEquals;

public record SpotMarketConfig(String symbol,
                               int marketIndex,
                               Instant launchTs,
                               AccountMeta readOracle,
                               PublicKey pythPullOraclePDA,
                               PublicKey mint,
                               PublicKey serumMarket,
                               PublicKey phoenixMarket,
                               PublicKey openbookMarket,
                               PublicKey pythFeedId,
                               AccountMeta readMarketPDA) implements SrcGen, MarketConfig {

  static SpotMarketConfig createConfig(final String symbol,
                                       final int marketIndex,
                                       final String launchTs,
                                       final String oracle,
                                       final String pythPullOraclePDA,
                                       final String mint,
                                       final String serumMarket,
                                       final String phoenixMarket,
                                       final String openbookMarket,
                                       final String pythFeedId,
                                       final String marketPDA) {
    return new SpotMarketConfig(
        symbol,
        marketIndex,
        launchTs == null ? null : Instant.parse(launchTs),
        SrcGen.readMetaFromBase58Encoded(oracle),
        SrcGen.fromBase58Encoded(pythPullOraclePDA),
        SrcGen.fromBase58Encoded(mint),
        SrcGen.fromBase58Encoded(serumMarket),
        SrcGen.fromBase58Encoded(phoenixMarket),
        SrcGen.fromBase58Encoded(openbookMarket),
        SrcGen.fromBase58Encoded(pythFeedId),
        SrcGen.readMetaFromBase58Encoded(marketPDA)
    );
  }

  @Override
  public String toSrc(final DriftAccounts driftAccounts) {
    return String.format("""
            createConfig(
                "%s",
                %d,
                %s,
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
        launchTs == null ? null : '"' + launchTs.toString() + '"',
        SrcGen.pubKeyConstant(readOracle.publicKey()),
        pythFeedId == null ? null : SrcGen.pubKeyConstant(DriftPDAs
            .derivePythPullOracleAccount(driftAccounts.driftProgram(), pythFeedId.toByteArray()).publicKey()),
        SrcGen.pubKeyConstant(mint),
        SrcGen.pubKeyConstant(serumMarket),
        SrcGen.pubKeyConstant(phoenixMarket),
        SrcGen.pubKeyConstant(openbookMarket),
        SrcGen.pubKeyConstant(pythFeedId),
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
          oracle == null ? null : AccountMeta.createRead(oracle),
          null,
          mint,
          serumMarket,
          phoenixMarket,
          openbookMarket,
          pythFeedId,
          null
      );
    }
  }
}
