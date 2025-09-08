package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AccountType implements Borsh.Enum {

  Vault,
  TokenizedVault,
  Mint;

  public static AccountType read(final byte[] _data, final int offset) {
    return Borsh.read(AccountType.values(), _data, offset);
  }
}