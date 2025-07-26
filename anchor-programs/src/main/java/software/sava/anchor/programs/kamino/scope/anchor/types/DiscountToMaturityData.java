package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DiscountToMaturityData(int discountPerYearBps, long maturityTimestamp) implements Borsh {

  public static final int BYTES = 10;

  public static DiscountToMaturityData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var discountPerYearBps = getInt16LE(_data, i);
    i += 2;
    final var maturityTimestamp = getInt64LE(_data, i);
    return new DiscountToMaturityData(discountPerYearBps, maturityTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, discountPerYearBps);
    i += 2;
    putInt64LE(_data, i, maturityTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
