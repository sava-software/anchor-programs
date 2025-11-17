package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.MarketStatus;
import software.sava.anchor.programs.drift.anchor.types.OracleSource;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.rpc.json.PublicKeyEncoding;
import systems.comodal.jsoniter.FieldBufferPredicate;
import systems.comodal.jsoniter.JsonIterator;

import java.time.Instant;
import java.util.*;
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
                               long pythLazerId,
                               AccountMeta readMarketPDA,
                               AccountMeta writeMarketPDA,
                               MarketStatus marketStatus) implements MarketConfig {

  private static final System.Logger logger = System.getLogger(MarketConfig.class.getName());

  public static List<PerpMarketConfig> parseConfigs(final JsonIterator ji, final DriftAccounts driftAccounts) {
    final var configs = new ArrayList<PerpMarketConfig>();
    while (ji.readArray()) {
      final var parser = new Parser();
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

  private static final class Parser implements FieldBufferPredicate {

    private static final Map<String, MarketStatus> STATUS_MAP = Arrays.stream(MarketStatus.values())
        .collect(Collectors.toUnmodifiableMap(e -> e.name().toUpperCase(), e -> e));

    private String fullName;
    private Set<String> categories;
    private String symbol;
    private String baseAssetSymbol;
    private int marketIndex;
    private Instant launchTs;
    private PublicKey oracle;
    private OracleSource oracleSource;
    private PublicKey pythFeedId;
    private long pythLazerId;
    private MarketStatus marketStatus;

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
          pythLazerId,
          AccountMeta.createRead(marketPDA),
          AccountMeta.createWrite(marketPDA),
          marketStatus
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
        pythFeedId = ji.applyChars(GenerateMarketConstants.DECODE_HEX);
      } else if (fieldEquals("pythLazerId", buf, offset, len)) {
        pythLazerId = ji.readLong();
      } else if (fieldEquals("marketStatus", buf, offset, len)) {
        marketStatus = STATUS_MAP.get(ji.readString().toUpperCase());
      } else {
        logger.log(System.Logger.Level.INFO, "Skipping unknown Drift Config field " + new String(buf, offset, len));
        ji.skip();
      }
      return true;
    }
  }
}
