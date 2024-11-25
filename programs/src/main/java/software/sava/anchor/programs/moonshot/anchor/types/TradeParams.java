package software.sava.anchor.programs.moonshot.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TradeParams(long tokenAmount,
                          long collateralAmount,
                          int fixedSide,
                          long slippageBps) implements Borsh {

  public static final int BYTES = 25;

  public static TradeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tokenAmount = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var fixedSide = _data[i] & 0xFF;
    ++i;
    final var slippageBps = getInt64LE(_data, i);
    return new TradeParams(tokenAmount,
                           collateralAmount,
                           fixedSide,
                           slippageBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tokenAmount);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    _data[i] = (byte) fixedSide;
    ++i;
    putInt64LE(_data, i, slippageBps);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
