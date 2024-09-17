package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ExecutiveWithdrawAction implements Borsh.Enum {

  Freeze,
  Unfreeze,
  Rebalance;

  public static ExecutiveWithdrawAction read(final byte[] _data, final int offset) {
    return Borsh.read(ExecutiveWithdrawAction.values(), _data, offset);
  }
}