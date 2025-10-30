package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ConstituentLpOperation implements Borsh.Enum {

  Swap,
  Deposit,
  Withdraw;

  public static ConstituentLpOperation read(final byte[] _data, final int offset) {
    return Borsh.read(ConstituentLpOperation.values(), _data, offset);
  }
}