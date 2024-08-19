package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

// * Delegate ACL
public enum Permission implements Borsh.Enum {

  DriftDeposit,
  DriftWithdraw,
  Stake,
  Unstake,
  LiquidUnstake,
  JupiterSwapFundAssets,
  JupiterSwapAnyAsset,
  WSolWrap,
  WSolUnwrap;

  public static Permission read(final byte[] _data, final int offset) {
    return Borsh.read(Permission.values(), _data, offset);
  }
}