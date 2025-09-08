package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.borsh.Borsh;

public enum HurdleType implements Borsh.Enum {

  Hard,
  Soft;

  public static HurdleType read(final byte[] _data, final int offset) {
    return Borsh.read(HurdleType.values(), _data, offset);
  }
}