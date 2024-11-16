package software.sava.anchor.programs.jupiter.governance.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GovernError extends ProgramError permits
    GovernError.InvalidVoteSide,
    GovernError.InvalidProposalType,
    GovernError.GovernorNotFound,
    GovernError.VotingDelayNotMet,
    GovernError.ProposalNotDraft,
    GovernError.ProposalNotActive,
    GovernError.InvalidMaxOption,
    GovernError.NotYesNoProposal,
    GovernError.NotOptionProposal,
    GovernError.InvalidOptionDescriptions {

  static GovernError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidVoteSide.INSTANCE;
      case 6001 -> InvalidProposalType.INSTANCE;
      case 6002 -> GovernorNotFound.INSTANCE;
      case 6003 -> VotingDelayNotMet.INSTANCE;
      case 6004 -> ProposalNotDraft.INSTANCE;
      case 6005 -> ProposalNotActive.INSTANCE;
      case 6006 -> InvalidMaxOption.INSTANCE;
      case 6007 -> NotYesNoProposal.INSTANCE;
      case 6008 -> NotOptionProposal.INSTANCE;
      case 6009 -> InvalidOptionDescriptions.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Govern error code: " + errorCode);
    };
  }

  record InvalidVoteSide(int code, String msg) implements GovernError {

    public static final InvalidVoteSide INSTANCE = new InvalidVoteSide(
        6000, "Invalid vote side."
    );
  }

  record InvalidProposalType(int code, String msg) implements GovernError {

    public static final InvalidProposalType INSTANCE = new InvalidProposalType(
        6001, "Invalid proposal type."
    );
  }

  record GovernorNotFound(int code, String msg) implements GovernError {

    public static final GovernorNotFound INSTANCE = new GovernorNotFound(
        6002, "The owner of the smart wallet doesn't match with current."
    );
  }

  record VotingDelayNotMet(int code, String msg) implements GovernError {

    public static final VotingDelayNotMet INSTANCE = new VotingDelayNotMet(
        6003, "The proposal cannot be activated since it has not yet passed the voting delay."
    );
  }

  record ProposalNotDraft(int code, String msg) implements GovernError {

    public static final ProposalNotDraft INSTANCE = new ProposalNotDraft(
        6004, "Only drafts can be canceled."
    );
  }

  record ProposalNotActive(int code, String msg) implements GovernError {

    public static final ProposalNotActive INSTANCE = new ProposalNotActive(
        6005, "The proposal must be active."
    );
  }

  record InvalidMaxOption(int code, String msg) implements GovernError {

    public static final InvalidMaxOption INSTANCE = new InvalidMaxOption(
        6006, "Max option is invalid"
    );
  }

  record NotYesNoProposal(int code, String msg) implements GovernError {

    public static final NotYesNoProposal INSTANCE = new NotYesNoProposal(
        6007, "Proposal is not YesNo."
    );
  }

  record NotOptionProposal(int code, String msg) implements GovernError {

    public static final NotOptionProposal INSTANCE = new NotOptionProposal(
        6008, "Proposal is not Option."
    );
  }

  record InvalidOptionDescriptions(int code, String msg) implements GovernError {

    public static final InvalidOptionDescriptions INSTANCE = new InvalidOptionDescriptions(
        6009, "Invalid option descriptions."
    );
  }
}
