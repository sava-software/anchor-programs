package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderType implements Borsh.Enum {

  Market,
  Limit,
  TriggerMarket,
  TriggerLimit,
  Oracle;

  public static OrderType read(final byte[] _data, final int offset) {
    return Borsh.read(OrderType.values(), _data, offset);
  }
}