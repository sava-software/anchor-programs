package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record OracleSetOperatorParams() implements Borsh {

  private static final OracleSetOperatorParams INSTANCE = new OracleSetOperatorParams();

  public static OracleSetOperatorParams read(final byte[] _data, final int offset) {
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
