package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GetAddCollateralQuoteData(long amountIn,
                                        long collateralDelta,
                                        long finalCollateralAmount,
                                        long finalCollateralUsd,
                                        long finalSizeAmount,
                                        long finalSizeUsd,
                                        OraclePrice finalAvgEntryPrice,
                                        boolean passesMinCollateralCheck,
                                        boolean passesLeverageCheck,
                                        boolean swapRequired,
                                        long receivingCustodyId,
                                        OraclePrice swapPrice,
                                        long swapFeeAmount,
                                        long swapFeeUsd,
                                        boolean swapPossible,
                                        OraclePrice increaseSizeEntryPrice,
                                        long increaseSizeFeeUsd,
                                        long increaseSizeFeeAmount) implements Borsh {

  public static final int BYTES = 128;

  public static GetAddCollateralQuoteData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var collateralDelta = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var finalSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var finalSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var finalAvgEntryPrice = OraclePrice.read(_data, i);
    i += Borsh.len(finalAvgEntryPrice);
    final var passesMinCollateralCheck = _data[i] == 1;
    ++i;
    final var passesLeverageCheck = _data[i] == 1;
    ++i;
    final var swapRequired = _data[i] == 1;
    ++i;
    final var receivingCustodyId = getInt64LE(_data, i);
    i += 8;
    final var swapPrice = OraclePrice.read(_data, i);
    i += Borsh.len(swapPrice);
    final var swapFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var swapFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var swapPossible = _data[i] == 1;
    ++i;
    final var increaseSizeEntryPrice = OraclePrice.read(_data, i);
    i += Borsh.len(increaseSizeEntryPrice);
    final var increaseSizeFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var increaseSizeFeeAmount = getInt64LE(_data, i);
    return new GetAddCollateralQuoteData(amountIn,
                                         collateralDelta,
                                         finalCollateralAmount,
                                         finalCollateralUsd,
                                         finalSizeAmount,
                                         finalSizeUsd,
                                         finalAvgEntryPrice,
                                         passesMinCollateralCheck,
                                         passesLeverageCheck,
                                         swapRequired,
                                         receivingCustodyId,
                                         swapPrice,
                                         swapFeeAmount,
                                         swapFeeUsd,
                                         swapPossible,
                                         increaseSizeEntryPrice,
                                         increaseSizeFeeUsd,
                                         increaseSizeFeeAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, collateralDelta);
    i += 8;
    putInt64LE(_data, i, finalCollateralAmount);
    i += 8;
    putInt64LE(_data, i, finalCollateralUsd);
    i += 8;
    putInt64LE(_data, i, finalSizeAmount);
    i += 8;
    putInt64LE(_data, i, finalSizeUsd);
    i += 8;
    i += Borsh.write(finalAvgEntryPrice, _data, i);
    _data[i] = (byte) (passesMinCollateralCheck ? 1 : 0);
    ++i;
    _data[i] = (byte) (passesLeverageCheck ? 1 : 0);
    ++i;
    _data[i] = (byte) (swapRequired ? 1 : 0);
    ++i;
    putInt64LE(_data, i, receivingCustodyId);
    i += 8;
    i += Borsh.write(swapPrice, _data, i);
    putInt64LE(_data, i, swapFeeAmount);
    i += 8;
    putInt64LE(_data, i, swapFeeUsd);
    i += 8;
    _data[i] = (byte) (swapPossible ? 1 : 0);
    ++i;
    i += Borsh.write(increaseSizeEntryPrice, _data, i);
    putInt64LE(_data, i, increaseSizeFeeUsd);
    i += 8;
    putInt64LE(_data, i, increaseSizeFeeAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
