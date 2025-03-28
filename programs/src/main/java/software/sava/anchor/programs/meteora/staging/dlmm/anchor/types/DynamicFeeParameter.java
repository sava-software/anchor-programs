package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record DynamicFeeParameter(// Filter period determine high frequency trading time window.
                                  int filterPeriod,
                                  // Decay period determine when the volatile fee start decay / decrease.
                                  int decayPeriod,
                                  // Reduction factor controls the volatile fee rate decrement rate.
                                  int reductionFactor,
                                  // Used to scale the variable fee component depending on the dynamic of the market
                                  int variableFeeControl,
                                  // Maximum number of bin crossed can be accumulated. Used to cap volatile fee rate.
                                  int maxVolatilityAccumulator) implements Borsh {

  public static final int BYTES = 14;

  public static DynamicFeeParameter read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var filterPeriod = getInt16LE(_data, i);
    i += 2;
    final var decayPeriod = getInt16LE(_data, i);
    i += 2;
    final var reductionFactor = getInt16LE(_data, i);
    i += 2;
    final var variableFeeControl = getInt32LE(_data, i);
    i += 4;
    final var maxVolatilityAccumulator = getInt32LE(_data, i);
    return new DynamicFeeParameter(filterPeriod,
                                   decayPeriod,
                                   reductionFactor,
                                   variableFeeControl,
                                   maxVolatilityAccumulator);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, filterPeriod);
    i += 2;
    putInt16LE(_data, i, decayPeriod);
    i += 2;
    putInt16LE(_data, i, reductionFactor);
    i += 2;
    putInt32LE(_data, i, variableFeeControl);
    i += 4;
    putInt32LE(_data, i, maxVolatilityAccumulator);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
