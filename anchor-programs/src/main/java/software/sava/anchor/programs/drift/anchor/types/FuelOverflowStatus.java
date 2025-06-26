package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FuelOverflowStatus implements Borsh.Enum {

  Exists;

  public static FuelOverflowStatus read(final byte[] _data, final int offset) {
    return Borsh.read(FuelOverflowStatus.values(), _data, offset);
  }
}