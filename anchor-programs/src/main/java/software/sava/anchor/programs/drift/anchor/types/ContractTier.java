package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ContractTier implements Borsh.Enum {

  A,
  B,
  C,
  Speculative,
  HighlySpeculative,
  Isolated;

  public static ContractTier read(final byte[] _data, final int offset) {
    return Borsh.read(ContractTier.values(), _data, offset);
  }
}