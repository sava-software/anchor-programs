package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

// migrate to cpmm, creator fee on quote token or both token
public enum AmmCreatorFeeOn implements Borsh.Enum {

  QuoteToken,
  BothToken;

  public static AmmCreatorFeeOn read(final byte[] _data, final int offset) {
    return Borsh.read(AmmCreatorFeeOn.values(), _data, offset);
  }
}