package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record OrderFillerRewardStructure(int rewardNumerator,
                                         int rewardDenominator,
                                         BigInteger timeBasedRewardLowerBound) implements Borsh {

  public static final int BYTES = 24;

  public static OrderFillerRewardStructure read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardNumerator = getInt32LE(_data, i);
    i += 4;
    final var rewardDenominator = getInt32LE(_data, i);
    i += 4;
    final var timeBasedRewardLowerBound = getInt128LE(_data, i);
    return new OrderFillerRewardStructure(rewardNumerator, rewardDenominator, timeBasedRewardLowerBound);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, rewardNumerator);
    i += 4;
    putInt32LE(_data, i, rewardDenominator);
    i += 4;
    putInt128LE(_data, i, timeBasedRewardLowerBound);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
