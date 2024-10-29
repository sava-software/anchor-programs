package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MegaSlotInfo(long reserved1,
                           long slotEnd,
                           long perfGoal,
                           long currentSignatureCount) implements Borsh {

  public static final int BYTES = 32;

  public static MegaSlotInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var reserved1 = getInt64LE(_data, i);
    i += 8;
    final var slotEnd = getInt64LE(_data, i);
    i += 8;
    final var perfGoal = getInt64LE(_data, i);
    i += 8;
    final var currentSignatureCount = getInt64LE(_data, i);
    return new MegaSlotInfo(reserved1,
                            slotEnd,
                            perfGoal,
                            currentSignatureCount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, reserved1);
    i += 8;
    putInt64LE(_data, i, slotEnd);
    i += 8;
    putInt64LE(_data, i, perfGoal);
    i += 8;
    putInt64LE(_data, i, currentSignatureCount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
