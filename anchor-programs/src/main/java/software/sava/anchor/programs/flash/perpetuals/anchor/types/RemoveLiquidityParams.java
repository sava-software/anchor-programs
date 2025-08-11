package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveLiquidityParams(long lpAmountIn, long minAmountOut) implements Borsh {

  public static final int BYTES = 16;

  public static RemoveLiquidityParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lpAmountIn = getInt64LE(_data, i);
    i += 8;
    final var minAmountOut = getInt64LE(_data, i);
    return new RemoveLiquidityParams(lpAmountIn, minAmountOut);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lpAmountIn);
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
