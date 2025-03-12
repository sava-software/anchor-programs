package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AccountsType implements Borsh.Enum {

  TransferHookA,
  TransferHookB,
  TransferHookReward,
  TransferHookInput,
  TransferHookIntermediate,
  TransferHookOutput,
  SupplementalTickArrays,
  SupplementalTickArraysOne,
  SupplementalTickArraysTwo;

  public static AccountsType read(final byte[] _data, final int offset) {
    return Borsh.read(AccountsType.values(), _data, offset);
  }
}