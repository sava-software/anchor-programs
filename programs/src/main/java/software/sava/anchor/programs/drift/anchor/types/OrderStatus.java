package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderStatus implements Borsh.Enum {

  Init,
  Open,
  Filled,
  Canceled;

  public static OrderStatus read(final byte[] _data, final int offset) {
    return Borsh.read(OrderStatus.values(), _data, offset);
  }
}