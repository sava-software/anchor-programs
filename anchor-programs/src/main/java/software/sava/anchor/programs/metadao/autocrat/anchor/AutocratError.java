package software.sava.anchor.programs.metadao.autocrat.anchor;

import software.sava.anchor.ProgramError;

public sealed interface AutocratError extends ProgramError permits
    AutocratError.AmmTooOld,
    AutocratError.InvalidInitialObservation,
    AutocratError.InvalidMaxObservationChange,
    AutocratError.InvalidStartDelaySlots,
    AutocratError.InvalidSettlementAuthority,
    AutocratError.ProposalTooYoung,
    AutocratError.MarketsTooYoung,
    AutocratError.ProposalAlreadyFinalized,
    AutocratError.InvalidVaultNonce,
    AutocratError.ProposalNotPassed,
    AutocratError.InsufficientLpTokenBalance,
    AutocratError.InsufficientLpTokenLock,
    AutocratError.ProposalDurationTooShort,
    AutocratError.QuestionMustBeBinary,
    AutocratError.InvalidSquadsProposalStatus {

  static AutocratError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> AmmTooOld.INSTANCE;
      case 6001 -> InvalidInitialObservation.INSTANCE;
      case 6002 -> InvalidMaxObservationChange.INSTANCE;
      case 6003 -> InvalidStartDelaySlots.INSTANCE;
      case 6004 -> InvalidSettlementAuthority.INSTANCE;
      case 6005 -> ProposalTooYoung.INSTANCE;
      case 6006 -> MarketsTooYoung.INSTANCE;
      case 6007 -> ProposalAlreadyFinalized.INSTANCE;
      case 6008 -> InvalidVaultNonce.INSTANCE;
      case 6009 -> ProposalNotPassed.INSTANCE;
      case 6010 -> InsufficientLpTokenBalance.INSTANCE;
      case 6011 -> InsufficientLpTokenLock.INSTANCE;
      case 6012 -> ProposalDurationTooShort.INSTANCE;
      case 6013 -> QuestionMustBeBinary.INSTANCE;
      case 6014 -> InvalidSquadsProposalStatus.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Autocrat error code: " + errorCode);
    };
  }

  record AmmTooOld(int code, String msg) implements AutocratError {

    public static final AmmTooOld INSTANCE = new AmmTooOld(
        6000, "Amms must have been created within 5 minutes (counted in slots) of proposal initialization"
    );
  }

  record InvalidInitialObservation(int code, String msg) implements AutocratError {

    public static final InvalidInitialObservation INSTANCE = new InvalidInitialObservation(
        6001, "An amm has an `initial_observation` that doesn't match the `dao`'s config"
    );
  }

  record InvalidMaxObservationChange(int code, String msg) implements AutocratError {

    public static final InvalidMaxObservationChange INSTANCE = new InvalidMaxObservationChange(
        6002, "An amm has a `max_observation_change_per_update` that doesn't match the `dao`'s config"
    );
  }

  record InvalidStartDelaySlots(int code, String msg) implements AutocratError {

    public static final InvalidStartDelaySlots INSTANCE = new InvalidStartDelaySlots(
        6003, "An amm has a `start_delay_slots` that doesn't match the `dao`'s config"
    );
  }

  record InvalidSettlementAuthority(int code, String msg) implements AutocratError {

    public static final InvalidSettlementAuthority INSTANCE = new InvalidSettlementAuthority(
        6004, "One of the vaults has an invalid `settlement_authority`"
    );
  }

  record ProposalTooYoung(int code, String msg) implements AutocratError {

    public static final ProposalTooYoung INSTANCE = new ProposalTooYoung(
        6005, "Proposal is too young to be executed or rejected"
    );
  }

  record MarketsTooYoung(int code, String msg) implements AutocratError {

    public static final MarketsTooYoung INSTANCE = new MarketsTooYoung(
        6006, "Markets too young for proposal to be finalized. TWAP might need to be cranked"
    );
  }

  record ProposalAlreadyFinalized(int code, String msg) implements AutocratError {

    public static final ProposalAlreadyFinalized INSTANCE = new ProposalAlreadyFinalized(
        6007, "This proposal has already been finalized"
    );
  }

  record InvalidVaultNonce(int code, String msg) implements AutocratError {

    public static final InvalidVaultNonce INSTANCE = new InvalidVaultNonce(
        6008, "A conditional vault has an invalid nonce. A nonce should encode the proposal number"
    );
  }

  record ProposalNotPassed(int code, String msg) implements AutocratError {

    public static final ProposalNotPassed INSTANCE = new ProposalNotPassed(
        6009, "This proposal can't be executed because it isn't in the passed state"
    );
  }

  record InsufficientLpTokenBalance(int code, String msg) implements AutocratError {

    public static final InsufficientLpTokenBalance INSTANCE = new InsufficientLpTokenBalance(
        6010, "The proposer has fewer pass or fail LP tokens than they requested to lock"
    );
  }

  record InsufficientLpTokenLock(int code, String msg) implements AutocratError {

    public static final InsufficientLpTokenLock INSTANCE = new InsufficientLpTokenLock(
        6011, "The LP tokens passed in have less liquidity than the DAO's `min_quote_futarchic_liquidity` or `min_base_futachic_liquidity`"
    );
  }

  record ProposalDurationTooShort(int code, String msg) implements AutocratError {

    public static final ProposalDurationTooShort INSTANCE = new ProposalDurationTooShort(
        6012, "Proposal duration must be longer than TWAP start delay"
    );
  }

  record QuestionMustBeBinary(int code, String msg) implements AutocratError {

    public static final QuestionMustBeBinary INSTANCE = new QuestionMustBeBinary(
        6013, "Question must have exactly 2 outcomes for binary futarchy"
    );
  }

  record InvalidSquadsProposalStatus(int code, String msg) implements AutocratError {

    public static final InvalidSquadsProposalStatus INSTANCE = new InvalidSquadsProposalStatus(
        6014, "Squads proposal must be in Draft status"
    );
  }
}
