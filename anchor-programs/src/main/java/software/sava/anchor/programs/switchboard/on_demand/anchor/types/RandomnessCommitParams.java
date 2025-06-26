package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record RandomnessCommitParams() implements Borsh {

  private static final RandomnessCommitParams INSTANCE = new RandomnessCommitParams();

  public static RandomnessCommitParams read(final byte[] _data, final int offset) {
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
