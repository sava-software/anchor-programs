package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TipDistributionAccountClosedEvent(// Account where unclaimed funds were transferred to.
                                                PublicKey expiredFundsAccount,
                                                // [TipDistributionAccount] closed.
                                                PublicKey tipDistributionAccount,
                                                // Unclaimed amount transferred.
                                                long expiredAmount) implements Borsh {

  public static final int BYTES = 72;

  public static TipDistributionAccountClosedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var expiredFundsAccount = readPubKey(_data, i);
    i += 32;
    final var tipDistributionAccount = readPubKey(_data, i);
    i += 32;
    final var expiredAmount = getInt64LE(_data, i);
    return new TipDistributionAccountClosedEvent(expiredFundsAccount, tipDistributionAccount, expiredAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    expiredFundsAccount.write(_data, i);
    i += 32;
    tipDistributionAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, expiredAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
