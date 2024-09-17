package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StrategyType implements Borsh.Enum {

  Stable,
  Pegged,
  Volatile;

  public static StrategyType read(final byte[] _data, final int offset) {
    return Borsh.read(StrategyType.values(), _data, offset);
  }
}