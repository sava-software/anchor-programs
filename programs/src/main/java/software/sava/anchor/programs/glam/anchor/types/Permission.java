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
  DriftModifyOrders,
  MeteoraDlmmInitPosition,
  MeteoraDlmmClosePosition,
  MeteoraDlmmLiquidity,
  MeteoraDlmmSwap;

  public static Permission read(final byte[] _data, final int offset) {
    return Borsh.read(Permission.values(), _data, offset);
  }
}