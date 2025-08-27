package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddLiquiditySingleSidePreciseParameter2(CompressedBinDepositAmount[] bins,
                                                      long decompressMultiplier,
                                                      long maxAmount) implements Borsh {

  public static AddLiquiditySingleSidePreciseParameter2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var bins = Borsh.readVector(CompressedBinDepositAmount.class, CompressedBinDepositAmount::read, _data, i);
    i += Borsh.lenVector(bins);
    final var decompressMultiplier = getInt64LE(_data, i);
    i += 8;
    final var maxAmount = getInt64LE(_data, i);
    return new AddLiquiditySingleSidePreciseParameter2(bins, decompressMultiplier, maxAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(bins, _data, i);
    putInt64LE(_data, i, decompressMultiplier);
    i += 8;
    putInt64LE(_data, i, maxAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(bins) + 8 + 8;
  }
}
