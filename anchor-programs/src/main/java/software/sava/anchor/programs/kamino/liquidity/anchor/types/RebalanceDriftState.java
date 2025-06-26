package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RebalanceDriftState(RebalanceDriftStep step,
                                  long lastDriftTimestamp,
                                  int lastMidTick) implements Borsh {

  public static final int BYTES = 13;

  public static RebalanceDriftState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var step = RebalanceDriftStep.read(_data, i);
    i += Borsh.len(step);
    final var lastDriftTimestamp = getInt64LE(_data, i);
    i += 8;
    final var lastMidTick = getInt32LE(_data, i);
    return new RebalanceDriftState(step, lastDriftTimestamp, lastMidTick);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(step, _data, i);
    putInt64LE(_data, i, lastDriftTimestamp);
    i += 8;
    putInt32LE(_data, i, lastMidTick);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
