package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateConfigMode implements Borsh.Enum {

  UpdateLoanToValuePct,
  UpdateMaxLiquidationBonusBps,
  UpdateLiquidationThresholdPct,
  UpdateProtocolLiquidationFee,
  UpdateProtocolTakeRate,
  UpdateFeesBorrowFee,
  UpdateFeesFlashLoanFee,
  UpdateFeesReferralFeeBps,
  UpdateDepositLimit,
  UpdateBorrowLimit,
  UpdateTokenInfoLowerHeuristic,
  UpdateTokenInfoUpperHeuristic,
  UpdateTokenInfoExpHeuristic,
  UpdateTokenInfoTwapDivergence,
  UpdateTokenInfoScopeTwap,
  UpdateTokenInfoScopeChain,
  UpdateTokenInfoName,
  UpdateTokenInfoPriceMaxAge,
  UpdateTokenInfoTwapMaxAge,
  UpdateScopePriceFeed,
  UpdatePythPrice,
  UpdateSwitchboardFeed,
  UpdateSwitchboardTwapFeed,
  UpdateBorrowRateCurve,
  UpdateEntireReserveConfig,
  UpdateDebtWithdrawalCap,
  UpdateDepositWithdrawalCap,
  UpdateDebtWithdrawalCapCurrentTotal,
  UpdateDepositWithdrawalCapCurrentTotal,
  UpdateBadDebtLiquidationBonusBps,
  UpdateMinLiquidationBonusBps,
  DeleveragingMarginCallPeriod,
  UpdateBorrowFactor,
  UpdateAssetTier,
  UpdateElevationGroup,
  DeleveragingThresholdSlotsPerBps,
  DeprecatedUpdateMultiplierSideBoost,
  DeprecatedUpdateMultiplierTagBoost,
  UpdateReserveStatus,
  UpdateFarmCollateral,
  UpdateFarmDebt,
  UpdateDisableUsageAsCollateralOutsideEmode,
  UpdateBlockBorrowingAboveUtilizationPct,
  UpdateBlockPriceUsage,
  UpdateBorrowLimitOutsideElevationGroup,
  UpdateBorrowLimitsInElevationGroupAgainstThisReserve,
  UpdateHostFixedInterestRateBps;

  public static UpdateConfigMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateConfigMode.values(), _data, offset);
  }
}