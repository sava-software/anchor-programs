package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record AccountEventHeader(PublicKey signer,
                                 PublicKey marginfiAccount,
                                 PublicKey marginfiAccountAuthority,
                                 PublicKey marginfiGroup) implements Borsh {

  public static AccountEventHeader read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signer = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (signer != null) {
      i += 32;
    }
    final var marginfiAccount = readPubKey(_data, i);
    i += 32;
    final var marginfiAccountAuthority = readPubKey(_data, i);
    i += 32;
    final var marginfiGroup = readPubKey(_data, i);
    return new AccountEventHeader(signer,
                                  marginfiAccount,
                                  marginfiAccountAuthority,
                                  marginfiGroup);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(signer, _data, i);
    marginfiAccount.write(_data, i);
    i += 32;
    marginfiAccountAuthority.write(_data, i);
    i += 32;
    marginfiGroup.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return (signer == null ? 1 : (1 + 32)) + 32 + 32 + 32;
  }
}
