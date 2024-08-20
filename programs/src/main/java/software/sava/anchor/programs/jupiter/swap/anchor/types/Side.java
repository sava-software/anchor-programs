package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Side implements Borsh.Enum {

  Bid,
  Ask;

  public static Side read(final byte[] _data, final int offset) {
    return Borsh.read(Side.values(), _data, offset);
  }
}