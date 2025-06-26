package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum MarginRequirementType implements Borsh.Enum {

  Initial,
  Fill,
  Maintenance;

  public static MarginRequirementType read(final byte[] _data, final int offset) {
    return Borsh.read(MarginRequirementType.values(), _data, offset);
  }
}