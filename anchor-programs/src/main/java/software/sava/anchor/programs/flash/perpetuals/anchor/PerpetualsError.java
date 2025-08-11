package software.sava.anchor.programs.flash.perpetuals.anchor;

import software.sava.anchor.ProgramError;

public sealed interface PerpetualsError extends ProgramError permits
    PerpetualsError.MultisigAccountNotAuthorized,
    PerpetualsError.MultisigAlreadySigned,
    PerpetualsError.MultisigAlreadyExecuted,
    PerpetualsError.MathOverflow,
    PerpetualsError.UnsupportedOracle,
    PerpetualsError.InvalidOracleAccount,
    PerpetualsError.InvalidOracleState,
    PerpetualsError.StaleOraclePrice,
    PerpetualsError.InvalidOraclePrice,
    PerpetualsError.InvalidEnvironment,
    PerpetualsError.InvalidPoolState,
    PerpetualsError.InvalidCustodyState,
    PerpetualsError.InvalidMarketState,
    PerpetualsError.InvalidCollateralCustody,
    PerpetualsError.InvalidPositionState,
    PerpetualsError.InvalidDispensingCustody,
    PerpetualsError.InvalidPerpetualsConfig,
    PerpetualsError.InvalidPoolConfig,
    PerpetualsError.InvalidCustodyConfig,
    PerpetualsError.InsufficientAmountReturned,
    PerpetualsError.MaxPriceSlippage,
    PerpetualsError.MaxLeverage,
    PerpetualsError.MaxInitLeverage,
    PerpetualsError.MinLeverage,
    PerpetualsError.CustodyAmountLimit,
    PerpetualsError.PositionAmountLimit,
    PerpetualsError.TokenRatioOutOfRange,
    PerpetualsError.UnsupportedToken,
    PerpetualsError.UnsupportedCustody,
    PerpetualsError.UnsupportedPool,
    PerpetualsError.UnsupportedMarket,
    PerpetualsError.InstructionNotAllowed,
    PerpetualsError.MaxUtilization,
    PerpetualsError.CloseOnlyMode,
    PerpetualsError.MinCollateral,
    PerpetualsError.PermissionlessOracleMissingSignature,
    PerpetualsError.PermissionlessOracleMalformedEd25519Data,
    PerpetualsError.PermissionlessOracleSignerMismatch,
    PerpetualsError.PermissionlessOracleMessageMismatch,
    PerpetualsError.ExponentMismatch,
    PerpetualsError.CloseRatio,
    PerpetualsError.InsufficientStakeAmount,
    PerpetualsError.InvalidFeeDeltas,
    PerpetualsError.InvalidFeeDistributionCustody,
    PerpetualsError.InvalidCollection,
    PerpetualsError.InvalidOwner,
    PerpetualsError.InvalidAccess,
    PerpetualsError.TokenStakeAccountMismatch,
    PerpetualsError.MaxDepostsReached,
    PerpetualsError.InvalidStopLossPrice,
    PerpetualsError.InvalidTakeProfitPrice,
    PerpetualsError.ExposureLimitExceeded,
    PerpetualsError.MaxStopLossOrders,
    PerpetualsError.MaxTakeProfitOrders,
    PerpetualsError.MaxOpenOrder,
    PerpetualsError.InvalidOrder,
    PerpetualsError.InvalidLimitPrice,
    PerpetualsError.MinReserve,
    PerpetualsError.MaxWithdrawTokenRequest,
    PerpetualsError.InvalidRewardDistribution,
    PerpetualsError.LpPriceOutOfBounds {

  static PerpetualsError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> MultisigAccountNotAuthorized.INSTANCE;
      case 6001 -> MultisigAlreadySigned.INSTANCE;
      case 6002 -> MultisigAlreadyExecuted.INSTANCE;
      case 6003 -> MathOverflow.INSTANCE;
      case 6004 -> UnsupportedOracle.INSTANCE;
      case 6005 -> InvalidOracleAccount.INSTANCE;
      case 6006 -> InvalidOracleState.INSTANCE;
      case 6007 -> StaleOraclePrice.INSTANCE;
      case 6008 -> InvalidOraclePrice.INSTANCE;
      case 6009 -> InvalidEnvironment.INSTANCE;
      case 6010 -> InvalidPoolState.INSTANCE;
      case 6011 -> InvalidCustodyState.INSTANCE;
      case 6012 -> InvalidMarketState.INSTANCE;
      case 6013 -> InvalidCollateralCustody.INSTANCE;
      case 6014 -> InvalidPositionState.INSTANCE;
      case 6015 -> InvalidDispensingCustody.INSTANCE;
      case 6016 -> InvalidPerpetualsConfig.INSTANCE;
      case 6017 -> InvalidPoolConfig.INSTANCE;
      case 6018 -> InvalidCustodyConfig.INSTANCE;
      case 6019 -> InsufficientAmountReturned.INSTANCE;
      case 6020 -> MaxPriceSlippage.INSTANCE;
      case 6021 -> MaxLeverage.INSTANCE;
      case 6022 -> MaxInitLeverage.INSTANCE;
      case 6023 -> MinLeverage.INSTANCE;
      case 6024 -> CustodyAmountLimit.INSTANCE;
      case 6025 -> PositionAmountLimit.INSTANCE;
      case 6026 -> TokenRatioOutOfRange.INSTANCE;
      case 6027 -> UnsupportedToken.INSTANCE;
      case 6028 -> UnsupportedCustody.INSTANCE;
      case 6029 -> UnsupportedPool.INSTANCE;
      case 6030 -> UnsupportedMarket.INSTANCE;
      case 6031 -> InstructionNotAllowed.INSTANCE;
      case 6032 -> MaxUtilization.INSTANCE;
      case 6033 -> CloseOnlyMode.INSTANCE;
      case 6034 -> MinCollateral.INSTANCE;
      case 6035 -> PermissionlessOracleMissingSignature.INSTANCE;
      case 6036 -> PermissionlessOracleMalformedEd25519Data.INSTANCE;
      case 6037 -> PermissionlessOracleSignerMismatch.INSTANCE;
      case 6038 -> PermissionlessOracleMessageMismatch.INSTANCE;
      case 6039 -> ExponentMismatch.INSTANCE;
      case 6040 -> CloseRatio.INSTANCE;
      case 6041 -> InsufficientStakeAmount.INSTANCE;
      case 6042 -> InvalidFeeDeltas.INSTANCE;
      case 6043 -> InvalidFeeDistributionCustody.INSTANCE;
      case 6044 -> InvalidCollection.INSTANCE;
      case 6045 -> InvalidOwner.INSTANCE;
      case 6046 -> InvalidAccess.INSTANCE;
      case 6047 -> TokenStakeAccountMismatch.INSTANCE;
      case 6048 -> MaxDepostsReached.INSTANCE;
      case 6049 -> InvalidStopLossPrice.INSTANCE;
      case 6050 -> InvalidTakeProfitPrice.INSTANCE;
      case 6051 -> ExposureLimitExceeded.INSTANCE;
      case 6052 -> MaxStopLossOrders.INSTANCE;
      case 6053 -> MaxTakeProfitOrders.INSTANCE;
      case 6054 -> MaxOpenOrder.INSTANCE;
      case 6055 -> InvalidOrder.INSTANCE;
      case 6056 -> InvalidLimitPrice.INSTANCE;
      case 6057 -> MinReserve.INSTANCE;
      case 6058 -> MaxWithdrawTokenRequest.INSTANCE;
      case 6059 -> InvalidRewardDistribution.INSTANCE;
      case 6060 -> LpPriceOutOfBounds.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Perpetuals error code: " + errorCode);
    };
  }

  record MultisigAccountNotAuthorized(int code, String msg) implements PerpetualsError {

    public static final MultisigAccountNotAuthorized INSTANCE = new MultisigAccountNotAuthorized(
        6000, "Account is not authorized to sign this instruction"
    );
  }

  record MultisigAlreadySigned(int code, String msg) implements PerpetualsError {

    public static final MultisigAlreadySigned INSTANCE = new MultisigAlreadySigned(
        6001, "Account has already signed this instruction"
    );
  }

  record MultisigAlreadyExecuted(int code, String msg) implements PerpetualsError {

    public static final MultisigAlreadyExecuted INSTANCE = new MultisigAlreadyExecuted(
        6002, "This instruction has already been executed"
    );
  }

  record MathOverflow(int code, String msg) implements PerpetualsError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6003, "Overflow in arithmetic operation"
    );
  }

  record UnsupportedOracle(int code, String msg) implements PerpetualsError {

    public static final UnsupportedOracle INSTANCE = new UnsupportedOracle(
        6004, "Unsupported price oracle"
    );
  }

  record InvalidOracleAccount(int code, String msg) implements PerpetualsError {

    public static final InvalidOracleAccount INSTANCE = new InvalidOracleAccount(
        6005, "Invalid oracle account"
    );
  }

  record InvalidOracleState(int code, String msg) implements PerpetualsError {

    public static final InvalidOracleState INSTANCE = new InvalidOracleState(
        6006, "Invalid oracle state"
    );
  }

  record StaleOraclePrice(int code, String msg) implements PerpetualsError {

    public static final StaleOraclePrice INSTANCE = new StaleOraclePrice(
        6007, "Stale oracle price"
    );
  }

  record InvalidOraclePrice(int code, String msg) implements PerpetualsError {

    public static final InvalidOraclePrice INSTANCE = new InvalidOraclePrice(
        6008, "Invalid oracle price"
    );
  }

  record InvalidEnvironment(int code, String msg) implements PerpetualsError {

    public static final InvalidEnvironment INSTANCE = new InvalidEnvironment(
        6009, "Instruction is not allowed in production"
    );
  }

  record InvalidPoolState(int code, String msg) implements PerpetualsError {

    public static final InvalidPoolState INSTANCE = new InvalidPoolState(
        6010, "Invalid pool state"
    );
  }

  record InvalidCustodyState(int code, String msg) implements PerpetualsError {

    public static final InvalidCustodyState INSTANCE = new InvalidCustodyState(
        6011, "Invalid custody state"
    );
  }

  record InvalidMarketState(int code, String msg) implements PerpetualsError {

    public static final InvalidMarketState INSTANCE = new InvalidMarketState(
        6012, "Invalid Market state"
    );
  }

  record InvalidCollateralCustody(int code, String msg) implements PerpetualsError {

    public static final InvalidCollateralCustody INSTANCE = new InvalidCollateralCustody(
        6013, "Invalid collateral custody"
    );
  }

  record InvalidPositionState(int code, String msg) implements PerpetualsError {

    public static final InvalidPositionState INSTANCE = new InvalidPositionState(
        6014, "Invalid position state"
    );
  }

  record InvalidDispensingCustody(int code, String msg) implements PerpetualsError {

    public static final InvalidDispensingCustody INSTANCE = new InvalidDispensingCustody(
        6015, "Invalid Dispensing Custody"
    );
  }

  record InvalidPerpetualsConfig(int code, String msg) implements PerpetualsError {

    public static final InvalidPerpetualsConfig INSTANCE = new InvalidPerpetualsConfig(
        6016, "Invalid perpetuals config"
    );
  }

  record InvalidPoolConfig(int code, String msg) implements PerpetualsError {

    public static final InvalidPoolConfig INSTANCE = new InvalidPoolConfig(
        6017, "Invalid pool config"
    );
  }

  record InvalidCustodyConfig(int code, String msg) implements PerpetualsError {

    public static final InvalidCustodyConfig INSTANCE = new InvalidCustodyConfig(
        6018, "Invalid custody config"
    );
  }

  record InsufficientAmountReturned(int code, String msg) implements PerpetualsError {

    public static final InsufficientAmountReturned INSTANCE = new InsufficientAmountReturned(
        6019, "Insufficient token amount returned"
    );
  }

  record MaxPriceSlippage(int code, String msg) implements PerpetualsError {

    public static final MaxPriceSlippage INSTANCE = new MaxPriceSlippage(
        6020, "Price slippage limit exceeded"
    );
  }

  record MaxLeverage(int code, String msg) implements PerpetualsError {

    public static final MaxLeverage INSTANCE = new MaxLeverage(
        6021, "Position leverage limit exceeded"
    );
  }

  record MaxInitLeverage(int code, String msg) implements PerpetualsError {

    public static final MaxInitLeverage INSTANCE = new MaxInitLeverage(
        6022, "Position initial leverage limit exceeded"
    );
  }

  record MinLeverage(int code, String msg) implements PerpetualsError {

    public static final MinLeverage INSTANCE = new MinLeverage(
        6023, "Position leverage less than minimum"
    );
  }

  record CustodyAmountLimit(int code, String msg) implements PerpetualsError {

    public static final CustodyAmountLimit INSTANCE = new CustodyAmountLimit(
        6024, "Custody amount limit exceeded"
    );
  }

  record PositionAmountLimit(int code, String msg) implements PerpetualsError {

    public static final PositionAmountLimit INSTANCE = new PositionAmountLimit(
        6025, "Position amount limit exceeded"
    );
  }

  record TokenRatioOutOfRange(int code, String msg) implements PerpetualsError {

    public static final TokenRatioOutOfRange INSTANCE = new TokenRatioOutOfRange(
        6026, "Token ratio out of range"
    );
  }

  record UnsupportedToken(int code, String msg) implements PerpetualsError {

    public static final UnsupportedToken INSTANCE = new UnsupportedToken(
        6027, "Token is not supported"
    );
  }

  record UnsupportedCustody(int code, String msg) implements PerpetualsError {

    public static final UnsupportedCustody INSTANCE = new UnsupportedCustody(
        6028, "Custody is not supported"
    );
  }

  record UnsupportedPool(int code, String msg) implements PerpetualsError {

    public static final UnsupportedPool INSTANCE = new UnsupportedPool(
        6029, "Pool is not supported"
    );
  }

  record UnsupportedMarket(int code, String msg) implements PerpetualsError {

    public static final UnsupportedMarket INSTANCE = new UnsupportedMarket(
        6030, "Market is not supported"
    );
  }

  record InstructionNotAllowed(int code, String msg) implements PerpetualsError {

    public static final InstructionNotAllowed INSTANCE = new InstructionNotAllowed(
        6031, "Instruction is not allowed at this time"
    );
  }

  record MaxUtilization(int code, String msg) implements PerpetualsError {

    public static final MaxUtilization INSTANCE = new MaxUtilization(
        6032, "Token utilization limit exceeded"
    );
  }

  record CloseOnlyMode(int code, String msg) implements PerpetualsError {

    public static final CloseOnlyMode INSTANCE = new CloseOnlyMode(
        6033, "Close-only mode activated"
    );
  }

  record MinCollateral(int code, String msg) implements PerpetualsError {

    public static final MinCollateral INSTANCE = new MinCollateral(
        6034, "Minimum collateral limit breached"
    );
  }

  record PermissionlessOracleMissingSignature(int code, String msg) implements PerpetualsError {

    public static final PermissionlessOracleMissingSignature INSTANCE = new PermissionlessOracleMissingSignature(
        6035, "Permissionless oracle update must be preceded by Ed25519 signature verification instruction"
    );
  }

  record PermissionlessOracleMalformedEd25519Data(int code, String msg) implements PerpetualsError {

    public static final PermissionlessOracleMalformedEd25519Data INSTANCE = new PermissionlessOracleMalformedEd25519Data(
        6036, "Ed25519 signature verification data does not match expected format"
    );
  }

  record PermissionlessOracleSignerMismatch(int code, String msg) implements PerpetualsError {

    public static final PermissionlessOracleSignerMismatch INSTANCE = new PermissionlessOracleSignerMismatch(
        6037, "Ed25519 signature was not signed by the oracle authority"
    );
  }

  record PermissionlessOracleMessageMismatch(int code, String msg) implements PerpetualsError {

    public static final PermissionlessOracleMessageMismatch INSTANCE = new PermissionlessOracleMessageMismatch(
        6038, "Signed message does not match instruction params"
    );
  }

  record ExponentMismatch(int code, String msg) implements PerpetualsError {

    public static final ExponentMismatch INSTANCE = new ExponentMismatch(
        6039, "Exponent Mismatch betweeen operands"
    );
  }

  record CloseRatio(int code, String msg) implements PerpetualsError {

    public static final CloseRatio INSTANCE = new CloseRatio(
        6040, "Invalid Close Ratio"
    );
  }

  record InsufficientStakeAmount(int code, String msg) implements PerpetualsError {

    public static final InsufficientStakeAmount INSTANCE = new InsufficientStakeAmount(
        6041, "Insufficient LP tokens staked"
    );
  }

  record InvalidFeeDeltas(int code, String msg) implements PerpetualsError {

    public static final InvalidFeeDeltas INSTANCE = new InvalidFeeDeltas(
        6042, "Invalid Fee Deltas"
    );
  }

  record InvalidFeeDistributionCustody(int code, String msg) implements PerpetualsError {

    public static final InvalidFeeDistributionCustody INSTANCE = new InvalidFeeDistributionCustody(
        6043, "Invalid Fee Distrivution Custody"
    );
  }

  record InvalidCollection(int code, String msg) implements PerpetualsError {

    public static final InvalidCollection INSTANCE = new InvalidCollection(
        6044, "Invalid Collection"
    );
  }

  record InvalidOwner(int code, String msg) implements PerpetualsError {

    public static final InvalidOwner INSTANCE = new InvalidOwner(
        6045, "Owner of Token Account does not match"
    );
  }

  record InvalidAccess(int code, String msg) implements PerpetualsError {

    public static final InvalidAccess INSTANCE = new InvalidAccess(
        6046, "Only nft holders or referred users can trade"
    );
  }

  record TokenStakeAccountMismatch(int code, String msg) implements PerpetualsError {

    public static final TokenStakeAccountMismatch INSTANCE = new TokenStakeAccountMismatch(
        6047, "Token Stake account doesnot match referral account"
    );
  }

  record MaxDepostsReached(int code, String msg) implements PerpetualsError {

    public static final MaxDepostsReached INSTANCE = new MaxDepostsReached(
        6048, "Max deposits reached"
    );
  }

  record InvalidStopLossPrice(int code, String msg) implements PerpetualsError {

    public static final InvalidStopLossPrice INSTANCE = new InvalidStopLossPrice(
        6049, "Invalid Stop Loss price"
    );
  }

  record InvalidTakeProfitPrice(int code, String msg) implements PerpetualsError {

    public static final InvalidTakeProfitPrice INSTANCE = new InvalidTakeProfitPrice(
        6050, "Invalid Take Profit price"
    );
  }

  record ExposureLimitExceeded(int code, String msg) implements PerpetualsError {

    public static final ExposureLimitExceeded INSTANCE = new ExposureLimitExceeded(
        6051, "Max exposure limit exceeded for the market"
    );
  }

  record MaxStopLossOrders(int code, String msg) implements PerpetualsError {

    public static final MaxStopLossOrders INSTANCE = new MaxStopLossOrders(
        6052, "Stop Loss limit exhausted"
    );
  }

  record MaxTakeProfitOrders(int code, String msg) implements PerpetualsError {

    public static final MaxTakeProfitOrders INSTANCE = new MaxTakeProfitOrders(
        6053, "Take Profit limit exhausted"
    );
  }

  record MaxOpenOrder(int code, String msg) implements PerpetualsError {

    public static final MaxOpenOrder INSTANCE = new MaxOpenOrder(
        6054, "Open order limit exhausted"
    );
  }

  record InvalidOrder(int code, String msg) implements PerpetualsError {

    public static final InvalidOrder INSTANCE = new InvalidOrder(
        6055, "Invalid Order"
    );
  }

  record InvalidLimitPrice(int code, String msg) implements PerpetualsError {

    public static final InvalidLimitPrice INSTANCE = new InvalidLimitPrice(
        6056, "Invalid Limit price"
    );
  }

  record MinReserve(int code, String msg) implements PerpetualsError {

    public static final MinReserve INSTANCE = new MinReserve(
        6057, "Minimum reserve limit breached"
    );
  }

  record MaxWithdrawTokenRequest(int code, String msg) implements PerpetualsError {

    public static final MaxWithdrawTokenRequest INSTANCE = new MaxWithdrawTokenRequest(
        6058, "Withdraw Token Request limit exhausted"
    );
  }

  record InvalidRewardDistribution(int code, String msg) implements PerpetualsError {

    public static final InvalidRewardDistribution INSTANCE = new InvalidRewardDistribution(
        6059, "Invalid Reward Distribution"
    );
  }

  record LpPriceOutOfBounds(int code, String msg) implements PerpetualsError {

    public static final LpPriceOutOfBounds INSTANCE = new LpPriceOutOfBounds(
        6060, "Liquidity Token price is out of bounds"
    );
  }
}
