package software.sava.anchor.programs.meteora.alpha_vault.anchor;

import software.sava.anchor.ProgramError;

public sealed interface AlphaVaultError extends ProgramError permits
    AlphaVaultError.StartSlotAfterEnd,
    AlphaVaultError.SlotNotInFuture,
    AlphaVaultError.IncorrectTokenMint,
    AlphaVaultError.IncorrectPairType,
    AlphaVaultError.PoolHasStarted,
    AlphaVaultError.NotPermitThisActionInThisSlot,
    AlphaVaultError.TheSaleIsOngoing,
    AlphaVaultError.EscrowIsNotClosable,
    AlphaVaultError.SlotOrdersAreIncorrect,
    AlphaVaultError.EscrowHasRefuned,
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
    AlphaVaultError.RefundAmountIsZero,
    AlphaVaultError.DepositingSlotDurationIsInvalid,
    AlphaVaultError.DepositingSlotIsInvalid,
    AlphaVaultError.IndividualDepositingCapIsZero,
    AlphaVaultError.InvalidFeeReceiverAccount,
    AlphaVaultError.NotPermissionedVault,
    AlphaVaultError.NotPermitToDoThisAction,
    AlphaVaultError.InvalidProof {

  static AlphaVaultError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> StartSlotAfterEnd.INSTANCE;
      case 6001 -> SlotNotInFuture.INSTANCE;
      case 6002 -> IncorrectTokenMint.INSTANCE;
      case 6003 -> IncorrectPairType.INSTANCE;
      case 6004 -> PoolHasStarted.INSTANCE;
      case 6005 -> NotPermitThisActionInThisSlot.INSTANCE;
      case 6006 -> TheSaleIsOngoing.INSTANCE;
      case 6007 -> EscrowIsNotClosable.INSTANCE;
      case 6008 -> SlotOrdersAreIncorrect.INSTANCE;
      case 6009 -> EscrowHasRefuned.INSTANCE;
      case 6010 -> MathOverflow.INSTANCE;
      case 6011 -> MaxBuyingCapIsZero.INSTANCE;
      case 6012 -> MaxAmountIsTooSmall.INSTANCE;
      case 6013 -> PoolTypeIsNotSupported.INSTANCE;
      case 6014 -> InvalidAdmin.INSTANCE;
      case 6015 -> VaultModeIsIncorrect.INSTANCE;
      case 6016 -> MaxDepositingCapIsInValid.INSTANCE;
      case 6017 -> VestingDurationIsInValid.INSTANCE;
      case 6018 -> DepositAmountIsZero.INSTANCE;
      case 6019 -> PoolOwnerIsMismatched.INSTANCE;
      case 6020 -> RefundAmountIsZero.INSTANCE;
      case 6021 -> DepositingSlotDurationIsInvalid.INSTANCE;
      case 6022 -> DepositingSlotIsInvalid.INSTANCE;
      case 6023 -> IndividualDepositingCapIsZero.INSTANCE;
      case 6024 -> InvalidFeeReceiverAccount.INSTANCE;
      case 6025 -> NotPermissionedVault.INSTANCE;
      case 6026 -> NotPermitToDoThisAction.INSTANCE;
      case 6027 -> InvalidProof.INSTANCE;
      default -> throw new IllegalStateException("Unexpected AlphaVault error code: " + errorCode);
    };
  }

  record StartSlotAfterEnd(int code, String msg) implements AlphaVaultError {

    public static final StartSlotAfterEnd INSTANCE = new StartSlotAfterEnd(
        6000, "start slot is after end slot"
    );
  }

  record SlotNotInFuture(int code, String msg) implements AlphaVaultError {

    public static final SlotNotInFuture INSTANCE = new SlotNotInFuture(
        6001, "slot is not in future"
    );
  }

  record IncorrectTokenMint(int code, String msg) implements AlphaVaultError {

    public static final IncorrectTokenMint INSTANCE = new IncorrectTokenMint(
        6002, "token mint is incorrect"
    );
  }

  record IncorrectPairType(int code, String msg) implements AlphaVaultError {

    public static final IncorrectPairType INSTANCE = new IncorrectPairType(
        6003, "pair is not permissioned"
    );
  }

  record PoolHasStarted(int code, String msg) implements AlphaVaultError {

    public static final PoolHasStarted INSTANCE = new PoolHasStarted(
        6004, "Pool has started"
    );
  }

  record NotPermitThisActionInThisSlot(int code, String msg) implements AlphaVaultError {

    public static final NotPermitThisActionInThisSlot INSTANCE = new NotPermitThisActionInThisSlot(
        6005, "This action is not permitted in this slot"
    );
  }

  record TheSaleIsOngoing(int code, String msg) implements AlphaVaultError {

    public static final TheSaleIsOngoing INSTANCE = new TheSaleIsOngoing(
        6006, "the sale is on going, cannot withdraw"
    );
  }

  record EscrowIsNotClosable(int code, String msg) implements AlphaVaultError {

    public static final EscrowIsNotClosable INSTANCE = new EscrowIsNotClosable(
        6007, "Escrow is not closable"
    );
  }

  record SlotOrdersAreIncorrect(int code, String msg) implements AlphaVaultError {

    public static final SlotOrdersAreIncorrect INSTANCE = new SlotOrdersAreIncorrect(
        6008, "Slot orders are incorrect"
    );
  }

  record EscrowHasRefuned(int code, String msg) implements AlphaVaultError {

    public static final EscrowHasRefuned INSTANCE = new EscrowHasRefuned(
        6009, "Escrow has refunded"
    );
  }

  record MathOverflow(int code, String msg) implements AlphaVaultError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6010, "Math operation overflow"
    );
  }

  record MaxBuyingCapIsZero(int code, String msg) implements AlphaVaultError {

    public static final MaxBuyingCapIsZero INSTANCE = new MaxBuyingCapIsZero(
        6011, "Max buying cap is zero"
    );
  }

  record MaxAmountIsTooSmall(int code, String msg) implements AlphaVaultError {

    public static final MaxAmountIsTooSmall INSTANCE = new MaxAmountIsTooSmall(
        6012, "Max amount is too small"
    );
  }

  record PoolTypeIsNotSupported(int code, String msg) implements AlphaVaultError {

    public static final PoolTypeIsNotSupported INSTANCE = new PoolTypeIsNotSupported(
        6013, "Pool type is not supported"
    );
  }

  record InvalidAdmin(int code, String msg) implements AlphaVaultError {

    public static final InvalidAdmin INSTANCE = new InvalidAdmin(
        6014, "Invalid admin"
    );
  }

  record VaultModeIsIncorrect(int code, String msg) implements AlphaVaultError {

    public static final VaultModeIsIncorrect INSTANCE = new VaultModeIsIncorrect(
        6015, "Vault mode is incorrect"
    );
  }

  record MaxDepositingCapIsInValid(int code, String msg) implements AlphaVaultError {

    public static final MaxDepositingCapIsInValid INSTANCE = new MaxDepositingCapIsInValid(
        6016, "Max depositing cap is invalid"
    );
  }

  record VestingDurationIsInValid(int code, String msg) implements AlphaVaultError {

    public static final VestingDurationIsInValid INSTANCE = new VestingDurationIsInValid(
        6017, "Vesting duration is invalid"
    );
  }

  record DepositAmountIsZero(int code, String msg) implements AlphaVaultError {

    public static final DepositAmountIsZero INSTANCE = new DepositAmountIsZero(
        6018, "Deposit amount is zero"
    );
  }

  record PoolOwnerIsMismatched(int code, String msg) implements AlphaVaultError {

    public static final PoolOwnerIsMismatched INSTANCE = new PoolOwnerIsMismatched(
        6019, "Pool owner is mismatched"
    );
  }

  record RefundAmountIsZero(int code, String msg) implements AlphaVaultError {

    public static final RefundAmountIsZero INSTANCE = new RefundAmountIsZero(
        6020, "Refund amount is zero"
    );
  }

  record DepositingSlotDurationIsInvalid(int code, String msg) implements AlphaVaultError {

    public static final DepositingSlotDurationIsInvalid INSTANCE = new DepositingSlotDurationIsInvalid(
        6021, "Depositing slot duration is invalid"
    );
  }

  record DepositingSlotIsInvalid(int code, String msg) implements AlphaVaultError {

    public static final DepositingSlotIsInvalid INSTANCE = new DepositingSlotIsInvalid(
        6022, "Depositing slot is invalid"
    );
  }

  record IndividualDepositingCapIsZero(int code, String msg) implements AlphaVaultError {

    public static final IndividualDepositingCapIsZero INSTANCE = new IndividualDepositingCapIsZero(
        6023, "Individual depositing cap is zero"
    );
  }

  record InvalidFeeReceiverAccount(int code, String msg) implements AlphaVaultError {

    public static final InvalidFeeReceiverAccount INSTANCE = new InvalidFeeReceiverAccount(
        6024, "Invalid fee receiver account"
    );
  }

  record NotPermissionedVault(int code, String msg) implements AlphaVaultError {

    public static final NotPermissionedVault INSTANCE = new NotPermissionedVault(
        6025, "Not permissioned vault"
    );
  }

  record NotPermitToDoThisAction(int code, String msg) implements AlphaVaultError {

    public static final NotPermitToDoThisAction INSTANCE = new NotPermitToDoThisAction(
        6026, "Not permit to do this action"
    );
  }

  record InvalidProof(int code, String msg) implements AlphaVaultError {

    public static final InvalidProof INSTANCE = new InvalidProof(
        6027, "Invalid Merkle proof."
    );
  }
}
