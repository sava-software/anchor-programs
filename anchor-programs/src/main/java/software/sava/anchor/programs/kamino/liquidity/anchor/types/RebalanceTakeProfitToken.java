package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceTakeProfitToken implements Borsh.Enum {

  A,
  B;

  public static RebalanceTakeProfitToken read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceTakeProfitToken.values(), _data, offset);
  }
}