package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record TokenPermissions(boolean allowDeposits,
                               boolean allowWithdrawal,
                               boolean allowRewardWithdrawal) implements Borsh {

  public static final int BYTES = 3;

  public static TokenPermissions read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var allowDeposits = _data[i] == 1;
    ++i;
    final var allowWithdrawal = _data[i] == 1;
    ++i;
    final var allowRewardWithdrawal = _data[i] == 1;
    return new TokenPermissions(allowDeposits, allowWithdrawal, allowRewardWithdrawal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (allowDeposits ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowWithdrawal ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowRewardWithdrawal ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
