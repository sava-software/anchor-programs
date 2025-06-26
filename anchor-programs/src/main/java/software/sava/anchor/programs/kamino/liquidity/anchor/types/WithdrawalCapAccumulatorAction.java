package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum WithdrawalCapAccumulatorAction implements Borsh.Enum {

  KeepAccumulator,
  ResetAccumulator;

  public static WithdrawalCapAccumulatorAction read(final byte[] _data, final int offset) {
    return Borsh.read(WithdrawalCapAccumulatorAction.values(), _data, offset);
  }
}