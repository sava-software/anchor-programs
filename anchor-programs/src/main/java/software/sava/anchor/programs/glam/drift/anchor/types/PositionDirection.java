package software.sava.anchor.programs.glam.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PositionDirection implements Borsh.Enum {

  Long,
  Short;

  public static PositionDirection read(final byte[] _data, final int offset) {
    return Borsh.read(PositionDirection.values(), _data, offset);
  }
}