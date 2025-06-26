package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DriftAction implements Borsh.Enum {

  UpdateFunding,
  SettlePnl,
  TriggerOrder,
  FillOrderMatch,
  FillOrderAmm,
  Liquidate,
  MarginCalc,
  UpdateTwap,
  UpdateAMMCurve,
  OracleOrderPrice;

  public static DriftAction read(final byte[] _data, final int offset) {
    return Borsh.read(DriftAction.values(), _data, offset);
  }
}