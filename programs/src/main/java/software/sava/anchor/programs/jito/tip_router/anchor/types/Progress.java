package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Progress(long tally,
                       long total,
                       byte[] reserved) implements Borsh {

  public static final int BYTES = 24;

  public static Progress read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tally = getInt64LE(_data, i);
    i += 8;
    final var total = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[8];
    Borsh.readArray(reserved, _data, i);
    return new Progress(tally, total, reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tally);
    i += 8;
    putInt64LE(_data, i, total);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
