package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VestingParams(long totalLockedAmount,
                            long cliffPeriod,
                            long unlockPeriod) implements Borsh {

  public static final int BYTES = 24;

  public static VestingParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var totalLockedAmount = getInt64LE(_data, i);
    i += 8;
    final var cliffPeriod = getInt64LE(_data, i);
    i += 8;
    final var unlockPeriod = getInt64LE(_data, i);
    return new VestingParams(totalLockedAmount, cliffPeriod, unlockPeriod);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, totalLockedAmount);
    i += 8;
    putInt64LE(_data, i, cliffPeriod);
    i += 8;
    putInt64LE(_data, i, unlockPeriod);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
