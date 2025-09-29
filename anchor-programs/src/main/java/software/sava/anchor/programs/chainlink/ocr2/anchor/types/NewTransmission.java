package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NewTransmission(int roundId,
                              byte[] configDigest,
                              BigInteger answer,
                              int transmitter,
                              int observationsTimestamp,
                              int observerCount,
                              byte[] observers,
                              long juelsPerLamport,
                              long reimbursementGjuels) implements Borsh {

  public static final int BYTES = 93;
  public static final int CONFIG_DIGEST_LEN = 32;
  public static final int OBSERVERS_LEN = 19;

  public static NewTransmission read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var roundId = getInt32LE(_data, i);
    i += 4;
    final var configDigest = new byte[32];
    i += Borsh.readArray(configDigest, _data, i);
    final var answer = getInt128LE(_data, i);
    i += 16;
    final var transmitter = _data[i] & 0xFF;
    ++i;
    final var observationsTimestamp = getInt32LE(_data, i);
    i += 4;
    final var observerCount = _data[i] & 0xFF;
    ++i;
    final var observers = new byte[19];
    i += Borsh.readArray(observers, _data, i);
    final var juelsPerLamport = getInt64LE(_data, i);
    i += 8;
    final var reimbursementGjuels = getInt64LE(_data, i);
    return new NewTransmission(roundId,
                               configDigest,
                               answer,
                               transmitter,
                               observationsTimestamp,
                               observerCount,
                               observers,
                               juelsPerLamport,
                               reimbursementGjuels);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, roundId);
    i += 4;
    i += Borsh.writeArrayChecked(configDigest, 32, _data, i);
    putInt128LE(_data, i, answer);
    i += 16;
    _data[i] = (byte) transmitter;
    ++i;
    putInt32LE(_data, i, observationsTimestamp);
    i += 4;
    _data[i] = (byte) observerCount;
    ++i;
    i += Borsh.writeArrayChecked(observers, 19, _data, i);
    putInt64LE(_data, i, juelsPerLamport);
    i += 8;
    putInt64LE(_data, i, reimbursementGjuels);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
