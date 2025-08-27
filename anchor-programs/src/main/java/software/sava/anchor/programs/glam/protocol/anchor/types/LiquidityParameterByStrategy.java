package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidityParameterByStrategy(long amountX,
                                           long amountY,
                                           int activeId,
                                           int maxActiveBinSlippage,
                                           StrategyParameters strategyParameters) implements Borsh {

  public static final int BYTES = 97;

  public static LiquidityParameterByStrategy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountX = getInt64LE(_data, i);
    i += 8;
    final var amountY = getInt64LE(_data, i);
    i += 8;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var maxActiveBinSlippage = getInt32LE(_data, i);
    i += 4;
    final var strategyParameters = StrategyParameters.read(_data, i);
    return new LiquidityParameterByStrategy(amountX,
                                            amountY,
                                            activeId,
                                            maxActiveBinSlippage,
                                            strategyParameters);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountX);
    i += 8;
    putInt64LE(_data, i, amountY);
    i += 8;
    putInt32LE(_data, i, activeId);
    i += 4;
    putInt32LE(_data, i, maxActiveBinSlippage);
    i += 4;
    i += Borsh.write(strategyParameters, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
