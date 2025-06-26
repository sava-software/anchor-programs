package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DepositExplanation implements Borsh.Enum {

  None,
  Transfer,
  Borrow,
  RepayBorrow,
  Reward;

  public static DepositExplanation read(final byte[] _data, final int offset) {
    return Borsh.read(DepositExplanation.values(), _data, offset);
  }
}