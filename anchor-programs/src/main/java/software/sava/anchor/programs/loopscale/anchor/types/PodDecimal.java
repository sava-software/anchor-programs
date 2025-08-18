package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// this is the scaled representation of a whole number. The whole number is scaled by 10^18 to avoid floating point errors when performing arithmetic operations.
public record PodDecimal(byte[] _array) implements Borsh {

  public static final int BYTES = 24;
  public static final int _ARRAY_LEN = 24;

  public static PodDecimal read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _array = new byte[24];
    Borsh.readArray(_array, _data, offset);
    return new PodDecimal(_array);
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
