package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum ModifyOrderPolicy implements Borsh.Enum {

  TryModify,
  MustModify;

  public static ModifyOrderPolicy read(final byte[] _data, final int offset) {
    return Borsh.read(ModifyOrderPolicy.values(), _data, offset);
  }
}