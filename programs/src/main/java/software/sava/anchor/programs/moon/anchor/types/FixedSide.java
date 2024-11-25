package software.sava.anchor.programs.moon.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FixedSide implements Borsh.Enum {

  ExactIn,
  ExactOut;

  public static FixedSide read(final byte[] _data, final int offset) {
    return Borsh.read(FixedSide.values(), _data, offset);
  }
}