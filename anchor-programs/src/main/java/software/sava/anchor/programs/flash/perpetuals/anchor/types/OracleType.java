package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleType implements Borsh.Enum {

  None,
  Custom,
  Pyth;

  public static OracleType read(final byte[] _data, final int offset) {
    return Borsh.read(OracleType.values(), _data, offset);
  }
}