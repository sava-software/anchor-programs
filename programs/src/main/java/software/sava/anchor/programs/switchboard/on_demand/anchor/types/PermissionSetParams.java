package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record PermissionSetParams(int permission, boolean enable) implements Borsh {

  public static final int BYTES = 2;

  public static PermissionSetParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var permission = _data[i] & 0xFF;
    ++i;
    final var enable = _data[i] == 1;
    return new PermissionSetParams(permission, enable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) permission;
    ++i;
    _data[i] = (byte) (enable ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
