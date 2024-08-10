package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LiquidationType implements Borsh.Enum {

  LiquidatePerp,
  LiquidateSpot,
  LiquidateBorrowForPerpPnl,
  LiquidatePerpPnlForDeposit,
  PerpBankruptcy,
  SpotBankruptcy;

  public static LiquidationType read(final byte[] _data, final int offset) {
    return Borsh.read(LiquidationType.values(), _data, offset);
  }
}