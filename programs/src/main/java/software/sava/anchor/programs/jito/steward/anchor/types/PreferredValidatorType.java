package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PreferredValidatorType implements Borsh.Enum {

  Deposit,
  Withdraw;

  public static PreferredValidatorType read(final byte[] _data, final int offset) {
    return Borsh.read(PreferredValidatorType.values(), _data, offset);
  }
}