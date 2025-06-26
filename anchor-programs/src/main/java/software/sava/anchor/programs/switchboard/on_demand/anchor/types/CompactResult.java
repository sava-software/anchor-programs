package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getFloat32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putFloat32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CompactResult(// The standard deviation of the submissions needed for quorom size
                            float stdDev,
                            // The mean of the submissions needed for quorom size
                            float mean,
                            // The slot at which this value was signed.
                            long slot) implements Borsh {

  public static final int BYTES = 16;

  public static CompactResult read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stdDev = getFloat32LE(_data, i);
    i += 4;
    final var mean = getFloat32LE(_data, i);
    i += 4;
    final var slot = getInt64LE(_data, i);
    return new CompactResult(stdDev, mean, slot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putFloat32LE(_data, i, stdDev);
    i += 4;
    putFloat32LE(_data, i, mean);
    i += 4;
    putInt64LE(_data, i, slot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
