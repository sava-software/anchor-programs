package software.sava.anchor.programs.marinade.anchor;

import software.sava.anchor.ProgramError;

public sealed interface MarinadeFinanceError extends ProgramError permits
    MarinadeFinanceError.WrongReserveOwner,
    MarinadeFinanceError.NonEmptyReserveData,
    MarinadeFinanceError.InvalidInitialReserveLamports,
    MarinadeFinanceError.ZeroValidatorChunkSize,
    MarinadeFinanceError.TooBigValidatorChunkSize,
    MarinadeFinanceError.ZeroCreditChunkSize,
    MarinadeFinanceError.TooBigCreditChunkSize,
    MarinadeFinanceError.TooLowCreditFee,
    MarinadeFinanceError.InvalidMintAuthority,
    MarinadeFinanceError.MintHasInitialSupply,
    MarinadeFinanceError.InvalidOwnerFeeState,
    MarinadeFinanceError.InvalidProgramId,
    MarinadeFinanceError.UnexpectedAccount,
    MarinadeFinanceError.CalculationFailure,
    MarinadeFinanceError.StakeAccountWithLockup,
    MarinadeFinanceError.MinStakeIsTooLow,
    MarinadeFinanceError.LpMaxFeeIsTooHigh,
    MarinadeFinanceError.BasisPointsOverflow,
    MarinadeFinanceError.LpFeesAreWrongWayRound,
    MarinadeFinanceError.LiquidityTargetTooLow,
    MarinadeFinanceError.TicketNotDue,
    MarinadeFinanceError.TicketNotReady,
    MarinadeFinanceError.WrongBeneficiary,
    MarinadeFinanceError.StakeAccountNotUpdatedYet,
    MarinadeFinanceError.StakeNotDelegated,
    MarinadeFinanceError.StakeAccountIsEmergencyUnstaking,
    MarinadeFinanceError.InsufficientLiquidity,
    MarinadeFinanceError.NotUsed6027,
    MarinadeFinanceError.InvalidAdminAuthority,
    MarinadeFinanceError.InvalidValidatorManager,
    MarinadeFinanceError.InvalidStakeListDiscriminator,
    MarinadeFinanceError.InvalidValidatorListDiscriminator,
    MarinadeFinanceError.TreasuryCutIsTooHigh,
    MarinadeFinanceError.RewardsFeeIsTooHigh,
    MarinadeFinanceError.StakingIsCapped,
    MarinadeFinanceError.LiquidityIsCapped,
    MarinadeFinanceError.UpdateWindowIsTooLow,
    MarinadeFinanceError.MinWithdrawIsTooHigh,
    MarinadeFinanceError.WithdrawAmountIsTooLow,
    MarinadeFinanceError.DepositAmountIsTooLow,
    MarinadeFinanceError.NotEnoughUserFunds,
    MarinadeFinanceError.WrongTokenOwnerOrDelegate,
    MarinadeFinanceError.TooEarlyForStakeDelta,
    MarinadeFinanceError.RequiredDelegatedStake,
    MarinadeFinanceError.RequiredActiveStake,
    MarinadeFinanceError.RequiredDeactivatingStake,
    MarinadeFinanceError.DepositingNotActivatedStake,
    MarinadeFinanceError.TooLowDelegationInDepositingStake,
    MarinadeFinanceError.WrongStakeBalance,
    MarinadeFinanceError.WrongValidatorAccountOrIndex,
    MarinadeFinanceError.WrongStakeAccountOrIndex,
    MarinadeFinanceError.UnstakingOnPositiveDelta,
    MarinadeFinanceError.StakingOnNegativeDelta,
    MarinadeFinanceError.MovingStakeIsCapped,
    MarinadeFinanceError.StakeMustBeUninitialized,
    MarinadeFinanceError.DestinationStakeMustBeDelegated,
    MarinadeFinanceError.DestinationStakeMustNotBeDeactivating,
    MarinadeFinanceError.DestinationStakeMustBeUpdated,
    MarinadeFinanceError.InvalidDestinationStakeDelegation,
    MarinadeFinanceError.SourceStakeMustBeDelegated,
    MarinadeFinanceError.SourceStakeMustNotBeDeactivating,
    MarinadeFinanceError.SourceStakeMustBeUpdated,
    MarinadeFinanceError.InvalidSourceStakeDelegation,
    MarinadeFinanceError.InvalidDelayedUnstakeTicket,
    MarinadeFinanceError.ReusingDelayedUnstakeTicket,
    MarinadeFinanceError.EmergencyUnstakingFromNonZeroScoredValidator,
    MarinadeFinanceError.WrongValidatorDuplicationFlag,
    MarinadeFinanceError.RedepositingMarinadeStake,
    MarinadeFinanceError.RemovingValidatorWithBalance,
    MarinadeFinanceError.RedelegateOverTarget,
    MarinadeFinanceError.SourceAndDestValidatorsAreTheSame,
    MarinadeFinanceError.UnregisteredMsolMinted,
    MarinadeFinanceError.UnregisteredLPMinted,
    MarinadeFinanceError.ListIndexOutOfBounds,
    MarinadeFinanceError.ListOverflow,
    MarinadeFinanceError.AlreadyPaused,
    MarinadeFinanceError.NotPaused,
    MarinadeFinanceError.ProgramIsPaused,
    MarinadeFinanceError.InvalidPauseAuthority,
    MarinadeFinanceError.SelectedStakeAccountHasNotEnoughFunds,
    MarinadeFinanceError.BasisPointCentsOverflow,
    MarinadeFinanceError.WithdrawStakeAccountIsNotEnabled,
    MarinadeFinanceError.WithdrawStakeAccountFeeIsTooHigh,
    MarinadeFinanceError.DelayedUnstakeFeeIsTooHigh,
    MarinadeFinanceError.WithdrawStakeLamportsIsTooLow,
    MarinadeFinanceError.StakeAccountRemainderTooLow,
    MarinadeFinanceError.ShrinkingListWithDeletingContents {

  static MarinadeFinanceError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> WrongReserveOwner.INSTANCE;
      case 6001 -> NonEmptyReserveData.INSTANCE;
      case 6002 -> InvalidInitialReserveLamports.INSTANCE;
      case 6003 -> ZeroValidatorChunkSize.INSTANCE;
      case 6004 -> TooBigValidatorChunkSize.INSTANCE;
      case 6005 -> ZeroCreditChunkSize.INSTANCE;
      case 6006 -> TooBigCreditChunkSize.INSTANCE;
      case 6007 -> TooLowCreditFee.INSTANCE;
      case 6008 -> InvalidMintAuthority.INSTANCE;
      case 6009 -> MintHasInitialSupply.INSTANCE;
      case 6010 -> InvalidOwnerFeeState.INSTANCE;
      case 6011 -> InvalidProgramId.INSTANCE;
      case 6012 -> UnexpectedAccount.INSTANCE;
      case 6013 -> CalculationFailure.INSTANCE;
      case 6014 -> StakeAccountWithLockup.INSTANCE;
      case 6015 -> MinStakeIsTooLow.INSTANCE;
      case 6016 -> LpMaxFeeIsTooHigh.INSTANCE;
      case 6017 -> BasisPointsOverflow.INSTANCE;
      case 6018 -> LpFeesAreWrongWayRound.INSTANCE;
      case 6019 -> LiquidityTargetTooLow.INSTANCE;
      case 6020 -> TicketNotDue.INSTANCE;
      case 6021 -> TicketNotReady.INSTANCE;
      case 6022 -> WrongBeneficiary.INSTANCE;
      case 6023 -> StakeAccountNotUpdatedYet.INSTANCE;
      case 6024 -> StakeNotDelegated.INSTANCE;
      case 6025 -> StakeAccountIsEmergencyUnstaking.INSTANCE;
      case 6026 -> InsufficientLiquidity.INSTANCE;
      case 6027 -> NotUsed6027.INSTANCE;
      case 6028 -> InvalidAdminAuthority.INSTANCE;
      case 6029 -> InvalidValidatorManager.INSTANCE;
      case 6030 -> InvalidStakeListDiscriminator.INSTANCE;
      case 6031 -> InvalidValidatorListDiscriminator.INSTANCE;
      case 6032 -> TreasuryCutIsTooHigh.INSTANCE;
      case 6033 -> RewardsFeeIsTooHigh.INSTANCE;
      case 6034 -> StakingIsCapped.INSTANCE;
      case 6035 -> LiquidityIsCapped.INSTANCE;
      case 6036 -> UpdateWindowIsTooLow.INSTANCE;
      case 6037 -> MinWithdrawIsTooHigh.INSTANCE;
      case 6038 -> WithdrawAmountIsTooLow.INSTANCE;
      case 6039 -> DepositAmountIsTooLow.INSTANCE;
      case 6040 -> NotEnoughUserFunds.INSTANCE;
      case 6041 -> WrongTokenOwnerOrDelegate.INSTANCE;
      case 6042 -> TooEarlyForStakeDelta.INSTANCE;
      case 6043 -> RequiredDelegatedStake.INSTANCE;
      case 6044 -> RequiredActiveStake.INSTANCE;
      case 6045 -> RequiredDeactivatingStake.INSTANCE;
      case 6046 -> DepositingNotActivatedStake.INSTANCE;
      case 6047 -> TooLowDelegationInDepositingStake.INSTANCE;
      case 6048 -> WrongStakeBalance.INSTANCE;
      case 6049 -> WrongValidatorAccountOrIndex.INSTANCE;
      case 6050 -> WrongStakeAccountOrIndex.INSTANCE;
      case 6051 -> UnstakingOnPositiveDelta.INSTANCE;
      case 6052 -> StakingOnNegativeDelta.INSTANCE;
      case 6053 -> MovingStakeIsCapped.INSTANCE;
      case 6054 -> StakeMustBeUninitialized.INSTANCE;
      case 6055 -> DestinationStakeMustBeDelegated.INSTANCE;
      case 6056 -> DestinationStakeMustNotBeDeactivating.INSTANCE;
      case 6057 -> DestinationStakeMustBeUpdated.INSTANCE;
      case 6058 -> InvalidDestinationStakeDelegation.INSTANCE;
      case 6059 -> SourceStakeMustBeDelegated.INSTANCE;
      case 6060 -> SourceStakeMustNotBeDeactivating.INSTANCE;
      case 6061 -> SourceStakeMustBeUpdated.INSTANCE;
      case 6062 -> InvalidSourceStakeDelegation.INSTANCE;
      case 6063 -> InvalidDelayedUnstakeTicket.INSTANCE;
      case 6064 -> ReusingDelayedUnstakeTicket.INSTANCE;
      case 6065 -> EmergencyUnstakingFromNonZeroScoredValidator.INSTANCE;
      case 6066 -> WrongValidatorDuplicationFlag.INSTANCE;
      case 6067 -> RedepositingMarinadeStake.INSTANCE;
      case 6068 -> RemovingValidatorWithBalance.INSTANCE;
      case 6069 -> RedelegateOverTarget.INSTANCE;
      case 6070 -> SourceAndDestValidatorsAreTheSame.INSTANCE;
      case 6071 -> UnregisteredMsolMinted.INSTANCE;
      case 6072 -> UnregisteredLPMinted.INSTANCE;
      case 6073 -> ListIndexOutOfBounds.INSTANCE;
      case 6074 -> ListOverflow.INSTANCE;
      case 6075 -> AlreadyPaused.INSTANCE;
      case 6076 -> NotPaused.INSTANCE;
      case 6077 -> ProgramIsPaused.INSTANCE;
      case 6078 -> InvalidPauseAuthority.INSTANCE;
      case 6079 -> SelectedStakeAccountHasNotEnoughFunds.INSTANCE;
      case 6080 -> BasisPointCentsOverflow.INSTANCE;
      case 6081 -> WithdrawStakeAccountIsNotEnabled.INSTANCE;
      case 6082 -> WithdrawStakeAccountFeeIsTooHigh.INSTANCE;
      case 6083 -> DelayedUnstakeFeeIsTooHigh.INSTANCE;
      case 6084 -> WithdrawStakeLamportsIsTooLow.INSTANCE;
      case 6085 -> StakeAccountRemainderTooLow.INSTANCE;
      case 6086 -> ShrinkingListWithDeletingContents.INSTANCE;
      default -> throw new IllegalStateException("Unexpected MarinadeFinance error code: " + errorCode);
    };
  }

  record WrongReserveOwner(int code, String msg) implements MarinadeFinanceError {

    public static final WrongReserveOwner INSTANCE = new WrongReserveOwner(
        6000, "Wrong reserve owner. Must be a system account"
    );
  }

  record NonEmptyReserveData(int code, String msg) implements MarinadeFinanceError {

    public static final NonEmptyReserveData INSTANCE = new NonEmptyReserveData(
        6001, "Reserve must have no data, but has data"
    );
  }

  record InvalidInitialReserveLamports(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidInitialReserveLamports INSTANCE = new InvalidInitialReserveLamports(
        6002, "Invalid initial reserve lamports"
    );
  }

  record ZeroValidatorChunkSize(int code, String msg) implements MarinadeFinanceError {

    public static final ZeroValidatorChunkSize INSTANCE = new ZeroValidatorChunkSize(
        6003, "Zero validator chunk size"
    );
  }

  record TooBigValidatorChunkSize(int code, String msg) implements MarinadeFinanceError {

    public static final TooBigValidatorChunkSize INSTANCE = new TooBigValidatorChunkSize(
        6004, "Too big validator chunk size"
    );
  }

  record ZeroCreditChunkSize(int code, String msg) implements MarinadeFinanceError {

    public static final ZeroCreditChunkSize INSTANCE = new ZeroCreditChunkSize(
        6005, "Zero credit chunk size"
    );
  }

  record TooBigCreditChunkSize(int code, String msg) implements MarinadeFinanceError {

    public static final TooBigCreditChunkSize INSTANCE = new TooBigCreditChunkSize(
        6006, "Too big credit chunk size"
    );
  }

  record TooLowCreditFee(int code, String msg) implements MarinadeFinanceError {

    public static final TooLowCreditFee INSTANCE = new TooLowCreditFee(
        6007, "Too low credit fee"
    );
  }

  record InvalidMintAuthority(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidMintAuthority INSTANCE = new InvalidMintAuthority(
        6008, "Invalid mint authority"
    );
  }

  record MintHasInitialSupply(int code, String msg) implements MarinadeFinanceError {

    public static final MintHasInitialSupply INSTANCE = new MintHasInitialSupply(
        6009, "Non empty initial mint supply"
    );
  }

  record InvalidOwnerFeeState(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidOwnerFeeState INSTANCE = new InvalidOwnerFeeState(
        6010, "Invalid owner fee state"
    );
  }

  record InvalidProgramId(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidProgramId INSTANCE = new InvalidProgramId(
        6011, "Invalid program id. For using program from another account please update id in the code"
    );
  }

  record UnexpectedAccount(int code, String msg) implements MarinadeFinanceError {

    public static final UnexpectedAccount INSTANCE = new UnexpectedAccount(
        6012, "Unexpected account"
    );
  }

  record CalculationFailure(int code, String msg) implements MarinadeFinanceError {

    public static final CalculationFailure INSTANCE = new CalculationFailure(
        6013, "Calculation failure"
    );
  }

  record StakeAccountWithLockup(int code, String msg) implements MarinadeFinanceError {

    public static final StakeAccountWithLockup INSTANCE = new StakeAccountWithLockup(
        6014, "You can't deposit a stake-account with lockup"
    );
  }

  record MinStakeIsTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final MinStakeIsTooLow INSTANCE = new MinStakeIsTooLow(
        6015, "Min stake is too low"
    );
  }

  record LpMaxFeeIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final LpMaxFeeIsTooHigh INSTANCE = new LpMaxFeeIsTooHigh(
        6016, "Lp max fee is too high"
    );
  }

  record BasisPointsOverflow(int code, String msg) implements MarinadeFinanceError {

    public static final BasisPointsOverflow INSTANCE = new BasisPointsOverflow(
        6017, "Basis points overflow"
    );
  }

  record LpFeesAreWrongWayRound(int code, String msg) implements MarinadeFinanceError {

    public static final LpFeesAreWrongWayRound INSTANCE = new LpFeesAreWrongWayRound(
        6018, "LP min fee > LP max fee"
    );
  }

  record LiquidityTargetTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final LiquidityTargetTooLow INSTANCE = new LiquidityTargetTooLow(
        6019, "Liquidity target too low"
    );
  }

  record TicketNotDue(int code, String msg) implements MarinadeFinanceError {

    public static final TicketNotDue INSTANCE = new TicketNotDue(
        6020, "Ticket not due. Wait more epochs"
    );
  }

  record TicketNotReady(int code, String msg) implements MarinadeFinanceError {

    public static final TicketNotReady INSTANCE = new TicketNotReady(
        6021, "Ticket not ready. Wait a few hours and try again"
    );
  }

  record WrongBeneficiary(int code, String msg) implements MarinadeFinanceError {

    public static final WrongBeneficiary INSTANCE = new WrongBeneficiary(
        6022, "Wrong Ticket Beneficiary"
    );
  }

  record StakeAccountNotUpdatedYet(int code, String msg) implements MarinadeFinanceError {

    public static final StakeAccountNotUpdatedYet INSTANCE = new StakeAccountNotUpdatedYet(
        6023, "Stake Account not updated yet"
    );
  }

  record StakeNotDelegated(int code, String msg) implements MarinadeFinanceError {

    public static final StakeNotDelegated INSTANCE = new StakeNotDelegated(
        6024, "Stake Account not delegated"
    );
  }

  record StakeAccountIsEmergencyUnstaking(int code, String msg) implements MarinadeFinanceError {

    public static final StakeAccountIsEmergencyUnstaking INSTANCE = new StakeAccountIsEmergencyUnstaking(
        6025, "Stake Account is emergency unstaking"
    );
  }

  record InsufficientLiquidity(int code, String msg) implements MarinadeFinanceError {

    public static final InsufficientLiquidity INSTANCE = new InsufficientLiquidity(
        6026, "Insufficient Liquidity in the Liquidity Pool"
    );
  }

  record NotUsed6027(int code, String msg) implements MarinadeFinanceError {

    public static final NotUsed6027 INSTANCE = new NotUsed6027(
        6027, "null"
    );
  }

  record InvalidAdminAuthority(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidAdminAuthority INSTANCE = new InvalidAdminAuthority(
        6028, "Invalid admin authority"
    );
  }

  record InvalidValidatorManager(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidValidatorManager INSTANCE = new InvalidValidatorManager(
        6029, "Invalid validator system manager"
    );
  }

  record InvalidStakeListDiscriminator(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidStakeListDiscriminator INSTANCE = new InvalidStakeListDiscriminator(
        6030, "Invalid stake list account discriminator"
    );
  }

  record InvalidValidatorListDiscriminator(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidValidatorListDiscriminator INSTANCE = new InvalidValidatorListDiscriminator(
        6031, "Invalid validator list account discriminator"
    );
  }

  record TreasuryCutIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final TreasuryCutIsTooHigh INSTANCE = new TreasuryCutIsTooHigh(
        6032, "Treasury cut is too high"
    );
  }

  record RewardsFeeIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final RewardsFeeIsTooHigh INSTANCE = new RewardsFeeIsTooHigh(
        6033, "Reward fee is too high"
    );
  }

  record StakingIsCapped(int code, String msg) implements MarinadeFinanceError {

    public static final StakingIsCapped INSTANCE = new StakingIsCapped(
        6034, "Staking is capped"
    );
  }

  record LiquidityIsCapped(int code, String msg) implements MarinadeFinanceError {

    public static final LiquidityIsCapped INSTANCE = new LiquidityIsCapped(
        6035, "Liquidity is capped"
    );
  }

  record UpdateWindowIsTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final UpdateWindowIsTooLow INSTANCE = new UpdateWindowIsTooLow(
        6036, "Update window is too low"
    );
  }

  record MinWithdrawIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final MinWithdrawIsTooHigh INSTANCE = new MinWithdrawIsTooHigh(
        6037, "Min withdraw is too high"
    );
  }

  record WithdrawAmountIsTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final WithdrawAmountIsTooLow INSTANCE = new WithdrawAmountIsTooLow(
        6038, "Withdraw amount is too low"
    );
  }

  record DepositAmountIsTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final DepositAmountIsTooLow INSTANCE = new DepositAmountIsTooLow(
        6039, "Deposit amount is too low"
    );
  }

  record NotEnoughUserFunds(int code, String msg) implements MarinadeFinanceError {

    public static final NotEnoughUserFunds INSTANCE = new NotEnoughUserFunds(
        6040, "Not enough user funds"
    );
  }

  record WrongTokenOwnerOrDelegate(int code, String msg) implements MarinadeFinanceError {

    public static final WrongTokenOwnerOrDelegate INSTANCE = new WrongTokenOwnerOrDelegate(
        6041, "Wrong token owner or delegate"
    );
  }

  record TooEarlyForStakeDelta(int code, String msg) implements MarinadeFinanceError {

    public static final TooEarlyForStakeDelta INSTANCE = new TooEarlyForStakeDelta(
        6042, "Too early for stake delta"
    );
  }

  record RequiredDelegatedStake(int code, String msg) implements MarinadeFinanceError {

    public static final RequiredDelegatedStake INSTANCE = new RequiredDelegatedStake(
        6043, "Required delegated stake"
    );
  }

  record RequiredActiveStake(int code, String msg) implements MarinadeFinanceError {

    public static final RequiredActiveStake INSTANCE = new RequiredActiveStake(
        6044, "Required active stake"
    );
  }

  record RequiredDeactivatingStake(int code, String msg) implements MarinadeFinanceError {

    public static final RequiredDeactivatingStake INSTANCE = new RequiredDeactivatingStake(
        6045, "Required deactivating stake"
    );
  }

  record DepositingNotActivatedStake(int code, String msg) implements MarinadeFinanceError {

    public static final DepositingNotActivatedStake INSTANCE = new DepositingNotActivatedStake(
        6046, "Depositing not activated stake"
    );
  }

  record TooLowDelegationInDepositingStake(int code, String msg) implements MarinadeFinanceError {

    public static final TooLowDelegationInDepositingStake INSTANCE = new TooLowDelegationInDepositingStake(
        6047, "Too low delegation in the depositing stake"
    );
  }

  record WrongStakeBalance(int code, String msg) implements MarinadeFinanceError {

    public static final WrongStakeBalance INSTANCE = new WrongStakeBalance(
        6048, "Wrong deposited stake balance"
    );
  }

  record WrongValidatorAccountOrIndex(int code, String msg) implements MarinadeFinanceError {

    public static final WrongValidatorAccountOrIndex INSTANCE = new WrongValidatorAccountOrIndex(
        6049, "Wrong validator account or index"
    );
  }

  record WrongStakeAccountOrIndex(int code, String msg) implements MarinadeFinanceError {

    public static final WrongStakeAccountOrIndex INSTANCE = new WrongStakeAccountOrIndex(
        6050, "Wrong stake account or index"
    );
  }

  record UnstakingOnPositiveDelta(int code, String msg) implements MarinadeFinanceError {

    public static final UnstakingOnPositiveDelta INSTANCE = new UnstakingOnPositiveDelta(
        6051, "Delta stake is positive so we must stake instead of unstake"
    );
  }

  record StakingOnNegativeDelta(int code, String msg) implements MarinadeFinanceError {

    public static final StakingOnNegativeDelta INSTANCE = new StakingOnNegativeDelta(
        6052, "Delta stake is negative so we must unstake instead of stake"
    );
  }

  record MovingStakeIsCapped(int code, String msg) implements MarinadeFinanceError {

    public static final MovingStakeIsCapped INSTANCE = new MovingStakeIsCapped(
        6053, "Moving stake during an epoch is capped"
    );
  }

  record StakeMustBeUninitialized(int code, String msg) implements MarinadeFinanceError {

    public static final StakeMustBeUninitialized INSTANCE = new StakeMustBeUninitialized(
        6054, "Stake must be uninitialized"
    );
  }

  record DestinationStakeMustBeDelegated(int code, String msg) implements MarinadeFinanceError {

    public static final DestinationStakeMustBeDelegated INSTANCE = new DestinationStakeMustBeDelegated(
        6055, "Destination stake must be delegated"
    );
  }

  record DestinationStakeMustNotBeDeactivating(int code, String msg) implements MarinadeFinanceError {

    public static final DestinationStakeMustNotBeDeactivating INSTANCE = new DestinationStakeMustNotBeDeactivating(
        6056, "Destination stake must not be deactivating"
    );
  }

  record DestinationStakeMustBeUpdated(int code, String msg) implements MarinadeFinanceError {

    public static final DestinationStakeMustBeUpdated INSTANCE = new DestinationStakeMustBeUpdated(
        6057, "Destination stake must be updated"
    );
  }

  record InvalidDestinationStakeDelegation(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidDestinationStakeDelegation INSTANCE = new InvalidDestinationStakeDelegation(
        6058, "Invalid destination stake delegation"
    );
  }

  record SourceStakeMustBeDelegated(int code, String msg) implements MarinadeFinanceError {

    public static final SourceStakeMustBeDelegated INSTANCE = new SourceStakeMustBeDelegated(
        6059, "Source stake must be delegated"
    );
  }

  record SourceStakeMustNotBeDeactivating(int code, String msg) implements MarinadeFinanceError {

    public static final SourceStakeMustNotBeDeactivating INSTANCE = new SourceStakeMustNotBeDeactivating(
        6060, "Source stake must not be deactivating"
    );
  }

  record SourceStakeMustBeUpdated(int code, String msg) implements MarinadeFinanceError {

    public static final SourceStakeMustBeUpdated INSTANCE = new SourceStakeMustBeUpdated(
        6061, "Source stake must be updated"
    );
  }

  record InvalidSourceStakeDelegation(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidSourceStakeDelegation INSTANCE = new InvalidSourceStakeDelegation(
        6062, "Invalid source stake delegation"
    );
  }

  record InvalidDelayedUnstakeTicket(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidDelayedUnstakeTicket INSTANCE = new InvalidDelayedUnstakeTicket(
        6063, "Invalid delayed unstake ticket"
    );
  }

  record ReusingDelayedUnstakeTicket(int code, String msg) implements MarinadeFinanceError {

    public static final ReusingDelayedUnstakeTicket INSTANCE = new ReusingDelayedUnstakeTicket(
        6064, "Reusing delayed unstake ticket"
    );
  }

  record EmergencyUnstakingFromNonZeroScoredValidator(int code, String msg) implements MarinadeFinanceError {

    public static final EmergencyUnstakingFromNonZeroScoredValidator INSTANCE = new EmergencyUnstakingFromNonZeroScoredValidator(
        6065, "Emergency unstaking from non zero scored validator"
    );
  }

  record WrongValidatorDuplicationFlag(int code, String msg) implements MarinadeFinanceError {

    public static final WrongValidatorDuplicationFlag INSTANCE = new WrongValidatorDuplicationFlag(
        6066, "Wrong validator duplication flag"
    );
  }

  record RedepositingMarinadeStake(int code, String msg) implements MarinadeFinanceError {

    public static final RedepositingMarinadeStake INSTANCE = new RedepositingMarinadeStake(
        6067, "Redepositing marinade stake"
    );
  }

  record RemovingValidatorWithBalance(int code, String msg) implements MarinadeFinanceError {

    public static final RemovingValidatorWithBalance INSTANCE = new RemovingValidatorWithBalance(
        6068, "Removing validator with balance"
    );
  }

  record RedelegateOverTarget(int code, String msg) implements MarinadeFinanceError {

    public static final RedelegateOverTarget INSTANCE = new RedelegateOverTarget(
        6069, "Redelegate will put validator over stake target"
    );
  }

  record SourceAndDestValidatorsAreTheSame(int code, String msg) implements MarinadeFinanceError {

    public static final SourceAndDestValidatorsAreTheSame INSTANCE = new SourceAndDestValidatorsAreTheSame(
        6070, "Source and Dest Validators are the same"
    );
  }

  record UnregisteredMsolMinted(int code, String msg) implements MarinadeFinanceError {

    public static final UnregisteredMsolMinted INSTANCE = new UnregisteredMsolMinted(
        6071, "Some mSOL tokens was minted outside of marinade contract"
    );
  }

  record UnregisteredLPMinted(int code, String msg) implements MarinadeFinanceError {

    public static final UnregisteredLPMinted INSTANCE = new UnregisteredLPMinted(
        6072, "Some LP tokens was minted outside of marinade contract"
    );
  }

  record ListIndexOutOfBounds(int code, String msg) implements MarinadeFinanceError {

    public static final ListIndexOutOfBounds INSTANCE = new ListIndexOutOfBounds(
        6073, "List index out of bounds"
    );
  }

  record ListOverflow(int code, String msg) implements MarinadeFinanceError {

    public static final ListOverflow INSTANCE = new ListOverflow(
        6074, "List overflow"
    );
  }

  record AlreadyPaused(int code, String msg) implements MarinadeFinanceError {

    public static final AlreadyPaused INSTANCE = new AlreadyPaused(
        6075, "Requested pause and already Paused"
    );
  }

  record NotPaused(int code, String msg) implements MarinadeFinanceError {

    public static final NotPaused INSTANCE = new NotPaused(
        6076, "Requested resume, but not Paused"
    );
  }

  record ProgramIsPaused(int code, String msg) implements MarinadeFinanceError {

    public static final ProgramIsPaused INSTANCE = new ProgramIsPaused(
        6077, "Emergency Pause is Active"
    );
  }

  record InvalidPauseAuthority(int code, String msg) implements MarinadeFinanceError {

    public static final InvalidPauseAuthority INSTANCE = new InvalidPauseAuthority(
        6078, "Invalid pause authority"
    );
  }

  record SelectedStakeAccountHasNotEnoughFunds(int code, String msg) implements MarinadeFinanceError {

    public static final SelectedStakeAccountHasNotEnoughFunds INSTANCE = new SelectedStakeAccountHasNotEnoughFunds(
        6079, "Selected Stake account has not enough funds"
    );
  }

  record BasisPointCentsOverflow(int code, String msg) implements MarinadeFinanceError {

    public static final BasisPointCentsOverflow INSTANCE = new BasisPointCentsOverflow(
        6080, "Basis point CENTS overflow"
    );
  }

  record WithdrawStakeAccountIsNotEnabled(int code, String msg) implements MarinadeFinanceError {

    public static final WithdrawStakeAccountIsNotEnabled INSTANCE = new WithdrawStakeAccountIsNotEnabled(
        6081, "Withdraw stake account is not enabled"
    );
  }

  record WithdrawStakeAccountFeeIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final WithdrawStakeAccountFeeIsTooHigh INSTANCE = new WithdrawStakeAccountFeeIsTooHigh(
        6082, "Withdraw stake account fee is too high"
    );
  }

  record DelayedUnstakeFeeIsTooHigh(int code, String msg) implements MarinadeFinanceError {

    public static final DelayedUnstakeFeeIsTooHigh INSTANCE = new DelayedUnstakeFeeIsTooHigh(
        6083, "Delayed unstake fee is too high"
    );
  }

  record WithdrawStakeLamportsIsTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final WithdrawStakeLamportsIsTooLow INSTANCE = new WithdrawStakeLamportsIsTooLow(
        6084, "Withdraw stake account value is too low"
    );
  }

  record StakeAccountRemainderTooLow(int code, String msg) implements MarinadeFinanceError {

    public static final StakeAccountRemainderTooLow INSTANCE = new StakeAccountRemainderTooLow(
        6085, "Stake account remainder too low"
    );
  }

  record ShrinkingListWithDeletingContents(int code, String msg) implements MarinadeFinanceError {

    public static final ShrinkingListWithDeletingContents INSTANCE = new ShrinkingListWithDeletingContents(
        6086, "Capacity of the list must be not less than it's current size"
    );
  }
}
