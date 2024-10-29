package software.sava.anchor.programs.pyth.push.anchor;

import software.sava.anchor.ProgramError;

public sealed interface PythPushOracleError extends ProgramError permits
    PythPushOracleError.UpdatesNotMonotonic,
    PythPushOracleError.PriceFeedMessageMismatch {

  static PythPushOracleError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> UpdatesNotMonotonic.INSTANCE;
      case 6001 -> PriceFeedMessageMismatch.INSTANCE;
      default -> throw new IllegalStateException("Unexpected PythPushOracle error code: " + errorCode);
    };
  }

  record UpdatesNotMonotonic(int code, String msg) implements PythPushOracleError {

    public static final UpdatesNotMonotonic INSTANCE = new UpdatesNotMonotonic(
        6000, "Updates must be monotonically increasing"
    );
  }

  record PriceFeedMessageMismatch(int code, String msg) implements PythPushOracleError {

    public static final PriceFeedMessageMismatch INSTANCE = new PriceFeedMessageMismatch(
        6001, "Trying to update price feed with the wrong feed id"
    );
  }
}
