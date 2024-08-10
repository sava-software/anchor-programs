package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TwapPeriod implements Borsh.Enum {

  FundingPeriod,
  FiveMin;

  public static TwapPeriod read(final byte[] _data, final int offset) {
    return Borsh.read(TwapPeriod.values(), _data, offset);
  }
}