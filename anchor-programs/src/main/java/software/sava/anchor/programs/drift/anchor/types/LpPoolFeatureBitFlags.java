package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LpPoolFeatureBitFlags implements Borsh.Enum {

  SettleLpPool,
  SwapLpPool,
  MintRedeemLpPool;

  public static LpPoolFeatureBitFlags read(final byte[] _data, final int offset) {
    return Borsh.read(LpPoolFeatureBitFlags.values(), _data, offset);
  }
}