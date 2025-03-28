package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeInfo(BigInteger feeXPerTokenComplete,
                      BigInteger feeYPerTokenComplete,
                      long feeXPending,
                      long feeYPending) implements Borsh {

  public static final int BYTES = 48;

  public static FeeInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeXPerTokenComplete = getInt128LE(_data, i);
    i += 16;
    final var feeYPerTokenComplete = getInt128LE(_data, i);
    i += 16;
    final var feeXPending = getInt64LE(_data, i);
    i += 8;
    final var feeYPending = getInt64LE(_data, i);
    return new FeeInfo(feeXPerTokenComplete,
                       feeYPerTokenComplete,
                       feeXPending,
                       feeYPending);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, feeXPerTokenComplete);
    i += 16;
    putInt128LE(_data, i, feeYPerTokenComplete);
    i += 16;
    putInt64LE(_data, i, feeXPending);
    i += 8;
    putInt64LE(_data, i, feeYPending);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
