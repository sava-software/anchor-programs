package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record GetEntryPriceAndFeeParams(long collateral,
                                        long size,
                                        Side side) implements Borsh {

  public static final int BYTES = 17;

  public static GetEntryPriceAndFeeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateral = getInt64LE(_data, i);
    i += 8;
    final var size = getInt64LE(_data, i);
    i += 8;
    final var side = Side.read(_data, i);
    return new GetEntryPriceAndFeeParams(collateral, size, side);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, collateral);
    i += 8;
    putInt64LE(_data, i, size);
    i += 8;
    i += Borsh.write(side, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
