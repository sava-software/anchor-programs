package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum CreationStatus implements Borsh.Enum {

  IGNORED,
  SHADOW,
  LIVE,
  DEPRECATED,
  STAGING;

  public static CreationStatus read(final byte[] _data, final int offset) {
    return Borsh.read(CreationStatus.values(), _data, offset);
  }
}