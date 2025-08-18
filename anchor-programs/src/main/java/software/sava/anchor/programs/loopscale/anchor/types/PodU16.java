package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// Represents a 16-bit unsigned integer stored as bytes (little-endian)
public record PodU16(byte[] _array) implements Borsh {

  public static final int BYTES = 2;
  public static final int _ARRAY_LEN = 2;

  public static PodU16 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _array = new byte[2];
    Borsh.readArray(_array, _data, offset);
    return new PodU16(_array);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(_array, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
