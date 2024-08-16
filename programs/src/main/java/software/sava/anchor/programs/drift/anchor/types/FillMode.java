package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FillMode implements Borsh.Enum {

  Fill,
  PlaceAndMake,
  PlaceAndTake,
  Liquidation;

  public static FillMode read(final byte[] _data, final int offset) {
    return Borsh.read(FillMode.values(), _data, offset);
  }
}