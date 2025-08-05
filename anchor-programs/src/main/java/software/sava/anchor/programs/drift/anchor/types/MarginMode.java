package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MarginMode implements Borsh.Enum {

  Default,
  HighLeverage,
  HighLeverageMaintenance;

  public static MarginMode read(final byte[] _data, final int offset) {
    return Borsh.read(MarginMode.values(), _data, offset);
  }
}