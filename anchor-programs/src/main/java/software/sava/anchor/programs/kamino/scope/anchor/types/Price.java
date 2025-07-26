package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Price(long value, long exp) implements Borsh {

  public static final int BYTES = 16;

  public static Price read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var value = getInt64LE(_data, i);
    i += 8;
    final var exp = getInt64LE(_data, i);
    return new Price(value, exp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, value);
    i += 8;
    putInt64LE(_data, i, exp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
