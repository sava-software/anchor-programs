package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenDelegateRole implements Borsh.Enum {

  Sale,
  Transfer,
  Utility,
  Staking,
  Standard,
  LockedTransfer,
  Migration;

  public static TokenDelegateRole read(final byte[] _data, final int offset) {
    return Borsh.read(TokenDelegateRole.values(), _data, offset);
  }
}