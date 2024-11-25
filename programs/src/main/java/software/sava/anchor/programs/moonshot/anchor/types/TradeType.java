package software.sava.anchor.programs.moonshot.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TradeType implements Borsh.Enum {

  Buy,
  Sell;

  public static TradeType read(final byte[] _data, final int offset) {
    return Borsh.read(TradeType.values(), _data, offset);
  }
}