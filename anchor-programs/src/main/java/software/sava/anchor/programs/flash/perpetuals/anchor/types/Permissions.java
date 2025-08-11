package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record Permissions(boolean allowSwap,
                          boolean allowAddLiquidity,
                          boolean allowRemoveLiquidity,
                          boolean allowOpenPosition,
                          boolean allowClosePosition,
                          boolean allowCollateralWithdrawal,
                          boolean allowSizeChange,
                          boolean allowLiquidation,
                          boolean allowLpStaking,
                          boolean allowFeeDistribution,
                          boolean allowUngatedTrading,
                          boolean allowFeeDiscounts,
                          boolean allowReferralRebates) implements Borsh {

  public static final int BYTES = 13;

  public static Permissions read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var allowSwap = _data[i] == 1;
    ++i;
    final var allowAddLiquidity = _data[i] == 1;
    ++i;
    final var allowRemoveLiquidity = _data[i] == 1;
    ++i;
    final var allowOpenPosition = _data[i] == 1;
    ++i;
    final var allowClosePosition = _data[i] == 1;
    ++i;
    final var allowCollateralWithdrawal = _data[i] == 1;
    ++i;
    final var allowSizeChange = _data[i] == 1;
    ++i;
    final var allowLiquidation = _data[i] == 1;
    ++i;
    final var allowLpStaking = _data[i] == 1;
    ++i;
    final var allowFeeDistribution = _data[i] == 1;
    ++i;
    final var allowUngatedTrading = _data[i] == 1;
    ++i;
    final var allowFeeDiscounts = _data[i] == 1;
    ++i;
    final var allowReferralRebates = _data[i] == 1;
    return new Permissions(allowSwap,
                           allowAddLiquidity,
                           allowRemoveLiquidity,
                           allowOpenPosition,
                           allowClosePosition,
                           allowCollateralWithdrawal,
                           allowSizeChange,
                           allowLiquidation,
                           allowLpStaking,
                           allowFeeDistribution,
                           allowUngatedTrading,
                           allowFeeDiscounts,
                           allowReferralRebates);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (allowSwap ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowAddLiquidity ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowRemoveLiquidity ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowOpenPosition ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowClosePosition ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowCollateralWithdrawal ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowSizeChange ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowLiquidation ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowLpStaking ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowFeeDistribution ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowUngatedTrading ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowFeeDiscounts ? 1 : 0);
    ++i;
    _data[i] = (byte) (allowReferralRebates ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
