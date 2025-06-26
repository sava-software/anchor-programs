package software.sava.anchor.programs.jupiter.limit.anchor;

import software.sava.anchor.ProgramError;

public sealed interface LimitOrderError extends ProgramError permits
    LimitOrderError.InvalidMakingAmount,
    LimitOrderError.InvalidTakingAmount,
    LimitOrderError.InvalidMaxTakingAmount,
    LimitOrderError.InvalidCalculation,
    LimitOrderError.InvalidInputAccount,
    LimitOrderError.InvalidOutputAccount,
    LimitOrderError.InvalidPair,
    LimitOrderError.MissingReferral,
    LimitOrderError.OrderExpired,
    LimitOrderError.OrderNotExpired,
    LimitOrderError.InvalidKeeper,
    LimitOrderError.MathOverflow,
    LimitOrderError.ProgramMismatch,
    LimitOrderError.UnknownInstruction,
    LimitOrderError.MissingRepayInstructions,
    LimitOrderError.InvalidOrder,
    LimitOrderError.InvalidBorrowMakingAmount {

  static LimitOrderError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidMakingAmount.INSTANCE;
      case 6001 -> InvalidTakingAmount.INSTANCE;
      case 6002 -> InvalidMaxTakingAmount.INSTANCE;
      case 6003 -> InvalidCalculation.INSTANCE;
      case 6004 -> InvalidInputAccount.INSTANCE;
      case 6005 -> InvalidOutputAccount.INSTANCE;
      case 6006 -> InvalidPair.INSTANCE;
      case 6007 -> MissingReferral.INSTANCE;
      case 6008 -> OrderExpired.INSTANCE;
      case 6009 -> OrderNotExpired.INSTANCE;
      case 6010 -> InvalidKeeper.INSTANCE;
      case 6011 -> MathOverflow.INSTANCE;
      case 6012 -> ProgramMismatch.INSTANCE;
      case 6013 -> UnknownInstruction.INSTANCE;
      case 6014 -> MissingRepayInstructions.INSTANCE;
      case 6015 -> InvalidOrder.INSTANCE;
      case 6016 -> InvalidBorrowMakingAmount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected LimitOrder error code: " + errorCode);
    };
  }

  record InvalidMakingAmount(int code, String msg) implements LimitOrderError {

    public static final InvalidMakingAmount INSTANCE = new InvalidMakingAmount(
        6000, "null"
    );
  }

  record InvalidTakingAmount(int code, String msg) implements LimitOrderError {

    public static final InvalidTakingAmount INSTANCE = new InvalidTakingAmount(
        6001, "null"
    );
  }

  record InvalidMaxTakingAmount(int code, String msg) implements LimitOrderError {

    public static final InvalidMaxTakingAmount INSTANCE = new InvalidMaxTakingAmount(
        6002, "null"
    );
  }

  record InvalidCalculation(int code, String msg) implements LimitOrderError {

    public static final InvalidCalculation INSTANCE = new InvalidCalculation(
        6003, "null"
    );
  }

  record InvalidInputAccount(int code, String msg) implements LimitOrderError {

    public static final InvalidInputAccount INSTANCE = new InvalidInputAccount(
        6004, "null"
    );
  }

  record InvalidOutputAccount(int code, String msg) implements LimitOrderError {

    public static final InvalidOutputAccount INSTANCE = new InvalidOutputAccount(
        6005, "null"
    );
  }

  record InvalidPair(int code, String msg) implements LimitOrderError {

    public static final InvalidPair INSTANCE = new InvalidPair(
        6006, "null"
    );
  }

  record MissingReferral(int code, String msg) implements LimitOrderError {

    public static final MissingReferral INSTANCE = new MissingReferral(
        6007, "null"
    );
  }

  record OrderExpired(int code, String msg) implements LimitOrderError {

    public static final OrderExpired INSTANCE = new OrderExpired(
        6008, "null"
    );
  }

  record OrderNotExpired(int code, String msg) implements LimitOrderError {

    public static final OrderNotExpired INSTANCE = new OrderNotExpired(
        6009, "null"
    );
  }

  record InvalidKeeper(int code, String msg) implements LimitOrderError {

    public static final InvalidKeeper INSTANCE = new InvalidKeeper(
        6010, "null"
    );
  }

  record MathOverflow(int code, String msg) implements LimitOrderError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6011, "null"
    );
  }

  record ProgramMismatch(int code, String msg) implements LimitOrderError {

    public static final ProgramMismatch INSTANCE = new ProgramMismatch(
        6012, "null"
    );
  }

  record UnknownInstruction(int code, String msg) implements LimitOrderError {

    public static final UnknownInstruction INSTANCE = new UnknownInstruction(
        6013, "null"
    );
  }

  record MissingRepayInstructions(int code, String msg) implements LimitOrderError {

    public static final MissingRepayInstructions INSTANCE = new MissingRepayInstructions(
        6014, "null"
    );
  }

  record InvalidOrder(int code, String msg) implements LimitOrderError {

    public static final InvalidOrder INSTANCE = new InvalidOrder(
        6015, "null"
    );
  }

  record InvalidBorrowMakingAmount(int code, String msg) implements LimitOrderError {

    public static final InvalidBorrowMakingAmount INSTANCE = new InvalidBorrowMakingAmount(
        6016, "null"
    );
  }
}
