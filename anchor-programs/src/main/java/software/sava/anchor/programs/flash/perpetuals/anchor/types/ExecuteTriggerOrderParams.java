package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record ExecuteTriggerOrderParams(boolean isStopLoss,
                                        int orderId,
                                        Privilege privilege) implements Borsh {

  public static final int BYTES = 3;

  public static ExecuteTriggerOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var isStopLoss = _data[i] == 1;
    ++i;
    final var orderId = _data[i] & 0xFF;
    ++i;
    final var privilege = Privilege.read(_data, i);
    return new ExecuteTriggerOrderParams(isStopLoss, orderId, privilege);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (isStopLoss ? 1 : 0);
    ++i;
    _data[i] = (byte) orderId;
    ++i;
    i += Borsh.write(privilege, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
