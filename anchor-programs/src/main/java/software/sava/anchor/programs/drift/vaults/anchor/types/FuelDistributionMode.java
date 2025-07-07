package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FuelDistributionMode implements Borsh.Enum {

  UsersOnly,
  UsersAndManager;

  public static FuelDistributionMode read(final byte[] _data, final int offset) {
    return Borsh.read(FuelDistributionMode.values(), _data, offset);
  }
}