package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.borsh.Borsh;

public enum TimeUnit implements Borsh.Enum {

  Seconds,
  Slots;

  public static TimeUnit read(final byte[] _data, final int offset) {
    return Borsh.read(TimeUnit.values(), _data, offset);
  }
}