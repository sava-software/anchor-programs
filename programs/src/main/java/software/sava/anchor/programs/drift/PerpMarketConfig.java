package software.sava.anchor.programs.drift;

import org.bouncycastle.util.encoders.Hex;
import software.sava.core.accounts.PublicKey;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.CharBufferFunction;
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
                               PublicKey oracle,
                               PublicKey pythFeedId) {

  static PerpMarketConfig createConfig(final String fullName,
                                       final Set<String> categories,
                                       final String symbol,
                                       final String baseAssetSymbol,
                                       final int marketIndex,
                                       final long launchTs,
                                       final String oracle,
                                       final String pythFeedId) {
    return new PerpMarketConfig(
        fullName,
        categories,
        symbol,
        baseAssetSymbol,
        marketIndex,
        Instant.ofEpochMilli(launchTs),
        PublicKey.fromBase58Encoded(oracle),
        pythFeedId == null ? null : PublicKey.fromBase58Encoded(pythFeedId)
    );
  }

  public String toSrc() {
    return String.format("""
            createConfig(
              "%s",
              Set.of(%s),
              "%s",
              "%s",
              %d,
              %dL,
              "%s",
              %s
            )""",
        fullName,
        categories.isEmpty() ? "" : categories.stream().collect(Collectors.joining("\", \"", "\"", "\"")),
        symbol,
        baseAssetSymbol,
        marketIndex,
        launchTs.toEpochMilli(),
        oracle.toBase58(),
        pythFeedId == null ? "null" : '"' + pythFeedId.toBase58() + '"'
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

  private static final CharBufferFunction<PublicKey> DECODE_HEX = (buf, offset, len) -> PublicKey
      .createPubKey(Hex.decode(new String(buf, offset + 2, len - 2)));

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
          oracle,
          pythFeedId
      );
    }
  }
}
