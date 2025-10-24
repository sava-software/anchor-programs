package software.sava.anchor.programs.kamino.lend.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface KaminoLendingError extends ProgramError permits
    KaminoLendingError.InvalidMarketAuthority,
    KaminoLendingError.InvalidMarketOwner,
    KaminoLendingError.InvalidAccountOwner,
    KaminoLendingError.InvalidAmount,
    KaminoLendingError.InvalidConfig,
    KaminoLendingError.InvalidSigner,
    KaminoLendingError.InvalidAccountInput,
    KaminoLendingError.MathOverflow,
    KaminoLendingError.InsufficientLiquidity,
    KaminoLendingError.ReserveStale,
    KaminoLendingError.WithdrawTooSmall,
    KaminoLendingError.WithdrawTooLarge,
    KaminoLendingError.BorrowTooSmall,
    KaminoLendingError.BorrowTooLarge,
    KaminoLendingError.RepayTooSmall,
    KaminoLendingError.LiquidationTooSmall,
    KaminoLendingError.ObligationHealthy,
    KaminoLendingError.ObligationStale,
    KaminoLendingError.ObligationReserveLimit,
    KaminoLendingError.InvalidObligationOwner,
    KaminoLendingError.ObligationDepositsEmpty,
    KaminoLendingError.ObligationBorrowsEmpty,
    KaminoLendingError.ObligationDepositsZero,
    KaminoLendingError.ObligationBorrowsZero,
    KaminoLendingError.InvalidObligationCollateral,
    KaminoLendingError.InvalidObligationLiquidity,
    KaminoLendingError.ObligationCollateralEmpty,
    KaminoLendingError.ObligationLiquidityEmpty,
    KaminoLendingError.NegativeInterestRate,
    KaminoLendingError.InvalidOracleConfig,
    KaminoLendingError.InsufficientProtocolFeesToRedeem,
    KaminoLendingError.FlashBorrowCpi,
    KaminoLendingError.NoFlashRepayFound,
    KaminoLendingError.InvalidFlashRepay,
    KaminoLendingError.FlashRepayCpi,
    KaminoLendingError.MultipleFlashBorrows,
    KaminoLendingError.FlashLoansDisabled,
    KaminoLendingError.SwitchboardV2Error,
    KaminoLendingError.CouldNotDeserializeScope,
    KaminoLendingError.PriceTooOld,
    KaminoLendingError.PriceTooDivergentFromTwap,
    KaminoLendingError.InvalidTwapPrice,
    KaminoLendingError.GlobalEmergencyMode,
    KaminoLendingError.InvalidFlag,
    KaminoLendingError.PriceNotValid,
    KaminoLendingError.PriceIsBiggerThanHeuristic,
    KaminoLendingError.PriceIsLowerThanHeuristic,
    KaminoLendingError.PriceIsZero,
    KaminoLendingError.PriceConfidenceTooWide,
    KaminoLendingError.IntegerOverflow,
    KaminoLendingError.NoFarmForReserve,
    KaminoLendingError.IncorrectInstructionInPosition,
    KaminoLendingError.NoPriceFound,
    KaminoLendingError.InvalidTwapConfig,
    KaminoLendingError.InvalidPythPriceAccount,
    KaminoLendingError.InvalidSwitchboardAccount,
    KaminoLendingError.InvalidScopePriceAccount,
    KaminoLendingError.ObligationCollateralLtvZero,
    KaminoLendingError.InvalidObligationSeedsValue,
    KaminoLendingError.DeprecatedInvalidObligationId,
    KaminoLendingError.InvalidBorrowRateCurvePoint,
    KaminoLendingError.InvalidUtilizationRate,
    KaminoLendingError.CannotSocializeObligationWithCollateral,
    KaminoLendingError.ObligationEmpty,
    KaminoLendingError.WithdrawalCapReached,
    KaminoLendingError.LastTimestampGreaterThanCurrent,
    KaminoLendingError.LiquidationRewardTooSmall,
    KaminoLendingError.IsolatedAssetTierViolation,
    KaminoLendingError.InconsistentElevationGroup,
    KaminoLendingError.InvalidElevationGroup,
    KaminoLendingError.InvalidElevationGroupConfig,
    KaminoLendingError.UnhealthyElevationGroupLtv,
    KaminoLendingError.ElevationGroupNewLoansDisabled,
    KaminoLendingError.ReserveDeprecated,
    KaminoLendingError.ReferrerAccountNotInitialized,
    KaminoLendingError.ReferrerAccountMintMissmatch,
    KaminoLendingError.ReferrerAccountWrongAddress,
    KaminoLendingError.ReferrerAccountReferrerMissmatch,
    KaminoLendingError.ReferrerAccountMissing,
    KaminoLendingError.InsufficientReferralFeesToRedeem,
    KaminoLendingError.CpiDisabled,
    KaminoLendingError.ShortUrlNotAsciiAlphanumeric,
    KaminoLendingError.ReserveObsolete,
    KaminoLendingError.ElevationGroupAlreadyActivated,
    KaminoLendingError.ObligationInObsoleteReserve,
    KaminoLendingError.ReferrerStateOwnerMismatch,
    KaminoLendingError.UserMetadataOwnerAlreadySet,
    KaminoLendingError.CollateralNonLiquidatable,
    KaminoLendingError.BorrowingDisabled,
    KaminoLendingError.BorrowLimitExceeded,
    KaminoLendingError.DepositLimitExceeded,
    KaminoLendingError.BorrowingDisabledOutsideElevationGroup,
    KaminoLendingError.NetValueRemainingTooSmall,
    KaminoLendingError.WorseLtvBlocked,
    KaminoLendingError.LiabilitiesBiggerThanAssets,
    KaminoLendingError.ReserveTokenBalanceMismatch,
    KaminoLendingError.ReserveVaultBalanceMismatch,
    KaminoLendingError.ReserveAccountingMismatch,
    KaminoLendingError.BorrowingAboveUtilizationRateDisabled,
    KaminoLendingError.LiquidationBorrowFactorPriority,
    KaminoLendingError.LiquidationLowestLiquidationLtvPriority,
    KaminoLendingError.ElevationGroupBorrowLimitExceeded,
    KaminoLendingError.ElevationGroupWithoutDebtReserve,
    KaminoLendingError.ElevationGroupMaxCollateralReserveZero,
    KaminoLendingError.ElevationGroupHasAnotherDebtReserve,
    KaminoLendingError.ElevationGroupDebtReserveAsCollateral,
    KaminoLendingError.ObligationCollateralExceedsElevationGroupLimit,
    KaminoLendingError.ObligationElevationGroupMultipleDebtReserve,
    KaminoLendingError.UnsupportedTokenExtension,
    KaminoLendingError.InvalidTokenAccount,
    KaminoLendingError.DepositDisabledOutsideElevationGroup,
    KaminoLendingError.CannotCalculateReferralAmountDueToSlotsMismatch,
    KaminoLendingError.ObligationOwnersMustMatch,
    KaminoLendingError.ObligationsMustMatch,
    KaminoLendingError.LendingMarketsMustMatch,
    KaminoLendingError.ObligationCurrentlyMarkedForDeleveraging,
    KaminoLendingError.MaximumWithdrawValueZero,
    KaminoLendingError.ZeroMaxLtvAssetsInDeposits,
    KaminoLendingError.LowestLtvAssetsPriority,
    KaminoLendingError.WorseLtvThanUnhealthyLtv,
    KaminoLendingError.FarmAccountsMissing,
    KaminoLendingError.RepayTooSmallForFullLiquidation,
    KaminoLendingError.InsufficientRepayAmount,
    KaminoLendingError.OrderIndexOutOfBounds,
    KaminoLendingError.InvalidOrderConfiguration,
    KaminoLendingError.OrderConfigurationNotSupportedByObligation,
    KaminoLendingError.OperationNotPermittedWithCurrentObligationOrders,
    KaminoLendingError.OperationNotPermittedMarketImmutable,
    KaminoLendingError.OrderCreationDisabled,
    KaminoLendingError.NoUpgradeAuthority {

  static KaminoLendingError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidMarketAuthority.INSTANCE;
      case 6001 -> InvalidMarketOwner.INSTANCE;
      case 6002 -> InvalidAccountOwner.INSTANCE;
      case 6003 -> InvalidAmount.INSTANCE;
      case 6004 -> InvalidConfig.INSTANCE;
      case 6005 -> InvalidSigner.INSTANCE;
      case 6006 -> InvalidAccountInput.INSTANCE;
      case 6007 -> MathOverflow.INSTANCE;
      case 6008 -> InsufficientLiquidity.INSTANCE;
      case 6009 -> ReserveStale.INSTANCE;
      case 6010 -> WithdrawTooSmall.INSTANCE;
      case 6011 -> WithdrawTooLarge.INSTANCE;
      case 6012 -> BorrowTooSmall.INSTANCE;
      case 6013 -> BorrowTooLarge.INSTANCE;
      case 6014 -> RepayTooSmall.INSTANCE;
      case 6015 -> LiquidationTooSmall.INSTANCE;
      case 6016 -> ObligationHealthy.INSTANCE;
      case 6017 -> ObligationStale.INSTANCE;
      case 6018 -> ObligationReserveLimit.INSTANCE;
      case 6019 -> InvalidObligationOwner.INSTANCE;
      case 6020 -> ObligationDepositsEmpty.INSTANCE;
      case 6021 -> ObligationBorrowsEmpty.INSTANCE;
      case 6022 -> ObligationDepositsZero.INSTANCE;
      case 6023 -> ObligationBorrowsZero.INSTANCE;
      case 6024 -> InvalidObligationCollateral.INSTANCE;
      case 6025 -> InvalidObligationLiquidity.INSTANCE;
      case 6026 -> ObligationCollateralEmpty.INSTANCE;
      case 6027 -> ObligationLiquidityEmpty.INSTANCE;
      case 6028 -> NegativeInterestRate.INSTANCE;
      case 6029 -> InvalidOracleConfig.INSTANCE;
      case 6030 -> InsufficientProtocolFeesToRedeem.INSTANCE;
      case 6031 -> FlashBorrowCpi.INSTANCE;
      case 6032 -> NoFlashRepayFound.INSTANCE;
      case 6033 -> InvalidFlashRepay.INSTANCE;
      case 6034 -> FlashRepayCpi.INSTANCE;
      case 6035 -> MultipleFlashBorrows.INSTANCE;
      case 6036 -> FlashLoansDisabled.INSTANCE;
      case 6037 -> SwitchboardV2Error.INSTANCE;
      case 6038 -> CouldNotDeserializeScope.INSTANCE;
      case 6039 -> PriceTooOld.INSTANCE;
      case 6040 -> PriceTooDivergentFromTwap.INSTANCE;
      case 6041 -> InvalidTwapPrice.INSTANCE;
      case 6042 -> GlobalEmergencyMode.INSTANCE;
      case 6043 -> InvalidFlag.INSTANCE;
      case 6044 -> PriceNotValid.INSTANCE;
      case 6045 -> PriceIsBiggerThanHeuristic.INSTANCE;
      case 6046 -> PriceIsLowerThanHeuristic.INSTANCE;
      case 6047 -> PriceIsZero.INSTANCE;
      case 6048 -> PriceConfidenceTooWide.INSTANCE;
      case 6049 -> IntegerOverflow.INSTANCE;
      case 6050 -> NoFarmForReserve.INSTANCE;
      case 6051 -> IncorrectInstructionInPosition.INSTANCE;
      case 6052 -> NoPriceFound.INSTANCE;
      case 6053 -> InvalidTwapConfig.INSTANCE;
      case 6054 -> InvalidPythPriceAccount.INSTANCE;
      case 6055 -> InvalidSwitchboardAccount.INSTANCE;
      case 6056 -> InvalidScopePriceAccount.INSTANCE;
      case 6057 -> ObligationCollateralLtvZero.INSTANCE;
      case 6058 -> InvalidObligationSeedsValue.INSTANCE;
      case 6059 -> DeprecatedInvalidObligationId.INSTANCE;
      case 6060 -> InvalidBorrowRateCurvePoint.INSTANCE;
      case 6061 -> InvalidUtilizationRate.INSTANCE;
      case 6062 -> CannotSocializeObligationWithCollateral.INSTANCE;
      case 6063 -> ObligationEmpty.INSTANCE;
      case 6064 -> WithdrawalCapReached.INSTANCE;
      case 6065 -> LastTimestampGreaterThanCurrent.INSTANCE;
      case 6066 -> LiquidationRewardTooSmall.INSTANCE;
      case 6067 -> IsolatedAssetTierViolation.INSTANCE;
      case 6068 -> InconsistentElevationGroup.INSTANCE;
      case 6069 -> InvalidElevationGroup.INSTANCE;
      case 6070 -> InvalidElevationGroupConfig.INSTANCE;
      case 6071 -> UnhealthyElevationGroupLtv.INSTANCE;
      case 6072 -> ElevationGroupNewLoansDisabled.INSTANCE;
      case 6073 -> ReserveDeprecated.INSTANCE;
      case 6074 -> ReferrerAccountNotInitialized.INSTANCE;
      case 6075 -> ReferrerAccountMintMissmatch.INSTANCE;
      case 6076 -> ReferrerAccountWrongAddress.INSTANCE;
      case 6077 -> ReferrerAccountReferrerMissmatch.INSTANCE;
      case 6078 -> ReferrerAccountMissing.INSTANCE;
      case 6079 -> InsufficientReferralFeesToRedeem.INSTANCE;
      case 6080 -> CpiDisabled.INSTANCE;
      case 6081 -> ShortUrlNotAsciiAlphanumeric.INSTANCE;
      case 6082 -> ReserveObsolete.INSTANCE;
      case 6083 -> ElevationGroupAlreadyActivated.INSTANCE;
      case 6084 -> ObligationInObsoleteReserve.INSTANCE;
      case 6085 -> ReferrerStateOwnerMismatch.INSTANCE;
      case 6086 -> UserMetadataOwnerAlreadySet.INSTANCE;
      case 6087 -> CollateralNonLiquidatable.INSTANCE;
      case 6088 -> BorrowingDisabled.INSTANCE;
      case 6089 -> BorrowLimitExceeded.INSTANCE;
      case 6090 -> DepositLimitExceeded.INSTANCE;
      case 6091 -> BorrowingDisabledOutsideElevationGroup.INSTANCE;
      case 6092 -> NetValueRemainingTooSmall.INSTANCE;
      case 6093 -> WorseLtvBlocked.INSTANCE;
      case 6094 -> LiabilitiesBiggerThanAssets.INSTANCE;
      case 6095 -> ReserveTokenBalanceMismatch.INSTANCE;
      case 6096 -> ReserveVaultBalanceMismatch.INSTANCE;
      case 6097 -> ReserveAccountingMismatch.INSTANCE;
      case 6098 -> BorrowingAboveUtilizationRateDisabled.INSTANCE;
      case 6099 -> LiquidationBorrowFactorPriority.INSTANCE;
      case 6100 -> LiquidationLowestLiquidationLtvPriority.INSTANCE;
      case 6101 -> ElevationGroupBorrowLimitExceeded.INSTANCE;
      case 6102 -> ElevationGroupWithoutDebtReserve.INSTANCE;
      case 6103 -> ElevationGroupMaxCollateralReserveZero.INSTANCE;
      case 6104 -> ElevationGroupHasAnotherDebtReserve.INSTANCE;
      case 6105 -> ElevationGroupDebtReserveAsCollateral.INSTANCE;
      case 6106 -> ObligationCollateralExceedsElevationGroupLimit.INSTANCE;
      case 6107 -> ObligationElevationGroupMultipleDebtReserve.INSTANCE;
      case 6108 -> UnsupportedTokenExtension.INSTANCE;
      case 6109 -> InvalidTokenAccount.INSTANCE;
      case 6110 -> DepositDisabledOutsideElevationGroup.INSTANCE;
      case 6111 -> CannotCalculateReferralAmountDueToSlotsMismatch.INSTANCE;
      case 6112 -> ObligationOwnersMustMatch.INSTANCE;
      case 6113 -> ObligationsMustMatch.INSTANCE;
      case 6114 -> LendingMarketsMustMatch.INSTANCE;
      case 6115 -> ObligationCurrentlyMarkedForDeleveraging.INSTANCE;
      case 6116 -> MaximumWithdrawValueZero.INSTANCE;
      case 6117 -> ZeroMaxLtvAssetsInDeposits.INSTANCE;
      case 6118 -> LowestLtvAssetsPriority.INSTANCE;
      case 6119 -> WorseLtvThanUnhealthyLtv.INSTANCE;
      case 6120 -> FarmAccountsMissing.INSTANCE;
      case 6121 -> RepayTooSmallForFullLiquidation.INSTANCE;
      case 6122 -> InsufficientRepayAmount.INSTANCE;
      case 6123 -> OrderIndexOutOfBounds.INSTANCE;
      case 6124 -> InvalidOrderConfiguration.INSTANCE;
      case 6125 -> OrderConfigurationNotSupportedByObligation.INSTANCE;
      case 6126 -> OperationNotPermittedWithCurrentObligationOrders.INSTANCE;
      case 6127 -> OperationNotPermittedMarketImmutable.INSTANCE;
      case 6128 -> OrderCreationDisabled.INSTANCE;
      case 6129 -> NoUpgradeAuthority.INSTANCE;
      default -> throw new IllegalStateException("Unexpected KaminoLending error code: " + errorCode);
    };
  }

  record InvalidMarketAuthority(int code, String msg) implements KaminoLendingError {

    public static final InvalidMarketAuthority INSTANCE = new InvalidMarketAuthority(
        6000, "Market authority is invalid"
    );
  }

  record InvalidMarketOwner(int code, String msg) implements KaminoLendingError {

    public static final InvalidMarketOwner INSTANCE = new InvalidMarketOwner(
        6001, "Market owner is invalid"
    );
  }

  record InvalidAccountOwner(int code, String msg) implements KaminoLendingError {

    public static final InvalidAccountOwner INSTANCE = new InvalidAccountOwner(
        6002, "Input account owner is not the program address"
    );
  }

  record InvalidAmount(int code, String msg) implements KaminoLendingError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        6003, "Input amount is invalid"
    );
  }

  record InvalidConfig(int code, String msg) implements KaminoLendingError {

    public static final InvalidConfig INSTANCE = new InvalidConfig(
        6004, "Input config value is invalid"
    );
  }

  record InvalidSigner(int code, String msg) implements KaminoLendingError {

    public static final InvalidSigner INSTANCE = new InvalidSigner(
        6005, "Signer is not allowed to perform this action"
    );
  }

  record InvalidAccountInput(int code, String msg) implements KaminoLendingError {

    public static final InvalidAccountInput INSTANCE = new InvalidAccountInput(
        6006, "Invalid account input"
    );
  }

  record MathOverflow(int code, String msg) implements KaminoLendingError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6007, "Math operation overflow"
    );
  }

  record InsufficientLiquidity(int code, String msg) implements KaminoLendingError {

    public static final InsufficientLiquidity INSTANCE = new InsufficientLiquidity(
        6008, "Insufficient liquidity available"
    );
  }

  record ReserveStale(int code, String msg) implements KaminoLendingError {

    public static final ReserveStale INSTANCE = new ReserveStale(
        6009, "Reserve state needs to be refreshed"
    );
  }

  record WithdrawTooSmall(int code, String msg) implements KaminoLendingError {

    public static final WithdrawTooSmall INSTANCE = new WithdrawTooSmall(
        6010, "Withdraw amount too small"
    );
  }

  record WithdrawTooLarge(int code, String msg) implements KaminoLendingError {

    public static final WithdrawTooLarge INSTANCE = new WithdrawTooLarge(
        6011, "Withdraw amount too large"
    );
  }

  record BorrowTooSmall(int code, String msg) implements KaminoLendingError {

    public static final BorrowTooSmall INSTANCE = new BorrowTooSmall(
        6012, "Borrow amount too small to receive liquidity after fees"
    );
  }

  record BorrowTooLarge(int code, String msg) implements KaminoLendingError {

    public static final BorrowTooLarge INSTANCE = new BorrowTooLarge(
        6013, "Borrow amount too large for deposited collateral"
    );
  }

  record RepayTooSmall(int code, String msg) implements KaminoLendingError {

    public static final RepayTooSmall INSTANCE = new RepayTooSmall(
        6014, "Repay amount too small to transfer liquidity"
    );
  }

  record LiquidationTooSmall(int code, String msg) implements KaminoLendingError {

    public static final LiquidationTooSmall INSTANCE = new LiquidationTooSmall(
        6015, "Liquidation amount too small to receive collateral"
    );
  }

  record ObligationHealthy(int code, String msg) implements KaminoLendingError {

    public static final ObligationHealthy INSTANCE = new ObligationHealthy(
        6016, "Cannot liquidate healthy obligations"
    );
  }

  record ObligationStale(int code, String msg) implements KaminoLendingError {

    public static final ObligationStale INSTANCE = new ObligationStale(
        6017, "Obligation state needs to be refreshed"
    );
  }

  record ObligationReserveLimit(int code, String msg) implements KaminoLendingError {

    public static final ObligationReserveLimit INSTANCE = new ObligationReserveLimit(
        6018, "Obligation reserve limit exceeded"
    );
  }

  record InvalidObligationOwner(int code, String msg) implements KaminoLendingError {

    public static final InvalidObligationOwner INSTANCE = new InvalidObligationOwner(
        6019, "Obligation owner is invalid"
    );
  }

  record ObligationDepositsEmpty(int code, String msg) implements KaminoLendingError {

    public static final ObligationDepositsEmpty INSTANCE = new ObligationDepositsEmpty(
        6020, "Obligation deposits are empty"
    );
  }

  record ObligationBorrowsEmpty(int code, String msg) implements KaminoLendingError {

    public static final ObligationBorrowsEmpty INSTANCE = new ObligationBorrowsEmpty(
        6021, "Obligation borrows are empty"
    );
  }

  record ObligationDepositsZero(int code, String msg) implements KaminoLendingError {

    public static final ObligationDepositsZero INSTANCE = new ObligationDepositsZero(
        6022, "Obligation deposits have zero value"
    );
  }

  record ObligationBorrowsZero(int code, String msg) implements KaminoLendingError {

    public static final ObligationBorrowsZero INSTANCE = new ObligationBorrowsZero(
        6023, "Obligation borrows have zero value"
    );
  }

  record InvalidObligationCollateral(int code, String msg) implements KaminoLendingError {

    public static final InvalidObligationCollateral INSTANCE = new InvalidObligationCollateral(
        6024, "Invalid obligation collateral"
    );
  }

  record InvalidObligationLiquidity(int code, String msg) implements KaminoLendingError {

    public static final InvalidObligationLiquidity INSTANCE = new InvalidObligationLiquidity(
        6025, "Invalid obligation liquidity"
    );
  }

  record ObligationCollateralEmpty(int code, String msg) implements KaminoLendingError {

    public static final ObligationCollateralEmpty INSTANCE = new ObligationCollateralEmpty(
        6026, "Obligation collateral is empty"
    );
  }

  record ObligationLiquidityEmpty(int code, String msg) implements KaminoLendingError {

    public static final ObligationLiquidityEmpty INSTANCE = new ObligationLiquidityEmpty(
        6027, "Obligation liquidity is empty"
    );
  }

  record NegativeInterestRate(int code, String msg) implements KaminoLendingError {

    public static final NegativeInterestRate INSTANCE = new NegativeInterestRate(
        6028, "Interest rate is negative"
    );
  }

  record InvalidOracleConfig(int code, String msg) implements KaminoLendingError {

    public static final InvalidOracleConfig INSTANCE = new InvalidOracleConfig(
        6029, "Input oracle config is invalid"
    );
  }

  record InsufficientProtocolFeesToRedeem(int code, String msg) implements KaminoLendingError {

    public static final InsufficientProtocolFeesToRedeem INSTANCE = new InsufficientProtocolFeesToRedeem(
        6030, "Insufficient protocol fees to claim or no liquidity available"
    );
  }

  record FlashBorrowCpi(int code, String msg) implements KaminoLendingError {

    public static final FlashBorrowCpi INSTANCE = new FlashBorrowCpi(
        6031, "No cpi flash borrows allowed"
    );
  }

  record NoFlashRepayFound(int code, String msg) implements KaminoLendingError {

    public static final NoFlashRepayFound INSTANCE = new NoFlashRepayFound(
        6032, "No corresponding repay found for flash borrow"
    );
  }

  record InvalidFlashRepay(int code, String msg) implements KaminoLendingError {

    public static final InvalidFlashRepay INSTANCE = new InvalidFlashRepay(
        6033, "Invalid repay found"
    );
  }

  record FlashRepayCpi(int code, String msg) implements KaminoLendingError {

    public static final FlashRepayCpi INSTANCE = new FlashRepayCpi(
        6034, "No cpi flash repays allowed"
    );
  }

  record MultipleFlashBorrows(int code, String msg) implements KaminoLendingError {

    public static final MultipleFlashBorrows INSTANCE = new MultipleFlashBorrows(
        6035, "Multiple flash borrows not allowed in the same transaction"
    );
  }

  record FlashLoansDisabled(int code, String msg) implements KaminoLendingError {

    public static final FlashLoansDisabled INSTANCE = new FlashLoansDisabled(
        6036, "Flash loans are disabled for this reserve"
    );
  }

  record SwitchboardV2Error(int code, String msg) implements KaminoLendingError {

    public static final SwitchboardV2Error INSTANCE = new SwitchboardV2Error(
        6037, "Switchboard error"
    );
  }

  record CouldNotDeserializeScope(int code, String msg) implements KaminoLendingError {

    public static final CouldNotDeserializeScope INSTANCE = new CouldNotDeserializeScope(
        6038, "Cannot deserialize the scope price account"
    );
  }

  record PriceTooOld(int code, String msg) implements KaminoLendingError {

    public static final PriceTooOld INSTANCE = new PriceTooOld(
        6039, "Price too old"
    );
  }

  record PriceTooDivergentFromTwap(int code, String msg) implements KaminoLendingError {

    public static final PriceTooDivergentFromTwap INSTANCE = new PriceTooDivergentFromTwap(
        6040, "Price too divergent from twap"
    );
  }

  record InvalidTwapPrice(int code, String msg) implements KaminoLendingError {

    public static final InvalidTwapPrice INSTANCE = new InvalidTwapPrice(
        6041, "Invalid twap price"
    );
  }

  record GlobalEmergencyMode(int code, String msg) implements KaminoLendingError {

    public static final GlobalEmergencyMode INSTANCE = new GlobalEmergencyMode(
        6042, "Emergency mode is enabled"
    );
  }

  record InvalidFlag(int code, String msg) implements KaminoLendingError {

    public static final InvalidFlag INSTANCE = new InvalidFlag(
        6043, "Invalid lending market config"
    );
  }

  record PriceNotValid(int code, String msg) implements KaminoLendingError {

    public static final PriceNotValid INSTANCE = new PriceNotValid(
        6044, "Price is not valid"
    );
  }

  record PriceIsBiggerThanHeuristic(int code, String msg) implements KaminoLendingError {

    public static final PriceIsBiggerThanHeuristic INSTANCE = new PriceIsBiggerThanHeuristic(
        6045, "Price is bigger than allowed by heuristic"
    );
  }

  record PriceIsLowerThanHeuristic(int code, String msg) implements KaminoLendingError {

    public static final PriceIsLowerThanHeuristic INSTANCE = new PriceIsLowerThanHeuristic(
        6046, "Price lower than allowed by heuristic"
    );
  }

  record PriceIsZero(int code, String msg) implements KaminoLendingError {

    public static final PriceIsZero INSTANCE = new PriceIsZero(
        6047, "Price is zero"
    );
  }

  record PriceConfidenceTooWide(int code, String msg) implements KaminoLendingError {

    public static final PriceConfidenceTooWide INSTANCE = new PriceConfidenceTooWide(
        6048, "Price confidence too wide"
    );
  }

  record IntegerOverflow(int code, String msg) implements KaminoLendingError {

    public static final IntegerOverflow INSTANCE = new IntegerOverflow(
        6049, "Conversion between integers failed"
    );
  }

  record NoFarmForReserve(int code, String msg) implements KaminoLendingError {

    public static final NoFarmForReserve INSTANCE = new NoFarmForReserve(
        6050, "This reserve does not have a farm"
    );
  }

  record IncorrectInstructionInPosition(int code, String msg) implements KaminoLendingError {

    public static final IncorrectInstructionInPosition INSTANCE = new IncorrectInstructionInPosition(
        6051, "Wrong instruction at expected position"
    );
  }

  record NoPriceFound(int code, String msg) implements KaminoLendingError {

    public static final NoPriceFound INSTANCE = new NoPriceFound(
        6052, "No price found"
    );
  }

  record InvalidTwapConfig(int code, String msg) implements KaminoLendingError {

    public static final InvalidTwapConfig INSTANCE = new InvalidTwapConfig(
        6053, "Invalid Twap configuration: Twap is enabled but one of the enabled price doesn't have a twap"
    );
  }

  record InvalidPythPriceAccount(int code, String msg) implements KaminoLendingError {

    public static final InvalidPythPriceAccount INSTANCE = new InvalidPythPriceAccount(
        6054, "Pyth price account does not match configuration"
    );
  }

  record InvalidSwitchboardAccount(int code, String msg) implements KaminoLendingError {

    public static final InvalidSwitchboardAccount INSTANCE = new InvalidSwitchboardAccount(
        6055, "Switchboard account(s) do not match configuration"
    );
  }

  record InvalidScopePriceAccount(int code, String msg) implements KaminoLendingError {

    public static final InvalidScopePriceAccount INSTANCE = new InvalidScopePriceAccount(
        6056, "Scope price account does not match configuration"
    );
  }

  record ObligationCollateralLtvZero(int code, String msg) implements KaminoLendingError {

    public static final ObligationCollateralLtvZero INSTANCE = new ObligationCollateralLtvZero(
        6057, "The obligation has one collateral with an LTV set to 0. Withdraw it before withdrawing other collaterals"
    );
  }

  record InvalidObligationSeedsValue(int code, String msg) implements KaminoLendingError {

    public static final InvalidObligationSeedsValue INSTANCE = new InvalidObligationSeedsValue(
        6058, "Seeds must be default pubkeys for tag 0, and mint addresses for tag 1 or 2"
    );
  }

  record DeprecatedInvalidObligationId(int code, String msg) implements KaminoLendingError {

    public static final DeprecatedInvalidObligationId INSTANCE = new DeprecatedInvalidObligationId(
        6059, "[DEPRECATED] Obligation id must be 0"
    );
  }

  record InvalidBorrowRateCurvePoint(int code, String msg) implements KaminoLendingError {

    public static final InvalidBorrowRateCurvePoint INSTANCE = new InvalidBorrowRateCurvePoint(
        6060, "Invalid borrow rate curve point"
    );
  }

  record InvalidUtilizationRate(int code, String msg) implements KaminoLendingError {

    public static final InvalidUtilizationRate INSTANCE = new InvalidUtilizationRate(
        6061, "Invalid utilization rate"
    );
  }

  record CannotSocializeObligationWithCollateral(int code, String msg) implements KaminoLendingError {

    public static final CannotSocializeObligationWithCollateral INSTANCE = new CannotSocializeObligationWithCollateral(
        6062, "Obligation hasn't been fully liquidated and debt cannot be socialized."
    );
  }

  record ObligationEmpty(int code, String msg) implements KaminoLendingError {

    public static final ObligationEmpty INSTANCE = new ObligationEmpty(
        6063, "Obligation has no borrows or deposits."
    );
  }

  record WithdrawalCapReached(int code, String msg) implements KaminoLendingError {

    public static final WithdrawalCapReached INSTANCE = new WithdrawalCapReached(
        6064, "Withdrawal cap is reached"
    );
  }

  record LastTimestampGreaterThanCurrent(int code, String msg) implements KaminoLendingError {

    public static final LastTimestampGreaterThanCurrent INSTANCE = new LastTimestampGreaterThanCurrent(
        6065, "The last interval start timestamp is greater than the current timestamp"
    );
  }

  record LiquidationRewardTooSmall(int code, String msg) implements KaminoLendingError {

    public static final LiquidationRewardTooSmall INSTANCE = new LiquidationRewardTooSmall(
        6066, "The reward amount is less than the minimum acceptable received liquidity"
    );
  }

  record IsolatedAssetTierViolation(int code, String msg) implements KaminoLendingError {

    public static final IsolatedAssetTierViolation INSTANCE = new IsolatedAssetTierViolation(
        6067, "Isolated Asset Tier Violation"
    );
  }

  record InconsistentElevationGroup(int code, String msg) implements KaminoLendingError {

    public static final InconsistentElevationGroup INSTANCE = new InconsistentElevationGroup(
        6068, "The obligation's elevation group and the reserve's are not the same"
    );
  }

  record InvalidElevationGroup(int code, String msg) implements KaminoLendingError {

    public static final InvalidElevationGroup INSTANCE = new InvalidElevationGroup(
        6069, "The elevation group chosen for the reserve does not exist in the lending market"
    );
  }

  record InvalidElevationGroupConfig(int code, String msg) implements KaminoLendingError {

    public static final InvalidElevationGroupConfig INSTANCE = new InvalidElevationGroupConfig(
        6070, "The elevation group updated has wrong parameters set"
    );
  }

  record UnhealthyElevationGroupLtv(int code, String msg) implements KaminoLendingError {

    public static final UnhealthyElevationGroupLtv INSTANCE = new UnhealthyElevationGroupLtv(
        6071, "The current obligation must have most or all its debt repaid before changing the elevation group"
    );
  }

  record ElevationGroupNewLoansDisabled(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupNewLoansDisabled INSTANCE = new ElevationGroupNewLoansDisabled(
        6072, "Elevation group does not accept any new loans or any new borrows/withdrawals"
    );
  }

  record ReserveDeprecated(int code, String msg) implements KaminoLendingError {

    public static final ReserveDeprecated INSTANCE = new ReserveDeprecated(
        6073, "Reserve was deprecated, no longer usable"
    );
  }

  record ReferrerAccountNotInitialized(int code, String msg) implements KaminoLendingError {

    public static final ReferrerAccountNotInitialized INSTANCE = new ReferrerAccountNotInitialized(
        6074, "Referrer account not initialized"
    );
  }

  record ReferrerAccountMintMissmatch(int code, String msg) implements KaminoLendingError {

    public static final ReferrerAccountMintMissmatch INSTANCE = new ReferrerAccountMintMissmatch(
        6075, "Referrer account mint does not match the operation reserve mint"
    );
  }

  record ReferrerAccountWrongAddress(int code, String msg) implements KaminoLendingError {

    public static final ReferrerAccountWrongAddress INSTANCE = new ReferrerAccountWrongAddress(
        6076, "Referrer account address is not a valid program address"
    );
  }

  record ReferrerAccountReferrerMissmatch(int code, String msg) implements KaminoLendingError {

    public static final ReferrerAccountReferrerMissmatch INSTANCE = new ReferrerAccountReferrerMissmatch(
        6077, "Referrer account referrer does not match the owner referrer"
    );
  }

  record ReferrerAccountMissing(int code, String msg) implements KaminoLendingError {

    public static final ReferrerAccountMissing INSTANCE = new ReferrerAccountMissing(
        6078, "Referrer account missing for obligation with referrer"
    );
  }

  record InsufficientReferralFeesToRedeem(int code, String msg) implements KaminoLendingError {

    public static final InsufficientReferralFeesToRedeem INSTANCE = new InsufficientReferralFeesToRedeem(
        6079, "Insufficient referral fees to claim or no liquidity available"
    );
  }

  record CpiDisabled(int code, String msg) implements KaminoLendingError {

    public static final CpiDisabled INSTANCE = new CpiDisabled(
        6080, "CPI disabled for this instruction"
    );
  }

  record ShortUrlNotAsciiAlphanumeric(int code, String msg) implements KaminoLendingError {

    public static final ShortUrlNotAsciiAlphanumeric INSTANCE = new ShortUrlNotAsciiAlphanumeric(
        6081, "Referrer short_url is not ascii alphanumeric"
    );
  }

  record ReserveObsolete(int code, String msg) implements KaminoLendingError {

    public static final ReserveObsolete INSTANCE = new ReserveObsolete(
        6082, "Reserve is marked as obsolete"
    );
  }

  record ElevationGroupAlreadyActivated(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupAlreadyActivated INSTANCE = new ElevationGroupAlreadyActivated(
        6083, "Obligation already part of the same elevation group"
    );
  }

  record ObligationInObsoleteReserve(int code, String msg) implements KaminoLendingError {

    public static final ObligationInObsoleteReserve INSTANCE = new ObligationInObsoleteReserve(
        6084, "Obligation has a deposit or borrow in an obsolete reserve"
    );
  }

  record ReferrerStateOwnerMismatch(int code, String msg) implements KaminoLendingError {

    public static final ReferrerStateOwnerMismatch INSTANCE = new ReferrerStateOwnerMismatch(
        6085, "Referrer state owner does not match the given signer"
    );
  }

  record UserMetadataOwnerAlreadySet(int code, String msg) implements KaminoLendingError {

    public static final UserMetadataOwnerAlreadySet INSTANCE = new UserMetadataOwnerAlreadySet(
        6086, "User metadata owner is already set"
    );
  }

  record CollateralNonLiquidatable(int code, String msg) implements KaminoLendingError {

    public static final CollateralNonLiquidatable INSTANCE = new CollateralNonLiquidatable(
        6087, "This collateral cannot be liquidated (LTV set to 0)"
    );
  }

  record BorrowingDisabled(int code, String msg) implements KaminoLendingError {

    public static final BorrowingDisabled INSTANCE = new BorrowingDisabled(
        6088, "Borrowing is disabled"
    );
  }

  record BorrowLimitExceeded(int code, String msg) implements KaminoLendingError {

    public static final BorrowLimitExceeded INSTANCE = new BorrowLimitExceeded(
        6089, "Cannot borrow above borrow limit"
    );
  }

  record DepositLimitExceeded(int code, String msg) implements KaminoLendingError {

    public static final DepositLimitExceeded INSTANCE = new DepositLimitExceeded(
        6090, "Cannot deposit above deposit limit"
    );
  }

  record BorrowingDisabledOutsideElevationGroup(int code, String msg) implements KaminoLendingError {

    public static final BorrowingDisabledOutsideElevationGroup INSTANCE = new BorrowingDisabledOutsideElevationGroup(
        6091, "Reserve does not accept any new borrows outside elevation group"
    );
  }

  record NetValueRemainingTooSmall(int code, String msg) implements KaminoLendingError {

    public static final NetValueRemainingTooSmall INSTANCE = new NetValueRemainingTooSmall(
        6092, "Net value remaining too small"
    );
  }

  record WorseLtvBlocked(int code, String msg) implements KaminoLendingError {

    public static final WorseLtvBlocked INSTANCE = new WorseLtvBlocked(
        6093, "Cannot get the obligation in a worse position"
    );
  }

  record LiabilitiesBiggerThanAssets(int code, String msg) implements KaminoLendingError {

    public static final LiabilitiesBiggerThanAssets INSTANCE = new LiabilitiesBiggerThanAssets(
        6094, "Cannot have more liabilities than assets in a position"
    );
  }

  record ReserveTokenBalanceMismatch(int code, String msg) implements KaminoLendingError {

    public static final ReserveTokenBalanceMismatch INSTANCE = new ReserveTokenBalanceMismatch(
        6095, "Reserve state and token account cannot drift"
    );
  }

  record ReserveVaultBalanceMismatch(int code, String msg) implements KaminoLendingError {

    public static final ReserveVaultBalanceMismatch INSTANCE = new ReserveVaultBalanceMismatch(
        6096, "Reserve token account has been unexpectedly modified"
    );
  }

  record ReserveAccountingMismatch(int code, String msg) implements KaminoLendingError {

    public static final ReserveAccountingMismatch INSTANCE = new ReserveAccountingMismatch(
        6097, "Reserve internal state accounting has been unexpectedly modified"
    );
  }

  record BorrowingAboveUtilizationRateDisabled(int code, String msg) implements KaminoLendingError {

    public static final BorrowingAboveUtilizationRateDisabled INSTANCE = new BorrowingAboveUtilizationRateDisabled(
        6098, "Borrowing above set utilization rate is disabled"
    );
  }

  record LiquidationBorrowFactorPriority(int code, String msg) implements KaminoLendingError {

    public static final LiquidationBorrowFactorPriority INSTANCE = new LiquidationBorrowFactorPriority(
        6099, "Liquidation must prioritize the debt with the highest borrow factor"
    );
  }

  record LiquidationLowestLiquidationLtvPriority(int code, String msg) implements KaminoLendingError {

    public static final LiquidationLowestLiquidationLtvPriority INSTANCE = new LiquidationLowestLiquidationLtvPriority(
        6100, "Liquidation must prioritize the collateral with the lowest liquidation LTV"
    );
  }

  record ElevationGroupBorrowLimitExceeded(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupBorrowLimitExceeded INSTANCE = new ElevationGroupBorrowLimitExceeded(
        6101, "Elevation group borrow limit exceeded"
    );
  }

  record ElevationGroupWithoutDebtReserve(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupWithoutDebtReserve INSTANCE = new ElevationGroupWithoutDebtReserve(
        6102, "The elevation group does not have a debt reserve defined"
    );
  }

  record ElevationGroupMaxCollateralReserveZero(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupMaxCollateralReserveZero INSTANCE = new ElevationGroupMaxCollateralReserveZero(
        6103, "The elevation group does not allow any collateral reserves"
    );
  }

  record ElevationGroupHasAnotherDebtReserve(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupHasAnotherDebtReserve INSTANCE = new ElevationGroupHasAnotherDebtReserve(
        6104, "In elevation group attempt to borrow from a reserve that is not the debt reserve"
    );
  }

  record ElevationGroupDebtReserveAsCollateral(int code, String msg) implements KaminoLendingError {

    public static final ElevationGroupDebtReserveAsCollateral INSTANCE = new ElevationGroupDebtReserveAsCollateral(
        6105, "The elevation group's debt reserve cannot be used as a collateral reserve"
    );
  }

  record ObligationCollateralExceedsElevationGroupLimit(int code, String msg) implements KaminoLendingError {

    public static final ObligationCollateralExceedsElevationGroupLimit INSTANCE = new ObligationCollateralExceedsElevationGroupLimit(
        6106, "Obligation have more collateral than the maximum allowed by the elevation group"
    );
  }

  record ObligationElevationGroupMultipleDebtReserve(int code, String msg) implements KaminoLendingError {

    public static final ObligationElevationGroupMultipleDebtReserve INSTANCE = new ObligationElevationGroupMultipleDebtReserve(
        6107, "Obligation is an elevation group but have more than one debt reserve"
    );
  }

  record UnsupportedTokenExtension(int code, String msg) implements KaminoLendingError {

    public static final UnsupportedTokenExtension INSTANCE = new UnsupportedTokenExtension(
        6108, "Mint has a token (2022) extension that is not supported"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements KaminoLendingError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        6109, "Can't have an spl token mint with a t22 account"
    );
  }

  record DepositDisabledOutsideElevationGroup(int code, String msg) implements KaminoLendingError {

    public static final DepositDisabledOutsideElevationGroup INSTANCE = new DepositDisabledOutsideElevationGroup(
        6110, "Can't deposit into this reserve outside elevation group"
    );
  }

  record CannotCalculateReferralAmountDueToSlotsMismatch(int code, String msg) implements KaminoLendingError {

    public static final CannotCalculateReferralAmountDueToSlotsMismatch INSTANCE = new CannotCalculateReferralAmountDueToSlotsMismatch(
        6111, "Cannot calculate referral amount due to slots mismatch"
    );
  }

  record ObligationOwnersMustMatch(int code, String msg) implements KaminoLendingError {

    public static final ObligationOwnersMustMatch INSTANCE = new ObligationOwnersMustMatch(
        6112, "Obligation owners must match"
    );
  }

  record ObligationsMustMatch(int code, String msg) implements KaminoLendingError {

    public static final ObligationsMustMatch INSTANCE = new ObligationsMustMatch(
        6113, "Obligations must match"
    );
  }

  record LendingMarketsMustMatch(int code, String msg) implements KaminoLendingError {

    public static final LendingMarketsMustMatch INSTANCE = new LendingMarketsMustMatch(
        6114, "Lending markets must match"
    );
  }

  record ObligationCurrentlyMarkedForDeleveraging(int code, String msg) implements KaminoLendingError {

    public static final ObligationCurrentlyMarkedForDeleveraging INSTANCE = new ObligationCurrentlyMarkedForDeleveraging(
        6115, "Obligation is already marked for deleveraging"
    );
  }

  record MaximumWithdrawValueZero(int code, String msg) implements KaminoLendingError {

    public static final MaximumWithdrawValueZero INSTANCE = new MaximumWithdrawValueZero(
        6116, "Maximum withdrawable value of this collateral is zero, LTV needs improved"
    );
  }

  record ZeroMaxLtvAssetsInDeposits(int code, String msg) implements KaminoLendingError {

    public static final ZeroMaxLtvAssetsInDeposits INSTANCE = new ZeroMaxLtvAssetsInDeposits(
        6117, "No max LTV 0 assets allowed in deposits for repay and withdraw"
    );
  }

  record LowestLtvAssetsPriority(int code, String msg) implements KaminoLendingError {

    public static final LowestLtvAssetsPriority INSTANCE = new LowestLtvAssetsPriority(
        6118, "Withdrawing must prioritize the collateral with the lowest reserve max-LTV"
    );
  }

  record WorseLtvThanUnhealthyLtv(int code, String msg) implements KaminoLendingError {

    public static final WorseLtvThanUnhealthyLtv INSTANCE = new WorseLtvThanUnhealthyLtv(
        6119, "Cannot get the obligation liquidatable"
    );
  }

  record FarmAccountsMissing(int code, String msg) implements KaminoLendingError {

    public static final FarmAccountsMissing INSTANCE = new FarmAccountsMissing(
        6120, "Farm accounts to refresh are missing"
    );
  }

  record RepayTooSmallForFullLiquidation(int code, String msg) implements KaminoLendingError {

    public static final RepayTooSmallForFullLiquidation INSTANCE = new RepayTooSmallForFullLiquidation(
        6121, "Repay amount is too small to satisfy the mandatory full liquidation"
    );
  }

  record InsufficientRepayAmount(int code, String msg) implements KaminoLendingError {

    public static final InsufficientRepayAmount INSTANCE = new InsufficientRepayAmount(
        6122, "Liquidator provided repay amount lower than required by liquidation rules"
    );
  }

  record OrderIndexOutOfBounds(int code, String msg) implements KaminoLendingError {

    public static final OrderIndexOutOfBounds INSTANCE = new OrderIndexOutOfBounds(
        6123, "Obligation order of the given index cannot exist"
    );
  }

  record InvalidOrderConfiguration(int code, String msg) implements KaminoLendingError {

    public static final InvalidOrderConfiguration INSTANCE = new InvalidOrderConfiguration(
        6124, "Given order configuration has wrong parameters"
    );
  }

  record OrderConfigurationNotSupportedByObligation(int code, String msg) implements KaminoLendingError {

    public static final OrderConfigurationNotSupportedByObligation INSTANCE = new OrderConfigurationNotSupportedByObligation(
        6125, "Given order configuration cannot be used with the current state of the obligation"
    );
  }

  record OperationNotPermittedWithCurrentObligationOrders(int code, String msg) implements KaminoLendingError {

    public static final OperationNotPermittedWithCurrentObligationOrders INSTANCE = new OperationNotPermittedWithCurrentObligationOrders(
        6126, "Single debt, single collateral obligation orders have to be cancelled before changing the deposit/borrow count"
    );
  }

  record OperationNotPermittedMarketImmutable(int code, String msg) implements KaminoLendingError {

    public static final OperationNotPermittedMarketImmutable INSTANCE = new OperationNotPermittedMarketImmutable(
        6127, "Cannot update lending market because it is set as immutable"
    );
  }

  record OrderCreationDisabled(int code, String msg) implements KaminoLendingError {

    public static final OrderCreationDisabled INSTANCE = new OrderCreationDisabled(
        6128, "Creation of new orders is disabled"
    );
  }

  record NoUpgradeAuthority(int code, String msg) implements KaminoLendingError {

    public static final NoUpgradeAuthority INSTANCE = new NoUpgradeAuthority(
        6129, "Cannot initialize global config because there is no upgrade authority to the program"
    );
  }
}
