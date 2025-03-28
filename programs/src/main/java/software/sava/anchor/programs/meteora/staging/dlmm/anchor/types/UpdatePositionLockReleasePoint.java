package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdatePositionLockReleasePoint(PublicKey position,
                                             long currentPoint,
                                             long newLockReleasePoint,
                                             long oldLockReleasePoint,
                                             PublicKey sender) implements Borsh {

  public static final int BYTES = 88;

  public static UpdatePositionLockReleasePoint read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var position = readPubKey(_data, i);
    i += 32;
    final var currentPoint = getInt64LE(_data, i);
    i += 8;
    final var newLockReleasePoint = getInt64LE(_data, i);
    i += 8;
    final var oldLockReleasePoint = getInt64LE(_data, i);
    i += 8;
    final var sender = readPubKey(_data, i);
    return new UpdatePositionLockReleasePoint(position,
                                              currentPoint,
                                              newLockReleasePoint,
                                              oldLockReleasePoint,
                                              sender);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    position.write(_data, i);
    i += 32;
    putInt64LE(_data, i, currentPoint);
    i += 8;
    putInt64LE(_data, i, newLockReleasePoint);
    i += 8;
    putInt64LE(_data, i, oldLockReleasePoint);
    i += 8;
    sender.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
