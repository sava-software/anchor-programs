package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum Privilege implements Borsh.Enum {

  None,
  Stake,
  Referral;

  public static Privilege read(final byte[] _data, final int offset) {
    return Borsh.read(Privilege.values(), _data, offset);
  }
}