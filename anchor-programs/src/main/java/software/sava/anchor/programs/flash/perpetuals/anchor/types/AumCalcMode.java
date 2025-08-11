package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AumCalcMode implements Borsh.Enum {

  IncludePnl,
  ExcludePnl;

  public static AumCalcMode read(final byte[] _data, final int offset) {
    return Borsh.read(AumCalcMode.values(), _data, offset);
  }
}