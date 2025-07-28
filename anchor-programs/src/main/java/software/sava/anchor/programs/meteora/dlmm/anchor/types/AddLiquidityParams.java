package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddLiquidityParams(int minDeltaId,
                                 int maxDeltaId,
                                 long x0,
                                 long y0,
                                 long deltaX,
                                 long deltaY,
                                 int bitFlag,
                                 boolean favorXInActiveId,
                                 byte[] padding) implements Borsh {

  public static final int BYTES = 58;
  public static final int PADDING_LEN = 16;

  public static AddLiquidityParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minDeltaId = getInt32LE(_data, i);
    i += 4;
    final var maxDeltaId = getInt32LE(_data, i);
    i += 4;
    final var x0 = getInt64LE(_data, i);
    i += 8;
    final var y0 = getInt64LE(_data, i);
    i += 8;
    final var deltaX = getInt64LE(_data, i);
    i += 8;
    final var deltaY = getInt64LE(_data, i);
    i += 8;
    final var bitFlag = _data[i] & 0xFF;
    ++i;
    final var favorXInActiveId = _data[i] == 1;
    ++i;
    final var padding = new byte[16];
    Borsh.readArray(padding, _data, i);
    return new AddLiquidityParams(minDeltaId,
                                  maxDeltaId,
                                  x0,
                                  y0,
                                  deltaX,
                                  deltaY,
                                  bitFlag,
                                  favorXInActiveId,
                                  padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, minDeltaId);
    i += 4;
    putInt32LE(_data, i, maxDeltaId);
    i += 4;
    putInt64LE(_data, i, x0);
    i += 8;
    putInt64LE(_data, i, y0);
    i += 8;
    putInt64LE(_data, i, deltaX);
    i += 8;
    putInt64LE(_data, i, deltaY);
    i += 8;
    _data[i] = (byte) bitFlag;
    ++i;
    _data[i] = (byte) (favorXInActiveId ? 1 : 0);
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
