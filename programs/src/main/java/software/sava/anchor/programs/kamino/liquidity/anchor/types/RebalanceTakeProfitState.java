package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public record RebalanceTakeProfitState(RebalanceTakeProfitStep step) implements Borsh {

  public static final int BYTES = 1;

  public static RebalanceTakeProfitState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var step = RebalanceTakeProfitStep.read(_data, offset);
    return new RebalanceTakeProfitState(step);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(step, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
