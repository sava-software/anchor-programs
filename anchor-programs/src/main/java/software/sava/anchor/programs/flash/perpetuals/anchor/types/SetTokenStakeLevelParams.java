package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetTokenStakeLevelParams(int level) implements Borsh {

  public static final int BYTES = 1;

  public static SetTokenStakeLevelParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var level = _data[offset] & 0xFF;
    return new SetTokenStakeLevelParams(level);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) level;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
