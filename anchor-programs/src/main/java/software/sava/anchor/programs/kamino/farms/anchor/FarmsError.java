package software.sava.anchor.programs.kamino.farms.anchor;

import software.sava.anchor.ProgramError;

public sealed interface FarmsError extends ProgramError permits
    FarmsError.StakeZero,
    FarmsError.UnstakeZero,
    FarmsError.NothingToUnstake,
    FarmsError.NoRewardToHarvest,
    FarmsError.NoRewardInList,
    FarmsError.RewardAlreadyInitialized,
    FarmsError.MaxRewardNumberReached,
    FarmsError.RewardDoesNotExist,
    FarmsError.WrongRewardVaultAccount,
    FarmsError.RewardVaultMismatch,
    FarmsError.RewardVaultAuthorityMismatch,
    FarmsError.NothingStaked,
    FarmsError.IntegerOverflow,
    FarmsError.ConversionFailure,
    FarmsError.UnexpectedAccount,
    FarmsError.OperationForbidden,
    FarmsError.MathOverflow,
    FarmsError.MinClaimDurationNotReached,
    FarmsError.RewardsVaultHasDelegate,
    FarmsError.RewardsVaultHasCloseAuthority,
    FarmsError.FarmVaultHasDelegate,
    FarmsError.FarmVaultHasCloseAuthority,
    FarmsError.RewardsTreasuryVaultHasDelegate,
    FarmsError.RewardsTreasuryVaultHasCloseAuthority,
    FarmsError.UserAtaRewardVaultMintMissmatch,
    FarmsError.UserAtaFarmTokenMintMissmatch,
    FarmsError.TokenFarmTokenMintMissmatch,
    FarmsError.RewardAtaRewardMintMissmatch,
    FarmsError.RewardAtaOwnerNotPayer,
    FarmsError.InvalidGlobalConfigMode,
    FarmsError.RewardIndexOutOfRange,
    FarmsError.NothingToWithdraw,
    FarmsError.UserDelegatedFarmNonDelegatedMissmatch,
    FarmsError.AuthorityFarmDelegateMissmatch,
    FarmsError.FarmNotDelegated,
    FarmsError.FarmDelegated,
    FarmsError.UnstakeNotElapsed,
    FarmsError.PendingWithdrawalNotWithdrawnYet,
    FarmsError.DepositZero,
    FarmsError.InvalidConfigValue,
    FarmsError.InvalidPenaltyPercentage,
    FarmsError.EarlyWithdrawalNotAllowed,
    FarmsError.InvalidLockingTimestamps,
    FarmsError.InvalidRpsCurvePoint,
    FarmsError.InvalidTimestamp,
    FarmsError.DepositCapReached,
    FarmsError.MissingScopePrices,
    FarmsError.ScopeOraclePriceTooOld,
    FarmsError.InvalidOracleConfig,
    FarmsError.CouldNotDeserializeScope,
    FarmsError.RewardAtaOwnerNotAdmin,
    FarmsError.WithdrawRewardZeroAvailable,
    FarmsError.RewardScheduleCurveSet,
    FarmsError.UnsupportedTokenExtension,
    FarmsError.InvalidFarmConfigUpdateAuthority,
    FarmsError.InvalidTransferOwnershipOldOwner,
    FarmsError.InvalidTransferOwnershipFarmState,
    FarmsError.InvalidTransferOwnershipUserStateOwnerDelegatee,
    FarmsError.InvalidTransferOwnershipFarmStateLockingMode,
    FarmsError.InvalidTransferOwnershipFarmStateWithdrawCooldownPeriod,
    FarmsError.InvalidTransferOwnershipStakeAmount {

  static FarmsError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> StakeZero.INSTANCE;
      case 6001 -> UnstakeZero.INSTANCE;
      case 6002 -> NothingToUnstake.INSTANCE;
      case 6003 -> NoRewardToHarvest.INSTANCE;
      case 6004 -> NoRewardInList.INSTANCE;
      case 6005 -> RewardAlreadyInitialized.INSTANCE;
      case 6006 -> MaxRewardNumberReached.INSTANCE;
      case 6007 -> RewardDoesNotExist.INSTANCE;
      case 6008 -> WrongRewardVaultAccount.INSTANCE;
      case 6009 -> RewardVaultMismatch.INSTANCE;
      case 6010 -> RewardVaultAuthorityMismatch.INSTANCE;
      case 6011 -> NothingStaked.INSTANCE;
      case 6012 -> IntegerOverflow.INSTANCE;
      case 6013 -> ConversionFailure.INSTANCE;
      case 6014 -> UnexpectedAccount.INSTANCE;
      case 6015 -> OperationForbidden.INSTANCE;
      case 6016 -> MathOverflow.INSTANCE;
      case 6017 -> MinClaimDurationNotReached.INSTANCE;
      case 6018 -> RewardsVaultHasDelegate.INSTANCE;
      case 6019 -> RewardsVaultHasCloseAuthority.INSTANCE;
      case 6020 -> FarmVaultHasDelegate.INSTANCE;
      case 6021 -> FarmVaultHasCloseAuthority.INSTANCE;
      case 6022 -> RewardsTreasuryVaultHasDelegate.INSTANCE;
      case 6023 -> RewardsTreasuryVaultHasCloseAuthority.INSTANCE;
      case 6024 -> UserAtaRewardVaultMintMissmatch.INSTANCE;
      case 6025 -> UserAtaFarmTokenMintMissmatch.INSTANCE;
      case 6026 -> TokenFarmTokenMintMissmatch.INSTANCE;
      case 6027 -> RewardAtaRewardMintMissmatch.INSTANCE;
      case 6028 -> RewardAtaOwnerNotPayer.INSTANCE;
      case 6029 -> InvalidGlobalConfigMode.INSTANCE;
      case 6030 -> RewardIndexOutOfRange.INSTANCE;
      case 6031 -> NothingToWithdraw.INSTANCE;
      case 6032 -> UserDelegatedFarmNonDelegatedMissmatch.INSTANCE;
      case 6033 -> AuthorityFarmDelegateMissmatch.INSTANCE;
      case 6034 -> FarmNotDelegated.INSTANCE;
      case 6035 -> FarmDelegated.INSTANCE;
      case 6036 -> UnstakeNotElapsed.INSTANCE;
      case 6037 -> PendingWithdrawalNotWithdrawnYet.INSTANCE;
      case 6038 -> DepositZero.INSTANCE;
      case 6039 -> InvalidConfigValue.INSTANCE;
      case 6040 -> InvalidPenaltyPercentage.INSTANCE;
      case 6041 -> EarlyWithdrawalNotAllowed.INSTANCE;
      case 6042 -> InvalidLockingTimestamps.INSTANCE;
      case 6043 -> InvalidRpsCurvePoint.INSTANCE;
      case 6044 -> InvalidTimestamp.INSTANCE;
      case 6045 -> DepositCapReached.INSTANCE;
      case 6046 -> MissingScopePrices.INSTANCE;
      case 6047 -> ScopeOraclePriceTooOld.INSTANCE;
      case 6048 -> InvalidOracleConfig.INSTANCE;
      case 6049 -> CouldNotDeserializeScope.INSTANCE;
      case 6050 -> RewardAtaOwnerNotAdmin.INSTANCE;
      case 6051 -> WithdrawRewardZeroAvailable.INSTANCE;
      case 6052 -> RewardScheduleCurveSet.INSTANCE;
      case 6053 -> UnsupportedTokenExtension.INSTANCE;
      case 6054 -> InvalidFarmConfigUpdateAuthority.INSTANCE;
      case 6055 -> InvalidTransferOwnershipOldOwner.INSTANCE;
      case 6056 -> InvalidTransferOwnershipFarmState.INSTANCE;
      case 6057 -> InvalidTransferOwnershipUserStateOwnerDelegatee.INSTANCE;
      case 6058 -> InvalidTransferOwnershipFarmStateLockingMode.INSTANCE;
      case 6059 -> InvalidTransferOwnershipFarmStateWithdrawCooldownPeriod.INSTANCE;
      case 6060 -> InvalidTransferOwnershipStakeAmount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Farms error code: " + errorCode);
    };
  }

  record StakeZero(int code, String msg) implements FarmsError {

    public static final StakeZero INSTANCE = new StakeZero(
        6000, "Cannot stake 0 amount"
    );
  }

  record UnstakeZero(int code, String msg) implements FarmsError {

    public static final UnstakeZero INSTANCE = new UnstakeZero(
        6001, "Cannot unstake 0 amount"
    );
  }

  record NothingToUnstake(int code, String msg) implements FarmsError {

    public static final NothingToUnstake INSTANCE = new NothingToUnstake(
        6002, "Nothing to unstake"
    );
  }

  record NoRewardToHarvest(int code, String msg) implements FarmsError {

    public static final NoRewardToHarvest INSTANCE = new NoRewardToHarvest(
        6003, "No reward to harvest"
    );
  }

  record NoRewardInList(int code, String msg) implements FarmsError {

    public static final NoRewardInList INSTANCE = new NoRewardInList(
        6004, "Reward not present in reward list"
    );
  }

  record RewardAlreadyInitialized(int code, String msg) implements FarmsError {

    public static final RewardAlreadyInitialized INSTANCE = new RewardAlreadyInitialized(
        6005, "Reward already initialized"
    );
  }

  record MaxRewardNumberReached(int code, String msg) implements FarmsError {

    public static final MaxRewardNumberReached INSTANCE = new MaxRewardNumberReached(
        6006, "Max number of reward tokens reached"
    );
  }

  record RewardDoesNotExist(int code, String msg) implements FarmsError {

    public static final RewardDoesNotExist INSTANCE = new RewardDoesNotExist(
        6007, "Reward does not exist"
    );
  }

  record WrongRewardVaultAccount(int code, String msg) implements FarmsError {

    public static final WrongRewardVaultAccount INSTANCE = new WrongRewardVaultAccount(
        6008, "Reward vault exists but the account is wrong"
    );
  }

  record RewardVaultMismatch(int code, String msg) implements FarmsError {

    public static final RewardVaultMismatch INSTANCE = new RewardVaultMismatch(
        6009, "Reward vault pubkey does not match staking pool vault"
    );
  }

  record RewardVaultAuthorityMismatch(int code, String msg) implements FarmsError {

    public static final RewardVaultAuthorityMismatch INSTANCE = new RewardVaultAuthorityMismatch(
        6010, "Reward vault authority pubkey does not match staking pool vault"
    );
  }

  record NothingStaked(int code, String msg) implements FarmsError {

    public static final NothingStaked INSTANCE = new NothingStaked(
        6011, "Nothing staked, cannot collect any rewards"
    );
  }

  record IntegerOverflow(int code, String msg) implements FarmsError {

    public static final IntegerOverflow INSTANCE = new IntegerOverflow(
        6012, "Integer overflow"
    );
  }

  record ConversionFailure(int code, String msg) implements FarmsError {

    public static final ConversionFailure INSTANCE = new ConversionFailure(
        6013, "Conversion failure"
    );
  }

  record UnexpectedAccount(int code, String msg) implements FarmsError {

    public static final UnexpectedAccount INSTANCE = new UnexpectedAccount(
        6014, "Unexpected account in instruction"
    );
  }

  record OperationForbidden(int code, String msg) implements FarmsError {

    public static final OperationForbidden INSTANCE = new OperationForbidden(
        6015, "Operation forbidden"
    );
  }

  record MathOverflow(int code, String msg) implements FarmsError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6016, "Mathematical operation with overflow"
    );
  }

  record MinClaimDurationNotReached(int code, String msg) implements FarmsError {

    public static final MinClaimDurationNotReached INSTANCE = new MinClaimDurationNotReached(
        6017, "Minimum claim duration has not been reached"
    );
  }

  record RewardsVaultHasDelegate(int code, String msg) implements FarmsError {

    public static final RewardsVaultHasDelegate INSTANCE = new RewardsVaultHasDelegate(
        6018, "Reward vault has a delegate"
    );
  }

  record RewardsVaultHasCloseAuthority(int code, String msg) implements FarmsError {

    public static final RewardsVaultHasCloseAuthority INSTANCE = new RewardsVaultHasCloseAuthority(
        6019, "Reward vault has a close authority"
    );
  }

  record FarmVaultHasDelegate(int code, String msg) implements FarmsError {

    public static final FarmVaultHasDelegate INSTANCE = new FarmVaultHasDelegate(
        6020, "Farm vault has a delegate"
    );
  }

  record FarmVaultHasCloseAuthority(int code, String msg) implements FarmsError {

    public static final FarmVaultHasCloseAuthority INSTANCE = new FarmVaultHasCloseAuthority(
        6021, "Farm vault has a close authority"
    );
  }

  record RewardsTreasuryVaultHasDelegate(int code, String msg) implements FarmsError {

    public static final RewardsTreasuryVaultHasDelegate INSTANCE = new RewardsTreasuryVaultHasDelegate(
        6022, "Reward vault has a delegate"
    );
  }

  record RewardsTreasuryVaultHasCloseAuthority(int code, String msg) implements FarmsError {

    public static final RewardsTreasuryVaultHasCloseAuthority INSTANCE = new RewardsTreasuryVaultHasCloseAuthority(
        6023, "Reward vault has a close authority"
    );
  }

  record UserAtaRewardVaultMintMissmatch(int code, String msg) implements FarmsError {

    public static final UserAtaRewardVaultMintMissmatch INSTANCE = new UserAtaRewardVaultMintMissmatch(
        6024, "User ata and reward vault have different mints"
    );
  }

  record UserAtaFarmTokenMintMissmatch(int code, String msg) implements FarmsError {

    public static final UserAtaFarmTokenMintMissmatch INSTANCE = new UserAtaFarmTokenMintMissmatch(
        6025, "User ata and farm token have different mints"
    );
  }

  record TokenFarmTokenMintMissmatch(int code, String msg) implements FarmsError {

    public static final TokenFarmTokenMintMissmatch INSTANCE = new TokenFarmTokenMintMissmatch(
        6026, "Token mint and farm token have different mints"
    );
  }

  record RewardAtaRewardMintMissmatch(int code, String msg) implements FarmsError {

    public static final RewardAtaRewardMintMissmatch INSTANCE = new RewardAtaRewardMintMissmatch(
        6027, "Reward ata mint is different than reward mint"
    );
  }

  record RewardAtaOwnerNotPayer(int code, String msg) implements FarmsError {

    public static final RewardAtaOwnerNotPayer INSTANCE = new RewardAtaOwnerNotPayer(
        6028, "Reward ata owner is different than payer"
    );
  }

  record InvalidGlobalConfigMode(int code, String msg) implements FarmsError {

    public static final InvalidGlobalConfigMode INSTANCE = new InvalidGlobalConfigMode(
        6029, "Mode to update global_config is invalid"
    );
  }

  record RewardIndexOutOfRange(int code, String msg) implements FarmsError {

    public static final RewardIndexOutOfRange INSTANCE = new RewardIndexOutOfRange(
        6030, "Reward Index is higher than number of rewards"
    );
  }

  record NothingToWithdraw(int code, String msg) implements FarmsError {

    public static final NothingToWithdraw INSTANCE = new NothingToWithdraw(
        6031, "No tokens available to withdraw"
    );
  }

  record UserDelegatedFarmNonDelegatedMissmatch(int code, String msg) implements FarmsError {

    public static final UserDelegatedFarmNonDelegatedMissmatch INSTANCE = new UserDelegatedFarmNonDelegatedMissmatch(
        6032, "user, user_ref, authority and payer must match for non-delegated farm"
    );
  }

  record AuthorityFarmDelegateMissmatch(int code, String msg) implements FarmsError {

    public static final AuthorityFarmDelegateMissmatch INSTANCE = new AuthorityFarmDelegateMissmatch(
        6033, "Authority must match farm delegate authority"
    );
  }

  record FarmNotDelegated(int code, String msg) implements FarmsError {

    public static final FarmNotDelegated INSTANCE = new FarmNotDelegated(
        6034, "Farm not delegated, can not set stake"
    );
  }

  record FarmDelegated(int code, String msg) implements FarmsError {

    public static final FarmDelegated INSTANCE = new FarmDelegated(
        6035, "Operation not allowed for delegated farm"
    );
  }

  record UnstakeNotElapsed(int code, String msg) implements FarmsError {

    public static final UnstakeNotElapsed INSTANCE = new UnstakeNotElapsed(
        6036, "Unstake lockup period is not elapsed. Deposit is locked until end of unstake period"
    );
  }

  record PendingWithdrawalNotWithdrawnYet(int code, String msg) implements FarmsError {

    public static final PendingWithdrawalNotWithdrawnYet INSTANCE = new PendingWithdrawalNotWithdrawnYet(
        6037, "Pending withdrawal already exist and not withdrawn yet"
    );
  }

  record DepositZero(int code, String msg) implements FarmsError {

    public static final DepositZero INSTANCE = new DepositZero(
        6038, "Cannot deposit zero amount directly to farm vault"
    );
  }

  record InvalidConfigValue(int code, String msg) implements FarmsError {

    public static final InvalidConfigValue INSTANCE = new InvalidConfigValue(
        6039, "Invalid config value"
    );
  }

  record InvalidPenaltyPercentage(int code, String msg) implements FarmsError {

    public static final InvalidPenaltyPercentage INSTANCE = new InvalidPenaltyPercentage(
        6040, "Invalid penalty percentage"
    );
  }

  record EarlyWithdrawalNotAllowed(int code, String msg) implements FarmsError {

    public static final EarlyWithdrawalNotAllowed INSTANCE = new EarlyWithdrawalNotAllowed(
        6041, "Early withdrawal not allowed"
    );
  }

  record InvalidLockingTimestamps(int code, String msg) implements FarmsError {

    public static final InvalidLockingTimestamps INSTANCE = new InvalidLockingTimestamps(
        6042, "Invalid locking timestamps"
    );
  }

  record InvalidRpsCurvePoint(int code, String msg) implements FarmsError {

    public static final InvalidRpsCurvePoint INSTANCE = new InvalidRpsCurvePoint(
        6043, "Invalid reward rate curve point"
    );
  }

  record InvalidTimestamp(int code, String msg) implements FarmsError {

    public static final InvalidTimestamp INSTANCE = new InvalidTimestamp(
        6044, "Invalid timestamp"
    );
  }

  record DepositCapReached(int code, String msg) implements FarmsError {

    public static final DepositCapReached INSTANCE = new DepositCapReached(
        6045, "Deposit cap reached"
    );
  }

  record MissingScopePrices(int code, String msg) implements FarmsError {

    public static final MissingScopePrices INSTANCE = new MissingScopePrices(
        6046, "Missing Scope Prices"
    );
  }

  record ScopeOraclePriceTooOld(int code, String msg) implements FarmsError {

    public static final ScopeOraclePriceTooOld INSTANCE = new ScopeOraclePriceTooOld(
        6047, "Scope Oracle Price Too Old"
    );
  }

  record InvalidOracleConfig(int code, String msg) implements FarmsError {

    public static final InvalidOracleConfig INSTANCE = new InvalidOracleConfig(
        6048, "Invalid Oracle Config"
    );
  }

  record CouldNotDeserializeScope(int code, String msg) implements FarmsError {

    public static final CouldNotDeserializeScope INSTANCE = new CouldNotDeserializeScope(
        6049, "Could not deserialize scope"
    );
  }

  record RewardAtaOwnerNotAdmin(int code, String msg) implements FarmsError {

    public static final RewardAtaOwnerNotAdmin INSTANCE = new RewardAtaOwnerNotAdmin(
        6050, "Reward ata owner is different than farm admin"
    );
  }

  record WithdrawRewardZeroAvailable(int code, String msg) implements FarmsError {

    public static final WithdrawRewardZeroAvailable INSTANCE = new WithdrawRewardZeroAvailable(
        6051, "Cannot withdraw reward as available amount is zero"
    );
  }

  record RewardScheduleCurveSet(int code, String msg) implements FarmsError {

    public static final RewardScheduleCurveSet INSTANCE = new RewardScheduleCurveSet(
        6052, "Cannot withdraw reward as reward schedule is set"
    );
  }

  record UnsupportedTokenExtension(int code, String msg) implements FarmsError {

    public static final UnsupportedTokenExtension INSTANCE = new UnsupportedTokenExtension(
        6053, "Cannot initialize farm while having a mint with token22 and requested extensions"
    );
  }

  record InvalidFarmConfigUpdateAuthority(int code, String msg) implements FarmsError {

    public static final InvalidFarmConfigUpdateAuthority INSTANCE = new InvalidFarmConfigUpdateAuthority(
        6054, "Invalid authority for updating farm config"
    );
  }

  record InvalidTransferOwnershipOldOwner(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipOldOwner INSTANCE = new InvalidTransferOwnershipOldOwner(
        6055, "Invalid authority for transfer ownersip new user state initialization"
    );
  }

  record InvalidTransferOwnershipFarmState(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipFarmState INSTANCE = new InvalidTransferOwnershipFarmState(
        6056, "Invalid farm state for transfer ownership new user state initialization"
    );
  }

  record InvalidTransferOwnershipUserStateOwnerDelegatee(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipUserStateOwnerDelegatee INSTANCE = new InvalidTransferOwnershipUserStateOwnerDelegatee(
        6057, "Invalid user state for transfer ownership, owner must match delegatee"
    );
  }

  record InvalidTransferOwnershipFarmStateLockingMode(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipFarmStateLockingMode INSTANCE = new InvalidTransferOwnershipFarmStateLockingMode(
        6058, "Invalid farm state locking mode for transfer ownership, must be 0"
    );
  }

  record InvalidTransferOwnershipFarmStateWithdrawCooldownPeriod(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipFarmStateWithdrawCooldownPeriod INSTANCE = new InvalidTransferOwnershipFarmStateWithdrawCooldownPeriod(
        6059, "Invalid farm state withdrawal cooldown period for transfer ownership, must be 0"
    );
  }

  record InvalidTransferOwnershipStakeAmount(int code, String msg) implements FarmsError {

    public static final InvalidTransferOwnershipStakeAmount INSTANCE = new InvalidTransferOwnershipStakeAmount(
        6060, "Invalid transfer ownership stake amount, must be equal to unstaked deposits"
    );
  }
}
