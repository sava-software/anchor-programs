package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ExternalYieldAccounts(PublicKey externalYieldAccount, PublicKey externalYieldVault) implements Borsh {

  public static final int BYTES = 64;

  public static ExternalYieldAccounts read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var externalYieldAccount = readPubKey(_data, i);
    i += 32;
    final var externalYieldVault = readPubKey(_data, i);
    return new ExternalYieldAccounts(externalYieldAccount, externalYieldVault);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    externalYieldAccount.write(_data, i);
    i += 32;
    externalYieldVault.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
