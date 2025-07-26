package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record PythLazerData(int feedId,
                            int exponent,
                            int confidenceFactor) implements Borsh {

  public static final int BYTES = 7;

  public static PythLazerData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feedId = getInt16LE(_data, i);
    i += 2;
    final var exponent = _data[i] & 0xFF;
    ++i;
    final var confidenceFactor = getInt32LE(_data, i);
    return new PythLazerData(feedId, exponent, confidenceFactor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, feedId);
    i += 2;
    _data[i] = (byte) exponent;
    ++i;
    putInt32LE(_data, i, confidenceFactor);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
