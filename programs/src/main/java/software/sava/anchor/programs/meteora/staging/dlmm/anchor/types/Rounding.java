package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Rounding implements Borsh.Enum {

  Up,
  Down;

  public static Rounding read(final byte[] _data, final int offset) {
    return Borsh.read(Rounding.values(), _data, offset);
  }
}