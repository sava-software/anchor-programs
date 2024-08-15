package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

public record BoolValueChange(boolean old, boolean _new) implements Borsh {

  public static final int BYTES = 2;

  public static BoolValueChange read(final byte[] _data, final int offset) {
    int i = offset;
    final var old = _data[i] == 1;
    ++i;
    final var _new = _data[i] == 1;
    return new BoolValueChange(old, _new);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (old ? 1 : 0);
    ++i;
    _data[i] = (byte) (_new ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
