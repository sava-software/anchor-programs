package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeStructure(FeeTier[] feeTiers,
                           OrderFillerRewardStructure fillerRewardStructure,
                           long referrerRewardEpochUpperBound,
                           long flatFillerFee) implements Borsh {

  public static final int BYTES = 360;

  public static FeeStructure read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeTiers = new FeeTier[10];
    i += Borsh.readArray(feeTiers, FeeTier::read, _data, i);
    final var fillerRewardStructure = OrderFillerRewardStructure.read(_data, i);
    i += Borsh.len(fillerRewardStructure);
    final var referrerRewardEpochUpperBound = getInt64LE(_data, i);
    i += 8;
    final var flatFillerFee = getInt64LE(_data, i);
    return new FeeStructure(feeTiers,
                            fillerRewardStructure,
                            referrerRewardEpochUpperBound,
                            flatFillerFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(feeTiers, _data, i);
    i += Borsh.write(fillerRewardStructure, _data, i);
    putInt64LE(_data, i, referrerRewardEpochUpperBound);
    i += 8;
    putInt64LE(_data, i, flatFillerFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
