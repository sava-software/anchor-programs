package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AccessError implements Borsh.Enum {

  NotAuthorized;

  public static AccessError read(final byte[] _data, final int offset) {
    return Borsh.read(AccessError.values(), _data, offset);
  }
}