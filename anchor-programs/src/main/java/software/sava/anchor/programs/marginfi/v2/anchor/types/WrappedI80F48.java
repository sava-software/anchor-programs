package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public record WrappedI80F48(byte[] value) implements Borsh {

  public static final int BYTES = 16;
  public static final int VALUE_LEN = 16;

  public static WrappedI80F48 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var value = new byte[16];
    Borsh.readArray(value, _data, offset);
    return new WrappedI80F48(value);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(value, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
