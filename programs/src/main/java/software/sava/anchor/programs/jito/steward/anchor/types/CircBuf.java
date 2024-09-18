package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CircBuf(long idx,
                      int isEmpty,
                      byte[] padding,
                      ValidatorHistoryEntry[] arr) implements Borsh {

  public static final int BYTES = 65552;

  public static CircBuf read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var idx = getInt64LE(_data, i);
    i += 8;
    final var isEmpty = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[7];
    i += Borsh.readArray(padding, _data, i);
    final var arr = new ValidatorHistoryEntry[512];
    Borsh.readArray(arr, ValidatorHistoryEntry::read, _data, i);
    return new CircBuf(idx,
                       isEmpty,
                       padding,
                       arr);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, idx);
    i += 8;
    _data[i] = (byte) isEmpty;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    i += Borsh.writeArray(arr, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
