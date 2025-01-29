package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.InvalidAccountType,
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
      case 6000 -> InvalidAccountType.INSTANCE;
      case 6001 -> InvalidName.INSTANCE;
      case 6002 -> InvalidSymbol.INSTANCE;
      case 6003 -> InvalidUri.INSTANCE;
      case 6004 -> InvalidAssetsLen.INSTANCE;
      case 6005 -> Disabled.INSTANCE;
      case 6006 -> NoShareClass.INSTANCE;
      case 6007 -> ShareClassesNotClosed.INSTANCE;
      case 6008 -> CloseNotEmptyError.INSTANCE;
      case 6009 -> WithdrawDenied.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record InvalidAccountType(int code, String msg) implements GlamError {

    public static final InvalidAccountType INSTANCE = new InvalidAccountType(
        6000, "Invalid account type"
    );
  }

  record InvalidName(int code, String msg) implements GlamError {

    public static final InvalidName INSTANCE = new InvalidName(
        6001, "Name too long: max 64 chars"
    );
  }

  record InvalidSymbol(int code, String msg) implements GlamError {

    public static final InvalidSymbol INSTANCE = new InvalidSymbol(
        6002, "Symbol too long: max 32 chars"
    );
  }

  record InvalidUri(int code, String msg) implements GlamError {

    public static final InvalidUri INSTANCE = new InvalidUri(
        6003, "Uri too long: max 128 chars"
    );
  }

  record InvalidAssetsLen(int code, String msg) implements GlamError {

    public static final InvalidAssetsLen INSTANCE = new InvalidAssetsLen(
        6004, "Too many assets: max 100"
    );
  }

  record Disabled(int code, String msg) implements GlamError {

    public static final Disabled INSTANCE = new Disabled(
        6005, "State account is disabled"
    );
  }

  record NoShareClass(int code, String msg) implements GlamError {

    public static final NoShareClass INSTANCE = new NoShareClass(
        6006, "No share class found"
    );
  }

  record ShareClassesNotClosed(int code, String msg) implements GlamError {

    public static final ShareClassesNotClosed INSTANCE = new ShareClassesNotClosed(
        6007, "Glam state account can't be closed. Close share classes first"
    );
  }

  record CloseNotEmptyError(int code, String msg) implements GlamError {

    public static final CloseNotEmptyError INSTANCE = new CloseNotEmptyError(
        6008, "Error closing state account: not empty"
    );
  }

  record WithdrawDenied(int code, String msg) implements GlamError {

    public static final WithdrawDenied INSTANCE = new WithdrawDenied(
        6009, "Withdraw denied. Only vaults allow withdraws (funds and mints don't)"
    );
  }
}
