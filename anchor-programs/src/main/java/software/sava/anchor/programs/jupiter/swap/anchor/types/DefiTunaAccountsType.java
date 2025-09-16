package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

public enum DefiTunaAccountsType implements Borsh.Enum {

  TransferHookA,
  TransferHookB,
  TransferHookInput,
  TransferHookIntermediate,
  TransferHookOutput,
  SupplementalTickArrays,
  SupplementalTickArraysOne,
  SupplementalTickArraysTwo;

  public static DefiTunaAccountsType read(final byte[] _data, final int offset) {
    return Borsh.read(DefiTunaAccountsType.values(), _data, offset);
  }
}