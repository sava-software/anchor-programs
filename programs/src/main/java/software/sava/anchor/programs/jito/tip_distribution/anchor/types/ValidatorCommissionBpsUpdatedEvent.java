package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ValidatorCommissionBpsUpdatedEvent(PublicKey tipDistributionAccount,
                                                 int oldCommissionBps,
                                                 int newCommissionBps) implements Borsh {

  public static final int BYTES = 36;

  public static ValidatorCommissionBpsUpdatedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tipDistributionAccount = readPubKey(_data, i);
    i += 32;
    final var oldCommissionBps = getInt16LE(_data, i);
    i += 2;
    final var newCommissionBps = getInt16LE(_data, i);
    return new ValidatorCommissionBpsUpdatedEvent(tipDistributionAccount, oldCommissionBps, newCommissionBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    tipDistributionAccount.write(_data, i);
    i += 32;
    putInt16LE(_data, i, oldCommissionBps);
    i += 2;
    putInt16LE(_data, i, newCommissionBps);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
