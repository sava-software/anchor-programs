package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SettlementDirection implements Borsh.Enum {

  ToLpPool,
  FromLpPool,
  None;

  public static SettlementDirection read(final byte[] _data, final int offset) {
    return Borsh.read(SettlementDirection.values(), _data, offset);
  }
}