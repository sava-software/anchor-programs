package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UserStatus implements Borsh.Enum {

  BeingLiquidated,
  Bankrupt,
  ReduceOnly,
  AdvancedLp;

  public static UserStatus read(final byte[] _data, final int offset) {
    return Borsh.read(UserStatus.values(), _data, offset);
  }
}