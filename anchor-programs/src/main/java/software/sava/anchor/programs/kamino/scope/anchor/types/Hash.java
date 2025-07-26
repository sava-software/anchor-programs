package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public record Hash(byte[] data) implements Borsh {

  public static final int BYTES = 32;
  public static final int DATA_LEN = 32;

  public static Hash read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var data = new byte[32];
    Borsh.readArray(data, _data, offset);
    return new Hash(data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
