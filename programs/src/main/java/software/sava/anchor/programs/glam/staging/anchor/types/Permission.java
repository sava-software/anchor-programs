package software.sava.anchor.programs.glam.staging.anchor.types;

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
  JupiterSwapAllowlisted,
  JupiterSwapAny,
  WSolWrap,
  WSolUnwrap,
  MintTokens,
  BurnTokens,
  ForceTransferTokens,
  SetTokenAccountState,
  StakeJup,
  VoteOnProposal,
  UnstakeJup,
  JupiterSwapLst,
  KaminoInit,
  KaminoDeposit,
  KaminoBorrow,
  KaminoRepay,
  KaminoWithdraw,
  DriftModifyOrders,
  MeteoraDlmmInitPosition,
  MeteoraDlmmClosePosition,
  MeteoraDlmmAddLiquidity,
  MeteoraDlmmRemoveLiquidity,
  MeteoraDlmmClaimFee,
  MeteoraDlmmClaimReward,
  MeteoraDlmmSwap,
  TransferToAllowlisted;

  public static Permission read(final byte[] _data, final int offset) {
    return Borsh.read(Permission.values(), _data, offset);
  }
}