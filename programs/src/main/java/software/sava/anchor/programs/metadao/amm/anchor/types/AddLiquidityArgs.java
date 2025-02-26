package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddLiquidityArgs(// How much quote token you will deposit to the pool
                               long quoteAmount,
                               // The maximum base token you will deposit to the pool
                               long maxBaseAmount,
                               // The minimum LP token you will get back
                               long minLpTokens) implements Borsh {

  public static final int BYTES = 24;

  public static AddLiquidityArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var quoteAmount = getInt64LE(_data, i);
    i += 8;
    final var maxBaseAmount = getInt64LE(_data, i);
    i += 8;
    final var minLpTokens = getInt64LE(_data, i);
    return new AddLiquidityArgs(quoteAmount, maxBaseAmount, minLpTokens);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, quoteAmount);
    i += 8;
    putInt64LE(_data, i, maxBaseAmount);
    i += 8;
    putInt64LE(_data, i, minLpTokens);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
