package software.sava.anchor.programs.drift.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ConstituentParams(OptionalLong maxWeightDeviation,
                                OptionalLong swapFeeMin,
                                OptionalLong swapFeeMax,
                                OptionalLong maxBorrowTokenAmount,
                                OptionalLong oracleStalenessThreshold,
                                OptionalInt costToTradeBps,
                                OptionalInt constituentDerivativeIndex,
                                OptionalLong derivativeWeight,
                                OptionalLong volatility,
                                OptionalInt gammaExecution,
                                OptionalInt gammaInventory,
                                OptionalInt xi) implements Borsh {

  public static ConstituentParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxWeightDeviation = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxWeightDeviation.isPresent()) {
      i += 8;
    }
    final var swapFeeMin = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (swapFeeMin.isPresent()) {
      i += 8;
    }
    final var swapFeeMax = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (swapFeeMax.isPresent()) {
      i += 8;
    }
    final var maxBorrowTokenAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxBorrowTokenAmount.isPresent()) {
      i += 8;
    }
    final var oracleStalenessThreshold = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (oracleStalenessThreshold.isPresent()) {
      i += 8;
    }
    final var costToTradeBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (costToTradeBps.isPresent()) {
      i += 4;
    }
    final var constituentDerivativeIndex = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (constituentDerivativeIndex.isPresent()) {
      i += 2;
    }
    final var derivativeWeight = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (derivativeWeight.isPresent()) {
      i += 8;
    }
    final var volatility = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (volatility.isPresent()) {
      i += 8;
    }
    final var gammaExecution = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (gammaExecution.isPresent()) {
      ++i;
    }
    final var gammaInventory = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (gammaInventory.isPresent()) {
      ++i;
    }
    final var xi = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    return new ConstituentParams(maxWeightDeviation,
                                 swapFeeMin,
                                 swapFeeMax,
                                 maxBorrowTokenAmount,
                                 oracleStalenessThreshold,
                                 costToTradeBps,
                                 constituentDerivativeIndex,
                                 derivativeWeight,
                                 volatility,
                                 gammaExecution,
                                 gammaInventory,
                                 xi);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(maxWeightDeviation, _data, i);
    i += Borsh.writeOptional(swapFeeMin, _data, i);
    i += Borsh.writeOptional(swapFeeMax, _data, i);
    i += Borsh.writeOptional(maxBorrowTokenAmount, _data, i);
    i += Borsh.writeOptional(oracleStalenessThreshold, _data, i);
    i += Borsh.writeOptional(costToTradeBps, _data, i);
    i += Borsh.writeOptionalshort(constituentDerivativeIndex, _data, i);
    i += Borsh.writeOptional(derivativeWeight, _data, i);
    i += Borsh.writeOptional(volatility, _data, i);
    i += Borsh.writeOptionalbyte(gammaExecution, _data, i);
    i += Borsh.writeOptionalbyte(gammaInventory, _data, i);
    i += Borsh.writeOptionalbyte(xi, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (maxWeightDeviation == null || maxWeightDeviation.isEmpty() ? 1 : (1 + 8))
         + (swapFeeMin == null || swapFeeMin.isEmpty() ? 1 : (1 + 8))
         + (swapFeeMax == null || swapFeeMax.isEmpty() ? 1 : (1 + 8))
         + (maxBorrowTokenAmount == null || maxBorrowTokenAmount.isEmpty() ? 1 : (1 + 8))
         + (oracleStalenessThreshold == null || oracleStalenessThreshold.isEmpty() ? 1 : (1 + 8))
         + (costToTradeBps == null || costToTradeBps.isEmpty() ? 1 : (1 + 4))
         + (constituentDerivativeIndex == null || constituentDerivativeIndex.isEmpty() ? 1 : (1 + 2))
         + (derivativeWeight == null || derivativeWeight.isEmpty() ? 1 : (1 + 8))
         + (volatility == null || volatility.isEmpty() ? 1 : (1 + 8))
         + (gammaExecution == null || gammaExecution.isEmpty() ? 1 : (1 + 1))
         + (gammaInventory == null || gammaInventory.isEmpty() ? 1 : (1 + 1))
         + (xi == null || xi.isEmpty() ? 1 : (1 + 1));
  }
}
