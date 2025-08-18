package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// Represents a bool stored as a byte
public record PodBool(int _u8) implements Borsh {

  public static final int BYTES = 1;

  public static PodBool read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _u8 = _data[offset] & 0xFF;
    return new PodBool(_u8);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) _u8;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
