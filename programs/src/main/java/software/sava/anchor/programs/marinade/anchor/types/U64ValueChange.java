package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record U64ValueChange(long old, long _new) implements Borsh {

  public static final int BYTES = 16;

  public static U64ValueChange read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var old = getInt64LE(_data, i);
    i += 8;
    final var _new = getInt64LE(_data, i);
    return new U64ValueChange(old, _new);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, old);
    i += 8;
    putInt64LE(_data, i, _new);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
