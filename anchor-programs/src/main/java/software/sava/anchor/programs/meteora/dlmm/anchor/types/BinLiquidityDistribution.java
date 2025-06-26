package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record BinLiquidityDistribution(// Define the bin ID wish to deposit to.
                                       int binId,
                                       // DistributionX (or distributionY) is the percentages of amountX (or amountY) you want to add to each bin.
                                       int distributionX,
                                       // DistributionX (or distributionY) is the percentages of amountX (or amountY) you want to add to each bin.
                                       int distributionY) implements Borsh {

  public static final int BYTES = 8;

  public static BinLiquidityDistribution read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var binId = getInt32LE(_data, i);
    i += 4;
    final var distributionX = getInt16LE(_data, i);
    i += 2;
    final var distributionY = getInt16LE(_data, i);
    return new BinLiquidityDistribution(binId, distributionX, distributionY);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, binId);
    i += 4;
    putInt16LE(_data, i, distributionX);
    i += 2;
    putInt16LE(_data, i, distributionY);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
