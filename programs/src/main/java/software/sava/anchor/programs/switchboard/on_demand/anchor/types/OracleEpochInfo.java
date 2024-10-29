package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OracleEpochInfo(long id,
                              long reserved1,
                              long slotEnd,
                              long slashScore,
                              long rewardScore,
                              long stakeScore) implements Borsh {

  public static final int BYTES = 48;

  public static OracleEpochInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var id = getInt64LE(_data, i);
    i += 8;
    final var reserved1 = getInt64LE(_data, i);
    i += 8;
    final var slotEnd = getInt64LE(_data, i);
    i += 8;
    final var slashScore = getInt64LE(_data, i);
    i += 8;
    final var rewardScore = getInt64LE(_data, i);
    i += 8;
    final var stakeScore = getInt64LE(_data, i);
    return new OracleEpochInfo(id,
                               reserved1,
                               slotEnd,
                               slashScore,
                               rewardScore,
                               stakeScore);
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
    putInt64LE(_data, i, slashScore);
    i += 8;
    putInt64LE(_data, i, rewardScore);
    i += 8;
    putInt64LE(_data, i, stakeScore);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
