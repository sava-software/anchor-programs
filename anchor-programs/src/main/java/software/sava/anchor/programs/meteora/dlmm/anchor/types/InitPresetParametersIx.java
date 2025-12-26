package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record InitPresetParametersIx(int index,
                                     // Bin step. Represent the price increment / decrement.
                                     int binStep,
                                     // Used for base fee calculation. base_fee_rate = base_factor * bin_step * 10 * 10^base_fee_power_factor
                                     int baseFactor,
                                     // Filter period determine high frequency trading time window.
                                     int filterPeriod,
                                     // Decay period determine when the volatile fee start decay / decrease.
                                     int decayPeriod,
                                     // Reduction factor controls the volatile fee rate decrement rate.
                                     int reductionFactor,
                                     // Used to scale the variable fee component depending on the dynamic of the market
                                     int variableFeeControl,
                                     // Maximum number of bin crossed can be accumulated. Used to cap volatile fee rate.
                                     int maxVolatilityAccumulator,
                                     // Portion of swap fees retained by the protocol by controlling protocol_share parameter. protocol_swap_fee = protocol_share * total_swap_fee
                                     int protocolShare,
                                     // Base fee power factor
                                     int baseFeePowerFactor,
                                     // function type
                                     int functionType) implements Borsh {

  public static final int BYTES = 24;

  public static InitPresetParametersIx read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var index = getInt16LE(_data, i);
    i += 2;
    final var binStep = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    i += 2;
    final var filterPeriod = getInt16LE(_data, i);
    i += 2;
    final var decayPeriod = getInt16LE(_data, i);
    i += 2;
    final var reductionFactor = getInt16LE(_data, i);
    i += 2;
    final var variableFeeControl = getInt32LE(_data, i);
    i += 4;
    final var maxVolatilityAccumulator = getInt32LE(_data, i);
    i += 4;
    final var protocolShare = getInt16LE(_data, i);
    i += 2;
    final var baseFeePowerFactor = _data[i] & 0xFF;
    ++i;
    final var functionType = _data[i] & 0xFF;
    return new InitPresetParametersIx(index,
                                      binStep,
                                      baseFactor,
                                      filterPeriod,
                                      decayPeriod,
                                      reductionFactor,
                                      variableFeeControl,
                                      maxVolatilityAccumulator,
                                      protocolShare,
                                      baseFeePowerFactor,
                                      functionType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, index);
    i += 2;
    putInt16LE(_data, i, binStep);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
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
    putInt16LE(_data, i, protocolShare);
    i += 2;
    _data[i] = (byte) baseFeePowerFactor;
    ++i;
    _data[i] = (byte) functionType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
