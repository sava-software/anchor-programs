package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DepositRecord(long ts,
                            PublicKey userAuthority,
                            PublicKey user,
                            DepositDirection direction,
                            long depositRecordId,
                            long amount,
                            int marketIndex,
                            long oraclePrice,
                            BigInteger marketDepositBalance,
                            BigInteger marketWithdrawBalance,
                            BigInteger marketCumulativeDepositInterest,
                            BigInteger marketCumulativeBorrowInterest,
                            long totalDepositsAfter,
                            long totalWithdrawsAfter,
                            DepositExplanation explanation,
                            PublicKey transferUser) implements Borsh {

  public static DepositRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var userAuthority = readPubKey(_data, i);
    i += 32;
    final var user = readPubKey(_data, i);
    i += 32;
    final var direction = DepositDirection.read(_data, i);
    i += Borsh.len(direction);
    final var depositRecordId = getInt64LE(_data, i);
    i += 8;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var oraclePrice = getInt64LE(_data, i);
    i += 8;
    final var marketDepositBalance = getInt128LE(_data, i);
    i += 16;
    final var marketWithdrawBalance = getInt128LE(_data, i);
    i += 16;
    final var marketCumulativeDepositInterest = getInt128LE(_data, i);
    i += 16;
    final var marketCumulativeBorrowInterest = getInt128LE(_data, i);
    i += 16;
    final var totalDepositsAfter = getInt64LE(_data, i);
    i += 8;
    final var totalWithdrawsAfter = getInt64LE(_data, i);
    i += 8;
    final var explanation = DepositExplanation.read(_data, i);
    i += Borsh.len(explanation);
    final var transferUser = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new DepositRecord(ts,
                             userAuthority,
                             user,
                             direction,
                             depositRecordId,
                             amount,
                             marketIndex,
                             oraclePrice,
                             marketDepositBalance,
                             marketWithdrawBalance,
                             marketCumulativeDepositInterest,
                             marketCumulativeBorrowInterest,
                             totalDepositsAfter,
                             totalWithdrawsAfter,
                             explanation,
                             transferUser);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    userAuthority.write(_data, i);
    i += 32;
    user.write(_data, i);
    i += 32;
    i += Borsh.write(direction, _data, i);
    putInt64LE(_data, i, depositRecordId);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, oraclePrice);
    i += 8;
    putInt128LE(_data, i, marketDepositBalance);
    i += 16;
    putInt128LE(_data, i, marketWithdrawBalance);
    i += 16;
    putInt128LE(_data, i, marketCumulativeDepositInterest);
    i += 16;
    putInt128LE(_data, i, marketCumulativeBorrowInterest);
    i += 16;
    putInt64LE(_data, i, totalDepositsAfter);
    i += 8;
    putInt64LE(_data, i, totalWithdrawsAfter);
    i += 8;
    i += Borsh.write(explanation, _data, i);
    i += Borsh.writeOptional(transferUser, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 32
         + 32
         + Borsh.len(direction)
         + 8
         + 8
         + 2
         + 8
         + 16
         + 16
         + 16
         + 16
         + 8
         + 8
         + Borsh.len(explanation)
         + Borsh.lenOptional(transferUser, 32);
  }
}
