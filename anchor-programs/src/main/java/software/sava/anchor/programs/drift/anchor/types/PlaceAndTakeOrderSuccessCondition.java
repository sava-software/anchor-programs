package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PlaceAndTakeOrderSuccessCondition implements Borsh.Enum {

  PartialFill,
  FullFill;

  public static PlaceAndTakeOrderSuccessCondition read(final byte[] _data, final int offset) {
    return Borsh.read(PlaceAndTakeOrderSuccessCondition.values(), _data, offset);
  }
}