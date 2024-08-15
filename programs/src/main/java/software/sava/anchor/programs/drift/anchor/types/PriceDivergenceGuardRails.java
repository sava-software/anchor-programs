package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PriceDivergenceGuardRails(long markOraclePercentDivergence, long oracleTwap5minPercentDivergence) implements Borsh {

  public static final int BYTES = 16;

  public static PriceDivergenceGuardRails read(final byte[] _data, final int offset) {
    int i = offset;
    final var markOraclePercentDivergence = getInt64LE(_data, i);
    i += 8;
    final var oracleTwap5minPercentDivergence = getInt64LE(_data, i);
    return new PriceDivergenceGuardRails(markOraclePercentDivergence, oracleTwap5minPercentDivergence);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, markOraclePercentDivergence);
    i += 8;
    putInt64LE(_data, i, oracleTwap5minPercentDivergence);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
