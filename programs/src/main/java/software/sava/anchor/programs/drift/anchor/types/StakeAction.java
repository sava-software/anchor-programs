package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum StakeAction implements Borsh.Enum {

  Stake,
  UnstakeRequest,
  UnstakeCancelRequest,
  Unstake,
  UnstakeTransfer,
  StakeTransfer;

  public static StakeAction read(final byte[] _data, final int offset) {
    return Borsh.read(StakeAction.values(), _data, offset);
  }
}