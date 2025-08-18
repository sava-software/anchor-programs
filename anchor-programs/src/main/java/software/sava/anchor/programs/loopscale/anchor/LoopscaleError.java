package software.sava.anchor.programs.loopscale.anchor;

import software.sava.anchor.ProgramError;

public sealed interface LoopscaleError extends ProgramError permits
    LoopscaleError.InvalidCollateralMintOrIdentifier,
    LoopscaleError.MaintanenceCollateralNotMet,
    LoopscaleError.ArithmeticError,
    LoopscaleError.InvalidTimestamp,
    LoopscaleError.MaxCollateralReached,
    LoopscaleError.PartialWithdrawsNotAllowedForOrcaPositions,
    LoopscaleError.InvalidLedgerStatusForRefinance,
    LoopscaleError.InvalidLedgerStatusForLedgerSale,
    LoopscaleError.InvalidMarketInformation,
    LoopscaleError.LoanSizeExceedsMaxOriginationSize,
    LoopscaleError.InvalidManager,
    LoopscaleError.NoOpenLedgers,
    LoopscaleError.InvalidDurationIndex,
    LoopscaleError.InvalidAssetIdentifier,
    LoopscaleError.APYDisabled,
    LoopscaleError.CollateralNotPresent,
    LoopscaleError.InvalidPrincipalMint,
    LoopscaleError.InvalidLedgerStrategy,
    LoopscaleError.InvalidLedgerIndex,
    LoopscaleError.NoSupportedCollateralFound,
    LoopscaleError.CannotSellToSameStrategy,
    LoopscaleError.InvalidOracleAccount,
    LoopscaleError.InvalidLegacyPythAccount,
    LoopscaleError.InvalidPriceExpo,
    LoopscaleError.StalePrice,
    LoopscaleError.PriceUncertainityExceeded,
    LoopscaleError.NegativePrice,
    LoopscaleError.PriceOverflow,
    LoopscaleError.MissingMarketInformationAccount,
    LoopscaleError.MissingOracleAccount,
    LoopscaleError.InvalidSeeds,
    LoopscaleError.InvalidLoanVault,
    LoopscaleError.LoanNotInDefault,
    LoopscaleError.OrderStatusMismatch,
    LoopscaleError.LSTOracleInvalid,
    LoopscaleError.LSTOraclePriceNotFound,
    LoopscaleError.InvalidDecimal,
    LoopscaleError.InvalidConversionOracleQuote,
    LoopscaleError.MissingConversionRate,
    LoopscaleError.NotEnoughRemainingAccounts,
    LoopscaleError.InvalidQuoteMintForMeteoraVault,
    LoopscaleError.InvalidBaseMintForMeteoraVault,
    LoopscaleError.InvalidDecimalsForMeteoraVault,
    LoopscaleError.MeteoraVaultTotalAmountErr,
    LoopscaleError.InvalidExtraAccounts,
    LoopscaleError.InvalidSwitchboardAccountOwner,
    LoopscaleError.InvalidSwitchboardAccount,
    LoopscaleError.InvalidOrcaAccountOwner,
    LoopscaleError.InvalidCLMMPosition,
    LoopscaleError.InvalidOrcaWhirlpool,
    LoopscaleError.InvalidTickArray,
    LoopscaleError.PositionDoesNotMatchPool,
    LoopscaleError.PositionDoesNotMatchMint,
    LoopscaleError.TickArrayDoesNotMatchPool,
    LoopscaleError.MintDoesNotMatchWhirlpool,
    LoopscaleError.InvalidPythAccount,
    LoopscaleError.InvalidLtvData,
    LoopscaleError.LtvDataNotFound,
    LoopscaleError.InvalidMintType,
    LoopscaleError.InvalidMeteoraPool,
    LoopscaleError.InvalidLPAccount,
    LoopscaleError.UnsupportedCurveType,
    LoopscaleError.SwapSimulationFailed,
    LoopscaleError.InvalidBaseMintForFLP,
    LoopscaleError.FLPPoolNotSupported,
    LoopscaleError.InvalidAssetIndex,
    LoopscaleError.InvalidAssetIndexGuidance,
    LoopscaleError.PriceNotFound,
    LoopscaleError.DuplicateCollateralMintsInMarketInformation,
    LoopscaleError.MarketInformationFull,
    LoopscaleError.AssetNotFoundInMarketInformation,
    LoopscaleError.MarketInformationAlreadyExists,
    LoopscaleError.InvalidVaultStrategy,
    LoopscaleError.LedgerHealthy,
    LoopscaleError.InvalidLiquidation,
    LoopscaleError.InsufficientLiquidity,
    LoopscaleError.InterestNotAccrued,
    LoopscaleError.InvalidInterestPerSecondForClose,
    LoopscaleError.InvalidExternalYieldAmountForClose,
    LoopscaleError.InvalidCurrentDeployedAmountForClose,
    LoopscaleError.InvalidTokenBalanceForClose,
    LoopscaleError.InvalidFeeClaimableForClose,
    LoopscaleError.InvalidLender,
    LoopscaleError.SaleSlippageExceeded,
    LoopscaleError.ExpectedLtvMismatch,
    LoopscaleError.ExpectedLqtMismatch,
    LoopscaleError.ExpectedApyMismatch,
    LoopscaleError.LpSlippageToleranceExceeded,
    LoopscaleError.InvalidStartTime,
    LoopscaleError.InvalidWeightMatrix,
    LoopscaleError.LoanPastEndTime,
    LoopscaleError.InvalidCollateralWithdrawalWeightMatrixAssignment,
    LoopscaleError.TooMuchCollateralWithdrawn,
    LoopscaleError.InvalidPrincipalWithdrawalWeightMatrixAssignment,
    LoopscaleError.LedgerInRefinanceGracePeriodCannotBeWithdrawn,
    LoopscaleError.OnlyBorrowerCanRefinanceBeforeEnd,
    LoopscaleError.InvalidDurationForLedgerSale,
    LoopscaleError.StakedSolCurrentlyUnsupported,
    LoopscaleError.LoanNotFullyRepaid,
    LoopscaleError.InvalidLiquidationThreshold,
    LoopscaleError.MaxAmountInExceeded,
    LoopscaleError.MinAmountOutNotMet,
    LoopscaleError.MissingAccount,
    LoopscaleError.LQTWeightedCollateralValueGreaterThanTotalDebt,
    LoopscaleError.StrategyOriginationsDisabled,
    LoopscaleError.TimelockDelayNotMet,
    LoopscaleError.VaultDepositsDisabled,
    LoopscaleError.InvalidLpParams,
    LoopscaleError.InvalidVaultAccount,
    LoopscaleError.InvalidStakeDuration,
    LoopscaleError.EndTimeBeforeStartTime,
    LoopscaleError.InvalidRewardStartTime,
    LoopscaleError.RewardsScheduleNotEligibleForClose,
    LoopscaleError.InvalidRewardEndTime,
    LoopscaleError.NoAvailableRewardsSchedules,
    LoopscaleError.NotEnoughRewardsTransferred,
    LoopscaleError.InvalidCpiProgram,
    LoopscaleError.InvalidRewardMint,
    LoopscaleError.InvalidPerenaPoolData,
    LoopscaleError.PriceUnderflow,
    LoopscaleError.InvalidPriceData,
    LoopscaleError.RemoveCollateralNotFullDisable,
    LoopscaleError.LedgerStrategyMismatch,
    LoopscaleError.InvalidLiquidityAmount,
    LoopscaleError.CannotUpdateVaultMarketInfo,
    LoopscaleError.InvalidRewardsMint,
    LoopscaleError.InvalidOracleAccountOwner,
    LoopscaleError.InvalidMaxUncertainty,
    LoopscaleError.InvalidMaxAge,
    LoopscaleError.InvalidLtv,
    LoopscaleError.UnsupportedOracleType,
    LoopscaleError.InvalidQuoteMint,
    LoopscaleError.TotalWeightedStakeSupplyZero,
    LoopscaleError.TransferFeeNotSupported,
    LoopscaleError.TransferHookNotSupported,
    LoopscaleError.InvalidAuthority,
    LoopscaleError.InvalidAssetMint,
    LoopscaleError.PythPriceVerificationLevelTooLow,
    LoopscaleError.InvalidInputAmount,
    LoopscaleError.InvalidCpiAccount,
    LoopscaleError.CollateralAllocationCapExceeded,
    LoopscaleError.OneHourPrincipalCapExceeded,
    LoopscaleError.TwentyFourHourPrincipalCapExceeded,
    LoopscaleError.OutstandingPrincipalCapExceeded,
    LoopscaleError.NoCapsToUpdateFoundOnTimelock,
    LoopscaleError.IllegalLoanLock,
    LoopscaleError.InvalidLoanStatus,
    LoopscaleError.LoanNotEligibleForClose,
    LoopscaleError.UnsupportedAction,
    LoopscaleError.ZeroPrice {

  static LoopscaleError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidCollateralMintOrIdentifier.INSTANCE;
      case 6001 -> MaintanenceCollateralNotMet.INSTANCE;
      case 6002 -> ArithmeticError.INSTANCE;
      case 6003 -> InvalidTimestamp.INSTANCE;
      case 6004 -> MaxCollateralReached.INSTANCE;
      case 6005 -> PartialWithdrawsNotAllowedForOrcaPositions.INSTANCE;
      case 6006 -> InvalidLedgerStatusForRefinance.INSTANCE;
      case 6007 -> InvalidLedgerStatusForLedgerSale.INSTANCE;
      case 6008 -> InvalidMarketInformation.INSTANCE;
      case 6009 -> LoanSizeExceedsMaxOriginationSize.INSTANCE;
      case 6010 -> InvalidManager.INSTANCE;
      case 6011 -> NoOpenLedgers.INSTANCE;
      case 6012 -> InvalidDurationIndex.INSTANCE;
      case 6013 -> InvalidAssetIdentifier.INSTANCE;
      case 6014 -> APYDisabled.INSTANCE;
      case 6015 -> CollateralNotPresent.INSTANCE;
      case 6016 -> InvalidPrincipalMint.INSTANCE;
      case 6017 -> InvalidLedgerStrategy.INSTANCE;
      case 6018 -> InvalidLedgerIndex.INSTANCE;
      case 6019 -> NoSupportedCollateralFound.INSTANCE;
      case 6020 -> CannotSellToSameStrategy.INSTANCE;
      case 6021 -> InvalidOracleAccount.INSTANCE;
      case 6022 -> InvalidLegacyPythAccount.INSTANCE;
      case 6023 -> InvalidPriceExpo.INSTANCE;
      case 6024 -> StalePrice.INSTANCE;
      case 6025 -> PriceUncertainityExceeded.INSTANCE;
      case 6026 -> NegativePrice.INSTANCE;
      case 6027 -> PriceOverflow.INSTANCE;
      case 6028 -> MissingMarketInformationAccount.INSTANCE;
      case 6029 -> MissingOracleAccount.INSTANCE;
      case 6030 -> InvalidSeeds.INSTANCE;
      case 6031 -> InvalidLoanVault.INSTANCE;
      case 6032 -> LoanNotInDefault.INSTANCE;
      case 6033 -> OrderStatusMismatch.INSTANCE;
      case 6034 -> LSTOracleInvalid.INSTANCE;
      case 6035 -> LSTOraclePriceNotFound.INSTANCE;
      case 6036 -> InvalidDecimal.INSTANCE;
      case 6037 -> InvalidConversionOracleQuote.INSTANCE;
      case 6038 -> MissingConversionRate.INSTANCE;
      case 6039 -> NotEnoughRemainingAccounts.INSTANCE;
      case 6040 -> InvalidQuoteMintForMeteoraVault.INSTANCE;
      case 6041 -> InvalidBaseMintForMeteoraVault.INSTANCE;
      case 6042 -> InvalidDecimalsForMeteoraVault.INSTANCE;
      case 6043 -> MeteoraVaultTotalAmountErr.INSTANCE;
      case 6044 -> InvalidExtraAccounts.INSTANCE;
      case 6045 -> InvalidSwitchboardAccountOwner.INSTANCE;
      case 6046 -> InvalidSwitchboardAccount.INSTANCE;
      case 6047 -> InvalidOrcaAccountOwner.INSTANCE;
      case 6048 -> InvalidCLMMPosition.INSTANCE;
      case 6049 -> InvalidOrcaWhirlpool.INSTANCE;
      case 6050 -> InvalidTickArray.INSTANCE;
      case 6051 -> PositionDoesNotMatchPool.INSTANCE;
      case 6052 -> PositionDoesNotMatchMint.INSTANCE;
      case 6053 -> TickArrayDoesNotMatchPool.INSTANCE;
      case 6054 -> MintDoesNotMatchWhirlpool.INSTANCE;
      case 6055 -> InvalidPythAccount.INSTANCE;
      case 6056 -> InvalidLtvData.INSTANCE;
      case 6057 -> LtvDataNotFound.INSTANCE;
      case 6058 -> InvalidMintType.INSTANCE;
      case 6059 -> InvalidMeteoraPool.INSTANCE;
      case 6060 -> InvalidLPAccount.INSTANCE;
      case 6061 -> UnsupportedCurveType.INSTANCE;
      case 6062 -> SwapSimulationFailed.INSTANCE;
      case 6063 -> InvalidBaseMintForFLP.INSTANCE;
      case 6064 -> FLPPoolNotSupported.INSTANCE;
      case 6065 -> InvalidAssetIndex.INSTANCE;
      case 6066 -> InvalidAssetIndexGuidance.INSTANCE;
      case 6067 -> PriceNotFound.INSTANCE;
      case 6068 -> DuplicateCollateralMintsInMarketInformation.INSTANCE;
      case 6069 -> MarketInformationFull.INSTANCE;
      case 6070 -> AssetNotFoundInMarketInformation.INSTANCE;
      case 6071 -> MarketInformationAlreadyExists.INSTANCE;
      case 6072 -> InvalidVaultStrategy.INSTANCE;
      case 6073 -> LedgerHealthy.INSTANCE;
      case 6074 -> InvalidLiquidation.INSTANCE;
      case 6075 -> InsufficientLiquidity.INSTANCE;
      case 6076 -> InterestNotAccrued.INSTANCE;
      case 6077 -> InvalidInterestPerSecondForClose.INSTANCE;
      case 6078 -> InvalidExternalYieldAmountForClose.INSTANCE;
      case 6079 -> InvalidCurrentDeployedAmountForClose.INSTANCE;
      case 6080 -> InvalidTokenBalanceForClose.INSTANCE;
      case 6081 -> InvalidFeeClaimableForClose.INSTANCE;
      case 6082 -> InvalidLender.INSTANCE;
      case 6083 -> SaleSlippageExceeded.INSTANCE;
      case 6084 -> ExpectedLtvMismatch.INSTANCE;
      case 6085 -> ExpectedLqtMismatch.INSTANCE;
      case 6086 -> ExpectedApyMismatch.INSTANCE;
      case 6087 -> LpSlippageToleranceExceeded.INSTANCE;
      case 6088 -> InvalidStartTime.INSTANCE;
      case 6089 -> InvalidWeightMatrix.INSTANCE;
      case 6090 -> LoanPastEndTime.INSTANCE;
      case 6091 -> InvalidCollateralWithdrawalWeightMatrixAssignment.INSTANCE;
      case 6092 -> TooMuchCollateralWithdrawn.INSTANCE;
      case 6093 -> InvalidPrincipalWithdrawalWeightMatrixAssignment.INSTANCE;
      case 6094 -> LedgerInRefinanceGracePeriodCannotBeWithdrawn.INSTANCE;
      case 6095 -> OnlyBorrowerCanRefinanceBeforeEnd.INSTANCE;
      case 6096 -> InvalidDurationForLedgerSale.INSTANCE;
      case 6097 -> StakedSolCurrentlyUnsupported.INSTANCE;
      case 6098 -> LoanNotFullyRepaid.INSTANCE;
      case 6099 -> InvalidLiquidationThreshold.INSTANCE;
      case 6100 -> MaxAmountInExceeded.INSTANCE;
      case 6101 -> MinAmountOutNotMet.INSTANCE;
      case 6102 -> MissingAccount.INSTANCE;
      case 6103 -> LQTWeightedCollateralValueGreaterThanTotalDebt.INSTANCE;
      case 6104 -> StrategyOriginationsDisabled.INSTANCE;
      case 6105 -> TimelockDelayNotMet.INSTANCE;
      case 6106 -> VaultDepositsDisabled.INSTANCE;
      case 6107 -> InvalidLpParams.INSTANCE;
      case 6108 -> InvalidVaultAccount.INSTANCE;
      case 6109 -> InvalidStakeDuration.INSTANCE;
      case 6110 -> EndTimeBeforeStartTime.INSTANCE;
      case 6111 -> InvalidRewardStartTime.INSTANCE;
      case 6112 -> RewardsScheduleNotEligibleForClose.INSTANCE;
      case 6113 -> InvalidRewardEndTime.INSTANCE;
      case 6114 -> NoAvailableRewardsSchedules.INSTANCE;
      case 6115 -> NotEnoughRewardsTransferred.INSTANCE;
      case 6116 -> InvalidCpiProgram.INSTANCE;
      case 6117 -> InvalidRewardMint.INSTANCE;
      case 6118 -> InvalidPerenaPoolData.INSTANCE;
      case 6119 -> PriceUnderflow.INSTANCE;
      case 6120 -> InvalidPriceData.INSTANCE;
      case 6121 -> RemoveCollateralNotFullDisable.INSTANCE;
      case 6122 -> LedgerStrategyMismatch.INSTANCE;
      case 6123 -> InvalidLiquidityAmount.INSTANCE;
      case 6124 -> CannotUpdateVaultMarketInfo.INSTANCE;
      case 6125 -> InvalidRewardsMint.INSTANCE;
      case 6126 -> InvalidOracleAccountOwner.INSTANCE;
      case 6127 -> InvalidMaxUncertainty.INSTANCE;
      case 6128 -> InvalidMaxAge.INSTANCE;
      case 6129 -> InvalidLtv.INSTANCE;
      case 6130 -> UnsupportedOracleType.INSTANCE;
      case 6131 -> InvalidQuoteMint.INSTANCE;
      case 6132 -> TotalWeightedStakeSupplyZero.INSTANCE;
      case 6133 -> TransferFeeNotSupported.INSTANCE;
      case 6134 -> TransferHookNotSupported.INSTANCE;
      case 6135 -> InvalidAuthority.INSTANCE;
      case 6136 -> InvalidAssetMint.INSTANCE;
      case 6137 -> PythPriceVerificationLevelTooLow.INSTANCE;
      case 6138 -> InvalidInputAmount.INSTANCE;
      case 6139 -> InvalidCpiAccount.INSTANCE;
      case 6140 -> CollateralAllocationCapExceeded.INSTANCE;
      case 6141 -> OneHourPrincipalCapExceeded.INSTANCE;
      case 6142 -> TwentyFourHourPrincipalCapExceeded.INSTANCE;
      case 6143 -> OutstandingPrincipalCapExceeded.INSTANCE;
      case 6144 -> NoCapsToUpdateFoundOnTimelock.INSTANCE;
      case 6145 -> IllegalLoanLock.INSTANCE;
      case 6146 -> InvalidLoanStatus.INSTANCE;
      case 6147 -> LoanNotEligibleForClose.INSTANCE;
      case 6148 -> UnsupportedAction.INSTANCE;
      case 6149 -> ZeroPrice.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Loopscale error code: " + errorCode);
    };
  }

  record InvalidCollateralMintOrIdentifier(int code, String msg) implements LoopscaleError {

    public static final InvalidCollateralMintOrIdentifier INSTANCE = new InvalidCollateralMintOrIdentifier(
        6000, "The collateral mint specified does not match identifier according to type"
    );
  }

  record MaintanenceCollateralNotMet(int code, String msg) implements LoopscaleError {

    public static final MaintanenceCollateralNotMet INSTANCE = new MaintanenceCollateralNotMet(
        6001, "Loan doesnt have enough collateral for maintenance"
    );
  }

  record ArithmeticError(int code, String msg) implements LoopscaleError {

    public static final ArithmeticError INSTANCE = new ArithmeticError(
        6002, "Arithmetic underflow/overflow"
    );
  }

  record InvalidTimestamp(int code, String msg) implements LoopscaleError {

    public static final InvalidTimestamp INSTANCE = new InvalidTimestamp(
        6003, "Invalid timestamp"
    );
  }

  record MaxCollateralReached(int code, String msg) implements LoopscaleError {

    public static final MaxCollateralReached INSTANCE = new MaxCollateralReached(
        6004, "Max Collateral reached"
    );
  }

  record PartialWithdrawsNotAllowedForOrcaPositions(int code, String msg) implements LoopscaleError {

    public static final PartialWithdrawsNotAllowedForOrcaPositions INSTANCE = new PartialWithdrawsNotAllowedForOrcaPositions(
        6005, "Partial withdraws not allowed for orca positions"
    );
  }

  record InvalidLedgerStatusForRefinance(int code, String msg) implements LoopscaleError {

    public static final InvalidLedgerStatusForRefinance INSTANCE = new InvalidLedgerStatusForRefinance(
        6006, "Invalid ledger status for refinance"
    );
  }

  record InvalidLedgerStatusForLedgerSale(int code, String msg) implements LoopscaleError {

    public static final InvalidLedgerStatusForLedgerSale INSTANCE = new InvalidLedgerStatusForLedgerSale(
        6007, "Invalid ledger status for ledger sale"
    );
  }

  record InvalidMarketInformation(int code, String msg) implements LoopscaleError {

    public static final InvalidMarketInformation INSTANCE = new InvalidMarketInformation(
        6008, "Invalid market information"
    );
  }

  record LoanSizeExceedsMaxOriginationSize(int code, String msg) implements LoopscaleError {

    public static final LoanSizeExceedsMaxOriginationSize INSTANCE = new LoanSizeExceedsMaxOriginationSize(
        6009, "Loan size exceeds max origination size"
    );
  }

  record InvalidManager(int code, String msg) implements LoopscaleError {

    public static final InvalidManager INSTANCE = new InvalidManager(
        6010, "Invalid manager"
    );
  }

  record NoOpenLedgers(int code, String msg) implements LoopscaleError {

    public static final NoOpenLedgers INSTANCE = new NoOpenLedgers(
        6011, "No open ledgers"
    );
  }

  record InvalidDurationIndex(int code, String msg) implements LoopscaleError {

    public static final InvalidDurationIndex INSTANCE = new InvalidDurationIndex(
        6012, "Invalid duration index"
    );
  }

  record InvalidAssetIdentifier(int code, String msg) implements LoopscaleError {

    public static final InvalidAssetIdentifier INSTANCE = new InvalidAssetIdentifier(
        6013, "Invalid asset identifier"
    );
  }

  record APYDisabled(int code, String msg) implements LoopscaleError {

    public static final APYDisabled INSTANCE = new APYDisabled(
        6014, "APY is disabled"
    );
  }

  record CollateralNotPresent(int code, String msg) implements LoopscaleError {

    public static final CollateralNotPresent INSTANCE = new CollateralNotPresent(
        6015, "Collateral not present"
    );
  }

  record InvalidPrincipalMint(int code, String msg) implements LoopscaleError {

    public static final InvalidPrincipalMint INSTANCE = new InvalidPrincipalMint(
        6016, "Invalid principal mint"
    );
  }

  record InvalidLedgerStrategy(int code, String msg) implements LoopscaleError {

    public static final InvalidLedgerStrategy INSTANCE = new InvalidLedgerStrategy(
        6017, "Invalid ledger strategy"
    );
  }

  record InvalidLedgerIndex(int code, String msg) implements LoopscaleError {

    public static final InvalidLedgerIndex INSTANCE = new InvalidLedgerIndex(
        6018, "Invalid ledger index"
    );
  }

  record NoSupportedCollateralFound(int code, String msg) implements LoopscaleError {

    public static final NoSupportedCollateralFound INSTANCE = new NoSupportedCollateralFound(
        6019, "No supported collateral found"
    );
  }

  record CannotSellToSameStrategy(int code, String msg) implements LoopscaleError {

    public static final CannotSellToSameStrategy INSTANCE = new CannotSellToSameStrategy(
        6020, "Cannot sell to same strategy"
    );
  }

  record InvalidOracleAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidOracleAccount INSTANCE = new InvalidOracleAccount(
        6021, "Invalid Oracle account"
    );
  }

  record InvalidLegacyPythAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidLegacyPythAccount INSTANCE = new InvalidLegacyPythAccount(
        6022, "Invalid Legacy Pyth account"
    );
  }

  record InvalidPriceExpo(int code, String msg) implements LoopscaleError {

    public static final InvalidPriceExpo INSTANCE = new InvalidPriceExpo(
        6023, "Invalid price exponent"
    );
  }

  record StalePrice(int code, String msg) implements LoopscaleError {

    public static final StalePrice INSTANCE = new StalePrice(
        6024, "Stale price"
    );
  }

  record PriceUncertainityExceeded(int code, String msg) implements LoopscaleError {

    public static final PriceUncertainityExceeded INSTANCE = new PriceUncertainityExceeded(
        6025, "Price uncertainilty is more than max uncertainity"
    );
  }

  record NegativePrice(int code, String msg) implements LoopscaleError {

    public static final NegativePrice INSTANCE = new NegativePrice(
        6026, "Price cannot be negative"
    );
  }

  record PriceOverflow(int code, String msg) implements LoopscaleError {

    public static final PriceOverflow INSTANCE = new PriceOverflow(
        6027, "Price overflow"
    );
  }

  record MissingMarketInformationAccount(int code, String msg) implements LoopscaleError {

    public static final MissingMarketInformationAccount INSTANCE = new MissingMarketInformationAccount(
        6028, "Missing Oracle Information account in remaining accounts"
    );
  }

  record MissingOracleAccount(int code, String msg) implements LoopscaleError {

    public static final MissingOracleAccount INSTANCE = new MissingOracleAccount(
        6029, "Missing Oracle account in remaining accounts"
    );
  }

  record InvalidSeeds(int code, String msg) implements LoopscaleError {

    public static final InvalidSeeds INSTANCE = new InvalidSeeds(
        6030, "Invalid seeds provided"
    );
  }

  record InvalidLoanVault(int code, String msg) implements LoopscaleError {

    public static final InvalidLoanVault INSTANCE = new InvalidLoanVault(
        6031, "Invalid loan vault"
    );
  }

  record LoanNotInDefault(int code, String msg) implements LoopscaleError {

    public static final LoanNotInDefault INSTANCE = new LoanNotInDefault(
        6032, "Loan not in default"
    );
  }

  record OrderStatusMismatch(int code, String msg) implements LoopscaleError {

    public static final OrderStatusMismatch INSTANCE = new OrderStatusMismatch(
        6033, "Order status must be filled"
    );
  }

  record LSTOracleInvalid(int code, String msg) implements LoopscaleError {

    public static final LSTOracleInvalid INSTANCE = new LSTOracleInvalid(
        6034, "LST Oracle invalid"
    );
  }

  record LSTOraclePriceNotFound(int code, String msg) implements LoopscaleError {

    public static final LSTOraclePriceNotFound INSTANCE = new LSTOraclePriceNotFound(
        6035, "Could not get price per LST"
    );
  }

  record InvalidDecimal(int code, String msg) implements LoopscaleError {

    public static final InvalidDecimal INSTANCE = new InvalidDecimal(
        6036, "value could not be converted to Decimal"
    );
  }

  record InvalidConversionOracleQuote(int code, String msg) implements LoopscaleError {

    public static final InvalidConversionOracleQuote INSTANCE = new InvalidConversionOracleQuote(
        6037, "Invalid quote mint for conversion oracle"
    );
  }

  record MissingConversionRate(int code, String msg) implements LoopscaleError {

    public static final MissingConversionRate INSTANCE = new MissingConversionRate(
        6038, "Missing conversion rate"
    );
  }

  record NotEnoughRemainingAccounts(int code, String msg) implements LoopscaleError {

    public static final NotEnoughRemainingAccounts INSTANCE = new NotEnoughRemainingAccounts(
        6039, "Not enough remaining accounts passed in. Each lockbox asset requires at least 2 remaining accounts"
    );
  }

  record InvalidQuoteMintForMeteoraVault(int code, String msg) implements LoopscaleError {

    public static final InvalidQuoteMintForMeteoraVault INSTANCE = new InvalidQuoteMintForMeteoraVault(
        6040, "Invalid quote mint for vault oracle. Must be the same as vault base token"
    );
  }

  record InvalidBaseMintForMeteoraVault(int code, String msg) implements LoopscaleError {

    public static final InvalidBaseMintForMeteoraVault INSTANCE = new InvalidBaseMintForMeteoraVault(
        6041, "Invalid base mint for vault oracle. Must be the same as vault LP token"
    );
  }

  record InvalidDecimalsForMeteoraVault(int code, String msg) implements LoopscaleError {

    public static final InvalidDecimalsForMeteoraVault INSTANCE = new InvalidDecimalsForMeteoraVault(
        6042, "Invalid decimals for vault oracle. Must be the same as vault LP token"
    );
  }

  record MeteoraVaultTotalAmountErr(int code, String msg) implements LoopscaleError {

    public static final MeteoraVaultTotalAmountErr INSTANCE = new MeteoraVaultTotalAmountErr(
        6043, "Could not calculate total amount for meteroa vault"
    );
  }

  record InvalidExtraAccounts(int code, String msg) implements LoopscaleError {

    public static final InvalidExtraAccounts INSTANCE = new InvalidExtraAccounts(
        6044, "Not enough extra accounts"
    );
  }

  record InvalidSwitchboardAccountOwner(int code, String msg) implements LoopscaleError {

    public static final InvalidSwitchboardAccountOwner INSTANCE = new InvalidSwitchboardAccountOwner(
        6045, "Invalid switchboard account owner"
    );
  }

  record InvalidSwitchboardAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidSwitchboardAccount INSTANCE = new InvalidSwitchboardAccount(
        6046, "Invalid switchboard account"
    );
  }

  record InvalidOrcaAccountOwner(int code, String msg) implements LoopscaleError {

    public static final InvalidOrcaAccountOwner INSTANCE = new InvalidOrcaAccountOwner(
        6047, "Invalid orca account owner"
    );
  }

  record InvalidCLMMPosition(int code, String msg) implements LoopscaleError {

    public static final InvalidCLMMPosition INSTANCE = new InvalidCLMMPosition(
        6048, "Invalid CLMM position"
    );
  }

  record InvalidOrcaWhirlpool(int code, String msg) implements LoopscaleError {

    public static final InvalidOrcaWhirlpool INSTANCE = new InvalidOrcaWhirlpool(
        6049, "Invalid orca whirlpool"
    );
  }

  record InvalidTickArray(int code, String msg) implements LoopscaleError {

    public static final InvalidTickArray INSTANCE = new InvalidTickArray(
        6050, "Invalid CLMM tick array"
    );
  }

  record PositionDoesNotMatchPool(int code, String msg) implements LoopscaleError {

    public static final PositionDoesNotMatchPool INSTANCE = new PositionDoesNotMatchPool(
        6051, "Position does not match CLMM pool"
    );
  }

  record PositionDoesNotMatchMint(int code, String msg) implements LoopscaleError {

    public static final PositionDoesNotMatchMint INSTANCE = new PositionDoesNotMatchMint(
        6052, "Position does not match mint"
    );
  }

  record TickArrayDoesNotMatchPool(int code, String msg) implements LoopscaleError {

    public static final TickArrayDoesNotMatchPool INSTANCE = new TickArrayDoesNotMatchPool(
        6053, "Tick array does not match whirlpool"
    );
  }

  record MintDoesNotMatchWhirlpool(int code, String msg) implements LoopscaleError {

    public static final MintDoesNotMatchWhirlpool INSTANCE = new MintDoesNotMatchWhirlpool(
        6054, "Mint does not match whirlpool"
    );
  }

  record InvalidPythAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidPythAccount INSTANCE = new InvalidPythAccount(
        6055, "Invalid Pyth account"
    );
  }

  record InvalidLtvData(int code, String msg) implements LoopscaleError {

    public static final InvalidLtvData INSTANCE = new InvalidLtvData(
        6056, "Invalid LTV data"
    );
  }

  record LtvDataNotFound(int code, String msg) implements LoopscaleError {

    public static final LtvDataNotFound INSTANCE = new LtvDataNotFound(
        6057, "Ltv data not found"
    );
  }

  record InvalidMintType(int code, String msg) implements LoopscaleError {

    public static final InvalidMintType INSTANCE = new InvalidMintType(
        6058, "Invalid mint type for oracle"
    );
  }

  record InvalidMeteoraPool(int code, String msg) implements LoopscaleError {

    public static final InvalidMeteoraPool INSTANCE = new InvalidMeteoraPool(
        6059, "Invalid meteora pool"
    );
  }

  record InvalidLPAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidLPAccount INSTANCE = new InvalidLPAccount(
        6060, "Invalid LP account"
    );
  }

  record UnsupportedCurveType(int code, String msg) implements LoopscaleError {

    public static final UnsupportedCurveType INSTANCE = new UnsupportedCurveType(
        6061, "Unsupported curve type"
    );
  }

  record SwapSimulationFailed(int code, String msg) implements LoopscaleError {

    public static final SwapSimulationFailed INSTANCE = new SwapSimulationFailed(
        6062, "Swap simulation failed"
    );
  }

  record InvalidBaseMintForFLP(int code, String msg) implements LoopscaleError {

    public static final InvalidBaseMintForFLP INSTANCE = new InvalidBaseMintForFLP(
        6063, "Invalid base mint for FLP"
    );
  }

  record FLPPoolNotSupported(int code, String msg) implements LoopscaleError {

    public static final FLPPoolNotSupported INSTANCE = new FLPPoolNotSupported(
        6064, "FLP pool not supported"
    );
  }

  record InvalidAssetIndex(int code, String msg) implements LoopscaleError {

    public static final InvalidAssetIndex INSTANCE = new InvalidAssetIndex(
        6065, "Invalid asset index"
    );
  }

  record InvalidAssetIndexGuidance(int code, String msg) implements LoopscaleError {

    public static final InvalidAssetIndexGuidance INSTANCE = new InvalidAssetIndexGuidance(
        6066, "Invalid asset index guidance"
    );
  }

  record PriceNotFound(int code, String msg) implements LoopscaleError {

    public static final PriceNotFound INSTANCE = new PriceNotFound(
        6067, "Quote price not found in cache"
    );
  }

  record DuplicateCollateralMintsInMarketInformation(int code, String msg) implements LoopscaleError {

    public static final DuplicateCollateralMintsInMarketInformation INSTANCE = new DuplicateCollateralMintsInMarketInformation(
        6068, "Duplicate collateral mints in market information"
    );
  }

  record MarketInformationFull(int code, String msg) implements LoopscaleError {

    public static final MarketInformationFull INSTANCE = new MarketInformationFull(
        6069, "Market information is full"
    );
  }

  record AssetNotFoundInMarketInformation(int code, String msg) implements LoopscaleError {

    public static final AssetNotFoundInMarketInformation INSTANCE = new AssetNotFoundInMarketInformation(
        6070, "Asset not found in market information"
    );
  }

  record MarketInformationAlreadyExists(int code, String msg) implements LoopscaleError {

    public static final MarketInformationAlreadyExists INSTANCE = new MarketInformationAlreadyExists(
        6071, "Market information already exists"
    );
  }

  record InvalidVaultStrategy(int code, String msg) implements LoopscaleError {

    public static final InvalidVaultStrategy INSTANCE = new InvalidVaultStrategy(
        6072, "Invalid vault strategy"
    );
  }

  record LedgerHealthy(int code, String msg) implements LoopscaleError {

    public static final LedgerHealthy INSTANCE = new LedgerHealthy(
        6073, "Cannot liquidate a healthy ledger"
    );
  }

  record InvalidLiquidation(int code, String msg) implements LoopscaleError {

    public static final InvalidLiquidation INSTANCE = new InvalidLiquidation(
        6074, "Invalid liquidation"
    );
  }

  record InsufficientLiquidity(int code, String msg) implements LoopscaleError {

    public static final InsufficientLiquidity INSTANCE = new InsufficientLiquidity(
        6075, "Liquidity buffer has been exceeded"
    );
  }

  record InterestNotAccrued(int code, String msg) implements LoopscaleError {

    public static final InterestNotAccrued INSTANCE = new InterestNotAccrued(
        6076, "Interest not accrued"
    );
  }

  record InvalidInterestPerSecondForClose(int code, String msg) implements LoopscaleError {

    public static final InvalidInterestPerSecondForClose INSTANCE = new InvalidInterestPerSecondForClose(
        6077, "Invalid interest per second. Must be 0"
    );
  }

  record InvalidExternalYieldAmountForClose(int code, String msg) implements LoopscaleError {

    public static final InvalidExternalYieldAmountForClose INSTANCE = new InvalidExternalYieldAmountForClose(
        6078, "Invalid external yield amount. Must be 0"
    );
  }

  record InvalidCurrentDeployedAmountForClose(int code, String msg) implements LoopscaleError {

    public static final InvalidCurrentDeployedAmountForClose INSTANCE = new InvalidCurrentDeployedAmountForClose(
        6079, "Invalid current deployed amount. Must be 0"
    );
  }

  record InvalidTokenBalanceForClose(int code, String msg) implements LoopscaleError {

    public static final InvalidTokenBalanceForClose INSTANCE = new InvalidTokenBalanceForClose(
        6080, "Invalid token balance. Must be 0"
    );
  }

  record InvalidFeeClaimableForClose(int code, String msg) implements LoopscaleError {

    public static final InvalidFeeClaimableForClose INSTANCE = new InvalidFeeClaimableForClose(
        6081, "Invalid fee claimable. Must be 0"
    );
  }

  record InvalidLender(int code, String msg) implements LoopscaleError {

    public static final InvalidLender INSTANCE = new InvalidLender(
        6082, "Invalid lender"
    );
  }

  record SaleSlippageExceeded(int code, String msg) implements LoopscaleError {

    public static final SaleSlippageExceeded INSTANCE = new SaleSlippageExceeded(
        6083, "Sale slippage exceeded"
    );
  }

  record ExpectedLtvMismatch(int code, String msg) implements LoopscaleError {

    public static final ExpectedLtvMismatch INSTANCE = new ExpectedLtvMismatch(
        6084, "Expected LTV mismatch"
    );
  }

  record ExpectedLqtMismatch(int code, String msg) implements LoopscaleError {

    public static final ExpectedLqtMismatch INSTANCE = new ExpectedLqtMismatch(
        6085, "Expected LQT mismatch"
    );
  }

  record ExpectedApyMismatch(int code, String msg) implements LoopscaleError {

    public static final ExpectedApyMismatch INSTANCE = new ExpectedApyMismatch(
        6086, "Expected APY mismatch"
    );
  }

  record LpSlippageToleranceExceeded(int code, String msg) implements LoopscaleError {

    public static final LpSlippageToleranceExceeded INSTANCE = new LpSlippageToleranceExceeded(
        6087, "Lp slippage tolerance exceeded"
    );
  }

  record InvalidStartTime(int code, String msg) implements LoopscaleError {

    public static final InvalidStartTime INSTANCE = new InvalidStartTime(
        6088, "Invalid start time. Loan start time must be within 5 minutes of current time"
    );
  }

  record InvalidWeightMatrix(int code, String msg) implements LoopscaleError {

    public static final InvalidWeightMatrix INSTANCE = new InvalidWeightMatrix(
        6089, "Invalid weight matrix"
    );
  }

  record LoanPastEndTime(int code, String msg) implements LoopscaleError {

    public static final LoanPastEndTime INSTANCE = new LoanPastEndTime(
        6090, "Loan is past end time"
    );
  }

  record InvalidCollateralWithdrawalWeightMatrixAssignment(int code, String msg) implements LoopscaleError {

    public static final InvalidCollateralWithdrawalWeightMatrixAssignment INSTANCE = new InvalidCollateralWithdrawalWeightMatrixAssignment(
        6091, "Invalid collateral withdrawal weight matrix assignment"
    );
  }

  record TooMuchCollateralWithdrawn(int code, String msg) implements LoopscaleError {

    public static final TooMuchCollateralWithdrawn INSTANCE = new TooMuchCollateralWithdrawn(
        6092, "Too much collateral withdrawn"
    );
  }

  record InvalidPrincipalWithdrawalWeightMatrixAssignment(int code, String msg) implements LoopscaleError {

    public static final InvalidPrincipalWithdrawalWeightMatrixAssignment INSTANCE = new InvalidPrincipalWithdrawalWeightMatrixAssignment(
        6093, "Invalid principal withdrawal weight matrix assignment"
    );
  }

  record LedgerInRefinanceGracePeriodCannotBeWithdrawn(int code, String msg) implements LoopscaleError {

    public static final LedgerInRefinanceGracePeriodCannotBeWithdrawn INSTANCE = new LedgerInRefinanceGracePeriodCannotBeWithdrawn(
        6094, "Ledger in refinance grace period cannot be withdrawn"
    );
  }

  record OnlyBorrowerCanRefinanceBeforeEnd(int code, String msg) implements LoopscaleError {

    public static final OnlyBorrowerCanRefinanceBeforeEnd INSTANCE = new OnlyBorrowerCanRefinanceBeforeEnd(
        6095, "Only borrower can refinance before end"
    );
  }

  record InvalidDurationForLedgerSale(int code, String msg) implements LoopscaleError {

    public static final InvalidDurationForLedgerSale INSTANCE = new InvalidDurationForLedgerSale(
        6096, "Invalid duration for ledger sale"
    );
  }

  record StakedSolCurrentlyUnsupported(int code, String msg) implements LoopscaleError {

    public static final StakedSolCurrentlyUnsupported INSTANCE = new StakedSolCurrentlyUnsupported(
        6097, "Staked sol is currently unsupported"
    );
  }

  record LoanNotFullyRepaid(int code, String msg) implements LoopscaleError {

    public static final LoanNotFullyRepaid INSTANCE = new LoanNotFullyRepaid(
        6098, "Loan has not been fully repaid"
    );
  }

  record InvalidLiquidationThreshold(int code, String msg) implements LoopscaleError {

    public static final InvalidLiquidationThreshold INSTANCE = new InvalidLiquidationThreshold(
        6099, "Liquidation threshold must be >= ltv + buffer"
    );
  }

  record MaxAmountInExceeded(int code, String msg) implements LoopscaleError {

    public static final MaxAmountInExceeded INSTANCE = new MaxAmountInExceeded(
        6100, "Max amount in exceeded"
    );
  }

  record MinAmountOutNotMet(int code, String msg) implements LoopscaleError {

    public static final MinAmountOutNotMet INSTANCE = new MinAmountOutNotMet(
        6101, "Min amount out not met"
    );
  }

  record MissingAccount(int code, String msg) implements LoopscaleError {

    public static final MissingAccount INSTANCE = new MissingAccount(
        6102, "Missing account"
    );
  }

  record LQTWeightedCollateralValueGreaterThanTotalDebt(int code, String msg) implements LoopscaleError {

    public static final LQTWeightedCollateralValueGreaterThanTotalDebt INSTANCE = new LQTWeightedCollateralValueGreaterThanTotalDebt(
        6103, "LQT weighted collateral value is greater than total debt"
    );
  }

  record StrategyOriginationsDisabled(int code, String msg) implements LoopscaleError {

    public static final StrategyOriginationsDisabled INSTANCE = new StrategyOriginationsDisabled(
        6104, "Strategy originations are disabled"
    );
  }

  record TimelockDelayNotMet(int code, String msg) implements LoopscaleError {

    public static final TimelockDelayNotMet INSTANCE = new TimelockDelayNotMet(
        6105, "Timelock delay not met"
    );
  }

  record VaultDepositsDisabled(int code, String msg) implements LoopscaleError {

    public static final VaultDepositsDisabled INSTANCE = new VaultDepositsDisabled(
        6106, "Vault deposits are disabled"
    );
  }

  record InvalidLpParams(int code, String msg) implements LoopscaleError {

    public static final InvalidLpParams INSTANCE = new InvalidLpParams(
        6107, "Invalid LP params"
    );
  }

  record InvalidVaultAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidVaultAccount INSTANCE = new InvalidVaultAccount(
        6108, "Invalid Met Vault account"
    );
  }

  record InvalidStakeDuration(int code, String msg) implements LoopscaleError {

    public static final InvalidStakeDuration INSTANCE = new InvalidStakeDuration(
        6109, "Invalid stake duration"
    );
  }

  record EndTimeBeforeStartTime(int code, String msg) implements LoopscaleError {

    public static final EndTimeBeforeStartTime INSTANCE = new EndTimeBeforeStartTime(
        6110, "End time before start time"
    );
  }

  record InvalidRewardStartTime(int code, String msg) implements LoopscaleError {

    public static final InvalidRewardStartTime INSTANCE = new InvalidRewardStartTime(
        6111, "Invalid reward start time"
    );
  }

  record RewardsScheduleNotEligibleForClose(int code, String msg) implements LoopscaleError {

    public static final RewardsScheduleNotEligibleForClose INSTANCE = new RewardsScheduleNotEligibleForClose(
        6112, "Rewards schedule not eligible for close"
    );
  }

  record InvalidRewardEndTime(int code, String msg) implements LoopscaleError {

    public static final InvalidRewardEndTime INSTANCE = new InvalidRewardEndTime(
        6113, "Invalid reward end time"
    );
  }

  record NoAvailableRewardsSchedules(int code, String msg) implements LoopscaleError {

    public static final NoAvailableRewardsSchedules INSTANCE = new NoAvailableRewardsSchedules(
        6114, "No available rewards schedules"
    );
  }

  record NotEnoughRewardsTransferred(int code, String msg) implements LoopscaleError {

    public static final NotEnoughRewardsTransferred INSTANCE = new NotEnoughRewardsTransferred(
        6115, "Not enough rewards transferred"
    );
  }

  record InvalidCpiProgram(int code, String msg) implements LoopscaleError {

    public static final InvalidCpiProgram INSTANCE = new InvalidCpiProgram(
        6116, "Invalid CPI program"
    );
  }

  record InvalidRewardMint(int code, String msg) implements LoopscaleError {

    public static final InvalidRewardMint INSTANCE = new InvalidRewardMint(
        6117, "Invalid reward mint"
    );
  }

  record InvalidPerenaPoolData(int code, String msg) implements LoopscaleError {

    public static final InvalidPerenaPoolData INSTANCE = new InvalidPerenaPoolData(
        6118, "Invalid perena pool data"
    );
  }

  record PriceUnderflow(int code, String msg) implements LoopscaleError {

    public static final PriceUnderflow INSTANCE = new PriceUnderflow(
        6119, "Price underflow"
    );
  }

  record InvalidPriceData(int code, String msg) implements LoopscaleError {

    public static final InvalidPriceData INSTANCE = new InvalidPriceData(
        6120, "Invalid price data"
    );
  }

  record RemoveCollateralNotFullDisable(int code, String msg) implements LoopscaleError {

    public static final RemoveCollateralNotFullDisable INSTANCE = new RemoveCollateralNotFullDisable(
        6121, "Remove collateral timelock needs to be full disable"
    );
  }

  record LedgerStrategyMismatch(int code, String msg) implements LoopscaleError {

    public static final LedgerStrategyMismatch INSTANCE = new LedgerStrategyMismatch(
        6122, "Ledger strategy mismatch"
    );
  }

  record InvalidLiquidityAmount(int code, String msg) implements LoopscaleError {

    public static final InvalidLiquidityAmount INSTANCE = new InvalidLiquidityAmount(
        6123, "Invalid liquidity amount"
    );
  }

  record CannotUpdateVaultMarketInfo(int code, String msg) implements LoopscaleError {

    public static final CannotUpdateVaultMarketInfo INSTANCE = new CannotUpdateVaultMarketInfo(
        6124, "Cannot update vault market info"
    );
  }

  record InvalidRewardsMint(int code, String msg) implements LoopscaleError {

    public static final InvalidRewardsMint INSTANCE = new InvalidRewardsMint(
        6125, "Invalid rewards mint"
    );
  }

  record InvalidOracleAccountOwner(int code, String msg) implements LoopscaleError {

    public static final InvalidOracleAccountOwner INSTANCE = new InvalidOracleAccountOwner(
        6126, "Invalid oracle account owner"
    );
  }

  record InvalidMaxUncertainty(int code, String msg) implements LoopscaleError {

    public static final InvalidMaxUncertainty INSTANCE = new InvalidMaxUncertainty(
        6127, "Invalid max uncertainty"
    );
  }

  record InvalidMaxAge(int code, String msg) implements LoopscaleError {

    public static final InvalidMaxAge INSTANCE = new InvalidMaxAge(
        6128, "Invalid max age"
    );
  }

  record InvalidLtv(int code, String msg) implements LoopscaleError {

    public static final InvalidLtv INSTANCE = new InvalidLtv(
        6129, "Invalid ltv"
    );
  }

  record UnsupportedOracleType(int code, String msg) implements LoopscaleError {

    public static final UnsupportedOracleType INSTANCE = new UnsupportedOracleType(
        6130, "Unsupported oracle type"
    );
  }

  record InvalidQuoteMint(int code, String msg) implements LoopscaleError {

    public static final InvalidQuoteMint INSTANCE = new InvalidQuoteMint(
        6131, "Invalid quote mint"
    );
  }

  record TotalWeightedStakeSupplyZero(int code, String msg) implements LoopscaleError {

    public static final TotalWeightedStakeSupplyZero INSTANCE = new TotalWeightedStakeSupplyZero(
        6132, "Total weighted stake supply is zero"
    );
  }

  record TransferFeeNotSupported(int code, String msg) implements LoopscaleError {

    public static final TransferFeeNotSupported INSTANCE = new TransferFeeNotSupported(
        6133, "Transfer fee is not supported"
    );
  }

  record TransferHookNotSupported(int code, String msg) implements LoopscaleError {

    public static final TransferHookNotSupported INSTANCE = new TransferHookNotSupported(
        6134, "Transfer hook is not supported"
    );
  }

  record InvalidAuthority(int code, String msg) implements LoopscaleError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        6135, "Invalid authority"
    );
  }

  record InvalidAssetMint(int code, String msg) implements LoopscaleError {

    public static final InvalidAssetMint INSTANCE = new InvalidAssetMint(
        6136, "Invalid asset mint"
    );
  }

  record PythPriceVerificationLevelTooLow(int code, String msg) implements LoopscaleError {

    public static final PythPriceVerificationLevelTooLow INSTANCE = new PythPriceVerificationLevelTooLow(
        6137, "Pyth Price verification level too low"
    );
  }

  record InvalidInputAmount(int code, String msg) implements LoopscaleError {

    public static final InvalidInputAmount INSTANCE = new InvalidInputAmount(
        6138, "Invalid input amount"
    );
  }

  record InvalidCpiAccount(int code, String msg) implements LoopscaleError {

    public static final InvalidCpiAccount INSTANCE = new InvalidCpiAccount(
        6139, "Invalid cpi account"
    );
  }

  record CollateralAllocationCapExceeded(int code, String msg) implements LoopscaleError {

    public static final CollateralAllocationCapExceeded INSTANCE = new CollateralAllocationCapExceeded(
        6140, "Collateral allocation cap exceeded"
    );
  }

  record OneHourPrincipalCapExceeded(int code, String msg) implements LoopscaleError {

    public static final OneHourPrincipalCapExceeded INSTANCE = new OneHourPrincipalCapExceeded(
        6141, "1 Hour principal cap exceeded"
    );
  }

  record TwentyFourHourPrincipalCapExceeded(int code, String msg) implements LoopscaleError {

    public static final TwentyFourHourPrincipalCapExceeded INSTANCE = new TwentyFourHourPrincipalCapExceeded(
        6142, "24 Hour principal cap exceeded"
    );
  }

  record OutstandingPrincipalCapExceeded(int code, String msg) implements LoopscaleError {

    public static final OutstandingPrincipalCapExceeded INSTANCE = new OutstandingPrincipalCapExceeded(
        6143, "Outstanding principal cap exceeded"
    );
  }

  record NoCapsToUpdateFoundOnTimelock(int code, String msg) implements LoopscaleError {

    public static final NoCapsToUpdateFoundOnTimelock INSTANCE = new NoCapsToUpdateFoundOnTimelock(
        6144, "No Caps to update found on timelock"
    );
  }

  record IllegalLoanLock(int code, String msg) implements LoopscaleError {

    public static final IllegalLoanLock INSTANCE = new IllegalLoanLock(
        6145, "Illegal loan lock"
    );
  }

  record InvalidLoanStatus(int code, String msg) implements LoopscaleError {

    public static final InvalidLoanStatus INSTANCE = new InvalidLoanStatus(
        6146, "Invalid loan type"
    );
  }

  record LoanNotEligibleForClose(int code, String msg) implements LoopscaleError {

    public static final LoanNotEligibleForClose INSTANCE = new LoanNotEligibleForClose(
        6147, "Loan is not eligible for close"
    );
  }

  record UnsupportedAction(int code, String msg) implements LoopscaleError {

    public static final UnsupportedAction INSTANCE = new UnsupportedAction(
        6148, "Invalid action"
    );
  }

  record ZeroPrice(int code, String msg) implements LoopscaleError {

    public static final ZeroPrice INSTANCE = new ZeroPrice(
        6149, "Zero price"
    );
  }
}
