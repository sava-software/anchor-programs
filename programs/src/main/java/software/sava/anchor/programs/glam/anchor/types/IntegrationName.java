package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

// * Integration ACL
public enum IntegrationName implements Borsh.Enum {

  Drift,
  SplStakePool,
  SanctumStakePool,
  NativeStaking,
  Marinade,
  JupiterSwap,
  Mint,
  JupiterVote;

  public static IntegrationName read(final byte[] _data, final int offset) {
    return Borsh.read(IntegrationName.values(), _data, offset);
  }
}