package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PlaceLimitOrderParams(OraclePrice limitPrice,
                                    long reserveAmount,
                                    long sizeAmount,
                                    OraclePrice stopLossPrice,
                                    OraclePrice takeProfitPrice) implements Borsh {

  public static final int BYTES = 52;

  public static PlaceLimitOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var limitPrice = OraclePrice.read(_data, i);
    i += Borsh.len(limitPrice);
    final var reserveAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var stopLossPrice = OraclePrice.read(_data, i);
    i += Borsh.len(stopLossPrice);
    final var takeProfitPrice = OraclePrice.read(_data, i);
    return new PlaceLimitOrderParams(limitPrice,
                                     reserveAmount,
                                     sizeAmount,
                                     stopLossPrice,
                                     takeProfitPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(limitPrice, _data, i);
    putInt64LE(_data, i, reserveAmount);
    i += 8;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    i += Borsh.write(stopLossPrice, _data, i);
    i += Borsh.write(takeProfitPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
