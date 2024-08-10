package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CurveRecord(long ts,
                          long recordId,
                          BigInteger pegMultiplierBefore,
                          BigInteger baseAssetReserveBefore,
                          BigInteger quoteAssetReserveBefore,
                          BigInteger sqrtKBefore,
                          BigInteger pegMultiplierAfter,
                          BigInteger baseAssetReserveAfter,
                          BigInteger quoteAssetReserveAfter,
                          BigInteger sqrtKAfter,
                          BigInteger baseAssetAmountLong,
                          BigInteger baseAssetAmountShort,
                          BigInteger baseAssetAmountWithAmm,
                          BigInteger totalFee,
                          BigInteger totalFeeMinusDistributions,
                          BigInteger adjustmentCost,
                          long oraclePrice,
                          BigInteger fillRecord,
                          int numberOfUsers,
                          int marketIndex) implements Borsh {

  public static final int BYTES = 270;

  public static CurveRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var recordId = getInt64LE(_data, i);
    i += 8;
    final var pegMultiplierBefore = getInt128LE(_data, i);
    i += 16;
    final var baseAssetReserveBefore = getInt128LE(_data, i);
    i += 16;
    final var quoteAssetReserveBefore = getInt128LE(_data, i);
    i += 16;
    final var sqrtKBefore = getInt128LE(_data, i);
    i += 16;
    final var pegMultiplierAfter = getInt128LE(_data, i);
    i += 16;
    final var baseAssetReserveAfter = getInt128LE(_data, i);
    i += 16;
    final var quoteAssetReserveAfter = getInt128LE(_data, i);
    i += 16;
    final var sqrtKAfter = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountLong = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountShort = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountWithAmm = getInt128LE(_data, i);
    i += 16;
    final var totalFee = getInt128LE(_data, i);
    i += 16;
    final var totalFeeMinusDistributions = getInt128LE(_data, i);
    i += 16;
    final var adjustmentCost = getInt128LE(_data, i);
    i += 16;
    final var oraclePrice = getInt64LE(_data, i);
    i += 8;
    final var fillRecord = getInt128LE(_data, i);
    i += 16;
    final var numberOfUsers = getInt32LE(_data, i);
    i += 4;
    final var marketIndex = getInt16LE(_data, i);
    return new CurveRecord(ts,
                           recordId,
                           pegMultiplierBefore,
                           baseAssetReserveBefore,
                           quoteAssetReserveBefore,
                           sqrtKBefore,
                           pegMultiplierAfter,
                           baseAssetReserveAfter,
                           quoteAssetReserveAfter,
                           sqrtKAfter,
                           baseAssetAmountLong,
                           baseAssetAmountShort,
                           baseAssetAmountWithAmm,
                           totalFee,
                           totalFeeMinusDistributions,
                           adjustmentCost,
                           oraclePrice,
                           fillRecord,
                           numberOfUsers,
                           marketIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt64LE(_data, i, recordId);
    i += 8;
    putInt128LE(_data, i, pegMultiplierBefore);
    i += 16;
    putInt128LE(_data, i, baseAssetReserveBefore);
    i += 16;
    putInt128LE(_data, i, quoteAssetReserveBefore);
    i += 16;
    putInt128LE(_data, i, sqrtKBefore);
    i += 16;
    putInt128LE(_data, i, pegMultiplierAfter);
    i += 16;
    putInt128LE(_data, i, baseAssetReserveAfter);
    i += 16;
    putInt128LE(_data, i, quoteAssetReserveAfter);
    i += 16;
    putInt128LE(_data, i, sqrtKAfter);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountLong);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountShort);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountWithAmm);
    i += 16;
    putInt128LE(_data, i, totalFee);
    i += 16;
    putInt128LE(_data, i, totalFeeMinusDistributions);
    i += 16;
    putInt128LE(_data, i, adjustmentCost);
    i += 16;
    putInt64LE(_data, i, oraclePrice);
    i += 8;
    putInt128LE(_data, i, fillRecord);
    i += 16;
    putInt32LE(_data, i, numberOfUsers);
    i += 4;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 8
         + 16
         + 4
         + 2;
  }
}