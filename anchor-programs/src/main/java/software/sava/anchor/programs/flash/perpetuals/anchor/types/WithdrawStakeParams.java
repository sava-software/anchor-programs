package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record WithdrawStakeParams(boolean pendingActivation, boolean deactivated) implements Borsh {

  public static final int BYTES = 2;

  public static WithdrawStakeParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pendingActivation = _data[i] == 1;
    ++i;
    final var deactivated = _data[i] == 1;
    return new WithdrawStakeParams(pendingActivation, deactivated);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (pendingActivation ? 1 : 0);
    ++i;
    _data[i] = (byte) (deactivated ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
