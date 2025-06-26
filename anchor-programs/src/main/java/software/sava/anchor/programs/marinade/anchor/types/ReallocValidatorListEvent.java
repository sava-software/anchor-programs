package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record ReallocValidatorListEvent(PublicKey state,
                                        int count,
                                        int newCapacity) implements Borsh {

  public static final int BYTES = 40;

  public static ReallocValidatorListEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var count = getInt32LE(_data, i);
    i += 4;
    final var newCapacity = getInt32LE(_data, i);
    return new ReallocValidatorListEvent(state, count, newCapacity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt32LE(_data, i, count);
    i += 4;
    putInt32LE(_data, i, newCapacity);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
