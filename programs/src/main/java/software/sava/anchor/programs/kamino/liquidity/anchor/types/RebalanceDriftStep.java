package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceDriftStep implements Borsh.Enum {

  Uninitialized,
  Drifting;

  public static RebalanceDriftStep read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceDriftStep.values(), _data, offset);
  }
}