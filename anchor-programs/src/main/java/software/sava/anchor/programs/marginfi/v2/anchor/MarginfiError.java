package software.sava.anchor.programs.marginfi.v2.anchor;

import software.sava.anchor.ProgramError;

public sealed interface MarginfiError extends ProgramError permits
    MarginfiError.InternalLogicError,
    MarginfiError.BankNotFound,
    MarginfiError.LendingAccountBalanceNotFound,
    MarginfiError.BankAssetCapacityExceeded,
    MarginfiError.InvalidTransfer,
    MarginfiError.MissingPythOrBankAccount,
    MarginfiError.MissingPythAccount,
    MarginfiError.MissingBankAccount,
    MarginfiError.InvalidBankAccount,
    MarginfiError.RiskEngineInitRejected,
    MarginfiError.LendingAccountBalanceSlotsFull,
    MarginfiError.BankAlreadyExists,
    MarginfiError.ZeroLiquidationAmount,
    MarginfiError.AccountNotBankrupt,
    MarginfiError.BalanceNotBadDebt,
    MarginfiError.InvalidConfig,
    MarginfiError.BankPaused,
    MarginfiError.BankReduceOnly,
    MarginfiError.BankAccountNotFound,
    MarginfiError.OperationDepositOnly,
    MarginfiError.OperationWithdrawOnly,
    MarginfiError.OperationBorrowOnly,
    MarginfiError.OperationRepayOnly,
    MarginfiError.NoAssetFound,
    MarginfiError.NoLiabilityFound,
    MarginfiError.InvalidOracleSetup,
    MarginfiError.IllegalUtilizationRatio,
    MarginfiError.BankLiabilityCapacityExceeded,
    MarginfiError.InvalidPrice,
    MarginfiError.IsolatedAccountIllegalState,
    MarginfiError.EmissionsAlreadySetup,
    MarginfiError.OracleNotSetup,
    MarginfiError.InvalidSwitchboardDecimalConversion,
    MarginfiError.CannotCloseOutstandingEmissions,
    MarginfiError.EmissionsUpdateError,
    MarginfiError.AccountDisabled,
    MarginfiError.AccountTempActiveBalanceLimitExceeded,
    MarginfiError.AccountInFlashloan,
    MarginfiError.IllegalFlashloan,
    MarginfiError.IllegalFlag,
    MarginfiError.IllegalBalanceState,
    MarginfiError.IllegalAccountAuthorityTransfer,
    MarginfiError.Unauthorized,
    MarginfiError.IllegalAction,
    MarginfiError.T22MintRequired,
    MarginfiError.InvalidFeeAta,
    MarginfiError.AddedStakedPoolManually,
    MarginfiError.AssetTagMismatch,
    MarginfiError.StakePoolValidationFailed,
    MarginfiError.SwitchboardStalePrice,
    MarginfiError.PythPushStalePrice,
    MarginfiError.WrongNumberOfOracleAccounts,
    MarginfiError.WrongOracleAccountKeys,
    MarginfiError.PythPushWrongAccountOwner,
    MarginfiError.StakedPythPushWrongAccountOwner,
    MarginfiError.PythPushMismatchedFeedId,
    MarginfiError.PythPushInsufficientVerificationLevel,
    MarginfiError.PythPushFeedIdMustBe32Bytes,
    MarginfiError.PythPushFeedIdNonHexCharacter,
    MarginfiError.SwitchboardWrongAccountOwner,
    MarginfiError.PythPushInvalidAccount,
    MarginfiError.SwitchboardInvalidAccount,
    MarginfiError.MathError,
    MarginfiError.InvalidEmissionsDestinationAccount,
    MarginfiError.SameAssetAndLiabilityBanks,
    MarginfiError.OverliquidationAttempt,
    MarginfiError.NoLiabilitiesInLiabilityBank,
    MarginfiError.AssetsInLiabilityBank,
    MarginfiError.HealthyAccount,
    MarginfiError.ExhaustedLiability,
    MarginfiError.TooSeverePayoff,
    MarginfiError.TooSevereLiquidation,
    MarginfiError.WorseHealthPostLiquidation,
    MarginfiError.ArenaBankLimit,
    MarginfiError.ArenaSettingCannotChange,
    MarginfiError.BadEmodeConfig,
    MarginfiError.PythPushInvalidWindowSize,
    MarginfiError.InvalidFeesDestinationAccount,
    MarginfiError.ZeroAssetPrice,
    MarginfiError.ZeroLiabilityPrice,
    MarginfiError.OracleMaxConfidenceExceeded,
    MarginfiError.BankCannotClose,
    MarginfiError.AccountAlreadyMigrated {

  static MarginfiError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InternalLogicError.INSTANCE;
      case 6001 -> BankNotFound.INSTANCE;
      case 6002 -> LendingAccountBalanceNotFound.INSTANCE;
      case 6003 -> BankAssetCapacityExceeded.INSTANCE;
      case 6004 -> InvalidTransfer.INSTANCE;
      case 6005 -> MissingPythOrBankAccount.INSTANCE;
      case 6006 -> MissingPythAccount.INSTANCE;
      case 6007 -> MissingBankAccount.INSTANCE;
      case 6008 -> InvalidBankAccount.INSTANCE;
      case 6009 -> RiskEngineInitRejected.INSTANCE;
      case 6010 -> LendingAccountBalanceSlotsFull.INSTANCE;
      case 6011 -> BankAlreadyExists.INSTANCE;
      case 6012 -> ZeroLiquidationAmount.INSTANCE;
      case 6013 -> AccountNotBankrupt.INSTANCE;
      case 6014 -> BalanceNotBadDebt.INSTANCE;
      case 6015 -> InvalidConfig.INSTANCE;
      case 6016 -> BankPaused.INSTANCE;
      case 6017 -> BankReduceOnly.INSTANCE;
      case 6018 -> BankAccountNotFound.INSTANCE;
      case 6019 -> OperationDepositOnly.INSTANCE;
      case 6020 -> OperationWithdrawOnly.INSTANCE;
      case 6021 -> OperationBorrowOnly.INSTANCE;
      case 6022 -> OperationRepayOnly.INSTANCE;
      case 6023 -> NoAssetFound.INSTANCE;
      case 6024 -> NoLiabilityFound.INSTANCE;
      case 6025 -> InvalidOracleSetup.INSTANCE;
      case 6026 -> IllegalUtilizationRatio.INSTANCE;
      case 6027 -> BankLiabilityCapacityExceeded.INSTANCE;
      case 6028 -> InvalidPrice.INSTANCE;
      case 6029 -> IsolatedAccountIllegalState.INSTANCE;
      case 6030 -> EmissionsAlreadySetup.INSTANCE;
      case 6031 -> OracleNotSetup.INSTANCE;
      case 6032 -> InvalidSwitchboardDecimalConversion.INSTANCE;
      case 6033 -> CannotCloseOutstandingEmissions.INSTANCE;
      case 6034 -> EmissionsUpdateError.INSTANCE;
      case 6035 -> AccountDisabled.INSTANCE;
      case 6036 -> AccountTempActiveBalanceLimitExceeded.INSTANCE;
      case 6037 -> AccountInFlashloan.INSTANCE;
      case 6038 -> IllegalFlashloan.INSTANCE;
      case 6039 -> IllegalFlag.INSTANCE;
      case 6040 -> IllegalBalanceState.INSTANCE;
      case 6041 -> IllegalAccountAuthorityTransfer.INSTANCE;
      case 6042 -> Unauthorized.INSTANCE;
      case 6043 -> IllegalAction.INSTANCE;
      case 6044 -> T22MintRequired.INSTANCE;
      case 6045 -> InvalidFeeAta.INSTANCE;
      case 6046 -> AddedStakedPoolManually.INSTANCE;
      case 6047 -> AssetTagMismatch.INSTANCE;
      case 6048 -> StakePoolValidationFailed.INSTANCE;
      case 6049 -> SwitchboardStalePrice.INSTANCE;
      case 6050 -> PythPushStalePrice.INSTANCE;
      case 6051 -> WrongNumberOfOracleAccounts.INSTANCE;
      case 6052 -> WrongOracleAccountKeys.INSTANCE;
      case 6053 -> PythPushWrongAccountOwner.INSTANCE;
      case 6054 -> StakedPythPushWrongAccountOwner.INSTANCE;
      case 6055 -> PythPushMismatchedFeedId.INSTANCE;
      case 6056 -> PythPushInsufficientVerificationLevel.INSTANCE;
      case 6057 -> PythPushFeedIdMustBe32Bytes.INSTANCE;
      case 6058 -> PythPushFeedIdNonHexCharacter.INSTANCE;
      case 6059 -> SwitchboardWrongAccountOwner.INSTANCE;
      case 6060 -> PythPushInvalidAccount.INSTANCE;
      case 6061 -> SwitchboardInvalidAccount.INSTANCE;
      case 6062 -> MathError.INSTANCE;
      case 6063 -> InvalidEmissionsDestinationAccount.INSTANCE;
      case 6064 -> SameAssetAndLiabilityBanks.INSTANCE;
      case 6065 -> OverliquidationAttempt.INSTANCE;
      case 6066 -> NoLiabilitiesInLiabilityBank.INSTANCE;
      case 6067 -> AssetsInLiabilityBank.INSTANCE;
      case 6068 -> HealthyAccount.INSTANCE;
      case 6069 -> ExhaustedLiability.INSTANCE;
      case 6070 -> TooSeverePayoff.INSTANCE;
      case 6071 -> TooSevereLiquidation.INSTANCE;
      case 6072 -> WorseHealthPostLiquidation.INSTANCE;
      case 6073 -> ArenaBankLimit.INSTANCE;
      case 6074 -> ArenaSettingCannotChange.INSTANCE;
      case 6075 -> BadEmodeConfig.INSTANCE;
      case 6076 -> PythPushInvalidWindowSize.INSTANCE;
      case 6077 -> InvalidFeesDestinationAccount.INSTANCE;
      case 6078 -> ZeroAssetPrice.INSTANCE;
      case 6079 -> ZeroLiabilityPrice.INSTANCE;
      case 6080 -> OracleMaxConfidenceExceeded.INSTANCE;
      case 6081 -> BankCannotClose.INSTANCE;
      case 6082 -> AccountAlreadyMigrated.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Marginfi error code: " + errorCode);
    };
  }

  record InternalLogicError(int code, String msg) implements MarginfiError {

    public static final InternalLogicError INSTANCE = new InternalLogicError(
        6000, "Internal Marginfi logic error"
    );
  }

  record BankNotFound(int code, String msg) implements MarginfiError {

    public static final BankNotFound INSTANCE = new BankNotFound(
        6001, "Invalid bank index"
    );
  }

  record LendingAccountBalanceNotFound(int code, String msg) implements MarginfiError {

    public static final LendingAccountBalanceNotFound INSTANCE = new LendingAccountBalanceNotFound(
        6002, "Lending account balance not found"
    );
  }

  record BankAssetCapacityExceeded(int code, String msg) implements MarginfiError {

    public static final BankAssetCapacityExceeded INSTANCE = new BankAssetCapacityExceeded(
        6003, "Bank deposit capacity exceeded"
    );
  }

  record InvalidTransfer(int code, String msg) implements MarginfiError {

    public static final InvalidTransfer INSTANCE = new InvalidTransfer(
        6004, "Invalid transfer"
    );
  }

  record MissingPythOrBankAccount(int code, String msg) implements MarginfiError {

    public static final MissingPythOrBankAccount INSTANCE = new MissingPythOrBankAccount(
        6005, "Missing Oracle, Bank, LST mint, or Sol Pool"
    );
  }

  record MissingPythAccount(int code, String msg) implements MarginfiError {

    public static final MissingPythAccount INSTANCE = new MissingPythAccount(
        6006, "Missing Pyth account"
    );
  }

  record MissingBankAccount(int code, String msg) implements MarginfiError {

    public static final MissingBankAccount INSTANCE = new MissingBankAccount(
        6007, "Missing Bank account"
    );
  }

  record InvalidBankAccount(int code, String msg) implements MarginfiError {

    public static final InvalidBankAccount INSTANCE = new InvalidBankAccount(
        6008, "Invalid Bank account"
    );
  }

  record RiskEngineInitRejected(int code, String msg) implements MarginfiError {

    public static final RiskEngineInitRejected INSTANCE = new RiskEngineInitRejected(
        6009, "RiskEngine rejected due to either bad health or stale oracles"
    );
  }

  record LendingAccountBalanceSlotsFull(int code, String msg) implements MarginfiError {

    public static final LendingAccountBalanceSlotsFull INSTANCE = new LendingAccountBalanceSlotsFull(
        6010, "Lending account balance slots are full"
    );
  }

  record BankAlreadyExists(int code, String msg) implements MarginfiError {

    public static final BankAlreadyExists INSTANCE = new BankAlreadyExists(
        6011, "Bank already exists"
    );
  }

  record ZeroLiquidationAmount(int code, String msg) implements MarginfiError {

    public static final ZeroLiquidationAmount INSTANCE = new ZeroLiquidationAmount(
        6012, "Amount to liquidate must be positive"
    );
  }

  record AccountNotBankrupt(int code, String msg) implements MarginfiError {

    public static final AccountNotBankrupt INSTANCE = new AccountNotBankrupt(
        6013, "Account is not bankrupt"
    );
  }

  record BalanceNotBadDebt(int code, String msg) implements MarginfiError {

    public static final BalanceNotBadDebt INSTANCE = new BalanceNotBadDebt(
        6014, "Account balance is not bad debt"
    );
  }

  record InvalidConfig(int code, String msg) implements MarginfiError {

    public static final InvalidConfig INSTANCE = new InvalidConfig(
        6015, "Invalid group config"
    );
  }

  record BankPaused(int code, String msg) implements MarginfiError {

    public static final BankPaused INSTANCE = new BankPaused(
        6016, "Bank paused"
    );
  }

  record BankReduceOnly(int code, String msg) implements MarginfiError {

    public static final BankReduceOnly INSTANCE = new BankReduceOnly(
        6017, "Bank is ReduceOnly mode"
    );
  }

  record BankAccountNotFound(int code, String msg) implements MarginfiError {

    public static final BankAccountNotFound INSTANCE = new BankAccountNotFound(
        6018, "Bank is missing"
    );
  }

  record OperationDepositOnly(int code, String msg) implements MarginfiError {

    public static final OperationDepositOnly INSTANCE = new OperationDepositOnly(
        6019, "Operation is deposit-only"
    );
  }

  record OperationWithdrawOnly(int code, String msg) implements MarginfiError {

    public static final OperationWithdrawOnly INSTANCE = new OperationWithdrawOnly(
        6020, "Operation is withdraw-only"
    );
  }

  record OperationBorrowOnly(int code, String msg) implements MarginfiError {

    public static final OperationBorrowOnly INSTANCE = new OperationBorrowOnly(
        6021, "Operation is borrow-only"
    );
  }

  record OperationRepayOnly(int code, String msg) implements MarginfiError {

    public static final OperationRepayOnly INSTANCE = new OperationRepayOnly(
        6022, "Operation is repay-only"
    );
  }

  record NoAssetFound(int code, String msg) implements MarginfiError {

    public static final NoAssetFound INSTANCE = new NoAssetFound(
        6023, "No asset found"
    );
  }

  record NoLiabilityFound(int code, String msg) implements MarginfiError {

    public static final NoLiabilityFound INSTANCE = new NoLiabilityFound(
        6024, "No liability found"
    );
  }

  record InvalidOracleSetup(int code, String msg) implements MarginfiError {

    public static final InvalidOracleSetup INSTANCE = new InvalidOracleSetup(
        6025, "Invalid oracle setup"
    );
  }

  record IllegalUtilizationRatio(int code, String msg) implements MarginfiError {

    public static final IllegalUtilizationRatio INSTANCE = new IllegalUtilizationRatio(
        6026, "Invalid bank utilization ratio"
    );
  }

  record BankLiabilityCapacityExceeded(int code, String msg) implements MarginfiError {

    public static final BankLiabilityCapacityExceeded INSTANCE = new BankLiabilityCapacityExceeded(
        6027, "Bank borrow cap exceeded"
    );
  }

  record InvalidPrice(int code, String msg) implements MarginfiError {

    public static final InvalidPrice INSTANCE = new InvalidPrice(
        6028, "Invalid Price"
    );
  }

  record IsolatedAccountIllegalState(int code, String msg) implements MarginfiError {

    public static final IsolatedAccountIllegalState INSTANCE = new IsolatedAccountIllegalState(
        6029, "Account can have only one liability when account is under isolated risk"
    );
  }

  record EmissionsAlreadySetup(int code, String msg) implements MarginfiError {

    public static final EmissionsAlreadySetup INSTANCE = new EmissionsAlreadySetup(
        6030, "Emissions already setup"
    );
  }

  record OracleNotSetup(int code, String msg) implements MarginfiError {

    public static final OracleNotSetup INSTANCE = new OracleNotSetup(
        6031, "Oracle is not set"
    );
  }

  record InvalidSwitchboardDecimalConversion(int code, String msg) implements MarginfiError {

    public static final InvalidSwitchboardDecimalConversion INSTANCE = new InvalidSwitchboardDecimalConversion(
        6032, "Invalid switchboard decimal conversion"
    );
  }

  record CannotCloseOutstandingEmissions(int code, String msg) implements MarginfiError {

    public static final CannotCloseOutstandingEmissions INSTANCE = new CannotCloseOutstandingEmissions(
        6033, "Cannot close balance because of outstanding emissions"
    );
  }

  record EmissionsUpdateError(int code, String msg) implements MarginfiError {

    public static final EmissionsUpdateError INSTANCE = new EmissionsUpdateError(
        6034, "Update emissions error"
    );
  }

  record AccountDisabled(int code, String msg) implements MarginfiError {

    public static final AccountDisabled INSTANCE = new AccountDisabled(
        6035, "Account disabled"
    );
  }

  record AccountTempActiveBalanceLimitExceeded(int code, String msg) implements MarginfiError {

    public static final AccountTempActiveBalanceLimitExceeded INSTANCE = new AccountTempActiveBalanceLimitExceeded(
        6036, "Account can't temporarily open 3 balances, please close a balance first"
    );
  }

  record AccountInFlashloan(int code, String msg) implements MarginfiError {

    public static final AccountInFlashloan INSTANCE = new AccountInFlashloan(
        6037, "Illegal action during flashloan"
    );
  }

  record IllegalFlashloan(int code, String msg) implements MarginfiError {

    public static final IllegalFlashloan INSTANCE = new IllegalFlashloan(
        6038, "Illegal flashloan"
    );
  }

  record IllegalFlag(int code, String msg) implements MarginfiError {

    public static final IllegalFlag INSTANCE = new IllegalFlag(
        6039, "Illegal flag"
    );
  }

  record IllegalBalanceState(int code, String msg) implements MarginfiError {

    public static final IllegalBalanceState INSTANCE = new IllegalBalanceState(
        6040, "Illegal balance state"
    );
  }

  record IllegalAccountAuthorityTransfer(int code, String msg) implements MarginfiError {

    public static final IllegalAccountAuthorityTransfer INSTANCE = new IllegalAccountAuthorityTransfer(
        6041, "Illegal account authority transfer"
    );
  }

  record Unauthorized(int code, String msg) implements MarginfiError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6042, "Unauthorized"
    );
  }

  record IllegalAction(int code, String msg) implements MarginfiError {

    public static final IllegalAction INSTANCE = new IllegalAction(
        6043, "Invalid account authority"
    );
  }

  record T22MintRequired(int code, String msg) implements MarginfiError {

    public static final T22MintRequired INSTANCE = new T22MintRequired(
        6044, "Token22 Banks require mint account as first remaining account"
    );
  }

  record InvalidFeeAta(int code, String msg) implements MarginfiError {

    public static final InvalidFeeAta INSTANCE = new InvalidFeeAta(
        6045, "Invalid ATA for global fee account"
    );
  }

  record AddedStakedPoolManually(int code, String msg) implements MarginfiError {

    public static final AddedStakedPoolManually INSTANCE = new AddedStakedPoolManually(
        6046, "Use add pool permissionless instead"
    );
  }

  record AssetTagMismatch(int code, String msg) implements MarginfiError {

    public static final AssetTagMismatch INSTANCE = new AssetTagMismatch(
        6047, "Staked SOL accounts can only deposit staked assets and borrow SOL"
    );
  }

  record StakePoolValidationFailed(int code, String msg) implements MarginfiError {

    public static final StakePoolValidationFailed INSTANCE = new StakePoolValidationFailed(
        6048, "Stake pool validation failed: check the stake pool, mint, or sol pool"
    );
  }

  record SwitchboardStalePrice(int code, String msg) implements MarginfiError {

    public static final SwitchboardStalePrice INSTANCE = new SwitchboardStalePrice(
        6049, "Switchboard oracle: stale price"
    );
  }

  record PythPushStalePrice(int code, String msg) implements MarginfiError {

    public static final PythPushStalePrice INSTANCE = new PythPushStalePrice(
        6050, "Pyth Push oracle: stale price"
    );
  }

  record WrongNumberOfOracleAccounts(int code, String msg) implements MarginfiError {

    public static final WrongNumberOfOracleAccounts INSTANCE = new WrongNumberOfOracleAccounts(
        6051, "Oracle error: wrong number of accounts"
    );
  }

  record WrongOracleAccountKeys(int code, String msg) implements MarginfiError {

    public static final WrongOracleAccountKeys INSTANCE = new WrongOracleAccountKeys(
        6052, "Oracle error: wrong account keys"
    );
  }

  record PythPushWrongAccountOwner(int code, String msg) implements MarginfiError {

    public static final PythPushWrongAccountOwner INSTANCE = new PythPushWrongAccountOwner(
        6053, "Pyth Push oracle: wrong account owner"
    );
  }

  record StakedPythPushWrongAccountOwner(int code, String msg) implements MarginfiError {

    public static final StakedPythPushWrongAccountOwner INSTANCE = new StakedPythPushWrongAccountOwner(
        6054, "Staked Pyth Push oracle: wrong account owner"
    );
  }

  record PythPushMismatchedFeedId(int code, String msg) implements MarginfiError {

    public static final PythPushMismatchedFeedId INSTANCE = new PythPushMismatchedFeedId(
        6055, "Pyth Push oracle: mismatched feed id"
    );
  }

  record PythPushInsufficientVerificationLevel(int code, String msg) implements MarginfiError {

    public static final PythPushInsufficientVerificationLevel INSTANCE = new PythPushInsufficientVerificationLevel(
        6056, "Pyth Push oracle: insufficient verification level"
    );
  }

  record PythPushFeedIdMustBe32Bytes(int code, String msg) implements MarginfiError {

    public static final PythPushFeedIdMustBe32Bytes INSTANCE = new PythPushFeedIdMustBe32Bytes(
        6057, "Pyth Push oracle: feed id must be 32 Bytes"
    );
  }

  record PythPushFeedIdNonHexCharacter(int code, String msg) implements MarginfiError {

    public static final PythPushFeedIdNonHexCharacter INSTANCE = new PythPushFeedIdNonHexCharacter(
        6058, "Pyth Push oracle: feed id contains non-hex characters"
    );
  }

  record SwitchboardWrongAccountOwner(int code, String msg) implements MarginfiError {

    public static final SwitchboardWrongAccountOwner INSTANCE = new SwitchboardWrongAccountOwner(
        6059, "Switchboard oracle: wrong account owner"
    );
  }

  record PythPushInvalidAccount(int code, String msg) implements MarginfiError {

    public static final PythPushInvalidAccount INSTANCE = new PythPushInvalidAccount(
        6060, "Pyth Push oracle: invalid account"
    );
  }

  record SwitchboardInvalidAccount(int code, String msg) implements MarginfiError {

    public static final SwitchboardInvalidAccount INSTANCE = new SwitchboardInvalidAccount(
        6061, "Switchboard oracle: invalid account"
    );
  }

  record MathError(int code, String msg) implements MarginfiError {

    public static final MathError INSTANCE = new MathError(
        6062, "Math error"
    );
  }

  record InvalidEmissionsDestinationAccount(int code, String msg) implements MarginfiError {

    public static final InvalidEmissionsDestinationAccount INSTANCE = new InvalidEmissionsDestinationAccount(
        6063, "Invalid emissions destination account"
    );
  }

  record SameAssetAndLiabilityBanks(int code, String msg) implements MarginfiError {

    public static final SameAssetAndLiabilityBanks INSTANCE = new SameAssetAndLiabilityBanks(
        6064, "Asset and liability bank cannot be the same"
    );
  }

  record OverliquidationAttempt(int code, String msg) implements MarginfiError {

    public static final OverliquidationAttempt INSTANCE = new OverliquidationAttempt(
        6065, "Trying to withdraw more assets than available"
    );
  }

  record NoLiabilitiesInLiabilityBank(int code, String msg) implements MarginfiError {

    public static final NoLiabilitiesInLiabilityBank INSTANCE = new NoLiabilitiesInLiabilityBank(
        6066, "Liability bank has no liabilities"
    );
  }

  record AssetsInLiabilityBank(int code, String msg) implements MarginfiError {

    public static final AssetsInLiabilityBank INSTANCE = new AssetsInLiabilityBank(
        6067, "Liability bank has assets"
    );
  }

  record HealthyAccount(int code, String msg) implements MarginfiError {

    public static final HealthyAccount INSTANCE = new HealthyAccount(
        6068, "Account is healthy and cannot be liquidated"
    );
  }

  record ExhaustedLiability(int code, String msg) implements MarginfiError {

    public static final ExhaustedLiability INSTANCE = new ExhaustedLiability(
        6069, "Liability payoff too severe, exhausted liability"
    );
  }

  record TooSeverePayoff(int code, String msg) implements MarginfiError {

    public static final TooSeverePayoff INSTANCE = new TooSeverePayoff(
        6070, "Liability payoff too severe, liability balance has assets"
    );
  }

  record TooSevereLiquidation(int code, String msg) implements MarginfiError {

    public static final TooSevereLiquidation INSTANCE = new TooSevereLiquidation(
        6071, "Liquidation too severe, account above maintenance requirement"
    );
  }

  record WorseHealthPostLiquidation(int code, String msg) implements MarginfiError {

    public static final WorseHealthPostLiquidation INSTANCE = new WorseHealthPostLiquidation(
        6072, "Liquidation would worsen account health"
    );
  }

  record ArenaBankLimit(int code, String msg) implements MarginfiError {

    public static final ArenaBankLimit INSTANCE = new ArenaBankLimit(
        6073, "Arena groups can only support two banks"
    );
  }

  record ArenaSettingCannotChange(int code, String msg) implements MarginfiError {

    public static final ArenaSettingCannotChange INSTANCE = new ArenaSettingCannotChange(
        6074, "Arena groups cannot return to non-arena status"
    );
  }

  record BadEmodeConfig(int code, String msg) implements MarginfiError {

    public static final BadEmodeConfig INSTANCE = new BadEmodeConfig(
        6075, "The Emode config was invalid"
    );
  }

  record PythPushInvalidWindowSize(int code, String msg) implements MarginfiError {

    public static final PythPushInvalidWindowSize INSTANCE = new PythPushInvalidWindowSize(
        6076, "TWAP window size does not match expected duration"
    );
  }

  record InvalidFeesDestinationAccount(int code, String msg) implements MarginfiError {

    public static final InvalidFeesDestinationAccount INSTANCE = new InvalidFeesDestinationAccount(
        6077, "Invalid fees destination account"
    );
  }

  record ZeroAssetPrice(int code, String msg) implements MarginfiError {

    public static final ZeroAssetPrice INSTANCE = new ZeroAssetPrice(
        6078, "Zero asset price"
    );
  }

  record ZeroLiabilityPrice(int code, String msg) implements MarginfiError {

    public static final ZeroLiabilityPrice INSTANCE = new ZeroLiabilityPrice(
        6079, "Zero liability price"
    );
  }

  record OracleMaxConfidenceExceeded(int code, String msg) implements MarginfiError {

    public static final OracleMaxConfidenceExceeded INSTANCE = new OracleMaxConfidenceExceeded(
        6080, "Oracle max confidence exceeded: try again later"
    );
  }

  record BankCannotClose(int code, String msg) implements MarginfiError {

    public static final BankCannotClose INSTANCE = new BankCannotClose(
        6081, "Banks cannot close when they have open positions or emissions outstanding"
    );
  }

  record AccountAlreadyMigrated(int code, String msg) implements MarginfiError {

    public static final AccountAlreadyMigrated INSTANCE = new AccountAlreadyMigrated(
        6082, "Account already migrated"
    );
  }
}
