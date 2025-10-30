package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record ConstituentTargetBaseFixed(PublicKey lpPool,
                                         int bump,
                                         byte[] pad,
                                         // total elements in the flattened `data` vec
                                         int len) implements Borsh {

  public static final int BYTES = 40;
  public static final int PAD_LEN = 3;

  public static ConstituentTargetBaseFixed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lpPool = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var pad = new byte[3];
    i += Borsh.readArray(pad, _data, i);
    final var len = getInt32LE(_data, i);
    return new ConstituentTargetBaseFixed(lpPool,
                                          bump,
                                          pad,
                                          len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lpPool.write(_data, i);
    i += 32;
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
