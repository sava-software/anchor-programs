package software.sava.anchor.programs.jito.steward.anchor;

import software.sava.anchor.ProgramError;

public sealed interface StewardError extends ProgramError permits
    StewardError.InvalidAuthorityType,
    StewardError.ScoringNotComplete,
    StewardError.ValidatorNotInList,
    StewardError.Unauthorized,
    StewardError.BitmaskOutOfBounds,
    StewardError.InvalidState,
    StewardError.StakeStateIsNotStake,
    StewardError.ValidatorBelowStakeMinimum,
    StewardError.ValidatorBelowLivenessMinimum,
    StewardError.VoteHistoryNotRecentEnough,
    StewardError.StakeHistoryNotRecentEnough,
    StewardError.ClusterHistoryNotRecentEnough,
    StewardError.StateMachinePaused,
    StewardError.InvalidParameterValue,
    StewardError.InstantUnstakeNotReady,
    StewardError.ValidatorIndexOutOfBounds,
    StewardError.ValidatorListTypeMismatch,
    StewardError.ArithmeticError,
    StewardError.ValidatorNotRemovable,
    StewardError.ValidatorMarkedActive,
    StewardError.MaxValidatorsReached,
    StewardError.EpochMaintenanceNotComplete,
    StewardError.StakePoolNotUpdated,
    StewardError.EpochMaintenanceAlreadyComplete,
    StewardError.ValidatorsNeedToBeRemoved,
    StewardError.ValidatorNotMarkedForRemoval,
    StewardError.ValidatorsHaveNotBeenRemoved,
    StewardError.ListStateMismatch,
    StewardError.VoteAccountDoesNotMatch,
    StewardError.ValidatorNeedsToBeMarkedForRemoval,
    StewardError.InvalidStakeState {

  static StewardError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidAuthorityType.INSTANCE;
      case 6001 -> ScoringNotComplete.INSTANCE;
      case 6002 -> ValidatorNotInList.INSTANCE;
      case 6003 -> Unauthorized.INSTANCE;
      case 6004 -> BitmaskOutOfBounds.INSTANCE;
      case 6005 -> InvalidState.INSTANCE;
      case 6006 -> StakeStateIsNotStake.INSTANCE;
      case 6007 -> ValidatorBelowStakeMinimum.INSTANCE;
      case 6008 -> ValidatorBelowLivenessMinimum.INSTANCE;
      case 6009 -> VoteHistoryNotRecentEnough.INSTANCE;
      case 6010 -> StakeHistoryNotRecentEnough.INSTANCE;
      case 6011 -> ClusterHistoryNotRecentEnough.INSTANCE;
      case 6012 -> StateMachinePaused.INSTANCE;
      case 6013 -> InvalidParameterValue.INSTANCE;
      case 6014 -> InstantUnstakeNotReady.INSTANCE;
      case 6015 -> ValidatorIndexOutOfBounds.INSTANCE;
      case 6016 -> ValidatorListTypeMismatch.INSTANCE;
      case 6017 -> ArithmeticError.INSTANCE;
      case 6018 -> ValidatorNotRemovable.INSTANCE;
      case 6019 -> ValidatorMarkedActive.INSTANCE;
      case 6020 -> MaxValidatorsReached.INSTANCE;
      case 6021 -> EpochMaintenanceNotComplete.INSTANCE;
      case 6022 -> StakePoolNotUpdated.INSTANCE;
      case 6023 -> EpochMaintenanceAlreadyComplete.INSTANCE;
      case 6024 -> ValidatorsNeedToBeRemoved.INSTANCE;
      case 6025 -> ValidatorNotMarkedForRemoval.INSTANCE;
      case 6026 -> ValidatorsHaveNotBeenRemoved.INSTANCE;
      case 6027 -> ListStateMismatch.INSTANCE;
      case 6028 -> VoteAccountDoesNotMatch.INSTANCE;
      case 6029 -> ValidatorNeedsToBeMarkedForRemoval.INSTANCE;
      case 6030 -> InvalidStakeState.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Steward error code: " + errorCode);
    };
  }

  record InvalidAuthorityType(int code, String msg) implements StewardError {

    public static final InvalidAuthorityType INSTANCE = new InvalidAuthorityType(
        6000, "Invalid set authority type: 0: SetAdmin, 1: SetBlacklistAuthority, 2: SetParametersAuthority"
    );
  }

  record ScoringNotComplete(int code, String msg) implements StewardError {

    public static final ScoringNotComplete INSTANCE = new ScoringNotComplete(
        6001, "Scoring must be completed before any other steps can be taken"
    );
  }

  record ValidatorNotInList(int code, String msg) implements StewardError {

    public static final ValidatorNotInList INSTANCE = new ValidatorNotInList(
        6002, "Validator does not exist at the ValidatorList index provided"
    );
  }

  record Unauthorized(int code, String msg) implements StewardError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6003, "Unauthorized to perform this action"
    );
  }

  record BitmaskOutOfBounds(int code, String msg) implements StewardError {

    public static final BitmaskOutOfBounds INSTANCE = new BitmaskOutOfBounds(
        6004, "Bitmask index out of bounds"
    );
  }

  record InvalidState(int code, String msg) implements StewardError {

    public static final InvalidState INSTANCE = new InvalidState(
        6005, "Invalid state"
    );
  }

  record StakeStateIsNotStake(int code, String msg) implements StewardError {

    public static final StakeStateIsNotStake INSTANCE = new StakeStateIsNotStake(
        6006, "Stake state is not Stake"
    );
  }

  record ValidatorBelowStakeMinimum(int code, String msg) implements StewardError {

    public static final ValidatorBelowStakeMinimum INSTANCE = new ValidatorBelowStakeMinimum(
        6007, "Validator not eligible to be added to the pool. Must meet stake minimum"
    );
  }

  record ValidatorBelowLivenessMinimum(int code, String msg) implements StewardError {

    public static final ValidatorBelowLivenessMinimum INSTANCE = new ValidatorBelowLivenessMinimum(
        6008, "Validator not eligible to be added to the pool. Must meet recent voting minimum"
    );
  }

  record VoteHistoryNotRecentEnough(int code, String msg) implements StewardError {

    public static final VoteHistoryNotRecentEnough INSTANCE = new VoteHistoryNotRecentEnough(
        6009, "Validator History vote data not recent enough to be used for scoring. Must be updated this epoch"
    );
  }

  record StakeHistoryNotRecentEnough(int code, String msg) implements StewardError {

    public static final StakeHistoryNotRecentEnough INSTANCE = new StakeHistoryNotRecentEnough(
        6010, "Validator History stake data not recent enough to be used for scoring. Must be updated this epoch"
    );
  }

  record ClusterHistoryNotRecentEnough(int code, String msg) implements StewardError {

    public static final ClusterHistoryNotRecentEnough INSTANCE = new ClusterHistoryNotRecentEnough(
        6011, "Cluster History data not recent enough to be used for scoring. Must be updated this epoch"
    );
  }

  record StateMachinePaused(int code, String msg) implements StewardError {

    public static final StateMachinePaused INSTANCE = new StateMachinePaused(
        6012, "Steward State Machine is paused. No state machine actions can be taken"
    );
  }

  record InvalidParameterValue(int code, String msg) implements StewardError {

    public static final InvalidParameterValue INSTANCE = new InvalidParameterValue(
        6013, "Config parameter is out of range or otherwise invalid"
    );
  }

  record InstantUnstakeNotReady(int code, String msg) implements StewardError {

    public static final InstantUnstakeNotReady INSTANCE = new InstantUnstakeNotReady(
        6014, "Instant unstake cannot be performed yet."
    );
  }

  record ValidatorIndexOutOfBounds(int code, String msg) implements StewardError {

    public static final ValidatorIndexOutOfBounds INSTANCE = new ValidatorIndexOutOfBounds(
        6015, "Validator index out of bounds of state machine"
    );
  }

  record ValidatorListTypeMismatch(int code, String msg) implements StewardError {

    public static final ValidatorListTypeMismatch INSTANCE = new ValidatorListTypeMismatch(
        6016, "ValidatorList account type mismatch"
    );
  }

  record ArithmeticError(int code, String msg) implements StewardError {

    public static final ArithmeticError INSTANCE = new ArithmeticError(
        6017, "An operation caused an overflow/underflow"
    );
  }

  record ValidatorNotRemovable(int code, String msg) implements StewardError {

    public static final ValidatorNotRemovable INSTANCE = new ValidatorNotRemovable(
        6018, "Validator not eligible for removal. Must be delinquent or have closed vote account"
    );
  }

  record ValidatorMarkedActive(int code, String msg) implements StewardError {

    public static final ValidatorMarkedActive INSTANCE = new ValidatorMarkedActive(
        6019, "Validator was marked active when it should be deactivating"
    );
  }

  record MaxValidatorsReached(int code, String msg) implements StewardError {

    public static final MaxValidatorsReached INSTANCE = new MaxValidatorsReached(
        6020, "Max validators reached"
    );
  }

  record EpochMaintenanceNotComplete(int code, String msg) implements StewardError {

    public static final EpochMaintenanceNotComplete INSTANCE = new EpochMaintenanceNotComplete(
        6021, "Epoch Maintenance must be called before continuing"
    );
  }

  record StakePoolNotUpdated(int code, String msg) implements StewardError {

    public static final StakePoolNotUpdated INSTANCE = new StakePoolNotUpdated(
        6022, "The stake pool must be updated before continuing"
    );
  }

  record EpochMaintenanceAlreadyComplete(int code, String msg) implements StewardError {

    public static final EpochMaintenanceAlreadyComplete INSTANCE = new EpochMaintenanceAlreadyComplete(
        6023, "Epoch Maintenance has already been completed"
    );
  }

  record ValidatorsNeedToBeRemoved(int code, String msg) implements StewardError {

    public static final ValidatorsNeedToBeRemoved INSTANCE = new ValidatorsNeedToBeRemoved(
        6024, "Validators are marked for immediate removal"
    );
  }

  record ValidatorNotMarkedForRemoval(int code, String msg) implements StewardError {

    public static final ValidatorNotMarkedForRemoval INSTANCE = new ValidatorNotMarkedForRemoval(
        6025, "Validator not marked for removal"
    );
  }

  record ValidatorsHaveNotBeenRemoved(int code, String msg) implements StewardError {

    public static final ValidatorsHaveNotBeenRemoved INSTANCE = new ValidatorsHaveNotBeenRemoved(
        6026, "Validators have not been removed"
    );
  }

  record ListStateMismatch(int code, String msg) implements StewardError {

    public static final ListStateMismatch INSTANCE = new ListStateMismatch(
        6027, "Validator List count does not match state machine"
    );
  }

  record VoteAccountDoesNotMatch(int code, String msg) implements StewardError {

    public static final VoteAccountDoesNotMatch INSTANCE = new VoteAccountDoesNotMatch(
        6028, "Vote account does not match"
    );
  }

  record ValidatorNeedsToBeMarkedForRemoval(int code, String msg) implements StewardError {

    public static final ValidatorNeedsToBeMarkedForRemoval INSTANCE = new ValidatorNeedsToBeMarkedForRemoval(
        6029, "Validator needs to be marked for removal"
    );
  }

  record InvalidStakeState(int code, String msg) implements StewardError {

    public static final InvalidStakeState INSTANCE = new InvalidStakeState(
        6030, "Invalid stake state"
    );
  }
}
