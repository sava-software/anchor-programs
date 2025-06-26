package software.sava.anchor.programs.jupiter.dca.anchor;

import software.sava.anchor.ProgramError;

public sealed interface DcaError extends ProgramError permits
    DcaError.InvalidAmount,
    DcaError.InvalidCycleAmount,
    DcaError.InvalidPair,
    DcaError.TooFrequent,
    DcaError.InvalidMinPrice,
    DcaError.InvalidMaxPrice,
    DcaError.InAmountInsufficient,
    DcaError.Unauthorized,
    DcaError.NoInATA,
    DcaError.NoUserInATA,
    DcaError.NoOutATA,
    DcaError.NoUserOutATA,
    DcaError.InsufficientBalanceInProgram,
    DcaError.InvalidDepositAmount,
    DcaError.UserInsufficientBalance,
    DcaError.UnauthorizedKeeper,
    DcaError.UnrecognizedProgram,
    DcaError.MathErrors,
    DcaError.KeeperNotTimeToFill,
    DcaError.OrderFillAmountWrong,
    DcaError.SwapOutAmountBelowMinimum,
    DcaError.WrongAdmin,
    DcaError.MathOverflow,
    DcaError.AddressMismatch,
    DcaError.ProgramMismatch,
    DcaError.IncorrectRepaymentAmount,
    DcaError.CannotBorrowBeforeRepay,
    DcaError.NoRepaymentInstructionFound,
    DcaError.MissingSwapInstructions,
    DcaError.UnexpectedSwapProgram,
    DcaError.InvalidSwapMint,
    DcaError.UnknownInstruction,
    DcaError.MissingRepayInstructions,
    DcaError.KeeperShortchanged,
    DcaError.WrongSwapOutAccount,
    DcaError.WrongTransferAmount,
    DcaError.InsufficientBalanceForRent,
    DcaError.UnexpectedSolBalance,
    DcaError.InsufficientWsolForTransfer,
    DcaError.MissedInstruction,
    DcaError.WrongProgram,
    DcaError.BalanceNotZero,
    DcaError.UnexpectedWSOLLeftover,
    DcaError.IntermediateAccountNotSet,
    DcaError.UnexpectedSwapInstruction,
    DcaError.SwapOutLessThanUserMinimum,
    DcaError.SwapOutMoreThanUserMaximum {

  static DcaError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidAmount.INSTANCE;
      case 6001 -> InvalidCycleAmount.INSTANCE;
      case 6002 -> InvalidPair.INSTANCE;
      case 6003 -> TooFrequent.INSTANCE;
      case 6004 -> InvalidMinPrice.INSTANCE;
      case 6005 -> InvalidMaxPrice.INSTANCE;
      case 6006 -> InAmountInsufficient.INSTANCE;
      case 6007 -> Unauthorized.INSTANCE;
      case 6008 -> NoInATA.INSTANCE;
      case 6009 -> NoUserInATA.INSTANCE;
      case 6010 -> NoOutATA.INSTANCE;
      case 6011 -> NoUserOutATA.INSTANCE;
      case 6012 -> InsufficientBalanceInProgram.INSTANCE;
      case 6013 -> InvalidDepositAmount.INSTANCE;
      case 6014 -> UserInsufficientBalance.INSTANCE;
      case 6015 -> UnauthorizedKeeper.INSTANCE;
      case 6016 -> UnrecognizedProgram.INSTANCE;
      case 6017 -> MathErrors.INSTANCE;
      case 6018 -> KeeperNotTimeToFill.INSTANCE;
      case 6019 -> OrderFillAmountWrong.INSTANCE;
      case 6020 -> SwapOutAmountBelowMinimum.INSTANCE;
      case 6021 -> WrongAdmin.INSTANCE;
      case 6022 -> MathOverflow.INSTANCE;
      case 6023 -> AddressMismatch.INSTANCE;
      case 6024 -> ProgramMismatch.INSTANCE;
      case 6025 -> IncorrectRepaymentAmount.INSTANCE;
      case 6026 -> CannotBorrowBeforeRepay.INSTANCE;
      case 6027 -> NoRepaymentInstructionFound.INSTANCE;
      case 6028 -> MissingSwapInstructions.INSTANCE;
      case 6029 -> UnexpectedSwapProgram.INSTANCE;
      case 6030 -> InvalidSwapMint.INSTANCE;
      case 6031 -> UnknownInstruction.INSTANCE;
      case 6032 -> MissingRepayInstructions.INSTANCE;
      case 6033 -> KeeperShortchanged.INSTANCE;
      case 6034 -> WrongSwapOutAccount.INSTANCE;
      case 6035 -> WrongTransferAmount.INSTANCE;
      case 6036 -> InsufficientBalanceForRent.INSTANCE;
      case 6037 -> UnexpectedSolBalance.INSTANCE;
      case 6038 -> InsufficientWsolForTransfer.INSTANCE;
      case 6039 -> MissedInstruction.INSTANCE;
      case 6040 -> WrongProgram.INSTANCE;
      case 6041 -> BalanceNotZero.INSTANCE;
      case 6042 -> UnexpectedWSOLLeftover.INSTANCE;
      case 6043 -> IntermediateAccountNotSet.INSTANCE;
      case 6044 -> UnexpectedSwapInstruction.INSTANCE;
      case 6045 -> SwapOutLessThanUserMinimum.INSTANCE;
      case 6046 -> SwapOutMoreThanUserMaximum.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Dca error code: " + errorCode);
    };
  }

  record InvalidAmount(int code, String msg) implements DcaError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        6000, "Invalid deposit amount"
    );
  }

  record InvalidCycleAmount(int code, String msg) implements DcaError {

    public static final InvalidCycleAmount INSTANCE = new InvalidCycleAmount(
        6001, "Invalid deposit amount"
    );
  }

  record InvalidPair(int code, String msg) implements DcaError {

    public static final InvalidPair INSTANCE = new InvalidPair(
        6002, "Invalid pair"
    );
  }

  record TooFrequent(int code, String msg) implements DcaError {

    public static final TooFrequent INSTANCE = new TooFrequent(
        6003, "Too frequent DCA cycle"
    );
  }

  record InvalidMinPrice(int code, String msg) implements DcaError {

    public static final InvalidMinPrice INSTANCE = new InvalidMinPrice(
        6004, "Minimum price constraint must be greater than 0"
    );
  }

  record InvalidMaxPrice(int code, String msg) implements DcaError {

    public static final InvalidMaxPrice INSTANCE = new InvalidMaxPrice(
        6005, "Maximum price constraint must be greater than 0"
    );
  }

  record InAmountInsufficient(int code, String msg) implements DcaError {

    public static final InAmountInsufficient INSTANCE = new InAmountInsufficient(
        6006, "In amount needs to be more than in amount per cycle"
    );
  }

  record Unauthorized(int code, String msg) implements DcaError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6007, "Wrong user"
    );
  }

  record NoInATA(int code, String msg) implements DcaError {

    public static final NoInATA INSTANCE = new NoInATA(
        6008, "inAta not passed in"
    );
  }

  record NoUserInATA(int code, String msg) implements DcaError {

    public static final NoUserInATA INSTANCE = new NoUserInATA(
        6009, "userInAta not passed in"
    );
  }

  record NoOutATA(int code, String msg) implements DcaError {

    public static final NoOutATA INSTANCE = new NoOutATA(
        6010, "outAta not passed in"
    );
  }

  record NoUserOutATA(int code, String msg) implements DcaError {

    public static final NoUserOutATA INSTANCE = new NoUserOutATA(
        6011, "userOutAta not passed in"
    );
  }

  record InsufficientBalanceInProgram(int code, String msg) implements DcaError {

    public static final InsufficientBalanceInProgram INSTANCE = new InsufficientBalanceInProgram(
        6012, "Trying to withdraw more than available"
    );
  }

  record InvalidDepositAmount(int code, String msg) implements DcaError {

    public static final InvalidDepositAmount INSTANCE = new InvalidDepositAmount(
        6013, "Deposit should be more than 0"
    );
  }

  record UserInsufficientBalance(int code, String msg) implements DcaError {

    public static final UserInsufficientBalance INSTANCE = new UserInsufficientBalance(
        6014, "User has insufficient balance"
    );
  }

  record UnauthorizedKeeper(int code, String msg) implements DcaError {

    public static final UnauthorizedKeeper INSTANCE = new UnauthorizedKeeper(
        6015, "Unauthorized Keeper"
    );
  }

  record UnrecognizedProgram(int code, String msg) implements DcaError {

    public static final UnrecognizedProgram INSTANCE = new UnrecognizedProgram(
        6016, "Unrecognized Program"
    );
  }

  record MathErrors(int code, String msg) implements DcaError {

    public static final MathErrors INSTANCE = new MathErrors(
        6017, "Calculation errors"
    );
  }

  record KeeperNotTimeToFill(int code, String msg) implements DcaError {

    public static final KeeperNotTimeToFill INSTANCE = new KeeperNotTimeToFill(
        6018, "Not time to fill"
    );
  }

  record OrderFillAmountWrong(int code, String msg) implements DcaError {

    public static final OrderFillAmountWrong INSTANCE = new OrderFillAmountWrong(
        6019, "Order amount wrong"
    );
  }

  record SwapOutAmountBelowMinimum(int code, String msg) implements DcaError {

    public static final SwapOutAmountBelowMinimum INSTANCE = new SwapOutAmountBelowMinimum(
        6020, "Out amount below expectations"
    );
  }

  record WrongAdmin(int code, String msg) implements DcaError {

    public static final WrongAdmin INSTANCE = new WrongAdmin(
        6021, "Wrong admin"
    );
  }

  record MathOverflow(int code, String msg) implements DcaError {

    public static final MathOverflow INSTANCE = new MathOverflow(
        6022, "Overflow in arithmetic operation"
    );
  }

  record AddressMismatch(int code, String msg) implements DcaError {

    public static final AddressMismatch INSTANCE = new AddressMismatch(
        6023, "Address Mismatch"
    );
  }

  record ProgramMismatch(int code, String msg) implements DcaError {

    public static final ProgramMismatch INSTANCE = new ProgramMismatch(
        6024, "Program Mismatch"
    );
  }

  record IncorrectRepaymentAmount(int code, String msg) implements DcaError {

    public static final IncorrectRepaymentAmount INSTANCE = new IncorrectRepaymentAmount(
        6025, "Incorrect Repayment Amount"
    );
  }

  record CannotBorrowBeforeRepay(int code, String msg) implements DcaError {

    public static final CannotBorrowBeforeRepay INSTANCE = new CannotBorrowBeforeRepay(
        6026, "Cannot Borrow Before Repay"
    );
  }

  record NoRepaymentInstructionFound(int code, String msg) implements DcaError {

    public static final NoRepaymentInstructionFound INSTANCE = new NoRepaymentInstructionFound(
        6027, "No Repayment Found"
    );
  }

  record MissingSwapInstructions(int code, String msg) implements DcaError {

    public static final MissingSwapInstructions INSTANCE = new MissingSwapInstructions(
        6028, "Missing Swap Instruction"
    );
  }

  record UnexpectedSwapProgram(int code, String msg) implements DcaError {

    public static final UnexpectedSwapProgram INSTANCE = new UnexpectedSwapProgram(
        6029, "Expected Instruction to use Jupiter Swap Program"
    );
  }

  record InvalidSwapMint(int code, String msg) implements DcaError {

    public static final InvalidSwapMint INSTANCE = new InvalidSwapMint(
        6030, "Invalid Swap Mint"
    );
  }

  record UnknownInstruction(int code, String msg) implements DcaError {

    public static final UnknownInstruction INSTANCE = new UnknownInstruction(
        6031, "Unknown Instruction"
    );
  }

  record MissingRepayInstructions(int code, String msg) implements DcaError {

    public static final MissingRepayInstructions INSTANCE = new MissingRepayInstructions(
        6032, "Missing Repay Instruction"
    );
  }

  record KeeperShortchanged(int code, String msg) implements DcaError {

    public static final KeeperShortchanged INSTANCE = new KeeperShortchanged(
        6033, "Keeper Shortchanged"
    );
  }

  record WrongSwapOutAccount(int code, String msg) implements DcaError {

    public static final WrongSwapOutAccount INSTANCE = new WrongSwapOutAccount(
        6034, "Jup Swap to Wrong Out Account"
    );
  }

  record WrongTransferAmount(int code, String msg) implements DcaError {

    public static final WrongTransferAmount INSTANCE = new WrongTransferAmount(
        6035, "Transfer amount should be exactly account balance"
    );
  }

  record InsufficientBalanceForRent(int code, String msg) implements DcaError {

    public static final InsufficientBalanceForRent INSTANCE = new InsufficientBalanceForRent(
        6036, "Insufficient balance for rent"
    );
  }

  record UnexpectedSolBalance(int code, String msg) implements DcaError {

    public static final UnexpectedSolBalance INSTANCE = new UnexpectedSolBalance(
        6037, "Unexpected SOL amount in intermediate account"
    );
  }

  record InsufficientWsolForTransfer(int code, String msg) implements DcaError {

    public static final InsufficientWsolForTransfer INSTANCE = new InsufficientWsolForTransfer(
        6038, "Too little WSOL to perform transfer"
    );
  }

  record MissedInstruction(int code, String msg) implements DcaError {

    public static final MissedInstruction INSTANCE = new MissedInstruction(
        6039, "Did not call initiate_flash_fill"
    );
  }

  record WrongProgram(int code, String msg) implements DcaError {

    public static final WrongProgram INSTANCE = new WrongProgram(
        6040, "Did not call this program's initiate_flash_fill"
    );
  }

  record BalanceNotZero(int code, String msg) implements DcaError {

    public static final BalanceNotZero INSTANCE = new BalanceNotZero(
        6041, "Can't close account with balance"
    );
  }

  record UnexpectedWSOLLeftover(int code, String msg) implements DcaError {

    public static final UnexpectedWSOLLeftover INSTANCE = new UnexpectedWSOLLeftover(
        6042, "Should not have WSOL leftover in DCA out-token account"
    );
  }

  record IntermediateAccountNotSet(int code, String msg) implements DcaError {

    public static final IntermediateAccountNotSet INSTANCE = new IntermediateAccountNotSet(
        6043, "Should pass in a WSOL intermediate account when transferring SOL"
    );
  }

  record UnexpectedSwapInstruction(int code, String msg) implements DcaError {

    public static final UnexpectedSwapInstruction INSTANCE = new UnexpectedSwapInstruction(
        6044, "Did not call jup swap"
    );
  }

  record SwapOutLessThanUserMinimum(int code, String msg) implements DcaError {

    public static final SwapOutLessThanUserMinimum INSTANCE = new SwapOutLessThanUserMinimum(
        6045, "Expect more from swap"
    );
  }

  record SwapOutMoreThanUserMaximum(int code, String msg) implements DcaError {

    public static final SwapOutMoreThanUserMaximum INSTANCE = new SwapOutMoreThanUserMaximum(
        6046, "Expect less from swap"
    );
  }
}
