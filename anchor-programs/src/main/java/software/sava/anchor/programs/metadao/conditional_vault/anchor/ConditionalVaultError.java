package software.sava.anchor.programs.metadao.conditional_vault.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface ConditionalVaultError extends ProgramError permits
    ConditionalVaultError.AssertFailed,
    ConditionalVaultError.InsufficientUnderlyingTokens,
    ConditionalVaultError.InsufficientConditionalTokens,
    ConditionalVaultError.InvalidVaultUnderlyingTokenAccount,
    ConditionalVaultError.InvalidConditionalTokenMint,
    ConditionalVaultError.CantRedeemConditionalTokens,
    ConditionalVaultError.InsufficientNumConditions,
    ConditionalVaultError.InvalidNumPayoutNumerators,
    ConditionalVaultError.InvalidConditionals,
    ConditionalVaultError.ConditionalMintMismatch,
    ConditionalVaultError.BadConditionalMint,
    ConditionalVaultError.BadConditionalTokenAccount,
    ConditionalVaultError.ConditionalTokenMintMismatch,
    ConditionalVaultError.PayoutZero,
    ConditionalVaultError.QuestionAlreadyResolved,
    ConditionalVaultError.ConditionalTokenMetadataAlreadySet,
    ConditionalVaultError.UnauthorizedConditionalTokenAccount,
    ConditionalVaultError.InvalidPayoutNumerators,
    ConditionalVaultError.TooManyOutcomes {

  static ConditionalVaultError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> AssertFailed.INSTANCE;
      case 6001 -> InsufficientUnderlyingTokens.INSTANCE;
      case 6002 -> InsufficientConditionalTokens.INSTANCE;
      case 6003 -> InvalidVaultUnderlyingTokenAccount.INSTANCE;
      case 6004 -> InvalidConditionalTokenMint.INSTANCE;
      case 6005 -> CantRedeemConditionalTokens.INSTANCE;
      case 6006 -> InsufficientNumConditions.INSTANCE;
      case 6007 -> InvalidNumPayoutNumerators.INSTANCE;
      case 6008 -> InvalidConditionals.INSTANCE;
      case 6009 -> ConditionalMintMismatch.INSTANCE;
      case 6010 -> BadConditionalMint.INSTANCE;
      case 6011 -> BadConditionalTokenAccount.INSTANCE;
      case 6012 -> ConditionalTokenMintMismatch.INSTANCE;
      case 6013 -> PayoutZero.INSTANCE;
      case 6014 -> QuestionAlreadyResolved.INSTANCE;
      case 6015 -> ConditionalTokenMetadataAlreadySet.INSTANCE;
      case 6016 -> UnauthorizedConditionalTokenAccount.INSTANCE;
      case 6017 -> InvalidPayoutNumerators.INSTANCE;
      case 6018 -> TooManyOutcomes.INSTANCE;
      default -> throw new IllegalStateException("Unexpected ConditionalVault error code: " + errorCode);
    };
  }

  record AssertFailed(int code, String msg) implements ConditionalVaultError {

    public static final AssertFailed INSTANCE = new AssertFailed(
        6000, "An assertion failed"
    );
  }

  record InsufficientUnderlyingTokens(int code, String msg) implements ConditionalVaultError {

    public static final InsufficientUnderlyingTokens INSTANCE = new InsufficientUnderlyingTokens(
        6001, "Insufficient underlying token balance to mint this amount of conditional tokens"
    );
  }

  record InsufficientConditionalTokens(int code, String msg) implements ConditionalVaultError {

    public static final InsufficientConditionalTokens INSTANCE = new InsufficientConditionalTokens(
        6002, "Insufficient conditional token balance to merge this `amount`"
    );
  }

  record InvalidVaultUnderlyingTokenAccount(int code, String msg) implements ConditionalVaultError {

    public static final InvalidVaultUnderlyingTokenAccount INSTANCE = new InvalidVaultUnderlyingTokenAccount(
        6003, "This `vault_underlying_token_account` is not this vault's `underlying_token_account`"
    );
  }

  record InvalidConditionalTokenMint(int code, String msg) implements ConditionalVaultError {

    public static final InvalidConditionalTokenMint INSTANCE = new InvalidConditionalTokenMint(
        6004, "This conditional token mint is not this vault's conditional token mint"
    );
  }

  record CantRedeemConditionalTokens(int code, String msg) implements ConditionalVaultError {

    public static final CantRedeemConditionalTokens INSTANCE = new CantRedeemConditionalTokens(
        6005, "Question needs to be resolved before users can redeem conditional tokens for underlying tokens"
    );
  }

  record InsufficientNumConditions(int code, String msg) implements ConditionalVaultError {

    public static final InsufficientNumConditions INSTANCE = new InsufficientNumConditions(
        6006, "Questions need 2 or more conditions"
    );
  }

  record InvalidNumPayoutNumerators(int code, String msg) implements ConditionalVaultError {

    public static final InvalidNumPayoutNumerators INSTANCE = new InvalidNumPayoutNumerators(
        6007, "Invalid number of payout numerators"
    );
  }

  record InvalidConditionals(int code, String msg) implements ConditionalVaultError {

    public static final InvalidConditionals INSTANCE = new InvalidConditionals(
        6008, "Client needs to pass in the list of conditional mints for a vault followed by the user's token accounts for those tokens"
    );
  }

  record ConditionalMintMismatch(int code, String msg) implements ConditionalVaultError {

    public static final ConditionalMintMismatch INSTANCE = new ConditionalMintMismatch(
        6009, "Conditional mint not in vault"
    );
  }

  record BadConditionalMint(int code, String msg) implements ConditionalVaultError {

    public static final BadConditionalMint INSTANCE = new BadConditionalMint(
        6010, "Unable to deserialize a conditional token mint"
    );
  }

  record BadConditionalTokenAccount(int code, String msg) implements ConditionalVaultError {

    public static final BadConditionalTokenAccount INSTANCE = new BadConditionalTokenAccount(
        6011, "Unable to deserialize a conditional token account"
    );
  }

  record ConditionalTokenMintMismatch(int code, String msg) implements ConditionalVaultError {

    public static final ConditionalTokenMintMismatch INSTANCE = new ConditionalTokenMintMismatch(
        6012, "User conditional token account mint does not match conditional mint"
    );
  }

  record PayoutZero(int code, String msg) implements ConditionalVaultError {

    public static final PayoutZero INSTANCE = new PayoutZero(
        6013, "Payouts must sum to 1 or more"
    );
  }

  record QuestionAlreadyResolved(int code, String msg) implements ConditionalVaultError {

    public static final QuestionAlreadyResolved INSTANCE = new QuestionAlreadyResolved(
        6014, "Question already resolved"
    );
  }

  record ConditionalTokenMetadataAlreadySet(int code, String msg) implements ConditionalVaultError {

    public static final ConditionalTokenMetadataAlreadySet INSTANCE = new ConditionalTokenMetadataAlreadySet(
        6015, "Conditional token metadata already set"
    );
  }

  record UnauthorizedConditionalTokenAccount(int code, String msg) implements ConditionalVaultError {

    public static final UnauthorizedConditionalTokenAccount INSTANCE = new UnauthorizedConditionalTokenAccount(
        6016, "Conditional token account is not owned by the authority"
    );
  }

  record InvalidPayoutNumerators(int code, String msg) implements ConditionalVaultError {

    public static final InvalidPayoutNumerators INSTANCE = new InvalidPayoutNumerators(
        6017, "Payout numerators are too large, causing an overflow"
    );
  }

  record TooManyOutcomes(int code, String msg) implements ConditionalVaultError {

    public static final TooManyOutcomes INSTANCE = new TooManyOutcomes(
        6018, "Questions can only have up to 10 outcomes"
    );
  }
}
