package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PriceAndFee(OraclePrice price, long feeUsd) implements Borsh {

  public static final int BYTES = 20;

  public static PriceAndFee read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var price = OraclePrice.read(_data, i);
    i += Borsh.len(price);
    final var feeUsd = getInt64LE(_data, i);
    return new PriceAndFee(price, feeUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(price, _data, i);
    putInt64LE(_data, i, feeUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
