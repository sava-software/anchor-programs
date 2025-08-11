package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record QuoteData(long amountIn,
                        long collateralAmount,
                        long collateralUsd,
                        long sizeAmount,
                        long sizeUsd,
                        OraclePrice entryPrice,
                        long totalFeeAmount,
                        long totalFeeUsd,
                        long entryFeeAmount,
                        long entryFeeUsd,
                        long volatilityFeeAmount,
                        long volatilityFeeUsd,
                        boolean swapRequired,
                        long swapFeeAmount,
                        long swapFeeUsd,
                        OraclePrice liquidationPrice) implements Borsh {

  public static final int BYTES = 129;

  public static QuoteData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var entryPrice = OraclePrice.read(_data, i);
    i += Borsh.len(entryPrice);
    final var totalFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var totalFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var entryFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var entryFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var volatilityFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var volatilityFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var swapRequired = _data[i] == 1;
    ++i;
    final var swapFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var swapFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var liquidationPrice = OraclePrice.read(_data, i);
    return new QuoteData(amountIn,
                         collateralAmount,
                         collateralUsd,
                         sizeAmount,
                         sizeUsd,
                         entryPrice,
                         totalFeeAmount,
                         totalFeeUsd,
                         entryFeeAmount,
                         entryFeeUsd,
                         volatilityFeeAmount,
                         volatilityFeeUsd,
                         swapRequired,
                         swapFeeAmount,
                         swapFeeUsd,
                         liquidationPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    i += Borsh.write(entryPrice, _data, i);
    putInt64LE(_data, i, totalFeeAmount);
    i += 8;
    putInt64LE(_data, i, totalFeeUsd);
    i += 8;
    putInt64LE(_data, i, entryFeeAmount);
    i += 8;
    putInt64LE(_data, i, entryFeeUsd);
    i += 8;
    putInt64LE(_data, i, volatilityFeeAmount);
    i += 8;
    putInt64LE(_data, i, volatilityFeeUsd);
    i += 8;
    _data[i] = (byte) (swapRequired ? 1 : 0);
    ++i;
    putInt64LE(_data, i, swapFeeAmount);
    i += 8;
    putInt64LE(_data, i, swapFeeUsd);
    i += 8;
    i += Borsh.write(liquidationPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
