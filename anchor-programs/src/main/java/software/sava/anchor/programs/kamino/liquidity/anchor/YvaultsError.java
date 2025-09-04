package software.sava.anchor.programs.kamino.liquidity.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface YvaultsError extends ProgramError permits
    YvaultsError.IntegerOverflow,
    YvaultsError.OperationForbidden,
    YvaultsError.ZeroAmount,
    YvaultsError.UnableToDeserializeAccount,
    YvaultsError.VaultBalanceDoesNotMatchTokenA,
    YvaultsError.VaultBalanceDoesNotMatchTokenB,
    YvaultsError.SharesIssuedAmountDoesNotMatch,
    YvaultsError.GlobalConfigKeyError,
    YvaultsError.SystemInEmergencyMode,
    YvaultsError.GlobalDepositBlocked,
    YvaultsError.GlobalWithdrawBlocked,
    YvaultsError.GlobalInvestBlocked,
    YvaultsError.OutOfRangeIntegralConversion,
    YvaultsError.MathOverflow,
    YvaultsError.TooMuchLiquidityToWithdraw,
    YvaultsError.DepositAmountsZero,
    YvaultsError.SharesZero,
    YvaultsError.StrategyNotActive,
    YvaultsError.UnharvestedAmounts,
    YvaultsError.InvalidRewardMapping,
    YvaultsError.InvalidRewardIndex,
    YvaultsError.OwnRewardUninitialized,
    YvaultsError.PriceNotValid,
    YvaultsError.SwapRewardImbalanced,
    YvaultsError.SwapRewardTooSmall,
    YvaultsError.SwapRewardLessThanRequested,
    YvaultsError.SwapRewardLessThanMinimum,
    YvaultsError.WrongDiscriminator,
    YvaultsError.WrongMint,
    YvaultsError.WrongVault,
    YvaultsError.SwapAmountsZero,
    YvaultsError.PriceTooOld,
    YvaultsError.CannotInvestZeroAmount,
    YvaultsError.MaxInvestableZero,
    YvaultsError.CollectFeesBlocked,
    YvaultsError.CollectRewardsBlocked,
    YvaultsError.SwapRewardsBlocked,
    YvaultsError.WrongRewardCollateralID,
    YvaultsError.InvalidPositionAccount,
    YvaultsError.CouldNotDeserializeScope,
    YvaultsError.WrongCollateralID,
    YvaultsError.CollateralTokensExceedDepositCap,
    YvaultsError.SwapUnevenVaultsBlocked,
    YvaultsError.VaultsAreAlreadyBalanced,
    YvaultsError.CannotSwapUnevenOutOfRange,
    YvaultsError.DivideByZero,
    YvaultsError.DeltaATooLarge,
    YvaultsError.DeltaBTooLarge,
    YvaultsError.CannotExecutiveWithdrawZeroAmount,
    YvaultsError.CannotWithdrawZeroAmount,
    YvaultsError.CannotCollectFeesOnZeroLiquidityPosition,
    YvaultsError.StrategyNotActiveWhenDepositing,
    YvaultsError.StrategyNotActiveWhenOpeningPosition,
    YvaultsError.CollateralTokensExceedDepositCapPerIxn,
    YvaultsError.CannotDepositOutOfRange,
    YvaultsError.CannotInvestOutOfRange,
    YvaultsError.WithdrawalCapReached,
    YvaultsError.TimestampDecrease,
    YvaultsError.CPINotAllowed,
    YvaultsError.OrcaPriceTooDifferentFromScope,
    YvaultsError.LowerTickLargerThanUpperTick,
    YvaultsError.LowerTickTooLow,
    YvaultsError.UpperTickTooLarge,
    YvaultsError.LowerTickNotMultipleOfTickSpacing,
    YvaultsError.UpperTickNotMultipleOfTickSpacing,
    YvaultsError.CannotChangeAdminAuthority,
    YvaultsError.CannotResizeAccount,
    YvaultsError.ScopeChainUpdateFailed,
    YvaultsError.PriceTooDivergentFromTwap,
    YvaultsError.ExistingRewardOverride,
    YvaultsError.WrongKaminoRewardId,
    YvaultsError.KaminoRewardNotExist,
    YvaultsError.KaminoRewardAlreadyExists,
    YvaultsError.KaminoCollateralNotValid,
    YvaultsError.KaminoRewardExceedsAvailableAmount,
    YvaultsError.SwapUnevenVaultsOvershoot,
    YvaultsError.BpsNotInRange,
    YvaultsError.EmergencySwapBlocked,
    YvaultsError.StrategyNotFrozen,
    YvaultsError.UnexpectedTokenAmountsPostSwap,
    YvaultsError.AccountNotBelongToDEX,
    YvaultsError.WrongDEXProgramID,
    YvaultsError.OrcaRewardUninitialized,
    YvaultsError.InvalidAdminAuthority,
    YvaultsError.PriceIsBiggerThanHeuristic,
    YvaultsError.PriceIsLowerThanHeuristic,
    YvaultsError.AccountDifferentThanExpected,
    YvaultsError.SwapAmountsTooSmall,
    YvaultsError.InvalidDexProgramId,
    YvaultsError.StrategyDepositBlocked,
    YvaultsError.StrategyInvestBlocked,
    YvaultsError.StrategyWithdrawBlocked,
    YvaultsError.WrongSwapVaultDirection,
    YvaultsError.SwapVaultsTooBig,
    YvaultsError.SwapVaultsCashOutputBelowMinimum,
    YvaultsError.FlashIxsNotEnded,
    YvaultsError.FlashTxWithUnexpectedIxs,
    YvaultsError.FlashIxsAccountMismatch,
    YvaultsError.FlashIxsIncludeScope,
    YvaultsError.FlashVaultSwapBlocked,
    YvaultsError.FlashVaultSwapWrongAmountToLeave,
    YvaultsError.DepositLessThanMinimum,
    YvaultsError.DepositWithoutInvestDisallowed,
    YvaultsError.InvalidScopeChain,
    YvaultsError.InvalidScopeTWAPChain,
    YvaultsError.PositionHasRemainingLiquidity,
    YvaultsError.PoolRebalancing,
    YvaultsError.PermissionlessRebalancingDisabled,
    YvaultsError.ManualRebalanceInvalidOwner,
    YvaultsError.InvalidRebalanceType,
    YvaultsError.NoRebalanceNecessary,
    YvaultsError.TickArraysDoNotMatchRebalance,
    YvaultsError.StrategyPositionNotValid,
    YvaultsError.CouldNotDeserializeRebalanceState,
    YvaultsError.CouldNotSerializeRebalanceState,
    YvaultsError.CouldNotDeserializeRebalanceParams,
    YvaultsError.NotEnoughTokensForRatio,
    YvaultsError.AmountsRepresentZeroShares,
    YvaultsError.MaxLossExceeded,
    YvaultsError.RewardNotStrategyToken,
    YvaultsError.DecimalToU64ConversionFailed,
    YvaultsError.DecimalOperationFailed,
    YvaultsError.VaultBalancesCausesWrongSharesIssuance,
    YvaultsError.TokenDisabled,
    YvaultsError.InvalidReferencePriceType,
    YvaultsError.TokenToSwapNotEnough,
    YvaultsError.TokenAccountBalanceMismatch,
    YvaultsError.UnexpectedProgramIdForPrerequisiteIx,
    YvaultsError.ComputeFeesAndRewardsUpdateError,
    YvaultsError.SharesNotZero,
    YvaultsError.InvalidScopeStakingRateChain,
    YvaultsError.StakingRateNotValid,
    YvaultsError.DecimalToU128ConversionFailed,
    YvaultsError.DecimalNegativeSqrtRoot,
    YvaultsError.DriftingOppositeDirection,
    YvaultsError.WrongRewardCollateralId2,
    YvaultsError.CollateralInfoAlreadyExists,
    YvaultsError.InvestTooEarly,
    YvaultsError.SwapUnevenTooEarly,
    YvaultsError.FlashSwapTooEarly,
    YvaultsError.RebalancesCapReached,
    YvaultsError.SwapUnevenInvalidAuthority,
    YvaultsError.InvalidTick,
    YvaultsError.MeteoraMathOverflow,
    YvaultsError.StrategyTickArrayNotValid,
    YvaultsError.WrongEventAuthority,
    YvaultsError.StrategyFieldUpdateNotAllowed,
    YvaultsError.UnsupportedDex,
    YvaultsError.InvalidBPSValue,
    YvaultsError.RewardVaultOverrideNotAllowed,
    YvaultsError.ComputeFeesAndRewardsInvalidReward,
    YvaultsError.EmptyTreasury,
    YvaultsError.ChangingPoolRewardMintMismatch,
    YvaultsError.ProvidedRewardVaultMismatch,
    YvaultsError.RepeatedMint,
    YvaultsError.UnsupportedTokenExtension,
    YvaultsError.UnsupportedDexForToken22 {

  static YvaultsError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> IntegerOverflow.INSTANCE;
      case 6001 -> OperationForbidden.INSTANCE;
      case 6002 -> ZeroAmount.INSTANCE;
      case 6003 -> UnableToDeserializeAccount.INSTANCE;
      case 6004 -> VaultBalanceDoesNotMatchTokenA.INSTANCE;
      case 6005 -> VaultBalanceDoesNotMatchTokenB.INSTANCE;
      case 6006 -> SharesIssuedAmountDoesNotMatch.INSTANCE;
      case 6007 -> GlobalConfigKeyError.INSTANCE;
      case 6008 -> SystemInEmergencyMode.INSTANCE;
      case 6009 -> GlobalDepositBlocked.INSTANCE;
      case 6010 -> GlobalWithdrawBlocked.INSTANCE;
      case 6011 -> GlobalInvestBlocked.INSTANCE;
      case 6012 -> OutOfRangeIntegralConversion.INSTANCE;
      case 6013 -> MathOverflow.INSTANCE;
      case 6014 -> TooMuchLiquidityToWithdraw.INSTANCE;
      case 6015 -> DepositAmountsZero.INSTANCE;
      case 6016 -> SharesZero.INSTANCE;
      case 6017 -> StrategyNotActive.INSTANCE;
      case 6018 -> UnharvestedAmounts.INSTANCE;
      case 6019 -> InvalidRewardMapping.INSTANCE;
      case 6020 -> InvalidRewardIndex.INSTANCE;
      case 6021 -> OwnRewardUninitialized.INSTANCE;
      case 6022 -> PriceNotValid.INSTANCE;
      case 6023 -> SwapRewardImbalanced.INSTANCE;
      case 6024 -> SwapRewardTooSmall.INSTANCE;
      case 6025 -> SwapRewardLessThanRequested.INSTANCE;
      case 6026 -> SwapRewardLessThanMinimum.INSTANCE;
      case 6027 -> WrongDiscriminator.INSTANCE;
      case 6028 -> WrongMint.INSTANCE;
      case 6029 -> WrongVault.INSTANCE;
      case 6030 -> SwapAmountsZero.INSTANCE;
      case 6031 -> PriceTooOld.INSTANCE;
      case 6032 -> CannotInvestZeroAmount.INSTANCE;
      case 6033 -> MaxInvestableZero.INSTANCE;
      case 6034 -> CollectFeesBlocked.INSTANCE;
      case 6035 -> CollectRewardsBlocked.INSTANCE;
      case 6036 -> SwapRewardsBlocked.INSTANCE;
      case 6037 -> WrongRewardCollateralID.INSTANCE;
      case 6038 -> InvalidPositionAccount.INSTANCE;
      case 6039 -> CouldNotDeserializeScope.INSTANCE;
      case 6040 -> WrongCollateralID.INSTANCE;
      case 6041 -> CollateralTokensExceedDepositCap.INSTANCE;
      case 6042 -> SwapUnevenVaultsBlocked.INSTANCE;
      case 6043 -> VaultsAreAlreadyBalanced.INSTANCE;
      case 6044 -> CannotSwapUnevenOutOfRange.INSTANCE;
      case 6045 -> DivideByZero.INSTANCE;
      case 6046 -> DeltaATooLarge.INSTANCE;
      case 6047 -> DeltaBTooLarge.INSTANCE;
      case 6048 -> CannotExecutiveWithdrawZeroAmount.INSTANCE;
      case 6049 -> CannotWithdrawZeroAmount.INSTANCE;
      case 6050 -> CannotCollectFeesOnZeroLiquidityPosition.INSTANCE;
      case 6051 -> StrategyNotActiveWhenDepositing.INSTANCE;
      case 6052 -> StrategyNotActiveWhenOpeningPosition.INSTANCE;
      case 6053 -> CollateralTokensExceedDepositCapPerIxn.INSTANCE;
      case 6054 -> CannotDepositOutOfRange.INSTANCE;
      case 6055 -> CannotInvestOutOfRange.INSTANCE;
      case 6056 -> WithdrawalCapReached.INSTANCE;
      case 6057 -> TimestampDecrease.INSTANCE;
      case 6058 -> CPINotAllowed.INSTANCE;
      case 6059 -> OrcaPriceTooDifferentFromScope.INSTANCE;
      case 6060 -> LowerTickLargerThanUpperTick.INSTANCE;
      case 6061 -> LowerTickTooLow.INSTANCE;
      case 6062 -> UpperTickTooLarge.INSTANCE;
      case 6063 -> LowerTickNotMultipleOfTickSpacing.INSTANCE;
      case 6064 -> UpperTickNotMultipleOfTickSpacing.INSTANCE;
      case 6065 -> CannotChangeAdminAuthority.INSTANCE;
      case 6066 -> CannotResizeAccount.INSTANCE;
      case 6067 -> ScopeChainUpdateFailed.INSTANCE;
      case 6068 -> PriceTooDivergentFromTwap.INSTANCE;
      case 6069 -> ExistingRewardOverride.INSTANCE;
      case 6070 -> WrongKaminoRewardId.INSTANCE;
      case 6071 -> KaminoRewardNotExist.INSTANCE;
      case 6072 -> KaminoRewardAlreadyExists.INSTANCE;
      case 6073 -> KaminoCollateralNotValid.INSTANCE;
      case 6074 -> KaminoRewardExceedsAvailableAmount.INSTANCE;
      case 6075 -> SwapUnevenVaultsOvershoot.INSTANCE;
      case 6076 -> BpsNotInRange.INSTANCE;
      case 6077 -> EmergencySwapBlocked.INSTANCE;
      case 6078 -> StrategyNotFrozen.INSTANCE;
      case 6079 -> UnexpectedTokenAmountsPostSwap.INSTANCE;
      case 6080 -> AccountNotBelongToDEX.INSTANCE;
      case 6081 -> WrongDEXProgramID.INSTANCE;
      case 6082 -> OrcaRewardUninitialized.INSTANCE;
      case 6083 -> InvalidAdminAuthority.INSTANCE;
      case 6084 -> PriceIsBiggerThanHeuristic.INSTANCE;
      case 6085 -> PriceIsLowerThanHeuristic.INSTANCE;
      case 6086 -> AccountDifferentThanExpected.INSTANCE;
      case 6087 -> SwapAmountsTooSmall.INSTANCE;
      case 6088 -> InvalidDexProgramId.INSTANCE;
      case 6089 -> StrategyDepositBlocked.INSTANCE;
      case 6090 -> StrategyInvestBlocked.INSTANCE;
      case 6091 -> StrategyWithdrawBlocked.INSTANCE;
      case 6092 -> WrongSwapVaultDirection.INSTANCE;
      case 6093 -> SwapVaultsTooBig.INSTANCE;
      case 6094 -> SwapVaultsCashOutputBelowMinimum.INSTANCE;
      case 6095 -> FlashIxsNotEnded.INSTANCE;
      case 6096 -> FlashTxWithUnexpectedIxs.INSTANCE;
      case 6097 -> FlashIxsAccountMismatch.INSTANCE;
      case 6098 -> FlashIxsIncludeScope.INSTANCE;
      case 6099 -> FlashVaultSwapBlocked.INSTANCE;
      case 6100 -> FlashVaultSwapWrongAmountToLeave.INSTANCE;
      case 6101 -> DepositLessThanMinimum.INSTANCE;
      case 6102 -> DepositWithoutInvestDisallowed.INSTANCE;
      case 6103 -> InvalidScopeChain.INSTANCE;
      case 6104 -> InvalidScopeTWAPChain.INSTANCE;
      case 6105 -> PositionHasRemainingLiquidity.INSTANCE;
      case 6106 -> PoolRebalancing.INSTANCE;
      case 6107 -> PermissionlessRebalancingDisabled.INSTANCE;
      case 6108 -> ManualRebalanceInvalidOwner.INSTANCE;
      case 6109 -> InvalidRebalanceType.INSTANCE;
      case 6110 -> NoRebalanceNecessary.INSTANCE;
      case 6111 -> TickArraysDoNotMatchRebalance.INSTANCE;
      case 6112 -> StrategyPositionNotValid.INSTANCE;
      case 6113 -> CouldNotDeserializeRebalanceState.INSTANCE;
      case 6114 -> CouldNotSerializeRebalanceState.INSTANCE;
      case 6115 -> CouldNotDeserializeRebalanceParams.INSTANCE;
      case 6116 -> NotEnoughTokensForRatio.INSTANCE;
      case 6117 -> AmountsRepresentZeroShares.INSTANCE;
      case 6118 -> MaxLossExceeded.INSTANCE;
      case 6119 -> RewardNotStrategyToken.INSTANCE;
      case 6120 -> DecimalToU64ConversionFailed.INSTANCE;
      case 6121 -> DecimalOperationFailed.INSTANCE;
      case 6122 -> VaultBalancesCausesWrongSharesIssuance.INSTANCE;
      case 6123 -> TokenDisabled.INSTANCE;
      case 6124 -> InvalidReferencePriceType.INSTANCE;
      case 6125 -> TokenToSwapNotEnough.INSTANCE;
      case 6126 -> TokenAccountBalanceMismatch.INSTANCE;
      case 6127 -> UnexpectedProgramIdForPrerequisiteIx.INSTANCE;
      case 6128 -> ComputeFeesAndRewardsUpdateError.INSTANCE;
      case 6129 -> SharesNotZero.INSTANCE;
      case 6130 -> InvalidScopeStakingRateChain.INSTANCE;
      case 6131 -> StakingRateNotValid.INSTANCE;
      case 6132 -> DecimalToU128ConversionFailed.INSTANCE;
      case 6133 -> DecimalNegativeSqrtRoot.INSTANCE;
      case 6134 -> DriftingOppositeDirection.INSTANCE;
      case 6135 -> WrongRewardCollateralId2.INSTANCE;
      case 6136 -> CollateralInfoAlreadyExists.INSTANCE;
      case 6137 -> InvestTooEarly.INSTANCE;
      case 6138 -> SwapUnevenTooEarly.INSTANCE;
      case 6139 -> FlashSwapTooEarly.INSTANCE;
      case 6140 -> RebalancesCapReached.INSTANCE;
      case 6141 -> SwapUnevenInvalidAuthority.INSTANCE;
      case 6142 -> InvalidTick.INSTANCE;
      case 6143 -> MeteoraMathOverflow.INSTANCE;
      case 6144 -> StrategyTickArrayNotValid.INSTANCE;
      case 6145 -> WrongEventAuthority.INSTANCE;
      case 6146 -> StrategyFieldUpdateNotAllowed.INSTANCE;
      case 6147 -> UnsupportedDex.INSTANCE;
      case 6148 -> InvalidBPSValue.INSTANCE;
      case 6149 -> RewardVaultOverrideNotAllowed.INSTANCE;
      case 6150 -> ComputeFeesAndRewardsInvalidReward.INSTANCE;
      case 6151 -> EmptyTreasury.INSTANCE;
      case 6152 -> ChangingPoolRewardMintMismatch.INSTANCE;
      case 6153 -> ProvidedRewardVaultMismatch.INSTANCE;
      case 6154 -> RepeatedMint.INSTANCE;
      case 6155 -> UnsupportedTokenExtension.INSTANCE;
      case 6156 -> UnsupportedDexForToken22.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Yvaults error code: " + errorCode);
    };
  }

  record IntegerOverflow(int code, String msg) implements YvaultsError {

    public static final IntegerOverflow INSTANCE = new IntegerOverflow(
        6000, "Integer overflow"
    );
  }

  record OperationForbidden(int code, String msg) implements YvaultsError {

    public static final OperationForbidden INSTANCE = new OperationForbidden(
        6001, "Operation Forbidden"
    );
  }

  record ZeroAmount(int code, String msg) implements YvaultsError {

    public static final ZeroAmount INSTANCE = new ZeroAmount(
        6002, "[DEPRECATED] Zero amount"
    );
  }

  record UnableToDeserializeAccount(int code, String msg) implements YvaultsError {

    public static final UnableToDeserializeAccount INSTANCE = new UnableToDeserializeAccount(
        6003, "Unable to deserialize account"
    );
  }

  record VaultBalanceDoesNotMatchTokenA(int code, String msg) implements YvaultsError {

    public static final VaultBalanceDoesNotMatchTokenA INSTANCE = new VaultBalanceDoesNotMatchTokenA(
        6004, "[DEPRECATED] Vault balance does not match for token A"
    );
  }

  record VaultBalanceDoesNotMatchTokenB(int code, String msg) implements YvaultsError {

    public static final VaultBalanceDoesNotMatchTokenB INSTANCE = new VaultBalanceDoesNotMatchTokenB(
        6005, "[DEPRECATED] Vault balance does not match for token B"
    );
  }

  record SharesIssuedAmountDoesNotMatch(int code, String msg) implements YvaultsError {

    public static final SharesIssuedAmountDoesNotMatch INSTANCE = new SharesIssuedAmountDoesNotMatch(
        6006, "[DEPRECATED] Shares issued amount does not match"
    );
  }

  record GlobalConfigKeyError(int code, String msg) implements YvaultsError {

    public static final GlobalConfigKeyError INSTANCE = new GlobalConfigKeyError(
        6007, "Key is not present in global config"
    );
  }

  record SystemInEmergencyMode(int code, String msg) implements YvaultsError {

    public static final SystemInEmergencyMode INSTANCE = new SystemInEmergencyMode(
        6008, "[DEPRECATED] System is in emergency mode"
    );
  }

  record GlobalDepositBlocked(int code, String msg) implements YvaultsError {

    public static final GlobalDepositBlocked INSTANCE = new GlobalDepositBlocked(
        6009, "Global deposit is currently blocked"
    );
  }

  record GlobalWithdrawBlocked(int code, String msg) implements YvaultsError {

    public static final GlobalWithdrawBlocked INSTANCE = new GlobalWithdrawBlocked(
        6010, "Global withdraw is currently blocked"
    );
  }

  record GlobalInvestBlocked(int code, String msg) implements YvaultsError {

    public static final GlobalInvestBlocked INSTANCE = new GlobalInvestBlocked(
        6011, "Global invest is currently blocked"
    );
  }

  record OutOfRangeIntegralConversion(int code, String msg) implements YvaultsError {

    public static final OutOfRangeIntegralConversion INSTANCE = new OutOfRangeIntegralConversion(
        6012, "Out of range integral conversion attempted"
    );
  }

  record MathOverflow(int code, String msg) implements YvaultsError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6013, "[DEPRECATED] Mathematical operation with overflow"
    );
  }

  record TooMuchLiquidityToWithdraw(int code, String msg) implements YvaultsError {

    public static final TooMuchLiquidityToWithdraw INSTANCE = new TooMuchLiquidityToWithdraw(
        6014, "Unable to withdraw more liquidity than available in position"
    );
  }

  record DepositAmountsZero(int code, String msg) implements YvaultsError {

    public static final DepositAmountsZero INSTANCE = new DepositAmountsZero(
        6015, "Deposit amounts must be greater than zero"
    );
  }

  record SharesZero(int code, String msg) implements YvaultsError {

    public static final SharesZero INSTANCE = new SharesZero(
        6016, "Number of shares to withdraw must be greater than zero"
    );
  }

  record StrategyNotActive(int code, String msg) implements YvaultsError {

    public static final StrategyNotActive INSTANCE = new StrategyNotActive(
        6017, "Strategy not active"
    );
  }

  record UnharvestedAmounts(int code, String msg) implements YvaultsError {

    public static final UnharvestedAmounts INSTANCE = new UnharvestedAmounts(
        6018, "There are unharvested gains"
    );
  }

  record InvalidRewardMapping(int code, String msg) implements YvaultsError {

    public static final InvalidRewardMapping INSTANCE = new InvalidRewardMapping(
        6019, "Reward mapping incorrect"
    );
  }

  record InvalidRewardIndex(int code, String msg) implements YvaultsError {

    public static final InvalidRewardIndex INSTANCE = new InvalidRewardIndex(
        6020, "Reward index incorrect"
    );
  }

  record OwnRewardUninitialized(int code, String msg) implements YvaultsError {

    public static final OwnRewardUninitialized INSTANCE = new OwnRewardUninitialized(
        6021, "Cannot use uninitialized reward vault"
    );
  }

  record PriceNotValid(int code, String msg) implements YvaultsError {

    public static final PriceNotValid INSTANCE = new PriceNotValid(
        6022, "Price is not valid"
    );
  }

  record SwapRewardImbalanced(int code, String msg) implements YvaultsError {

    public static final SwapRewardImbalanced INSTANCE = new SwapRewardImbalanced(
        6023, "Must provide almost equal amounts of tokens"
    );
  }

  record SwapRewardTooSmall(int code, String msg) implements YvaultsError {

    public static final SwapRewardTooSmall INSTANCE = new SwapRewardTooSmall(
        6024, "Swap reward is zero or less than requested"
    );
  }

  record SwapRewardLessThanRequested(int code, String msg) implements YvaultsError {

    public static final SwapRewardLessThanRequested INSTANCE = new SwapRewardLessThanRequested(
        6025, "Swap reward is less than what user requested as minimum"
    );
  }

  record SwapRewardLessThanMinimum(int code, String msg) implements YvaultsError {

    public static final SwapRewardLessThanMinimum INSTANCE = new SwapRewardLessThanMinimum(
        6026, "Swap reward is less than minimum acceptable"
    );
  }

  record WrongDiscriminator(int code, String msg) implements YvaultsError {

    public static final WrongDiscriminator INSTANCE = new WrongDiscriminator(
        6027, "Wrong discriminator"
    );
  }

  record WrongMint(int code, String msg) implements YvaultsError {

    public static final WrongMint INSTANCE = new WrongMint(
        6028, "Wrong mint"
    );
  }

  record WrongVault(int code, String msg) implements YvaultsError {

    public static final WrongVault INSTANCE = new WrongVault(
        6029, "Wrong vault"
    );
  }

  record SwapAmountsZero(int code, String msg) implements YvaultsError {

    public static final SwapAmountsZero INSTANCE = new SwapAmountsZero(
        6030, "Swap amounts must be greater than zero"
    );
  }

  record PriceTooOld(int code, String msg) implements YvaultsError {

    public static final PriceTooOld INSTANCE = new PriceTooOld(
        6031, "Price too old"
    );
  }

  record CannotInvestZeroAmount(int code, String msg) implements YvaultsError {

    public static final CannotInvestZeroAmount INSTANCE = new CannotInvestZeroAmount(
        6032, "Cannot invest zero amount"
    );
  }

  record MaxInvestableZero(int code, String msg) implements YvaultsError {

    public static final MaxInvestableZero INSTANCE = new MaxInvestableZero(
        6033, "Cannot have zero investable amount"
    );
  }

  record CollectFeesBlocked(int code, String msg) implements YvaultsError {

    public static final CollectFeesBlocked INSTANCE = new CollectFeesBlocked(
        6034, "Collect fees is blocked"
    );
  }

  record CollectRewardsBlocked(int code, String msg) implements YvaultsError {

    public static final CollectRewardsBlocked INSTANCE = new CollectRewardsBlocked(
        6035, "Collect rewards is blocked"
    );
  }

  record SwapRewardsBlocked(int code, String msg) implements YvaultsError {

    public static final SwapRewardsBlocked INSTANCE = new SwapRewardsBlocked(
        6036, "Swap rewards is blocked"
    );
  }

  record WrongRewardCollateralID(int code, String msg) implements YvaultsError {

    public static final WrongRewardCollateralID INSTANCE = new WrongRewardCollateralID(
        6037, "Reward collateral ID is incorrect for strategy"
    );
  }

  record InvalidPositionAccount(int code, String msg) implements YvaultsError {

    public static final InvalidPositionAccount INSTANCE = new InvalidPositionAccount(
        6038, "Position account doesn't match internal records"
    );
  }

  record CouldNotDeserializeScope(int code, String msg) implements YvaultsError {

    public static final CouldNotDeserializeScope INSTANCE = new CouldNotDeserializeScope(
        6039, "Scope account could not be deserialized"
    );
  }

  record WrongCollateralID(int code, String msg) implements YvaultsError {

    public static final WrongCollateralID INSTANCE = new WrongCollateralID(
        6040, "[DEPRECATED] Collateral ID invalid for strategy"
    );
  }

  record CollateralTokensExceedDepositCap(int code, String msg) implements YvaultsError {

    public static final CollateralTokensExceedDepositCap INSTANCE = new CollateralTokensExceedDepositCap(
        6041, "Collaterals exceed deposit cap"
    );
  }

  record SwapUnevenVaultsBlocked(int code, String msg) implements YvaultsError {

    public static final SwapUnevenVaultsBlocked INSTANCE = new SwapUnevenVaultsBlocked(
        6042, "Swap uneven vaults is blocked"
    );
  }

  record VaultsAreAlreadyBalanced(int code, String msg) implements YvaultsError {

    public static final VaultsAreAlreadyBalanced INSTANCE = new VaultsAreAlreadyBalanced(
        6043, "Cannot swap as vaults are already balanced"
    );
  }

  record CannotSwapUnevenOutOfRange(int code, String msg) implements YvaultsError {

    public static final CannotSwapUnevenOutOfRange INSTANCE = new CannotSwapUnevenOutOfRange(
        6044, "Cannot swap uneven vaults when position is out of range"
    );
  }

  record DivideByZero(int code, String msg) implements YvaultsError {

    public static final DivideByZero INSTANCE = new DivideByZero(
        6045, "Cannot divide by zero"
    );
  }

  record DeltaATooLarge(int code, String msg) implements YvaultsError {

    public static final DeltaATooLarge INSTANCE = new DeltaATooLarge(
        6046, "[DEPRECATED] Delta A too large"
    );
  }

  record DeltaBTooLarge(int code, String msg) implements YvaultsError {

    public static final DeltaBTooLarge INSTANCE = new DeltaBTooLarge(
        6047, "[DEPRECATED] Delta B too large"
    );
  }

  record CannotExecutiveWithdrawZeroAmount(int code, String msg) implements YvaultsError {

    public static final CannotExecutiveWithdrawZeroAmount INSTANCE = new CannotExecutiveWithdrawZeroAmount(
        6048, "[DEPRECATED] Cannot executive withdraw zero amount"
    );
  }

  record CannotWithdrawZeroAmount(int code, String msg) implements YvaultsError {

    public static final CannotWithdrawZeroAmount INSTANCE = new CannotWithdrawZeroAmount(
        6049, "Cannot withdraw zero amount"
    );
  }

  record CannotCollectFeesOnZeroLiquidityPosition(int code, String msg) implements YvaultsError {

    public static final CannotCollectFeesOnZeroLiquidityPosition INSTANCE = new CannotCollectFeesOnZeroLiquidityPosition(
        6050, "[DEPRECATED] Cannot collect fees on zero liquidity position"
    );
  }

  record StrategyNotActiveWhenDepositing(int code, String msg) implements YvaultsError {

    public static final StrategyNotActiveWhenDepositing INSTANCE = new StrategyNotActiveWhenDepositing(
        6051, "Cannot deposit inactive position"
    );
  }

  record StrategyNotActiveWhenOpeningPosition(int code, String msg) implements YvaultsError {

    public static final StrategyNotActiveWhenOpeningPosition INSTANCE = new StrategyNotActiveWhenOpeningPosition(
        6052, "Cannot open position with existing opened position"
    );
  }

  record CollateralTokensExceedDepositCapPerIxn(int code, String msg) implements YvaultsError {

    public static final CollateralTokensExceedDepositCapPerIxn INSTANCE = new CollateralTokensExceedDepositCapPerIxn(
        6053, "Collaterals exceed deposit ixn cap"
    );
  }

  record CannotDepositOutOfRange(int code, String msg) implements YvaultsError {

    public static final CannotDepositOutOfRange INSTANCE = new CannotDepositOutOfRange(
        6054, "Cannot deposit when strategy out of range"
    );
  }

  record CannotInvestOutOfRange(int code, String msg) implements YvaultsError {

    public static final CannotInvestOutOfRange INSTANCE = new CannotInvestOutOfRange(
        6055, "Cannot invest when strategy out of range"
    );
  }

  record WithdrawalCapReached(int code, String msg) implements YvaultsError {

    public static final WithdrawalCapReached INSTANCE = new WithdrawalCapReached(
        6056, "Withdrawal cap is reached"
    );
  }

  record TimestampDecrease(int code, String msg) implements YvaultsError {

    public static final TimestampDecrease INSTANCE = new TimestampDecrease(
        6057, "Timestamp decrease"
    );
  }

  record CPINotAllowed(int code, String msg) implements YvaultsError {

    public static final CPINotAllowed INSTANCE = new CPINotAllowed(
        6058, "CPI not allowed"
    );
  }

  record OrcaPriceTooDifferentFromScope(int code, String msg) implements YvaultsError {

    public static final OrcaPriceTooDifferentFromScope INSTANCE = new OrcaPriceTooDifferentFromScope(
        6059, "Cannot use orca price as it is too different from scope price"
    );
  }

  record LowerTickLargerThanUpperTick(int code, String msg) implements YvaultsError {

    public static final LowerTickLargerThanUpperTick INSTANCE = new LowerTickLargerThanUpperTick(
        6060, "Lower tick larger than upper tick"
    );
  }

  record LowerTickTooLow(int code, String msg) implements YvaultsError {

    public static final LowerTickTooLow INSTANCE = new LowerTickTooLow(
        6061, "Lower tick is lower than the minimal supported low tick"
    );
  }

  record UpperTickTooLarge(int code, String msg) implements YvaultsError {

    public static final UpperTickTooLarge INSTANCE = new UpperTickTooLarge(
        6062, "Upper tick is larger than the maximum supported tick"
    );
  }

  record LowerTickNotMultipleOfTickSpacing(int code, String msg) implements YvaultsError {

    public static final LowerTickNotMultipleOfTickSpacing INSTANCE = new LowerTickNotMultipleOfTickSpacing(
        6063, "Lower tick is not a multiple of tick spacing"
    );
  }

  record UpperTickNotMultipleOfTickSpacing(int code, String msg) implements YvaultsError {

    public static final UpperTickNotMultipleOfTickSpacing INSTANCE = new UpperTickNotMultipleOfTickSpacing(
        6064, "Upper tick is not a multiple of tick spacing"
    );
  }

  record CannotChangeAdminAuthority(int code, String msg) implements YvaultsError {

    public static final CannotChangeAdminAuthority INSTANCE = new CannotChangeAdminAuthority(
        6065, "Cannot change admin authority"
    );
  }

  record CannotResizeAccount(int code, String msg) implements YvaultsError {

    public static final CannotResizeAccount INSTANCE = new CannotResizeAccount(
        6066, "Cannot resize with smaller new size"
    );
  }

  record ScopeChainUpdateFailed(int code, String msg) implements YvaultsError {

    public static final ScopeChainUpdateFailed INSTANCE = new ScopeChainUpdateFailed(
        6067, "Scope chain update failed"
    );
  }

  record PriceTooDivergentFromTwap(int code, String msg) implements YvaultsError {

    public static final PriceTooDivergentFromTwap INSTANCE = new PriceTooDivergentFromTwap(
        6068, "Price too divergent from twap"
    );
  }

  record ExistingRewardOverride(int code, String msg) implements YvaultsError {

    public static final ExistingRewardOverride INSTANCE = new ExistingRewardOverride(
        6069, "[DEPRECATED] Can not override the existing reward"
    );
  }

  record WrongKaminoRewardId(int code, String msg) implements YvaultsError {

    public static final WrongKaminoRewardId INSTANCE = new WrongKaminoRewardId(
        6070, "Kamino reward id exceeds the available slots"
    );
  }

  record KaminoRewardNotExist(int code, String msg) implements YvaultsError {

    public static final KaminoRewardNotExist INSTANCE = new KaminoRewardNotExist(
        6071, "Kamino reward is not initialized"
    );
  }

  record KaminoRewardAlreadyExists(int code, String msg) implements YvaultsError {

    public static final KaminoRewardAlreadyExists INSTANCE = new KaminoRewardAlreadyExists(
        6072, "Kamino reward is already initialized"
    );
  }

  record KaminoCollateralNotValid(int code, String msg) implements YvaultsError {

    public static final KaminoCollateralNotValid INSTANCE = new KaminoCollateralNotValid(
        6073, "Kamino collateral is not valid"
    );
  }

  record KaminoRewardExceedsAvailableAmount(int code, String msg) implements YvaultsError {

    public static final KaminoRewardExceedsAvailableAmount INSTANCE = new KaminoRewardExceedsAvailableAmount(
        6074, "[DEPRECATED] Expected kamino reward is bigger then the available amount within the vault"
    );
  }

  record SwapUnevenVaultsOvershoot(int code, String msg) implements YvaultsError {

    public static final SwapUnevenVaultsOvershoot INSTANCE = new SwapUnevenVaultsOvershoot(
        6075, "Swap uneven vaults result in the opposite unbalance of the vaults"
    );
  }

  record BpsNotInRange(int code, String msg) implements YvaultsError {

    public static final BpsNotInRange INSTANCE = new BpsNotInRange(
        6076, "Bps parameter passed to instruction is not in range"
    );
  }

  record EmergencySwapBlocked(int code, String msg) implements YvaultsError {

    public static final EmergencySwapBlocked INSTANCE = new EmergencySwapBlocked(
        6077, "Emergency Swap is blocked"
    );
  }

  record StrategyNotFrozen(int code, String msg) implements YvaultsError {

    public static final StrategyNotFrozen INSTANCE = new StrategyNotFrozen(
        6078, "Strategy is expected to be frozen for this operation"
    );
  }

  record UnexpectedTokenAmountsPostSwap(int code, String msg) implements YvaultsError {

    public static final UnexpectedTokenAmountsPostSwap INSTANCE = new UnexpectedTokenAmountsPostSwap(
        6079, "Token left in vault post swap are lower than expected"
    );
  }

  record AccountNotBelongToDEX(int code, String msg) implements YvaultsError {

    public static final AccountNotBelongToDEX INSTANCE = new AccountNotBelongToDEX(
        6080, "Account doesn't belong to the DEX"
    );
  }

  record WrongDEXProgramID(int code, String msg) implements YvaultsError {

    public static final WrongDEXProgramID INSTANCE = new WrongDEXProgramID(
        6081, "Wrong DEX program ID"
    );
  }

  record OrcaRewardUninitialized(int code, String msg) implements YvaultsError {

    public static final OrcaRewardUninitialized INSTANCE = new OrcaRewardUninitialized(
        6082, "Cannot use uninitialized orca reward vault"
    );
  }

  record InvalidAdminAuthority(int code, String msg) implements YvaultsError {

    public static final InvalidAdminAuthority INSTANCE = new InvalidAdminAuthority(
        6083, "Invalid admin authority"
    );
  }

  record PriceIsBiggerThanHeuristic(int code, String msg) implements YvaultsError {

    public static final PriceIsBiggerThanHeuristic INSTANCE = new PriceIsBiggerThanHeuristic(
        6084, "Token price is bigger than heuristic"
    );
  }

  record PriceIsLowerThanHeuristic(int code, String msg) implements YvaultsError {

    public static final PriceIsLowerThanHeuristic INSTANCE = new PriceIsLowerThanHeuristic(
        6085, "Token price is lower than heuristic"
    );
  }

  record AccountDifferentThanExpected(int code, String msg) implements YvaultsError {

    public static final AccountDifferentThanExpected INSTANCE = new AccountDifferentThanExpected(
        6086, "Account different than expected"
    );
  }

  record SwapAmountsTooSmall(int code, String msg) implements YvaultsError {

    public static final SwapAmountsTooSmall INSTANCE = new SwapAmountsTooSmall(
        6087, "Swap amount below the minimum value"
    );
  }

  record InvalidDexProgramId(int code, String msg) implements YvaultsError {

    public static final InvalidDexProgramId INSTANCE = new InvalidDexProgramId(
        6088, "Invalid dex program id"
    );
  }

  record StrategyDepositBlocked(int code, String msg) implements YvaultsError {

    public static final StrategyDepositBlocked INSTANCE = new StrategyDepositBlocked(
        6089, "Strategy deposit is currently blocked"
    );
  }

  record StrategyInvestBlocked(int code, String msg) implements YvaultsError {

    public static final StrategyInvestBlocked INSTANCE = new StrategyInvestBlocked(
        6090, "Strategy invest is currently blocked"
    );
  }

  record StrategyWithdrawBlocked(int code, String msg) implements YvaultsError {

    public static final StrategyWithdrawBlocked INSTANCE = new StrategyWithdrawBlocked(
        6091, "Strategy withdraw is currently blocked"
    );
  }

  record WrongSwapVaultDirection(int code, String msg) implements YvaultsError {

    public static final WrongSwapVaultDirection INSTANCE = new WrongSwapVaultDirection(
        6092, "Vault swap can't be performed in the required direction"
    );
  }

  record SwapVaultsTooBig(int code, String msg) implements YvaultsError {

    public static final SwapVaultsTooBig INSTANCE = new SwapVaultsTooBig(
        6093, "Provided amount for vault swap is over the limit"
    );
  }

  record SwapVaultsCashOutputBelowMinimum(int code, String msg) implements YvaultsError {

    public static final SwapVaultsCashOutputBelowMinimum INSTANCE = new SwapVaultsCashOutputBelowMinimum(
        6094, "Token out for cash based vault swap is below minimum expected"
    );
  }

  record FlashIxsNotEnded(int code, String msg) implements YvaultsError {

    public static final FlashIxsNotEnded INSTANCE = new FlashIxsNotEnded(
        6095, "Flash ixs initiated without the closing ix in the transaction"
    );
  }

  record FlashTxWithUnexpectedIxs(int code, String msg) implements YvaultsError {

    public static final FlashTxWithUnexpectedIxs INSTANCE = new FlashTxWithUnexpectedIxs(
        6096, "Some unexpected instructions are present in the tx. Either before or after the flash ixs, or some ix target the same program between"
    );
  }

  record FlashIxsAccountMismatch(int code, String msg) implements YvaultsError {

    public static final FlashIxsAccountMismatch INSTANCE = new FlashIxsAccountMismatch(
        6097, "Some accounts differ between the two flash ixs"
    );
  }

  record FlashIxsIncludeScope(int code, String msg) implements YvaultsError {

    public static final FlashIxsIncludeScope INSTANCE = new FlashIxsIncludeScope(
        6098, "A scope ix is present in a flash tx"
    );
  }

  record FlashVaultSwapBlocked(int code, String msg) implements YvaultsError {

    public static final FlashVaultSwapBlocked INSTANCE = new FlashVaultSwapBlocked(
        6099, "Flash vault swap is blocked on this strategy"
    );
  }

  record FlashVaultSwapWrongAmountToLeave(int code, String msg) implements YvaultsError {

    public static final FlashVaultSwapWrongAmountToLeave INSTANCE = new FlashVaultSwapWrongAmountToLeave(
        6100, "Unexpected amount of tokens in ata prior flash vault swap (wrong amount_to_leave_to_user)"
    );
  }

  record DepositLessThanMinimum(int code, String msg) implements YvaultsError {

    public static final DepositLessThanMinimum INSTANCE = new DepositLessThanMinimum(
        6101, "Deposit amount less than minimal allowed"
    );
  }

  record DepositWithoutInvestDisallowed(int code, String msg) implements YvaultsError {

    public static final DepositWithoutInvestDisallowed INSTANCE = new DepositWithoutInvestDisallowed(
        6102, "Cannot deposit without invest"
    );
  }

  record InvalidScopeChain(int code, String msg) implements YvaultsError {

    public static final InvalidScopeChain INSTANCE = new InvalidScopeChain(
        6103, "Invalid Scope Chain"
    );
  }

  record InvalidScopeTWAPChain(int code, String msg) implements YvaultsError {

    public static final InvalidScopeTWAPChain INSTANCE = new InvalidScopeTWAPChain(
        6104, "Invalid Scope TWAP Chain"
    );
  }

  record PositionHasRemainingLiquidity(int code, String msg) implements YvaultsError {

    public static final PositionHasRemainingLiquidity INSTANCE = new PositionHasRemainingLiquidity(
        6105, "Existent position has liquidity, new position creation is forbidden"
    );
  }

  record PoolRebalancing(int code, String msg) implements YvaultsError {

    public static final PoolRebalancing INSTANCE = new PoolRebalancing(
        6106, "Deposit is not allowed as pool is rebalancing"
    );
  }

  record PermissionlessRebalancingDisabled(int code, String msg) implements YvaultsError {

    public static final PermissionlessRebalancingDisabled INSTANCE = new PermissionlessRebalancingDisabled(
        6107, "Permissionless rebalancing is disabled"
    );
  }

  record ManualRebalanceInvalidOwner(int code, String msg) implements YvaultsError {

    public static final ManualRebalanceInvalidOwner INSTANCE = new ManualRebalanceInvalidOwner(
        6108, "Only the owner of the strategy can manually rebalance it"
    );
  }

  record InvalidRebalanceType(int code, String msg) implements YvaultsError {

    public static final InvalidRebalanceType INSTANCE = new InvalidRebalanceType(
        6109, "Invalid rebalance type for the strategy"
    );
  }

  record NoRebalanceNecessary(int code, String msg) implements YvaultsError {

    public static final NoRebalanceNecessary INSTANCE = new NoRebalanceNecessary(
        6110, "No rebalance necessary based on current rebalance type/parameters"
    );
  }

  record TickArraysDoNotMatchRebalance(int code, String msg) implements YvaultsError {

    public static final TickArraysDoNotMatchRebalance INSTANCE = new TickArraysDoNotMatchRebalance(
        6111, "The given tick arrays do not match the rebalance result"
    );
  }

  record StrategyPositionNotValid(int code, String msg) implements YvaultsError {

    public static final StrategyPositionNotValid INSTANCE = new StrategyPositionNotValid(
        6112, "Expected strategy position to be initialized"
    );
  }

  record CouldNotDeserializeRebalanceState(int code, String msg) implements YvaultsError {

    public static final CouldNotDeserializeRebalanceState INSTANCE = new CouldNotDeserializeRebalanceState(
        6113, "Rebalance state could not be deserialized"
    );
  }

  record CouldNotSerializeRebalanceState(int code, String msg) implements YvaultsError {

    public static final CouldNotSerializeRebalanceState INSTANCE = new CouldNotSerializeRebalanceState(
        6114, "Rebalance state could not be serialized"
    );
  }

  record CouldNotDeserializeRebalanceParams(int code, String msg) implements YvaultsError {

    public static final CouldNotDeserializeRebalanceParams INSTANCE = new CouldNotDeserializeRebalanceParams(
        6115, "Rebalance params could not be deserialized"
    );
  }

  record NotEnoughTokensForRatio(int code, String msg) implements YvaultsError {

    public static final NotEnoughTokensForRatio INSTANCE = new NotEnoughTokensForRatio(
        6116, "Deposit is not allowed as token amounts are not enough to match our holdings ratio"
    );
  }

  record AmountsRepresentZeroShares(int code, String msg) implements YvaultsError {

    public static final AmountsRepresentZeroShares INSTANCE = new AmountsRepresentZeroShares(
        6117, "The provided amounts are too small"
    );
  }

  record MaxLossExceeded(int code, String msg) implements YvaultsError {

    public static final MaxLossExceeded INSTANCE = new MaxLossExceeded(
        6118, "Rouding errors exceed the maximal loss tolerance"
    );
  }

  record RewardNotStrategyToken(int code, String msg) implements YvaultsError {

    public static final RewardNotStrategyToken INSTANCE = new RewardNotStrategyToken(
        6119, "Reward does not match strategy token"
    );
  }

  record DecimalToU64ConversionFailed(int code, String msg) implements YvaultsError {

    public static final DecimalToU64ConversionFailed INSTANCE = new DecimalToU64ConversionFailed(
        6120, "Decimal to u64 conversion failed"
    );
  }

  record DecimalOperationFailed(int code, String msg) implements YvaultsError {

    public static final DecimalOperationFailed INSTANCE = new DecimalOperationFailed(
        6121, "Decimal operation failed"
    );
  }

  record VaultBalancesCausesWrongSharesIssuance(int code, String msg) implements YvaultsError {

    public static final VaultBalancesCausesWrongSharesIssuance INSTANCE = new VaultBalancesCausesWrongSharesIssuance(
        6122, "Deposit is not allowed as the strategy is not fully invested in the pool "
    );
  }

  record TokenDisabled(int code, String msg) implements YvaultsError {

    public static final TokenDisabled INSTANCE = new TokenDisabled(
        6123, "Token cannot be used in strategy creation"
    );
  }

  record InvalidReferencePriceType(int code, String msg) implements YvaultsError {

    public static final InvalidReferencePriceType INSTANCE = new InvalidReferencePriceType(
        6124, "Invalid reference price type"
    );
  }

  record TokenToSwapNotEnough(int code, String msg) implements YvaultsError {

    public static final TokenToSwapNotEnough INSTANCE = new TokenToSwapNotEnough(
        6125, "Token amount to be swapped is not enough"
    );
  }

  record TokenAccountBalanceMismatch(int code, String msg) implements YvaultsError {

    public static final TokenAccountBalanceMismatch INSTANCE = new TokenAccountBalanceMismatch(
        6126, "Token amount in ata is different than the expected amount"
    );
  }

  record UnexpectedProgramIdForPrerequisiteIx(int code, String msg) implements YvaultsError {

    public static final UnexpectedProgramIdForPrerequisiteIx INSTANCE = new UnexpectedProgramIdForPrerequisiteIx(
        6127, "Unexpected programID for prerequisite ix"
    );
  }

  record ComputeFeesAndRewardsUpdateError(int code, String msg) implements YvaultsError {

    public static final ComputeFeesAndRewardsUpdateError INSTANCE = new ComputeFeesAndRewardsUpdateError(
        6128, "Got an error from the dex specific function while computing the fees/rewards update"
    );
  }

  record SharesNotZero(int code, String msg) implements YvaultsError {

    public static final SharesNotZero INSTANCE = new SharesNotZero(
        6129, "There must be no shares issued when closing a strategy"
    );
  }

  record InvalidScopeStakingRateChain(int code, String msg) implements YvaultsError {

    public static final InvalidScopeStakingRateChain INSTANCE = new InvalidScopeStakingRateChain(
        6130, "Invalid Scope staking rate Chain"
    );
  }

  record StakingRateNotValid(int code, String msg) implements YvaultsError {

    public static final StakingRateNotValid INSTANCE = new StakingRateNotValid(
        6131, "Staking rate (provided by Scope) is not valid"
    );
  }

  record DecimalToU128ConversionFailed(int code, String msg) implements YvaultsError {

    public static final DecimalToU128ConversionFailed INSTANCE = new DecimalToU128ConversionFailed(
        6132, "Decimal to u128 conversion failed"
    );
  }

  record DecimalNegativeSqrtRoot(int code, String msg) implements YvaultsError {

    public static final DecimalNegativeSqrtRoot INSTANCE = new DecimalNegativeSqrtRoot(
        6133, "Decimal sqrt on negative number"
    );
  }

  record DriftingOppositeDirection(int code, String msg) implements YvaultsError {

    public static final DriftingOppositeDirection INSTANCE = new DriftingOppositeDirection(
        6134, "Drifting strategy is moving in the opposite direction"
    );
  }

  record WrongRewardCollateralId2(int code, String msg) implements YvaultsError {

    public static final WrongRewardCollateralId2 INSTANCE = new WrongRewardCollateralId2(
        6135, "Wrong reward collateral_id"
    );
  }

  record CollateralInfoAlreadyExists(int code, String msg) implements YvaultsError {

    public static final CollateralInfoAlreadyExists INSTANCE = new CollateralInfoAlreadyExists(
        6136, "Collateral info already exists for given index"
    );
  }

  record InvestTooEarly(int code, String msg) implements YvaultsError {

    public static final InvestTooEarly INSTANCE = new InvestTooEarly(
        6137, "Invest is too early after the position was opened"
    );
  }

  record SwapUnevenTooEarly(int code, String msg) implements YvaultsError {

    public static final SwapUnevenTooEarly INSTANCE = new SwapUnevenTooEarly(
        6138, "Swap uneven is too early after the position was opened"
    );
  }

  record FlashSwapTooEarly(int code, String msg) implements YvaultsError {

    public static final FlashSwapTooEarly INSTANCE = new FlashSwapTooEarly(
        6139, "Flash swap is too early after the position was opened"
    );
  }

  record RebalancesCapReached(int code, String msg) implements YvaultsError {

    public static final RebalancesCapReached INSTANCE = new RebalancesCapReached(
        6140, "Rebalance caps reached, no rebalances are allowed until the end of the current interval"
    );
  }

  record SwapUnevenInvalidAuthority(int code, String msg) implements YvaultsError {

    public static final SwapUnevenInvalidAuthority INSTANCE = new SwapUnevenInvalidAuthority(
        6141, "Cannot swap uneven because authority is set and the given signer does not correspond"
    );
  }

  record InvalidTick(int code, String msg) implements YvaultsError {

    public static final InvalidTick INSTANCE = new InvalidTick(
        6142, "Invalid tick requested"
    );
  }

  record MeteoraMathOverflow(int code, String msg) implements YvaultsError {

    public static final MeteoraMathOverflow INSTANCE = new MeteoraMathOverflow(
        6143, "Meteora math overflowed"
    );
  }

  record StrategyTickArrayNotValid(int code, String msg) implements YvaultsError {

    public static final StrategyTickArrayNotValid INSTANCE = new StrategyTickArrayNotValid(
        6144, "Expected strategy tick arrays to be initialized"
    );
  }

  record WrongEventAuthority(int code, String msg) implements YvaultsError {

    public static final WrongEventAuthority INSTANCE = new WrongEventAuthority(
        6145, "Wrong event authority"
    );
  }

  record StrategyFieldUpdateNotAllowed(int code, String msg) implements YvaultsError {

    public static final StrategyFieldUpdateNotAllowed INSTANCE = new StrategyFieldUpdateNotAllowed(
        6146, "Strategy field update is not allowed"
    );
  }

  record UnsupportedDex(int code, String msg) implements YvaultsError {

    public static final UnsupportedDex INSTANCE = new UnsupportedDex(
        6147, "DEX is not supported for this operation"
    );
  }

  record InvalidBPSValue(int code, String msg) implements YvaultsError {

    public static final InvalidBPSValue INSTANCE = new InvalidBPSValue(
        6148, "Invalid BPS value provided"
    );
  }

  record RewardVaultOverrideNotAllowed(int code, String msg) implements YvaultsError {

    public static final RewardVaultOverrideNotAllowed INSTANCE = new RewardVaultOverrideNotAllowed(
        6149, "Reward vault override not allowed"
    );
  }

  record ComputeFeesAndRewardsInvalidReward(int code, String msg) implements YvaultsError {

    public static final ComputeFeesAndRewardsInvalidReward INSTANCE = new ComputeFeesAndRewardsInvalidReward(
        6150, "Got invalid reward from the dex specific function while computing the fees/rewards update"
    );
  }

  record EmptyTreasury(int code, String msg) implements YvaultsError {

    public static final EmptyTreasury INSTANCE = new EmptyTreasury(
        6151, "No tokens to withdraw from treasury fee vault"
    );
  }

  record ChangingPoolRewardMintMismatch(int code, String msg) implements YvaultsError {

    public static final ChangingPoolRewardMintMismatch INSTANCE = new ChangingPoolRewardMintMismatch(
        6152, "New pool reward mint does not match the old pool reward mint"
    );
  }

  record ProvidedRewardVaultMismatch(int code, String msg) implements YvaultsError {

    public static final ProvidedRewardVaultMismatch INSTANCE = new ProvidedRewardVaultMismatch(
        6153, "The provided reward vault does not match the strategy state"
    );
  }

  record RepeatedMint(int code, String msg) implements YvaultsError {

    public static final RepeatedMint INSTANCE = new RepeatedMint(
        6154, "The provided reward vault does not match the strategy state"
    );
  }

  record UnsupportedTokenExtension(int code, String msg) implements YvaultsError {

    public static final UnsupportedTokenExtension INSTANCE = new UnsupportedTokenExtension(
        6155, "The token extension is not supported by the program"
    );
  }

  record UnsupportedDexForToken22(int code, String msg) implements YvaultsError {

    public static final UnsupportedDexForToken22 INSTANCE = new UnsupportedDexForToken22(
        6156, "Cannot initialize strategy with this dex while having a mint with token22"
    );
  }
}
