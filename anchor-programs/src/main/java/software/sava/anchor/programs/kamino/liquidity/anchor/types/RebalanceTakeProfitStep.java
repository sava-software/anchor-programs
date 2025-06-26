package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceTakeProfitStep implements Borsh.Enum {

  Uninitialized,
  TakingProfit,
  Finished;

  public static RebalanceTakeProfitStep read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceTakeProfitStep.values(), _data, offset);
  }
}