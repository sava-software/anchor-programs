package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReserveStatus implements Borsh.Enum {

  Active,
  Obsolete,
  Hidden;

  public static ReserveStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ReserveStatus.values(), _data, offset);
  }
}