package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SwapType implements Borsh.Enum {

  Buy,
  Sell;

  public static SwapType read(final byte[] _data, final int offset) {
    return Borsh.read(SwapType.values(), _data, offset);
  }
}