package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StrategyStatus implements Borsh.Enum {

  Uninitialized,
  Active,
  Frozen,
  Rebalancing,
  NoPosition;

  public static StrategyStatus read(final byte[] _data, final int offset) {
    return Borsh.read(StrategyStatus.values(), _data, offset);
  }
}