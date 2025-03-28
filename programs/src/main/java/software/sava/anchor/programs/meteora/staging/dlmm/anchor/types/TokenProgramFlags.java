package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenProgramFlags implements Borsh.Enum {

  TokenProgram,
  TokenProgram2022;

  public static TokenProgramFlags read(final byte[] _data, final int offset) {
    return Borsh.read(TokenProgramFlags.values(), _data, offset);
  }
}