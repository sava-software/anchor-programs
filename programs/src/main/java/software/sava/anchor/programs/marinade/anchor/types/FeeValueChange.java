package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

public record FeeValueChange(Fee old, Fee _new) implements Borsh {

  public static final int BYTES = 8;

  public static FeeValueChange read(final byte[] _data, final int offset) {
    int i = offset;
    final var old = Fee.read(_data, i);
    i += Borsh.len(old);
    final var _new = Fee.read(_data, i);
    return new FeeValueChange(old, _new);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(old, _data, i);
    i += Borsh.write(_new, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
