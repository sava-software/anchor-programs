package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LPAction implements Borsh.Enum {

  AddLiquidity,
  RemoveLiquidity,
  SettleLiquidity,
  RemoveLiquidityDerisk;

  public static LPAction read(final byte[] _data, final int offset) {
    return Borsh.read(LPAction.values(), _data, offset);
  }
}