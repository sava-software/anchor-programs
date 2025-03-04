package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VaultDepositorAction implements Borsh.Enum {

  Deposit,
  WithdrawRequest,
  CancelWithdrawRequest,
  Withdraw,
  FeePayment;

  public static VaultDepositorAction read(final byte[] _data, final int offset) {
    return Borsh.read(VaultDepositorAction.values(), _data, offset);
  }
}