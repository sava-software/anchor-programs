package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public record BigFractionBytes(long[] value, long[] padding) implements Borsh {

  public static final int BYTES = 48;

  public static BigFractionBytes read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var value = new long[4];
    i += Borsh.readArray(value, _data, i);
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new BigFractionBytes(value, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(value, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
