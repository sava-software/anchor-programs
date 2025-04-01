package software.sava.anchor.programs.glam.staging.anchor.types;

import software.sava.core.borsh.Borsh;

public record InitObligationArgs(int tag, int id) implements Borsh {

  public static final int BYTES = 2;

  public static InitObligationArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tag = _data[i] & 0xFF;
    ++i;
    final var id = _data[i] & 0xFF;
    return new InitObligationArgs(tag, id);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) tag;
    ++i;
    _data[i] = (byte) id;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
