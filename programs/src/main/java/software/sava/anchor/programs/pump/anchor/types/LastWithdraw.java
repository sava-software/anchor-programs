package software.sava.anchor.programs.pump.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LastWithdraw(long lastWithdrawTimestamp) implements Borsh {

  public static final int BYTES = 8;

  public static LastWithdraw read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var lastWithdrawTimestamp = getInt64LE(_data, offset);
    return new LastWithdraw(lastWithdrawTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastWithdrawTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
