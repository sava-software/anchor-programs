package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MarketStatus implements Borsh.Enum {

  Initialized,
  Active,
  FundingPaused,
  AmmPaused,
  FillPaused,
  WithdrawPaused,
  ReduceOnly,
  Settlement,
  Delisted;

  public static MarketStatus read(final byte[] _data, final int offset) {
    return Borsh.read(MarketStatus.values(), _data, offset);
  }
}