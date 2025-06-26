package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record TipDistributionAccountInitializedEvent(PublicKey tipDistributionAccount) implements Borsh {

  public static final int BYTES = 32;

  public static TipDistributionAccountInitializedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var tipDistributionAccount = readPubKey(_data, offset);
    return new TipDistributionAccountInitializedEvent(tipDistributionAccount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    tipDistributionAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
