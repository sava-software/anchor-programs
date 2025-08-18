package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateRewardsScheduleParams(long amountToTransfer,
                                          OptionalLong extendEndTime,
                                          int scheduleIndex) implements Borsh {

  public static UpdateRewardsScheduleParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountToTransfer = getInt64LE(_data, i);
    i += 8;
    final var extendEndTime = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (extendEndTime.isPresent()) {
      i += 8;
    }
    final var scheduleIndex = _data[i] & 0xFF;
    return new UpdateRewardsScheduleParams(amountToTransfer, extendEndTime, scheduleIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountToTransfer);
    i += 8;
    i += Borsh.writeOptional(extendEndTime, _data, i);
    _data[i] = (byte) scheduleIndex;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + (extendEndTime == null || extendEndTime.isEmpty() ? 1 : (1 + 8)) + 1;
  }
}
