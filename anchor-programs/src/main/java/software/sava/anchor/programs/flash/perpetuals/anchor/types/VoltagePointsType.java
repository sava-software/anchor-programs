package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public enum VoltagePointsType implements Borsh.Enum {

  OpenPosition,
  SwapAndOpen,
  IncreaseSize,
  DecreaseSize,
  CloseAndSwap,
  ClosePosition,
  ExecuteLimitOrder,
  ExecuteLimitWithSwap,
  ExecuteTriggerOrder,
  ExecuteTriggerWithSwap,
  CollectStakeReward,
  DecreaseAndRemoveCollateral;

  public static VoltagePointsType read(final byte[] _data, final int offset) {
    return Borsh.read(VoltagePointsType.values(), _data, offset);
  }
}