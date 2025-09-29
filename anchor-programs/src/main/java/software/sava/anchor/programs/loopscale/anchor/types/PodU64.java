package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// Represents a 64-bit unsigned integer stored as bytes (little-endian)
public record PodU64(byte[] _array) implements Borsh {

  public static final int BYTES = 8;
  public static final int _ARRAY_LEN = 8;

  public static PodU64 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _array = new byte[8];
    Borsh.readArray(_array, _data, offset);
    return new PodU64(_array);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(_array, 8, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
