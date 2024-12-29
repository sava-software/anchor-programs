package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.ShareClassNotEmpty,
    GlamError.InvalidTokenAccount {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> ShareClassNotEmpty.INSTANCE;
      case 6001 -> InvalidTokenAccount.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record ShareClassNotEmpty(int code, String msg) implements GlamError {

    public static final ShareClassNotEmpty INSTANCE = new ShareClassNotEmpty(
        6000, "Share class mint supply not zero"
    );
  }

  record InvalidTokenAccount(int code, String msg) implements GlamError {

    public static final InvalidTokenAccount INSTANCE = new InvalidTokenAccount(
        6001, "Invalid token account"
    );
  }
}
