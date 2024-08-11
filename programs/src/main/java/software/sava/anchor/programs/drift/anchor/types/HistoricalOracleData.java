package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record HistoricalOracleData(// precision: PRICE_PRECISION
                                   long lastOraclePrice,
                                   // precision: PRICE_PRECISION
                                   long lastOracleConf,
                                   // number of slots since last update
                                   long lastOracleDelay,
                                   // precision: PRICE_PRECISION
                                   long lastOraclePriceTwap,
                                   // precision: PRICE_PRECISION
                                   long lastOraclePriceTwap5min,
                                   // unix_timestamp of last snapshot
                                   long lastOraclePriceTwapTs) implements Borsh {

  public static final int BYTES = 48;

  public static HistoricalOracleData read(final byte[] _data, final int offset) {
    int i = offset;
    final var lastOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var lastOracleConf = getInt64LE(_data, i);
    i += 8;
    final var lastOracleDelay = getInt64LE(_data, i);
    i += 8;
    final var lastOraclePriceTwap = getInt64LE(_data, i);
    i += 8;
    final var lastOraclePriceTwap5min = getInt64LE(_data, i);
    i += 8;
    final var lastOraclePriceTwapTs = getInt64LE(_data, i);
    return new HistoricalOracleData(lastOraclePrice,
                                    lastOracleConf,
                                    lastOracleDelay,
                                    lastOraclePriceTwap,
                                    lastOraclePriceTwap5min,
                                    lastOraclePriceTwapTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastOraclePrice);
    i += 8;
    putInt64LE(_data, i, lastOracleConf);
    i += 8;
    putInt64LE(_data, i, lastOracleDelay);
    i += 8;
    putInt64LE(_data, i, lastOraclePriceTwap);
    i += 8;
    putInt64LE(_data, i, lastOraclePriceTwap5min);
    i += 8;
    putInt64LE(_data, i, lastOraclePriceTwapTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}
