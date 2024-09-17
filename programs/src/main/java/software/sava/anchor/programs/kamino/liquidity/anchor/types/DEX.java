package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DEX implements Borsh.Enum {

  Orca,
  Raydium,
  Meteora;

  public static DEX read(final byte[] _data, final int offset) {
    return Borsh.read(DEX.values(), _data, offset);
  }
}