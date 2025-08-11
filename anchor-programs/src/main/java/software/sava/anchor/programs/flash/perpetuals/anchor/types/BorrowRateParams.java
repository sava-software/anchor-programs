package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BorrowRateParams(long baseRate,
                               long slope1,
                               long slope2,
                               long optimalUtilization) implements Borsh {

  public static final int BYTES = 32;

  public static BorrowRateParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var baseRate = getInt64LE(_data, i);
    i += 8;
    final var slope1 = getInt64LE(_data, i);
    i += 8;
    final var slope2 = getInt64LE(_data, i);
    i += 8;
    final var optimalUtilization = getInt64LE(_data, i);
    return new BorrowRateParams(baseRate,
                                slope1,
                                slope2,
                                optimalUtilization);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, baseRate);
    i += 8;
    putInt64LE(_data, i, slope1);
    i += 8;
    putInt64LE(_data, i, slope2);
    i += 8;
    putInt64LE(_data, i, optimalUtilization);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
