package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.borsh.Borsh;

public enum PostOnlyParam implements Borsh.Enum {

  None,
  MustPostOnly,
  TryPostOnly,
  Slide;

  public static PostOnlyParam read(final byte[] _data, final int offset) {
    return Borsh.read(PostOnlyParam.values(), _data, offset);
  }
}