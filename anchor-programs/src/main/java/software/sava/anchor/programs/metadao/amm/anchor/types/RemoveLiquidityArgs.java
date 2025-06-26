package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveLiquidityArgs(long lpTokensToBurn,
                                  long minQuoteAmount,
                                  long minBaseAmount) implements Borsh {

  public static final int BYTES = 24;

  public static RemoveLiquidityArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lpTokensToBurn = getInt64LE(_data, i);
    i += 8;
    final var minQuoteAmount = getInt64LE(_data, i);
    i += 8;
    final var minBaseAmount = getInt64LE(_data, i);
    return new RemoveLiquidityArgs(lpTokensToBurn, minQuoteAmount, minBaseAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lpTokensToBurn);
    i += 8;
    putInt64LE(_data, i, minQuoteAmount);
    i += 8;
    putInt64LE(_data, i, minBaseAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
