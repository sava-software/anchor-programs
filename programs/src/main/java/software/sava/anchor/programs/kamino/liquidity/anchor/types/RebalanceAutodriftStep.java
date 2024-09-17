package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceAutodriftStep implements Borsh.Enum {

  Uninitialized,
  Autodrifting;

  public static RebalanceAutodriftStep read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceAutodriftStep.values(), _data, offset);
  }
}