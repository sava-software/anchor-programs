package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

// * Delegate ACL
public enum Permission implements Borsh.Enum {

  DriftInit,
  DriftUpdateUser,
  DriftDeleteUser,
  DriftDeposit,
  DriftWithdraw,
  DriftOrders,
  DriftBorrow,
  DriftPerpMarket,
  DriftSpotMarket,
  Stake,
  Unstake,
  DeprecatedLiquidUnstake,
  JupiterSwapAllowlisted,
  JupiterSwapAny,
  WSol,
  DeprecatedWSolUnwrap,
  MintTokens,
  BurnTokens,
  ForceTransferTokens,
  SetTokenAccountState,
  StakeJup,
  VoteOnProposal,
  UnstakeJup,
  JupiterSwapLst,
  JupiterSwapPriceable,
  KaminoInit,
  KaminoDeposit,
  KaminoBorrow,
  KaminoWithdraw,
  MeteoraDlmmPosition,
  MeteoraDlmmLiquidity,
  MeteoraDlmmClaim,
  MeteoraDlmmSwap,
  TransferToAllowlisted,
  WithdrawJup,
  ClaimJup;

  public static Permission read(final byte[] _data, final int offset) {
    return Borsh.read(Permission.values(), _data, offset);
  }
}