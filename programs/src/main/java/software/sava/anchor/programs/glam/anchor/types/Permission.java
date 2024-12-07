package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

// * Delegate ACL
public enum Permission implements Borsh.Enum {

  DriftInitialize,
  DriftUpdateUser,
  DriftDeleteUser,
  DriftDeposit,
  DriftWithdraw,
  DriftPlaceOrders,
  DriftCancelOrders,
  DriftPerpMarket,
  DriftSpotMarket,
  Stake,
  Unstake,
  LiquidUnstake,
  JupiterSwapFundAssets,
  JupiterSwapAnyAsset,
  WSolWrap,
  WSolUnwrap,
  MintShare,
  BurnShare,
  ForceTransferShare,
  SetTokenAccountsStates,
  StakeJup,
  VoteOnProposal;

  public static Permission read(final byte[] _data, final int offset) {
    return Borsh.read(Permission.values(), _data, offset);
  }
}