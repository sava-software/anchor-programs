package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PerpLpOperation implements Borsh.Enum {

  TrackAmmRevenue,
  SettleQuoteOwed;

  public static PerpLpOperation read(final byte[] _data, final int offset) {
    return Borsh.read(PerpLpOperation.values(), _data, offset);
  }
}