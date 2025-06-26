package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

public record FeeCentsValueChange(FeeCents old, FeeCents _new) implements Borsh {

  public static final int BYTES = 8;

  public static FeeCentsValueChange read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var old = FeeCents.read(_data, i);
    i += Borsh.len(old);
    final var _new = FeeCents.read(_data, i);
    return new FeeCentsValueChange(old, _new);
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
