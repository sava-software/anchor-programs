package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CloseQuoteData(long sizeAmount,
                             long sizeUsd,
                             OraclePrice exitPrice,
                             long collateralReturn,
                             long feeAmount,
                             long feeUsd,
                             long pnlAmount,
                             long pnlUsd,
                             boolean isProfitable,
                             long remainingCollateral,
                             long remainingSize) implements Borsh {

  public static final int BYTES = 85;

  public static CloseQuoteData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var exitPrice = OraclePrice.read(_data, i);
    i += Borsh.len(exitPrice);
    final var collateralReturn = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var feeUsd = getInt64LE(_data, i);
    i += 8;
    final var pnlAmount = getInt64LE(_data, i);
    i += 8;
    final var pnlUsd = getInt64LE(_data, i);
    i += 8;
    final var isProfitable = _data[i] == 1;
    ++i;
    final var remainingCollateral = getInt64LE(_data, i);
    i += 8;
    final var remainingSize = getInt64LE(_data, i);
    return new CloseQuoteData(sizeAmount,
                              sizeUsd,
                              exitPrice,
                              collateralReturn,
                              feeAmount,
                              feeUsd,
                              pnlAmount,
                              pnlUsd,
                              isProfitable,
                              remainingCollateral,
                              remainingSize);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    i += Borsh.write(exitPrice, _data, i);
    putInt64LE(_data, i, collateralReturn);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, feeUsd);
    i += 8;
    putInt64LE(_data, i, pnlAmount);
    i += 8;
    putInt64LE(_data, i, pnlUsd);
    i += 8;
    _data[i] = (byte) (isProfitable ? 1 : 0);
    ++i;
    putInt64LE(_data, i, remainingCollateral);
    i += 8;
    putInt64LE(_data, i, remainingSize);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
