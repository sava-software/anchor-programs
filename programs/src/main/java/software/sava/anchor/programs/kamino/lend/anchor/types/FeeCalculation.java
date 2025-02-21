package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeeCalculation implements Borsh.Enum {

  Exclusive,
  Inclusive;

  public static FeeCalculation read(final byte[] _data, final int offset) {
    return Borsh.read(FeeCalculation.values(), _data, offset);
  }
}