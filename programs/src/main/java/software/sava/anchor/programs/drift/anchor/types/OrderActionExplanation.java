package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderActionExplanation implements Borsh.Enum {

  None,
  InsufficientFreeCollateral,
  OraclePriceBreachedLimitPrice,
  MarketOrderFilledToLimitPrice,
  OrderExpired,
  Liquidation,
  OrderFilledWithAMM,
  OrderFilledWithAMMJit,
  OrderFilledWithMatch,
  OrderFilledWithMatchJit,
  MarketExpired,
  RiskingIncreasingOrder,
  ReduceOnlyOrderIncreasedPosition,
  OrderFillWithSerum,
  NoBorrowLiquidity,
  OrderFillWithPhoenix,
  OrderFilledWithAMMJitLPSplit,
  OrderFilledWithLPJit,
  DeriskLp,
  OrderFilledWithOpenbookV2;

  public static OrderActionExplanation read(final byte[] _data, final int offset) {
    return Borsh.read(OrderActionExplanation.values(), _data, offset);
  }
}