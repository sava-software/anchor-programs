package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BorrowRateState(long currentRate,
                              BigInteger cumulativeLockFee,
                              long lastUpdate) implements Borsh {

  public static final int BYTES = 32;

  public static BorrowRateState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var currentRate = getInt64LE(_data, i);
    i += 8;
    final var cumulativeLockFee = getInt128LE(_data, i);
    i += 16;
    final var lastUpdate = getInt64LE(_data, i);
    return new BorrowRateState(currentRate, cumulativeLockFee, lastUpdate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, currentRate);
    i += 8;
    putInt128LE(_data, i, cumulativeLockFee);
    i += 16;
    putInt64LE(_data, i, lastUpdate);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
