package software.sava.anchor.programs.glam.staging.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ValuationModel implements Borsh.Enum {

  Continuous,
  Periodic;

  public static ValuationModel read(final byte[] _data, final int offset) {
    return Borsh.read(ValuationModel.values(), _data, offset);
  }
}