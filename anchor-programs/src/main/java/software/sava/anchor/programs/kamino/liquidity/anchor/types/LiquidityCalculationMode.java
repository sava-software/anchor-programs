package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LiquidityCalculationMode implements Borsh.Enum {

  Deposit,
  Withdraw;

  public static LiquidityCalculationMode read(final byte[] _data, final int offset) {
    return Borsh.read(LiquidityCalculationMode.values(), _data, offset);
  }
}