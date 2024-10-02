package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.TransfersDisabled,
    GlamError.AmountTooBig,
    GlamError.LockOut {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> TransfersDisabled.INSTANCE;
      case 6001 -> AmountTooBig.INSTANCE;
      case 6002 -> LockOut.INSTANCE;
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

  record LockOut(int code, String msg) implements GlamError {

    public static final LockOut INSTANCE = new LockOut(
        6002, "Policy violation: lock out period"
    );
  }
}
