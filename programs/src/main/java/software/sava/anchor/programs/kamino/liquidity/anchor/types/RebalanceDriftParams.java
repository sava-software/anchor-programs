package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RebalanceDriftParams(int startMidTick,
                                   int ticksBelowMid,
                                   int ticksAboveMid,
                                   long secondsPerTick,
                                   DriftDirection direction) implements Borsh {

  public static final int BYTES = 21;

  public static RebalanceDriftParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var startMidTick = getInt32LE(_data, i);
    i += 4;
    final var ticksBelowMid = getInt32LE(_data, i);
    i += 4;
    final var ticksAboveMid = getInt32LE(_data, i);
    i += 4;
    final var secondsPerTick = getInt64LE(_data, i);
    i += 8;
    final var direction = DriftDirection.read(_data, i);
    return new RebalanceDriftParams(startMidTick,
                                    ticksBelowMid,
                                    ticksAboveMid,
                                    secondsPerTick,
                                    direction);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, startMidTick);
    i += 4;
    putInt32LE(_data, i, ticksBelowMid);
    i += 4;
    putInt32LE(_data, i, ticksAboveMid);
    i += 4;
    putInt64LE(_data, i, secondsPerTick);
    i += 8;
    i += Borsh.write(direction, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
