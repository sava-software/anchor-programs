package software.sava.anchor.programs.jito.steward.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StewardStateEnum implements Borsh.Enum {

  ComputeScores,
  ComputeDelegations,
  Idle,
  ComputeInstantUnstake,
  Rebalance;

  public static StewardStateEnum read(final byte[] _data, final int offset) {
    return Borsh.read(StewardStateEnum.values(), _data, offset);
  }
}