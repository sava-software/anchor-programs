package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ModifyOrderPolicy implements Borsh.Enum {

  MustModify,
  ExcludePreviousFill;

  public static ModifyOrderPolicy read(final byte[] _data, final int offset) {
    return Borsh.read(ModifyOrderPolicy.values(), _data, offset);
  }
}