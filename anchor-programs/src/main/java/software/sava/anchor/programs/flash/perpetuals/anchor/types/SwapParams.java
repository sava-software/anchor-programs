package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapParams(long amountIn, long minAmountOut) implements Borsh {

  public static final int BYTES = 16;

  public static SwapParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var minAmountOut = getInt64LE(_data, i);
    return new SwapParams(amountIn, minAmountOut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minAmountOut);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
