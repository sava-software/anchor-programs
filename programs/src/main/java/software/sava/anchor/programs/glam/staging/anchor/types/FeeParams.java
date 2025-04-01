package software.sava.anchor.programs.glam.staging.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeParams(int yearInSeconds,
                        BigInteger highWaterMark,
                        BigInteger lastNav,
                        long lastPerformanceFeeCrystallized,
                        long lastManagementFeeCrystallized,
                        long lastProtocolFeeCrystallized) implements Borsh {

  public static final int BYTES = 60;

  public static FeeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var yearInSeconds = getInt32LE(_data, i);
    i += 4;
    final var highWaterMark = getInt128LE(_data, i);
    i += 16;
    final var lastNav = getInt128LE(_data, i);
    i += 16;
    final var lastPerformanceFeeCrystallized = getInt64LE(_data, i);
    i += 8;
    final var lastManagementFeeCrystallized = getInt64LE(_data, i);
    i += 8;
    final var lastProtocolFeeCrystallized = getInt64LE(_data, i);
    return new FeeParams(yearInSeconds,
                         highWaterMark,
                         lastNav,
                         lastPerformanceFeeCrystallized,
                         lastManagementFeeCrystallized,
                         lastProtocolFeeCrystallized);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, yearInSeconds);
    i += 4;
    putInt128LE(_data, i, highWaterMark);
    i += 16;
    putInt128LE(_data, i, lastNav);
    i += 16;
    putInt64LE(_data, i, lastPerformanceFeeCrystallized);
    i += 8;
    putInt64LE(_data, i, lastManagementFeeCrystallized);
    i += 8;
    putInt64LE(_data, i, lastProtocolFeeCrystallized);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
