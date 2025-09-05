package software.sava.anchor.programs.pyth.receiver.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface PythSolanaReceiverError extends ProgramError permits
    PythSolanaReceiverError.InvalidWormholeMessage,
    PythSolanaReceiverError.DeserializeMessageFailed,
    PythSolanaReceiverError.InvalidPriceUpdate,
    PythSolanaReceiverError.UnsupportedMessageType,
    PythSolanaReceiverError.InvalidDataSource,
    PythSolanaReceiverError.InsufficientFunds,
    PythSolanaReceiverError.FeedIdMismatch,
    PythSolanaReceiverError.ExponentMismatch,
    PythSolanaReceiverError.InvalidTwapSlots,
    PythSolanaReceiverError.InvalidTwapStartMessage,
    PythSolanaReceiverError.InvalidTwapEndMessage,
    PythSolanaReceiverError.TwapCalculationOverflow,
    PythSolanaReceiverError.WrongWriteAuthority,
    PythSolanaReceiverError.WrongVaaOwner,
    PythSolanaReceiverError.DeserializeVaaFailed,
    PythSolanaReceiverError.InsufficientGuardianSignatures,
    PythSolanaReceiverError.InvalidVaaVersion,
    PythSolanaReceiverError.GuardianSetMismatch,
    PythSolanaReceiverError.InvalidGuardianOrder,
    PythSolanaReceiverError.InvalidGuardianIndex,
    PythSolanaReceiverError.InvalidSignature,
    PythSolanaReceiverError.InvalidGuardianKeyRecovery,
    PythSolanaReceiverError.WrongGuardianSetOwner,
    PythSolanaReceiverError.InvalidGuardianSetPda,
    PythSolanaReceiverError.GuardianSetExpired,
    PythSolanaReceiverError.GovernanceAuthorityMismatch,
    PythSolanaReceiverError.TargetGovernanceAuthorityMismatch,
    PythSolanaReceiverError.NonexistentGovernanceAuthorityTransferRequest,
    PythSolanaReceiverError.ZeroMinimumSignatures {

  static PythSolanaReceiverError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidWormholeMessage.INSTANCE;
      case 6001 -> DeserializeMessageFailed.INSTANCE;
      case 6002 -> InvalidPriceUpdate.INSTANCE;
      case 6003 -> UnsupportedMessageType.INSTANCE;
      case 6004 -> InvalidDataSource.INSTANCE;
      case 6005 -> InsufficientFunds.INSTANCE;
      case 6006 -> FeedIdMismatch.INSTANCE;
      case 6007 -> ExponentMismatch.INSTANCE;
      case 6008 -> InvalidTwapSlots.INSTANCE;
      case 6009 -> InvalidTwapStartMessage.INSTANCE;
      case 6010 -> InvalidTwapEndMessage.INSTANCE;
      case 6011 -> TwapCalculationOverflow.INSTANCE;
      case 6012 -> WrongWriteAuthority.INSTANCE;
      case 6013 -> WrongVaaOwner.INSTANCE;
      case 6014 -> DeserializeVaaFailed.INSTANCE;
      case 6015 -> InsufficientGuardianSignatures.INSTANCE;
      case 6016 -> InvalidVaaVersion.INSTANCE;
      case 6017 -> GuardianSetMismatch.INSTANCE;
      case 6018 -> InvalidGuardianOrder.INSTANCE;
      case 6019 -> InvalidGuardianIndex.INSTANCE;
      case 6020 -> InvalidSignature.INSTANCE;
      case 6021 -> InvalidGuardianKeyRecovery.INSTANCE;
      case 6022 -> WrongGuardianSetOwner.INSTANCE;
      case 6023 -> InvalidGuardianSetPda.INSTANCE;
      case 6024 -> GuardianSetExpired.INSTANCE;
      case 6025 -> GovernanceAuthorityMismatch.INSTANCE;
      case 6026 -> TargetGovernanceAuthorityMismatch.INSTANCE;
      case 6027 -> NonexistentGovernanceAuthorityTransferRequest.INSTANCE;
      case 6028 -> ZeroMinimumSignatures.INSTANCE;
      default -> throw new IllegalStateException("Unexpected PythSolanaReceiver error code: " + errorCode);
    };
  }

  record InvalidWormholeMessage(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidWormholeMessage INSTANCE = new InvalidWormholeMessage(
        6000, "Received an invalid wormhole message"
    );
  }

  record DeserializeMessageFailed(int code, String msg) implements PythSolanaReceiverError {

    public static final DeserializeMessageFailed INSTANCE = new DeserializeMessageFailed(
        6001, "An error occurred when deserializing the message"
    );
  }

  record InvalidPriceUpdate(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidPriceUpdate INSTANCE = new InvalidPriceUpdate(
        6002, "Received an invalid price update"
    );
  }

  record UnsupportedMessageType(int code, String msg) implements PythSolanaReceiverError {

    public static final UnsupportedMessageType INSTANCE = new UnsupportedMessageType(
        6003, "This type of message is not supported currently"
    );
  }

  record InvalidDataSource(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidDataSource INSTANCE = new InvalidDataSource(
        6004, "The tuple emitter chain, emitter doesn't match one of the valid data sources."
    );
  }

  record InsufficientFunds(int code, String msg) implements PythSolanaReceiverError {

    public static final InsufficientFunds INSTANCE = new InsufficientFunds(
        6005, "Funds are insufficient to pay the receiving fee"
    );
  }

  record FeedIdMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final FeedIdMismatch INSTANCE = new FeedIdMismatch(
        6006, "Cannot calculate TWAP, end slot must be greater than start slot"
    );
  }

  record ExponentMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final ExponentMismatch INSTANCE = new ExponentMismatch(
        6007, "The start and end messages must have the same feed ID"
    );
  }

  record InvalidTwapSlots(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidTwapSlots INSTANCE = new InvalidTwapSlots(
        6008, "The start and end messages must have the same exponent"
    );
  }

  record InvalidTwapStartMessage(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidTwapStartMessage INSTANCE = new InvalidTwapStartMessage(
        6009, "Start message is not the first update for its timestamp"
    );
  }

  record InvalidTwapEndMessage(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidTwapEndMessage INSTANCE = new InvalidTwapEndMessage(
        6010, "End message is not the first update for its timestamp"
    );
  }

  record TwapCalculationOverflow(int code, String msg) implements PythSolanaReceiverError {

    public static final TwapCalculationOverflow INSTANCE = new TwapCalculationOverflow(
        6011, "Overflow in TWAP calculation"
    );
  }

  record WrongWriteAuthority(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongWriteAuthority INSTANCE = new WrongWriteAuthority(
        6012, "This signer can't write to price update account"
    );
  }

  record WrongVaaOwner(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongVaaOwner INSTANCE = new WrongVaaOwner(
        6013, "The posted VAA account has the wrong owner."
    );
  }

  record DeserializeVaaFailed(int code, String msg) implements PythSolanaReceiverError {

    public static final DeserializeVaaFailed INSTANCE = new DeserializeVaaFailed(
        6014, "An error occurred when deserializing the VAA."
    );
  }

  record InsufficientGuardianSignatures(int code, String msg) implements PythSolanaReceiverError {

    public static final InsufficientGuardianSignatures INSTANCE = new InsufficientGuardianSignatures(
        6015, "The number of guardian signatures is below the minimum"
    );
  }

  record InvalidVaaVersion(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidVaaVersion INSTANCE = new InvalidVaaVersion(
        6016, "Invalid VAA version"
    );
  }

  record GuardianSetMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final GuardianSetMismatch INSTANCE = new GuardianSetMismatch(
        6017, "Guardian set version in the VAA doesn't match the guardian set passed"
    );
  }

  record InvalidGuardianOrder(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianOrder INSTANCE = new InvalidGuardianOrder(
        6018, "Guardian signature indices must be increasing"
    );
  }

  record InvalidGuardianIndex(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianIndex INSTANCE = new InvalidGuardianIndex(
        6019, "Guardian index exceeds the number of guardians in the set"
    );
  }

  record InvalidSignature(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidSignature INSTANCE = new InvalidSignature(
        6020, "A VAA signature is invalid"
    );
  }

  record InvalidGuardianKeyRecovery(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianKeyRecovery INSTANCE = new InvalidGuardianKeyRecovery(
        6021, "The recovered guardian public key doesn't match the guardian set"
    );
  }

  record WrongGuardianSetOwner(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongGuardianSetOwner INSTANCE = new WrongGuardianSetOwner(
        6022, "The guardian set account is owned by the wrong program"
    );
  }

  record InvalidGuardianSetPda(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianSetPda INSTANCE = new InvalidGuardianSetPda(
        6023, "The Guardian Set account doesn't match the PDA derivation"
    );
  }

  record GuardianSetExpired(int code, String msg) implements PythSolanaReceiverError {

    public static final GuardianSetExpired INSTANCE = new GuardianSetExpired(
        6024, "The Guardian Set is expired"
    );
  }

  record GovernanceAuthorityMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final GovernanceAuthorityMismatch INSTANCE = new GovernanceAuthorityMismatch(
        6025, "The signer is not authorized to perform this governance action"
    );
  }

  record TargetGovernanceAuthorityMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final TargetGovernanceAuthorityMismatch INSTANCE = new TargetGovernanceAuthorityMismatch(
        6026, "The signer is not authorized to accept the governance authority"
    );
  }

  record NonexistentGovernanceAuthorityTransferRequest(int code, String msg) implements PythSolanaReceiverError {

    public static final NonexistentGovernanceAuthorityTransferRequest INSTANCE = new NonexistentGovernanceAuthorityTransferRequest(
        6027, "The governance authority needs to request a transfer first"
    );
  }

  record ZeroMinimumSignatures(int code, String msg) implements PythSolanaReceiverError {

    public static final ZeroMinimumSignatures INSTANCE = new ZeroMinimumSignatures(
        6028, "The minimum number of signatures should be at least 1"
    );
  }
}
