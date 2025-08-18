package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultUnstakeParams(int actionType, long principalAmount) implements Borsh {

  public static final int BYTES = 9;

  public static VaultUnstakeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var actionType = _data[i] & 0xFF;
    ++i;
    final var principalAmount = getInt64LE(_data, i);
    return new VaultUnstakeParams(actionType, principalAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) actionType;
    ++i;
    putInt64LE(_data, i, principalAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
