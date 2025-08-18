package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record MarginfiAccountTransferToNewAccount(AccountEventHeader header,
                                                  PublicKey oldAccount,
                                                  PublicKey oldAccountAuthority,
                                                  PublicKey newAccountAuthority) implements Borsh {

  public static MarginfiAccountTransferToNewAccount read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = AccountEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var oldAccount = readPubKey(_data, i);
    i += 32;
    final var oldAccountAuthority = readPubKey(_data, i);
    i += 32;
    final var newAccountAuthority = readPubKey(_data, i);
    return new MarginfiAccountTransferToNewAccount(header,
                                                   oldAccount,
                                                   oldAccountAuthority,
                                                   newAccountAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    oldAccount.write(_data, i);
    i += 32;
    oldAccountAuthority.write(_data, i);
    i += 32;
    newAccountAuthority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header) + 32 + 32 + 32;
  }
}
