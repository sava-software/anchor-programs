package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ReserveConfig(int status,
                            int assetTier,
                            int hostFixedInterestRateBps,
                            byte[] reserved2,
                            byte[] reserved3,
                            int protocolTakeRatePct,
                            int protocolLiquidationFeePct,
                            int loanToValuePct,
                            int liquidationThresholdPct,
                            int minLiquidationBonusBps,
                            int maxLiquidationBonusBps,
                            int badDebtLiquidationBonusBps,
                            long deleveragingMarginCallPeriodSecs,
                            long deleveragingThresholdDecreaseBpsPerDay,
                            ReserveFees fees,
                            BorrowRateCurve borrowRateCurve,
                            long borrowFactorPct,
                            long depositLimit,
                            long borrowLimit,
                            TokenInfo tokenInfo,
                            WithdrawalCaps depositWithdrawalCap,
                            WithdrawalCaps debtWithdrawalCap,
                            byte[] elevationGroups,
                            int disableUsageAsCollOutsideEmode,
                            int utilizationLimitBlockBorrowingAbovePct,
                            int autodeleverageEnabled,
                            byte[] reserved1,
                            long borrowLimitOutsideElevationGroup,
                            long[] borrowLimitAgainstThisCollateralInElevationGroup,
                            long deleveragingBonusIncreaseBpsPerDay) implements Borsh {

  public static final int BYTES = 920;

  public static ReserveConfig read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var status = _data[i] & 0xFF;
    ++i;
    final var assetTier = _data[i] & 0xFF;
    ++i;
    final var hostFixedInterestRateBps = getInt16LE(_data, i);
    i += 2;
    final var reserved2 = new byte[2];
    i += Borsh.readArray(reserved2, _data, i);
    final var reserved3 = new byte[8];
    i += Borsh.readArray(reserved3, _data, i);
    final var protocolTakeRatePct = _data[i] & 0xFF;
    ++i;
    final var protocolLiquidationFeePct = _data[i] & 0xFF;
    ++i;
    final var loanToValuePct = _data[i] & 0xFF;
    ++i;
    final var liquidationThresholdPct = _data[i] & 0xFF;
    ++i;
    final var minLiquidationBonusBps = getInt16LE(_data, i);
    i += 2;
    final var maxLiquidationBonusBps = getInt16LE(_data, i);
    i += 2;
    final var badDebtLiquidationBonusBps = getInt16LE(_data, i);
    i += 2;
    final var deleveragingMarginCallPeriodSecs = getInt64LE(_data, i);
    i += 8;
    final var deleveragingThresholdDecreaseBpsPerDay = getInt64LE(_data, i);
    i += 8;
    final var fees = ReserveFees.read(_data, i);
    i += Borsh.len(fees);
    final var borrowRateCurve = BorrowRateCurve.read(_data, i);
    i += Borsh.len(borrowRateCurve);
    final var borrowFactorPct = getInt64LE(_data, i);
    i += 8;
    final var depositLimit = getInt64LE(_data, i);
    i += 8;
    final var borrowLimit = getInt64LE(_data, i);
    i += 8;
    final var tokenInfo = TokenInfo.read(_data, i);
    i += Borsh.len(tokenInfo);
    final var depositWithdrawalCap = WithdrawalCaps.read(_data, i);
    i += Borsh.len(depositWithdrawalCap);
    final var debtWithdrawalCap = WithdrawalCaps.read(_data, i);
    i += Borsh.len(debtWithdrawalCap);
    final var elevationGroups = new byte[20];
    i += Borsh.readArray(elevationGroups, _data, i);
    final var disableUsageAsCollOutsideEmode = _data[i] & 0xFF;
    ++i;
    final var utilizationLimitBlockBorrowingAbovePct = _data[i] & 0xFF;
    ++i;
    final var autodeleverageEnabled = _data[i] & 0xFF;
    ++i;
    final var reserved1 = new byte[1];
    i += Borsh.readArray(reserved1, _data, i);
    final var borrowLimitOutsideElevationGroup = getInt64LE(_data, i);
    i += 8;
    final var borrowLimitAgainstThisCollateralInElevationGroup = new long[32];
    i += Borsh.readArray(borrowLimitAgainstThisCollateralInElevationGroup, _data, i);
    final var deleveragingBonusIncreaseBpsPerDay = getInt64LE(_data, i);
    return new ReserveConfig(status,
                             assetTier,
                             hostFixedInterestRateBps,
                             reserved2,
                             reserved3,
                             protocolTakeRatePct,
                             protocolLiquidationFeePct,
                             loanToValuePct,
                             liquidationThresholdPct,
                             minLiquidationBonusBps,
                             maxLiquidationBonusBps,
                             badDebtLiquidationBonusBps,
                             deleveragingMarginCallPeriodSecs,
                             deleveragingThresholdDecreaseBpsPerDay,
                             fees,
                             borrowRateCurve,
                             borrowFactorPct,
                             depositLimit,
                             borrowLimit,
                             tokenInfo,
                             depositWithdrawalCap,
                             debtWithdrawalCap,
                             elevationGroups,
                             disableUsageAsCollOutsideEmode,
                             utilizationLimitBlockBorrowingAbovePct,
                             autodeleverageEnabled,
                             reserved1,
                             borrowLimitOutsideElevationGroup,
                             borrowLimitAgainstThisCollateralInElevationGroup,
                             deleveragingBonusIncreaseBpsPerDay);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) status;
    ++i;
    _data[i] = (byte) assetTier;
    ++i;
    putInt16LE(_data, i, hostFixedInterestRateBps);
    i += 2;
    i += Borsh.writeArray(reserved2, _data, i);
    i += Borsh.writeArray(reserved3, _data, i);
    _data[i] = (byte) protocolTakeRatePct;
    ++i;
    _data[i] = (byte) protocolLiquidationFeePct;
    ++i;
    _data[i] = (byte) loanToValuePct;
    ++i;
    _data[i] = (byte) liquidationThresholdPct;
    ++i;
    putInt16LE(_data, i, minLiquidationBonusBps);
    i += 2;
    putInt16LE(_data, i, maxLiquidationBonusBps);
    i += 2;
    putInt16LE(_data, i, badDebtLiquidationBonusBps);
    i += 2;
    putInt64LE(_data, i, deleveragingMarginCallPeriodSecs);
    i += 8;
    putInt64LE(_data, i, deleveragingThresholdDecreaseBpsPerDay);
    i += 8;
    i += Borsh.write(fees, _data, i);
    i += Borsh.write(borrowRateCurve, _data, i);
    putInt64LE(_data, i, borrowFactorPct);
    i += 8;
    putInt64LE(_data, i, depositLimit);
    i += 8;
    putInt64LE(_data, i, borrowLimit);
    i += 8;
    i += Borsh.write(tokenInfo, _data, i);
    i += Borsh.write(depositWithdrawalCap, _data, i);
    i += Borsh.write(debtWithdrawalCap, _data, i);
    i += Borsh.writeArray(elevationGroups, _data, i);
    _data[i] = (byte) disableUsageAsCollOutsideEmode;
    ++i;
    _data[i] = (byte) utilizationLimitBlockBorrowingAbovePct;
    ++i;
    _data[i] = (byte) autodeleverageEnabled;
    ++i;
    i += Borsh.writeArray(reserved1, _data, i);
    putInt64LE(_data, i, borrowLimitOutsideElevationGroup);
    i += 8;
    i += Borsh.writeArray(borrowLimitAgainstThisCollateralInElevationGroup, _data, i);
    putInt64LE(_data, i, deleveragingBonusIncreaseBpsPerDay);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
