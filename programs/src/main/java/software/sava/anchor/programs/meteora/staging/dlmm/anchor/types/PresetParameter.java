package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record PresetParameter(PublicKey _address,
                              Discriminator discriminator,
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
                              // Min bin id supported by the pool based on the configured bin step.
                              int minBinId,
                              // Max bin id supported by the pool based on the configured bin step.
                              int maxBinId,
                              // Portion of swap fees retained by the protocol by controlling protocol_share parameter. protocol_swap_fee = protocol_share * total_swap_fee
                              int protocolShare) implements Borsh {

  public static final int BYTES = 36;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int BIN_STEP_OFFSET = 8;
  public static final int BASE_FACTOR_OFFSET = 10;
  public static final int FILTER_PERIOD_OFFSET = 12;
  public static final int DECAY_PERIOD_OFFSET = 14;
  public static final int REDUCTION_FACTOR_OFFSET = 16;
  public static final int VARIABLE_FEE_CONTROL_OFFSET = 18;
  public static final int MAX_VOLATILITY_ACCUMULATOR_OFFSET = 22;
  public static final int MIN_BIN_ID_OFFSET = 26;
  public static final int MAX_BIN_ID_OFFSET = 30;
  public static final int PROTOCOL_SHARE_OFFSET = 34;

  public static Filter createBinStepFilter(final int binStep) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, binStep);
    return Filter.createMemCompFilter(BIN_STEP_OFFSET, _data);
  }

  public static Filter createBaseFactorFilter(final int baseFactor) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, baseFactor);
    return Filter.createMemCompFilter(BASE_FACTOR_OFFSET, _data);
  }

  public static Filter createFilterPeriodFilter(final int filterPeriod) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, filterPeriod);
    return Filter.createMemCompFilter(FILTER_PERIOD_OFFSET, _data);
  }

  public static Filter createDecayPeriodFilter(final int decayPeriod) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, decayPeriod);
    return Filter.createMemCompFilter(DECAY_PERIOD_OFFSET, _data);
  }

  public static Filter createReductionFactorFilter(final int reductionFactor) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, reductionFactor);
    return Filter.createMemCompFilter(REDUCTION_FACTOR_OFFSET, _data);
  }

  public static Filter createVariableFeeControlFilter(final int variableFeeControl) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, variableFeeControl);
    return Filter.createMemCompFilter(VARIABLE_FEE_CONTROL_OFFSET, _data);
  }

  public static Filter createMaxVolatilityAccumulatorFilter(final int maxVolatilityAccumulator) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maxVolatilityAccumulator);
    return Filter.createMemCompFilter(MAX_VOLATILITY_ACCUMULATOR_OFFSET, _data);
  }

  public static Filter createMinBinIdFilter(final int minBinId) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, minBinId);
    return Filter.createMemCompFilter(MIN_BIN_ID_OFFSET, _data);
  }

  public static Filter createMaxBinIdFilter(final int maxBinId) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maxBinId);
    return Filter.createMemCompFilter(MAX_BIN_ID_OFFSET, _data);
  }

  public static Filter createProtocolShareFilter(final int protocolShare) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, protocolShare);
    return Filter.createMemCompFilter(PROTOCOL_SHARE_OFFSET, _data);
  }

  public static PresetParameter read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PresetParameter read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PresetParameter read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PresetParameter> FACTORY = PresetParameter::read;

  public static PresetParameter read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
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
    final var minBinId = getInt32LE(_data, i);
    i += 4;
    final var maxBinId = getInt32LE(_data, i);
    i += 4;
    final var protocolShare = getInt16LE(_data, i);
    return new PresetParameter(_address,
                               discriminator,
                               binStep,
                               baseFactor,
                               filterPeriod,
                               decayPeriod,
                               reductionFactor,
                               variableFeeControl,
                               maxVolatilityAccumulator,
                               minBinId,
                               maxBinId,
                               protocolShare);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
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
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
