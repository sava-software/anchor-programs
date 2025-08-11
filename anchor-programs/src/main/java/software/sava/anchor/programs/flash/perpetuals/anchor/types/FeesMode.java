package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum FeesMode implements Borsh.Enum {

  Fixed,
  Linear;

  public static FeesMode read(final byte[] _data, final int offset) {
    return Borsh.read(FeesMode.values(), _data, offset);
  }
}