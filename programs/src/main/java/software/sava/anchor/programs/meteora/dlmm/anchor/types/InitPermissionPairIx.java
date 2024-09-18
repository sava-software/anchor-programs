package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitPermissionPairIx(int activeId,
                                   int binStep,
                                   int baseFactor,
                                   int minBinId,
                                   int maxBinId,
                                   long lockDuration,
                                   int activationType) implements Borsh {

  public static final int BYTES = 25;

  public static InitPermissionPairIx read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var binStep = getInt16LE(_data, i);
    i += 2;
    final var baseFactor = getInt16LE(_data, i);
    i += 2;
    final var minBinId = getInt32LE(_data, i);
    i += 4;
    final var maxBinId = getInt32LE(_data, i);
    i += 4;
    final var lockDuration = getInt64LE(_data, i);
    i += 8;
    final var activationType = _data[i] & 0xFF;
    return new InitPermissionPairIx(activeId,
                                    binStep,
                                    baseFactor,
                                    minBinId,
                                    maxBinId,
                                    lockDuration,
                                    activationType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, activeId);
    i += 4;
    putInt16LE(_data, i, binStep);
    i += 2;
    putInt16LE(_data, i, baseFactor);
    i += 2;
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    putInt64LE(_data, i, lockDuration);
    i += 8;
    _data[i] = (byte) activationType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}