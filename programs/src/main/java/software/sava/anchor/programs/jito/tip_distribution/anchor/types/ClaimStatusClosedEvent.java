package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ClaimStatusClosedEvent(// Account where funds were transferred to.
                                     PublicKey claimStatusPayer,
                                     // [ClaimStatus] account that was closed.
                                     PublicKey claimStatusAccount) implements Borsh {

  public static final int BYTES = 64;

  public static ClaimStatusClosedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var claimStatusPayer = readPubKey(_data, i);
    i += 32;
    final var claimStatusAccount = readPubKey(_data, i);
    return new ClaimStatusClosedEvent(claimStatusPayer, claimStatusAccount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    claimStatusPayer.write(_data, i);
    i += 32;
    claimStatusAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
