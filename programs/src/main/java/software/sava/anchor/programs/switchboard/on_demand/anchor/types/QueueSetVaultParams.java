package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record QueueSetVaultParams(boolean enable) implements Borsh {

  public static final int BYTES = 1;

  public static QueueSetVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var enable = _data[offset] == 1;
    return new QueueSetVaultParams(enable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (enable ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
