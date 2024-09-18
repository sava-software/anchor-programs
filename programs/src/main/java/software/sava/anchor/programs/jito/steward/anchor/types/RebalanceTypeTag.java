package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RebalanceTypeTag implements Borsh.Enum {

  None,
  Increase,
  Decrease;

  public static RebalanceTypeTag read(final byte[] _data, final int offset) {
    return Borsh.read(RebalanceTypeTag.values(), _data, offset);
  }
}