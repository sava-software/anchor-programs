package software.sava.anchor.programs.jito.tip_distribution.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface JitoTipDistributionError extends ProgramError permits
    JitoTipDistributionError.AccountValidationFailure,
    JitoTipDistributionError.ArithmeticError,
    JitoTipDistributionError.ExceedsMaxClaim,
    JitoTipDistributionError.ExceedsMaxNumNodes,
    JitoTipDistributionError.ExpiredTipDistributionAccount,
    JitoTipDistributionError.FundsAlreadyClaimed,
    JitoTipDistributionError.InvalidParameters,
    JitoTipDistributionError.InvalidProof,
    JitoTipDistributionError.InvalidVoteAccountData,
    JitoTipDistributionError.MaxValidatorCommissionFeeBpsExceeded,
    JitoTipDistributionError.PrematureCloseTipDistributionAccount,
    JitoTipDistributionError.PrematureCloseClaimStatus,
    JitoTipDistributionError.PrematureMerkleRootUpload,
    JitoTipDistributionError.RootNotUploaded,
    JitoTipDistributionError.Unauthorized,
    JitoTipDistributionError.InvalidTdaForMigration {

  static JitoTipDistributionError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> AccountValidationFailure.INSTANCE;
      case 6001 -> ArithmeticError.INSTANCE;
      case 6002 -> ExceedsMaxClaim.INSTANCE;
      case 6003 -> ExceedsMaxNumNodes.INSTANCE;
      case 6004 -> ExpiredTipDistributionAccount.INSTANCE;
      case 6005 -> FundsAlreadyClaimed.INSTANCE;
      case 6006 -> InvalidParameters.INSTANCE;
      case 6007 -> InvalidProof.INSTANCE;
      case 6008 -> InvalidVoteAccountData.INSTANCE;
      case 6009 -> MaxValidatorCommissionFeeBpsExceeded.INSTANCE;
      case 6010 -> PrematureCloseTipDistributionAccount.INSTANCE;
      case 6011 -> PrematureCloseClaimStatus.INSTANCE;
      case 6012 -> PrematureMerkleRootUpload.INSTANCE;
      case 6013 -> RootNotUploaded.INSTANCE;
      case 6014 -> Unauthorized.INSTANCE;
      case 6015 -> InvalidTdaForMigration.INSTANCE;
      default -> throw new IllegalStateException("Unexpected JitoTipDistribution error code: " + errorCode);
    };
  }

  record AccountValidationFailure(int code, String msg) implements JitoTipDistributionError {

    public static final AccountValidationFailure INSTANCE = new AccountValidationFailure(
        6000, "Account failed validation."
    );
  }

  record ArithmeticError(int code, String msg) implements JitoTipDistributionError {

    public static final ArithmeticError INSTANCE = new ArithmeticError(
        6001, "Encountered an arithmetic under/overflow error."
    );
  }

  record ExceedsMaxClaim(int code, String msg) implements JitoTipDistributionError {

    public static final ExceedsMaxClaim INSTANCE = new ExceedsMaxClaim(
        6002, "The maximum number of funds to be claimed has been exceeded."
    );
  }

  record ExceedsMaxNumNodes(int code, String msg) implements JitoTipDistributionError {

    public static final ExceedsMaxNumNodes INSTANCE = new ExceedsMaxNumNodes(
        6003, "The maximum number of claims has been exceeded."
    );
  }

  record ExpiredTipDistributionAccount(int code, String msg) implements JitoTipDistributionError {

    public static final ExpiredTipDistributionAccount INSTANCE = new ExpiredTipDistributionAccount(
        6004, "The given TipDistributionAccount has expired."
    );
  }

  record FundsAlreadyClaimed(int code, String msg) implements JitoTipDistributionError {

    public static final FundsAlreadyClaimed INSTANCE = new FundsAlreadyClaimed(
        6005, "The funds for the given index and TipDistributionAccount have already been claimed."
    );
  }

  record InvalidParameters(int code, String msg) implements JitoTipDistributionError {

    public static final InvalidParameters INSTANCE = new InvalidParameters(
        6006, "Supplied invalid parameters."
    );
  }

  record InvalidProof(int code, String msg) implements JitoTipDistributionError {

    public static final InvalidProof INSTANCE = new InvalidProof(
        6007, "The given proof is invalid."
    );
  }

  record InvalidVoteAccountData(int code, String msg) implements JitoTipDistributionError {

    public static final InvalidVoteAccountData INSTANCE = new InvalidVoteAccountData(
        6008, "Failed to deserialize the supplied vote account data."
    );
  }

  record MaxValidatorCommissionFeeBpsExceeded(int code, String msg) implements JitoTipDistributionError {

    public static final MaxValidatorCommissionFeeBpsExceeded INSTANCE = new MaxValidatorCommissionFeeBpsExceeded(
        6009, "Validator's commission basis points must be less than or equal to the Config account's max_validator_commission_bps."
    );
  }

  record PrematureCloseTipDistributionAccount(int code, String msg) implements JitoTipDistributionError {

    public static final PrematureCloseTipDistributionAccount INSTANCE = new PrematureCloseTipDistributionAccount(
        6010, "The given TipDistributionAccount is not ready to be closed."
    );
  }

  record PrematureCloseClaimStatus(int code, String msg) implements JitoTipDistributionError {

    public static final PrematureCloseClaimStatus INSTANCE = new PrematureCloseClaimStatus(
        6011, "The given ClaimStatus account is not ready to be closed."
    );
  }

  record PrematureMerkleRootUpload(int code, String msg) implements JitoTipDistributionError {

    public static final PrematureMerkleRootUpload INSTANCE = new PrematureMerkleRootUpload(
        6012, "Must wait till at least one epoch after the tip distribution account was created to upload the merkle root."
    );
  }

  record RootNotUploaded(int code, String msg) implements JitoTipDistributionError {

    public static final RootNotUploaded INSTANCE = new RootNotUploaded(
        6013, "No merkle root has been uploaded to the given TipDistributionAccount."
    );
  }

  record Unauthorized(int code, String msg) implements JitoTipDistributionError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6014, "Unauthorized signer."
    );
  }

  record InvalidTdaForMigration(int code, String msg) implements JitoTipDistributionError {

    public static final InvalidTdaForMigration INSTANCE = new InvalidTdaForMigration(
        6015, "TDA not valid for migration."
    );
  }
}
