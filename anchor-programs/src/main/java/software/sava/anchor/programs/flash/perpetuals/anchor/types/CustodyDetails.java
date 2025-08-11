package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CustodyDetails(long tradeSpreadMin,
                             long tradeSpreadMax,
                             long delaySeconds,
                             OraclePrice minPrice,
                             OraclePrice maxPrice) implements Borsh {

  public static final int BYTES = 48;

  public static CustodyDetails read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var tradeSpreadMin = getInt64LE(_data, i);
    i += 8;
    final var tradeSpreadMax = getInt64LE(_data, i);
    i += 8;
    final var delaySeconds = getInt64LE(_data, i);
    i += 8;
    final var minPrice = OraclePrice.read(_data, i);
    i += Borsh.len(minPrice);
    final var maxPrice = OraclePrice.read(_data, i);
    return new CustodyDetails(tradeSpreadMin,
                              tradeSpreadMax,
                              delaySeconds,
                              minPrice,
                              maxPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, tradeSpreadMin);
    i += 8;
    putInt64LE(_data, i, tradeSpreadMax);
    i += 8;
    putInt64LE(_data, i, delaySeconds);
    i += 8;
    i += Borsh.write(minPrice, _data, i);
    i += Borsh.write(maxPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
