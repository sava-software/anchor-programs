package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record RebalancePricePercentageWithResetState(BigInteger lastRebalanceLowerResetPoolPrice, BigInteger lastRebalanceUpperResetPoolPrice) implements Borsh {

  public static final int BYTES = 32;

  public static RebalancePricePercentageWithResetState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastRebalanceLowerResetPoolPrice = getInt128LE(_data, i);
    i += 16;
    final var lastRebalanceUpperResetPoolPrice = getInt128LE(_data, i);
    return new RebalancePricePercentageWithResetState(lastRebalanceLowerResetPoolPrice, lastRebalanceUpperResetPoolPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, lastRebalanceLowerResetPoolPrice);
    i += 16;
    putInt128LE(_data, i, lastRebalanceUpperResetPoolPrice);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
