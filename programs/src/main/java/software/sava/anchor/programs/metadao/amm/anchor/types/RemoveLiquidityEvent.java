package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveLiquidityEvent(CommonFields common,
                                   long lpTokensBurned,
                                   long minQuoteAmount,
                                   long minBaseAmount,
                                   long baseAmount,
                                   long quoteAmount) implements Borsh {

  public static final int BYTES = 192;

  public static RemoveLiquidityEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var lpTokensBurned = getInt64LE(_data, i);
    i += 8;
    final var minQuoteAmount = getInt64LE(_data, i);
    i += 8;
    final var minBaseAmount = getInt64LE(_data, i);
    i += 8;
    final var baseAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteAmount = getInt64LE(_data, i);
    return new RemoveLiquidityEvent(common,
                                    lpTokensBurned,
                                    minQuoteAmount,
                                    minBaseAmount,
                                    baseAmount,
                                    quoteAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    putInt64LE(_data, i, lpTokensBurned);
    i += 8;
    putInt64LE(_data, i, minQuoteAmount);
    i += 8;
    putInt64LE(_data, i, minBaseAmount);
    i += 8;
    putInt64LE(_data, i, baseAmount);
    i += 8;
    putInt64LE(_data, i, quoteAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
