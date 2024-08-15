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

public record SpotInterestRecord(long ts,
                                 int marketIndex,
                                 BigInteger depositBalance,
                                 BigInteger cumulativeDepositInterest,
                                 BigInteger borrowBalance,
                                 BigInteger cumulativeBorrowInterest,
                                 int optimalUtilization,
                                 int optimalBorrowRate,
                                 int maxBorrowRate) implements Borsh {

  public static final int BYTES = 86;

  public static SpotInterestRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var depositBalance = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDepositInterest = getInt128LE(_data, i);
    i += 16;
    final var borrowBalance = getInt128LE(_data, i);
    i += 16;
    final var cumulativeBorrowInterest = getInt128LE(_data, i);
    i += 16;
    final var optimalUtilization = getInt32LE(_data, i);
    i += 4;
    final var optimalBorrowRate = getInt32LE(_data, i);
    i += 4;
    final var maxBorrowRate = getInt32LE(_data, i);
    return new SpotInterestRecord(ts,
                                  marketIndex,
                                  depositBalance,
                                  cumulativeDepositInterest,
                                  borrowBalance,
                                  cumulativeBorrowInterest,
                                  optimalUtilization,
                                  optimalBorrowRate,
                                  maxBorrowRate);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, depositBalance);
    i += 16;
    putInt128LE(_data, i, cumulativeDepositInterest);
    i += 16;
    putInt128LE(_data, i, borrowBalance);
    i += 16;
    putInt128LE(_data, i, cumulativeBorrowInterest);
    i += 16;
    putInt32LE(_data, i, optimalUtilization);
    i += 4;
    putInt32LE(_data, i, optimalBorrowRate);
    i += 4;
    putInt32LE(_data, i, maxBorrowRate);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
