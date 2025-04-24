package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ClaimedEvent(// [TipDistributionAccount] claimed from.
                           PublicKey tipDistributionAccount,
                           // User that paid for the claim, may or may not be the same as claimant.
                           PublicKey payer,
                           // Account that received the funds.
                           PublicKey claimant,
                           // Amount of funds to distribute.
                           long amount) implements Borsh {

  public static final int BYTES = 104;

  public static ClaimedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tipDistributionAccount = readPubKey(_data, i);
    i += 32;
    final var payer = readPubKey(_data, i);
    i += 32;
    final var claimant = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new ClaimedEvent(tipDistributionAccount,
                            payer,
                            claimant,
                            amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    tipDistributionAccount.write(_data, i);
    i += 32;
    payer.write(_data, i);
    i += 32;
    claimant.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
