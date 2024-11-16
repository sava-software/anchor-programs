package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SwiftUserOrdersFixed(PublicKey userPubkey,
                                   int padding,
                                   int len) implements Borsh {

  public static final int BYTES = 40;

  public static SwiftUserOrdersFixed read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var userPubkey = readPubKey(_data, i);
    i += 32;
    final var padding = getInt32LE(_data, i);
    i += 4;
    final var len = getInt32LE(_data, i);
    return new SwiftUserOrdersFixed(userPubkey, padding, len);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    userPubkey.write(_data, i);
    i += 32;
    putInt32LE(_data, i, padding);
    i += 4;
    putInt32LE(_data, i, len);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
