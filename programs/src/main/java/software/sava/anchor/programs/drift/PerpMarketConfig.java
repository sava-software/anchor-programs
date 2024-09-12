package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.ContextFieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static systems.comodal.jsoniter.JsonIterator.fieldEquals;

public record PerpMarketConfig(String fullName,
                               Set<String> categories,
                               String symbol,
                               String baseAssetSymbol,
                               int marketIndex,
                               Instant launchTs,
                               AccountMeta readOracle,
                               PublicKey pythPullOraclePDA,
                               PublicKey pythFeedId,
                               AccountMeta readMarketPDA) implements SrcGen, MarketConfig {

  static PerpMarketConfig createConfig(final String fullName,
                                       final Set<String> categories,
                                       final String symbol,
                                       final String baseAssetSymbol,
                                       final int marketIndex,
                                       final String launchTs,
                                       final String oracle,
                                       final String pythPullOraclePDA,
                                       final String pythFeedId,
                                       final String marketPDA) {
    return new PerpMarketConfig(
        fullName,
        categories,
        symbol,
        baseAssetSymbol,
        marketIndex,
        Instant.parse(launchTs),
        SrcGen.readMetaFromBase58Encoded(oracle),
        SrcGen.fromBase58Encoded(pythPullOraclePDA),
        SrcGen.fromBase58Encoded(pythFeedId),
        SrcGen.readMetaFromBase58Encoded(marketPDA)
    );
  }

  @Override
  public String toSrc(final DriftAccounts driftAccounts) {
    return String.format("""
            createConfig(
                "%s",
                Set.of(%s),
                "%s",
                "%s",
                %d,
                %s,
                %s,
                %s,
                %s,
                %s
            )""",
        fullName,
        categories.isEmpty() ? null : categories.stream().collect(Collectors.joining("\", \"", "\"", "\"")),
        symbol,
        baseAssetSymbol,
        marketIndex,
        launchTs == null ? null : '"' + launchTs.toString() + '"',
        SrcGen.pubKeyConstant(readOracle.publicKey()),
        pythFeedId == null ? null : SrcGen.pubKeyConstant(DriftPDAs
            .derivePythPullOracleAccount(driftAccounts.driftProgram(), pythFeedId.toByteArray()).publicKey()),
        SrcGen.pubKeyConstant(pythFeedId),
        SrcGen.pubKeyConstant(DriftPDAs.derivePerpMarketAccount(driftAccounts, marketIndex).publicKey())
    );
  }

  public static List<PerpMarketConfig> parseConfigs(final JsonIterator ji) {
    final var configs = new ArrayList<PerpMarketConfig>();
    while (ji.readArray()) {
      final var config = ji.testObject(new PerpMarketConfig.Builder(), PARSER).create();
      configs.add(config);
    }
    return configs;
  }

  private static final ContextFieldBufferPredicate<PerpMarketConfig.Builder> PARSER = (builder, buf, offset, len, ji) -> {
    if (fieldEquals("fullName", buf, offset, len)) {
      builder.fullName = ji.readString();
    } else if (fieldEquals("category", buf, offset, len)) {
      final var categories = new HashSet<String>();
      while (ji.readArray()) {
        categories.add(ji.readString());
      }
      builder.categories = categories;
    } else if (fieldEquals("symbol", buf, offset, len)) {
      builder.symbol = ji.readString();
    } else if (fieldEquals("baseAssetSymbol", buf, offset, len)) {
      builder.baseAssetSymbol = ji.readString();
    } else if (fieldEquals("marketIndex", buf, offset, len)) {
      builder.marketIndex = ji.readInt();
    } else if (fieldEquals("oracle", buf, offset, len)) {
      builder.oracle = PublicKeyEncoding.parseBase58Encoded(ji);
    } else if (fieldEquals("launchTs", buf, offset, len)) {
      builder.launchTs = Instant.ofEpochMilli(ji.readLong());
    } else if (fieldEquals("pythFeedId", buf, offset, len)) {
      builder.pythFeedId = ji.applyChars(DECODE_HEX);
    } else {
      ji.skip();
    }
    return true;
  };

  private static final class Builder {

    private String fullName;
    private Set<String> categories;
    private String symbol;
    private String baseAssetSymbol;
    private int marketIndex;
    private Instant launchTs;
    private PublicKey oracle;
    private PublicKey pythFeedId;

    private PerpMarketConfig create() {
      return new PerpMarketConfig(
          fullName,
          categories,
          symbol,
          baseAssetSymbol,
          marketIndex,
          launchTs,
          oracle == null ? null : AccountMeta.createRead(oracle),
          null,
          pythFeedId,
          null
      );
    }
  }
}
