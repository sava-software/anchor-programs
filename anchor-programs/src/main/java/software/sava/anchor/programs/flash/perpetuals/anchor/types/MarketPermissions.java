package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record MarketPermissions(boolean allowOpenPosition,
                                boolean allowClosePosition,
                                boolean allowCollateralWithdrawal,
                                boolean allowSizeChange) implements Borsh {

  public static final int BYTES = 4;

  public static MarketPermissions read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var allowOpenPosition = _data[i] == 1;
    ++i;
    final var allowClosePosition = _data[i] == 1;
    ++i;
    final var allowCollateralWithdrawal = _data[i] == 1;
    ++i;
    final var allowSizeChange = _data[i] == 1;
    return new MarketPermissions(allowOpenPosition,
                                 allowClosePosition,
                                 allowCollateralWithdrawal,
                                 allowSizeChange);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (allowOpenPosition ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowClosePosition ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowCollateralWithdrawal ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowSizeChange ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
