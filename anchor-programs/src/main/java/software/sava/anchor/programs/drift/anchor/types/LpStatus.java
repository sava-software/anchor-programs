package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LpStatus implements Borsh.Enum {

  Uncollateralized,
  Active,
  Decommissioning;

  public static LpStatus read(final byte[] _data, final int offset) {
    return Borsh.read(LpStatus.values(), _data, offset);
  }
}