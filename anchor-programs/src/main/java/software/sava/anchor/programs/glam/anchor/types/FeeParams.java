package software.sava.anchor.programs.glam.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeParams(int yearInSeconds,
                        BigInteger paHighWaterMark,
                        BigInteger paLastNav,
                        BigInteger lastAum,
                        long lastPerformanceFeeCrystallized,
                        long lastManagementFeeCrystallized,
                        long lastProtocolFeeCrystallized) implements Borsh {

  public static final int BYTES = 76;

  public static FeeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var yearInSeconds = getInt32LE(_data, i);
    i += 4;
    final var paHighWaterMark = getInt128LE(_data, i);
    i += 16;
    final var paLastNav = getInt128LE(_data, i);
    i += 16;
    final var lastAum = getInt128LE(_data, i);
    i += 16;
    final var lastPerformanceFeeCrystallized = getInt64LE(_data, i);
    i += 8;
    final var lastManagementFeeCrystallized = getInt64LE(_data, i);
    i += 8;
    final var lastProtocolFeeCrystallized = getInt64LE(_data, i);
    return new FeeParams(yearInSeconds,
                         paHighWaterMark,
                         paLastNav,
                         lastAum,
                         lastPerformanceFeeCrystallized,
                         lastManagementFeeCrystallized,
                         lastProtocolFeeCrystallized);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, yearInSeconds);
    i += 4;
    putInt128LE(_data, i, paHighWaterMark);
    i += 16;
    putInt128LE(_data, i, paLastNav);
    i += 16;
    putInt128LE(_data, i, lastAum);
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
