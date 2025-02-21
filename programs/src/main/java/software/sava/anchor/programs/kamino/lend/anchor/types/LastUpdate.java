package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LastUpdate(long slot,
                         int stale,
                         int priceStatus,
                         byte[] placeholder) implements Borsh {

  public static final int BYTES = 16;

  public static LastUpdate read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var stale = _data[i] & 0xFF;
    ++i;
    final var priceStatus = _data[i] & 0xFF;
    ++i;
    final var placeholder = new byte[6];
    Borsh.readArray(placeholder, _data, i);
    return new LastUpdate(slot,
                          stale,
                          priceStatus,
                          placeholder);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    _data[i] = (byte) stale;
    ++i;
    _data[i] = (byte) priceStatus;
    ++i;
    i += Borsh.writeArray(placeholder, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
