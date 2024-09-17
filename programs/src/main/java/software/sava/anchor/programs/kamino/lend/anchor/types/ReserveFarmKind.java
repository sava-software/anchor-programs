package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReserveFarmKind implements Borsh.Enum {

  Collateral,
  Debt;

  public static ReserveFarmKind read(final byte[] _data, final int offset) {
    return Borsh.read(ReserveFarmKind.values(), _data, offset);
  }
}