package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RiskTier implements Borsh.Enum {

  Collateral,
  Isolated;

  public static RiskTier read(final byte[] _data, final int offset) {
    return Borsh.read(RiskTier.values(), _data, offset);
  }
}