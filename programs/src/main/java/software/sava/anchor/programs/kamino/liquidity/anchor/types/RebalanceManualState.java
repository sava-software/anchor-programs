package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public record RebalanceManualState() implements Borsh {

  private static final RebalanceManualState INSTANCE = new RebalanceManualState();

  public static RebalanceManualState read(final byte[] _data, final int offset) {
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
