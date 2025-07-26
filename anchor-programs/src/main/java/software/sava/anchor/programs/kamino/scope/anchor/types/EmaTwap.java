package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EmaTwap(long lastUpdateSlot,
                      long lastUpdateUnixTimestamp,
                      BigInteger currentEma1h,
                      // The sample tracker is a 64 bit number where each bit represents a point in time.
                      long updatesTracker1h,
                      long padding0,
                      BigInteger[] padding1) implements Borsh {

  public static final int BYTES = 672;
  public static final int PADDING_1_LEN = 39;

  public static EmaTwap read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateUnixTimestamp = getInt64LE(_data, i);
    i += 8;
    final var currentEma1h = getInt128LE(_data, i);
    i += 16;
    final var updatesTracker1h = getInt64LE(_data, i);
    i += 8;
    final var padding0 = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new BigInteger[39];
    Borsh.read128Array(padding1, _data, i);
    return new EmaTwap(lastUpdateSlot,
                       lastUpdateUnixTimestamp,
                       currentEma1h,
                       updatesTracker1h,
                       padding0,
                       padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastUpdateSlot);
    i += 8;
    putInt64LE(_data, i, lastUpdateUnixTimestamp);
    i += 8;
    putInt128LE(_data, i, currentEma1h);
    i += 16;
    putInt64LE(_data, i, updatesTracker1h);
    i += 8;
    putInt64LE(_data, i, padding0);
    i += 8;
    i += Borsh.write128Array(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
