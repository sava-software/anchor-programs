package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TargetsDatum(int costToTradeBps,
                           byte[] padding,
                           long targetBase,
                           long lastOracleSlot,
                           long lastPositionSlot) implements Borsh {

  public static final int BYTES = 32;
  public static final int PADDING_LEN = 4;

  public static TargetsDatum read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var costToTradeBps = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[4];
    i += Borsh.readArray(padding, _data, i);
    final var targetBase = getInt64LE(_data, i);
    i += 8;
    final var lastOracleSlot = getInt64LE(_data, i);
    i += 8;
    final var lastPositionSlot = getInt64LE(_data, i);
    return new TargetsDatum(costToTradeBps,
                            padding,
                            targetBase,
                            lastOracleSlot,
                            lastPositionSlot);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, costToTradeBps);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    putInt64LE(_data, i, targetBase);
    i += 8;
    putInt64LE(_data, i, lastOracleSlot);
    i += 8;
    putInt64LE(_data, i, lastPositionSlot);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
