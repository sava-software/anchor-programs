package software.sava.anchor.programs.pyth.receiver.anchor;


import software.sava.anchor.programs._commons.ProgramError;

public sealed interface PythSolanaReceiverError extends ProgramError permits
    PythSolanaReceiverError.InvalidWormholeMessage,
    PythSolanaReceiverError.DeserializeMessageFailed,
    PythSolanaReceiverError.InvalidPriceUpdate,
    PythSolanaReceiverError.UnsupportedMessageType,
    PythSolanaReceiverError.InvalidDataSource,
    PythSolanaReceiverError.InsufficientFunds,
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
    PythSolanaReceiverError.NonexistentGovernanceAuthorityTransferRequest {

  static PythSolanaReceiverError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidWormholeMessage.INSTANCE;
      case 6001 -> DeserializeMessageFailed.INSTANCE;
      case 6002 -> InvalidPriceUpdate.INSTANCE;
      case 6003 -> UnsupportedMessageType.INSTANCE;
      case 6004 -> InvalidDataSource.INSTANCE;
      case 6005 -> InsufficientFunds.INSTANCE;
      case 6006 -> WrongWriteAuthority.INSTANCE;
      case 6007 -> WrongVaaOwner.INSTANCE;
      case 6008 -> DeserializeVaaFailed.INSTANCE;
      case 6009 -> InsufficientGuardianSignatures.INSTANCE;
      case 6010 -> InvalidVaaVersion.INSTANCE;
      case 6011 -> GuardianSetMismatch.INSTANCE;
      case 6012 -> InvalidGuardianOrder.INSTANCE;
      case 6013 -> InvalidGuardianIndex.INSTANCE;
      case 6014 -> InvalidSignature.INSTANCE;
      case 6015 -> InvalidGuardianKeyRecovery.INSTANCE;
      case 6016 -> WrongGuardianSetOwner.INSTANCE;
      case 6017 -> InvalidGuardianSetPda.INSTANCE;
      case 6018 -> GuardianSetExpired.INSTANCE;
      case 6019 -> GovernanceAuthorityMismatch.INSTANCE;
      case 6020 -> TargetGovernanceAuthorityMismatch.INSTANCE;
      case 6021 -> NonexistentGovernanceAuthorityTransferRequest.INSTANCE;
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

  record WrongWriteAuthority(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongWriteAuthority INSTANCE = new WrongWriteAuthority(
        6006, "This signer can't write to price update account"
    );
  }

  record WrongVaaOwner(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongVaaOwner INSTANCE = new WrongVaaOwner(
        6007, "The posted VAA account has the wrong owner."
    );
  }

  record DeserializeVaaFailed(int code, String msg) implements PythSolanaReceiverError {

    public static final DeserializeVaaFailed INSTANCE = new DeserializeVaaFailed(
        6008, "An error occurred when deserializing the VAA."
    );
  }

  record InsufficientGuardianSignatures(int code, String msg) implements PythSolanaReceiverError {

    public static final InsufficientGuardianSignatures INSTANCE = new InsufficientGuardianSignatures(
        6009, "The number of guardian signatures is below the minimum"
    );
  }

  record InvalidVaaVersion(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidVaaVersion INSTANCE = new InvalidVaaVersion(
        6010, "Invalid VAA version"
    );
  }

  record GuardianSetMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final GuardianSetMismatch INSTANCE = new GuardianSetMismatch(
        6011, "Guardian set version in the VAA doesn't match the guardian set passed"
    );
  }

  record InvalidGuardianOrder(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianOrder INSTANCE = new InvalidGuardianOrder(
        6012, "Guardian signature indices must be increasing"
    );
  }

  record InvalidGuardianIndex(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianIndex INSTANCE = new InvalidGuardianIndex(
        6013, "Guardian index exceeds the number of guardians in the set"
    );
  }

  record InvalidSignature(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidSignature INSTANCE = new InvalidSignature(
        6014, "A VAA signature is invalid"
    );
  }

  record InvalidGuardianKeyRecovery(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianKeyRecovery INSTANCE = new InvalidGuardianKeyRecovery(
        6015, "The recovered guardian public key doesn't match the guardian set"
    );
  }

  record WrongGuardianSetOwner(int code, String msg) implements PythSolanaReceiverError {

    public static final WrongGuardianSetOwner INSTANCE = new WrongGuardianSetOwner(
        6016, "The guardian set account is owned by the wrong program"
    );
  }

  record InvalidGuardianSetPda(int code, String msg) implements PythSolanaReceiverError {

    public static final InvalidGuardianSetPda INSTANCE = new InvalidGuardianSetPda(
        6017, "The Guardian Set account doesn't match the PDA derivation"
    );
  }

  record GuardianSetExpired(int code, String msg) implements PythSolanaReceiverError {

    public static final GuardianSetExpired INSTANCE = new GuardianSetExpired(
        6018, "The Guardian Set is expired"
    );
  }

  record GovernanceAuthorityMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final GovernanceAuthorityMismatch INSTANCE = new GovernanceAuthorityMismatch(
        6019, "The signer is not authorized to perform this governance action"
    );
  }

  record TargetGovernanceAuthorityMismatch(int code, String msg) implements PythSolanaReceiverError {

    public static final TargetGovernanceAuthorityMismatch INSTANCE = new TargetGovernanceAuthorityMismatch(
        6020, "The signer is not authorized to accept the governance authority"
    );
  }

  record NonexistentGovernanceAuthorityTransferRequest(int code, String msg) implements PythSolanaReceiverError {

    public static final NonexistentGovernanceAuthorityTransferRequest INSTANCE = new NonexistentGovernanceAuthorityTransferRequest(
        6021, "The governance authority needs to request a transfer first"
    );
  }
}
