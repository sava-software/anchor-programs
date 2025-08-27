package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StrategyType implements Borsh.Enum {

  SpotOneSide,
  CurveOneSide,
  BidAskOneSide,
  SpotBalanced,
  CurveBalanced,
  BidAskBalanced,
  SpotImBalanced,
  CurveImBalanced,
  BidAskImBalanced;

  public static StrategyType read(final byte[] _data, final int offset) {
    return Borsh.read(StrategyType.values(), _data, offset);
  }
}