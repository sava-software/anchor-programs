package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StakingRateSource implements Borsh.Enum {

  Constant,
  Scope;

  public static StakingRateSource read(final byte[] _data, final int offset) {
    return Borsh.read(StakingRateSource.values(), _data, offset);
  }
}