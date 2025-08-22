package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Round(int roundId,
                    long slot,
                    int timestamp,
                    BigInteger answer) implements Borsh {

  public static final int BYTES = 32;

  public static Round read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var roundId = getInt32LE(_data, i);
    i += 4;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var timestamp = getInt32LE(_data, i);
    i += 4;
    final var answer = getInt128LE(_data, i);
    return new Round(roundId,
                     slot,
                     timestamp,
                     answer);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, roundId);
    i += 4;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt32LE(_data, i, timestamp);
    i += 4;
    putInt128LE(_data, i, answer);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
