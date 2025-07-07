package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeeUpdateStatus implements Borsh.Enum {

  None,
  PendingFeeUpdate;

  public static FeeUpdateStatus read(final byte[] _data, final int offset) {
    return Borsh.read(FeeUpdateStatus.values(), _data, offset);
  }
}