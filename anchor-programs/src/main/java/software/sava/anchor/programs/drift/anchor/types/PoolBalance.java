package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record PoolBalance(// To get the pool's token amount, you must multiply the scaled balance by the market's cumulative
                          // deposit interest
                          // precision: SPOT_BALANCE_PRECISION
                          BigInteger scaledBalance,
                          // The spot market the pool is for
                          int marketIndex,
                          byte[] padding) implements Borsh {

  public static final int BYTES = 24;
  public static final int PADDING_LEN = 6;

  public static PoolBalance read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var scaledBalance = getInt128LE(_data, i);
    i += 16;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[6];
    Borsh.readArray(padding, _data, i);
    return new PoolBalance(scaledBalance, marketIndex, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, scaledBalance);
    i += 16;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.writeArrayChecked(padding, 6, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
