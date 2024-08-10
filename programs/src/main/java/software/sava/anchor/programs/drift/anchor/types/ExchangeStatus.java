package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ExchangeStatus implements Borsh.Enum {

  DepositPaused,
  WithdrawPaused,
  AmmPaused,
  FillPaused,
  LiqPaused,
  FundingPaused,
  SettlePnlPaused;

  public static ExchangeStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ExchangeStatus.values(), _data, offset);
  }
}