package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.borsh.Borsh;

public enum RewardType implements Borsh.Enum {

  Proportional,
  Constant;

  public static RewardType read(final byte[] _data, final int offset) {
    return Borsh.read(RewardType.values(), _data, offset);
  }
}