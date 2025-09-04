package software.sava.anchor.programs.chainlink.store.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface StoreError extends ProgramError permits
    StoreError.Unauthorized,
    StoreError.InvalidInput,
    StoreError.NotFound,
    StoreError.InvalidVersion,
    StoreError.InsufficientSize {

  static StoreError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> Unauthorized.INSTANCE;
      case 6001 -> InvalidInput.INSTANCE;
      case 6002 -> NotFound.INSTANCE;
      case 6003 -> InvalidVersion.INSTANCE;
      case 6004 -> InsufficientSize.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Store error code: " + errorCode);
    };
  }

  record Unauthorized(int code, String msg) implements StoreError {

    public static final Unauthorized INSTANCE = new Unauthorized(
        6000, "Unauthorized"
    );
  }

  record InvalidInput(int code, String msg) implements StoreError {

    public static final InvalidInput INSTANCE = new InvalidInput(
        6001, "Invalid input"
    );
  }

  record NotFound(int code, String msg) implements StoreError {

    public static final NotFound INSTANCE = new NotFound(
        6002, "null"
    );
  }

  record InvalidVersion(int code, String msg) implements StoreError {

    public static final InvalidVersion INSTANCE = new InvalidVersion(
        6003, "Invalid version"
    );
  }

  record InsufficientSize(int code, String msg) implements StoreError {

    public static final InsufficientSize INSTANCE = new InsufficientSize(
        6004, "Insufficient or invalid feed account size, has to be `8 + HEADER_SIZE + n * size_of::<Transmission>()`"
    );
  }
}
