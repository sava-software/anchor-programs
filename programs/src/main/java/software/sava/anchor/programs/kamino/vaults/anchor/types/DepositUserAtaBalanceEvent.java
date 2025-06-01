package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DepositUserAtaBalanceEvent(long userAtaBalance) implements Borsh {

  public static final int BYTES = 8;

  public static DepositUserAtaBalanceEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var userAtaBalance = getInt64LE(_data, offset);
    return new DepositUserAtaBalanceEvent(userAtaBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, userAtaBalance);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
