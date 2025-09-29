package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Oracles(Oracle[] xs, long len) implements Borsh {

  public static final int BYTES = 2440;
  public static final int XS_LEN = 19;

  public static Oracles read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var xs = new Oracle[19];
    i += Borsh.readArray(xs, Oracle::read, _data, i);
    final var len = getInt64LE(_data, i);
    return new Oracles(xs, len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(xs, 19, _data, i);
    putInt64LE(_data, i, len);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
