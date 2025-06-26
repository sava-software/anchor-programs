package software.sava.anchor.programs.jito.tip_router.anchor;

import software.sava.anchor.ProgramError;

public sealed interface JitoTipRouterError extends ProgramError permits
    JitoTipRouterError.DenominatorIsZero,
    JitoTipRouterError.ArithmeticOverflow,
    JitoTipRouterError.ArithmeticUnderflowError,
    JitoTipRouterError.ArithmeticFloorError,
    JitoTipRouterError.ModuloOverflow,
    JitoTipRouterError.NewPreciseNumberError,
    JitoTipRouterError.CastToImpreciseNumberError,
    JitoTipRouterError.CastToU64Error,
    JitoTipRouterError.CastToU128Error,
    JitoTipRouterError.IncorrectWeightTableAdmin,
    JitoTipRouterError.DuplicateMintsInTable,
    JitoTipRouterError.NoMintsInTable,
    JitoTipRouterError.TableNotInitialized,
    JitoTipRouterError.RegistryNotInitialized,
    JitoTipRouterError.NoVaultsInRegistry,
    JitoTipRouterError.VaultNotInRegistry,
    JitoTipRouterError.MintInTable,
    JitoTipRouterError.TooManyMintsForTable,
    JitoTipRouterError.TooManyVaultsForRegistry,
    JitoTipRouterError.WeightTableAlreadyInitialized,
    JitoTipRouterError.CannotCreateFutureWeightTables,
    JitoTipRouterError.WeightMintsDoNotMatchLength,
    JitoTipRouterError.WeightMintsDoNotMatchMintHash,
    JitoTipRouterError.InvalidMintForWeightTable,
    JitoTipRouterError.ConfigMintsNotUpdated,
    JitoTipRouterError.ConfigMintListFull,
    JitoTipRouterError.VaultRegistryListFull,
    JitoTipRouterError.VaultRegistryVaultLocked,
    JitoTipRouterError.VaultIndexAlreadyInUse,
    JitoTipRouterError.MintEntryNotFound,
    JitoTipRouterError.FeeCapExceeded,
    JitoTipRouterError.TotalFeesCannotBeZero,
    JitoTipRouterError.DefaultDaoWallet,
    JitoTipRouterError.IncorrectNcnAdmin,
    JitoTipRouterError.IncorrectNcn,
    JitoTipRouterError.IncorrectFeeAdmin,
    JitoTipRouterError.WeightTableNotFinalized,
    JitoTipRouterError.WeightNotFound,
    JitoTipRouterError.NoOperators,
    JitoTipRouterError.VaultOperatorDelegationFinalized,
    JitoTipRouterError.OperatorFinalized,
    JitoTipRouterError.TooManyVaultOperatorDelegations,
    JitoTipRouterError.DuplicateVaultOperatorDelegation,
    JitoTipRouterError.DuplicateVoteCast,
    JitoTipRouterError.OperatorVotesFull,
    JitoTipRouterError.BallotTallyFull,
    JitoTipRouterError.BallotTallyNotFoundFull,
    JitoTipRouterError.BallotTallyNotEmpty,
    JitoTipRouterError.ConsensusAlreadyReached,
    JitoTipRouterError.ConsensusNotReached,
    JitoTipRouterError.EpochSnapshotNotFinalized,
    JitoTipRouterError.VotingNotValid,
    JitoTipRouterError.TieBreakerAdminInvalid,
    JitoTipRouterError.VotingNotFinalized,
    JitoTipRouterError.TieBreakerNotInPriorVotes,
    JitoTipRouterError.InvalidMerkleProof,
    JitoTipRouterError.InvalidOperatorVoter,
    JitoTipRouterError.InvalidNcnFeeGroup,
    JitoTipRouterError.InvalidBaseFeeGroup,
    JitoTipRouterError.OperatorRewardListFull,
    JitoTipRouterError.OperatorRewardNotFound,
    JitoTipRouterError.VaultRewardNotFound,
    JitoTipRouterError.DestinationMismatch,
    JitoTipRouterError.NcnRewardRouteNotFound,
    JitoTipRouterError.FeeNotActive,
    JitoTipRouterError.NoRewards,
    JitoTipRouterError.NoFeedWeightNotSet,
    JitoTipRouterError.SwitchboardNotRegistered,
    JitoTipRouterError.BadSwitchboardFeed,
    JitoTipRouterError.BadSwitchboardValue,
    JitoTipRouterError.StaleSwitchboardFeed,
    JitoTipRouterError.NoFeedWeightOrSwitchboardFeed,
    JitoTipRouterError.RouterStillRouting,
    JitoTipRouterError.InvalidEpochsBeforeStall,
    JitoTipRouterError.InvalidEpochsBeforeClose,
    JitoTipRouterError.InvalidSlotsAfterConsensus,
    JitoTipRouterError.VaultNeedsUpdate,
    JitoTipRouterError.InvalidAccountStatus,
    JitoTipRouterError.AccountAlreadyInitialized,
    JitoTipRouterError.BadBallot,
    JitoTipRouterError.VotingIsNotOver,
    JitoTipRouterError.OperatorIsNotInSnapshot,
    JitoTipRouterError.InvalidAccountToCloseDiscriminator,
    JitoTipRouterError.CannotCloseAccount,
    JitoTipRouterError.CannotCloseAccountAlreadyClosed,
    JitoTipRouterError.CannotCloseAccountNotEnoughEpochs,
    JitoTipRouterError.CannotCloseAccountNoReceiverProvided,
    JitoTipRouterError.CannotCloseEpochStateAccount,
    JitoTipRouterError.InvalidDaoWallet,
    JitoTipRouterError.EpochIsClosingDown,
    JitoTipRouterError.MarkerExists {

  static JitoTipRouterError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 8448 -> DenominatorIsZero.INSTANCE;
      case 8449 -> ArithmeticOverflow.INSTANCE;
      case 8450 -> ArithmeticUnderflowError.INSTANCE;
      case 8451 -> ArithmeticFloorError.INSTANCE;
      case 8452 -> ModuloOverflow.INSTANCE;
      case 8453 -> NewPreciseNumberError.INSTANCE;
      case 8454 -> CastToImpreciseNumberError.INSTANCE;
      case 8455 -> CastToU64Error.INSTANCE;
      case 8456 -> CastToU128Error.INSTANCE;
      case 8704 -> IncorrectWeightTableAdmin.INSTANCE;
      case 8705 -> DuplicateMintsInTable.INSTANCE;
      case 8706 -> NoMintsInTable.INSTANCE;
      case 8707 -> TableNotInitialized.INSTANCE;
      case 8708 -> RegistryNotInitialized.INSTANCE;
      case 8709 -> NoVaultsInRegistry.INSTANCE;
      case 8710 -> VaultNotInRegistry.INSTANCE;
      case 8711 -> MintInTable.INSTANCE;
      case 8712 -> TooManyMintsForTable.INSTANCE;
      case 8713 -> TooManyVaultsForRegistry.INSTANCE;
      case 8714 -> WeightTableAlreadyInitialized.INSTANCE;
      case 8715 -> CannotCreateFutureWeightTables.INSTANCE;
      case 8716 -> WeightMintsDoNotMatchLength.INSTANCE;
      case 8717 -> WeightMintsDoNotMatchMintHash.INSTANCE;
      case 8718 -> InvalidMintForWeightTable.INSTANCE;
      case 8719 -> ConfigMintsNotUpdated.INSTANCE;
      case 8720 -> ConfigMintListFull.INSTANCE;
      case 8721 -> VaultRegistryListFull.INSTANCE;
      case 8722 -> VaultRegistryVaultLocked.INSTANCE;
      case 8723 -> VaultIndexAlreadyInUse.INSTANCE;
      case 8724 -> MintEntryNotFound.INSTANCE;
      case 8725 -> FeeCapExceeded.INSTANCE;
      case 8726 -> TotalFeesCannotBeZero.INSTANCE;
      case 8727 -> DefaultDaoWallet.INSTANCE;
      case 8728 -> IncorrectNcnAdmin.INSTANCE;
      case 8729 -> IncorrectNcn.INSTANCE;
      case 8730 -> IncorrectFeeAdmin.INSTANCE;
      case 8731 -> WeightTableNotFinalized.INSTANCE;
      case 8732 -> WeightNotFound.INSTANCE;
      case 8733 -> NoOperators.INSTANCE;
      case 8734 -> VaultOperatorDelegationFinalized.INSTANCE;
      case 8735 -> OperatorFinalized.INSTANCE;
      case 8736 -> TooManyVaultOperatorDelegations.INSTANCE;
      case 8737 -> DuplicateVaultOperatorDelegation.INSTANCE;
      case 8738 -> DuplicateVoteCast.INSTANCE;
      case 8739 -> OperatorVotesFull.INSTANCE;
      case 8740 -> BallotTallyFull.INSTANCE;
      case 8741 -> BallotTallyNotFoundFull.INSTANCE;
      case 8742 -> BallotTallyNotEmpty.INSTANCE;
      case 8743 -> ConsensusAlreadyReached.INSTANCE;
      case 8744 -> ConsensusNotReached.INSTANCE;
      case 8745 -> EpochSnapshotNotFinalized.INSTANCE;
      case 8746 -> VotingNotValid.INSTANCE;
      case 8747 -> TieBreakerAdminInvalid.INSTANCE;
      case 8748 -> VotingNotFinalized.INSTANCE;
      case 8749 -> TieBreakerNotInPriorVotes.INSTANCE;
      case 8750 -> InvalidMerkleProof.INSTANCE;
      case 8751 -> InvalidOperatorVoter.INSTANCE;
      case 8752 -> InvalidNcnFeeGroup.INSTANCE;
      case 8753 -> InvalidBaseFeeGroup.INSTANCE;
      case 8754 -> OperatorRewardListFull.INSTANCE;
      case 8755 -> OperatorRewardNotFound.INSTANCE;
      case 8756 -> VaultRewardNotFound.INSTANCE;
      case 8757 -> DestinationMismatch.INSTANCE;
      case 8758 -> NcnRewardRouteNotFound.INSTANCE;
      case 8759 -> FeeNotActive.INSTANCE;
      case 8760 -> NoRewards.INSTANCE;
      case 8761 -> NoFeedWeightNotSet.INSTANCE;
      case 8762 -> SwitchboardNotRegistered.INSTANCE;
      case 8763 -> BadSwitchboardFeed.INSTANCE;
      case 8764 -> BadSwitchboardValue.INSTANCE;
      case 8765 -> StaleSwitchboardFeed.INSTANCE;
      case 8766 -> NoFeedWeightOrSwitchboardFeed.INSTANCE;
      case 8767 -> RouterStillRouting.INSTANCE;
      case 8768 -> InvalidEpochsBeforeStall.INSTANCE;
      case 8769 -> InvalidEpochsBeforeClose.INSTANCE;
      case 8770 -> InvalidSlotsAfterConsensus.INSTANCE;
      case 8771 -> VaultNeedsUpdate.INSTANCE;
      case 8772 -> InvalidAccountStatus.INSTANCE;
      case 8773 -> AccountAlreadyInitialized.INSTANCE;
      case 8774 -> BadBallot.INSTANCE;
      case 8775 -> VotingIsNotOver.INSTANCE;
      case 8776 -> OperatorIsNotInSnapshot.INSTANCE;
      case 8777 -> InvalidAccountToCloseDiscriminator.INSTANCE;
      case 8778 -> CannotCloseAccount.INSTANCE;
      case 8779 -> CannotCloseAccountAlreadyClosed.INSTANCE;
      case 8780 -> CannotCloseAccountNotEnoughEpochs.INSTANCE;
      case 8781 -> CannotCloseAccountNoReceiverProvided.INSTANCE;
      case 8782 -> CannotCloseEpochStateAccount.INSTANCE;
      case 8783 -> InvalidDaoWallet.INSTANCE;
      case 8784 -> EpochIsClosingDown.INSTANCE;
      case 8785 -> MarkerExists.INSTANCE;
      default -> throw new IllegalStateException("Unexpected JitoTipRouter error code: " + errorCode);
    };
  }

  record DenominatorIsZero(int code, String msg) implements JitoTipRouterError {

    public static final DenominatorIsZero INSTANCE = new DenominatorIsZero(
        8448, "Zero in the denominator"
    );
  }

  record ArithmeticOverflow(int code, String msg) implements JitoTipRouterError {

    public static final ArithmeticOverflow INSTANCE = new ArithmeticOverflow(
        8449, "Overflow"
    );
  }

  record ArithmeticUnderflowError(int code, String msg) implements JitoTipRouterError {

    public static final ArithmeticUnderflowError INSTANCE = new ArithmeticUnderflowError(
        8450, "Underflow"
    );
  }

  record ArithmeticFloorError(int code, String msg) implements JitoTipRouterError {

    public static final ArithmeticFloorError INSTANCE = new ArithmeticFloorError(
        8451, "Floor Overflow"
    );
  }

  record ModuloOverflow(int code, String msg) implements JitoTipRouterError {

    public static final ModuloOverflow INSTANCE = new ModuloOverflow(
        8452, "Modulo Overflow"
    );
  }

  record NewPreciseNumberError(int code, String msg) implements JitoTipRouterError {

    public static final NewPreciseNumberError INSTANCE = new NewPreciseNumberError(
        8453, "New precise number error"
    );
  }

  record CastToImpreciseNumberError(int code, String msg) implements JitoTipRouterError {

    public static final CastToImpreciseNumberError INSTANCE = new CastToImpreciseNumberError(
        8454, "Cast to imprecise number error"
    );
  }

  record CastToU64Error(int code, String msg) implements JitoTipRouterError {

    public static final CastToU64Error INSTANCE = new CastToU64Error(
        8455, "Cast to u64 error"
    );
  }

  record CastToU128Error(int code, String msg) implements JitoTipRouterError {

    public static final CastToU128Error INSTANCE = new CastToU128Error(
        8456, "Cast to u128 error"
    );
  }

  record IncorrectWeightTableAdmin(int code, String msg) implements JitoTipRouterError {

    public static final IncorrectWeightTableAdmin INSTANCE = new IncorrectWeightTableAdmin(
        8704, "Incorrect weight table admin"
    );
  }

  record DuplicateMintsInTable(int code, String msg) implements JitoTipRouterError {

    public static final DuplicateMintsInTable INSTANCE = new DuplicateMintsInTable(
        8705, "Duplicate mints in table"
    );
  }

  record NoMintsInTable(int code, String msg) implements JitoTipRouterError {

    public static final NoMintsInTable INSTANCE = new NoMintsInTable(
        8706, "There are no mints in the table"
    );
  }

  record TableNotInitialized(int code, String msg) implements JitoTipRouterError {

    public static final TableNotInitialized INSTANCE = new TableNotInitialized(
        8707, "Table not initialized"
    );
  }

  record RegistryNotInitialized(int code, String msg) implements JitoTipRouterError {

    public static final RegistryNotInitialized INSTANCE = new RegistryNotInitialized(
        8708, "Registry not initialized"
    );
  }

  record NoVaultsInRegistry(int code, String msg) implements JitoTipRouterError {

    public static final NoVaultsInRegistry INSTANCE = new NoVaultsInRegistry(
        8709, "There are no vaults in the registry"
    );
  }

  record VaultNotInRegistry(int code, String msg) implements JitoTipRouterError {

    public static final VaultNotInRegistry INSTANCE = new VaultNotInRegistry(
        8710, "Vault not in weight table registry"
    );
  }

  record MintInTable(int code, String msg) implements JitoTipRouterError {

    public static final MintInTable INSTANCE = new MintInTable(
        8711, "Mint is already in the table"
    );
  }

  record TooManyMintsForTable(int code, String msg) implements JitoTipRouterError {

    public static final TooManyMintsForTable INSTANCE = new TooManyMintsForTable(
        8712, "Too many mints for table"
    );
  }

  record TooManyVaultsForRegistry(int code, String msg) implements JitoTipRouterError {

    public static final TooManyVaultsForRegistry INSTANCE = new TooManyVaultsForRegistry(
        8713, "Too many vaults for registry"
    );
  }

  record WeightTableAlreadyInitialized(int code, String msg) implements JitoTipRouterError {

    public static final WeightTableAlreadyInitialized INSTANCE = new WeightTableAlreadyInitialized(
        8714, "Weight table already initialized"
    );
  }

  record CannotCreateFutureWeightTables(int code, String msg) implements JitoTipRouterError {

    public static final CannotCreateFutureWeightTables INSTANCE = new CannotCreateFutureWeightTables(
        8715, "Cannnot create future weight tables"
    );
  }

  record WeightMintsDoNotMatchLength(int code, String msg) implements JitoTipRouterError {

    public static final WeightMintsDoNotMatchLength INSTANCE = new WeightMintsDoNotMatchLength(
        8716, "Weight mints do not match - length"
    );
  }

  record WeightMintsDoNotMatchMintHash(int code, String msg) implements JitoTipRouterError {

    public static final WeightMintsDoNotMatchMintHash INSTANCE = new WeightMintsDoNotMatchMintHash(
        8717, "Weight mints do not match - mint hash"
    );
  }

  record InvalidMintForWeightTable(int code, String msg) implements JitoTipRouterError {

    public static final InvalidMintForWeightTable INSTANCE = new InvalidMintForWeightTable(
        8718, "Invalid mint for weight table"
    );
  }

  record ConfigMintsNotUpdated(int code, String msg) implements JitoTipRouterError {

    public static final ConfigMintsNotUpdated INSTANCE = new ConfigMintsNotUpdated(
        8719, "Config supported mints do not match NCN Vault Count"
    );
  }

  record ConfigMintListFull(int code, String msg) implements JitoTipRouterError {

    public static final ConfigMintListFull INSTANCE = new ConfigMintListFull(
        8720, "NCN config vaults are at capacity"
    );
  }

  record VaultRegistryListFull(int code, String msg) implements JitoTipRouterError {

    public static final VaultRegistryListFull INSTANCE = new VaultRegistryListFull(
        8721, "Vault Registry mints are at capacity"
    );
  }

  record VaultRegistryVaultLocked(int code, String msg) implements JitoTipRouterError {

    public static final VaultRegistryVaultLocked INSTANCE = new VaultRegistryVaultLocked(
        8722, "Vault registry are locked for the epoch"
    );
  }

  record VaultIndexAlreadyInUse(int code, String msg) implements JitoTipRouterError {

    public static final VaultIndexAlreadyInUse INSTANCE = new VaultIndexAlreadyInUse(
        8723, "Vault index already in use by a different mint"
    );
  }

  record MintEntryNotFound(int code, String msg) implements JitoTipRouterError {

    public static final MintEntryNotFound INSTANCE = new MintEntryNotFound(
        8724, "Mint Entry not found"
    );
  }

  record FeeCapExceeded(int code, String msg) implements JitoTipRouterError {

    public static final FeeCapExceeded INSTANCE = new FeeCapExceeded(
        8725, "Fee cap exceeded"
    );
  }

  record TotalFeesCannotBeZero(int code, String msg) implements JitoTipRouterError {

    public static final TotalFeesCannotBeZero INSTANCE = new TotalFeesCannotBeZero(
        8726, "Total fees cannot be 0"
    );
  }

  record DefaultDaoWallet(int code, String msg) implements JitoTipRouterError {

    public static final DefaultDaoWallet INSTANCE = new DefaultDaoWallet(
        8727, "DAO wallet cannot be default"
    );
  }

  record IncorrectNcnAdmin(int code, String msg) implements JitoTipRouterError {

    public static final IncorrectNcnAdmin INSTANCE = new IncorrectNcnAdmin(
        8728, "Incorrect NCN Admin"
    );
  }

  record IncorrectNcn(int code, String msg) implements JitoTipRouterError {

    public static final IncorrectNcn INSTANCE = new IncorrectNcn(
        8729, "Incorrect NCN"
    );
  }

  record IncorrectFeeAdmin(int code, String msg) implements JitoTipRouterError {

    public static final IncorrectFeeAdmin INSTANCE = new IncorrectFeeAdmin(
        8730, "Incorrect fee admin"
    );
  }

  record WeightTableNotFinalized(int code, String msg) implements JitoTipRouterError {

    public static final WeightTableNotFinalized INSTANCE = new WeightTableNotFinalized(
        8731, "Weight table not finalized"
    );
  }

  record WeightNotFound(int code, String msg) implements JitoTipRouterError {

    public static final WeightNotFound INSTANCE = new WeightNotFound(
        8732, "Weight not found"
    );
  }

  record NoOperators(int code, String msg) implements JitoTipRouterError {

    public static final NoOperators INSTANCE = new NoOperators(
        8733, "No operators in ncn"
    );
  }

  record VaultOperatorDelegationFinalized(int code, String msg) implements JitoTipRouterError {

    public static final VaultOperatorDelegationFinalized INSTANCE = new VaultOperatorDelegationFinalized(
        8734, "Vault operator delegation is already finalized - should not happen"
    );
  }

  record OperatorFinalized(int code, String msg) implements JitoTipRouterError {

    public static final OperatorFinalized INSTANCE = new OperatorFinalized(
        8735, "Operator is already finalized - should not happen"
    );
  }

  record TooManyVaultOperatorDelegations(int code, String msg) implements JitoTipRouterError {

    public static final TooManyVaultOperatorDelegations INSTANCE = new TooManyVaultOperatorDelegations(
        8736, "Too many vault operator delegations"
    );
  }

  record DuplicateVaultOperatorDelegation(int code, String msg) implements JitoTipRouterError {

    public static final DuplicateVaultOperatorDelegation INSTANCE = new DuplicateVaultOperatorDelegation(
        8737, "Duplicate vault operator delegation"
    );
  }

  record DuplicateVoteCast(int code, String msg) implements JitoTipRouterError {

    public static final DuplicateVoteCast INSTANCE = new DuplicateVoteCast(
        8738, "Duplicate Vote Cast"
    );
  }

  record OperatorVotesFull(int code, String msg) implements JitoTipRouterError {

    public static final OperatorVotesFull INSTANCE = new OperatorVotesFull(
        8739, "Operator votes full"
    );
  }

  record BallotTallyFull(int code, String msg) implements JitoTipRouterError {

    public static final BallotTallyFull INSTANCE = new BallotTallyFull(
        8740, "Merkle root tally full"
    );
  }

  record BallotTallyNotFoundFull(int code, String msg) implements JitoTipRouterError {

    public static final BallotTallyNotFoundFull INSTANCE = new BallotTallyNotFoundFull(
        8741, "Ballot tally not found"
    );
  }

  record BallotTallyNotEmpty(int code, String msg) implements JitoTipRouterError {

    public static final BallotTallyNotEmpty INSTANCE = new BallotTallyNotEmpty(
        8742, "Ballot tally not empty"
    );
  }

  record ConsensusAlreadyReached(int code, String msg) implements JitoTipRouterError {

    public static final ConsensusAlreadyReached INSTANCE = new ConsensusAlreadyReached(
        8743, "Consensus already reached, cannot change vote"
    );
  }

  record ConsensusNotReached(int code, String msg) implements JitoTipRouterError {

    public static final ConsensusNotReached INSTANCE = new ConsensusNotReached(
        8744, "Consensus not reached"
    );
  }

  record EpochSnapshotNotFinalized(int code, String msg) implements JitoTipRouterError {

    public static final EpochSnapshotNotFinalized INSTANCE = new EpochSnapshotNotFinalized(
        8745, "Epoch snapshot not finalized"
    );
  }

  record VotingNotValid(int code, String msg) implements JitoTipRouterError {

    public static final VotingNotValid INSTANCE = new VotingNotValid(
        8746, "Voting not valid, too many slots after consensus reached"
    );
  }

  record TieBreakerAdminInvalid(int code, String msg) implements JitoTipRouterError {

    public static final TieBreakerAdminInvalid INSTANCE = new TieBreakerAdminInvalid(
        8747, "Tie breaker admin invalid"
    );
  }

  record VotingNotFinalized(int code, String msg) implements JitoTipRouterError {

    public static final VotingNotFinalized INSTANCE = new VotingNotFinalized(
        8748, "Voting not finalized"
    );
  }

  record TieBreakerNotInPriorVotes(int code, String msg) implements JitoTipRouterError {

    public static final TieBreakerNotInPriorVotes INSTANCE = new TieBreakerNotInPriorVotes(
        8749, "Tie breaking ballot must be one of the prior votes"
    );
  }

  record InvalidMerkleProof(int code, String msg) implements JitoTipRouterError {

    public static final InvalidMerkleProof INSTANCE = new InvalidMerkleProof(
        8750, "Invalid merkle proof"
    );
  }

  record InvalidOperatorVoter(int code, String msg) implements JitoTipRouterError {

    public static final InvalidOperatorVoter INSTANCE = new InvalidOperatorVoter(
        8751, "Operator voter needs to sign its vote"
    );
  }

  record InvalidNcnFeeGroup(int code, String msg) implements JitoTipRouterError {

    public static final InvalidNcnFeeGroup INSTANCE = new InvalidNcnFeeGroup(
        8752, "Not a valid NCN fee group"
    );
  }

  record InvalidBaseFeeGroup(int code, String msg) implements JitoTipRouterError {

    public static final InvalidBaseFeeGroup INSTANCE = new InvalidBaseFeeGroup(
        8753, "Not a valid base fee group"
    );
  }

  record OperatorRewardListFull(int code, String msg) implements JitoTipRouterError {

    public static final OperatorRewardListFull INSTANCE = new OperatorRewardListFull(
        8754, "Operator reward list full"
    );
  }

  record OperatorRewardNotFound(int code, String msg) implements JitoTipRouterError {

    public static final OperatorRewardNotFound INSTANCE = new OperatorRewardNotFound(
        8755, "Operator Reward not found"
    );
  }

  record VaultRewardNotFound(int code, String msg) implements JitoTipRouterError {

    public static final VaultRewardNotFound INSTANCE = new VaultRewardNotFound(
        8756, "Vault Reward not found"
    );
  }

  record DestinationMismatch(int code, String msg) implements JitoTipRouterError {

    public static final DestinationMismatch INSTANCE = new DestinationMismatch(
        8757, "Destination mismatch"
    );
  }

  record NcnRewardRouteNotFound(int code, String msg) implements JitoTipRouterError {

    public static final NcnRewardRouteNotFound INSTANCE = new NcnRewardRouteNotFound(
        8758, "Ncn reward route not found"
    );
  }

  record FeeNotActive(int code, String msg) implements JitoTipRouterError {

    public static final FeeNotActive INSTANCE = new FeeNotActive(
        8759, "Fee not active"
    );
  }

  record NoRewards(int code, String msg) implements JitoTipRouterError {

    public static final NoRewards INSTANCE = new NoRewards(
        8760, "No rewards to distribute"
    );
  }

  record NoFeedWeightNotSet(int code, String msg) implements JitoTipRouterError {

    public static final NoFeedWeightNotSet INSTANCE = new NoFeedWeightNotSet(
        8761, "No Feed Weight not set"
    );
  }

  record SwitchboardNotRegistered(int code, String msg) implements JitoTipRouterError {

    public static final SwitchboardNotRegistered INSTANCE = new SwitchboardNotRegistered(
        8762, "Switchboard not registered"
    );
  }

  record BadSwitchboardFeed(int code, String msg) implements JitoTipRouterError {

    public static final BadSwitchboardFeed INSTANCE = new BadSwitchboardFeed(
        8763, "Bad switchboard feed"
    );
  }

  record BadSwitchboardValue(int code, String msg) implements JitoTipRouterError {

    public static final BadSwitchboardValue INSTANCE = new BadSwitchboardValue(
        8764, "Bad switchboard value"
    );
  }

  record StaleSwitchboardFeed(int code, String msg) implements JitoTipRouterError {

    public static final StaleSwitchboardFeed INSTANCE = new StaleSwitchboardFeed(
        8765, "Stale switchboard feed"
    );
  }

  record NoFeedWeightOrSwitchboardFeed(int code, String msg) implements JitoTipRouterError {

    public static final NoFeedWeightOrSwitchboardFeed INSTANCE = new NoFeedWeightOrSwitchboardFeed(
        8766, "Weight entry needs either a feed or a no feed weight"
    );
  }

  record RouterStillRouting(int code, String msg) implements JitoTipRouterError {

    public static final RouterStillRouting INSTANCE = new RouterStillRouting(
        8767, "Router still routing"
    );
  }

  record InvalidEpochsBeforeStall(int code, String msg) implements JitoTipRouterError {

    public static final InvalidEpochsBeforeStall INSTANCE = new InvalidEpochsBeforeStall(
        8768, "Invalid epochs before stall"
    );
  }

  record InvalidEpochsBeforeClose(int code, String msg) implements JitoTipRouterError {

    public static final InvalidEpochsBeforeClose INSTANCE = new InvalidEpochsBeforeClose(
        8769, "Invalid epochs before accounts can close"
    );
  }

  record InvalidSlotsAfterConsensus(int code, String msg) implements JitoTipRouterError {

    public static final InvalidSlotsAfterConsensus INSTANCE = new InvalidSlotsAfterConsensus(
        8770, "Invalid slots after consensus"
    );
  }

  record VaultNeedsUpdate(int code, String msg) implements JitoTipRouterError {

    public static final VaultNeedsUpdate INSTANCE = new VaultNeedsUpdate(
        8771, "Vault needs to be updated"
    );
  }

  record InvalidAccountStatus(int code, String msg) implements JitoTipRouterError {

    public static final InvalidAccountStatus INSTANCE = new InvalidAccountStatus(
        8772, "Invalid Account Status"
    );
  }

  record AccountAlreadyInitialized(int code, String msg) implements JitoTipRouterError {

    public static final AccountAlreadyInitialized INSTANCE = new AccountAlreadyInitialized(
        8773, "Account already initialized"
    );
  }

  record BadBallot(int code, String msg) implements JitoTipRouterError {

    public static final BadBallot INSTANCE = new BadBallot(
        8774, "Cannot vote with uninitialized account"
    );
  }

  record VotingIsNotOver(int code, String msg) implements JitoTipRouterError {

    public static final VotingIsNotOver INSTANCE = new VotingIsNotOver(
        8775, "Cannot route until voting is over"
    );
  }

  record OperatorIsNotInSnapshot(int code, String msg) implements JitoTipRouterError {

    public static final OperatorIsNotInSnapshot INSTANCE = new OperatorIsNotInSnapshot(
        8776, "Operator is not in snapshot"
    );
  }

  record InvalidAccountToCloseDiscriminator(int code, String msg) implements JitoTipRouterError {

    public static final InvalidAccountToCloseDiscriminator INSTANCE = new InvalidAccountToCloseDiscriminator(
        8777, "Invalid account_to_close Discriminator"
    );
  }

  record CannotCloseAccount(int code, String msg) implements JitoTipRouterError {

    public static final CannotCloseAccount INSTANCE = new CannotCloseAccount(
        8778, "Cannot close account"
    );
  }

  record CannotCloseAccountAlreadyClosed(int code, String msg) implements JitoTipRouterError {

    public static final CannotCloseAccountAlreadyClosed INSTANCE = new CannotCloseAccountAlreadyClosed(
        8779, "Cannot close account - Already closed"
    );
  }

  record CannotCloseAccountNotEnoughEpochs(int code, String msg) implements JitoTipRouterError {

    public static final CannotCloseAccountNotEnoughEpochs INSTANCE = new CannotCloseAccountNotEnoughEpochs(
        8780, "Cannot close account - Not enough epochs have passed since consensus reached"
    );
  }

  record CannotCloseAccountNoReceiverProvided(int code, String msg) implements JitoTipRouterError {

    public static final CannotCloseAccountNoReceiverProvided INSTANCE = new CannotCloseAccountNoReceiverProvided(
        8781, "Cannot close account - No receiver provided"
    );
  }

  record CannotCloseEpochStateAccount(int code, String msg) implements JitoTipRouterError {

    public static final CannotCloseEpochStateAccount INSTANCE = new CannotCloseEpochStateAccount(
        8782, "Cannot close epoch state account - Epoch state needs all other accounts to be closed first"
    );
  }

  record InvalidDaoWallet(int code, String msg) implements JitoTipRouterError {

    public static final InvalidDaoWallet INSTANCE = new InvalidDaoWallet(
        8783, "Invalid DAO wallet"
    );
  }

  record EpochIsClosingDown(int code, String msg) implements JitoTipRouterError {

    public static final EpochIsClosingDown INSTANCE = new EpochIsClosingDown(
        8784, "Epoch is closing down"
    );
  }

  record MarkerExists(int code, String msg) implements JitoTipRouterError {

    public static final MarkerExists INSTANCE = new MarkerExists(
        8785, "Marker exists"
    );
  }
}
