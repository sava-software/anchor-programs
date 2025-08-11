package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LimitOrder(OraclePrice limitPrice,
                         long reserveAmount,
                         int reserveCustodyUid,
                         int receiveCustodyUid,
                         long sizeAmount,
                         OraclePrice stopLossPrice,
                         OraclePrice takeProfitPrice) implements Borsh {

  public static final int BYTES = 54;

  public static LimitOrder read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var limitPrice = OraclePrice.read(_data, i);
    i += Borsh.len(limitPrice);
    final var reserveAmount = getInt64LE(_data, i);
    i += 8;
    final var reserveCustodyUid = _data[i] & 0xFF;
    ++i;
    final var receiveCustodyUid = _data[i] & 0xFF;
    ++i;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var stopLossPrice = OraclePrice.read(_data, i);
    i += Borsh.len(stopLossPrice);
    final var takeProfitPrice = OraclePrice.read(_data, i);
    return new LimitOrder(limitPrice,
                          reserveAmount,
                          reserveCustodyUid,
                          receiveCustodyUid,
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
    _data[i] = (byte) reserveCustodyUid;
    ++i;
    _data[i] = (byte) receiveCustodyUid;
    ++i;
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
