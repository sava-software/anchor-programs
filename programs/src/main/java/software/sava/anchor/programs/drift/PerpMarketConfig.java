package software.sava.anchor.programs.drift;

import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.FieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static software.sava.anchor.programs.drift.SpotMarketConfig.parseOracleSource;
import static systems.comodal.jsoniter.JsonIterator.fieldEquals;

public record PerpMarketConfig(String fullName,
                               Set<String> categories,
                               String symbol,
                               String baseAssetSymbol,
                               int marketIndex,
                               Instant launchTs,
                               AccountMeta readOracle,
                               AccountMeta writeOracle,
                               OracleSource oracleSource,
                               PublicKey pythPullOraclePDA,
                               PublicKey pythFeedId,
                               AccountMeta readMarketPDA,
                               AccountMeta writeMarketPDA) implements SrcGen, MarketConfig {

  static PerpMarketConfig createConfig(final String fullName,
                                       final Set<String> categories,
                                       final String symbol,
                                       final String baseAssetSymbol,
                                       final int marketIndex,
                                       final String launchTs,
                                       final String oracle,
                                       final OracleSource oracleSource,
                                       final String pythPullOraclePDA,
                                       final String pythFeedId,
                                       final String marketPDA) {
    final var marketPDAKey = PublicKey.fromBase58Encoded(marketPDA);
    final AccountMeta readOracle;
    final AccountMeta writeOracle;
    if (oracle == null) {
      readOracle = null;
      writeOracle = null;
    } else {
      final var oracleKey = PublicKey.fromBase58Encoded(oracle);
      readOracle = AccountMeta.createRead(oracleKey);
      writeOracle = AccountMeta.createWrite(oracleKey);
    }
    return new PerpMarketConfig(
        fullName,
        categories,
        symbol,
        baseAssetSymbol,
        marketIndex,
        Instant.parse(launchTs),
        readOracle, writeOracle,
        oracleSource,
        SrcGen.fromBase58Encoded(pythPullOraclePDA),
        SrcGen.fromBase58Encoded(pythFeedId),
        AccountMeta.createRead(marketPDAKey),
        AccountMeta.createWrite(marketPDAKey)
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
        oracleSource == null ? null : "OracleSource." + oracleSource.name(),
        SrcGen.pubKeyConstant(pythPullOraclePDA),
        SrcGen.pubKeyConstant(pythFeedId),
        SrcGen.pubKeyConstant(readMarketPDA.publicKey())
    );
  }

  public static List<PerpMarketConfig> parseConfigs(final JsonIterator ji, final DriftAccounts driftAccounts) {
    final var configs = new ArrayList<PerpMarketConfig>();
    while (ji.readArray()) {
      final var parser = new PerpMarketConfig.Builder();
      ji.testObject(parser);
      final var config = parser.create(driftAccounts);
      configs.add(config);
    }
    return configs;
  }

  @Override
  public AccountMeta oracle(final boolean write) {
    return write && oracleSource == OracleSource.Prelaunch
        ? writeOracle
        : readOracle;
  }

  private static final class Builder implements FieldBufferPredicate {

    private String fullName;
    private Set<String> categories;
    private String symbol;
    private String baseAssetSymbol;
    private int marketIndex;
    private Instant launchTs;
    private PublicKey oracle;
    private OracleSource oracleSource;
    private PublicKey pythFeedId;

    private PerpMarketConfig create(final DriftAccounts driftAccounts) {
      final AccountMeta readOracle;
      final AccountMeta writeOracle;
      if (oracle == null) {
        readOracle = null;
        writeOracle = null;
      } else {
        readOracle = AccountMeta.createRead(oracle);
        writeOracle = AccountMeta.createWrite(oracle);
      }
      final var pythPullOraclePDA = pythFeedId == null ? null : DriftPDAs
          .derivePythPullOracleAccount(driftAccounts.driftProgram(), pythFeedId.toByteArray()).publicKey();
      final var marketPDA = DriftPDAs.derivePerpMarketAccount(driftAccounts, marketIndex).publicKey();
      return new PerpMarketConfig(
          fullName,
          categories,
          symbol,
          baseAssetSymbol,
          marketIndex,
          launchTs,
          readOracle, writeOracle,
          oracleSource,
          pythPullOraclePDA,
          pythFeedId,
          AccountMeta.createRead(marketPDA),
          AccountMeta.createWrite(marketPDA)
      );
    }

    @Override
    public boolean test(final char[] buf, final int offset, final int len, final JsonIterator ji) {
      if (fieldEquals("fullName", buf, offset, len)) {
        fullName = ji.readString();
      } else if (fieldEquals("category", buf, offset, len)) {
        final var categories = new HashSet<String>();
        while (ji.readArray()) {
          categories.add(ji.readString());
        }
        this.categories = categories;
      } else if (fieldEquals("symbol", buf, offset, len)) {
        symbol = ji.readString();
      } else if (fieldEquals("baseAssetSymbol", buf, offset, len)) {
        baseAssetSymbol = ji.readString();
      } else if (fieldEquals("marketIndex", buf, offset, len)) {
        marketIndex = ji.readInt();
      } else if (fieldEquals("oracle", buf, offset, len)) {
        oracle = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("oracleSource", buf, offset, len)) {
        oracleSource = parseOracleSource(ji);
      } else if (fieldEquals("launchTs", buf, offset, len)) {
        launchTs = Instant.ofEpochMilli(ji.readLong());
      } else if (fieldEquals("pythFeedId", buf, offset, len)) {
        pythFeedId = ji.applyChars(DECODE_HEX);
      } else {
        ji.skip();
      }
      return true;
    }
  }
}
