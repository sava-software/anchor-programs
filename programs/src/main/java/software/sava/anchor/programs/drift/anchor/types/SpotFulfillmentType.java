package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SpotFulfillmentType implements Borsh.Enum {

  SerumV3,
  Match,
  PhoenixV1;

  public static SpotFulfillmentType read(final byte[] _data, final int offset) {
    return Borsh.read(SpotFulfillmentType.values(), _data, offset);
  }
}