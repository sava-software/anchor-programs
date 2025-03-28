package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidityParameterByStrategyOneSide(// Amount of X token or Y token to deposit
                                                  long amount,
                                                  // Active bin that integrator observe off-chain
                                                  int activeId,
                                                  // max active bin slippage allowed
                                                  int maxActiveBinSlippage,
                                                  // strategy parameters
                                                  StrategyParameters strategyParameters) implements Borsh {

  public static final int BYTES = 89;

  public static LiquidityParameterByStrategyOneSide read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var maxActiveBinSlippage = getInt32LE(_data, i);
    i += 4;
    final var strategyParameters = StrategyParameters.read(_data, i);
    return new LiquidityParameterByStrategyOneSide(amount,
                                                   activeId,
                                                   maxActiveBinSlippage,
                                                   strategyParameters);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
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
