package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum BalanceStatus implements Borsh.Enum {

  Balanced,
  Unbalanced;

  public static BalanceStatus read(final byte[] _data, final int offset) {
    return Borsh.read(BalanceStatus.values(), _data, offset);
  }
}