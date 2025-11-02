package software.sava.anchor.programs.glam.mint.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record AumRecord(BigInteger baseAssetAmount) implements Borsh {

  public static final int BYTES = 16;

  public static AumRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var baseAssetAmount = getInt128LE(_data, offset);
    return new AumRecord(baseAssetAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, baseAssetAmount);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
