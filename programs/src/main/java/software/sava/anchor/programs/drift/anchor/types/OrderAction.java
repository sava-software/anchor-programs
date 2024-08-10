package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderAction implements Borsh.Enum {

  Place,
  Cancel,
  Fill,
  Trigger,
  Expire;

  public static OrderAction read(final byte[] _data, final int offset) {
    return Borsh.read(OrderAction.values(), _data, offset);
  }
}