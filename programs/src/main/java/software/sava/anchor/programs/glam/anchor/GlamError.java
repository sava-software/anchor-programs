package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.NotAuthorized,
    GlamError.IntegrationDisabled {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> NotAuthorized.INSTANCE;
      case 6001 -> IntegrationDisabled.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record NotAuthorized(int code, String msg) implements GlamError {

    public static final NotAuthorized INSTANCE = new NotAuthorized(
        6000, "Signer is not authorized"
    );
  }

  record IntegrationDisabled(int code, String msg) implements GlamError {

    public static final IntegrationDisabled INSTANCE = new IntegrationDisabled(
        6001, "Integration is disabled"
    );
  }
}
