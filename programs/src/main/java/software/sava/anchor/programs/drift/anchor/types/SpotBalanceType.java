package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum SpotBalanceType implements Borsh.Enum {

  Deposit,
  Borrow;

  public static SpotBalanceType read(final byte[] _data, final int offset) {
    return Borsh.read(SpotBalanceType.values(), _data, offset);
  }
}