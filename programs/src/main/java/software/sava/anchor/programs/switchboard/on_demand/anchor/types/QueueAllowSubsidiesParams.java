package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record QueueAllowSubsidiesParams(int allowSubsidies) implements Borsh {

  public static final int BYTES = 1;

  public static QueueAllowSubsidiesParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var allowSubsidies = _data[offset] & 0xFF;
    return new QueueAllowSubsidiesParams(allowSubsidies);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) allowSubsidies;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
