package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CurrentResult(// The median value of the submissions needed for quorom size
                            BigInteger value,
                            // The standard deviation of the submissions needed for quorom size
                            BigInteger stdDev,
                            // The mean of the submissions needed for quorom size
                            BigInteger mean,
                            // The range of the submissions needed for quorom size
                            BigInteger range,
                            // The minimum value of the submissions needed for quorom size
                            BigInteger minValue,
                            // The maximum value of the submissions needed for quorom size
                            BigInteger maxValue,
                            // The number of samples used to calculate this result
                            int numSamples,
                            byte[] padding1,
                            // The slot at which this value was signed.
                            long slot,
                            // The slot at which the first considered submission was made
                            long minSlot,
                            // The slot at which the last considered submission was made
                            long maxSlot) implements Borsh {

  public static final int BYTES = 128;

  public static CurrentResult read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var value = getInt128LE(_data, i);
    i += 16;
    final var stdDev = getInt128LE(_data, i);
    i += 16;
    final var mean = getInt128LE(_data, i);
    i += 16;
    final var range = getInt128LE(_data, i);
    i += 16;
    final var minValue = getInt128LE(_data, i);
    i += 16;
    final var maxValue = getInt128LE(_data, i);
    i += 16;
    final var numSamples = _data[i] & 0xFF;
    ++i;
    final var padding1 = new byte[7];
    i += Borsh.readArray(padding1, _data, i);
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var minSlot = getInt64LE(_data, i);
    i += 8;
    final var maxSlot = getInt64LE(_data, i);
    return new CurrentResult(value,
                             stdDev,
                             mean,
                             range,
                             minValue,
                             maxValue,
                             numSamples,
                             padding1,
                             slot,
                             minSlot,
                             maxSlot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, value);
    i += 16;
    putInt128LE(_data, i, stdDev);
    i += 16;
    putInt128LE(_data, i, mean);
    i += 16;
    putInt128LE(_data, i, range);
    i += 16;
    putInt128LE(_data, i, minValue);
    i += 16;
    putInt128LE(_data, i, maxValue);
    i += 16;
    _data[i] = (byte) numSamples;
    ++i;
    i += Borsh.writeArray(padding1, _data, i);
    putInt64LE(_data, i, slot);
    i += 8;
    putInt64LE(_data, i, minSlot);
    i += 8;
    putInt64LE(_data, i, maxSlot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
