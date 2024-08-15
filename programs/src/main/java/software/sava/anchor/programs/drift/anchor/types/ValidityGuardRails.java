package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ValidityGuardRails(long slotsBeforeStaleForAmm,
                                 long slotsBeforeStaleForMargin,
                                 long confidenceIntervalMaxSize,
                                 long tooVolatileRatio) implements Borsh {

  public static final int BYTES = 32;

  public static ValidityGuardRails read(final byte[] _data, final int offset) {
    int i = offset;
    final var slotsBeforeStaleForAmm = getInt64LE(_data, i);
    i += 8;
    final var slotsBeforeStaleForMargin = getInt64LE(_data, i);
    i += 8;
    final var confidenceIntervalMaxSize = getInt64LE(_data, i);
    i += 8;
    final var tooVolatileRatio = getInt64LE(_data, i);
    return new ValidityGuardRails(slotsBeforeStaleForAmm,
                                  slotsBeforeStaleForMargin,
                                  confidenceIntervalMaxSize,
                                  tooVolatileRatio);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slotsBeforeStaleForAmm);
    i += 8;
    putInt64LE(_data, i, slotsBeforeStaleForMargin);
    i += 8;
    putInt64LE(_data, i, confidenceIntervalMaxSize);
    i += 8;
    putInt64LE(_data, i, tooVolatileRatio);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
