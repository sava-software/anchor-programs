package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateConfigMode implements Borsh.Enum {

  UpdateLoanToValuePct,
  UpdateMaxLiquidationBonusBps,
  UpdateLiquidationThresholdPct,
  UpdateProtocolLiquidationFee,
  UpdateProtocolTakeRate,
  UpdateFeesOriginationFee,
  UpdateFeesFlashLoanFee,
  DeprecatedUpdateFeesReferralFeeBps,
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
  DeprecatedUpdateDebtWithdrawalCapCurrentTotal,
  DeprecatedUpdateDepositWithdrawalCapCurrentTotal,
  UpdateBadDebtLiquidationBonusBps,
  UpdateMinLiquidationBonusBps,
  UpdateDeleveragingMarginCallPeriod,
  UpdateBorrowFactor,
  UpdateAssetTier,
  UpdateElevationGroup,
  UpdateDeleveragingThresholdDecreaseBpsPerDay,
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
  UpdateHostFixedInterestRateBps,
  UpdateAutodeleverageEnabled,
  UpdateDeleveragingBonusIncreaseBpsPerDay,
  UpdateProtocolOrderExecutionFee,
  UpdateProposerAuthorityLock,
  UpdateMinDeleveragingBonusBps,
  UpdateBlockCTokenUsage;

  public static UpdateConfigMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateConfigMode.values(), _data, offset);
  }
}