package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.TransfersDisabled,
    GlamError.AmountTooBig,
    GlamError.LockUp {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> TransfersDisabled.INSTANCE;
      case 6001 -> AmountTooBig.INSTANCE;
      case 6002 -> LockUp.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record TransfersDisabled(int code, String msg) implements GlamError {

    public static final TransfersDisabled INSTANCE = new TransfersDisabled(
        6000, "Policy violation: transfers disabled"
    );
  }

  record AmountTooBig(int code, String msg) implements GlamError {

    public static final AmountTooBig INSTANCE = new AmountTooBig(
        6001, "Policy violation: amount too big"
    );
  }

  record LockUp(int code, String msg) implements GlamError {

    public static final LockUp INSTANCE = new LockUp(
        6002, "Policy violation: lock-up period"
    );
  }
}
