package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

// A boolean type stored as a u8.
public record U8Bool(int value) implements Borsh {

  public static final int BYTES = 1;

  public static U8Bool read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var value = _data[offset] & 0xFF;
    return new U8Bool(value);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) value;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
