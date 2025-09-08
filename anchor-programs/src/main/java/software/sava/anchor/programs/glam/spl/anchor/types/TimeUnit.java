package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TimeUnit implements Borsh.Enum {

  Second,
  Slot;

  public static TimeUnit read(final byte[] _data, final int offset) {
    return Borsh.read(TimeUnit.values(), _data, offset);
  }
}