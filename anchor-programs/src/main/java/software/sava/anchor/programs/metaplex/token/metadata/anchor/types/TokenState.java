package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenState implements Borsh.Enum {

  Unlocked,
  Locked,
  Listed;

  public static TokenState read(final byte[] _data, final int offset) {
    return Borsh.read(TokenState.values(), _data, offset);
  }
}