package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record CurvePoint(int utilizationRateBps, int borrowRateBps) implements Borsh {

  public static final int BYTES = 8;

  public static CurvePoint read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var utilizationRateBps = getInt32LE(_data, i);
    i += 4;
    final var borrowRateBps = getInt32LE(_data, i);
    return new CurvePoint(utilizationRateBps, borrowRateBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, utilizationRateBps);
    i += 4;
    putInt32LE(_data, i, borrowRateBps);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
