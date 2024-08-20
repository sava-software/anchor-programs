package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Withdrawal implements Borsh.Enum {

  In,
  Out;

  public static Withdrawal read(final byte[] _data, final int offset) {
    return Borsh.read(Withdrawal.values(), _data, offset);
  }
}