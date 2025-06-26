package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ReferrerStatus implements Borsh.Enum {

  IsReferrer,
  IsReferred;

  public static ReferrerStatus read(final byte[] _data, final int offset) {
    return Borsh.read(ReferrerStatus.values(), _data, offset);
  }
}