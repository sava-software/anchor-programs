package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record RebalanceExpanderState(BigInteger initialPoolPrice, int expansionCount) implements Borsh {

  public static final int BYTES = 18;

  public static RebalanceExpanderState read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var initialPoolPrice = getInt128LE(_data, i);
    i += 16;
    final var expansionCount = getInt16LE(_data, i);
    return new RebalanceExpanderState(initialPoolPrice, expansionCount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, initialPoolPrice);
    i += 16;
    putInt16LE(_data, i, expansionCount);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
