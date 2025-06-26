package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PerpOperation implements Borsh.Enum {

  UpdateFunding,
  AmmFill,
  Fill,
  SettlePnl,
  SettlePnlWithPosition,
  Liquidation,
  AmmImmediateFill;

  public static PerpOperation read(final byte[] _data, final int offset) {
    return Borsh.read(PerpOperation.values(), _data, offset);
  }
}