package software.sava.anchor.programs.meteora.dlmm.anchor.types;

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
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record PresetParameter2(PublicKey _address,
                               Discriminator discriminator,
                               // Bin step. Represent the price increment / decrement.
                               int binStep,
                               // Used for base fee calculation. base_fee_rate = base_factor * bin_step * 10 * 10^base_fee_power_factor
                               int baseFactor,
                               // Filter period determine high frequency trading time window.
                               int filterPeriod,
                               // Decay period determine when the volatile fee start decay / decrease.
                               int decayPeriod,
                               // Used to scale the variable fee component depending on the dynamic of the market
                               int variableFeeControl,
                               // Maximum number of bin crossed can be accumulated. Used to cap volatile fee rate.
                               int maxVolatilityAccumulator,
                               // Reduction factor controls the volatile fee rate decrement rate.
                               int reductionFactor,
                               // Portion of swap fees retained by the protocol by controlling protocol_share parameter. protocol_swap_fee = protocol_share * total_swap_fee
                               int protocolShare,
                               // index
                               int index,
                               // Base fee power factor
                               int baseFeePowerFactor,
                               // Padding 0 for future use
                               int padding0,
                               // Padding 1 for future use
                               long[] padding1) implements Borsh {

  public static final int BYTES = 192;
  public static final int PADDING_1_LEN = 20;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(171, 236, 148, 115, 162, 113, 222, 174);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int BIN_STEP_OFFSET = 8;
  public static final int BASE_FACTOR_OFFSET = 10;
  public static final int FILTER_PERIOD_OFFSET = 12;
  public static final int DECAY_PERIOD_OFFSET = 14;
  public static final int VARIABLE_FEE_CONTROL_OFFSET = 16;
  public static final int MAX_VOLATILITY_ACCUMULATOR_OFFSET = 20;
  public static final int REDUCTION_FACTOR_OFFSET = 24;
  public static final int PROTOCOL_SHARE_OFFSET = 26;
  public static final int INDEX_OFFSET = 28;
  public static final int BASE_FEE_POWER_FACTOR_OFFSET = 30;
  public static final int PADDING_0_OFFSET = 31;
  public static final int PADDING_1_OFFSET = 32;

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

  public static Filter createReductionFactorFilter(final int reductionFactor) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, reductionFactor);
    return Filter.createMemCompFilter(REDUCTION_FACTOR_OFFSET, _data);
  }

  public static Filter createProtocolShareFilter(final int protocolShare) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, protocolShare);
    return Filter.createMemCompFilter(PROTOCOL_SHARE_OFFSET, _data);
  }

  public static Filter createIndexFilter(final int index) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, index);
    return Filter.createMemCompFilter(INDEX_OFFSET, _data);
  }

  public static Filter createBaseFeePowerFactorFilter(final int baseFeePowerFactor) {
    return Filter.createMemCompFilter(BASE_FEE_POWER_FACTOR_OFFSET, new byte[]{(byte) baseFeePowerFactor});
  }

  public static Filter createPadding0Filter(final int padding0) {
    return Filter.createMemCompFilter(PADDING_0_OFFSET, new byte[]{(byte) padding0});
  }

  public static PresetParameter2 read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PresetParameter2 read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PresetParameter2 read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PresetParameter2> FACTORY = PresetParameter2::read;

  public static PresetParameter2 read(final PublicKey _address, final byte[] _data, final int offset) {
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
    final var variableFeeControl = getInt32LE(_data, i);
    i += 4;
    final var maxVolatilityAccumulator = getInt32LE(_data, i);
    i += 4;
    final var reductionFactor = getInt16LE(_data, i);
    i += 2;
    final var protocolShare = getInt16LE(_data, i);
    i += 2;
    final var index = getInt16LE(_data, i);
    i += 2;
    final var baseFeePowerFactor = _data[i] & 0xFF;
    ++i;
    final var padding0 = _data[i] & 0xFF;
    ++i;
    final var padding1 = new long[20];
    Borsh.readArray(padding1, _data, i);
    return new PresetParameter2(_address,
                                discriminator,
                                binStep,
                                baseFactor,
                                filterPeriod,
                                decayPeriod,
                                variableFeeControl,
                                maxVolatilityAccumulator,
                                reductionFactor,
                                protocolShare,
                                index,
                                baseFeePowerFactor,
                                padding0,
                                padding1);
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
    putInt32LE(_data, i, variableFeeControl);
    i += 4;
    putInt32LE(_data, i, maxVolatilityAccumulator);
    i += 4;
    putInt16LE(_data, i, reductionFactor);
    i += 2;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    putInt16LE(_data, i, index);
    i += 2;
    _data[i] = (byte) baseFeePowerFactor;
    ++i;
    _data[i] = (byte) padding0;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
