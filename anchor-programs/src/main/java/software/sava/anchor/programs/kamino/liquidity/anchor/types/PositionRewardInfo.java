package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PositionRewardInfo(BigInteger growthInsideCheckpoint, long amountOwed) implements Borsh {

  public static final int BYTES = 24;

  public static PositionRewardInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var growthInsideCheckpoint = getInt128LE(_data, i);
    i += 16;
    final var amountOwed = getInt64LE(_data, i);
    return new PositionRewardInfo(growthInsideCheckpoint, amountOwed);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, growthInsideCheckpoint);
    i += 16;
    putInt64LE(_data, i, amountOwed);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
