package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public enum BankOperationalState implements Borsh.Enum {

  Paused,
  Operational,
  ReduceOnly;

  public static BankOperationalState read(final byte[] _data, final int offset) {
    return Borsh.read(BankOperationalState.values(), _data, offset);
  }
}