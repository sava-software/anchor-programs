package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SwitchboardDecimal(BigInteger mantissa, int scale) implements Borsh {

  public static final int BYTES = 20;

  public static SwitchboardDecimal read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mantissa = getInt128LE(_data, i);
    i += 16;
    final var scale = getInt32LE(_data, i);
    return new SwitchboardDecimal(mantissa, scale);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, mantissa);
    i += 16;
    putInt32LE(_data, i, scale);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
