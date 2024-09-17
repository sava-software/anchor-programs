package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DriftDirection implements Borsh.Enum {

  Increasing,
  Decreasing;

  public static DriftDirection read(final byte[] _data, final int offset) {
    return Borsh.read(DriftDirection.values(), _data, offset);
  }
}