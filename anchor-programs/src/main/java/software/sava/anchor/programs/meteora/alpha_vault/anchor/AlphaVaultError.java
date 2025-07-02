package software.sava.anchor.programs.meteora.alpha_vault.anchor;

import software.sava.anchor.ProgramError;

public sealed interface AlphaVaultError extends ProgramError permits
    AlphaVaultError.TimePointNotInFuture,
    AlphaVaultError.IncorrectTokenMint,
    AlphaVaultError.IncorrectPairType,
    AlphaVaultError.PoolHasStarted,
    AlphaVaultError.NotPermitThisActionInThisTimePoint,
    AlphaVaultError.TheSaleIsOngoing,
    AlphaVaultError.EscrowIsNotClosable,
    AlphaVaultError.TimePointOrdersAreIncorrect,
    AlphaVaultError.EscrowHasRefunded,
    AlphaVaultError.MathOverflow,
    AlphaVaultError.MaxBuyingCapIsZero,
    AlphaVaultError.MaxAmountIsTooSmall,
    AlphaVaultError.PoolTypeIsNotSupported,
    AlphaVaultError.InvalidAdmin,
    AlphaVaultError.VaultModeIsIncorrect,
    AlphaVaultError.MaxDepositingCapIsInValid,
    AlphaVaultError.VestingDurationIsInValid,
    AlphaVaultError.DepositAmountIsZero,
    AlphaVaultError.PoolOwnerIsMismatched,
    AlphaVaultError.WithdrawAmountIsZero,
    AlphaVaultError.DepositingDurationIsInvalid,
    AlphaVaultError.DepositingTimePointIsInvalid,
    AlphaVaultError.IndividualDepositingCapIsZero,
    AlphaVaultError.InvalidFeeReceiverAccount,
    AlphaVaultError.NotPermissionedVault,
    AlphaVaultError.NotPermitToDoThisAction,
    AlphaVaultError.InvalidProof,
    AlphaVaultError.InvalidActivationType,
    AlphaVaultError.ActivationTypeIsMismatched,
    AlphaVaultError.InvalidPool,
    AlphaVaultError.InvalidCreator,
    AlphaVaultError.PermissionedVaultCannotChargeEscrowFee,
    AlphaVaultError.EscrowFeeTooHigh,
    AlphaVaultError.LockDurationInvalid,
    AlphaVaultError.MaxBuyingCapIsTooSmall,
    AlphaVaultError.MaxDepositingCapIsTooSmall,
    AlphaVaultError.InvalidWhitelistWalletMode,
    AlphaVaultError.InvalidCrankFeeWhitelist,
    AlphaVaultError.MissingFeeReceiver,
    AlphaVaultError.DiscriminatorIsMismatched {

  static AlphaVaultError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> TimePointNotInFuture.INSTANCE;
      case 6001 -> IncorrectTokenMint.INSTANCE;
      case 6002 -> IncorrectPairType.INSTANCE;
      case 6003 -> PoolHasStarted.INSTANCE;
      case 6004 -> NotPermitThisActionInThisTimePoint.INSTANCE;
      case 6005 -> TheSaleIsOngoing.INSTANCE;
      case 6006 -> EscrowIsNotClosable.INSTANCE;
      case 6007 -> TimePointOrdersAreIncorrect.INSTANCE;
      case 6008 -> EscrowHasRefunded.INSTANCE;
      case 6009 -> MathOverflow.INSTANCE;
      case 6010 -> MaxBuyingCapIsZero.INSTANCE;
      case 6011 -> MaxAmountIsTooSmall.INSTANCE;
      case 6012 -> PoolTypeIsNotSupported.INSTANCE;
      case 6013 -> InvalidAdmin.INSTANCE;
      case 6014 -> VaultModeIsIncorrect.INSTANCE;
      case 6015 -> MaxDepositingCapIsInValid.INSTANCE;
      case 6016 -> VestingDurationIsInValid.INSTANCE;
      case 6017 -> DepositAmountIsZero.INSTANCE;
      case 6018 -> PoolOwnerIsMismatched.INSTANCE;
      case 6019 -> WithdrawAmountIsZero.INSTANCE;
      case 6020 -> DepositingDurationIsInvalid.INSTANCE;
      case 6021 -> DepositingTimePointIsInvalid.INSTANCE;
      case 6022 -> IndividualDepositingCapIsZero.INSTANCE;
      case 6023 -> InvalidFeeReceiverAccount.INSTANCE;
      case 6024 -> NotPermissionedVault.INSTANCE;
      case 6025 -> NotPermitToDoThisAction.INSTANCE;
      case 6026 -> InvalidProof.INSTANCE;
      case 6027 -> InvalidActivationType.INSTANCE;
      case 6028 -> ActivationTypeIsMismatched.INSTANCE;
      case 6029 -> InvalidPool.INSTANCE;
      case 6030 -> InvalidCreator.INSTANCE;
      case 6031 -> PermissionedVaultCannotChargeEscrowFee.INSTANCE;
      case 6032 -> EscrowFeeTooHigh.INSTANCE;
      case 6033 -> LockDurationInvalid.INSTANCE;
      case 6034 -> MaxBuyingCapIsTooSmall.INSTANCE;
      case 6035 -> MaxDepositingCapIsTooSmall.INSTANCE;
      case 6036 -> InvalidWhitelistWalletMode.INSTANCE;
      case 6037 -> InvalidCrankFeeWhitelist.INSTANCE;
      case 6038 -> MissingFeeReceiver.INSTANCE;
      case 6039 -> DiscriminatorIsMismatched.INSTANCE;
      default -> throw new IllegalStateException("Unexpected AlphaVault error code: " + errorCode);
    };
  }

  record TimePointNotInFuture(int code, String msg) implements AlphaVaultError {

    public static final TimePointNotInFuture INSTANCE = new TimePointNotInFuture(
        6000, "Time point is not in future"
    );
  }

  record IncorrectTokenMint(int code, String msg) implements AlphaVaultError {

    public static final IncorrectTokenMint INSTANCE = new IncorrectTokenMint(
        6001, "Token mint is incorrect"
    );
  }

  record IncorrectPairType(int code, String msg) implements AlphaVaultError {

    public static final IncorrectPairType INSTANCE = new IncorrectPairType(
        6002, "Pair is not permissioned"
    );
  }

  record PoolHasStarted(int code, String msg) implements AlphaVaultError {

    public static final PoolHasStarted INSTANCE = new PoolHasStarted(
        6003, "Pool has started"
    );
  }

  record NotPermitThisActionInThisTimePoint(int code, String msg) implements AlphaVaultError {

    public static final NotPermitThisActionInThisTimePoint INSTANCE = new NotPermitThisActionInThisTimePoint(
        6004, "This action is not permitted in this time point"
    );
  }

  record TheSaleIsOngoing(int code, String msg) implements AlphaVaultError {

    public static final TheSaleIsOngoing INSTANCE = new TheSaleIsOngoing(
        6005, "The sale is on going, cannot withdraw"
    );
  }

  record EscrowIsNotClosable(int code, String msg) implements AlphaVaultError {

    public static final EscrowIsNotClosable INSTANCE = new EscrowIsNotClosable(
        6006, "Escrow is not closable"
    );
  }

  record TimePointOrdersAreIncorrect(int code, String msg) implements AlphaVaultError {

    public static final TimePointOrdersAreIncorrect INSTANCE = new TimePointOrdersAreIncorrect(
        6007, "Time point orders are incorrect"
    );
  }

  record EscrowHasRefunded(int code, String msg) implements AlphaVaultError {

    public static final EscrowHasRefunded INSTANCE = new EscrowHasRefunded(
        6008, "Escrow has refunded"
    );
  }

  record MathOverflow(int code, String msg) implements AlphaVaultError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6009, "Math operation overflow"
    );
  }

  record MaxBuyingCapIsZero(int code, String msg) implements AlphaVaultError {

    public static final MaxBuyingCapIsZero INSTANCE = new MaxBuyingCapIsZero(
        6010, "Max buying cap is zero"
    );
  }

  record MaxAmountIsTooSmall(int code, String msg) implements AlphaVaultError {

    public static final MaxAmountIsTooSmall INSTANCE = new MaxAmountIsTooSmall(
        6011, "Max amount is too small"
    );
  }

  record PoolTypeIsNotSupported(int code, String msg) implements AlphaVaultError {

    public static final PoolTypeIsNotSupported INSTANCE = new PoolTypeIsNotSupported(
        6012, "Pool type is not supported"
    );
  }

  record InvalidAdmin(int code, String msg) implements AlphaVaultError {

    public static final InvalidAdmin INSTANCE = new InvalidAdmin(
        6013, "Invalid admin"
    );
  }

  record VaultModeIsIncorrect(int code, String msg) implements AlphaVaultError {

    public static final VaultModeIsIncorrect INSTANCE = new VaultModeIsIncorrect(
        6014, "Vault mode is incorrect"
    );
  }

  record MaxDepositingCapIsInValid(int code, String msg) implements AlphaVaultError {

    public static final MaxDepositingCapIsInValid INSTANCE = new MaxDepositingCapIsInValid(
        6015, "Max depositing cap is invalid"
    );
  }

  record VestingDurationIsInValid(int code, String msg) implements AlphaVaultError {

    public static final VestingDurationIsInValid INSTANCE = new VestingDurationIsInValid(
        6016, "Vesting duration is invalid"
    );
  }

  record DepositAmountIsZero(int code, String msg) implements AlphaVaultError {

    public static final DepositAmountIsZero INSTANCE = new DepositAmountIsZero(
        6017, "Deposit amount is zero"
    );
  }

  record PoolOwnerIsMismatched(int code, String msg) implements AlphaVaultError {

    public static final PoolOwnerIsMismatched INSTANCE = new PoolOwnerIsMismatched(
        6018, "Pool owner is mismatched"
    );
  }

  record WithdrawAmountIsZero(int code, String msg) implements AlphaVaultError {

    public static final WithdrawAmountIsZero INSTANCE = new WithdrawAmountIsZero(
        6019, "Withdraw amount is zero"
    );
  }

  record DepositingDurationIsInvalid(int code, String msg) implements AlphaVaultError {

    public static final DepositingDurationIsInvalid INSTANCE = new DepositingDurationIsInvalid(
        6020, "Depositing duration is invalid"
    );
  }

  record DepositingTimePointIsInvalid(int code, String msg) implements AlphaVaultError {

    public static final DepositingTimePointIsInvalid INSTANCE = new DepositingTimePointIsInvalid(
        6021, "Depositing time point is invalid"
    );
  }

  record IndividualDepositingCapIsZero(int code, String msg) implements AlphaVaultError {

    public static final IndividualDepositingCapIsZero INSTANCE = new IndividualDepositingCapIsZero(
        6022, "Individual depositing cap is zero"
    );
  }

  record InvalidFeeReceiverAccount(int code, String msg) implements AlphaVaultError {

    public static final InvalidFeeReceiverAccount INSTANCE = new InvalidFeeReceiverAccount(
        6023, "Invalid fee receiver account"
    );
  }

  record NotPermissionedVault(int code, String msg) implements AlphaVaultError {

    public static final NotPermissionedVault INSTANCE = new NotPermissionedVault(
        6024, "Not permissioned vault"
    );
  }

  record NotPermitToDoThisAction(int code, String msg) implements AlphaVaultError {

    public static final NotPermitToDoThisAction INSTANCE = new NotPermitToDoThisAction(
        6025, "Not permit to do this action"
    );
  }

  record InvalidProof(int code, String msg) implements AlphaVaultError {

    public static final InvalidProof INSTANCE = new InvalidProof(
        6026, "Invalid Merkle proof"
    );
  }

  record InvalidActivationType(int code, String msg) implements AlphaVaultError {

    public static final InvalidActivationType INSTANCE = new InvalidActivationType(
        6027, "Invalid activation type"
    );
  }

  record ActivationTypeIsMismatched(int code, String msg) implements AlphaVaultError {

    public static final ActivationTypeIsMismatched INSTANCE = new ActivationTypeIsMismatched(
        6028, "Activation type is mismatched"
    );
  }

  record InvalidPool(int code, String msg) implements AlphaVaultError {

    public static final InvalidPool INSTANCE = new InvalidPool(
        6029, "Pool is not connected to the alpha vault"
    );
  }

  record InvalidCreator(int code, String msg) implements AlphaVaultError {

    public static final InvalidCreator INSTANCE = new InvalidCreator(
        6030, "Invalid creator"
    );
  }

  record PermissionedVaultCannotChargeEscrowFee(int code, String msg) implements AlphaVaultError {

    public static final PermissionedVaultCannotChargeEscrowFee INSTANCE = new PermissionedVaultCannotChargeEscrowFee(
        6031, "Permissioned vault cannot charge escrow fee"
    );
  }

  record EscrowFeeTooHigh(int code, String msg) implements AlphaVaultError {

    public static final EscrowFeeTooHigh INSTANCE = new EscrowFeeTooHigh(
        6032, "Escrow fee too high"
    );
  }

  record LockDurationInvalid(int code, String msg) implements AlphaVaultError {

    public static final LockDurationInvalid INSTANCE = new LockDurationInvalid(
        6033, "Lock duration is invalid"
    );
  }

  record MaxBuyingCapIsTooSmall(int code, String msg) implements AlphaVaultError {

    public static final MaxBuyingCapIsTooSmall INSTANCE = new MaxBuyingCapIsTooSmall(
        6034, "Max buying cap is too small"
    );
  }

  record MaxDepositingCapIsTooSmall(int code, String msg) implements AlphaVaultError {

    public static final MaxDepositingCapIsTooSmall INSTANCE = new MaxDepositingCapIsTooSmall(
        6035, "Max depositing cap is too small"
    );
  }

  record InvalidWhitelistWalletMode(int code, String msg) implements AlphaVaultError {

    public static final InvalidWhitelistWalletMode INSTANCE = new InvalidWhitelistWalletMode(
        6036, "Invalid whitelist wallet mode"
    );
  }

  record InvalidCrankFeeWhitelist(int code, String msg) implements AlphaVaultError {

    public static final InvalidCrankFeeWhitelist INSTANCE = new InvalidCrankFeeWhitelist(
        6037, "Invalid crank fee whitelist"
    );
  }

  record MissingFeeReceiver(int code, String msg) implements AlphaVaultError {

    public static final MissingFeeReceiver INSTANCE = new MissingFeeReceiver(
        6038, "Missing fee receiver"
    );
  }

  record DiscriminatorIsMismatched(int code, String msg) implements AlphaVaultError {

    public static final DiscriminatorIsMismatched INSTANCE = new DiscriminatorIsMismatched(
        6039, "Discriminator is mismatched"
    );
  }
}
