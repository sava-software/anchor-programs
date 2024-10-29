package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StateEpochInfo(long id,
                             long reserved1,
                             long slotEnd) implements Borsh {

  public static final int BYTES = 24;

  public static StateEpochInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var id = getInt64LE(_data, i);
    i += 8;
    final var reserved1 = getInt64LE(_data, i);
    i += 8;
    final var slotEnd = getInt64LE(_data, i);
    return new StateEpochInfo(id, reserved1, slotEnd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, id);
    i += 8;
    putInt64LE(_data, i, reserved1);
    i += 8;
    putInt64LE(_data, i, slotEnd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
