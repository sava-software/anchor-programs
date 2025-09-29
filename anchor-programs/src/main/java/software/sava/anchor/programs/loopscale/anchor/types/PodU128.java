package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// Represents a 128-bit unsigned integer stored as bytes (little-endian)
public record PodU128(byte[] _array) implements Borsh {

  public static final int BYTES = 16;
  public static final int _ARRAY_LEN = 16;

  public static PodU128 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _array = new byte[16];
    Borsh.readArray(_array, _data, offset);
    return new PodU128(_array);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(_array, 16, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
