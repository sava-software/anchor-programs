package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleAccountType implements Borsh.Enum {

  Internal,
  External,
  Backup,
  Custom;

  public static OracleAccountType read(final byte[] _data, final int offset) {
    return Borsh.read(OracleAccountType.values(), _data, offset);
  }
}