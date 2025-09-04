package software.sava.anchor.programs.switchboard.on_demand.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface SbOnDemandError extends ProgramError permits
    SbOnDemandError.GenericError,
    SbOnDemandError.InvalidQuote,
    SbOnDemandError.InsufficientQueue,
    SbOnDemandError.QueueFull,
    SbOnDemandError.InvalidEnclaveSigner,
    SbOnDemandError.InvalidSigner,
    SbOnDemandError.MrEnclaveAlreadyExists,
    SbOnDemandError.MrEnclaveAtCapacity,
    SbOnDemandError.MrEnclaveDoesntExist,
    SbOnDemandError.PermissionDenied,
    SbOnDemandError.InvalidQueue,
    SbOnDemandError.IncorrectMrEnclave,
    SbOnDemandError.InvalidAuthority,
    SbOnDemandError.InvalidMrEnclave,
    SbOnDemandError.InvalidTimestamp,
    SbOnDemandError.InvalidOracleIdx,
    SbOnDemandError.InvalidSecpSignature,
    SbOnDemandError.InvalidGuardianQueue,
    SbOnDemandError.InvalidIndex,
    SbOnDemandError.InvalidOracleQueue,
    SbOnDemandError.InvalidPermission,
    SbOnDemandError.InvalidePermissionedAccount,
    SbOnDemandError.InvalidEpochRotate,
    SbOnDemandError.InvalidEpochFinalize,
    SbOnDemandError.InvalidEscrow,
    SbOnDemandError.IllegalOracle,
    SbOnDemandError.IllegalExecuteAttempt,
    SbOnDemandError.IllegalFeedValue,
    SbOnDemandError.InvalidOracleFeedStats,
    SbOnDemandError.InvalidStateAuthority,
    SbOnDemandError.NotEnoughSamples,
    SbOnDemandError.OracleIsVerified,
    SbOnDemandError.QueueIsEmpty,
    SbOnDemandError.SecpRecoverFailure,
    SbOnDemandError.StaleSample,
    SbOnDemandError.SwitchboardRandomnessTooOld,
    SbOnDemandError.EpochIdMismatch,
    SbOnDemandError.GuardianAlreadyVoted,
    SbOnDemandError.RandomnessNotRequested,
    SbOnDemandError.InvalidSlotNumber,
    SbOnDemandError.RandomnessOracleKeyExpired,
    SbOnDemandError.InvalidAdvisory,
    SbOnDemandError.InvalidOracleStats,
    SbOnDemandError.InvalidStakeProgram,
    SbOnDemandError.InvalidStakePool,
    SbOnDemandError.InvalidDelegationPool,
    SbOnDemandError.UnparsableAccount,
    SbOnDemandError.InvalidInstruction,
    SbOnDemandError.OracleAlreadyVerified,
    SbOnDemandError.GuardianNotVerified,
    SbOnDemandError.InvalidConstraint,
    SbOnDemandError.InvalidDelegationGroup,
    SbOnDemandError.OracleKeyNotFound,
    SbOnDemandError.GuardianReregisterAttempt,
    SbOnDemandError.InvalidManySubmissionCount,
    SbOnDemandError.MissingSecpIx,
    SbOnDemandError.ChecksumMismatch,
    SbOnDemandError.InvalidSubmissionFeedsCount,
    SbOnDemandError.InvalidSecpSignatureOraclesCount,
    SbOnDemandError.InvalidEthAddress,
    SbOnDemandError.NoLutKeysAdded,
    SbOnDemandError.InvalidVaultOperatorDelegation,
    SbOnDemandError.InvalidVaultTokenAccount,
    SbOnDemandError.InvalidRemainingAccounts,
    SbOnDemandError.SubsidiesNotAllowed,
    SbOnDemandError.MissingVod,
    SbOnDemandError.InvalidVodEpoch,
    SbOnDemandError.InvalidOracleSubsidyWallet,
    SbOnDemandError.InvalidOperator,
    SbOnDemandError.Max128SampleValue,
    SbOnDemandError.RewardAlreadyPaid {

  static SbOnDemandError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> GenericError.INSTANCE;
      case 6001 -> InvalidQuote.INSTANCE;
      case 6002 -> InsufficientQueue.INSTANCE;
      case 6003 -> QueueFull.INSTANCE;
      case 6004 -> InvalidEnclaveSigner.INSTANCE;
      case 6005 -> InvalidSigner.INSTANCE;
      case 6006 -> MrEnclaveAlreadyExists.INSTANCE;
      case 6007 -> MrEnclaveAtCapacity.INSTANCE;
      case 6008 -> MrEnclaveDoesntExist.INSTANCE;
      case 6009 -> PermissionDenied.INSTANCE;
      case 6010 -> InvalidQueue.INSTANCE;
      case 6011 -> IncorrectMrEnclave.INSTANCE;
      case 6012 -> InvalidAuthority.INSTANCE;
      case 6013 -> InvalidMrEnclave.INSTANCE;
      case 6014 -> InvalidTimestamp.INSTANCE;
      case 6015 -> InvalidOracleIdx.INSTANCE;
      case 6016 -> InvalidSecpSignature.INSTANCE;
      case 6017 -> InvalidGuardianQueue.INSTANCE;
      case 6018 -> InvalidIndex.INSTANCE;
      case 6019 -> InvalidOracleQueue.INSTANCE;
      case 6020 -> InvalidPermission.INSTANCE;
      case 6021 -> InvalidePermissionedAccount.INSTANCE;
      case 6022 -> InvalidEpochRotate.INSTANCE;
      case 6023 -> InvalidEpochFinalize.INSTANCE;
      case 6024 -> InvalidEscrow.INSTANCE;
      case 6025 -> IllegalOracle.INSTANCE;
      case 6026 -> IllegalExecuteAttempt.INSTANCE;
      case 6027 -> IllegalFeedValue.INSTANCE;
      case 6028 -> InvalidOracleFeedStats.INSTANCE;
      case 6029 -> InvalidStateAuthority.INSTANCE;
      case 6030 -> NotEnoughSamples.INSTANCE;
      case 6031 -> OracleIsVerified.INSTANCE;
      case 6032 -> QueueIsEmpty.INSTANCE;
      case 6033 -> SecpRecoverFailure.INSTANCE;
      case 6034 -> StaleSample.INSTANCE;
      case 6035 -> SwitchboardRandomnessTooOld.INSTANCE;
      case 6036 -> EpochIdMismatch.INSTANCE;
      case 6037 -> GuardianAlreadyVoted.INSTANCE;
      case 6038 -> RandomnessNotRequested.INSTANCE;
      case 6039 -> InvalidSlotNumber.INSTANCE;
      case 6040 -> RandomnessOracleKeyExpired.INSTANCE;
      case 6041 -> InvalidAdvisory.INSTANCE;
      case 6042 -> InvalidOracleStats.INSTANCE;
      case 6043 -> InvalidStakeProgram.INSTANCE;
      case 6044 -> InvalidStakePool.INSTANCE;
      case 6045 -> InvalidDelegationPool.INSTANCE;
      case 6046 -> UnparsableAccount.INSTANCE;
      case 6047 -> InvalidInstruction.INSTANCE;
      case 6048 -> OracleAlreadyVerified.INSTANCE;
      case 6049 -> GuardianNotVerified.INSTANCE;
      case 6050 -> InvalidConstraint.INSTANCE;
      case 6051 -> InvalidDelegationGroup.INSTANCE;
      case 6052 -> OracleKeyNotFound.INSTANCE;
      case 6053 -> GuardianReregisterAttempt.INSTANCE;
      case 6054 -> InvalidManySubmissionCount.INSTANCE;
      case 6055 -> MissingSecpIx.INSTANCE;
      case 6056 -> ChecksumMismatch.INSTANCE;
      case 6057 -> InvalidSubmissionFeedsCount.INSTANCE;
      case 6058 -> InvalidSecpSignatureOraclesCount.INSTANCE;
      case 6059 -> InvalidEthAddress.INSTANCE;
      case 6060 -> NoLutKeysAdded.INSTANCE;
      case 6061 -> InvalidVaultOperatorDelegation.INSTANCE;
      case 6062 -> InvalidVaultTokenAccount.INSTANCE;
      case 6063 -> InvalidRemainingAccounts.INSTANCE;
      case 6064 -> SubsidiesNotAllowed.INSTANCE;
      case 6065 -> MissingVod.INSTANCE;
      case 6066 -> InvalidVodEpoch.INSTANCE;
      case 6067 -> InvalidOracleSubsidyWallet.INSTANCE;
      case 6068 -> InvalidOperator.INSTANCE;
      case 6069 -> Max128SampleValue.INSTANCE;
      case 6070 -> RewardAlreadyPaid.INSTANCE;
      default -> throw new IllegalStateException("Unexpected SbOnDemand error code: " + errorCode);
    };
  }

  record GenericError(int code, String msg) implements SbOnDemandError {

    public static final GenericError INSTANCE = new GenericError(
        6000, "null"
    );
  }

  record InvalidQuote(int code, String msg) implements SbOnDemandError {

    public static final InvalidQuote INSTANCE = new InvalidQuote(
        6001, "null"
    );
  }

  record InsufficientQueue(int code, String msg) implements SbOnDemandError {

    public static final InsufficientQueue INSTANCE = new InsufficientQueue(
        6002, "null"
    );
  }

  record QueueFull(int code, String msg) implements SbOnDemandError {

    public static final QueueFull INSTANCE = new QueueFull(
        6003, "null"
    );
  }

  record InvalidEnclaveSigner(int code, String msg) implements SbOnDemandError {

    public static final InvalidEnclaveSigner INSTANCE = new InvalidEnclaveSigner(
        6004, "null"
    );
  }

  record InvalidSigner(int code, String msg) implements SbOnDemandError {

    public static final InvalidSigner INSTANCE = new InvalidSigner(
        6005, "null"
    );
  }

  record MrEnclaveAlreadyExists(int code, String msg) implements SbOnDemandError {

    public static final MrEnclaveAlreadyExists INSTANCE = new MrEnclaveAlreadyExists(
        6006, "null"
    );
  }

  record MrEnclaveAtCapacity(int code, String msg) implements SbOnDemandError {

    public static final MrEnclaveAtCapacity INSTANCE = new MrEnclaveAtCapacity(
        6007, "null"
    );
  }

  record MrEnclaveDoesntExist(int code, String msg) implements SbOnDemandError {

    public static final MrEnclaveDoesntExist INSTANCE = new MrEnclaveDoesntExist(
        6008, "null"
    );
  }

  record PermissionDenied(int code, String msg) implements SbOnDemandError {

    public static final PermissionDenied INSTANCE = new PermissionDenied(
        6009, "null"
    );
  }

  record InvalidQueue(int code, String msg) implements SbOnDemandError {

    public static final InvalidQueue INSTANCE = new InvalidQueue(
        6010, "null"
    );
  }

  record IncorrectMrEnclave(int code, String msg) implements SbOnDemandError {

    public static final IncorrectMrEnclave INSTANCE = new IncorrectMrEnclave(
        6011, "null"
    );
  }

  record InvalidAuthority(int code, String msg) implements SbOnDemandError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        6012, "null"
    );
  }

  record InvalidMrEnclave(int code, String msg) implements SbOnDemandError {

    public static final InvalidMrEnclave INSTANCE = new InvalidMrEnclave(
        6013, "null"
    );
  }

  record InvalidTimestamp(int code, String msg) implements SbOnDemandError {

    public static final InvalidTimestamp INSTANCE = new InvalidTimestamp(
        6014, "null"
    );
  }

  record InvalidOracleIdx(int code, String msg) implements SbOnDemandError {

    public static final InvalidOracleIdx INSTANCE = new InvalidOracleIdx(
        6015, "null"
    );
  }

  record InvalidSecpSignature(int code, String msg) implements SbOnDemandError {

    public static final InvalidSecpSignature INSTANCE = new InvalidSecpSignature(
        6016, "null"
    );
  }

  record InvalidGuardianQueue(int code, String msg) implements SbOnDemandError {

    public static final InvalidGuardianQueue INSTANCE = new InvalidGuardianQueue(
        6017, "null"
    );
  }

  record InvalidIndex(int code, String msg) implements SbOnDemandError {

    public static final InvalidIndex INSTANCE = new InvalidIndex(
        6018, "null"
    );
  }

  record InvalidOracleQueue(int code, String msg) implements SbOnDemandError {

    public static final InvalidOracleQueue INSTANCE = new InvalidOracleQueue(
        6019, "null"
    );
  }

  record InvalidPermission(int code, String msg) implements SbOnDemandError {

    public static final InvalidPermission INSTANCE = new InvalidPermission(
        6020, "null"
    );
  }

  record InvalidePermissionedAccount(int code, String msg) implements SbOnDemandError {

    public static final InvalidePermissionedAccount INSTANCE = new InvalidePermissionedAccount(
        6021, "null"
    );
  }

  record InvalidEpochRotate(int code, String msg) implements SbOnDemandError {

    public static final InvalidEpochRotate INSTANCE = new InvalidEpochRotate(
        6022, "null"
    );
  }

  record InvalidEpochFinalize(int code, String msg) implements SbOnDemandError {

    public static final InvalidEpochFinalize INSTANCE = new InvalidEpochFinalize(
        6023, "null"
    );
  }

  record InvalidEscrow(int code, String msg) implements SbOnDemandError {

    public static final InvalidEscrow INSTANCE = new InvalidEscrow(
        6024, "null"
    );
  }

  record IllegalOracle(int code, String msg) implements SbOnDemandError {

    public static final IllegalOracle INSTANCE = new IllegalOracle(
        6025, "null"
    );
  }

  record IllegalExecuteAttempt(int code, String msg) implements SbOnDemandError {

    public static final IllegalExecuteAttempt INSTANCE = new IllegalExecuteAttempt(
        6026, "null"
    );
  }

  record IllegalFeedValue(int code, String msg) implements SbOnDemandError {

    public static final IllegalFeedValue INSTANCE = new IllegalFeedValue(
        6027, "null"
    );
  }

  record InvalidOracleFeedStats(int code, String msg) implements SbOnDemandError {

    public static final InvalidOracleFeedStats INSTANCE = new InvalidOracleFeedStats(
        6028, "null"
    );
  }

  record InvalidStateAuthority(int code, String msg) implements SbOnDemandError {

    public static final InvalidStateAuthority INSTANCE = new InvalidStateAuthority(
        6029, "null"
    );
  }

  record NotEnoughSamples(int code, String msg) implements SbOnDemandError {

    public static final NotEnoughSamples INSTANCE = new NotEnoughSamples(
        6030, "null"
    );
  }

  record OracleIsVerified(int code, String msg) implements SbOnDemandError {

    public static final OracleIsVerified INSTANCE = new OracleIsVerified(
        6031, "null"
    );
  }

  record QueueIsEmpty(int code, String msg) implements SbOnDemandError {

    public static final QueueIsEmpty INSTANCE = new QueueIsEmpty(
        6032, "null"
    );
  }

  record SecpRecoverFailure(int code, String msg) implements SbOnDemandError {

    public static final SecpRecoverFailure INSTANCE = new SecpRecoverFailure(
        6033, "null"
    );
  }

  record StaleSample(int code, String msg) implements SbOnDemandError {

    public static final StaleSample INSTANCE = new StaleSample(
        6034, "null"
    );
  }

  record SwitchboardRandomnessTooOld(int code, String msg) implements SbOnDemandError {

    public static final SwitchboardRandomnessTooOld INSTANCE = new SwitchboardRandomnessTooOld(
        6035, "null"
    );
  }

  record EpochIdMismatch(int code, String msg) implements SbOnDemandError {

    public static final EpochIdMismatch INSTANCE = new EpochIdMismatch(
        6036, "null"
    );
  }

  record GuardianAlreadyVoted(int code, String msg) implements SbOnDemandError {

    public static final GuardianAlreadyVoted INSTANCE = new GuardianAlreadyVoted(
        6037, "null"
    );
  }

  record RandomnessNotRequested(int code, String msg) implements SbOnDemandError {

    public static final RandomnessNotRequested INSTANCE = new RandomnessNotRequested(
        6038, "null"
    );
  }

  record InvalidSlotNumber(int code, String msg) implements SbOnDemandError {

    public static final InvalidSlotNumber INSTANCE = new InvalidSlotNumber(
        6039, "null"
    );
  }

  record RandomnessOracleKeyExpired(int code, String msg) implements SbOnDemandError {

    public static final RandomnessOracleKeyExpired INSTANCE = new RandomnessOracleKeyExpired(
        6040, "null"
    );
  }

  record InvalidAdvisory(int code, String msg) implements SbOnDemandError {

    public static final InvalidAdvisory INSTANCE = new InvalidAdvisory(
        6041, "null"
    );
  }

  record InvalidOracleStats(int code, String msg) implements SbOnDemandError {

    public static final InvalidOracleStats INSTANCE = new InvalidOracleStats(
        6042, "null"
    );
  }

  record InvalidStakeProgram(int code, String msg) implements SbOnDemandError {

    public static final InvalidStakeProgram INSTANCE = new InvalidStakeProgram(
        6043, "null"
    );
  }

  record InvalidStakePool(int code, String msg) implements SbOnDemandError {

    public static final InvalidStakePool INSTANCE = new InvalidStakePool(
        6044, "null"
    );
  }

  record InvalidDelegationPool(int code, String msg) implements SbOnDemandError {

    public static final InvalidDelegationPool INSTANCE = new InvalidDelegationPool(
        6045, "null"
    );
  }

  record UnparsableAccount(int code, String msg) implements SbOnDemandError {

    public static final UnparsableAccount INSTANCE = new UnparsableAccount(
        6046, "null"
    );
  }

  record InvalidInstruction(int code, String msg) implements SbOnDemandError {

    public static final InvalidInstruction INSTANCE = new InvalidInstruction(
        6047, "null"
    );
  }

  record OracleAlreadyVerified(int code, String msg) implements SbOnDemandError {

    public static final OracleAlreadyVerified INSTANCE = new OracleAlreadyVerified(
        6048, "null"
    );
  }

  record GuardianNotVerified(int code, String msg) implements SbOnDemandError {

    public static final GuardianNotVerified INSTANCE = new GuardianNotVerified(
        6049, "null"
    );
  }

  record InvalidConstraint(int code, String msg) implements SbOnDemandError {

    public static final InvalidConstraint INSTANCE = new InvalidConstraint(
        6050, "null"
    );
  }

  record InvalidDelegationGroup(int code, String msg) implements SbOnDemandError {

    public static final InvalidDelegationGroup INSTANCE = new InvalidDelegationGroup(
        6051, "null"
    );
  }

  record OracleKeyNotFound(int code, String msg) implements SbOnDemandError {

    public static final OracleKeyNotFound INSTANCE = new OracleKeyNotFound(
        6052, "null"
    );
  }

  record GuardianReregisterAttempt(int code, String msg) implements SbOnDemandError {

    public static final GuardianReregisterAttempt INSTANCE = new GuardianReregisterAttempt(
        6053, "null"
    );
  }

  record InvalidManySubmissionCount(int code, String msg) implements SbOnDemandError {

    public static final InvalidManySubmissionCount INSTANCE = new InvalidManySubmissionCount(
        6054, "null"
    );
  }

  record MissingSecpIx(int code, String msg) implements SbOnDemandError {

    public static final MissingSecpIx INSTANCE = new MissingSecpIx(
        6055, "null"
    );
  }

  record ChecksumMismatch(int code, String msg) implements SbOnDemandError {

    public static final ChecksumMismatch INSTANCE = new ChecksumMismatch(
        6056, "null"
    );
  }

  record InvalidSubmissionFeedsCount(int code, String msg) implements SbOnDemandError {

    public static final InvalidSubmissionFeedsCount INSTANCE = new InvalidSubmissionFeedsCount(
        6057, "null"
    );
  }

  record InvalidSecpSignatureOraclesCount(int code, String msg) implements SbOnDemandError {

    public static final InvalidSecpSignatureOraclesCount INSTANCE = new InvalidSecpSignatureOraclesCount(
        6058, "null"
    );
  }

  record InvalidEthAddress(int code, String msg) implements SbOnDemandError {

    public static final InvalidEthAddress INSTANCE = new InvalidEthAddress(
        6059, "null"
    );
  }

  record NoLutKeysAdded(int code, String msg) implements SbOnDemandError {

    public static final NoLutKeysAdded INSTANCE = new NoLutKeysAdded(
        6060, "null"
    );
  }

  record InvalidVaultOperatorDelegation(int code, String msg) implements SbOnDemandError {

    public static final InvalidVaultOperatorDelegation INSTANCE = new InvalidVaultOperatorDelegation(
        6061, "null"
    );
  }

  record InvalidVaultTokenAccount(int code, String msg) implements SbOnDemandError {

    public static final InvalidVaultTokenAccount INSTANCE = new InvalidVaultTokenAccount(
        6062, "null"
    );
  }

  record InvalidRemainingAccounts(int code, String msg) implements SbOnDemandError {

    public static final InvalidRemainingAccounts INSTANCE = new InvalidRemainingAccounts(
        6063, "null"
    );
  }

  record SubsidiesNotAllowed(int code, String msg) implements SbOnDemandError {

    public static final SubsidiesNotAllowed INSTANCE = new SubsidiesNotAllowed(
        6064, "null"
    );
  }

  record MissingVod(int code, String msg) implements SbOnDemandError {

    public static final MissingVod INSTANCE = new MissingVod(
        6065, "null"
    );
  }

  record InvalidVodEpoch(int code, String msg) implements SbOnDemandError {

    public static final InvalidVodEpoch INSTANCE = new InvalidVodEpoch(
        6066, "null"
    );
  }

  record InvalidOracleSubsidyWallet(int code, String msg) implements SbOnDemandError {

    public static final InvalidOracleSubsidyWallet INSTANCE = new InvalidOracleSubsidyWallet(
        6067, "null"
    );
  }

  record InvalidOperator(int code, String msg) implements SbOnDemandError {

    public static final InvalidOperator INSTANCE = new InvalidOperator(
        6068, "null"
    );
  }

  record Max128SampleValue(int code, String msg) implements SbOnDemandError {

    public static final Max128SampleValue INSTANCE = new Max128SampleValue(
        6069, "null"
    );
  }

  record RewardAlreadyPaid(int code, String msg) implements SbOnDemandError {

    public static final RewardAlreadyPaid INSTANCE = new RewardAlreadyPaid(
        6070, "null"
    );
  }
}
