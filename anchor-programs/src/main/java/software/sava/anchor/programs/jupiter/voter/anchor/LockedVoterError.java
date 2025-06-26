package software.sava.anchor.programs.jupiter.voter.anchor;

import software.sava.anchor.ProgramError;

public sealed interface LockedVoterError extends ProgramError permits
    LockedVoterError.LockupDurationTooShort,
    LockedVoterError.LockupDurationTooLong,
    LockedVoterError.RefreshCannotShorten,
    LockedVoterError.EscrowNotEnded,
    LockedVoterError.MaxLockIsSet,
    LockedVoterError.ExpirationIsLessThanCurrentTime,
    LockedVoterError.LockerIsExpired,
    LockedVoterError.ExpirationIsNotZero,
    LockedVoterError.AmountIsZero,
    LockedVoterError.MaxLockIsNotSet,
    LockedVoterError.InvalidAmountForPartialUnstaking,
    LockedVoterError.EscrowHasBeenEnded,
    LockedVoterError.InvalidUnstakingLockDuration,
    LockedVoterError.PartialUnstakingAmountIsNotZero,
    LockedVoterError.PartialUnstakingIsNotEnded {

  static LockedVoterError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> LockupDurationTooShort.INSTANCE;
      case 6001 -> LockupDurationTooLong.INSTANCE;
      case 6002 -> RefreshCannotShorten.INSTANCE;
      case 6003 -> EscrowNotEnded.INSTANCE;
      case 6004 -> MaxLockIsSet.INSTANCE;
      case 6005 -> ExpirationIsLessThanCurrentTime.INSTANCE;
      case 6006 -> LockerIsExpired.INSTANCE;
      case 6007 -> ExpirationIsNotZero.INSTANCE;
      case 6008 -> AmountIsZero.INSTANCE;
      case 6009 -> MaxLockIsNotSet.INSTANCE;
      case 6010 -> InvalidAmountForPartialUnstaking.INSTANCE;
      case 6011 -> EscrowHasBeenEnded.INSTANCE;
      case 6012 -> InvalidUnstakingLockDuration.INSTANCE;
      case 6013 -> PartialUnstakingAmountIsNotZero.INSTANCE;
      case 6014 -> PartialUnstakingIsNotEnded.INSTANCE;
      default -> throw new IllegalStateException("Unexpected LockedVoter error code: " + errorCode);
    };
  }

  record LockupDurationTooShort(int code, String msg) implements LockedVoterError {

    public static final LockupDurationTooShort INSTANCE = new LockupDurationTooShort(
        6000, "Lockup duration must at least be the min stake duration"
    );
  }

  record LockupDurationTooLong(int code, String msg) implements LockedVoterError {

    public static final LockupDurationTooLong INSTANCE = new LockupDurationTooLong(
        6001, "Lockup duration must at most be the max stake duration"
    );
  }

  record RefreshCannotShorten(int code, String msg) implements LockedVoterError {

    public static final RefreshCannotShorten INSTANCE = new RefreshCannotShorten(
        6002, "A voting escrow refresh cannot shorten the escrow time remaining"
    );
  }

  record EscrowNotEnded(int code, String msg) implements LockedVoterError {

    public static final EscrowNotEnded INSTANCE = new EscrowNotEnded(
        6003, "Escrow has not ended"
    );
  }

  record MaxLockIsSet(int code, String msg) implements LockedVoterError {

    public static final MaxLockIsSet INSTANCE = new MaxLockIsSet(
        6004, "Maxlock is set"
    );
  }

  record ExpirationIsLessThanCurrentTime(int code, String msg) implements LockedVoterError {

    public static final ExpirationIsLessThanCurrentTime INSTANCE = new ExpirationIsLessThanCurrentTime(
        6005, "Cannot set expiration less than the current time"
    );
  }

  record LockerIsExpired(int code, String msg) implements LockedVoterError {

    public static final LockerIsExpired INSTANCE = new LockerIsExpired(
        6006, "Locker is expired"
    );
  }

  record ExpirationIsNotZero(int code, String msg) implements LockedVoterError {

    public static final ExpirationIsNotZero INSTANCE = new ExpirationIsNotZero(
        6007, "Expiration is not zero"
    );
  }

  record AmountIsZero(int code, String msg) implements LockedVoterError {

    public static final AmountIsZero INSTANCE = new AmountIsZero(
        6008, "Amount is zero"
    );
  }

  record MaxLockIsNotSet(int code, String msg) implements LockedVoterError {

    public static final MaxLockIsNotSet INSTANCE = new MaxLockIsNotSet(
        6009, "Maxlock is not set"
    );
  }

  record InvalidAmountForPartialUnstaking(int code, String msg) implements LockedVoterError {

    public static final InvalidAmountForPartialUnstaking INSTANCE = new InvalidAmountForPartialUnstaking(
        6010, "Invalid amount for partial unstaking"
    );
  }

  record EscrowHasBeenEnded(int code, String msg) implements LockedVoterError {

    public static final EscrowHasBeenEnded INSTANCE = new EscrowHasBeenEnded(
        6011, "Escrow has been ended"
    );
  }

  record InvalidUnstakingLockDuration(int code, String msg) implements LockedVoterError {

    public static final InvalidUnstakingLockDuration INSTANCE = new InvalidUnstakingLockDuration(
        6012, "Invalid unstaking lock duration"
    );
  }

  record PartialUnstakingAmountIsNotZero(int code, String msg) implements LockedVoterError {

    public static final PartialUnstakingAmountIsNotZero INSTANCE = new PartialUnstakingAmountIsNotZero(
        6013, "Partial unstaking amount is not zero"
    );
  }

  record PartialUnstakingIsNotEnded(int code, String msg) implements LockedVoterError {

    public static final PartialUnstakingIsNotEnded INSTANCE = new PartialUnstakingIsNotEnded(
        6014, "Partial unstaking has not ended"
    );
  }
}
