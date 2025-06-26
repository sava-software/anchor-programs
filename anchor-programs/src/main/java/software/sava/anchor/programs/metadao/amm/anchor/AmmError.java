package software.sava.anchor.programs.metadao.amm.anchor;

import software.sava.anchor.ProgramError;

public sealed interface AmmError extends ProgramError permits
    AmmError.AssertFailed,
    AmmError.NoSlotsPassed,
    AmmError.NoReserves,
    AmmError.InputAmountOverflow,
    AmmError.AddLiquidityCalculationError,
    AmmError.DecimalScaleError,
    AmmError.SameTokenMints,
    AmmError.SwapSlippageExceeded,
    AmmError.InsufficientBalance,
    AmmError.ZeroLiquidityRemove,
    AmmError.ZeroLiquidityToAdd,
    AmmError.ZeroMinLpTokens,
    AmmError.AddLiquiditySlippageExceeded,
    AmmError.AddLiquidityMaxBaseExceeded,
    AmmError.InsufficientQuoteAmount,
    AmmError.ZeroSwapAmount,
    AmmError.ConstantProductInvariantFailed,
    AmmError.CastingOverflow {

  static AmmError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> AssertFailed.INSTANCE;
      case 6001 -> NoSlotsPassed.INSTANCE;
      case 6002 -> NoReserves.INSTANCE;
      case 6003 -> InputAmountOverflow.INSTANCE;
      case 6004 -> AddLiquidityCalculationError.INSTANCE;
      case 6005 -> DecimalScaleError.INSTANCE;
      case 6006 -> SameTokenMints.INSTANCE;
      case 6007 -> SwapSlippageExceeded.INSTANCE;
      case 6008 -> InsufficientBalance.INSTANCE;
      case 6009 -> ZeroLiquidityRemove.INSTANCE;
      case 6010 -> ZeroLiquidityToAdd.INSTANCE;
      case 6011 -> ZeroMinLpTokens.INSTANCE;
      case 6012 -> AddLiquiditySlippageExceeded.INSTANCE;
      case 6013 -> AddLiquidityMaxBaseExceeded.INSTANCE;
      case 6014 -> InsufficientQuoteAmount.INSTANCE;
      case 6015 -> ZeroSwapAmount.INSTANCE;
      case 6016 -> ConstantProductInvariantFailed.INSTANCE;
      case 6017 -> CastingOverflow.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Amm error code: " + errorCode);
    };
  }

  record AssertFailed(int code, String msg) implements AmmError {

    public static final AssertFailed INSTANCE = new AssertFailed(
        6000, "An assertion failed"
    );
  }

  record NoSlotsPassed(int code, String msg) implements AmmError {

    public static final NoSlotsPassed INSTANCE = new NoSlotsPassed(
        6001, "Can't get a TWAP before some observations have been stored"
    );
  }

  record NoReserves(int code, String msg) implements AmmError {

    public static final NoReserves INSTANCE = new NoReserves(
        6002, "Can't swap through a pool without token reserves on either side"
    );
  }

  record InputAmountOverflow(int code, String msg) implements AmmError {

    public static final InputAmountOverflow INSTANCE = new InputAmountOverflow(
        6003, "Input token amount is too large for a swap, causes overflow"
    );
  }

  record AddLiquidityCalculationError(int code, String msg) implements AmmError {

    public static final AddLiquidityCalculationError INSTANCE = new AddLiquidityCalculationError(
        6004, "Add liquidity calculation error"
    );
  }

  record DecimalScaleError(int code, String msg) implements AmmError {

    public static final DecimalScaleError INSTANCE = new DecimalScaleError(
        6005, "Error in decimal scale conversion"
    );
  }

  record SameTokenMints(int code, String msg) implements AmmError {

    public static final SameTokenMints INSTANCE = new SameTokenMints(
        6006, "You can't create an AMM pool where the token mints are the same"
    );
  }

  record SwapSlippageExceeded(int code, String msg) implements AmmError {

    public static final SwapSlippageExceeded INSTANCE = new SwapSlippageExceeded(
        6007, "A user wouldn't have gotten back their `output_amount_min`, reverting"
    );
  }

  record InsufficientBalance(int code, String msg) implements AmmError {

    public static final InsufficientBalance INSTANCE = new InsufficientBalance(
        6008, "The user had insufficient balance to do this"
    );
  }

  record ZeroLiquidityRemove(int code, String msg) implements AmmError {

    public static final ZeroLiquidityRemove INSTANCE = new ZeroLiquidityRemove(
        6009, "Must remove a non-zero amount of liquidity"
    );
  }

  record ZeroLiquidityToAdd(int code, String msg) implements AmmError {

    public static final ZeroLiquidityToAdd INSTANCE = new ZeroLiquidityToAdd(
        6010, "Cannot add liquidity with 0 tokens on either side"
    );
  }

  record ZeroMinLpTokens(int code, String msg) implements AmmError {

    public static final ZeroMinLpTokens INSTANCE = new ZeroMinLpTokens(
        6011, "Must specify a non-zero `min_lp_tokens` when adding to an existing pool"
    );
  }

  record AddLiquiditySlippageExceeded(int code, String msg) implements AmmError {

    public static final AddLiquiditySlippageExceeded INSTANCE = new AddLiquiditySlippageExceeded(
        6012, "LP wouldn't have gotten back `lp_token_min`"
    );
  }

  record AddLiquidityMaxBaseExceeded(int code, String msg) implements AmmError {

    public static final AddLiquidityMaxBaseExceeded INSTANCE = new AddLiquidityMaxBaseExceeded(
        6013, "LP would have spent more than `max_base_amount`"
    );
  }

  record InsufficientQuoteAmount(int code, String msg) implements AmmError {

    public static final InsufficientQuoteAmount INSTANCE = new InsufficientQuoteAmount(
        6014, "`quote_amount` must be greater than 100000000 when initializing a pool"
    );
  }

  record ZeroSwapAmount(int code, String msg) implements AmmError {

    public static final ZeroSwapAmount INSTANCE = new ZeroSwapAmount(
        6015, "Users must swap a non-zero amount"
    );
  }

  record ConstantProductInvariantFailed(int code, String msg) implements AmmError {

    public static final ConstantProductInvariantFailed INSTANCE = new ConstantProductInvariantFailed(
        6016, "K should always be increasing"
    );
  }

  record CastingOverflow(int code, String msg) implements AmmError {

    public static final CastingOverflow INSTANCE = new CastingOverflow(
        6017, "Casting has caused an overflow"
    );
  }
}
