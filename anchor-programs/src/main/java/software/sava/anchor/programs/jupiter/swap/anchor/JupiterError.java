package software.sava.anchor.programs.jupiter.swap.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface JupiterError extends ProgramError permits
    JupiterError.EmptyRoute,
    JupiterError.SlippageToleranceExceeded,
    JupiterError.InvalidCalculation,
    JupiterError.MissingPlatformFeeAccount,
    JupiterError.InvalidSlippage,
    JupiterError.NotEnoughPercent,
    JupiterError.InvalidInputIndex,
    JupiterError.InvalidOutputIndex,
    JupiterError.NotEnoughAccountKeys,
    JupiterError.NonZeroMinimumOutAmountNotSupported,
    JupiterError.InvalidRoutePlan,
    JupiterError.InvalidReferralAuthority,
    JupiterError.LedgerTokenAccountDoesNotMatch,
    JupiterError.InvalidTokenLedger,
    JupiterError.IncorrectTokenProgramID,
    JupiterError.TokenProgramNotProvided,
    JupiterError.SwapNotSupported,
    JupiterError.ExactOutAmountNotMatched,
    JupiterError.SourceAndDestinationMintCannotBeTheSame,
    JupiterError.InvalidMint,
    JupiterError.InvalidProgramAuthority,
    JupiterError.InvalidOutputTokenAccount,
    JupiterError.InvalidFeeWallet,
    JupiterError.InvalidAuthority,
    JupiterError.InsufficientFunds,
    JupiterError.InvalidTokenAccount,
    JupiterError.BondingCurveAlreadyCompleted {

  static JupiterError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> EmptyRoute.INSTANCE;
      case 6001 -> SlippageToleranceExceeded.INSTANCE;
      case 6002 -> InvalidCalculation.INSTANCE;
      case 6003 -> MissingPlatformFeeAccount.INSTANCE;
      case 6004 -> InvalidSlippage.INSTANCE;
      case 6005 -> NotEnoughPercent.INSTANCE;
      case 6006 -> InvalidInputIndex.INSTANCE;
      case 6007 -> InvalidOutputIndex.INSTANCE;
      case 6008 -> NotEnoughAccountKeys.INSTANCE;
      case 6009 -> NonZeroMinimumOutAmountNotSupported.INSTANCE;
      case 6010 -> InvalidRoutePlan.INSTANCE;
      case 6011 -> InvalidReferralAuthority.INSTANCE;
      case 6012 -> LedgerTokenAccountDoesNotMatch.INSTANCE;
      case 6013 -> InvalidTokenLedger.INSTANCE;
      case 6014 -> IncorrectTokenProgramID.INSTANCE;
      case 6015 -> TokenProgramNotProvided.INSTANCE;
      case 6016 -> SwapNotSupported.INSTANCE;
      case 6017 -> ExactOutAmountNotMatched.INSTANCE;
      case 6018 -> SourceAndDestinationMintCannotBeTheSame.INSTANCE;
      case 6019 -> InvalidMint.INSTANCE;
      case 6020 -> InvalidProgramAuthority.INSTANCE;
      case 6021 -> InvalidOutputTokenAccount.INSTANCE;
      case 6022 -> InvalidFeeWallet.INSTANCE;
      case 6023 -> InvalidAuthority.INSTANCE;
      case 6024 -> InsufficientFunds.INSTANCE;
      case 6025 -> InvalidTokenAccount.INSTANCE;
      case 6026 -> BondingCurveAlreadyCompleted.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Jupiter error code: " + errorCode);
    };
  }

  record EmptyRoute(int code, String msg) implements JupiterError {

    public static final EmptyRoute INSTANCE = new EmptyRoute(
        6000, "Empty route"
    );
  }

  record SlippageToleranceExceeded(int code, String msg) implements JupiterError {

    public static final SlippageToleranceExceeded INSTANCE = new SlippageToleranceExceeded(
        6001, "Slippage tolerance exceeded"
    );
  }

  record InvalidCalculation(int code, String msg) implements JupiterError {

    public static final InvalidCalculation INSTANCE = new InvalidCalculation(
        6002, "Invalid calculation"
    );
  }

  record MissingPlatformFeeAccount(int code, String msg) implements JupiterError {

    public static final MissingPlatformFeeAccount INSTANCE = new MissingPlatformFeeAccount(
        6003, "Missing platform fee account"
    );
  }

  record InvalidSlippage(int code, String msg) implements JupiterError {

    public static final InvalidSlippage INSTANCE = new InvalidSlippage(
        6004, "Invalid slippage"
    );
  }

  record NotEnoughPercent(int code, String msg) implements JupiterError {

    public static final NotEnoughPercent INSTANCE = new NotEnoughPercent(
        6005, "Not enough percent to 100"
    );
  }

  record InvalidInputIndex(int code, String msg) implements JupiterError {

    public static final InvalidInputIndex INSTANCE = new InvalidInputIndex(
        6006, "Token input index is invalid"
    );
  }

  record InvalidOutputIndex(int code, String msg) implements JupiterError {

    public static final InvalidOutputIndex INSTANCE = new InvalidOutputIndex(
        6007, "Token output index is invalid"
    );
  }

  record NotEnoughAccountKeys(int code, String msg) implements JupiterError {

    public static final NotEnoughAccountKeys INSTANCE = new NotEnoughAccountKeys(
        6008, "Not Enough Account keys"
    );
  }

  record NonZeroMinimumOutAmountNotSupported(int code, String msg) implements JupiterError {

    public static final NonZeroMinimumOutAmountNotSupported INSTANCE = new NonZeroMinimumOutAmountNotSupported(
        6009, "Non zero minimum out amount not supported"
    );
  }

  record InvalidRoutePlan(int code, String msg) implements JupiterError {

    public static final InvalidRoutePlan INSTANCE = new InvalidRoutePlan(
        6010, "Invalid route plan"
    );
  }

  record InvalidReferralAuthority(int code, String msg) implements JupiterError {

    public static final InvalidReferralAuthority INSTANCE = new InvalidReferralAuthority(
        6011, "Invalid referral authority"
    );
  }

  record LedgerTokenAccountDoesNotMatch(int code, String msg) implements JupiterError {

    public static final LedgerTokenAccountDoesNotMatch INSTANCE = new LedgerTokenAccountDoesNotMatch(
        6012, "Token account doesn't match the ledger"
    );
  }

  record InvalidTokenLedger(int code, String msg) implements JupiterError {

    public static final InvalidTokenLedger INSTANCE = new InvalidTokenLedger(
        6013, "Invalid token ledger"
    );
  }

  record IncorrectTokenProgramID(int code, String msg) implements JupiterError {

    public static final IncorrectTokenProgramID INSTANCE = new IncorrectTokenProgramID(
        6014, "Token program ID is invalid"
    );
  }

  record TokenProgramNotProvided(int code, String msg) implements JupiterError {

    public static final TokenProgramNotProvided INSTANCE = new TokenProgramNotProvided(
        6015, "Token program not provided"
    );
  }

  record SwapNotSupported(int code, String msg) implements JupiterError {

    public static final SwapNotSupported INSTANCE = new SwapNotSupported(
        6016, "Swap not supported"
    );
  }

  record ExactOutAmountNotMatched(int code, String msg) implements JupiterError {

    public static final ExactOutAmountNotMatched INSTANCE = new ExactOutAmountNotMatched(
        6017, "Exact out amount doesn't match"
    );
  }

  record SourceAndDestinationMintCannotBeTheSame(int code, String msg) implements JupiterError {

    public static final SourceAndDestinationMintCannotBeTheSame INSTANCE = new SourceAndDestinationMintCannotBeTheSame(
        6018, "Source mint and destination mint cannot the same"
    );
  }

  record InvalidMint(int code, String msg) implements JupiterError {

    public static final InvalidMint INSTANCE = new InvalidMint(
        6019, "Invalid mint"
    );
  }

  record InvalidProgramAuthority(int code, String msg) implements JupiterError {

    public static final InvalidProgramAuthority INSTANCE = new InvalidProgramAuthority(
        6020, "Invalid program authority"
    );
  }

  record InvalidOutputTokenAccount(int code, String msg) implements JupiterError {

    public static final InvalidOutputTokenAccount INSTANCE = new InvalidOutputTokenAccount(
        6021, "Invalid output token account"
    );
  }

  record InvalidFeeWallet(int code, String msg) implements JupiterError {

    public static final InvalidFeeWallet INSTANCE = new InvalidFeeWallet(
        6022, "Invalid fee wallet"
    );
  }

  record InvalidAuthority(int code, String msg) implements JupiterError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        6023, "Invalid authority"
    );
  }

  record InsufficientFunds(int code, String msg) implements JupiterError {

    public static final InsufficientFunds INSTANCE = new InsufficientFunds(
        6024, "Insufficient funds"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements JupiterError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        6025, "Invalid token account"
    );
  }

  record BondingCurveAlreadyCompleted(int code, String msg) implements JupiterError {

    public static final BondingCurveAlreadyCompleted INSTANCE = new BondingCurveAlreadyCompleted(
        6026, "Bonding curve already completed"
    );
  }
}
