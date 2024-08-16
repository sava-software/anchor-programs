package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SpotMarketVaultDepositRecord(long ts,
                                           int marketIndex,
                                           BigInteger depositBalance,
                                           BigInteger cumulativeDepositInterestBefore,
                                           BigInteger cumulativeDepositInterestAfter,
                                           long depositTokenAmountBefore,
                                           long amount) implements Borsh {

  public static final int BYTES = 74;

  public static SpotMarketVaultDepositRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var depositBalance = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDepositInterestBefore = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDepositInterestAfter = getInt128LE(_data, i);
    i += 16;
    final var depositTokenAmountBefore = getInt64LE(_data, i);
    i += 8;
    final var amount = getInt64LE(_data, i);
    return new SpotMarketVaultDepositRecord(ts,
                                            marketIndex,
                                            depositBalance,
                                            cumulativeDepositInterestBefore,
                                            cumulativeDepositInterestAfter,
                                            depositTokenAmountBefore,
                                            amount);
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
    putInt128LE(_data, i, cumulativeDepositInterestBefore);
    i += 16;
    putInt128LE(_data, i, cumulativeDepositInterestAfter);
    i += 16;
    putInt64LE(_data, i, depositTokenAmountBefore);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
