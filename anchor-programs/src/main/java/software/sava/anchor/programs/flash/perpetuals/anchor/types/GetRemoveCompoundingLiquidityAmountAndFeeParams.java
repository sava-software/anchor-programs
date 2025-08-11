package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GetRemoveCompoundingLiquidityAmountAndFeeParams(long compoundingAmountIn) implements Borsh {

  public static final int BYTES = 8;

  public static GetRemoveCompoundingLiquidityAmountAndFeeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var compoundingAmountIn = getInt64LE(_data, offset);
    return new GetRemoveCompoundingLiquidityAmountAndFeeParams(compoundingAmountIn);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, compoundingAmountIn);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
