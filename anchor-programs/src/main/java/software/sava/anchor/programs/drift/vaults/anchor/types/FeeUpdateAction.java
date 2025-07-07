package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeeUpdateAction implements Borsh.Enum {

  Pending,
  Applied,
  Cancelled;

  public static FeeUpdateAction read(final byte[] _data, final int offset) {
    return Borsh.read(FeeUpdateAction.values(), _data, offset);
  }
}