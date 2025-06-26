package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MintingMethod implements Borsh.Enum {

  PriceBased,
  Proportional;

  public static MintingMethod read(final byte[] _data, final int offset) {
    return Borsh.read(MintingMethod.values(), _data, offset);
  }
}