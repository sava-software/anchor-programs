package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TokenProgramFlag implements Borsh.Enum {

  Token2022,
  TransferHook;

  public static TokenProgramFlag read(final byte[] _data, final int offset) {
    return Borsh.read(TokenProgramFlag.values(), _data, offset);
  }
}