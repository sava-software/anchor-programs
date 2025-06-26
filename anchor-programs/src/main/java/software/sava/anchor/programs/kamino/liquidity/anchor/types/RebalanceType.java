package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceType implements Borsh.Enum {

  Manual,
  PricePercentage,
  PricePercentageWithReset,
  Drift,
  TakeProfit,
  PeriodicRebalance,
  Expander,
  Autodrift;

  public static RebalanceType read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceType.values(), _data, offset);
  }
}