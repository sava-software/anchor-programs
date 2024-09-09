package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record FeeTier(int feeNumerator,
                      int feeDenominator,
                      int makerRebateNumerator,
                      int makerRebateDenominator,
                      int referrerRewardNumerator,
                      int referrerRewardDenominator,
                      int refereeFeeNumerator,
                      int refereeFeeDenominator) implements Borsh {

  public static final int BYTES = 32;

  public static FeeTier read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeNumerator = getInt32LE(_data, i);
    i += 4;
    final var feeDenominator = getInt32LE(_data, i);
    i += 4;
    final var makerRebateNumerator = getInt32LE(_data, i);
    i += 4;
    final var makerRebateDenominator = getInt32LE(_data, i);
    i += 4;
    final var referrerRewardNumerator = getInt32LE(_data, i);
    i += 4;
    final var referrerRewardDenominator = getInt32LE(_data, i);
    i += 4;
    final var refereeFeeNumerator = getInt32LE(_data, i);
    i += 4;
    final var refereeFeeDenominator = getInt32LE(_data, i);
    return new FeeTier(feeNumerator,
                       feeDenominator,
                       makerRebateNumerator,
                       makerRebateDenominator,
                       referrerRewardNumerator,
                       referrerRewardDenominator,
                       refereeFeeNumerator,
                       refereeFeeDenominator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, feeNumerator);
    i += 4;
    putInt32LE(_data, i, feeDenominator);
    i += 4;
    putInt32LE(_data, i, makerRebateNumerator);
    i += 4;
    putInt32LE(_data, i, makerRebateDenominator);
    i += 4;
    putInt32LE(_data, i, referrerRewardNumerator);
    i += 4;
    putInt32LE(_data, i, referrerRewardDenominator);
    i += 4;
    putInt32LE(_data, i, refereeFeeNumerator);
    i += 4;
    putInt32LE(_data, i, refereeFeeDenominator);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
