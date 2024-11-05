package software.sava.anchor.programs.pump.anchor;

import software.sava.anchor.ProgramError;

public sealed interface PumpError extends ProgramError permits
    PumpError.NotAuthorized,
    PumpError.AlreadyInitialized,
    PumpError.TooMuchSolRequired,
    PumpError.TooLittleSolReceived,
    PumpError.MintDoesNotMatchBondingCurve,
    PumpError.BondingCurveComplete,
    PumpError.BondingCurveNotComplete,
    PumpError.NotInitialized,
    PumpError.WithdrawTooFrequent {

  static PumpError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> NotAuthorized.INSTANCE;
      case 6001 -> AlreadyInitialized.INSTANCE;
      case 6002 -> TooMuchSolRequired.INSTANCE;
      case 6003 -> TooLittleSolReceived.INSTANCE;
      case 6004 -> MintDoesNotMatchBondingCurve.INSTANCE;
      case 6005 -> BondingCurveComplete.INSTANCE;
      case 6006 -> BondingCurveNotComplete.INSTANCE;
      case 6007 -> NotInitialized.INSTANCE;
      case 6008 -> WithdrawTooFrequent.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Pump error code: " + errorCode);
    };
  }

  record NotAuthorized(int code, String msg) implements PumpError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        6000, "The given account is not authorized to execute this instruction."
    );
  }

  record AlreadyInitialized(int code, String msg) implements PumpError {

    public static final AlreadyInitialized INSTANCE = new AlreadyInitialized(
        6001, "The program is already initialized."
    );
  }

  record TooMuchSolRequired(int code, String msg) implements PumpError {

    public static final TooMuchSolRequired INSTANCE = new TooMuchSolRequired(
        6002, "slippage: Too much SOL required to buy the given amount of tokens."
    );
  }

  record TooLittleSolReceived(int code, String msg) implements PumpError {

    public static final TooLittleSolReceived INSTANCE = new TooLittleSolReceived(
        6003, "slippage: Too little SOL received to sell the given amount of tokens."
    );
  }

  record MintDoesNotMatchBondingCurve(int code, String msg) implements PumpError {

    public static final MintDoesNotMatchBondingCurve INSTANCE = new MintDoesNotMatchBondingCurve(
        6004, "The mint does not match the bonding curve."
    );
  }

  record BondingCurveComplete(int code, String msg) implements PumpError {

    public static final BondingCurveComplete INSTANCE = new BondingCurveComplete(
        6005, "The bonding curve has completed and liquidity migrated to raydium."
    );
  }

  record BondingCurveNotComplete(int code, String msg) implements PumpError {

    public static final BondingCurveNotComplete INSTANCE = new BondingCurveNotComplete(
        6006, "The bonding curve has not completed."
    );
  }

  record NotInitialized(int code, String msg) implements PumpError {

    public static final NotInitialized INSTANCE = new NotInitialized(
        6007, "The program is not initialized."
    );
  }

  record WithdrawTooFrequent(int code, String msg) implements PumpError {

    public static final WithdrawTooFrequent INSTANCE = new WithdrawTooFrequent(
        6008, "Withdraw too frequent"
    );
  }
}
