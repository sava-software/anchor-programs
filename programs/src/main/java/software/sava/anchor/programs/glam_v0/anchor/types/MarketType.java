package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MarketType implements Borsh.Enum {

  Spot,
  Perp;

  public static MarketType read(final byte[] _data, final int offset) {
    return Borsh.read(MarketType.values(), _data, offset);
  }
}