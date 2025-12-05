package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UserStatsPausedOperations implements Borsh.Enum {

  UpdateBidAskTwap,
  AmmAtomicFill,
  AmmAtomicRiskIncreasingFill;

  public static UserStatsPausedOperations read(final byte[] _data, final int offset) {
    return Borsh.read(UserStatsPausedOperations.values(), _data, offset);
  }
}