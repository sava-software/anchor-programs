package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetPositionPriceImpactParams(long priceImpactUsd) implements Borsh {

  public static final int BYTES = 8;

  public static SetPositionPriceImpactParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var priceImpactUsd = getInt64LE(_data, offset);
    return new SetPositionPriceImpactParams(priceImpactUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, priceImpactUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
