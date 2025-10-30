package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record AmmCacheFixed(int bump,
                            byte[] pad,
                            int len) implements Borsh {

  public static final int BYTES = 8;
  public static final int PAD_LEN = 3;

  public static AmmCacheFixed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var pad = new byte[3];
    i += Borsh.readArray(pad, _data, i);
    final var len = getInt32LE(_data, i);
    return new AmmCacheFixed(bump, pad, len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArrayChecked(pad, 3, _data, i);
    putInt32LE(_data, i, len);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
