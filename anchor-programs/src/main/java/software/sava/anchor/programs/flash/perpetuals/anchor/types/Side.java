package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Side implements Borsh.Enum {

  None,
  Long,
  Short;

  public static Side read(final byte[] _data, final int offset) {
    return Borsh.read(Side.values(), _data, offset);
  }
}