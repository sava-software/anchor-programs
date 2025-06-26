package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum LiquidationMultiplierType implements Borsh.Enum {

  Discount,
  Premium;

  public static LiquidationMultiplierType read(final byte[] _data, final int offset) {
    return Borsh.read(LiquidationMultiplierType.values(), _data, offset);
  }
}