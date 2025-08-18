package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

// helper type to store u32 cbps values
public record PodU32CBPS(byte[] _array) implements Borsh {

  public static final int BYTES = 4;
  public static final int _ARRAY_LEN = 4;

  public static PodU32CBPS read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var _array = new byte[4];
    Borsh.readArray(_array, _data, offset);
    return new PodU32CBPS(_array);
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
