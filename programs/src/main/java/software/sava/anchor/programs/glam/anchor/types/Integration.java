package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Integration implements Borsh.Enum {

  Drift,
  SplStakePool,
  SanctumStakePool,
  NativeStaking,
  Marinade,
  JupiterSwap,
  JupiterVote,
  KaminoLending;

  public static Integration read(final byte[] _data, final int offset) {
    return Borsh.read(Integration.values(), _data, offset);
  }
}