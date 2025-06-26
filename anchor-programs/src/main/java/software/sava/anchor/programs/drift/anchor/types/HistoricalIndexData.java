package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record HistoricalIndexData(// precision: PRICE_PRECISION
                                  long lastIndexBidPrice,
                                  // precision: PRICE_PRECISION
                                  long lastIndexAskPrice,
                                  // precision: PRICE_PRECISION
                                  long lastIndexPriceTwap,
                                  // precision: PRICE_PRECISION
                                  long lastIndexPriceTwap5min,
                                  // unix_timestamp of last snapshot
                                  long lastIndexPriceTwapTs) implements Borsh {

  public static final int BYTES = 40;

  public static HistoricalIndexData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastIndexBidPrice = getInt64LE(_data, i);
    i += 8;
    final var lastIndexAskPrice = getInt64LE(_data, i);
    i += 8;
    final var lastIndexPriceTwap = getInt64LE(_data, i);
    i += 8;
    final var lastIndexPriceTwap5min = getInt64LE(_data, i);
    i += 8;
    final var lastIndexPriceTwapTs = getInt64LE(_data, i);
    return new HistoricalIndexData(lastIndexBidPrice,
                                   lastIndexAskPrice,
                                   lastIndexPriceTwap,
                                   lastIndexPriceTwap5min,
                                   lastIndexPriceTwapTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastIndexBidPrice);
    i += 8;
    putInt64LE(_data, i, lastIndexAskPrice);
    i += 8;
    putInt64LE(_data, i, lastIndexPriceTwap);
    i += 8;
    putInt64LE(_data, i, lastIndexPriceTwap5min);
    i += 8;
    putInt64LE(_data, i, lastIndexPriceTwapTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
