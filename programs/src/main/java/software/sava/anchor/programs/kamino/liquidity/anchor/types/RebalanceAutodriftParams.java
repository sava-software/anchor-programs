package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record RebalanceAutodriftParams(int initDriftTicksPerEpoch,
                                       int ticksBelowMid,
                                       int ticksAboveMid,
                                       int frontrunMultiplierBps,
                                       StakingRateSource stakingRateASource,
                                       StakingRateSource stakingRateBSource,
                                       DriftDirection initDriftDirection) implements Borsh {

  public static final int BYTES = 17;

  public static RebalanceAutodriftParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var initDriftTicksPerEpoch = getInt32LE(_data, i);
    i += 4;
    final var ticksBelowMid = getInt32LE(_data, i);
    i += 4;
    final var ticksAboveMid = getInt32LE(_data, i);
    i += 4;
    final var frontrunMultiplierBps = getInt16LE(_data, i);
    i += 2;
    final var stakingRateASource = StakingRateSource.read(_data, i);
    i += Borsh.len(stakingRateASource);
    final var stakingRateBSource = StakingRateSource.read(_data, i);
    i += Borsh.len(stakingRateBSource);
    final var initDriftDirection = DriftDirection.read(_data, i);
    return new RebalanceAutodriftParams(initDriftTicksPerEpoch,
                                        ticksBelowMid,
                                        ticksAboveMid,
                                        frontrunMultiplierBps,
                                        stakingRateASource,
                                        stakingRateBSource,
                                        initDriftDirection);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, initDriftTicksPerEpoch);
    i += 4;
    putInt32LE(_data, i, ticksBelowMid);
    i += 4;
    putInt32LE(_data, i, ticksAboveMid);
    i += 4;
    putInt16LE(_data, i, frontrunMultiplierBps);
    i += 2;
    i += Borsh.write(stakingRateASource, _data, i);
    i += Borsh.write(stakingRateBSource, _data, i);
    i += Borsh.write(initDriftDirection, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
