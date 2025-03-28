package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record BinLiquidityReduction(int binId, int bpsToRemove) implements Borsh {

  public static final int BYTES = 6;

  public static BinLiquidityReduction read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var binId = getInt32LE(_data, i);
    i += 4;
    final var bpsToRemove = getInt16LE(_data, i);
    return new BinLiquidityReduction(binId, bpsToRemove);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, binId);
    i += 4;
    putInt16LE(_data, i, bpsToRemove);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
