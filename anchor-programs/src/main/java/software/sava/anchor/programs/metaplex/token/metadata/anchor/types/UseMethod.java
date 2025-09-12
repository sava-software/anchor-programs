package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UseMethod implements Borsh.Enum {

  Burn,
  Multiple,
  Single;

  public static UseMethod read(final byte[] _data, final int offset) {
    return Borsh.read(UseMethod.values(), _data, offset);
  }
}