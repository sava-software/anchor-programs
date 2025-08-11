package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VoltageMultiplier(long volume,
                                long rewards,
                                long rebates) implements Borsh {

  public static final int BYTES = 24;

  public static VoltageMultiplier read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var volume = getInt64LE(_data, i);
    i += 8;
    final var rewards = getInt64LE(_data, i);
    i += 8;
    final var rebates = getInt64LE(_data, i);
    return new VoltageMultiplier(volume, rewards, rebates);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, volume);
    i += 8;
    putInt64LE(_data, i, rewards);
    i += 8;
    putInt64LE(_data, i, rebates);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
