package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record CancelTriggerOrderParams(int orderId, boolean isStopLoss) implements Borsh {

  public static final int BYTES = 2;

  public static CancelTriggerOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var orderId = _data[i] & 0xFF;
    ++i;
    final var isStopLoss = _data[i] == 1;
    return new CancelTriggerOrderParams(orderId, isStopLoss);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) orderId;
    ++i;
    _data[i] = (byte) (isStopLoss ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
