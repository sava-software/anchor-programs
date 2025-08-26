package software.sava.anchor.programs.glam.mint.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PendingRequest(PublicKey user,
                             long incoming,
                             long outgoing,
                             long createdAt,
                             long fulfilledAt,
                             int timeUnit,
                             RequestType requestType,
                             byte[] reserved) implements Borsh {

  public static final int BYTES = 72;
  public static final int RESERVED_LEN = 6;

  public static PendingRequest read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var user = readPubKey(_data, i);
    i += 32;
    final var incoming = getInt64LE(_data, i);
    i += 8;
    final var outgoing = getInt64LE(_data, i);
    i += 8;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var fulfilledAt = getInt64LE(_data, i);
    i += 8;
    final var timeUnit = _data[i] & 0xFF;
    ++i;
    final var requestType = RequestType.read(_data, i);
    i += Borsh.len(requestType);
    final var reserved = new byte[6];
    Borsh.readArray(reserved, _data, i);
    return new PendingRequest(user,
                              incoming,
                              outgoing,
                              createdAt,
                              fulfilledAt,
                              timeUnit,
                              requestType,
                              reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    user.write(_data, i);
    i += 32;
    putInt64LE(_data, i, incoming);
    i += 8;
    putInt64LE(_data, i, outgoing);
    i += 8;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, fulfilledAt);
    i += 8;
    _data[i] = (byte) timeUnit;
    ++i;
    i += Borsh.write(requestType, _data, i);
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
