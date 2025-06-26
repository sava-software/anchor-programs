package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PositionUpdateType implements Borsh.Enum {

  Open,
  Increase,
  Reduce,
  Close,
  Flip;

  public static PositionUpdateType read(final byte[] _data, final int offset) {
    return Borsh.read(PositionUpdateType.values(), _data, offset);
  }
}