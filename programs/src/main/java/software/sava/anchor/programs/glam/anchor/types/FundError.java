package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FundError implements Borsh.Enum {

  NoShareClassInFund,
  ShareClassNotEmpty,
  CantCloseShareClasses;

  public static FundError read(final byte[] _data, final int offset) {
    return Borsh.read(FundError.values(), _data, offset);
  }
}