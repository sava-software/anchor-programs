package software.sava.anchor.programs.metadao.launchpad.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface LaunchpadError extends ProgramError permits
    LaunchpadError.InvalidAmount,
    LaunchpadError.SupplyNonZero,
    LaunchpadError.InvalidSecondsForLaunch,
    LaunchpadError.InsufficientFunds,
    LaunchpadError.InvalidTokenKey,
    LaunchpadError.InvalidLaunchState,
    LaunchpadError.LaunchPeriodNotOver,
    LaunchpadError.LaunchExpired,
    LaunchpadError.LaunchNotRefunding,
    LaunchpadError.LaunchNotInitialized,
    LaunchpadError.FreezeAuthoritySet,
    LaunchpadError.InvalidMonthlySpendingLimit {

  static LaunchpadError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidAmount.INSTANCE;
      case 6001 -> SupplyNonZero.INSTANCE;
      case 6002 -> InvalidSecondsForLaunch.INSTANCE;
      case 6003 -> InsufficientFunds.INSTANCE;
      case 6004 -> InvalidTokenKey.INSTANCE;
      case 6005 -> InvalidLaunchState.INSTANCE;
      case 6006 -> LaunchPeriodNotOver.INSTANCE;
      case 6007 -> LaunchExpired.INSTANCE;
      case 6008 -> LaunchNotRefunding.INSTANCE;
      case 6009 -> LaunchNotInitialized.INSTANCE;
      case 6010 -> FreezeAuthoritySet.INSTANCE;
      case 6011 -> InvalidMonthlySpendingLimit.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Launchpad error code: " + errorCode);
    };
  }

  record InvalidAmount(int code, String msg) implements LaunchpadError {

    public static final InvalidAmount INSTANCE = new InvalidAmount(
        6000, "Invalid amount"
    );
  }

  record SupplyNonZero(int code, String msg) implements LaunchpadError {

    public static final SupplyNonZero INSTANCE = new SupplyNonZero(
        6001, "Supply must be zero"
    );
  }

  record InvalidSecondsForLaunch(int code, String msg) implements LaunchpadError {

    public static final InvalidSecondsForLaunch INSTANCE = new InvalidSecondsForLaunch(
        6002, "Launch period must be between 1 hour and 2 weeks"
    );
  }

  record InsufficientFunds(int code, String msg) implements LaunchpadError {

    public static final InsufficientFunds INSTANCE = new InsufficientFunds(
        6003, "Insufficient funds"
    );
  }

  record InvalidTokenKey(int code, String msg) implements LaunchpadError {

    public static final InvalidTokenKey INSTANCE = new InvalidTokenKey(
        6004, "Token mint key must end in 'meta'"
    );
  }

  record InvalidLaunchState(int code, String msg) implements LaunchpadError {

    public static final InvalidLaunchState INSTANCE = new InvalidLaunchState(
        6005, "Invalid launch state"
    );
  }

  record LaunchPeriodNotOver(int code, String msg) implements LaunchpadError {

    public static final LaunchPeriodNotOver INSTANCE = new LaunchPeriodNotOver(
        6006, "Launch period not over"
    );
  }

  record LaunchExpired(int code, String msg) implements LaunchpadError {

    public static final LaunchExpired INSTANCE = new LaunchExpired(
        6007, "Launch is complete, no more funding allowed"
    );
  }

  record LaunchNotRefunding(int code, String msg) implements LaunchpadError {

    public static final LaunchNotRefunding INSTANCE = new LaunchNotRefunding(
        6008, "Launch needs to be in refunding state to get a refund"
    );
  }

  record LaunchNotInitialized(int code, String msg) implements LaunchpadError {

    public static final LaunchNotInitialized INSTANCE = new LaunchNotInitialized(
        6009, "Launch must be initialized to be started"
    );
  }

  record FreezeAuthoritySet(int code, String msg) implements LaunchpadError {

    public static final FreezeAuthoritySet INSTANCE = new FreezeAuthoritySet(
        6010, "Freeze authority can't be set on launchpad tokens"
    );
  }

  record InvalidMonthlySpendingLimit(int code, String msg) implements LaunchpadError {

    public static final InvalidMonthlySpendingLimit INSTANCE = new InvalidMonthlySpendingLimit(
        6011, "Monthly spending limit must be less than 1/6th of the minimum raise amount"
    );
  }
}
