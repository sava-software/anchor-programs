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

import static java.lang.System.Logger.Level.WARNING;
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
                               long pythLazerId,
                               AccountMeta readMarketPDA,
                               AccountMeta writeMarketPDA,
                               PublicKey vaultPDA) implements MarketConfig {

  private static final System.Logger logger = System.getLogger(MarketConfig.class.getName());

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

  private static String replaceScale(final String enumString,
                                     final int index,
                                     final char replacement) {
    final var prefix = enumString.substring(0, index + 1);
    final int fromSuffix = index + 2;
    if (fromSuffix < enumString.length()) {
      return prefix + replacement + enumString.substring(fromSuffix);
    } else {
      return prefix + replacement;
    }
  }

  static OracleSource parseOracleSource(final JsonIterator ji) {
    final var oracleSource = ji.readString();
    if (oracleSource == null || oracleSource.isBlank()) {
      return null;
    }
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
      logger.log(WARNING, "Unknown oracle source: " + enumString, ex.getCause());
      return null;
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
    private long pythLazerId;

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
          pythLazerId,
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
        pythFeedId = ji.applyChars(GenerateMarketConstants.DECODE_HEX);
      } else if (fieldEquals("pythLazerId", buf, offset, len)) {
        pythLazerId = ji.readLong();
      } else {
        ji.skip();
      }
      return true;
    }
  }
}
