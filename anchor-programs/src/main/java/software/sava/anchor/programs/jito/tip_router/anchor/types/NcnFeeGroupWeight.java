package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record NcnFeeGroupWeight(BigInteger weight) implements Borsh {

  public static final int BYTES = 16;

  public static NcnFeeGroupWeight read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var weight = getInt128LE(_data, offset);
    return new NcnFeeGroupWeight(weight);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, weight);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
