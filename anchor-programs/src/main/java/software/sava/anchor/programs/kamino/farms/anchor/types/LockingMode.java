package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LockingMode implements Borsh.Enum {

  None,
  Continuous,
  WithExpiry;

  public static LockingMode read(final byte[] _data, final int offset) {
    return Borsh.read(LockingMode.values(), _data, offset);
  }
}