package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RebalanceAutodriftWindow(Price stakingRateA,
                                       Price stakingRateB,
                                       long epoch,
                                       int theoreticalTick,
                                       int stratMidTick) implements Borsh {

  public static RebalanceAutodriftWindow read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stakingRateA = _data[i++] == 0 ? null : Price.read(_data, i);
    if (stakingRateA != null) {
      i += Borsh.len(stakingRateA);
    }
    final var stakingRateB = _data[i++] == 0 ? null : Price.read(_data, i);
    if (stakingRateB != null) {
      i += Borsh.len(stakingRateB);
    }
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var theoreticalTick = getInt32LE(_data, i);
    i += 4;
    final var stratMidTick = getInt32LE(_data, i);
    return new RebalanceAutodriftWindow(stakingRateA,
                                        stakingRateB,
                                        epoch,
                                        theoreticalTick,
                                        stratMidTick);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(stakingRateA, _data, i);
    i += Borsh.writeOptional(stakingRateB, _data, i);
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt32LE(_data, i, theoreticalTick);
    i += 4;
    putInt32LE(_data, i, stratMidTick);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return (stakingRateA == null ? 1 : (1 + Borsh.len(stakingRateA)))
         + (stakingRateB == null ? 1 : (1 + Borsh.len(stakingRateB)))
         + 8
         + 4
         + 4;
  }
}
