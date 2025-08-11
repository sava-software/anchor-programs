package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EditTriggerOrderParams(int orderId,
                                     OraclePrice triggerPrice,
                                     long deltaSizeAmount,
                                     boolean isStopLoss) implements Borsh {

  public static final int BYTES = 22;

  public static EditTriggerOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var orderId = _data[i] & 0xFF;
    ++i;
    final var triggerPrice = OraclePrice.read(_data, i);
    i += Borsh.len(triggerPrice);
    final var deltaSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var isStopLoss = _data[i] == 1;
    return new EditTriggerOrderParams(orderId,
                                      triggerPrice,
                                      deltaSizeAmount,
                                      isStopLoss);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) orderId;
    ++i;
    i += Borsh.write(triggerPrice, _data, i);
    putInt64LE(_data, i, deltaSizeAmount);
    i += 8;
    _data[i] = (byte) (isStopLoss ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
