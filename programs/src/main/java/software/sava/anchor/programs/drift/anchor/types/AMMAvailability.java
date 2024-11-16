package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum AMMAvailability implements Borsh.Enum {

  Immediate,
  AfterMinDuration,
  Unavailable;

  public static AMMAvailability read(final byte[] _data, final int offset) {
    return Borsh.read(AMMAvailability.values(), _data, offset);
  }
}