package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SharesToWithdrawEvent(long sharesAmount, long userSharesBefore) implements Borsh {

  public static final int BYTES = 16;

  public static SharesToWithdrawEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var sharesAmount = getInt64LE(_data, i);
    i += 8;
    final var userSharesBefore = getInt64LE(_data, i);
    return new SharesToWithdrawEvent(sharesAmount, userSharesBefore);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, sharesAmount);
    i += 8;
    putInt64LE(_data, i, userSharesBefore);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
