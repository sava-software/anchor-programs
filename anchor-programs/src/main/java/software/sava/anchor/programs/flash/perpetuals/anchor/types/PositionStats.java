package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PositionStats(long openPositions,
                            long updateTime,
                            OraclePrice averageEntryPrice,
                            long sizeAmount,
                            long sizeUsd,
                            long lockedAmount,
                            long lockedUsd,
                            long collateralAmount,
                            long collateralUsd,
                            long unsettledFeeUsd,
                            BigInteger cumulativeLockFeeSnapshot,
                            int sizeDecimals,
                            int lockedDecimals,
                            int collateralDecimals) implements Borsh {

  public static final int BYTES = 103;

  public static PositionStats read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var openPositions = getInt64LE(_data, i);
    i += 8;
    final var updateTime = getInt64LE(_data, i);
    i += 8;
    final var averageEntryPrice = OraclePrice.read(_data, i);
    i += Borsh.len(averageEntryPrice);
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var lockedAmount = getInt64LE(_data, i);
    i += 8;
    final var lockedUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var unsettledFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var cumulativeLockFeeSnapshot = getInt128LE(_data, i);
    i += 16;
    final var sizeDecimals = _data[i] & 0xFF;
    ++i;
    final var lockedDecimals = _data[i] & 0xFF;
    ++i;
    final var collateralDecimals = _data[i] & 0xFF;
    return new PositionStats(openPositions,
                             updateTime,
                             averageEntryPrice,
                             sizeAmount,
                             sizeUsd,
                             lockedAmount,
                             lockedUsd,
                             collateralAmount,
                             collateralUsd,
                             unsettledFeeUsd,
                             cumulativeLockFeeSnapshot,
                             sizeDecimals,
                             lockedDecimals,
                             collateralDecimals);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, openPositions);
    i += 8;
    putInt64LE(_data, i, updateTime);
    i += 8;
    i += Borsh.write(averageEntryPrice, _data, i);
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, lockedAmount);
    i += 8;
    putInt64LE(_data, i, lockedUsd);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, unsettledFeeUsd);
    i += 8;
    putInt128LE(_data, i, cumulativeLockFeeSnapshot);
    i += 16;
    _data[i] = (byte) sizeDecimals;
    ++i;
    _data[i] = (byte) lockedDecimals;
    ++i;
    _data[i] = (byte) collateralDecimals;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
