package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ExactOutParams(long amountOut, long maxAmountIn) implements Borsh {

  public static final int BYTES = 16;

  public static ExactOutParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var maxAmountIn = getInt64LE(_data, i);
    return new ExactOutParams(amountOut, maxAmountIn);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, maxAmountIn);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
