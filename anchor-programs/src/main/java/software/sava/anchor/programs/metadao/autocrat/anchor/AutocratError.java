package software.sava.anchor.programs.metadao.autocrat.anchor;

import software.sava.anchor.ProgramError;

public sealed interface AutocratError extends ProgramError permits
    AutocratError.AmmTooOld,
    AutocratError.InvalidInitialObservation,
    AutocratError.InvalidMaxObservationChange,
    AutocratError.InvalidSettlementAuthority,
    AutocratError.ProposalTooYoung,
    AutocratError.MarketsTooYoung,
    AutocratError.ProposalAlreadyFinalized,
    AutocratError.InvalidVaultNonce,
    AutocratError.ProposalNotPassed,
    AutocratError.InsufficientLpTokenBalance,
    AutocratError.InsufficientLpTokenLock {

  static AutocratError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> AmmTooOld.INSTANCE;
      case 6001 -> InvalidInitialObservation.INSTANCE;
      case 6002 -> InvalidMaxObservationChange.INSTANCE;
      case 6003 -> InvalidSettlementAuthority.INSTANCE;
      case 6004 -> ProposalTooYoung.INSTANCE;
      case 6005 -> MarketsTooYoung.INSTANCE;
      case 6006 -> ProposalAlreadyFinalized.INSTANCE;
      case 6007 -> InvalidVaultNonce.INSTANCE;
      case 6008 -> ProposalNotPassed.INSTANCE;
      case 6009 -> InsufficientLpTokenBalance.INSTANCE;
      case 6010 -> InsufficientLpTokenLock.INSTANCE;
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

  record InvalidSettlementAuthority(int code, String msg) implements AutocratError {

    public static final InvalidSettlementAuthority INSTANCE = new InvalidSettlementAuthority(
        6003, "One of the vaults has an invalid `settlement_authority`"
    );
  }

  record ProposalTooYoung(int code, String msg) implements AutocratError {

    public static final ProposalTooYoung INSTANCE = new ProposalTooYoung(
        6004, "Proposal is too young to be executed or rejected"
    );
  }

  record MarketsTooYoung(int code, String msg) implements AutocratError {

    public static final MarketsTooYoung INSTANCE = new MarketsTooYoung(
        6005, "Markets too young for proposal to be finalized. TWAP might need to be cranked"
    );
  }

  record ProposalAlreadyFinalized(int code, String msg) implements AutocratError {

    public static final ProposalAlreadyFinalized INSTANCE = new ProposalAlreadyFinalized(
        6006, "This proposal has already been finalized"
    );
  }

  record InvalidVaultNonce(int code, String msg) implements AutocratError {

    public static final InvalidVaultNonce INSTANCE = new InvalidVaultNonce(
        6007, "A conditional vault has an invalid nonce. A nonce should encode the proposal number"
    );
  }

  record ProposalNotPassed(int code, String msg) implements AutocratError {

    public static final ProposalNotPassed INSTANCE = new ProposalNotPassed(
        6008, "This proposal can't be executed because it isn't in the passed state"
    );
  }

  record InsufficientLpTokenBalance(int code, String msg) implements AutocratError {

    public static final InsufficientLpTokenBalance INSTANCE = new InsufficientLpTokenBalance(
        6009, "The proposer has fewer pass or fail LP tokens than they requested to lock"
    );
  }

  record InsufficientLpTokenLock(int code, String msg) implements AutocratError {

    public static final InsufficientLpTokenLock INSTANCE = new InsufficientLpTokenLock(
        6010, "The LP tokens passed in have less liquidity than the DAO's `min_quote_futarchic_liquidity` or `min_base_futachic_liquidity`"
    );
  }
}
