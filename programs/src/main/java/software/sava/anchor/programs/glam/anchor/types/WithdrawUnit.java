package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum WithdrawUnit implements Borsh.Enum {

  Shares,
  Token,
  SharesPercent;

  public static WithdrawUnit read(final byte[] _data, final int offset) {
    return Borsh.read(WithdrawUnit.values(), _data, offset);
  }
}