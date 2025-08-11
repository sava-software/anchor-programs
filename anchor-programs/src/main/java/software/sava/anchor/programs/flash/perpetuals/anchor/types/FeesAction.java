package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeesAction implements Borsh.Enum {

  AddLiquidity,
  RemoveLiquidity,
  SwapIn,
  SwapOut,
  StableSwapIn,
  StableSwapOut;

  public static FeesAction read(final byte[] _data, final int offset) {
    return Borsh.read(FeesAction.values(), _data, offset);
  }
}