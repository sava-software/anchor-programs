package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StakeStats(long pendingActivation,
                         long activeAmount,
                         long pendingDeactivation,
                         long deactivatedAmount) implements Borsh {

  public static final int BYTES = 32;

  public static StakeStats read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pendingActivation = getInt64LE(_data, i);
    i += 8;
    final var activeAmount = getInt64LE(_data, i);
    i += 8;
    final var pendingDeactivation = getInt64LE(_data, i);
    i += 8;
    final var deactivatedAmount = getInt64LE(_data, i);
    return new StakeStats(pendingActivation,
                          activeAmount,
                          pendingDeactivation,
                          deactivatedAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, pendingActivation);
    i += 8;
    putInt64LE(_data, i, activeAmount);
    i += 8;
    putInt64LE(_data, i, pendingDeactivation);
    i += 8;
    putInt64LE(_data, i, deactivatedAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
