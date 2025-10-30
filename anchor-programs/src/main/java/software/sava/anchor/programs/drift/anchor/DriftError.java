package software.sava.anchor.programs.drift.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface DriftError extends ProgramError permits
    DriftError.InvalidSpotMarketAuthority,
    DriftError.InvalidInsuranceFundAuthority,
    DriftError.InsufficientDeposit,
    DriftError.InsufficientCollateral,
    DriftError.SufficientCollateral,
    DriftError.MaxNumberOfPositions,
    DriftError.AdminControlsPricesDisabled,
    DriftError.MarketDelisted,
    DriftError.MarketIndexAlreadyInitialized,
    DriftError.UserAccountAndUserPositionsAccountMismatch,
    DriftError.UserHasNoPositionInMarket,
    DriftError.InvalidInitialPeg,
    DriftError.InvalidRepegRedundant,
    DriftError.InvalidRepegDirection,
    DriftError.InvalidRepegProfitability,
    DriftError.SlippageOutsideLimit,
    DriftError.OrderSizeTooSmall,
    DriftError.InvalidUpdateK,
    DriftError.AdminWithdrawTooLarge,
    DriftError.MathError,
    DriftError.BnConversionError,
    DriftError.ClockUnavailable,
    DriftError.UnableToLoadOracle,
    DriftError.PriceBandsBreached,
    DriftError.ExchangePaused,
    DriftError.InvalidWhitelistToken,
    DriftError.WhitelistTokenNotFound,
    DriftError.InvalidDiscountToken,
    DriftError.DiscountTokenNotFound,
    DriftError.ReferrerNotFound,
    DriftError.ReferrerStatsNotFound,
    DriftError.ReferrerMustBeWritable,
    DriftError.ReferrerStatsMustBeWritable,
    DriftError.ReferrerAndReferrerStatsAuthorityUnequal,
    DriftError.InvalidReferrer,
    DriftError.InvalidOracle,
    DriftError.OracleNotFound,
    DriftError.LiquidationsBlockedByOracle,
    DriftError.MaxDeposit,
    DriftError.CantDeleteUserWithCollateral,
    DriftError.InvalidFundingProfitability,
    DriftError.CastingFailure,
    DriftError.InvalidOrder,
    DriftError.InvalidOrderMaxTs,
    DriftError.InvalidOrderMarketType,
    DriftError.InvalidOrderForInitialMarginReq,
    DriftError.InvalidOrderNotRiskReducing,
    DriftError.InvalidOrderSizeTooSmall,
    DriftError.InvalidOrderNotStepSizeMultiple,
    DriftError.InvalidOrderBaseQuoteAsset,
    DriftError.InvalidOrderIOC,
    DriftError.InvalidOrderPostOnly,
    DriftError.InvalidOrderIOCPostOnly,
    DriftError.InvalidOrderTrigger,
    DriftError.InvalidOrderAuction,
    DriftError.InvalidOrderOracleOffset,
    DriftError.InvalidOrderMinOrderSize,
    DriftError.PlacePostOnlyLimitFailure,
    DriftError.UserHasNoOrder,
    DriftError.OrderAmountTooSmall,
    DriftError.MaxNumberOfOrders,
    DriftError.OrderDoesNotExist,
    DriftError.OrderNotOpen,
    DriftError.FillOrderDidNotUpdateState,
    DriftError.ReduceOnlyOrderIncreasedRisk,
    DriftError.UnableToLoadAccountLoader,
    DriftError.TradeSizeTooLarge,
    DriftError.UserCantReferThemselves,
    DriftError.DidNotReceiveExpectedReferrer,
    DriftError.CouldNotDeserializeReferrer,
    DriftError.CouldNotDeserializeReferrerStats,
    DriftError.UserOrderIdAlreadyInUse,
    DriftError.NoPositionsLiquidatable,
    DriftError.InvalidMarginRatio,
    DriftError.CantCancelPostOnlyOrder,
    DriftError.InvalidOracleOffset,
    DriftError.CantExpireOrders,
    DriftError.CouldNotLoadMarketData,
    DriftError.PerpMarketNotFound,
    DriftError.InvalidMarketAccount,
    DriftError.UnableToLoadPerpMarketAccount,
    DriftError.MarketWrongMutability,
    DriftError.UnableToCastUnixTime,
    DriftError.CouldNotFindSpotPosition,
    DriftError.NoSpotPositionAvailable,
    DriftError.InvalidSpotMarketInitialization,
    DriftError.CouldNotLoadSpotMarketData,
    DriftError.SpotMarketNotFound,
    DriftError.InvalidSpotMarketAccount,
    DriftError.UnableToLoadSpotMarketAccount,
    DriftError.SpotMarketWrongMutability,
    DriftError.SpotMarketInterestNotUpToDate,
    DriftError.SpotMarketInsufficientDeposits,
    DriftError.UserMustSettleTheirOwnPositiveUnsettledPNL,
    DriftError.CantUpdatePoolBalanceType,
    DriftError.InsufficientCollateralForSettlingPNL,
    DriftError.AMMNotUpdatedInSameSlot,
    DriftError.AuctionNotComplete,
    DriftError.MakerNotFound,
    DriftError.MakerStatsNotFound,
    DriftError.MakerMustBeWritable,
    DriftError.MakerStatsMustBeWritable,
    DriftError.MakerOrderNotFound,
    DriftError.CouldNotDeserializeMaker,
    DriftError.CouldNotDeserializeMakerStats,
    DriftError.AuctionPriceDoesNotSatisfyMaker,
    DriftError.MakerCantFulfillOwnOrder,
    DriftError.MakerOrderMustBePostOnly,
    DriftError.CantMatchTwoPostOnlys,
    DriftError.OrderBreachesOraclePriceLimits,
    DriftError.OrderMustBeTriggeredFirst,
    DriftError.OrderNotTriggerable,
    DriftError.OrderDidNotSatisfyTriggerCondition,
    DriftError.PositionAlreadyBeingLiquidated,
    DriftError.PositionDoesntHaveOpenPositionOrOrders,
    DriftError.AllOrdersAreAlreadyLiquidations,
    DriftError.CantCancelLiquidationOrder,
    DriftError.UserIsBeingLiquidated,
    DriftError.LiquidationsOngoing,
    DriftError.WrongSpotBalanceType,
    DriftError.UserCantLiquidateThemself,
    DriftError.InvalidPerpPositionToLiquidate,
    DriftError.InvalidBaseAssetAmountForLiquidatePerp,
    DriftError.InvalidPositionLastFundingRate,
    DriftError.InvalidPositionDelta,
    DriftError.UserBankrupt,
    DriftError.UserNotBankrupt,
    DriftError.UserHasInvalidBorrow,
    DriftError.DailyWithdrawLimit,
    DriftError.DefaultError,
    DriftError.InsufficientLPTokens,
    DriftError.CantLPWithPerpPosition,
    DriftError.UnableToBurnLPTokens,
    DriftError.TryingToRemoveLiquidityTooFast,
    DriftError.InvalidSpotMarketVault,
    DriftError.InvalidSpotMarketState,
    DriftError.InvalidSerumProgram,
    DriftError.InvalidSerumMarket,
    DriftError.InvalidSerumBids,
    DriftError.InvalidSerumAsks,
    DriftError.InvalidSerumOpenOrders,
    DriftError.FailedSerumCPI,
    DriftError.FailedToFillOnExternalMarket,
    DriftError.InvalidFulfillmentConfig,
    DriftError.InvalidFeeStructure,
    DriftError.InsufficientIFShares,
    DriftError.MarketActionPaused,
    DriftError.MarketPlaceOrderPaused,
    DriftError.MarketFillOrderPaused,
    DriftError.MarketWithdrawPaused,
    DriftError.ProtectedAssetTierViolation,
    DriftError.IsolatedAssetTierViolation,
    DriftError.UserCantBeDeleted,
    DriftError.ReduceOnlyWithdrawIncreasedRisk,
    DriftError.MaxOpenInterest,
    DriftError.CantResolvePerpBankruptcy,
    DriftError.LiquidationDoesntSatisfyLimitPrice,
    DriftError.MarginTradingDisabled,
    DriftError.InvalidMarketStatusToSettlePnl,
    DriftError.PerpMarketNotInSettlement,
    DriftError.PerpMarketNotInReduceOnly,
    DriftError.PerpMarketSettlementBufferNotReached,
    DriftError.PerpMarketSettlementUserHasOpenOrders,
    DriftError.PerpMarketSettlementUserHasActiveLP,
    DriftError.UnableToSettleExpiredUserPosition,
    DriftError.UnequalMarketIndexForSpotTransfer,
    DriftError.InvalidPerpPositionDetected,
    DriftError.InvalidSpotPositionDetected,
    DriftError.InvalidAmmDetected,
    DriftError.InvalidAmmForFillDetected,
    DriftError.InvalidAmmLimitPriceOverride,
    DriftError.InvalidOrderFillPrice,
    DriftError.SpotMarketBalanceInvariantViolated,
    DriftError.SpotMarketVaultInvariantViolated,
    DriftError.InvalidPDA,
    DriftError.InvalidPDASigner,
    DriftError.RevenueSettingsCannotSettleToIF,
    DriftError.NoRevenueToSettleToIF,
    DriftError.NoAmmPerpPnlDeficit,
    DriftError.SufficientPerpPnlPool,
    DriftError.InsufficientPerpPnlPool,
    DriftError.PerpPnlDeficitBelowThreshold,
    DriftError.MaxRevenueWithdrawPerPeriodReached,
    DriftError.MaxIFWithdrawReached,
    DriftError.NoIFWithdrawAvailable,
    DriftError.InvalidIFUnstake,
    DriftError.InvalidIFUnstakeSize,
    DriftError.InvalidIFUnstakeCancel,
    DriftError.InvalidIFForNewStakes,
    DriftError.InvalidIFRebase,
    DriftError.InvalidInsuranceUnstakeSize,
    DriftError.InvalidOrderLimitPrice,
    DriftError.InvalidIFDetected,
    DriftError.InvalidAmmMaxSpreadDetected,
    DriftError.InvalidConcentrationCoef,
    DriftError.InvalidSrmVault,
    DriftError.InvalidVaultOwner,
    DriftError.InvalidMarketStatusForFills,
    DriftError.IFWithdrawRequestInProgress,
    DriftError.NoIFWithdrawRequestInProgress,
    DriftError.IFWithdrawRequestTooSmall,
    DriftError.IncorrectSpotMarketAccountPassed,
    DriftError.BlockchainClockInconsistency,
    DriftError.InvalidIFSharesDetected,
    DriftError.NewLPSizeTooSmall,
    DriftError.MarketStatusInvalidForNewLP,
    DriftError.InvalidMarkTwapUpdateDetected,
    DriftError.MarketSettlementAttemptOnActiveMarket,
    DriftError.MarketSettlementRequiresSettledLP,
    DriftError.MarketSettlementAttemptTooEarly,
    DriftError.MarketSettlementTargetPriceInvalid,
    DriftError.UnsupportedSpotMarket,
    DriftError.SpotOrdersDisabled,
    DriftError.MarketBeingInitialized,
    DriftError.InvalidUserSubAccountId,
    DriftError.InvalidTriggerOrderCondition,
    DriftError.InvalidSpotPosition,
    DriftError.CantTransferBetweenSameUserAccount,
    DriftError.InvalidPerpPosition,
    DriftError.UnableToGetLimitPrice,
    DriftError.InvalidLiquidation,
    DriftError.SpotFulfillmentConfigDisabled,
    DriftError.InvalidMaker,
    DriftError.FailedUnwrap,
    DriftError.MaxNumberOfUsers,
    DriftError.InvalidOracleForSettlePnl,
    DriftError.MarginOrdersOpen,
    DriftError.TierViolationLiquidatingPerpPnl,
    DriftError.CouldNotLoadUserData,
    DriftError.UserWrongMutability,
    DriftError.InvalidUserAccount,
    DriftError.CouldNotLoadUserStatsData,
    DriftError.UserStatsWrongMutability,
    DriftError.InvalidUserStatsAccount,
    DriftError.UserNotFound,
    DriftError.UnableToLoadUserAccount,
    DriftError.UserStatsNotFound,
    DriftError.UnableToLoadUserStatsAccount,
    DriftError.UserNotInactive,
    DriftError.RevertFill,
    DriftError.InvalidMarketAccountforDeletion,
    DriftError.InvalidSpotFulfillmentParams,
    DriftError.FailedToGetMint,
    DriftError.FailedPhoenixCPI,
    DriftError.FailedToDeserializePhoenixMarket,
    DriftError.InvalidPricePrecision,
    DriftError.InvalidPhoenixProgram,
    DriftError.InvalidPhoenixMarket,
    DriftError.InvalidSwap,
    DriftError.SwapLimitPriceBreached,
    DriftError.SpotMarketReduceOnly,
    DriftError.FundingWasNotUpdated,
    DriftError.ImpossibleFill,
    DriftError.CantUpdatePerpBidAskTwap,
    DriftError.UserReduceOnly,
    DriftError.InvalidMarginCalculation,
    DriftError.CantPayUserInitFee,
    DriftError.CantReclaimRent,
    DriftError.InsuranceFundOperationPaused,
    DriftError.NoUnsettledPnl,
    DriftError.PnlPoolCantSettleUser,
    DriftError.OracleNonPositive,
    DriftError.OracleTooVolatile,
    DriftError.OracleTooUncertain,
    DriftError.OracleStaleForMargin,
    DriftError.OracleInsufficientDataPoints,
    DriftError.OracleStaleForAMM,
    DriftError.UnableToParsePullOracleMessage,
    DriftError.MaxBorrows,
    DriftError.OracleUpdatesNotMonotonic,
    DriftError.OraclePriceFeedMessageMismatch,
    DriftError.OracleUnsupportedMessageType,
    DriftError.OracleDeserializeMessageFailed,
    DriftError.OracleWrongGuardianSetOwner,
    DriftError.OracleWrongWriteAuthority,
    DriftError.OracleWrongVaaOwner,
    DriftError.OracleTooManyPriceAccountUpdates,
    DriftError.OracleMismatchedVaaAndPriceUpdates,
    DriftError.OracleBadRemainingAccountPublicKey,
    DriftError.FailedOpenbookV2CPI,
    DriftError.InvalidOpenbookV2Program,
    DriftError.InvalidOpenbookV2Market,
    DriftError.NonZeroTransferFee,
    DriftError.LiquidationOrderFailedToFill,
    DriftError.InvalidPredictionMarketOrder,
    DriftError.InvalidVerificationIxIndex,
    DriftError.SigVerificationFailed,
    DriftError.MismatchedSignedMsgOrderParamsMarketIndex,
    DriftError.InvalidSignedMsgOrderParam,
    DriftError.PlaceAndTakeOrderSuccessConditionFailed,
    DriftError.InvalidHighLeverageModeConfig,
    DriftError.InvalidRFQUserAccount,
    DriftError.RFQUserAccountWrongMutability,
    DriftError.RFQUserAccountFull,
    DriftError.RFQOrderNotFilled,
    DriftError.InvalidRFQOrder,
    DriftError.InvalidRFQMatch,
    DriftError.InvalidSignedMsgUserAccount,
    DriftError.SignedMsgUserAccountWrongMutability,
    DriftError.SignedMsgUserOrdersAccountFull,
    DriftError.SignedMsgOrderDoesNotExist,
    DriftError.InvalidSignedMsgOrderId,
    DriftError.InvalidPoolId,
    DriftError.InvalidProtectedMakerModeConfig,
    DriftError.InvalidPythLazerStorageOwner,
    DriftError.UnverifiedPythLazerMessage,
    DriftError.InvalidPythLazerMessage,
    DriftError.PythLazerMessagePriceFeedMismatch,
    DriftError.InvalidLiquidateSpotWithSwap,
    DriftError.SignedMsgUserContextUserMismatch,
    DriftError.UserFuelOverflowThresholdNotMet,
    DriftError.FuelOverflowAccountNotFound,
    DriftError.InvalidTransferPerpPosition,
    DriftError.InvalidSignedMsgUserOrdersResize,
    DriftError.CouldNotDeserializeHighLeverageModeConfig,
    DriftError.InvalidIfRebalanceConfig,
    DriftError.InvalidIfRebalanceSwap,
    DriftError.InvalidRevenueShareResize,
    DriftError.BuilderRevoked,
    DriftError.InvalidBuilderFee,
    DriftError.RevenueShareEscrowAuthorityMismatch,
    DriftError.RevenueShareEscrowOrdersAccountFull,
    DriftError.InvalidRevenueShareAccount,
    DriftError.CannotRevokeBuilderWithOpenOrders,
    DriftError.UnableToLoadRevenueShareAccount,
    DriftError.InvalidConstituent,
    DriftError.InvalidAmmConstituentMappingArgument,
    DriftError.ConstituentNotFound,
    DriftError.ConstituentCouldNotLoad,
    DriftError.ConstituentWrongMutability,
    DriftError.WrongNumberOfConstituents,
    DriftError.InsufficientConstituentTokenBalance,
    DriftError.AMMCacheStale,
    DriftError.LpPoolAumDelayed,
    DriftError.ConstituentOracleStale,
    DriftError.LpInvariantFailed,
    DriftError.InvalidConstituentDerivativeWeights,
    DriftError.MaxDlpAumBreached,
    DriftError.SettleLpPoolDisabled,
    DriftError.MintRedeemLpPoolDisabled,
    DriftError.LpPoolSettleInvariantBreached,
    DriftError.InvalidConstituentOperation,
    DriftError.Unauthorized,
    DriftError.InvalidLpPoolId {

  static DriftError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidSpotMarketAuthority.INSTANCE;
      case 6001 -> InvalidInsuranceFundAuthority.INSTANCE;
      case 6002 -> InsufficientDeposit.INSTANCE;
      case 6003 -> InsufficientCollateral.INSTANCE;
      case 6004 -> SufficientCollateral.INSTANCE;
      case 6005 -> MaxNumberOfPositions.INSTANCE;
      case 6006 -> AdminControlsPricesDisabled.INSTANCE;
      case 6007 -> MarketDelisted.INSTANCE;
      case 6008 -> MarketIndexAlreadyInitialized.INSTANCE;
      case 6009 -> UserAccountAndUserPositionsAccountMismatch.INSTANCE;
      case 6010 -> UserHasNoPositionInMarket.INSTANCE;
      case 6011 -> InvalidInitialPeg.INSTANCE;
      case 6012 -> InvalidRepegRedundant.INSTANCE;
      case 6013 -> InvalidRepegDirection.INSTANCE;
      case 6014 -> InvalidRepegProfitability.INSTANCE;
      case 6015 -> SlippageOutsideLimit.INSTANCE;
      case 6016 -> OrderSizeTooSmall.INSTANCE;
      case 6017 -> InvalidUpdateK.INSTANCE;
      case 6018 -> AdminWithdrawTooLarge.INSTANCE;
      case 6019 -> MathError.INSTANCE;
      case 6020 -> BnConversionError.INSTANCE;
      case 6021 -> ClockUnavailable.INSTANCE;
      case 6022 -> UnableToLoadOracle.INSTANCE;
      case 6023 -> PriceBandsBreached.INSTANCE;
      case 6024 -> ExchangePaused.INSTANCE;
      case 6025 -> InvalidWhitelistToken.INSTANCE;
      case 6026 -> WhitelistTokenNotFound.INSTANCE;
      case 6027 -> InvalidDiscountToken.INSTANCE;
      case 6028 -> DiscountTokenNotFound.INSTANCE;
      case 6029 -> ReferrerNotFound.INSTANCE;
      case 6030 -> ReferrerStatsNotFound.INSTANCE;
      case 6031 -> ReferrerMustBeWritable.INSTANCE;
      case 6032 -> ReferrerStatsMustBeWritable.INSTANCE;
      case 6033 -> ReferrerAndReferrerStatsAuthorityUnequal.INSTANCE;
      case 6034 -> InvalidReferrer.INSTANCE;
      case 6035 -> InvalidOracle.INSTANCE;
      case 6036 -> OracleNotFound.INSTANCE;
      case 6037 -> LiquidationsBlockedByOracle.INSTANCE;
      case 6038 -> MaxDeposit.INSTANCE;
      case 6039 -> CantDeleteUserWithCollateral.INSTANCE;
      case 6040 -> InvalidFundingProfitability.INSTANCE;
      case 6041 -> CastingFailure.INSTANCE;
      case 6042 -> InvalidOrder.INSTANCE;
      case 6043 -> InvalidOrderMaxTs.INSTANCE;
      case 6044 -> InvalidOrderMarketType.INSTANCE;
      case 6045 -> InvalidOrderForInitialMarginReq.INSTANCE;
      case 6046 -> InvalidOrderNotRiskReducing.INSTANCE;
      case 6047 -> InvalidOrderSizeTooSmall.INSTANCE;
      case 6048 -> InvalidOrderNotStepSizeMultiple.INSTANCE;
      case 6049 -> InvalidOrderBaseQuoteAsset.INSTANCE;
      case 6050 -> InvalidOrderIOC.INSTANCE;
      case 6051 -> InvalidOrderPostOnly.INSTANCE;
      case 6052 -> InvalidOrderIOCPostOnly.INSTANCE;
      case 6053 -> InvalidOrderTrigger.INSTANCE;
      case 6054 -> InvalidOrderAuction.INSTANCE;
      case 6055 -> InvalidOrderOracleOffset.INSTANCE;
      case 6056 -> InvalidOrderMinOrderSize.INSTANCE;
      case 6057 -> PlacePostOnlyLimitFailure.INSTANCE;
      case 6058 -> UserHasNoOrder.INSTANCE;
      case 6059 -> OrderAmountTooSmall.INSTANCE;
      case 6060 -> MaxNumberOfOrders.INSTANCE;
      case 6061 -> OrderDoesNotExist.INSTANCE;
      case 6062 -> OrderNotOpen.INSTANCE;
      case 6063 -> FillOrderDidNotUpdateState.INSTANCE;
      case 6064 -> ReduceOnlyOrderIncreasedRisk.INSTANCE;
      case 6065 -> UnableToLoadAccountLoader.INSTANCE;
      case 6066 -> TradeSizeTooLarge.INSTANCE;
      case 6067 -> UserCantReferThemselves.INSTANCE;
      case 6068 -> DidNotReceiveExpectedReferrer.INSTANCE;
      case 6069 -> CouldNotDeserializeReferrer.INSTANCE;
      case 6070 -> CouldNotDeserializeReferrerStats.INSTANCE;
      case 6071 -> UserOrderIdAlreadyInUse.INSTANCE;
      case 6072 -> NoPositionsLiquidatable.INSTANCE;
      case 6073 -> InvalidMarginRatio.INSTANCE;
      case 6074 -> CantCancelPostOnlyOrder.INSTANCE;
      case 6075 -> InvalidOracleOffset.INSTANCE;
      case 6076 -> CantExpireOrders.INSTANCE;
      case 6077 -> CouldNotLoadMarketData.INSTANCE;
      case 6078 -> PerpMarketNotFound.INSTANCE;
      case 6079 -> InvalidMarketAccount.INSTANCE;
      case 6080 -> UnableToLoadPerpMarketAccount.INSTANCE;
      case 6081 -> MarketWrongMutability.INSTANCE;
      case 6082 -> UnableToCastUnixTime.INSTANCE;
      case 6083 -> CouldNotFindSpotPosition.INSTANCE;
      case 6084 -> NoSpotPositionAvailable.INSTANCE;
      case 6085 -> InvalidSpotMarketInitialization.INSTANCE;
      case 6086 -> CouldNotLoadSpotMarketData.INSTANCE;
      case 6087 -> SpotMarketNotFound.INSTANCE;
      case 6088 -> InvalidSpotMarketAccount.INSTANCE;
      case 6089 -> UnableToLoadSpotMarketAccount.INSTANCE;
      case 6090 -> SpotMarketWrongMutability.INSTANCE;
      case 6091 -> SpotMarketInterestNotUpToDate.INSTANCE;
      case 6092 -> SpotMarketInsufficientDeposits.INSTANCE;
      case 6093 -> UserMustSettleTheirOwnPositiveUnsettledPNL.INSTANCE;
      case 6094 -> CantUpdatePoolBalanceType.INSTANCE;
      case 6095 -> InsufficientCollateralForSettlingPNL.INSTANCE;
      case 6096 -> AMMNotUpdatedInSameSlot.INSTANCE;
      case 6097 -> AuctionNotComplete.INSTANCE;
      case 6098 -> MakerNotFound.INSTANCE;
      case 6099 -> MakerStatsNotFound.INSTANCE;
      case 6100 -> MakerMustBeWritable.INSTANCE;
      case 6101 -> MakerStatsMustBeWritable.INSTANCE;
      case 6102 -> MakerOrderNotFound.INSTANCE;
      case 6103 -> CouldNotDeserializeMaker.INSTANCE;
      case 6104 -> CouldNotDeserializeMakerStats.INSTANCE;
      case 6105 -> AuctionPriceDoesNotSatisfyMaker.INSTANCE;
      case 6106 -> MakerCantFulfillOwnOrder.INSTANCE;
      case 6107 -> MakerOrderMustBePostOnly.INSTANCE;
      case 6108 -> CantMatchTwoPostOnlys.INSTANCE;
      case 6109 -> OrderBreachesOraclePriceLimits.INSTANCE;
      case 6110 -> OrderMustBeTriggeredFirst.INSTANCE;
      case 6111 -> OrderNotTriggerable.INSTANCE;
      case 6112 -> OrderDidNotSatisfyTriggerCondition.INSTANCE;
      case 6113 -> PositionAlreadyBeingLiquidated.INSTANCE;
      case 6114 -> PositionDoesntHaveOpenPositionOrOrders.INSTANCE;
      case 6115 -> AllOrdersAreAlreadyLiquidations.INSTANCE;
      case 6116 -> CantCancelLiquidationOrder.INSTANCE;
      case 6117 -> UserIsBeingLiquidated.INSTANCE;
      case 6118 -> LiquidationsOngoing.INSTANCE;
      case 6119 -> WrongSpotBalanceType.INSTANCE;
      case 6120 -> UserCantLiquidateThemself.INSTANCE;
      case 6121 -> InvalidPerpPositionToLiquidate.INSTANCE;
      case 6122 -> InvalidBaseAssetAmountForLiquidatePerp.INSTANCE;
      case 6123 -> InvalidPositionLastFundingRate.INSTANCE;
      case 6124 -> InvalidPositionDelta.INSTANCE;
      case 6125 -> UserBankrupt.INSTANCE;
      case 6126 -> UserNotBankrupt.INSTANCE;
      case 6127 -> UserHasInvalidBorrow.INSTANCE;
      case 6128 -> DailyWithdrawLimit.INSTANCE;
      case 6129 -> DefaultError.INSTANCE;
      case 6130 -> InsufficientLPTokens.INSTANCE;
      case 6131 -> CantLPWithPerpPosition.INSTANCE;
      case 6132 -> UnableToBurnLPTokens.INSTANCE;
      case 6133 -> TryingToRemoveLiquidityTooFast.INSTANCE;
      case 6134 -> InvalidSpotMarketVault.INSTANCE;
      case 6135 -> InvalidSpotMarketState.INSTANCE;
      case 6136 -> InvalidSerumProgram.INSTANCE;
      case 6137 -> InvalidSerumMarket.INSTANCE;
      case 6138 -> InvalidSerumBids.INSTANCE;
      case 6139 -> InvalidSerumAsks.INSTANCE;
      case 6140 -> InvalidSerumOpenOrders.INSTANCE;
      case 6141 -> FailedSerumCPI.INSTANCE;
      case 6142 -> FailedToFillOnExternalMarket.INSTANCE;
      case 6143 -> InvalidFulfillmentConfig.INSTANCE;
      case 6144 -> InvalidFeeStructure.INSTANCE;
      case 6145 -> InsufficientIFShares.INSTANCE;
      case 6146 -> MarketActionPaused.INSTANCE;
      case 6147 -> MarketPlaceOrderPaused.INSTANCE;
      case 6148 -> MarketFillOrderPaused.INSTANCE;
      case 6149 -> MarketWithdrawPaused.INSTANCE;
      case 6150 -> ProtectedAssetTierViolation.INSTANCE;
      case 6151 -> IsolatedAssetTierViolation.INSTANCE;
      case 6152 -> UserCantBeDeleted.INSTANCE;
      case 6153 -> ReduceOnlyWithdrawIncreasedRisk.INSTANCE;
      case 6154 -> MaxOpenInterest.INSTANCE;
      case 6155 -> CantResolvePerpBankruptcy.INSTANCE;
      case 6156 -> LiquidationDoesntSatisfyLimitPrice.INSTANCE;
      case 6157 -> MarginTradingDisabled.INSTANCE;
      case 6158 -> InvalidMarketStatusToSettlePnl.INSTANCE;
      case 6159 -> PerpMarketNotInSettlement.INSTANCE;
      case 6160 -> PerpMarketNotInReduceOnly.INSTANCE;
      case 6161 -> PerpMarketSettlementBufferNotReached.INSTANCE;
      case 6162 -> PerpMarketSettlementUserHasOpenOrders.INSTANCE;
      case 6163 -> PerpMarketSettlementUserHasActiveLP.INSTANCE;
      case 6164 -> UnableToSettleExpiredUserPosition.INSTANCE;
      case 6165 -> UnequalMarketIndexForSpotTransfer.INSTANCE;
      case 6166 -> InvalidPerpPositionDetected.INSTANCE;
      case 6167 -> InvalidSpotPositionDetected.INSTANCE;
      case 6168 -> InvalidAmmDetected.INSTANCE;
      case 6169 -> InvalidAmmForFillDetected.INSTANCE;
      case 6170 -> InvalidAmmLimitPriceOverride.INSTANCE;
      case 6171 -> InvalidOrderFillPrice.INSTANCE;
      case 6172 -> SpotMarketBalanceInvariantViolated.INSTANCE;
      case 6173 -> SpotMarketVaultInvariantViolated.INSTANCE;
      case 6174 -> InvalidPDA.INSTANCE;
      case 6175 -> InvalidPDASigner.INSTANCE;
      case 6176 -> RevenueSettingsCannotSettleToIF.INSTANCE;
      case 6177 -> NoRevenueToSettleToIF.INSTANCE;
      case 6178 -> NoAmmPerpPnlDeficit.INSTANCE;
      case 6179 -> SufficientPerpPnlPool.INSTANCE;
      case 6180 -> InsufficientPerpPnlPool.INSTANCE;
      case 6181 -> PerpPnlDeficitBelowThreshold.INSTANCE;
      case 6182 -> MaxRevenueWithdrawPerPeriodReached.INSTANCE;
      case 6183 -> MaxIFWithdrawReached.INSTANCE;
      case 6184 -> NoIFWithdrawAvailable.INSTANCE;
      case 6185 -> InvalidIFUnstake.INSTANCE;
      case 6186 -> InvalidIFUnstakeSize.INSTANCE;
      case 6187 -> InvalidIFUnstakeCancel.INSTANCE;
      case 6188 -> InvalidIFForNewStakes.INSTANCE;
      case 6189 -> InvalidIFRebase.INSTANCE;
      case 6190 -> InvalidInsuranceUnstakeSize.INSTANCE;
      case 6191 -> InvalidOrderLimitPrice.INSTANCE;
      case 6192 -> InvalidIFDetected.INSTANCE;
      case 6193 -> InvalidAmmMaxSpreadDetected.INSTANCE;
      case 6194 -> InvalidConcentrationCoef.INSTANCE;
      case 6195 -> InvalidSrmVault.INSTANCE;
      case 6196 -> InvalidVaultOwner.INSTANCE;
      case 6197 -> InvalidMarketStatusForFills.INSTANCE;
      case 6198 -> IFWithdrawRequestInProgress.INSTANCE;
      case 6199 -> NoIFWithdrawRequestInProgress.INSTANCE;
      case 6200 -> IFWithdrawRequestTooSmall.INSTANCE;
      case 6201 -> IncorrectSpotMarketAccountPassed.INSTANCE;
      case 6202 -> BlockchainClockInconsistency.INSTANCE;
      case 6203 -> InvalidIFSharesDetected.INSTANCE;
      case 6204 -> NewLPSizeTooSmall.INSTANCE;
      case 6205 -> MarketStatusInvalidForNewLP.INSTANCE;
      case 6206 -> InvalidMarkTwapUpdateDetected.INSTANCE;
      case 6207 -> MarketSettlementAttemptOnActiveMarket.INSTANCE;
      case 6208 -> MarketSettlementRequiresSettledLP.INSTANCE;
      case 6209 -> MarketSettlementAttemptTooEarly.INSTANCE;
      case 6210 -> MarketSettlementTargetPriceInvalid.INSTANCE;
      case 6211 -> UnsupportedSpotMarket.INSTANCE;
      case 6212 -> SpotOrdersDisabled.INSTANCE;
      case 6213 -> MarketBeingInitialized.INSTANCE;
      case 6214 -> InvalidUserSubAccountId.INSTANCE;
      case 6215 -> InvalidTriggerOrderCondition.INSTANCE;
      case 6216 -> InvalidSpotPosition.INSTANCE;
      case 6217 -> CantTransferBetweenSameUserAccount.INSTANCE;
      case 6218 -> InvalidPerpPosition.INSTANCE;
      case 6219 -> UnableToGetLimitPrice.INSTANCE;
      case 6220 -> InvalidLiquidation.INSTANCE;
      case 6221 -> SpotFulfillmentConfigDisabled.INSTANCE;
      case 6222 -> InvalidMaker.INSTANCE;
      case 6223 -> FailedUnwrap.INSTANCE;
      case 6224 -> MaxNumberOfUsers.INSTANCE;
      case 6225 -> InvalidOracleForSettlePnl.INSTANCE;
      case 6226 -> MarginOrdersOpen.INSTANCE;
      case 6227 -> TierViolationLiquidatingPerpPnl.INSTANCE;
      case 6228 -> CouldNotLoadUserData.INSTANCE;
      case 6229 -> UserWrongMutability.INSTANCE;
      case 6230 -> InvalidUserAccount.INSTANCE;
      case 6231 -> CouldNotLoadUserStatsData.INSTANCE;
      case 6232 -> UserStatsWrongMutability.INSTANCE;
      case 6233 -> InvalidUserStatsAccount.INSTANCE;
      case 6234 -> UserNotFound.INSTANCE;
      case 6235 -> UnableToLoadUserAccount.INSTANCE;
      case 6236 -> UserStatsNotFound.INSTANCE;
      case 6237 -> UnableToLoadUserStatsAccount.INSTANCE;
      case 6238 -> UserNotInactive.INSTANCE;
      case 6239 -> RevertFill.INSTANCE;
      case 6240 -> InvalidMarketAccountforDeletion.INSTANCE;
      case 6241 -> InvalidSpotFulfillmentParams.INSTANCE;
      case 6242 -> FailedToGetMint.INSTANCE;
      case 6243 -> FailedPhoenixCPI.INSTANCE;
      case 6244 -> FailedToDeserializePhoenixMarket.INSTANCE;
      case 6245 -> InvalidPricePrecision.INSTANCE;
      case 6246 -> InvalidPhoenixProgram.INSTANCE;
      case 6247 -> InvalidPhoenixMarket.INSTANCE;
      case 6248 -> InvalidSwap.INSTANCE;
      case 6249 -> SwapLimitPriceBreached.INSTANCE;
      case 6250 -> SpotMarketReduceOnly.INSTANCE;
      case 6251 -> FundingWasNotUpdated.INSTANCE;
      case 6252 -> ImpossibleFill.INSTANCE;
      case 6253 -> CantUpdatePerpBidAskTwap.INSTANCE;
      case 6254 -> UserReduceOnly.INSTANCE;
      case 6255 -> InvalidMarginCalculation.INSTANCE;
      case 6256 -> CantPayUserInitFee.INSTANCE;
      case 6257 -> CantReclaimRent.INSTANCE;
      case 6258 -> InsuranceFundOperationPaused.INSTANCE;
      case 6259 -> NoUnsettledPnl.INSTANCE;
      case 6260 -> PnlPoolCantSettleUser.INSTANCE;
      case 6261 -> OracleNonPositive.INSTANCE;
      case 6262 -> OracleTooVolatile.INSTANCE;
      case 6263 -> OracleTooUncertain.INSTANCE;
      case 6264 -> OracleStaleForMargin.INSTANCE;
      case 6265 -> OracleInsufficientDataPoints.INSTANCE;
      case 6266 -> OracleStaleForAMM.INSTANCE;
      case 6267 -> UnableToParsePullOracleMessage.INSTANCE;
      case 6268 -> MaxBorrows.INSTANCE;
      case 6269 -> OracleUpdatesNotMonotonic.INSTANCE;
      case 6270 -> OraclePriceFeedMessageMismatch.INSTANCE;
      case 6271 -> OracleUnsupportedMessageType.INSTANCE;
      case 6272 -> OracleDeserializeMessageFailed.INSTANCE;
      case 6273 -> OracleWrongGuardianSetOwner.INSTANCE;
      case 6274 -> OracleWrongWriteAuthority.INSTANCE;
      case 6275 -> OracleWrongVaaOwner.INSTANCE;
      case 6276 -> OracleTooManyPriceAccountUpdates.INSTANCE;
      case 6277 -> OracleMismatchedVaaAndPriceUpdates.INSTANCE;
      case 6278 -> OracleBadRemainingAccountPublicKey.INSTANCE;
      case 6279 -> FailedOpenbookV2CPI.INSTANCE;
      case 6280 -> InvalidOpenbookV2Program.INSTANCE;
      case 6281 -> InvalidOpenbookV2Market.INSTANCE;
      case 6282 -> NonZeroTransferFee.INSTANCE;
      case 6283 -> LiquidationOrderFailedToFill.INSTANCE;
      case 6284 -> InvalidPredictionMarketOrder.INSTANCE;
      case 6285 -> InvalidVerificationIxIndex.INSTANCE;
      case 6286 -> SigVerificationFailed.INSTANCE;
      case 6287 -> MismatchedSignedMsgOrderParamsMarketIndex.INSTANCE;
      case 6288 -> InvalidSignedMsgOrderParam.INSTANCE;
      case 6289 -> PlaceAndTakeOrderSuccessConditionFailed.INSTANCE;
      case 6290 -> InvalidHighLeverageModeConfig.INSTANCE;
      case 6291 -> InvalidRFQUserAccount.INSTANCE;
      case 6292 -> RFQUserAccountWrongMutability.INSTANCE;
      case 6293 -> RFQUserAccountFull.INSTANCE;
      case 6294 -> RFQOrderNotFilled.INSTANCE;
      case 6295 -> InvalidRFQOrder.INSTANCE;
      case 6296 -> InvalidRFQMatch.INSTANCE;
      case 6297 -> InvalidSignedMsgUserAccount.INSTANCE;
      case 6298 -> SignedMsgUserAccountWrongMutability.INSTANCE;
      case 6299 -> SignedMsgUserOrdersAccountFull.INSTANCE;
      case 6300 -> SignedMsgOrderDoesNotExist.INSTANCE;
      case 6301 -> InvalidSignedMsgOrderId.INSTANCE;
      case 6302 -> InvalidPoolId.INSTANCE;
      case 6303 -> InvalidProtectedMakerModeConfig.INSTANCE;
      case 6304 -> InvalidPythLazerStorageOwner.INSTANCE;
      case 6305 -> UnverifiedPythLazerMessage.INSTANCE;
      case 6306 -> InvalidPythLazerMessage.INSTANCE;
      case 6307 -> PythLazerMessagePriceFeedMismatch.INSTANCE;
      case 6308 -> InvalidLiquidateSpotWithSwap.INSTANCE;
      case 6309 -> SignedMsgUserContextUserMismatch.INSTANCE;
      case 6310 -> UserFuelOverflowThresholdNotMet.INSTANCE;
      case 6311 -> FuelOverflowAccountNotFound.INSTANCE;
      case 6312 -> InvalidTransferPerpPosition.INSTANCE;
      case 6313 -> InvalidSignedMsgUserOrdersResize.INSTANCE;
      case 6314 -> CouldNotDeserializeHighLeverageModeConfig.INSTANCE;
      case 6315 -> InvalidIfRebalanceConfig.INSTANCE;
      case 6316 -> InvalidIfRebalanceSwap.INSTANCE;
      case 6317 -> InvalidRevenueShareResize.INSTANCE;
      case 6318 -> BuilderRevoked.INSTANCE;
      case 6319 -> InvalidBuilderFee.INSTANCE;
      case 6320 -> RevenueShareEscrowAuthorityMismatch.INSTANCE;
      case 6321 -> RevenueShareEscrowOrdersAccountFull.INSTANCE;
      case 6322 -> InvalidRevenueShareAccount.INSTANCE;
      case 6323 -> CannotRevokeBuilderWithOpenOrders.INSTANCE;
      case 6324 -> UnableToLoadRevenueShareAccount.INSTANCE;
      case 6325 -> InvalidConstituent.INSTANCE;
      case 6326 -> InvalidAmmConstituentMappingArgument.INSTANCE;
      case 6327 -> ConstituentNotFound.INSTANCE;
      case 6328 -> ConstituentCouldNotLoad.INSTANCE;
      case 6329 -> ConstituentWrongMutability.INSTANCE;
      case 6330 -> WrongNumberOfConstituents.INSTANCE;
      case 6331 -> InsufficientConstituentTokenBalance.INSTANCE;
      case 6332 -> AMMCacheStale.INSTANCE;
      case 6333 -> LpPoolAumDelayed.INSTANCE;
      case 6334 -> ConstituentOracleStale.INSTANCE;
      case 6335 -> LpInvariantFailed.INSTANCE;
      case 6336 -> InvalidConstituentDerivativeWeights.INSTANCE;
      case 6337 -> MaxDlpAumBreached.INSTANCE;
      case 6338 -> SettleLpPoolDisabled.INSTANCE;
      case 6339 -> MintRedeemLpPoolDisabled.INSTANCE;
      case 6340 -> LpPoolSettleInvariantBreached.INSTANCE;
      case 6341 -> InvalidConstituentOperation.INSTANCE;
      case 6342 -> Unauthorized.INSTANCE;
      case 6343 -> InvalidLpPoolId.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Drift error code: " + errorCode);
    };
  }

  record InvalidSpotMarketAuthority(int code, String msg) implements DriftError {

    public static final InvalidSpotMarketAuthority INSTANCE = new InvalidSpotMarketAuthority(
        6000, "Invalid Spot Market Authority"
    );
  }

  record InvalidInsuranceFundAuthority(int code, String msg) implements DriftError {

    public static final InvalidInsuranceFundAuthority INSTANCE = new InvalidInsuranceFundAuthority(
        6001, "Clearing house not insurance fund authority"
    );
  }

  record InsufficientDeposit(int code, String msg) implements DriftError {

    public static final InsufficientDeposit INSTANCE = new InsufficientDeposit(
        6002, "Insufficient deposit"
    );
  }

  record InsufficientCollateral(int code, String msg) implements DriftError {

    public static final InsufficientCollateral INSTANCE = new InsufficientCollateral(
        6003, "Insufficient collateral"
    );
  }

  record SufficientCollateral(int code, String msg) implements DriftError {

    public static final SufficientCollateral INSTANCE = new SufficientCollateral(
        6004, "Sufficient collateral"
    );
  }

  record MaxNumberOfPositions(int code, String msg) implements DriftError {

    public static final MaxNumberOfPositions INSTANCE = new MaxNumberOfPositions(
        6005, "Max number of positions taken"
    );
  }

  record AdminControlsPricesDisabled(int code, String msg) implements DriftError {

    public static final AdminControlsPricesDisabled INSTANCE = new AdminControlsPricesDisabled(
        6006, "Admin Controls Prices Disabled"
    );
  }

  record MarketDelisted(int code, String msg) implements DriftError {

    public static final MarketDelisted INSTANCE = new MarketDelisted(
        6007, "Market Delisted"
    );
  }

  record MarketIndexAlreadyInitialized(int code, String msg) implements DriftError {

    public static final MarketIndexAlreadyInitialized INSTANCE = new MarketIndexAlreadyInitialized(
        6008, "Market Index Already Initialized"
    );
  }

  record UserAccountAndUserPositionsAccountMismatch(int code, String msg) implements DriftError {

    public static final UserAccountAndUserPositionsAccountMismatch INSTANCE = new UserAccountAndUserPositionsAccountMismatch(
        6009, "User Account And User Positions Account Mismatch"
    );
  }

  record UserHasNoPositionInMarket(int code, String msg) implements DriftError {

    public static final UserHasNoPositionInMarket INSTANCE = new UserHasNoPositionInMarket(
        6010, "User Has No Position In Market"
    );
  }

  record InvalidInitialPeg(int code, String msg) implements DriftError {

    public static final InvalidInitialPeg INSTANCE = new InvalidInitialPeg(
        6011, "Invalid Initial Peg"
    );
  }

  record InvalidRepegRedundant(int code, String msg) implements DriftError {

    public static final InvalidRepegRedundant INSTANCE = new InvalidRepegRedundant(
        6012, "AMM repeg already configured with amt given"
    );
  }

  record InvalidRepegDirection(int code, String msg) implements DriftError {

    public static final InvalidRepegDirection INSTANCE = new InvalidRepegDirection(
        6013, "AMM repeg incorrect repeg direction"
    );
  }

  record InvalidRepegProfitability(int code, String msg) implements DriftError {

    public static final InvalidRepegProfitability INSTANCE = new InvalidRepegProfitability(
        6014, "AMM repeg out of bounds pnl"
    );
  }

  record SlippageOutsideLimit(int code, String msg) implements DriftError {

    public static final SlippageOutsideLimit INSTANCE = new SlippageOutsideLimit(
        6015, "Slippage Outside Limit Price"
    );
  }

  record OrderSizeTooSmall(int code, String msg) implements DriftError {

    public static final OrderSizeTooSmall INSTANCE = new OrderSizeTooSmall(
        6016, "Order Size Too Small"
    );
  }

  record InvalidUpdateK(int code, String msg) implements DriftError {

    public static final InvalidUpdateK INSTANCE = new InvalidUpdateK(
        6017, "Price change too large when updating K"
    );
  }

  record AdminWithdrawTooLarge(int code, String msg) implements DriftError {

    public static final AdminWithdrawTooLarge INSTANCE = new AdminWithdrawTooLarge(
        6018, "Admin tried to withdraw amount larger than fees collected"
    );
  }

  record MathError(int code, String msg) implements DriftError {

    public static final MathError INSTANCE = new MathError(
        6019, "Math Error"
    );
  }

  record BnConversionError(int code, String msg) implements DriftError {

    public static final BnConversionError INSTANCE = new BnConversionError(
        6020, "Conversion to u128/u64 failed with an overflow or underflow"
    );
  }

  record ClockUnavailable(int code, String msg) implements DriftError {

    public static final ClockUnavailable INSTANCE = new ClockUnavailable(
        6021, "Clock unavailable"
    );
  }

  record UnableToLoadOracle(int code, String msg) implements DriftError {

    public static final UnableToLoadOracle INSTANCE = new UnableToLoadOracle(
        6022, "Unable To Load Oracles"
    );
  }

  record PriceBandsBreached(int code, String msg) implements DriftError {

    public static final PriceBandsBreached INSTANCE = new PriceBandsBreached(
        6023, "Price Bands Breached"
    );
  }

  record ExchangePaused(int code, String msg) implements DriftError {

    public static final ExchangePaused INSTANCE = new ExchangePaused(
        6024, "Exchange is paused"
    );
  }

  record InvalidWhitelistToken(int code, String msg) implements DriftError {

    public static final InvalidWhitelistToken INSTANCE = new InvalidWhitelistToken(
        6025, "Invalid whitelist token"
    );
  }

  record WhitelistTokenNotFound(int code, String msg) implements DriftError {

    public static final WhitelistTokenNotFound INSTANCE = new WhitelistTokenNotFound(
        6026, "Whitelist token not found"
    );
  }

  record InvalidDiscountToken(int code, String msg) implements DriftError {

    public static final InvalidDiscountToken INSTANCE = new InvalidDiscountToken(
        6027, "Invalid discount token"
    );
  }

  record DiscountTokenNotFound(int code, String msg) implements DriftError {

    public static final DiscountTokenNotFound INSTANCE = new DiscountTokenNotFound(
        6028, "Discount token not found"
    );
  }

  record ReferrerNotFound(int code, String msg) implements DriftError {

    public static final ReferrerNotFound INSTANCE = new ReferrerNotFound(
        6029, "Referrer not found"
    );
  }

  record ReferrerStatsNotFound(int code, String msg) implements DriftError {

    public static final ReferrerStatsNotFound INSTANCE = new ReferrerStatsNotFound(
        6030, "ReferrerNotFound"
    );
  }

  record ReferrerMustBeWritable(int code, String msg) implements DriftError {

    public static final ReferrerMustBeWritable INSTANCE = new ReferrerMustBeWritable(
        6031, "ReferrerMustBeWritable"
    );
  }

  record ReferrerStatsMustBeWritable(int code, String msg) implements DriftError {

    public static final ReferrerStatsMustBeWritable INSTANCE = new ReferrerStatsMustBeWritable(
        6032, "ReferrerMustBeWritable"
    );
  }

  record ReferrerAndReferrerStatsAuthorityUnequal(int code, String msg) implements DriftError {

    public static final ReferrerAndReferrerStatsAuthorityUnequal INSTANCE = new ReferrerAndReferrerStatsAuthorityUnequal(
        6033, "ReferrerAndReferrerStatsAuthorityUnequal"
    );
  }

  record InvalidReferrer(int code, String msg) implements DriftError {

    public static final InvalidReferrer INSTANCE = new InvalidReferrer(
        6034, "InvalidReferrer"
    );
  }

  record InvalidOracle(int code, String msg) implements DriftError {

    public static final InvalidOracle INSTANCE = new InvalidOracle(
        6035, "InvalidOracle"
    );
  }

  record OracleNotFound(int code, String msg) implements DriftError {

    public static final OracleNotFound INSTANCE = new OracleNotFound(
        6036, "OracleNotFound"
    );
  }

  record LiquidationsBlockedByOracle(int code, String msg) implements DriftError {

    public static final LiquidationsBlockedByOracle INSTANCE = new LiquidationsBlockedByOracle(
        6037, "Liquidations Blocked By Oracle"
    );
  }

  record MaxDeposit(int code, String msg) implements DriftError {

    public static final MaxDeposit INSTANCE = new MaxDeposit(
        6038, "Can not deposit more than max deposit"
    );
  }

  record CantDeleteUserWithCollateral(int code, String msg) implements DriftError {

    public static final CantDeleteUserWithCollateral INSTANCE = new CantDeleteUserWithCollateral(
        6039, "Can not delete user that still has collateral"
    );
  }

  record InvalidFundingProfitability(int code, String msg) implements DriftError {

    public static final InvalidFundingProfitability INSTANCE = new InvalidFundingProfitability(
        6040, "AMM funding out of bounds pnl"
    );
  }

  record CastingFailure(int code, String msg) implements DriftError {

    public static final CastingFailure INSTANCE = new CastingFailure(
        6041, "Casting Failure"
    );
  }

  record InvalidOrder(int code, String msg) implements DriftError {

    public static final InvalidOrder INSTANCE = new InvalidOrder(
        6042, "InvalidOrder"
    );
  }

  record InvalidOrderMaxTs(int code, String msg) implements DriftError {

    public static final InvalidOrderMaxTs INSTANCE = new InvalidOrderMaxTs(
        6043, "InvalidOrderMaxTs"
    );
  }

  record InvalidOrderMarketType(int code, String msg) implements DriftError {

    public static final InvalidOrderMarketType INSTANCE = new InvalidOrderMarketType(
        6044, "InvalidOrderMarketType"
    );
  }

  record InvalidOrderForInitialMarginReq(int code, String msg) implements DriftError {

    public static final InvalidOrderForInitialMarginReq INSTANCE = new InvalidOrderForInitialMarginReq(
        6045, "InvalidOrderForInitialMarginReq"
    );
  }

  record InvalidOrderNotRiskReducing(int code, String msg) implements DriftError {

    public static final InvalidOrderNotRiskReducing INSTANCE = new InvalidOrderNotRiskReducing(
        6046, "InvalidOrderNotRiskReducing"
    );
  }

  record InvalidOrderSizeTooSmall(int code, String msg) implements DriftError {

    public static final InvalidOrderSizeTooSmall INSTANCE = new InvalidOrderSizeTooSmall(
        6047, "InvalidOrderSizeTooSmall"
    );
  }

  record InvalidOrderNotStepSizeMultiple(int code, String msg) implements DriftError {

    public static final InvalidOrderNotStepSizeMultiple INSTANCE = new InvalidOrderNotStepSizeMultiple(
        6048, "InvalidOrderNotStepSizeMultiple"
    );
  }

  record InvalidOrderBaseQuoteAsset(int code, String msg) implements DriftError {

    public static final InvalidOrderBaseQuoteAsset INSTANCE = new InvalidOrderBaseQuoteAsset(
        6049, "InvalidOrderBaseQuoteAsset"
    );
  }

  record InvalidOrderIOC(int code, String msg) implements DriftError {

    public static final InvalidOrderIOC INSTANCE = new InvalidOrderIOC(
        6050, "InvalidOrderIOC"
    );
  }

  record InvalidOrderPostOnly(int code, String msg) implements DriftError {

    public static final InvalidOrderPostOnly INSTANCE = new InvalidOrderPostOnly(
        6051, "InvalidOrderPostOnly"
    );
  }

  record InvalidOrderIOCPostOnly(int code, String msg) implements DriftError {

    public static final InvalidOrderIOCPostOnly INSTANCE = new InvalidOrderIOCPostOnly(
        6052, "InvalidOrderIOCPostOnly"
    );
  }

  record InvalidOrderTrigger(int code, String msg) implements DriftError {

    public static final InvalidOrderTrigger INSTANCE = new InvalidOrderTrigger(
        6053, "InvalidOrderTrigger"
    );
  }

  record InvalidOrderAuction(int code, String msg) implements DriftError {

    public static final InvalidOrderAuction INSTANCE = new InvalidOrderAuction(
        6054, "InvalidOrderAuction"
    );
  }

  record InvalidOrderOracleOffset(int code, String msg) implements DriftError {

    public static final InvalidOrderOracleOffset INSTANCE = new InvalidOrderOracleOffset(
        6055, "InvalidOrderOracleOffset"
    );
  }

  record InvalidOrderMinOrderSize(int code, String msg) implements DriftError {

    public static final InvalidOrderMinOrderSize INSTANCE = new InvalidOrderMinOrderSize(
        6056, "InvalidOrderMinOrderSize"
    );
  }

  record PlacePostOnlyLimitFailure(int code, String msg) implements DriftError {

    public static final PlacePostOnlyLimitFailure INSTANCE = new PlacePostOnlyLimitFailure(
        6057, "Failed to Place Post-Only Limit Order"
    );
  }

  record UserHasNoOrder(int code, String msg) implements DriftError {

    public static final UserHasNoOrder INSTANCE = new UserHasNoOrder(
        6058, "User has no order"
    );
  }

  record OrderAmountTooSmall(int code, String msg) implements DriftError {

    public static final OrderAmountTooSmall INSTANCE = new OrderAmountTooSmall(
        6059, "Order Amount Too Small"
    );
  }

  record MaxNumberOfOrders(int code, String msg) implements DriftError {

    public static final MaxNumberOfOrders INSTANCE = new MaxNumberOfOrders(
        6060, "Max number of orders taken"
    );
  }

  record OrderDoesNotExist(int code, String msg) implements DriftError {

    public static final OrderDoesNotExist INSTANCE = new OrderDoesNotExist(
        6061, "Order does not exist"
    );
  }

  record OrderNotOpen(int code, String msg) implements DriftError {

    public static final OrderNotOpen INSTANCE = new OrderNotOpen(
        6062, "Order not open"
    );
  }

  record FillOrderDidNotUpdateState(int code, String msg) implements DriftError {

    public static final FillOrderDidNotUpdateState INSTANCE = new FillOrderDidNotUpdateState(
        6063, "FillOrderDidNotUpdateState"
    );
  }

  record ReduceOnlyOrderIncreasedRisk(int code, String msg) implements DriftError {

    public static final ReduceOnlyOrderIncreasedRisk INSTANCE = new ReduceOnlyOrderIncreasedRisk(
        6064, "Reduce only order increased risk"
    );
  }

  record UnableToLoadAccountLoader(int code, String msg) implements DriftError {

    public static final UnableToLoadAccountLoader INSTANCE = new UnableToLoadAccountLoader(
        6065, "Unable to load AccountLoader"
    );
  }

  record TradeSizeTooLarge(int code, String msg) implements DriftError {

    public static final TradeSizeTooLarge INSTANCE = new TradeSizeTooLarge(
        6066, "Trade Size Too Large"
    );
  }

  record UserCantReferThemselves(int code, String msg) implements DriftError {

    public static final UserCantReferThemselves INSTANCE = new UserCantReferThemselves(
        6067, "User cant refer themselves"
    );
  }

  record DidNotReceiveExpectedReferrer(int code, String msg) implements DriftError {

    public static final DidNotReceiveExpectedReferrer INSTANCE = new DidNotReceiveExpectedReferrer(
        6068, "Did not receive expected referrer"
    );
  }

  record CouldNotDeserializeReferrer(int code, String msg) implements DriftError {

    public static final CouldNotDeserializeReferrer INSTANCE = new CouldNotDeserializeReferrer(
        6069, "Could not deserialize referrer"
    );
  }

  record CouldNotDeserializeReferrerStats(int code, String msg) implements DriftError {

    public static final CouldNotDeserializeReferrerStats INSTANCE = new CouldNotDeserializeReferrerStats(
        6070, "Could not deserialize referrer stats"
    );
  }

  record UserOrderIdAlreadyInUse(int code, String msg) implements DriftError {

    public static final UserOrderIdAlreadyInUse INSTANCE = new UserOrderIdAlreadyInUse(
        6071, "User Order Id Already In Use"
    );
  }

  record NoPositionsLiquidatable(int code, String msg) implements DriftError {

    public static final NoPositionsLiquidatable INSTANCE = new NoPositionsLiquidatable(
        6072, "No positions liquidatable"
    );
  }

  record InvalidMarginRatio(int code, String msg) implements DriftError {

    public static final InvalidMarginRatio INSTANCE = new InvalidMarginRatio(
        6073, "Invalid Margin Ratio"
    );
  }

  record CantCancelPostOnlyOrder(int code, String msg) implements DriftError {

    public static final CantCancelPostOnlyOrder INSTANCE = new CantCancelPostOnlyOrder(
        6074, "Cant Cancel Post Only Order"
    );
  }

  record InvalidOracleOffset(int code, String msg) implements DriftError {

    public static final InvalidOracleOffset INSTANCE = new InvalidOracleOffset(
        6075, "InvalidOracleOffset"
    );
  }

  record CantExpireOrders(int code, String msg) implements DriftError {

    public static final CantExpireOrders INSTANCE = new CantExpireOrders(
        6076, "CantExpireOrders"
    );
  }

  record CouldNotLoadMarketData(int code, String msg) implements DriftError {

    public static final CouldNotLoadMarketData INSTANCE = new CouldNotLoadMarketData(
        6077, "CouldNotLoadMarketData"
    );
  }

  record PerpMarketNotFound(int code, String msg) implements DriftError {

    public static final PerpMarketNotFound INSTANCE = new PerpMarketNotFound(
        6078, "PerpMarketNotFound"
    );
  }

  record InvalidMarketAccount(int code, String msg) implements DriftError {

    public static final InvalidMarketAccount INSTANCE = new InvalidMarketAccount(
        6079, "InvalidMarketAccount"
    );
  }

  record UnableToLoadPerpMarketAccount(int code, String msg) implements DriftError {

    public static final UnableToLoadPerpMarketAccount INSTANCE = new UnableToLoadPerpMarketAccount(
        6080, "UnableToLoadMarketAccount"
    );
  }

  record MarketWrongMutability(int code, String msg) implements DriftError {

    public static final MarketWrongMutability INSTANCE = new MarketWrongMutability(
        6081, "MarketWrongMutability"
    );
  }

  record UnableToCastUnixTime(int code, String msg) implements DriftError {

    public static final UnableToCastUnixTime INSTANCE = new UnableToCastUnixTime(
        6082, "UnableToCastUnixTime"
    );
  }

  record CouldNotFindSpotPosition(int code, String msg) implements DriftError {

    public static final CouldNotFindSpotPosition INSTANCE = new CouldNotFindSpotPosition(
        6083, "CouldNotFindSpotPosition"
    );
  }

  record NoSpotPositionAvailable(int code, String msg) implements DriftError {

    public static final NoSpotPositionAvailable INSTANCE = new NoSpotPositionAvailable(
        6084, "NoSpotPositionAvailable"
    );
  }

  record InvalidSpotMarketInitialization(int code, String msg) implements DriftError {

    public static final InvalidSpotMarketInitialization INSTANCE = new InvalidSpotMarketInitialization(
        6085, "InvalidSpotMarketInitialization"
    );
  }

  record CouldNotLoadSpotMarketData(int code, String msg) implements DriftError {

    public static final CouldNotLoadSpotMarketData INSTANCE = new CouldNotLoadSpotMarketData(
        6086, "CouldNotLoadSpotMarketData"
    );
  }

  record SpotMarketNotFound(int code, String msg) implements DriftError {

    public static final SpotMarketNotFound INSTANCE = new SpotMarketNotFound(
        6087, "SpotMarketNotFound"
    );
  }

  record InvalidSpotMarketAccount(int code, String msg) implements DriftError {

    public static final InvalidSpotMarketAccount INSTANCE = new InvalidSpotMarketAccount(
        6088, "InvalidSpotMarketAccount"
    );
  }

  record UnableToLoadSpotMarketAccount(int code, String msg) implements DriftError {

    public static final UnableToLoadSpotMarketAccount INSTANCE = new UnableToLoadSpotMarketAccount(
        6089, "UnableToLoadSpotMarketAccount"
    );
  }

  record SpotMarketWrongMutability(int code, String msg) implements DriftError {

    public static final SpotMarketWrongMutability INSTANCE = new SpotMarketWrongMutability(
        6090, "SpotMarketWrongMutability"
    );
  }

  record SpotMarketInterestNotUpToDate(int code, String msg) implements DriftError {

    public static final SpotMarketInterestNotUpToDate INSTANCE = new SpotMarketInterestNotUpToDate(
        6091, "SpotInterestNotUpToDate"
    );
  }

  record SpotMarketInsufficientDeposits(int code, String msg) implements DriftError {

    public static final SpotMarketInsufficientDeposits INSTANCE = new SpotMarketInsufficientDeposits(
        6092, "SpotMarketInsufficientDeposits"
    );
  }

  record UserMustSettleTheirOwnPositiveUnsettledPNL(int code, String msg) implements DriftError {

    public static final UserMustSettleTheirOwnPositiveUnsettledPNL INSTANCE = new UserMustSettleTheirOwnPositiveUnsettledPNL(
        6093, "UserMustSettleTheirOwnPositiveUnsettledPNL"
    );
  }

  record CantUpdatePoolBalanceType(int code, String msg) implements DriftError {

    public static final CantUpdatePoolBalanceType INSTANCE = new CantUpdatePoolBalanceType(
        6094, "CantUpdatePoolBalanceType"
    );
  }

  record InsufficientCollateralForSettlingPNL(int code, String msg) implements DriftError {

    public static final InsufficientCollateralForSettlingPNL INSTANCE = new InsufficientCollateralForSettlingPNL(
        6095, "InsufficientCollateralForSettlingPNL"
    );
  }

  record AMMNotUpdatedInSameSlot(int code, String msg) implements DriftError {

    public static final AMMNotUpdatedInSameSlot INSTANCE = new AMMNotUpdatedInSameSlot(
        6096, "AMMNotUpdatedInSameSlot"
    );
  }

  record AuctionNotComplete(int code, String msg) implements DriftError {

    public static final AuctionNotComplete INSTANCE = new AuctionNotComplete(
        6097, "AuctionNotComplete"
    );
  }

  record MakerNotFound(int code, String msg) implements DriftError {

    public static final MakerNotFound INSTANCE = new MakerNotFound(
        6098, "MakerNotFound"
    );
  }

  record MakerStatsNotFound(int code, String msg) implements DriftError {

    public static final MakerStatsNotFound INSTANCE = new MakerStatsNotFound(
        6099, "MakerNotFound"
    );
  }

  record MakerMustBeWritable(int code, String msg) implements DriftError {

    public static final MakerMustBeWritable INSTANCE = new MakerMustBeWritable(
        6100, "MakerMustBeWritable"
    );
  }

  record MakerStatsMustBeWritable(int code, String msg) implements DriftError {

    public static final MakerStatsMustBeWritable INSTANCE = new MakerStatsMustBeWritable(
        6101, "MakerMustBeWritable"
    );
  }

  record MakerOrderNotFound(int code, String msg) implements DriftError {

    public static final MakerOrderNotFound INSTANCE = new MakerOrderNotFound(
        6102, "MakerOrderNotFound"
    );
  }

  record CouldNotDeserializeMaker(int code, String msg) implements DriftError {

    public static final CouldNotDeserializeMaker INSTANCE = new CouldNotDeserializeMaker(
        6103, "CouldNotDeserializeMaker"
    );
  }

  record CouldNotDeserializeMakerStats(int code, String msg) implements DriftError {

    public static final CouldNotDeserializeMakerStats INSTANCE = new CouldNotDeserializeMakerStats(
        6104, "CouldNotDeserializeMaker"
    );
  }

  record AuctionPriceDoesNotSatisfyMaker(int code, String msg) implements DriftError {

    public static final AuctionPriceDoesNotSatisfyMaker INSTANCE = new AuctionPriceDoesNotSatisfyMaker(
        6105, "AuctionPriceDoesNotSatisfyMaker"
    );
  }

  record MakerCantFulfillOwnOrder(int code, String msg) implements DriftError {

    public static final MakerCantFulfillOwnOrder INSTANCE = new MakerCantFulfillOwnOrder(
        6106, "MakerCantFulfillOwnOrder"
    );
  }

  record MakerOrderMustBePostOnly(int code, String msg) implements DriftError {

    public static final MakerOrderMustBePostOnly INSTANCE = new MakerOrderMustBePostOnly(
        6107, "MakerOrderMustBePostOnly"
    );
  }

  record CantMatchTwoPostOnlys(int code, String msg) implements DriftError {

    public static final CantMatchTwoPostOnlys INSTANCE = new CantMatchTwoPostOnlys(
        6108, "CantMatchTwoPostOnlys"
    );
  }

  record OrderBreachesOraclePriceLimits(int code, String msg) implements DriftError {

    public static final OrderBreachesOraclePriceLimits INSTANCE = new OrderBreachesOraclePriceLimits(
        6109, "OrderBreachesOraclePriceLimits"
    );
  }

  record OrderMustBeTriggeredFirst(int code, String msg) implements DriftError {

    public static final OrderMustBeTriggeredFirst INSTANCE = new OrderMustBeTriggeredFirst(
        6110, "OrderMustBeTriggeredFirst"
    );
  }

  record OrderNotTriggerable(int code, String msg) implements DriftError {

    public static final OrderNotTriggerable INSTANCE = new OrderNotTriggerable(
        6111, "OrderNotTriggerable"
    );
  }

  record OrderDidNotSatisfyTriggerCondition(int code, String msg) implements DriftError {

    public static final OrderDidNotSatisfyTriggerCondition INSTANCE = new OrderDidNotSatisfyTriggerCondition(
        6112, "OrderDidNotSatisfyTriggerCondition"
    );
  }

  record PositionAlreadyBeingLiquidated(int code, String msg) implements DriftError {

    public static final PositionAlreadyBeingLiquidated INSTANCE = new PositionAlreadyBeingLiquidated(
        6113, "PositionAlreadyBeingLiquidated"
    );
  }

  record PositionDoesntHaveOpenPositionOrOrders(int code, String msg) implements DriftError {

    public static final PositionDoesntHaveOpenPositionOrOrders INSTANCE = new PositionDoesntHaveOpenPositionOrOrders(
        6114, "PositionDoesntHaveOpenPositionOrOrders"
    );
  }

  record AllOrdersAreAlreadyLiquidations(int code, String msg) implements DriftError {

    public static final AllOrdersAreAlreadyLiquidations INSTANCE = new AllOrdersAreAlreadyLiquidations(
        6115, "AllOrdersAreAlreadyLiquidations"
    );
  }

  record CantCancelLiquidationOrder(int code, String msg) implements DriftError {

    public static final CantCancelLiquidationOrder INSTANCE = new CantCancelLiquidationOrder(
        6116, "CantCancelLiquidationOrder"
    );
  }

  record UserIsBeingLiquidated(int code, String msg) implements DriftError {

    public static final UserIsBeingLiquidated INSTANCE = new UserIsBeingLiquidated(
        6117, "UserIsBeingLiquidated"
    );
  }

  record LiquidationsOngoing(int code, String msg) implements DriftError {

    public static final LiquidationsOngoing INSTANCE = new LiquidationsOngoing(
        6118, "LiquidationsOngoing"
    );
  }

  record WrongSpotBalanceType(int code, String msg) implements DriftError {

    public static final WrongSpotBalanceType INSTANCE = new WrongSpotBalanceType(
        6119, "WrongSpotBalanceType"
    );
  }

  record UserCantLiquidateThemself(int code, String msg) implements DriftError {

    public static final UserCantLiquidateThemself INSTANCE = new UserCantLiquidateThemself(
        6120, "UserCantLiquidateThemself"
    );
  }

  record InvalidPerpPositionToLiquidate(int code, String msg) implements DriftError {

    public static final InvalidPerpPositionToLiquidate INSTANCE = new InvalidPerpPositionToLiquidate(
        6121, "InvalidPerpPositionToLiquidate"
    );
  }

  record InvalidBaseAssetAmountForLiquidatePerp(int code, String msg) implements DriftError {

    public static final InvalidBaseAssetAmountForLiquidatePerp INSTANCE = new InvalidBaseAssetAmountForLiquidatePerp(
        6122, "InvalidBaseAssetAmountForLiquidatePerp"
    );
  }

  record InvalidPositionLastFundingRate(int code, String msg) implements DriftError {

    public static final InvalidPositionLastFundingRate INSTANCE = new InvalidPositionLastFundingRate(
        6123, "InvalidPositionLastFundingRate"
    );
  }

  record InvalidPositionDelta(int code, String msg) implements DriftError {

    public static final InvalidPositionDelta INSTANCE = new InvalidPositionDelta(
        6124, "InvalidPositionDelta"
    );
  }

  record UserBankrupt(int code, String msg) implements DriftError {

    public static final UserBankrupt INSTANCE = new UserBankrupt(
        6125, "UserBankrupt"
    );
  }

  record UserNotBankrupt(int code, String msg) implements DriftError {

    public static final UserNotBankrupt INSTANCE = new UserNotBankrupt(
        6126, "UserNotBankrupt"
    );
  }

  record UserHasInvalidBorrow(int code, String msg) implements DriftError {

    public static final UserHasInvalidBorrow INSTANCE = new UserHasInvalidBorrow(
        6127, "UserHasInvalidBorrow"
    );
  }

  record DailyWithdrawLimit(int code, String msg) implements DriftError {

    public static final DailyWithdrawLimit INSTANCE = new DailyWithdrawLimit(
        6128, "DailyWithdrawLimit"
    );
  }

  record DefaultError(int code, String msg) implements DriftError {

    public static final DefaultError INSTANCE = new DefaultError(
        6129, "DefaultError"
    );
  }

  record InsufficientLPTokens(int code, String msg) implements DriftError {

    public static final InsufficientLPTokens INSTANCE = new InsufficientLPTokens(
        6130, "Insufficient LP tokens"
    );
  }

  record CantLPWithPerpPosition(int code, String msg) implements DriftError {

    public static final CantLPWithPerpPosition INSTANCE = new CantLPWithPerpPosition(
        6131, "Cant LP with a market position"
    );
  }

  record UnableToBurnLPTokens(int code, String msg) implements DriftError {

    public static final UnableToBurnLPTokens INSTANCE = new UnableToBurnLPTokens(
        6132, "Unable to burn LP tokens"
    );
  }

  record TryingToRemoveLiquidityTooFast(int code, String msg) implements DriftError {

    public static final TryingToRemoveLiquidityTooFast INSTANCE = new TryingToRemoveLiquidityTooFast(
        6133, "Trying to remove liqudity too fast after adding it"
    );
  }

  record InvalidSpotMarketVault(int code, String msg) implements DriftError {

    public static final InvalidSpotMarketVault INSTANCE = new InvalidSpotMarketVault(
        6134, "Invalid Spot Market Vault"
    );
  }

  record InvalidSpotMarketState(int code, String msg) implements DriftError {

    public static final InvalidSpotMarketState INSTANCE = new InvalidSpotMarketState(
        6135, "Invalid Spot Market State"
    );
  }

  record InvalidSerumProgram(int code, String msg) implements DriftError {

    public static final InvalidSerumProgram INSTANCE = new InvalidSerumProgram(
        6136, "InvalidSerumProgram"
    );
  }

  record InvalidSerumMarket(int code, String msg) implements DriftError {

    public static final InvalidSerumMarket INSTANCE = new InvalidSerumMarket(
        6137, "InvalidSerumMarket"
    );
  }

  record InvalidSerumBids(int code, String msg) implements DriftError {

    public static final InvalidSerumBids INSTANCE = new InvalidSerumBids(
        6138, "InvalidSerumBids"
    );
  }

  record InvalidSerumAsks(int code, String msg) implements DriftError {

    public static final InvalidSerumAsks INSTANCE = new InvalidSerumAsks(
        6139, "InvalidSerumAsks"
    );
  }

  record InvalidSerumOpenOrders(int code, String msg) implements DriftError {

    public static final InvalidSerumOpenOrders INSTANCE = new InvalidSerumOpenOrders(
        6140, "InvalidSerumOpenOrders"
    );
  }

  record FailedSerumCPI(int code, String msg) implements DriftError {

    public static final FailedSerumCPI INSTANCE = new FailedSerumCPI(
        6141, "FailedSerumCPI"
    );
  }

  record FailedToFillOnExternalMarket(int code, String msg) implements DriftError {

    public static final FailedToFillOnExternalMarket INSTANCE = new FailedToFillOnExternalMarket(
        6142, "FailedToFillOnExternalMarket"
    );
  }

  record InvalidFulfillmentConfig(int code, String msg) implements DriftError {

    public static final InvalidFulfillmentConfig INSTANCE = new InvalidFulfillmentConfig(
        6143, "InvalidFulfillmentConfig"
    );
  }

  record InvalidFeeStructure(int code, String msg) implements DriftError {

    public static final InvalidFeeStructure INSTANCE = new InvalidFeeStructure(
        6144, "InvalidFeeStructure"
    );
  }

  record InsufficientIFShares(int code, String msg) implements DriftError {

    public static final InsufficientIFShares INSTANCE = new InsufficientIFShares(
        6145, "Insufficient IF shares"
    );
  }

  record MarketActionPaused(int code, String msg) implements DriftError {

    public static final MarketActionPaused INSTANCE = new MarketActionPaused(
        6146, "the Market has paused this action"
    );
  }

  record MarketPlaceOrderPaused(int code, String msg) implements DriftError {

    public static final MarketPlaceOrderPaused INSTANCE = new MarketPlaceOrderPaused(
        6147, "the Market status doesnt allow placing orders"
    );
  }

  record MarketFillOrderPaused(int code, String msg) implements DriftError {

    public static final MarketFillOrderPaused INSTANCE = new MarketFillOrderPaused(
        6148, "the Market status doesnt allow filling orders"
    );
  }

  record MarketWithdrawPaused(int code, String msg) implements DriftError {

    public static final MarketWithdrawPaused INSTANCE = new MarketWithdrawPaused(
        6149, "the Market status doesnt allow withdraws"
    );
  }

  record ProtectedAssetTierViolation(int code, String msg) implements DriftError {

    public static final ProtectedAssetTierViolation INSTANCE = new ProtectedAssetTierViolation(
        6150, "Action violates the Protected Asset Tier rules"
    );
  }

  record IsolatedAssetTierViolation(int code, String msg) implements DriftError {

    public static final IsolatedAssetTierViolation INSTANCE = new IsolatedAssetTierViolation(
        6151, "Action violates the Isolated Asset Tier rules"
    );
  }

  record UserCantBeDeleted(int code, String msg) implements DriftError {

    public static final UserCantBeDeleted INSTANCE = new UserCantBeDeleted(
        6152, "User Cant Be Deleted"
    );
  }

  record ReduceOnlyWithdrawIncreasedRisk(int code, String msg) implements DriftError {

    public static final ReduceOnlyWithdrawIncreasedRisk INSTANCE = new ReduceOnlyWithdrawIncreasedRisk(
        6153, "Reduce Only Withdraw Increased Risk"
    );
  }

  record MaxOpenInterest(int code, String msg) implements DriftError {

    public static final MaxOpenInterest INSTANCE = new MaxOpenInterest(
        6154, "Max Open Interest"
    );
  }

  record CantResolvePerpBankruptcy(int code, String msg) implements DriftError {

    public static final CantResolvePerpBankruptcy INSTANCE = new CantResolvePerpBankruptcy(
        6155, "Cant Resolve Perp Bankruptcy"
    );
  }

  record LiquidationDoesntSatisfyLimitPrice(int code, String msg) implements DriftError {

    public static final LiquidationDoesntSatisfyLimitPrice INSTANCE = new LiquidationDoesntSatisfyLimitPrice(
        6156, "Liquidation Doesnt Satisfy Limit Price"
    );
  }

  record MarginTradingDisabled(int code, String msg) implements DriftError {

    public static final MarginTradingDisabled INSTANCE = new MarginTradingDisabled(
        6157, "Margin Trading Disabled"
    );
  }

  record InvalidMarketStatusToSettlePnl(int code, String msg) implements DriftError {

    public static final InvalidMarketStatusToSettlePnl INSTANCE = new InvalidMarketStatusToSettlePnl(
        6158, "Invalid Market Status to Settle Perp Pnl"
    );
  }

  record PerpMarketNotInSettlement(int code, String msg) implements DriftError {

    public static final PerpMarketNotInSettlement INSTANCE = new PerpMarketNotInSettlement(
        6159, "PerpMarketNotInSettlement"
    );
  }

  record PerpMarketNotInReduceOnly(int code, String msg) implements DriftError {

    public static final PerpMarketNotInReduceOnly INSTANCE = new PerpMarketNotInReduceOnly(
        6160, "PerpMarketNotInReduceOnly"
    );
  }

  record PerpMarketSettlementBufferNotReached(int code, String msg) implements DriftError {

    public static final PerpMarketSettlementBufferNotReached INSTANCE = new PerpMarketSettlementBufferNotReached(
        6161, "PerpMarketSettlementBufferNotReached"
    );
  }

  record PerpMarketSettlementUserHasOpenOrders(int code, String msg) implements DriftError {

    public static final PerpMarketSettlementUserHasOpenOrders INSTANCE = new PerpMarketSettlementUserHasOpenOrders(
        6162, "PerpMarketSettlementUserHasOpenOrders"
    );
  }

  record PerpMarketSettlementUserHasActiveLP(int code, String msg) implements DriftError {

    public static final PerpMarketSettlementUserHasActiveLP INSTANCE = new PerpMarketSettlementUserHasActiveLP(
        6163, "PerpMarketSettlementUserHasActiveLP"
    );
  }

  record UnableToSettleExpiredUserPosition(int code, String msg) implements DriftError {

    public static final UnableToSettleExpiredUserPosition INSTANCE = new UnableToSettleExpiredUserPosition(
        6164, "UnableToSettleExpiredUserPosition"
    );
  }

  record UnequalMarketIndexForSpotTransfer(int code, String msg) implements DriftError {

    public static final UnequalMarketIndexForSpotTransfer INSTANCE = new UnequalMarketIndexForSpotTransfer(
        6165, "UnequalMarketIndexForSpotTransfer"
    );
  }

  record InvalidPerpPositionDetected(int code, String msg) implements DriftError {

    public static final InvalidPerpPositionDetected INSTANCE = new InvalidPerpPositionDetected(
        6166, "InvalidPerpPositionDetected"
    );
  }

  record InvalidSpotPositionDetected(int code, String msg) implements DriftError {

    public static final InvalidSpotPositionDetected INSTANCE = new InvalidSpotPositionDetected(
        6167, "InvalidSpotPositionDetected"
    );
  }

  record InvalidAmmDetected(int code, String msg) implements DriftError {

    public static final InvalidAmmDetected INSTANCE = new InvalidAmmDetected(
        6168, "InvalidAmmDetected"
    );
  }

  record InvalidAmmForFillDetected(int code, String msg) implements DriftError {

    public static final InvalidAmmForFillDetected INSTANCE = new InvalidAmmForFillDetected(
        6169, "InvalidAmmForFillDetected"
    );
  }

  record InvalidAmmLimitPriceOverride(int code, String msg) implements DriftError {

    public static final InvalidAmmLimitPriceOverride INSTANCE = new InvalidAmmLimitPriceOverride(
        6170, "InvalidAmmLimitPriceOverride"
    );
  }

  record InvalidOrderFillPrice(int code, String msg) implements DriftError {

    public static final InvalidOrderFillPrice INSTANCE = new InvalidOrderFillPrice(
        6171, "InvalidOrderFillPrice"
    );
  }

  record SpotMarketBalanceInvariantViolated(int code, String msg) implements DriftError {

    public static final SpotMarketBalanceInvariantViolated INSTANCE = new SpotMarketBalanceInvariantViolated(
        6172, "SpotMarketBalanceInvariantViolated"
    );
  }

  record SpotMarketVaultInvariantViolated(int code, String msg) implements DriftError {

    public static final SpotMarketVaultInvariantViolated INSTANCE = new SpotMarketVaultInvariantViolated(
        6173, "SpotMarketVaultInvariantViolated"
    );
  }

  record InvalidPDA(int code, String msg) implements DriftError {

    public static final InvalidPDA INSTANCE = new InvalidPDA(
        6174, "InvalidPDA"
    );
  }

  record InvalidPDASigner(int code, String msg) implements DriftError {

    public static final InvalidPDASigner INSTANCE = new InvalidPDASigner(
        6175, "InvalidPDASigner"
    );
  }

  record RevenueSettingsCannotSettleToIF(int code, String msg) implements DriftError {

    public static final RevenueSettingsCannotSettleToIF INSTANCE = new RevenueSettingsCannotSettleToIF(
        6176, "RevenueSettingsCannotSettleToIF"
    );
  }

  record NoRevenueToSettleToIF(int code, String msg) implements DriftError {

    public static final NoRevenueToSettleToIF INSTANCE = new NoRevenueToSettleToIF(
        6177, "NoRevenueToSettleToIF"
    );
  }

  record NoAmmPerpPnlDeficit(int code, String msg) implements DriftError {

    public static final NoAmmPerpPnlDeficit INSTANCE = new NoAmmPerpPnlDeficit(
        6178, "NoAmmPerpPnlDeficit"
    );
  }

  record SufficientPerpPnlPool(int code, String msg) implements DriftError {

    public static final SufficientPerpPnlPool INSTANCE = new SufficientPerpPnlPool(
        6179, "SufficientPerpPnlPool"
    );
  }

  record InsufficientPerpPnlPool(int code, String msg) implements DriftError {

    public static final InsufficientPerpPnlPool INSTANCE = new InsufficientPerpPnlPool(
        6180, "InsufficientPerpPnlPool"
    );
  }

  record PerpPnlDeficitBelowThreshold(int code, String msg) implements DriftError {

    public static final PerpPnlDeficitBelowThreshold INSTANCE = new PerpPnlDeficitBelowThreshold(
        6181, "PerpPnlDeficitBelowThreshold"
    );
  }

  record MaxRevenueWithdrawPerPeriodReached(int code, String msg) implements DriftError {

    public static final MaxRevenueWithdrawPerPeriodReached INSTANCE = new MaxRevenueWithdrawPerPeriodReached(
        6182, "MaxRevenueWithdrawPerPeriodReached"
    );
  }

  record MaxIFWithdrawReached(int code, String msg) implements DriftError {

    public static final MaxIFWithdrawReached INSTANCE = new MaxIFWithdrawReached(
        6183, "InvalidSpotPositionDetected"
    );
  }

  record NoIFWithdrawAvailable(int code, String msg) implements DriftError {

    public static final NoIFWithdrawAvailable INSTANCE = new NoIFWithdrawAvailable(
        6184, "NoIFWithdrawAvailable"
    );
  }

  record InvalidIFUnstake(int code, String msg) implements DriftError {

    public static final InvalidIFUnstake INSTANCE = new InvalidIFUnstake(
        6185, "InvalidIFUnstake"
    );
  }

  record InvalidIFUnstakeSize(int code, String msg) implements DriftError {

    public static final InvalidIFUnstakeSize INSTANCE = new InvalidIFUnstakeSize(
        6186, "InvalidIFUnstakeSize"
    );
  }

  record InvalidIFUnstakeCancel(int code, String msg) implements DriftError {

    public static final InvalidIFUnstakeCancel INSTANCE = new InvalidIFUnstakeCancel(
        6187, "InvalidIFUnstakeCancel"
    );
  }

  record InvalidIFForNewStakes(int code, String msg) implements DriftError {

    public static final InvalidIFForNewStakes INSTANCE = new InvalidIFForNewStakes(
        6188, "InvalidIFForNewStakes"
    );
  }

  record InvalidIFRebase(int code, String msg) implements DriftError {

    public static final InvalidIFRebase INSTANCE = new InvalidIFRebase(
        6189, "InvalidIFRebase"
    );
  }

  record InvalidInsuranceUnstakeSize(int code, String msg) implements DriftError {

    public static final InvalidInsuranceUnstakeSize INSTANCE = new InvalidInsuranceUnstakeSize(
        6190, "InvalidInsuranceUnstakeSize"
    );
  }

  record InvalidOrderLimitPrice(int code, String msg) implements DriftError {

    public static final InvalidOrderLimitPrice INSTANCE = new InvalidOrderLimitPrice(
        6191, "InvalidOrderLimitPrice"
    );
  }

  record InvalidIFDetected(int code, String msg) implements DriftError {

    public static final InvalidIFDetected INSTANCE = new InvalidIFDetected(
        6192, "InvalidIFDetected"
    );
  }

  record InvalidAmmMaxSpreadDetected(int code, String msg) implements DriftError {

    public static final InvalidAmmMaxSpreadDetected INSTANCE = new InvalidAmmMaxSpreadDetected(
        6193, "InvalidAmmMaxSpreadDetected"
    );
  }

  record InvalidConcentrationCoef(int code, String msg) implements DriftError {

    public static final InvalidConcentrationCoef INSTANCE = new InvalidConcentrationCoef(
        6194, "InvalidConcentrationCoef"
    );
  }

  record InvalidSrmVault(int code, String msg) implements DriftError {

    public static final InvalidSrmVault INSTANCE = new InvalidSrmVault(
        6195, "InvalidSrmVault"
    );
  }

  record InvalidVaultOwner(int code, String msg) implements DriftError {

    public static final InvalidVaultOwner INSTANCE = new InvalidVaultOwner(
        6196, "InvalidVaultOwner"
    );
  }

  record InvalidMarketStatusForFills(int code, String msg) implements DriftError {

    public static final InvalidMarketStatusForFills INSTANCE = new InvalidMarketStatusForFills(
        6197, "InvalidMarketStatusForFills"
    );
  }

  record IFWithdrawRequestInProgress(int code, String msg) implements DriftError {

    public static final IFWithdrawRequestInProgress INSTANCE = new IFWithdrawRequestInProgress(
        6198, "IFWithdrawRequestInProgress"
    );
  }

  record NoIFWithdrawRequestInProgress(int code, String msg) implements DriftError {

    public static final NoIFWithdrawRequestInProgress INSTANCE = new NoIFWithdrawRequestInProgress(
        6199, "NoIFWithdrawRequestInProgress"
    );
  }

  record IFWithdrawRequestTooSmall(int code, String msg) implements DriftError {

    public static final IFWithdrawRequestTooSmall INSTANCE = new IFWithdrawRequestTooSmall(
        6200, "IFWithdrawRequestTooSmall"
    );
  }

  record IncorrectSpotMarketAccountPassed(int code, String msg) implements DriftError {

    public static final IncorrectSpotMarketAccountPassed INSTANCE = new IncorrectSpotMarketAccountPassed(
        6201, "IncorrectSpotMarketAccountPassed"
    );
  }

  record BlockchainClockInconsistency(int code, String msg) implements DriftError {

    public static final BlockchainClockInconsistency INSTANCE = new BlockchainClockInconsistency(
        6202, "BlockchainClockInconsistency"
    );
  }

  record InvalidIFSharesDetected(int code, String msg) implements DriftError {

    public static final InvalidIFSharesDetected INSTANCE = new InvalidIFSharesDetected(
        6203, "InvalidIFSharesDetected"
    );
  }

  record NewLPSizeTooSmall(int code, String msg) implements DriftError {

    public static final NewLPSizeTooSmall INSTANCE = new NewLPSizeTooSmall(
        6204, "NewLPSizeTooSmall"
    );
  }

  record MarketStatusInvalidForNewLP(int code, String msg) implements DriftError {

    public static final MarketStatusInvalidForNewLP INSTANCE = new MarketStatusInvalidForNewLP(
        6205, "MarketStatusInvalidForNewLP"
    );
  }

  record InvalidMarkTwapUpdateDetected(int code, String msg) implements DriftError {

    public static final InvalidMarkTwapUpdateDetected INSTANCE = new InvalidMarkTwapUpdateDetected(
        6206, "InvalidMarkTwapUpdateDetected"
    );
  }

  record MarketSettlementAttemptOnActiveMarket(int code, String msg) implements DriftError {

    public static final MarketSettlementAttemptOnActiveMarket INSTANCE = new MarketSettlementAttemptOnActiveMarket(
        6207, "MarketSettlementAttemptOnActiveMarket"
    );
  }

  record MarketSettlementRequiresSettledLP(int code, String msg) implements DriftError {

    public static final MarketSettlementRequiresSettledLP INSTANCE = new MarketSettlementRequiresSettledLP(
        6208, "MarketSettlementRequiresSettledLP"
    );
  }

  record MarketSettlementAttemptTooEarly(int code, String msg) implements DriftError {

    public static final MarketSettlementAttemptTooEarly INSTANCE = new MarketSettlementAttemptTooEarly(
        6209, "MarketSettlementAttemptTooEarly"
    );
  }

  record MarketSettlementTargetPriceInvalid(int code, String msg) implements DriftError {

    public static final MarketSettlementTargetPriceInvalid INSTANCE = new MarketSettlementTargetPriceInvalid(
        6210, "MarketSettlementTargetPriceInvalid"
    );
  }

  record UnsupportedSpotMarket(int code, String msg) implements DriftError {

    public static final UnsupportedSpotMarket INSTANCE = new UnsupportedSpotMarket(
        6211, "UnsupportedSpotMarket"
    );
  }

  record SpotOrdersDisabled(int code, String msg) implements DriftError {

    public static final SpotOrdersDisabled INSTANCE = new SpotOrdersDisabled(
        6212, "SpotOrdersDisabled"
    );
  }

  record MarketBeingInitialized(int code, String msg) implements DriftError {

    public static final MarketBeingInitialized INSTANCE = new MarketBeingInitialized(
        6213, "Market Being Initialized"
    );
  }

  record InvalidUserSubAccountId(int code, String msg) implements DriftError {

    public static final InvalidUserSubAccountId INSTANCE = new InvalidUserSubAccountId(
        6214, "Invalid Sub Account Id"
    );
  }

  record InvalidTriggerOrderCondition(int code, String msg) implements DriftError {

    public static final InvalidTriggerOrderCondition INSTANCE = new InvalidTriggerOrderCondition(
        6215, "Invalid Trigger Order Condition"
    );
  }

  record InvalidSpotPosition(int code, String msg) implements DriftError {

    public static final InvalidSpotPosition INSTANCE = new InvalidSpotPosition(
        6216, "Invalid Spot Position"
    );
  }

  record CantTransferBetweenSameUserAccount(int code, String msg) implements DriftError {

    public static final CantTransferBetweenSameUserAccount INSTANCE = new CantTransferBetweenSameUserAccount(
        6217, "Cant transfer between same user account"
    );
  }

  record InvalidPerpPosition(int code, String msg) implements DriftError {

    public static final InvalidPerpPosition INSTANCE = new InvalidPerpPosition(
        6218, "Invalid Perp Position"
    );
  }

  record UnableToGetLimitPrice(int code, String msg) implements DriftError {

    public static final UnableToGetLimitPrice INSTANCE = new UnableToGetLimitPrice(
        6219, "Unable To Get Limit Price"
    );
  }

  record InvalidLiquidation(int code, String msg) implements DriftError {

    public static final InvalidLiquidation INSTANCE = new InvalidLiquidation(
        6220, "Invalid Liquidation"
    );
  }

  record SpotFulfillmentConfigDisabled(int code, String msg) implements DriftError {

    public static final SpotFulfillmentConfigDisabled INSTANCE = new SpotFulfillmentConfigDisabled(
        6221, "Spot Fulfillment Config Disabled"
    );
  }

  record InvalidMaker(int code, String msg) implements DriftError {

    public static final InvalidMaker INSTANCE = new InvalidMaker(
        6222, "Invalid Maker"
    );
  }

  record FailedUnwrap(int code, String msg) implements DriftError {

    public static final FailedUnwrap INSTANCE = new FailedUnwrap(
        6223, "Failed Unwrap"
    );
  }

  record MaxNumberOfUsers(int code, String msg) implements DriftError {

    public static final MaxNumberOfUsers INSTANCE = new MaxNumberOfUsers(
        6224, "Max Number Of Users"
    );
  }

  record InvalidOracleForSettlePnl(int code, String msg) implements DriftError {

    public static final InvalidOracleForSettlePnl INSTANCE = new InvalidOracleForSettlePnl(
        6225, "InvalidOracleForSettlePnl"
    );
  }

  record MarginOrdersOpen(int code, String msg) implements DriftError {

    public static final MarginOrdersOpen INSTANCE = new MarginOrdersOpen(
        6226, "MarginOrdersOpen"
    );
  }

  record TierViolationLiquidatingPerpPnl(int code, String msg) implements DriftError {

    public static final TierViolationLiquidatingPerpPnl INSTANCE = new TierViolationLiquidatingPerpPnl(
        6227, "TierViolationLiquidatingPerpPnl"
    );
  }

  record CouldNotLoadUserData(int code, String msg) implements DriftError {

    public static final CouldNotLoadUserData INSTANCE = new CouldNotLoadUserData(
        6228, "CouldNotLoadUserData"
    );
  }

  record UserWrongMutability(int code, String msg) implements DriftError {

    public static final UserWrongMutability INSTANCE = new UserWrongMutability(
        6229, "UserWrongMutability"
    );
  }

  record InvalidUserAccount(int code, String msg) implements DriftError {

    public static final InvalidUserAccount INSTANCE = new InvalidUserAccount(
        6230, "InvalidUserAccount"
    );
  }

  record CouldNotLoadUserStatsData(int code, String msg) implements DriftError {

    public static final CouldNotLoadUserStatsData INSTANCE = new CouldNotLoadUserStatsData(
        6231, "CouldNotLoadUserData"
    );
  }

  record UserStatsWrongMutability(int code, String msg) implements DriftError {

    public static final UserStatsWrongMutability INSTANCE = new UserStatsWrongMutability(
        6232, "UserWrongMutability"
    );
  }

  record InvalidUserStatsAccount(int code, String msg) implements DriftError {

    public static final InvalidUserStatsAccount INSTANCE = new InvalidUserStatsAccount(
        6233, "InvalidUserAccount"
    );
  }

  record UserNotFound(int code, String msg) implements DriftError {

    public static final UserNotFound INSTANCE = new UserNotFound(
        6234, "UserNotFound"
    );
  }

  record UnableToLoadUserAccount(int code, String msg) implements DriftError {

    public static final UnableToLoadUserAccount INSTANCE = new UnableToLoadUserAccount(
        6235, "UnableToLoadUserAccount"
    );
  }

  record UserStatsNotFound(int code, String msg) implements DriftError {

    public static final UserStatsNotFound INSTANCE = new UserStatsNotFound(
        6236, "UserStatsNotFound"
    );
  }

  record UnableToLoadUserStatsAccount(int code, String msg) implements DriftError {

    public static final UnableToLoadUserStatsAccount INSTANCE = new UnableToLoadUserStatsAccount(
        6237, "UnableToLoadUserStatsAccount"
    );
  }

  record UserNotInactive(int code, String msg) implements DriftError {

    public static final UserNotInactive INSTANCE = new UserNotInactive(
        6238, "User Not Inactive"
    );
  }

  record RevertFill(int code, String msg) implements DriftError {

    public static final RevertFill INSTANCE = new RevertFill(
        6239, "RevertFill"
    );
  }

  record InvalidMarketAccountforDeletion(int code, String msg) implements DriftError {

    public static final InvalidMarketAccountforDeletion INSTANCE = new InvalidMarketAccountforDeletion(
        6240, "Invalid MarketAccount for Deletion"
    );
  }

  record InvalidSpotFulfillmentParams(int code, String msg) implements DriftError {

    public static final InvalidSpotFulfillmentParams INSTANCE = new InvalidSpotFulfillmentParams(
        6241, "Invalid Spot Fulfillment Params"
    );
  }

  record FailedToGetMint(int code, String msg) implements DriftError {

    public static final FailedToGetMint INSTANCE = new FailedToGetMint(
        6242, "Failed to Get Mint"
    );
  }

  record FailedPhoenixCPI(int code, String msg) implements DriftError {

    public static final FailedPhoenixCPI INSTANCE = new FailedPhoenixCPI(
        6243, "FailedPhoenixCPI"
    );
  }

  record FailedToDeserializePhoenixMarket(int code, String msg) implements DriftError {

    public static final FailedToDeserializePhoenixMarket INSTANCE = new FailedToDeserializePhoenixMarket(
        6244, "FailedToDeserializePhoenixMarket"
    );
  }

  record InvalidPricePrecision(int code, String msg) implements DriftError {

    public static final InvalidPricePrecision INSTANCE = new InvalidPricePrecision(
        6245, "InvalidPricePrecision"
    );
  }

  record InvalidPhoenixProgram(int code, String msg) implements DriftError {

    public static final InvalidPhoenixProgram INSTANCE = new InvalidPhoenixProgram(
        6246, "InvalidPhoenixProgram"
    );
  }

  record InvalidPhoenixMarket(int code, String msg) implements DriftError {

    public static final InvalidPhoenixMarket INSTANCE = new InvalidPhoenixMarket(
        6247, "InvalidPhoenixMarket"
    );
  }

  record InvalidSwap(int code, String msg) implements DriftError {

    public static final InvalidSwap INSTANCE = new InvalidSwap(
        6248, "InvalidSwap"
    );
  }

  record SwapLimitPriceBreached(int code, String msg) implements DriftError {

    public static final SwapLimitPriceBreached INSTANCE = new SwapLimitPriceBreached(
        6249, "SwapLimitPriceBreached"
    );
  }

  record SpotMarketReduceOnly(int code, String msg) implements DriftError {

    public static final SpotMarketReduceOnly INSTANCE = new SpotMarketReduceOnly(
        6250, "SpotMarketReduceOnly"
    );
  }

  record FundingWasNotUpdated(int code, String msg) implements DriftError {

    public static final FundingWasNotUpdated INSTANCE = new FundingWasNotUpdated(
        6251, "FundingWasNotUpdated"
    );
  }

  record ImpossibleFill(int code, String msg) implements DriftError {

    public static final ImpossibleFill INSTANCE = new ImpossibleFill(
        6252, "ImpossibleFill"
    );
  }

  record CantUpdatePerpBidAskTwap(int code, String msg) implements DriftError {

    public static final CantUpdatePerpBidAskTwap INSTANCE = new CantUpdatePerpBidAskTwap(
        6253, "CantUpdatePerpBidAskTwap"
    );
  }

  record UserReduceOnly(int code, String msg) implements DriftError {

    public static final UserReduceOnly INSTANCE = new UserReduceOnly(
        6254, "UserReduceOnly"
    );
  }

  record InvalidMarginCalculation(int code, String msg) implements DriftError {

    public static final InvalidMarginCalculation INSTANCE = new InvalidMarginCalculation(
        6255, "InvalidMarginCalculation"
    );
  }

  record CantPayUserInitFee(int code, String msg) implements DriftError {

    public static final CantPayUserInitFee INSTANCE = new CantPayUserInitFee(
        6256, "CantPayUserInitFee"
    );
  }

  record CantReclaimRent(int code, String msg) implements DriftError {

    public static final CantReclaimRent INSTANCE = new CantReclaimRent(
        6257, "CantReclaimRent"
    );
  }

  record InsuranceFundOperationPaused(int code, String msg) implements DriftError {

    public static final InsuranceFundOperationPaused INSTANCE = new InsuranceFundOperationPaused(
        6258, "InsuranceFundOperationPaused"
    );
  }

  record NoUnsettledPnl(int code, String msg) implements DriftError {

    public static final NoUnsettledPnl INSTANCE = new NoUnsettledPnl(
        6259, "NoUnsettledPnl"
    );
  }

  record PnlPoolCantSettleUser(int code, String msg) implements DriftError {

    public static final PnlPoolCantSettleUser INSTANCE = new PnlPoolCantSettleUser(
        6260, "PnlPoolCantSettleUser"
    );
  }

  record OracleNonPositive(int code, String msg) implements DriftError {

    public static final OracleNonPositive INSTANCE = new OracleNonPositive(
        6261, "OracleInvalid"
    );
  }

  record OracleTooVolatile(int code, String msg) implements DriftError {

    public static final OracleTooVolatile INSTANCE = new OracleTooVolatile(
        6262, "OracleTooVolatile"
    );
  }

  record OracleTooUncertain(int code, String msg) implements DriftError {

    public static final OracleTooUncertain INSTANCE = new OracleTooUncertain(
        6263, "OracleTooUncertain"
    );
  }

  record OracleStaleForMargin(int code, String msg) implements DriftError {

    public static final OracleStaleForMargin INSTANCE = new OracleStaleForMargin(
        6264, "OracleStaleForMargin"
    );
  }

  record OracleInsufficientDataPoints(int code, String msg) implements DriftError {

    public static final OracleInsufficientDataPoints INSTANCE = new OracleInsufficientDataPoints(
        6265, "OracleInsufficientDataPoints"
    );
  }

  record OracleStaleForAMM(int code, String msg) implements DriftError {

    public static final OracleStaleForAMM INSTANCE = new OracleStaleForAMM(
        6266, "OracleStaleForAMM"
    );
  }

  record UnableToParsePullOracleMessage(int code, String msg) implements DriftError {

    public static final UnableToParsePullOracleMessage INSTANCE = new UnableToParsePullOracleMessage(
        6267, "Unable to parse pull oracle message"
    );
  }

  record MaxBorrows(int code, String msg) implements DriftError {

    public static final MaxBorrows INSTANCE = new MaxBorrows(
        6268, "Can not borow more than max borrows"
    );
  }

  record OracleUpdatesNotMonotonic(int code, String msg) implements DriftError {

    public static final OracleUpdatesNotMonotonic INSTANCE = new OracleUpdatesNotMonotonic(
        6269, "Updates must be monotonically increasing"
    );
  }

  record OraclePriceFeedMessageMismatch(int code, String msg) implements DriftError {

    public static final OraclePriceFeedMessageMismatch INSTANCE = new OraclePriceFeedMessageMismatch(
        6270, "Trying to update price feed with the wrong feed id"
    );
  }

  record OracleUnsupportedMessageType(int code, String msg) implements DriftError {

    public static final OracleUnsupportedMessageType INSTANCE = new OracleUnsupportedMessageType(
        6271, "The message in the update must be a PriceFeedMessage"
    );
  }

  record OracleDeserializeMessageFailed(int code, String msg) implements DriftError {

    public static final OracleDeserializeMessageFailed INSTANCE = new OracleDeserializeMessageFailed(
        6272, "Could not deserialize the message in the update"
    );
  }

  record OracleWrongGuardianSetOwner(int code, String msg) implements DriftError {

    public static final OracleWrongGuardianSetOwner INSTANCE = new OracleWrongGuardianSetOwner(
        6273, "Wrong guardian set owner in update price atomic"
    );
  }

  record OracleWrongWriteAuthority(int code, String msg) implements DriftError {

    public static final OracleWrongWriteAuthority INSTANCE = new OracleWrongWriteAuthority(
        6274, "Oracle post update atomic price feed account must be drift program"
    );
  }

  record OracleWrongVaaOwner(int code, String msg) implements DriftError {

    public static final OracleWrongVaaOwner INSTANCE = new OracleWrongVaaOwner(
        6275, "Oracle vaa owner must be wormhole program"
    );
  }

  record OracleTooManyPriceAccountUpdates(int code, String msg) implements DriftError {

    public static final OracleTooManyPriceAccountUpdates INSTANCE = new OracleTooManyPriceAccountUpdates(
        6276, "Multi updates must have 2 or fewer accounts passed in remaining accounts"
    );
  }

  record OracleMismatchedVaaAndPriceUpdates(int code, String msg) implements DriftError {

    public static final OracleMismatchedVaaAndPriceUpdates INSTANCE = new OracleMismatchedVaaAndPriceUpdates(
        6277, "Don't have the same remaining accounts number and pyth updates left"
    );
  }

  record OracleBadRemainingAccountPublicKey(int code, String msg) implements DriftError {

    public static final OracleBadRemainingAccountPublicKey INSTANCE = new OracleBadRemainingAccountPublicKey(
        6278, "Remaining account passed does not match oracle update derived pda"
    );
  }

  record FailedOpenbookV2CPI(int code, String msg) implements DriftError {

    public static final FailedOpenbookV2CPI INSTANCE = new FailedOpenbookV2CPI(
        6279, "FailedOpenbookV2CPI"
    );
  }

  record InvalidOpenbookV2Program(int code, String msg) implements DriftError {

    public static final InvalidOpenbookV2Program INSTANCE = new InvalidOpenbookV2Program(
        6280, "InvalidOpenbookV2Program"
    );
  }

  record InvalidOpenbookV2Market(int code, String msg) implements DriftError {

    public static final InvalidOpenbookV2Market INSTANCE = new InvalidOpenbookV2Market(
        6281, "InvalidOpenbookV2Market"
    );
  }

  record NonZeroTransferFee(int code, String msg) implements DriftError {

    public static final NonZeroTransferFee INSTANCE = new NonZeroTransferFee(
        6282, "Non zero transfer fee"
    );
  }

  record LiquidationOrderFailedToFill(int code, String msg) implements DriftError {

    public static final LiquidationOrderFailedToFill INSTANCE = new LiquidationOrderFailedToFill(
        6283, "Liquidation order failed to fill"
    );
  }

  record InvalidPredictionMarketOrder(int code, String msg) implements DriftError {

    public static final InvalidPredictionMarketOrder INSTANCE = new InvalidPredictionMarketOrder(
        6284, "Invalid prediction market order"
    );
  }

  record InvalidVerificationIxIndex(int code, String msg) implements DriftError {

    public static final InvalidVerificationIxIndex INSTANCE = new InvalidVerificationIxIndex(
        6285, "Ed25519 Ix must be before place and make SignedMsg order ix"
    );
  }

  record SigVerificationFailed(int code, String msg) implements DriftError {

    public static final SigVerificationFailed INSTANCE = new SigVerificationFailed(
        6286, "SignedMsg message verificaiton failed"
    );
  }

  record MismatchedSignedMsgOrderParamsMarketIndex(int code, String msg) implements DriftError {

    public static final MismatchedSignedMsgOrderParamsMarketIndex INSTANCE = new MismatchedSignedMsgOrderParamsMarketIndex(
        6287, "Market index mismatched b/w taker and maker SignedMsg order params"
    );
  }

  record InvalidSignedMsgOrderParam(int code, String msg) implements DriftError {

    public static final InvalidSignedMsgOrderParam INSTANCE = new InvalidSignedMsgOrderParam(
        6288, "Invalid SignedMsg order param"
    );
  }

  record PlaceAndTakeOrderSuccessConditionFailed(int code, String msg) implements DriftError {

    public static final PlaceAndTakeOrderSuccessConditionFailed INSTANCE = new PlaceAndTakeOrderSuccessConditionFailed(
        6289, "Place and take order success condition failed"
    );
  }

  record InvalidHighLeverageModeConfig(int code, String msg) implements DriftError {

    public static final InvalidHighLeverageModeConfig INSTANCE = new InvalidHighLeverageModeConfig(
        6290, "Invalid High Leverage Mode Config"
    );
  }

  record InvalidRFQUserAccount(int code, String msg) implements DriftError {

    public static final InvalidRFQUserAccount INSTANCE = new InvalidRFQUserAccount(
        6291, "Invalid RFQ User Account"
    );
  }

  record RFQUserAccountWrongMutability(int code, String msg) implements DriftError {

    public static final RFQUserAccountWrongMutability INSTANCE = new RFQUserAccountWrongMutability(
        6292, "RFQUserAccount should be mutable"
    );
  }

  record RFQUserAccountFull(int code, String msg) implements DriftError {

    public static final RFQUserAccountFull INSTANCE = new RFQUserAccountFull(
        6293, "RFQUserAccount has too many active RFQs"
    );
  }

  record RFQOrderNotFilled(int code, String msg) implements DriftError {

    public static final RFQOrderNotFilled INSTANCE = new RFQOrderNotFilled(
        6294, "RFQ order not filled as expected"
    );
  }

  record InvalidRFQOrder(int code, String msg) implements DriftError {

    public static final InvalidRFQOrder INSTANCE = new InvalidRFQOrder(
        6295, "RFQ orders must be jit makers"
    );
  }

  record InvalidRFQMatch(int code, String msg) implements DriftError {

    public static final InvalidRFQMatch INSTANCE = new InvalidRFQMatch(
        6296, "RFQ matches must be valid"
    );
  }

  record InvalidSignedMsgUserAccount(int code, String msg) implements DriftError {

    public static final InvalidSignedMsgUserAccount INSTANCE = new InvalidSignedMsgUserAccount(
        6297, "Invalid SignedMsg user account"
    );
  }

  record SignedMsgUserAccountWrongMutability(int code, String msg) implements DriftError {

    public static final SignedMsgUserAccountWrongMutability INSTANCE = new SignedMsgUserAccountWrongMutability(
        6298, "SignedMsg account wrong mutability"
    );
  }

  record SignedMsgUserOrdersAccountFull(int code, String msg) implements DriftError {

    public static final SignedMsgUserOrdersAccountFull INSTANCE = new SignedMsgUserOrdersAccountFull(
        6299, "SignedMsgUserAccount has too many active orders"
    );
  }

  record SignedMsgOrderDoesNotExist(int code, String msg) implements DriftError {

    public static final SignedMsgOrderDoesNotExist INSTANCE = new SignedMsgOrderDoesNotExist(
        6300, "Order with SignedMsg uuid does not exist"
    );
  }

  record InvalidSignedMsgOrderId(int code, String msg) implements DriftError {

    public static final InvalidSignedMsgOrderId INSTANCE = new InvalidSignedMsgOrderId(
        6301, "SignedMsg order id cannot be 0s"
    );
  }

  record InvalidPoolId(int code, String msg) implements DriftError {

    public static final InvalidPoolId INSTANCE = new InvalidPoolId(
        6302, "Invalid pool id"
    );
  }

  record InvalidProtectedMakerModeConfig(int code, String msg) implements DriftError {

    public static final InvalidProtectedMakerModeConfig INSTANCE = new InvalidProtectedMakerModeConfig(
        6303, "Invalid Protected Maker Mode Config"
    );
  }

  record InvalidPythLazerStorageOwner(int code, String msg) implements DriftError {

    public static final InvalidPythLazerStorageOwner INSTANCE = new InvalidPythLazerStorageOwner(
        6304, "Invalid pyth lazer storage owner"
    );
  }

  record UnverifiedPythLazerMessage(int code, String msg) implements DriftError {

    public static final UnverifiedPythLazerMessage INSTANCE = new UnverifiedPythLazerMessage(
        6305, "Verification of pyth lazer message failed"
    );
  }

  record InvalidPythLazerMessage(int code, String msg) implements DriftError {

    public static final InvalidPythLazerMessage INSTANCE = new InvalidPythLazerMessage(
        6306, "Invalid pyth lazer message"
    );
  }

  record PythLazerMessagePriceFeedMismatch(int code, String msg) implements DriftError {

    public static final PythLazerMessagePriceFeedMismatch INSTANCE = new PythLazerMessagePriceFeedMismatch(
        6307, "Pyth lazer message does not correspond to correct fed id"
    );
  }

  record InvalidLiquidateSpotWithSwap(int code, String msg) implements DriftError {

    public static final InvalidLiquidateSpotWithSwap INSTANCE = new InvalidLiquidateSpotWithSwap(
        6308, "InvalidLiquidateSpotWithSwap"
    );
  }

  record SignedMsgUserContextUserMismatch(int code, String msg) implements DriftError {

    public static final SignedMsgUserContextUserMismatch INSTANCE = new SignedMsgUserContextUserMismatch(
        6309, "User in SignedMsg message does not match user in ix context"
    );
  }

  record UserFuelOverflowThresholdNotMet(int code, String msg) implements DriftError {

    public static final UserFuelOverflowThresholdNotMet INSTANCE = new UserFuelOverflowThresholdNotMet(
        6310, "User fuel overflow threshold not met"
    );
  }

  record FuelOverflowAccountNotFound(int code, String msg) implements DriftError {

    public static final FuelOverflowAccountNotFound INSTANCE = new FuelOverflowAccountNotFound(
        6311, "FuelOverflow account not found"
    );
  }

  record InvalidTransferPerpPosition(int code, String msg) implements DriftError {

    public static final InvalidTransferPerpPosition INSTANCE = new InvalidTransferPerpPosition(
        6312, "Invalid Transfer Perp Position"
    );
  }

  record InvalidSignedMsgUserOrdersResize(int code, String msg) implements DriftError {

    public static final InvalidSignedMsgUserOrdersResize INSTANCE = new InvalidSignedMsgUserOrdersResize(
        6313, "Invalid SignedMsgUserOrders resize"
    );
  }

  record CouldNotDeserializeHighLeverageModeConfig(int code, String msg) implements DriftError {

    public static final CouldNotDeserializeHighLeverageModeConfig INSTANCE = new CouldNotDeserializeHighLeverageModeConfig(
        6314, "Could not deserialize high leverage mode config"
    );
  }

  record InvalidIfRebalanceConfig(int code, String msg) implements DriftError {

    public static final InvalidIfRebalanceConfig INSTANCE = new InvalidIfRebalanceConfig(
        6315, "Invalid If Rebalance Config"
    );
  }

  record InvalidIfRebalanceSwap(int code, String msg) implements DriftError {

    public static final InvalidIfRebalanceSwap INSTANCE = new InvalidIfRebalanceSwap(
        6316, "Invalid If Rebalance Swap"
    );
  }

  record InvalidRevenueShareResize(int code, String msg) implements DriftError {

    public static final InvalidRevenueShareResize INSTANCE = new InvalidRevenueShareResize(
        6317, "Invalid RevenueShare resize"
    );
  }

  record BuilderRevoked(int code, String msg) implements DriftError {

    public static final BuilderRevoked INSTANCE = new BuilderRevoked(
        6318, "Builder has been revoked"
    );
  }

  record InvalidBuilderFee(int code, String msg) implements DriftError {

    public static final InvalidBuilderFee INSTANCE = new InvalidBuilderFee(
        6319, "Builder fee is greater than max fee bps"
    );
  }

  record RevenueShareEscrowAuthorityMismatch(int code, String msg) implements DriftError {

    public static final RevenueShareEscrowAuthorityMismatch INSTANCE = new RevenueShareEscrowAuthorityMismatch(
        6320, "RevenueShareEscrow authority mismatch"
    );
  }

  record RevenueShareEscrowOrdersAccountFull(int code, String msg) implements DriftError {

    public static final RevenueShareEscrowOrdersAccountFull INSTANCE = new RevenueShareEscrowOrdersAccountFull(
        6321, "RevenueShareEscrow has too many active orders"
    );
  }

  record InvalidRevenueShareAccount(int code, String msg) implements DriftError {

    public static final InvalidRevenueShareAccount INSTANCE = new InvalidRevenueShareAccount(
        6322, "Invalid RevenueShareAccount"
    );
  }

  record CannotRevokeBuilderWithOpenOrders(int code, String msg) implements DriftError {

    public static final CannotRevokeBuilderWithOpenOrders INSTANCE = new CannotRevokeBuilderWithOpenOrders(
        6323, "Cannot revoke builder with open orders"
    );
  }

  record UnableToLoadRevenueShareAccount(int code, String msg) implements DriftError {

    public static final UnableToLoadRevenueShareAccount INSTANCE = new UnableToLoadRevenueShareAccount(
        6324, "Unable to load builder account"
    );
  }

  record InvalidConstituent(int code, String msg) implements DriftError {

    public static final InvalidConstituent INSTANCE = new InvalidConstituent(
        6325, "Invalid Constituent"
    );
  }

  record InvalidAmmConstituentMappingArgument(int code, String msg) implements DriftError {

    public static final InvalidAmmConstituentMappingArgument INSTANCE = new InvalidAmmConstituentMappingArgument(
        6326, "Invalid Amm Constituent Mapping argument"
    );
  }

  record ConstituentNotFound(int code, String msg) implements DriftError {

    public static final ConstituentNotFound INSTANCE = new ConstituentNotFound(
        6327, "Constituent not found"
    );
  }

  record ConstituentCouldNotLoad(int code, String msg) implements DriftError {

    public static final ConstituentCouldNotLoad INSTANCE = new ConstituentCouldNotLoad(
        6328, "Constituent could not load"
    );
  }

  record ConstituentWrongMutability(int code, String msg) implements DriftError {

    public static final ConstituentWrongMutability INSTANCE = new ConstituentWrongMutability(
        6329, "Constituent wrong mutability"
    );
  }

  record WrongNumberOfConstituents(int code, String msg) implements DriftError {

    public static final WrongNumberOfConstituents INSTANCE = new WrongNumberOfConstituents(
        6330, "Wrong number of constituents passed to instruction"
    );
  }

  record InsufficientConstituentTokenBalance(int code, String msg) implements DriftError {

    public static final InsufficientConstituentTokenBalance INSTANCE = new InsufficientConstituentTokenBalance(
        6331, "Insufficient constituent token balance"
    );
  }

  record AMMCacheStale(int code, String msg) implements DriftError {

    public static final AMMCacheStale INSTANCE = new AMMCacheStale(
        6332, "Amm Cache data too stale"
    );
  }

  record LpPoolAumDelayed(int code, String msg) implements DriftError {

    public static final LpPoolAumDelayed INSTANCE = new LpPoolAumDelayed(
        6333, "LP Pool AUM not updated recently"
    );
  }

  record ConstituentOracleStale(int code, String msg) implements DriftError {

    public static final ConstituentOracleStale INSTANCE = new ConstituentOracleStale(
        6334, "Constituent oracle is stale"
    );
  }

  record LpInvariantFailed(int code, String msg) implements DriftError {

    public static final LpInvariantFailed INSTANCE = new LpInvariantFailed(
        6335, "LP Invariant failed"
    );
  }

  record InvalidConstituentDerivativeWeights(int code, String msg) implements DriftError {

    public static final InvalidConstituentDerivativeWeights INSTANCE = new InvalidConstituentDerivativeWeights(
        6336, "Invalid constituent derivative weights"
    );
  }

  record MaxDlpAumBreached(int code, String msg) implements DriftError {

    public static final MaxDlpAumBreached INSTANCE = new MaxDlpAumBreached(
        6337, "Max DLP AUM Breached"
    );
  }

  record SettleLpPoolDisabled(int code, String msg) implements DriftError {

    public static final SettleLpPoolDisabled INSTANCE = new SettleLpPoolDisabled(
        6338, "Settle Lp Pool Disabled"
    );
  }

  record MintRedeemLpPoolDisabled(int code, String msg) implements DriftError {

    public static final MintRedeemLpPoolDisabled INSTANCE = new MintRedeemLpPoolDisabled(
        6339, "Mint/Redeem Lp Pool Disabled"
    );
  }

  record LpPoolSettleInvariantBreached(int code, String msg) implements DriftError {

    public static final LpPoolSettleInvariantBreached INSTANCE = new LpPoolSettleInvariantBreached(
        6340, "Settlement amount exceeded"
    );
  }

  record InvalidConstituentOperation(int code, String msg) implements DriftError {

    public static final InvalidConstituentOperation INSTANCE = new InvalidConstituentOperation(
        6341, "Invalid constituent operation"
    );
  }

  record Unauthorized(int code, String msg) implements DriftError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6342, "Unauthorized for operation"
    );
  }

  record InvalidLpPoolId(int code, String msg) implements DriftError {

    public static final InvalidLpPoolId INSTANCE = new InvalidLpPoolId(
        6343, "Invalid Lp Pool Id for Operation"
    );
  }
}
