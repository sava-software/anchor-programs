package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidityParameter(// Amount of X token to deposit
                                 long amountX,
                                 // Amount of Y token to deposit
                                 long amountY,
                                 // Liquidity distribution to each bins
                                 BinLiquidityDistribution[] binLiquidityDist) implements Borsh {

  public static LiquidityParameter read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountX = getInt64LE(_data, i);
    i += 8;
    final var amountY = getInt64LE(_data, i);
    i += 8;
    final var binLiquidityDist = Borsh.readVector(BinLiquidityDistribution.class, BinLiquidityDistribution::read, _data, i);
    return new LiquidityParameter(amountX, amountY, binLiquidityDist);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountX);
    i += 8;
    putInt64LE(_data, i, amountY);
    i += 8;
    i += Borsh.write(binLiquidityDist, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 8 + Borsh.len(binLiquidityDist);
  }
}
