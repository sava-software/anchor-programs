package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record SpotBankruptcyRecord(int marketIndex,
                                   BigInteger borrowAmount,
                                   BigInteger ifPayment,
                                   BigInteger cumulativeDepositInterestDelta) implements Borsh {

  public static final int BYTES = 50;

  public static SpotBankruptcyRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var borrowAmount = getInt128LE(_data, i);
    i += 16;
    final var ifPayment = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDepositInterestDelta = getInt128LE(_data, i);
    return new SpotBankruptcyRecord(marketIndex,
                                    borrowAmount,
                                    ifPayment,
                                    cumulativeDepositInterestDelta);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, borrowAmount);
    i += 16;
    putInt128LE(_data, i, ifPayment);
    i += 16;
    putInt128LE(_data, i, cumulativeDepositInterestDelta);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + 16 + 16 + 16;
  }
}
