package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidateBorrowForPerpPnlRecord(int perpMarketIndex,
                                              long marketOraclePrice,
                                              BigInteger pnlTransfer,
                                              int liabilityMarketIndex,
                                              long liabilityPrice,
                                              BigInteger liabilityTransfer) implements Borsh {

  public static final int BYTES = 52;

  public static LiquidateBorrowForPerpPnlRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var perpMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var marketOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var pnlTransfer = getInt128LE(_data, i);
    i += 16;
    final var liabilityMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var liabilityPrice = getInt64LE(_data, i);
    i += 8;
    final var liabilityTransfer = getInt128LE(_data, i);
    return new LiquidateBorrowForPerpPnlRecord(perpMarketIndex,
                                               marketOraclePrice,
                                               pnlTransfer,
                                               liabilityMarketIndex,
                                               liabilityPrice,
                                               liabilityTransfer);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, perpMarketIndex);
    i += 2;
    putInt64LE(_data, i, marketOraclePrice);
    i += 8;
    putInt128LE(_data, i, pnlTransfer);
    i += 16;
    putInt16LE(_data, i, liabilityMarketIndex);
    i += 2;
    putInt64LE(_data, i, liabilityPrice);
    i += 8;
    putInt128LE(_data, i, liabilityTransfer);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return 2
         + 8
         + 16
         + 2
         + 8
         + 16;
  }
}
