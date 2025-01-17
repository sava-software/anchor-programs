package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.InvalidName,
    GlamError.InvalidSymbol,
    GlamError.InvalidUri,
    GlamError.InvalidAssetsLen,
    GlamError.Disabled,
    GlamError.NoShareClass,
    GlamError.ShareClassesNotClosed,
    GlamError.CloseNotEmptyError,
    GlamError.WithdrawDenied {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidName.INSTANCE;
      case 6001 -> InvalidSymbol.INSTANCE;
      case 6002 -> InvalidUri.INSTANCE;
      case 6003 -> InvalidAssetsLen.INSTANCE;
      case 6004 -> Disabled.INSTANCE;
      case 6005 -> NoShareClass.INSTANCE;
      case 6006 -> ShareClassesNotClosed.INSTANCE;
      case 6007 -> CloseNotEmptyError.INSTANCE;
      case 6008 -> WithdrawDenied.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record InvalidName(int code, String msg) implements GlamError {

    public static final InvalidName INSTANCE = new InvalidName(
        6000, "Name too long: max 50 chars"
    );
  }

  record InvalidSymbol(int code, String msg) implements GlamError {

    public static final InvalidSymbol INSTANCE = new InvalidSymbol(
        6001, "Symbol too long: max 50 chars"
    );
  }

  record InvalidUri(int code, String msg) implements GlamError {

    public static final InvalidUri INSTANCE = new InvalidUri(
        6002, "Uri too long: max 20"
    );
  }

  record InvalidAssetsLen(int code, String msg) implements GlamError {

    public static final InvalidAssetsLen INSTANCE = new InvalidAssetsLen(
        6003, "Too many assets: max 100"
    );
  }

  record Disabled(int code, String msg) implements GlamError {

    public static final Disabled INSTANCE = new Disabled(
        6004, "State account is disabled"
    );
  }

  record NoShareClass(int code, String msg) implements GlamError {

    public static final NoShareClass INSTANCE = new NoShareClass(
        6005, "No share class found"
    );
  }

  record ShareClassesNotClosed(int code, String msg) implements GlamError {

    public static final ShareClassesNotClosed INSTANCE = new ShareClassesNotClosed(
        6006, "Glam state account can't be closed. Close share classes first"
    );
  }

  record CloseNotEmptyError(int code, String msg) implements GlamError {

    public static final CloseNotEmptyError INSTANCE = new CloseNotEmptyError(
        6007, "Error closing state account: not empty"
    );
  }

  record WithdrawDenied(int code, String msg) implements GlamError {

    public static final WithdrawDenied INSTANCE = new WithdrawDenied(
        6008, "Withdraw denied. Only vaults allow withdraws (funds and mints don't)"
    );
  }
}
