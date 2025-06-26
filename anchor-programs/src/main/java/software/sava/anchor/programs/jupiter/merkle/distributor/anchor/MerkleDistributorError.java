package software.sava.anchor.programs.jupiter.merkle.distributor.anchor;

import software.sava.anchor.ProgramError;

public sealed interface MerkleDistributorError extends ProgramError permits
    MerkleDistributorError.InsufficientUnlockedTokens,
    MerkleDistributorError.StartTooFarInFuture,
    MerkleDistributorError.InvalidProof,
    MerkleDistributorError.ExceededMaxClaim,
    MerkleDistributorError.MaxNodesExceeded,
    MerkleDistributorError.Unauthorized,
    MerkleDistributorError.OwnerMismatch,
    MerkleDistributorError.ClawbackDuringVesting,
    MerkleDistributorError.ClawbackBeforeStart,
    MerkleDistributorError.ClawbackAlreadyClaimed,
    MerkleDistributorError.InsufficientClawbackDelay,
    MerkleDistributorError.SameClawbackReceiver,
    MerkleDistributorError.SameAdmin,
    MerkleDistributorError.ClaimExpired,
    MerkleDistributorError.ArithmeticError,
    MerkleDistributorError.StartTimestampAfterEnd,
    MerkleDistributorError.TimestampsNotInFuture,
    MerkleDistributorError.InvalidVersion,
    MerkleDistributorError.ClaimingIsNotStarted,
    MerkleDistributorError.CannotCloseDistributor,
    MerkleDistributorError.CannotCloseClaimStatus,
    MerkleDistributorError.InvalidActivationType,
    MerkleDistributorError.TypeCastedError,
    MerkleDistributorError.InvalidOperator,
    MerkleDistributorError.InvalidClaimType,
    MerkleDistributorError.SameOperator,
    MerkleDistributorError.InvalidLocker,
    MerkleDistributorError.EscrowIsNotMaxLock {

  static MerkleDistributorError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InsufficientUnlockedTokens.INSTANCE;
      case 6001 -> StartTooFarInFuture.INSTANCE;
      case 6002 -> InvalidProof.INSTANCE;
      case 6003 -> ExceededMaxClaim.INSTANCE;
      case 6004 -> MaxNodesExceeded.INSTANCE;
      case 6005 -> Unauthorized.INSTANCE;
      case 6006 -> OwnerMismatch.INSTANCE;
      case 6007 -> ClawbackDuringVesting.INSTANCE;
      case 6008 -> ClawbackBeforeStart.INSTANCE;
      case 6009 -> ClawbackAlreadyClaimed.INSTANCE;
      case 6010 -> InsufficientClawbackDelay.INSTANCE;
      case 6011 -> SameClawbackReceiver.INSTANCE;
      case 6012 -> SameAdmin.INSTANCE;
      case 6013 -> ClaimExpired.INSTANCE;
      case 6014 -> ArithmeticError.INSTANCE;
      case 6015 -> StartTimestampAfterEnd.INSTANCE;
      case 6016 -> TimestampsNotInFuture.INSTANCE;
      case 6017 -> InvalidVersion.INSTANCE;
      case 6018 -> ClaimingIsNotStarted.INSTANCE;
      case 6019 -> CannotCloseDistributor.INSTANCE;
      case 6020 -> CannotCloseClaimStatus.INSTANCE;
      case 6021 -> InvalidActivationType.INSTANCE;
      case 6022 -> TypeCastedError.INSTANCE;
      case 6023 -> InvalidOperator.INSTANCE;
      case 6024 -> InvalidClaimType.INSTANCE;
      case 6025 -> SameOperator.INSTANCE;
      case 6026 -> InvalidLocker.INSTANCE;
      case 6027 -> EscrowIsNotMaxLock.INSTANCE;
      default -> throw new IllegalStateException("Unexpected MerkleDistributor error code: " + errorCode);
    };
  }

  record InsufficientUnlockedTokens(int code, String msg) implements MerkleDistributorError {

    public static final InsufficientUnlockedTokens INSTANCE = new InsufficientUnlockedTokens(
        6000, "Insufficient unlocked tokens"
    );
  }

  record StartTooFarInFuture(int code, String msg) implements MerkleDistributorError {

    public static final StartTooFarInFuture INSTANCE = new StartTooFarInFuture(
        6001, "Deposit Start too far in future"
    );
  }

  record InvalidProof(int code, String msg) implements MerkleDistributorError {

    public static final InvalidProof INSTANCE = new InvalidProof(
        6002, "Invalid Merkle proof."
    );
  }

  record ExceededMaxClaim(int code, String msg) implements MerkleDistributorError {

    public static final ExceededMaxClaim INSTANCE = new ExceededMaxClaim(
        6003, "Exceeded maximum claim amount"
    );
  }

  record MaxNodesExceeded(int code, String msg) implements MerkleDistributorError {

    public static final MaxNodesExceeded INSTANCE = new MaxNodesExceeded(
        6004, "Exceeded maximum node count"
    );
  }

  record Unauthorized(int code, String msg) implements MerkleDistributorError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6005, "Account is not authorized to execute this instruction"
    );
  }

  record OwnerMismatch(int code, String msg) implements MerkleDistributorError {

    public static final OwnerMismatch INSTANCE = new OwnerMismatch(
        6006, "Token account owner did not match intended owner"
    );
  }

  record ClawbackDuringVesting(int code, String msg) implements MerkleDistributorError {

    public static final ClawbackDuringVesting INSTANCE = new ClawbackDuringVesting(
        6007, "Clawback cannot be before vesting ends"
    );
  }

  record ClawbackBeforeStart(int code, String msg) implements MerkleDistributorError {

    public static final ClawbackBeforeStart INSTANCE = new ClawbackBeforeStart(
        6008, "Attempted clawback before start"
    );
  }

  record ClawbackAlreadyClaimed(int code, String msg) implements MerkleDistributorError {

    public static final ClawbackAlreadyClaimed INSTANCE = new ClawbackAlreadyClaimed(
        6009, "Clawback already claimed"
    );
  }

  record InsufficientClawbackDelay(int code, String msg) implements MerkleDistributorError {

    public static final InsufficientClawbackDelay INSTANCE = new InsufficientClawbackDelay(
        6010, "Clawback start must be at least one day after vesting end"
    );
  }

  record SameClawbackReceiver(int code, String msg) implements MerkleDistributorError {

    public static final SameClawbackReceiver INSTANCE = new SameClawbackReceiver(
        6011, "New and old Clawback receivers are identical"
    );
  }

  record SameAdmin(int code, String msg) implements MerkleDistributorError {

    public static final SameAdmin INSTANCE = new SameAdmin(
        6012, "New and old admin are identical"
    );
  }

  record ClaimExpired(int code, String msg) implements MerkleDistributorError {

    public static final ClaimExpired INSTANCE = new ClaimExpired(
        6013, "Claim window expired"
    );
  }

  record ArithmeticError(int code, String msg) implements MerkleDistributorError {

    public static final ArithmeticError INSTANCE = new ArithmeticError(
        6014, "Arithmetic Error (overflow/underflow)"
    );
  }

  record StartTimestampAfterEnd(int code, String msg) implements MerkleDistributorError {

    public static final StartTimestampAfterEnd INSTANCE = new StartTimestampAfterEnd(
        6015, "Start Timestamp cannot be after end Timestamp"
    );
  }

  record TimestampsNotInFuture(int code, String msg) implements MerkleDistributorError {

    public static final TimestampsNotInFuture INSTANCE = new TimestampsNotInFuture(
        6016, "Timestamps cannot be in the past"
    );
  }

  record InvalidVersion(int code, String msg) implements MerkleDistributorError {

    public static final InvalidVersion INSTANCE = new InvalidVersion(
        6017, "Airdrop Version Mismatch"
    );
  }

  record ClaimingIsNotStarted(int code, String msg) implements MerkleDistributorError {

    public static final ClaimingIsNotStarted INSTANCE = new ClaimingIsNotStarted(
        6018, "Claiming is not started"
    );
  }

  record CannotCloseDistributor(int code, String msg) implements MerkleDistributorError {

    public static final CannotCloseDistributor INSTANCE = new CannotCloseDistributor(
        6019, "Cannot close distributor"
    );
  }

  record CannotCloseClaimStatus(int code, String msg) implements MerkleDistributorError {

    public static final CannotCloseClaimStatus INSTANCE = new CannotCloseClaimStatus(
        6020, "Cannot close claim status"
    );
  }

  record InvalidActivationType(int code, String msg) implements MerkleDistributorError {

    public static final InvalidActivationType INSTANCE = new InvalidActivationType(
        6021, "Invalid activation type"
    );
  }

  record TypeCastedError(int code, String msg) implements MerkleDistributorError {

    public static final TypeCastedError INSTANCE = new TypeCastedError(
        6022, "Type casted error"
    );
  }

  record InvalidOperator(int code, String msg) implements MerkleDistributorError {

    public static final InvalidOperator INSTANCE = new InvalidOperator(
        6023, "Invalid operator"
    );
  }

  record InvalidClaimType(int code, String msg) implements MerkleDistributorError {

    public static final InvalidClaimType INSTANCE = new InvalidClaimType(
        6024, "Invalid claim type"
    );
  }

  record SameOperator(int code, String msg) implements MerkleDistributorError {

    public static final SameOperator INSTANCE = new SameOperator(
        6025, "Same operator"
    );
  }

  record InvalidLocker(int code, String msg) implements MerkleDistributorError {

    public static final InvalidLocker INSTANCE = new InvalidLocker(
        6026, "Invalid locker"
    );
  }

  record EscrowIsNotMaxLock(int code, String msg) implements MerkleDistributorError {

    public static final EscrowIsNotMaxLock INSTANCE = new EscrowIsNotMaxLock(
        6027, "Escrow is not max lock"
    );
  }
}
