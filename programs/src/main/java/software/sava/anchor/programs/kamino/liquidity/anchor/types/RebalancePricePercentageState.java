package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record RebalancePricePercentageState(BigInteger lastRebalanceLowerPoolPrice, BigInteger lastRebalanceUpperPoolPrice) implements Borsh {

  public static final int BYTES = 32;

  public static RebalancePricePercentageState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastRebalanceLowerPoolPrice = getInt128LE(_data, i);
    i += 16;
    final var lastRebalanceUpperPoolPrice = getInt128LE(_data, i);
    return new RebalancePricePercentageState(lastRebalanceLowerPoolPrice, lastRebalanceUpperPoolPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, lastRebalanceLowerPoolPrice);
    i += 16;
    putInt128LE(_data, i, lastRebalanceUpperPoolPrice);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
