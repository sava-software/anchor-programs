package software.sava.anchor.programs.kamino.lend.anchor.types;

import software.sava.core.borsh.Borsh;

public enum UpdateLendingMarketMode implements Borsh.Enum {

  UpdateOwner,
  UpdateEmergencyMode,
  UpdateLiquidationCloseFactor,
  UpdateLiquidationMaxValue,
  DeprecatedUpdateGlobalUnhealthyBorrow,
  UpdateGlobalAllowedBorrow,
  UpdateRiskCouncil,
  UpdateMinFullLiquidationThreshold,
  UpdateInsolvencyRiskLtv,
  UpdateElevationGroup,
  UpdateReferralFeeBps,
  DeprecatedUpdateMultiplierPoints,
  UpdatePriceRefreshTriggerToMaxAgePct,
  UpdateAutodeleverageEnabled,
  UpdateBorrowingDisabled,
  UpdateMinNetValueObligationPostAction,
  UpdateMinValueLtvSkipPriorityLiqCheck,
  UpdateMinValueBfSkipPriorityLiqCheck,
  UpdatePaddingFields,
  UpdateName,
  UpdateIndividualAutodeleverageMarginCallPeriodSecs,
  UpdateInitialDepositAmount;

  public static UpdateLendingMarketMode read(final byte[] _data, final int offset) {
    return Borsh.read(UpdateLendingMarketMode.values(), _data, offset);
  }
}