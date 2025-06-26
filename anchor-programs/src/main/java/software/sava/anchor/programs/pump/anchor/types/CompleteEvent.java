package software.sava.anchor.programs.pump.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CompleteEvent(PublicKey user,
                            PublicKey mint,
                            PublicKey bondingCurve,
                            long timestamp) implements Borsh {

  public static final int BYTES = 104;

  public static CompleteEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var user = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var bondingCurve = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    return new CompleteEvent(user,
                             mint,
                             bondingCurve,
                             timestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    user.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    bondingCurve.write(_data, i);
    i += 32;
    putInt64LE(_data, i, timestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
