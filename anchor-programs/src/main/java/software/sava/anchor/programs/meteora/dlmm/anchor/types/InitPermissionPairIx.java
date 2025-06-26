package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record InitPermissionPairIx(int activeId,
                                   int binStep,
                                   int baseFactor,
                                   int baseFeePowerFactor,
                                   int activationType,
                                   int protocolShare) implements Borsh {

  public static final int BYTES = 12;

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
    final var baseFeePowerFactor = _data[i] & 0xFF;
    ++i;
    final var activationType = _data[i] & 0xFF;
    ++i;
    final var protocolShare = getInt16LE(_data, i);
    return new InitPermissionPairIx(activeId,
                                    binStep,
                                    baseFactor,
                                    baseFeePowerFactor,
                                    activationType,
                                    protocolShare);
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
    _data[i] = (byte) baseFeePowerFactor;
    ++i;
    _data[i] = (byte) activationType;
    ++i;
    putInt16LE(_data, i, protocolShare);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
