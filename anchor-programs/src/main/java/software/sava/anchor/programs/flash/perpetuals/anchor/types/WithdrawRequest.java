package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawRequest(long pendingDeactivation, long withdrawRequestTimestamp) implements Borsh {

  public static final int BYTES = 16;

  public static WithdrawRequest read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pendingDeactivation = getInt64LE(_data, i);
    i += 8;
    final var withdrawRequestTimestamp = getInt64LE(_data, i);
    return new WithdrawRequest(pendingDeactivation, withdrawRequestTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, pendingDeactivation);
    i += 8;
    putInt64LE(_data, i, withdrawRequestTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
