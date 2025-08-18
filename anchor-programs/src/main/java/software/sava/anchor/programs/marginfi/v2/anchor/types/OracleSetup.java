package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OracleSetup implements Borsh.Enum {

  None,
  PythLegacy,
  SwitchboardV2,
  PythPushOracle,
  SwitchboardPull,
  StakedWithPythPush;

  public static OracleSetup read(final byte[] _data, final int offset) {
    return Borsh.read(OracleSetup.values(), _data, offset);
  }
}