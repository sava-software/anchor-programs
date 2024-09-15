package software.sava.anchor.programs.meteora.dlmm.anchor;

import software.sava.anchor.ProgramError;

public sealed interface LbClmmError extends ProgramError permits
    LbClmmError.InvalidStartBinIndex,
    LbClmmError.InvalidBinId,
    LbClmmError.InvalidInput,
    LbClmmError.ExceededAmountSlippageTolerance,
    LbClmmError.ExceededBinSlippageTolerance,
    LbClmmError.CompositionFactorFlawed,
    LbClmmError.NonPresetBinStep,
    LbClmmError.ZeroLiquidity,
    LbClmmError.InvalidPosition,
    LbClmmError.BinArrayNotFound,
    LbClmmError.InvalidTokenMint,
    LbClmmError.InvalidAccountForSingleDeposit,
    LbClmmError.PairInsufficientLiquidity,
    LbClmmError.InvalidFeeOwner,
    LbClmmError.InvalidFeeWithdrawAmount,
    LbClmmError.InvalidAdmin,
    LbClmmError.IdenticalFeeOwner,
    LbClmmError.InvalidBps,
    LbClmmError.MathOverflow,
    LbClmmError.TypeCastFailed,
    LbClmmError.InvalidRewardIndex,
    LbClmmError.InvalidRewardDuration,
    LbClmmError.RewardInitialized,
    LbClmmError.RewardUninitialized,
    LbClmmError.IdenticalFunder,
    LbClmmError.RewardCampaignInProgress,
    LbClmmError.IdenticalRewardDuration,
    LbClmmError.InvalidBinArray,
    LbClmmError.NonContinuousBinArrays,
    LbClmmError.InvalidRewardVault,
    LbClmmError.NonEmptyPosition,
    LbClmmError.UnauthorizedAccess,
    LbClmmError.InvalidFeeParameter,
    LbClmmError.MissingOracle,
    LbClmmError.InsufficientSample,
    LbClmmError.InvalidLookupTimestamp,
    LbClmmError.BitmapExtensionAccountIsNotProvided,
    LbClmmError.CannotFindNonZeroLiquidityBinArrayId,
    LbClmmError.BinIdOutOfBound,
    LbClmmError.InsufficientOutAmount,
    LbClmmError.InvalidPositionWidth,
    LbClmmError.ExcessiveFeeUpdate,
    LbClmmError.PoolDisabled,
    LbClmmError.InvalidPoolType,
    LbClmmError.ExceedMaxWhitelist,
    LbClmmError.InvalidIndex,
    LbClmmError.RewardNotEnded,
    LbClmmError.MustWithdrawnIneligibleReward,
    LbClmmError.UnauthorizedAddress,
    LbClmmError.OperatorsAreTheSame,
    LbClmmError.WithdrawToWrongTokenAccount,
    LbClmmError.WrongRentReceiver,
    LbClmmError.AlreadyPassActivationPoint,
    LbClmmError.ExceedMaxSwappedAmount,
    LbClmmError.InvalidStrategyParameters,
    LbClmmError.LiquidityLocked,
    LbClmmError.BinRangeIsNotEmpty,
    LbClmmError.NotExactAmountOut,
    LbClmmError.InvalidActivationType {

  static LbClmmError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidStartBinIndex.INSTANCE;
      case 6001 -> InvalidBinId.INSTANCE;
      case 6002 -> InvalidInput.INSTANCE;
      case 6003 -> ExceededAmountSlippageTolerance.INSTANCE;
      case 6004 -> ExceededBinSlippageTolerance.INSTANCE;
      case 6005 -> CompositionFactorFlawed.INSTANCE;
      case 6006 -> NonPresetBinStep.INSTANCE;
      case 6007 -> ZeroLiquidity.INSTANCE;
      case 6008 -> InvalidPosition.INSTANCE;
      case 6009 -> BinArrayNotFound.INSTANCE;
      case 6010 -> InvalidTokenMint.INSTANCE;
      case 6011 -> InvalidAccountForSingleDeposit.INSTANCE;
      case 6012 -> PairInsufficientLiquidity.INSTANCE;
      case 6013 -> InvalidFeeOwner.INSTANCE;
      case 6014 -> InvalidFeeWithdrawAmount.INSTANCE;
      case 6015 -> InvalidAdmin.INSTANCE;
      case 6016 -> IdenticalFeeOwner.INSTANCE;
      case 6017 -> InvalidBps.INSTANCE;
      case 6018 -> MathOverflow.INSTANCE;
      case 6019 -> TypeCastFailed.INSTANCE;
      case 6020 -> InvalidRewardIndex.INSTANCE;
      case 6021 -> InvalidRewardDuration.INSTANCE;
      case 6022 -> RewardInitialized.INSTANCE;
      case 6023 -> RewardUninitialized.INSTANCE;
      case 6024 -> IdenticalFunder.INSTANCE;
      case 6025 -> RewardCampaignInProgress.INSTANCE;
      case 6026 -> IdenticalRewardDuration.INSTANCE;
      case 6027 -> InvalidBinArray.INSTANCE;
      case 6028 -> NonContinuousBinArrays.INSTANCE;
      case 6029 -> InvalidRewardVault.INSTANCE;
      case 6030 -> NonEmptyPosition.INSTANCE;
      case 6031 -> UnauthorizedAccess.INSTANCE;
      case 6032 -> InvalidFeeParameter.INSTANCE;
      case 6033 -> MissingOracle.INSTANCE;
      case 6034 -> InsufficientSample.INSTANCE;
      case 6035 -> InvalidLookupTimestamp.INSTANCE;
      case 6036 -> BitmapExtensionAccountIsNotProvided.INSTANCE;
      case 6037 -> CannotFindNonZeroLiquidityBinArrayId.INSTANCE;
      case 6038 -> BinIdOutOfBound.INSTANCE;
      case 6039 -> InsufficientOutAmount.INSTANCE;
      case 6040 -> InvalidPositionWidth.INSTANCE;
      case 6041 -> ExcessiveFeeUpdate.INSTANCE;
      case 6042 -> PoolDisabled.INSTANCE;
      case 6043 -> InvalidPoolType.INSTANCE;
      case 6044 -> ExceedMaxWhitelist.INSTANCE;
      case 6045 -> InvalidIndex.INSTANCE;
      case 6046 -> RewardNotEnded.INSTANCE;
      case 6047 -> MustWithdrawnIneligibleReward.INSTANCE;
      case 6048 -> UnauthorizedAddress.INSTANCE;
      case 6049 -> OperatorsAreTheSame.INSTANCE;
      case 6050 -> WithdrawToWrongTokenAccount.INSTANCE;
      case 6051 -> WrongRentReceiver.INSTANCE;
      case 6052 -> AlreadyPassActivationPoint.INSTANCE;
      case 6053 -> ExceedMaxSwappedAmount.INSTANCE;
      case 6054 -> InvalidStrategyParameters.INSTANCE;
      case 6055 -> LiquidityLocked.INSTANCE;
      case 6056 -> BinRangeIsNotEmpty.INSTANCE;
      case 6057 -> NotExactAmountOut.INSTANCE;
      case 6058 -> InvalidActivationType.INSTANCE;
      default -> throw new IllegalStateException("Unexpected LbClmm error code: " + errorCode);
    };
  }

  record InvalidStartBinIndex(int code, String msg) implements LbClmmError {

    public static final InvalidStartBinIndex INSTANCE = new InvalidStartBinIndex(
        6000, "Invalid start bin index"
    );
  }

  record InvalidBinId(int code, String msg) implements LbClmmError {

    public static final InvalidBinId INSTANCE = new InvalidBinId(
        6001, "Invalid bin id"
    );
  }

  record InvalidInput(int code, String msg) implements LbClmmError {

    public static final InvalidInput INSTANCE = new InvalidInput(
        6002, "Invalid input data"
    );
  }

  record ExceededAmountSlippageTolerance(int code, String msg) implements LbClmmError {

    public static final ExceededAmountSlippageTolerance INSTANCE = new ExceededAmountSlippageTolerance(
        6003, "Exceeded amount slippage tolerance"
    );
  }

  record ExceededBinSlippageTolerance(int code, String msg) implements LbClmmError {

    public static final ExceededBinSlippageTolerance INSTANCE = new ExceededBinSlippageTolerance(
        6004, "Exceeded bin slippage tolerance"
    );
  }

  record CompositionFactorFlawed(int code, String msg) implements LbClmmError {

    public static final CompositionFactorFlawed INSTANCE = new CompositionFactorFlawed(
        6005, "Composition factor flawed"
    );
  }

  record NonPresetBinStep(int code, String msg) implements LbClmmError {

    public static final NonPresetBinStep INSTANCE = new NonPresetBinStep(
        6006, "Non preset bin step"
    );
  }

  record ZeroLiquidity(int code, String msg) implements LbClmmError {

    public static final ZeroLiquidity INSTANCE = new ZeroLiquidity(
        6007, "Zero liquidity"
    );
  }

  record InvalidPosition(int code, String msg) implements LbClmmError {

    public static final InvalidPosition INSTANCE = new InvalidPosition(
        6008, "Invalid position"
    );
  }

  record BinArrayNotFound(int code, String msg) implements LbClmmError {

    public static final BinArrayNotFound INSTANCE = new BinArrayNotFound(
        6009, "Bin array not found"
    );
  }

  record InvalidTokenMint(int code, String msg) implements LbClmmError {

    public static final InvalidTokenMint INSTANCE = new InvalidTokenMint(
        6010, "Invalid token mint"
    );
  }

  record InvalidAccountForSingleDeposit(int code, String msg) implements LbClmmError {

    public static final InvalidAccountForSingleDeposit INSTANCE = new InvalidAccountForSingleDeposit(
        6011, "Invalid account for single deposit"
    );
  }

  record PairInsufficientLiquidity(int code, String msg) implements LbClmmError {

    public static final PairInsufficientLiquidity INSTANCE = new PairInsufficientLiquidity(
        6012, "Pair insufficient liquidity"
    );
  }

  record InvalidFeeOwner(int code, String msg) implements LbClmmError {

    public static final InvalidFeeOwner INSTANCE = new InvalidFeeOwner(
        6013, "Invalid fee owner"
    );
  }

  record InvalidFeeWithdrawAmount(int code, String msg) implements LbClmmError {

    public static final InvalidFeeWithdrawAmount INSTANCE = new InvalidFeeWithdrawAmount(
        6014, "Invalid fee withdraw amount"
    );
  }

  record InvalidAdmin(int code, String msg) implements LbClmmError {

    public static final InvalidAdmin INSTANCE = new InvalidAdmin(
        6015, "Invalid admin"
    );
  }

  record IdenticalFeeOwner(int code, String msg) implements LbClmmError {

    public static final IdenticalFeeOwner INSTANCE = new IdenticalFeeOwner(
        6016, "Identical fee owner"
    );
  }

  record InvalidBps(int code, String msg) implements LbClmmError {

    public static final InvalidBps INSTANCE = new InvalidBps(
        6017, "Invalid basis point"
    );
  }

  record MathOverflow(int code, String msg) implements LbClmmError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6018, "Math operation overflow"
    );
  }

  record TypeCastFailed(int code, String msg) implements LbClmmError {

    public static final TypeCastFailed INSTANCE = new TypeCastFailed(
        6019, "Type cast error"
    );
  }

  record InvalidRewardIndex(int code, String msg) implements LbClmmError {

    public static final InvalidRewardIndex INSTANCE = new InvalidRewardIndex(
        6020, "Invalid reward index"
    );
  }

  record InvalidRewardDuration(int code, String msg) implements LbClmmError {

    public static final InvalidRewardDuration INSTANCE = new InvalidRewardDuration(
        6021, "Invalid reward duration"
    );
  }

  record RewardInitialized(int code, String msg) implements LbClmmError {

    public static final RewardInitialized INSTANCE = new RewardInitialized(
        6022, "Reward already initialized"
    );
  }

  record RewardUninitialized(int code, String msg) implements LbClmmError {

    public static final RewardUninitialized INSTANCE = new RewardUninitialized(
        6023, "Reward not initialized"
    );
  }

  record IdenticalFunder(int code, String msg) implements LbClmmError {

    public static final IdenticalFunder INSTANCE = new IdenticalFunder(
        6024, "Identical funder"
    );
  }

  record RewardCampaignInProgress(int code, String msg) implements LbClmmError {

    public static final RewardCampaignInProgress INSTANCE = new RewardCampaignInProgress(
        6025, "Reward campaign in progress"
    );
  }

  record IdenticalRewardDuration(int code, String msg) implements LbClmmError {

    public static final IdenticalRewardDuration INSTANCE = new IdenticalRewardDuration(
        6026, "Reward duration is the same"
    );
  }

  record InvalidBinArray(int code, String msg) implements LbClmmError {

    public static final InvalidBinArray INSTANCE = new InvalidBinArray(
        6027, "Invalid bin array"
    );
  }

  record NonContinuousBinArrays(int code, String msg) implements LbClmmError {

    public static final NonContinuousBinArrays INSTANCE = new NonContinuousBinArrays(
        6028, "Bin arrays must be continuous"
    );
  }

  record InvalidRewardVault(int code, String msg) implements LbClmmError {

    public static final InvalidRewardVault INSTANCE = new InvalidRewardVault(
        6029, "Invalid reward vault"
    );
  }

  record NonEmptyPosition(int code, String msg) implements LbClmmError {

    public static final NonEmptyPosition INSTANCE = new NonEmptyPosition(
        6030, "Position is not empty"
    );
  }

  record UnauthorizedAccess(int code, String msg) implements LbClmmError {

    public static final UnauthorizedAccess INSTANCE = new UnauthorizedAccess(
        6031, "Unauthorized access"
    );
  }

  record InvalidFeeParameter(int code, String msg) implements LbClmmError {

    public static final InvalidFeeParameter INSTANCE = new InvalidFeeParameter(
        6032, "Invalid fee parameter"
    );
  }

  record MissingOracle(int code, String msg) implements LbClmmError {

    public static final MissingOracle INSTANCE = new MissingOracle(
        6033, "Missing oracle account"
    );
  }

  record InsufficientSample(int code, String msg) implements LbClmmError {

    public static final InsufficientSample INSTANCE = new InsufficientSample(
        6034, "Insufficient observation sample"
    );
  }

  record InvalidLookupTimestamp(int code, String msg) implements LbClmmError {

    public static final InvalidLookupTimestamp INSTANCE = new InvalidLookupTimestamp(
        6035, "Invalid lookup timestamp"
    );
  }

  record BitmapExtensionAccountIsNotProvided(int code, String msg) implements LbClmmError {

    public static final BitmapExtensionAccountIsNotProvided INSTANCE = new BitmapExtensionAccountIsNotProvided(
        6036, "Bitmap extension account is not provided"
    );
  }

  record CannotFindNonZeroLiquidityBinArrayId(int code, String msg) implements LbClmmError {

    public static final CannotFindNonZeroLiquidityBinArrayId INSTANCE = new CannotFindNonZeroLiquidityBinArrayId(
        6037, "Cannot find non-zero liquidity binArrayId"
    );
  }

  record BinIdOutOfBound(int code, String msg) implements LbClmmError {

    public static final BinIdOutOfBound INSTANCE = new BinIdOutOfBound(
        6038, "Bin id out of bound"
    );
  }

  record InsufficientOutAmount(int code, String msg) implements LbClmmError {

    public static final InsufficientOutAmount INSTANCE = new InsufficientOutAmount(
        6039, "Insufficient amount in for minimum out"
    );
  }

  record InvalidPositionWidth(int code, String msg) implements LbClmmError {

    public static final InvalidPositionWidth INSTANCE = new InvalidPositionWidth(
        6040, "Invalid position width"
    );
  }

  record ExcessiveFeeUpdate(int code, String msg) implements LbClmmError {

    public static final ExcessiveFeeUpdate INSTANCE = new ExcessiveFeeUpdate(
        6041, "Excessive fee update"
    );
  }

  record PoolDisabled(int code, String msg) implements LbClmmError {

    public static final PoolDisabled INSTANCE = new PoolDisabled(
        6042, "Pool disabled"
    );
  }

  record InvalidPoolType(int code, String msg) implements LbClmmError {

    public static final InvalidPoolType INSTANCE = new InvalidPoolType(
        6043, "Invalid pool type"
    );
  }

  record ExceedMaxWhitelist(int code, String msg) implements LbClmmError {

    public static final ExceedMaxWhitelist INSTANCE = new ExceedMaxWhitelist(
        6044, "Whitelist for wallet is full"
    );
  }

  record InvalidIndex(int code, String msg) implements LbClmmError {

    public static final InvalidIndex INSTANCE = new InvalidIndex(
        6045, "Invalid index"
    );
  }

  record RewardNotEnded(int code, String msg) implements LbClmmError {

    public static final RewardNotEnded INSTANCE = new RewardNotEnded(
        6046, "Reward not ended"
    );
  }

  record MustWithdrawnIneligibleReward(int code, String msg) implements LbClmmError {

    public static final MustWithdrawnIneligibleReward INSTANCE = new MustWithdrawnIneligibleReward(
        6047, "Must withdraw ineligible reward"
    );
  }

  record UnauthorizedAddress(int code, String msg) implements LbClmmError {

    public static final UnauthorizedAddress INSTANCE = new UnauthorizedAddress(
        6048, "Unauthorized address"
    );
  }

  record OperatorsAreTheSame(int code, String msg) implements LbClmmError {

    public static final OperatorsAreTheSame INSTANCE = new OperatorsAreTheSame(
        6049, "Cannot update because operators are the same"
    );
  }

  record WithdrawToWrongTokenAccount(int code, String msg) implements LbClmmError {

    public static final WithdrawToWrongTokenAccount INSTANCE = new WithdrawToWrongTokenAccount(
        6050, "Withdraw to wrong token account"
    );
  }

  record WrongRentReceiver(int code, String msg) implements LbClmmError {

    public static final WrongRentReceiver INSTANCE = new WrongRentReceiver(
        6051, "Wrong rent receiver"
    );
  }

  record AlreadyPassActivationPoint(int code, String msg) implements LbClmmError {

    public static final AlreadyPassActivationPoint INSTANCE = new AlreadyPassActivationPoint(
        6052, "Already activated"
    );
  }

  record ExceedMaxSwappedAmount(int code, String msg) implements LbClmmError {

    public static final ExceedMaxSwappedAmount INSTANCE = new ExceedMaxSwappedAmount(
        6053, "Swapped amount is exceeded max swapped amount"
    );
  }

  record InvalidStrategyParameters(int code, String msg) implements LbClmmError {

    public static final InvalidStrategyParameters INSTANCE = new InvalidStrategyParameters(
        6054, "Invalid strategy parameters"
    );
  }

  record LiquidityLocked(int code, String msg) implements LbClmmError {

    public static final LiquidityLocked INSTANCE = new LiquidityLocked(
        6055, "Liquidity locked"
    );
  }

  record BinRangeIsNotEmpty(int code, String msg) implements LbClmmError {

    public static final BinRangeIsNotEmpty INSTANCE = new BinRangeIsNotEmpty(
        6056, "Bin range is not empty"
    );
  }

  record NotExactAmountOut(int code, String msg) implements LbClmmError {

    public static final NotExactAmountOut INSTANCE = new NotExactAmountOut(
        6057, "Amount out is not matched with exact amount out"
    );
  }

  record InvalidActivationType(int code, String msg) implements LbClmmError {

    public static final InvalidActivationType INSTANCE = new InvalidActivationType(
        6058, "Invalid activation type"
    );
  }
}
