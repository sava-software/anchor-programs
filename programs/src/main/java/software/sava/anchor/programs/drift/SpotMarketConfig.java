package software.sava.anchor.programs.drift;

import software.sava.anchor.AnchorUtil;
import software.sava.anchor.programs.drift.anchor.types.OracleSource;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.FieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static systems.comodal.jsoniter.JsonIterator.fieldEquals;

public record SpotMarketConfig(String symbol,
                               int marketIndex,
                               Instant launchTs,
                               AccountMeta readOracle,
                               AccountMeta writeOracle,
                               OracleSource oracleSource,
                               PublicKey pythPullOraclePDA,
                               PublicKey mint,
                               PublicKey serumMarket,
                               PublicKey phoenixMarket,
                               PublicKey openbookMarket,
                               PublicKey pythFeedId,
                               AccountMeta readMarketPDA,
                               AccountMeta writeMarketPDA,
                               PublicKey vaultPDA) implements SrcGen, MarketConfig {

  private static final System.Logger logger = System.getLogger(MarketConfig.class.getName());

  static SpotMarketConfig createConfig(final String symbol,
                                       final int marketIndex,
                                       final String launchTs,
                                       final String oracle,
                                       final OracleSource oracleSource,
                                       final String pythPullOraclePDA,
                                       final String mint,
                                       final String serumMarket,
                                       final String phoenixMarket,
                                       final String openbookMarket,
                                       final String pythFeedId,
                                       final String marketPDA,
                                       final String vaultPDA) {
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
    return new SpotMarketConfig(
        symbol,
        marketIndex,
        launchTs == null ? null : Instant.parse(launchTs),
        readOracle, writeOracle,
        oracleSource,
        SrcGen.fromBase58Encoded(pythPullOraclePDA),
        SrcGen.fromBase58Encoded(mint),
        SrcGen.fromBase58Encoded(serumMarket),
        SrcGen.fromBase58Encoded(phoenixMarket),
        SrcGen.fromBase58Encoded(openbookMarket),
        SrcGen.fromBase58Encoded(pythFeedId),
        AccountMeta.createRead(marketPDAKey),
        AccountMeta.createWrite(marketPDAKey),
        SrcGen.fromBase58Encoded(vaultPDA)
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
                %s,
                %s,
                %s
            )""",
        symbol,
        marketIndex,
        launchTs == null ? null : '"' + launchTs.toString() + '"',
        SrcGen.pubKeyConstant(readOracle.publicKey()),
        oracleSource == null ? null : "OracleSource." + oracleSource.name(),
        SrcGen.pubKeyConstant(pythPullOraclePDA),
        SrcGen.pubKeyConstant(mint),
        SrcGen.pubKeyConstant(serumMarket),
        SrcGen.pubKeyConstant(phoenixMarket),
        SrcGen.pubKeyConstant(openbookMarket),
        SrcGen.pubKeyConstant(pythFeedId),
        SrcGen.pubKeyConstant(readMarketPDA.publicKey()),
        SrcGen.pubKeyConstant(vaultPDA)
    );
  }

  public static List<SpotMarketConfig> parseConfigs(final JsonIterator ji, final DriftAccounts driftAccounts) {
    final var configs = new ArrayList<SpotMarketConfig>();
    while (ji.readArray()) {
      final var parser = new SpotMarketConfig.Builder();
      ji.testObject(parser);
      final var config = parser.create(driftAccounts);
      configs.add(config);
    }
    return configs;
  }

  private static String replaceScale(final String enumString, final int index, final char replacement) {
    return enumString.substring(0, index + 1) + replacement + enumString.substring(index + 3);
  }

  static OracleSource parseOracleSource(final JsonIterator ji) {
    final var oracleSource = ji.readString();
    if (oracleSource == null || oracleSource.isBlank()) {
      return null;
    }
    if (oracleSource.equalsIgnoreCase("PYTH_1M_PULL")) {
      return OracleSource.Pyth1MPull;
    } else if (oracleSource.equalsIgnoreCase("PYTH_1K_PULL")) {
      return OracleSource.Pyth1KPull;
    } else {
      var enumString = AnchorUtil.camelCase(oracleSource.toLowerCase(Locale.ENGLISH), true);
      int i = enumString.indexOf("1k");
      if (i > 0) {
        enumString = replaceScale(enumString, i, 'K');
      } else {
        i = enumString.indexOf("1m");
        if (i > 0) {
          enumString = replaceScale(enumString, i, 'M');
        }
      }
      try {
        return OracleSource.valueOf(enumString);
      } catch (final RuntimeException ex) {
        logger.log(System.Logger.Level.WARNING, "Unknown oracle source: " + oracleSource, ex.getCause());
        return null;
      }
    }
  }

  @Override
  public AccountMeta oracle(final boolean write) {
    return readOracle;
  }

  private static final class Builder implements FieldBufferPredicate {

    private String symbol;
    private int marketIndex;
    private Instant launchTs;
    private PublicKey oracle;
    private OracleSource oracleSource;
    private PublicKey mint;
    private PublicKey serumMarket;
    private PublicKey phoenixMarket;
    private PublicKey openbookMarket;
    private PublicKey pythFeedId;

    private SpotMarketConfig create(final DriftAccounts driftAccounts) {
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
      final var marketPDA = DriftPDAs.deriveSpotMarketAccount(driftAccounts, marketIndex).publicKey();

      return new SpotMarketConfig(
          symbol,
          marketIndex,
          launchTs,
          readOracle, writeOracle,
          oracleSource,
          pythPullOraclePDA,
          mint,
          serumMarket,
          phoenixMarket,
          openbookMarket,
          pythFeedId,
          AccountMeta.createRead(marketPDA),
          AccountMeta.createWrite(marketPDA),
          DriftPDAs.deriveSpotMarketVaultAccount(driftAccounts, marketIndex).publicKey()
      );
    }

    @Override
    public boolean test(final char[] buf, final int offset, final int len, final JsonIterator ji) {
      if (fieldEquals("symbol", buf, offset, len)) {
        symbol = ji.readString();
      } else if (fieldEquals("symbol", buf, offset, len)) {
        symbol = ji.readString();
      } else if (fieldEquals("marketIndex", buf, offset, len)) {
        marketIndex = ji.readInt();
      } else if (fieldEquals("launchTs", buf, offset, len)) {
        launchTs = Instant.ofEpochMilli(ji.readLong());
      } else if (fieldEquals("oracle", buf, offset, len)) {
        oracle = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("oracleSource", buf, offset, len)) {
        oracleSource = parseOracleSource(ji);
      } else if (fieldEquals("mint", buf, offset, len)) {
        mint = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("serumMarket", buf, offset, len)) {
        serumMarket = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("phoenixMarket", buf, offset, len)) {
        phoenixMarket = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("openbookMarket", buf, offset, len)) {
        openbookMarket = PublicKeyEncoding.parseBase58Encoded(ji);
      } else if (fieldEquals("pythFeedId", buf, offset, len)) {
        pythFeedId = ji.applyChars(SrcGen.DECODE_HEX);
      } else {
        ji.skip();
      }
      return true;
    }
  }
}
