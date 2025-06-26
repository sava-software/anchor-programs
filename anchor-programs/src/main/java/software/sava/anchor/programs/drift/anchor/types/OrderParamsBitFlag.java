package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderParamsBitFlag implements Borsh.Enum {

  ImmediateOrCancel,
  UpdateHighLeverageMode;

  public static OrderParamsBitFlag read(final byte[] _data, final int offset) {
    return Borsh.read(OrderParamsBitFlag.values(), _data, offset);
  }
}