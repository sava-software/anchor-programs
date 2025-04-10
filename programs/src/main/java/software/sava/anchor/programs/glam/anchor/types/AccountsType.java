package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AccountsType implements Borsh.Enum {

  TransferHookX,
  TransferHookY,
  TransferHookReward;

  public static AccountsType read(final byte[] _data, final int offset) {
    return Borsh.read(AccountsType.values(), _data, offset);
  }
}