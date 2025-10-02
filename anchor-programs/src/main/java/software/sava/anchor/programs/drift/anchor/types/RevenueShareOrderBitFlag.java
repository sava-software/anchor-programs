package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RevenueShareOrderBitFlag implements Borsh.Enum {

  Init,
  Open,
  Completed,
  Referral;

  public static RevenueShareOrderBitFlag read(final byte[] _data, final int offset) {
    return Borsh.read(RevenueShareOrderBitFlag.values(), _data, offset);
  }
}