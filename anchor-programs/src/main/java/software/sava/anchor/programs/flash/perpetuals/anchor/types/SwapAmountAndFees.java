package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapAmountAndFees(long amountOut,
                                long feeIn,
                                long feeOut) implements Borsh {

  public static final int BYTES = 24;

  public static SwapAmountAndFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var feeIn = getInt64LE(_data, i);
    i += 8;
    final var feeOut = getInt64LE(_data, i);
    return new SwapAmountAndFees(amountOut, feeIn, feeOut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, feeIn);
    i += 8;
    putInt64LE(_data, i, feeOut);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
