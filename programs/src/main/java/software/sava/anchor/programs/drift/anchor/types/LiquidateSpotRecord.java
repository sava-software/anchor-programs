package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LiquidateSpotRecord(int assetMarketIndex,
                                  long assetPrice,
                                  BigInteger assetTransfer,
                                  int liabilityMarketIndex,
                                  long liabilityPrice,
                                  // precision: token mint precision
                                  BigInteger liabilityTransfer,
                                  // precision: token mint precision
                                  long ifFee) implements Borsh {

  public static final int BYTES = 60;

  public static LiquidateSpotRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var assetMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var assetPrice = getInt64LE(_data, i);
    i += 8;
    final var assetTransfer = getInt128LE(_data, i);
    i += 16;
    final var liabilityMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var liabilityPrice = getInt64LE(_data, i);
    i += 8;
    final var liabilityTransfer = getInt128LE(_data, i);
    i += 16;
    final var ifFee = getInt64LE(_data, i);
    return new LiquidateSpotRecord(assetMarketIndex,
                                   assetPrice,
                                   assetTransfer,
                                   liabilityMarketIndex,
                                   liabilityPrice,
                                   liabilityTransfer,
                                   ifFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, assetMarketIndex);
    i += 2;
    putInt64LE(_data, i, assetPrice);
    i += 8;
    putInt128LE(_data, i, assetTransfer);
    i += 16;
    putInt16LE(_data, i, liabilityMarketIndex);
    i += 2;
    putInt64LE(_data, i, liabilityPrice);
    i += 8;
    putInt128LE(_data, i, liabilityTransfer);
    i += 16;
    putInt64LE(_data, i, ifFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 2
         + 8
         + 16
         + 2
         + 8
         + 16
         + 8;
  }
}
