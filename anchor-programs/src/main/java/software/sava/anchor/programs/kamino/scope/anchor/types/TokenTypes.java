package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenTypes implements Borsh.Enum {

  TokenA,
  TokenB;

  public static TokenTypes read(final byte[] _data, final int offset) {
    return Borsh.read(TokenTypes.values(), _data, offset);
  }
}