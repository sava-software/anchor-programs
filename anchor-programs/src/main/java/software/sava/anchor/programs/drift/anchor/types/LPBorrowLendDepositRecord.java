package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LPBorrowLendDepositRecord(long ts,
                                        long slot,
                                        int spotMarketIndex,
                                        int constituentIndex,
                                        DepositDirection direction,
                                        long tokenBalance,
                                        long lastTokenBalance,
                                        long interestAccruedTokenAmount,
                                        long amountDepositWithdraw,
                                        PublicKey lpPool) implements Borsh {

  public static final int BYTES = 85;

  public static LPBorrowLendDepositRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var constituentIndex = getInt16LE(_data, i);
    i += 2;
    final var direction = DepositDirection.read(_data, i);
    i += Borsh.len(direction);
    final var tokenBalance = getInt64LE(_data, i);
    i += 8;
    final var lastTokenBalance = getInt64LE(_data, i);
    i += 8;
    final var interestAccruedTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var amountDepositWithdraw = getInt64LE(_data, i);
    i += 8;
    final var lpPool = readPubKey(_data, i);
    return new LPBorrowLendDepositRecord(ts,
                                         slot,
                                         spotMarketIndex,
                                         constituentIndex,
                                         direction,
                                         tokenBalance,
                                         lastTokenBalance,
                                         interestAccruedTokenAmount,
                                         amountDepositWithdraw,
                                         lpPool);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt16LE(_data, i, constituentIndex);
    i += 2;
    i += Borsh.write(direction, _data, i);
    putInt64LE(_data, i, tokenBalance);
    i += 8;
    putInt64LE(_data, i, lastTokenBalance);
    i += 8;
    putInt64LE(_data, i, interestAccruedTokenAmount);
    i += 8;
    putInt64LE(_data, i, amountDepositWithdraw);
    i += 8;
    lpPool.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
