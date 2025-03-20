package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PriceDenom implements Borsh.Enum {

  SOL,
  USD,
  Asset,
  EUR;

  public static PriceDenom read(final byte[] _data, final int offset) {
    return Borsh.read(PriceDenom.values(), _data, offset);
  }
}