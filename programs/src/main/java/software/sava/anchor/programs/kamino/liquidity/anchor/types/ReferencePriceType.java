package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReferencePriceType implements Borsh.Enum {

  POOL,
  TWAP;

  public static ReferencePriceType read(final byte[] _data, final int offset) {
    return Borsh.read(ReferencePriceType.values(), _data, offset);
  }
}