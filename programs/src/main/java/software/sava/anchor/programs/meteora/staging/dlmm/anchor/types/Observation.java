package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Observation(// Cumulative active bin ID
                          BigInteger cumulativeActiveBinId,
                          // Observation sample created timestamp
                          long createdAt,
                          // Observation sample last updated timestamp
                          long lastUpdatedAt) implements Borsh {

  public static final int BYTES = 32;

  public static Observation read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var cumulativeActiveBinId = getInt128LE(_data, i);
    i += 16;
    final var createdAt = getInt64LE(_data, i);
    i += 8;
    final var lastUpdatedAt = getInt64LE(_data, i);
    return new Observation(cumulativeActiveBinId, createdAt, lastUpdatedAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, cumulativeActiveBinId);
    i += 16;
    putInt64LE(_data, i, createdAt);
    i += 8;
    putInt64LE(_data, i, lastUpdatedAt);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
