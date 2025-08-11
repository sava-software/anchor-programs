package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DistributeTokenRewardLog(long amount,
                                       int epochCount,
                                       long[] padding) implements Borsh {

  public static final int BYTES = 28;
  public static final int PADDING_LEN = 2;

  public static DistributeTokenRewardLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var epochCount = getInt32LE(_data, i);
    i += 4;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new DistributeTokenRewardLog(amount, epochCount, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt32LE(_data, i, epochCount);
    i += 4;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
