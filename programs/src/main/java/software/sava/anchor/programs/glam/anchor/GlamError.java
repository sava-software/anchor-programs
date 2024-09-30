package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.NotAuthorized {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> NotAuthorized.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record NotAuthorized(int code, String msg) implements GlamError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        6000, "Signer is not authorized"
    );
  }
}
