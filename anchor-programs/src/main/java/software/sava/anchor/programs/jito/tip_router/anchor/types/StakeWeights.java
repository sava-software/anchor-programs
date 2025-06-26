package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record StakeWeights(BigInteger stakeWeight, NcnFeeGroupWeight[] ncnFeeGroupStakeWeights) implements Borsh {

  public static final int BYTES = 144;

  public static StakeWeights read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stakeWeight = getInt128LE(_data, i);
    i += 16;
    final var ncnFeeGroupStakeWeights = new NcnFeeGroupWeight[8];
    Borsh.readArray(ncnFeeGroupStakeWeights, NcnFeeGroupWeight::read, _data, i);
    return new StakeWeights(stakeWeight, ncnFeeGroupStakeWeights);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, stakeWeight);
    i += 16;
    i += Borsh.writeArray(ncnFeeGroupStakeWeights, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
