package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record CollectTokenRewardParams() implements Borsh {

  private static final CollectTokenRewardParams INSTANCE = new CollectTokenRewardParams();

  public static CollectTokenRewardParams read(final byte[] _data, final int offset) {
    return INSTANCE;
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    return 0;
  }

  @Override
  public int l() {
    return 0;
  }
}
