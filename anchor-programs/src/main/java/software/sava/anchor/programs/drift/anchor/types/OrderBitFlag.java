package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderBitFlag implements Borsh.Enum {

  SignedMessage,
  OracleTriggerMarket,
  SafeTriggerOrder,
  NewTriggerReduceOnly;

  public static OrderBitFlag read(final byte[] _data, final int offset) {
    return Borsh.read(OrderBitFlag.values(), _data, offset);
  }
}