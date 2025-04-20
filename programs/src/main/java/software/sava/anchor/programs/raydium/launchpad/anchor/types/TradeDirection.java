package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

// Specifies the direction of a trade in the bonding curve
// This is important because curves can treat tokens differently through weights or offsets
public enum TradeDirection implements Borsh.Enum {

  Buy,
  Sell;

  public static TradeDirection read(final byte[] _data, final int offset) {
    return Borsh.read(TradeDirection.values(), _data, offset);
  }
}