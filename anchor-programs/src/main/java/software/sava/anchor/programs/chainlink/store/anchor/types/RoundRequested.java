package software.sava.anchor.programs.chainlink.store.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record RoundRequested(byte[] configDigest,
                             PublicKey requester,
                             int epoch,
                             int round) implements Borsh {

  public static final int BYTES = 69;
  public static final int CONFIG_DIGEST_LEN = 32;

  public static RoundRequested read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var configDigest = new byte[32];
    i += Borsh.readArray(configDigest, _data, i);
    final var requester = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt32LE(_data, i);
    i += 4;
    final var round = _data[i] & 0xFF;
    return new RoundRequested(configDigest,
                              requester,
                              epoch,
                              round);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(configDigest, _data, i);
    requester.write(_data, i);
    i += 32;
    putInt32LE(_data, i, epoch);
    i += 4;
    _data[i] = (byte) round;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
