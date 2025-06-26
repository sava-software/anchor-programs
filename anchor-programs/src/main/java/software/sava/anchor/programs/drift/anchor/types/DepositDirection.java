package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DepositDirection implements Borsh.Enum {

  Deposit,
  Withdraw;

  public static DepositDirection read(final byte[] _data, final int offset) {
    return Borsh.read(DepositDirection.values(), _data, offset);
  }
}