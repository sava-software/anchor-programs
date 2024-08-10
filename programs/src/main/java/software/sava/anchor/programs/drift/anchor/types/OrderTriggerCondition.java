package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum OrderTriggerCondition implements Borsh.Enum {

  Above,
  Below,
  TriggeredAbove,
  TriggeredBelow;

  public static OrderTriggerCondition read(final byte[] _data, final int offset) {
    return Borsh.read(OrderTriggerCondition.values(), _data, offset);
  }
}