package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.CloseNotEmptyError,
    GlamError.NotAuthorizedError,
    GlamError.InvalidFundName,
    GlamError.InvalidFundSymbol,
    GlamError.InvalidFundUri,
    GlamError.InvalidAssetsLen,
    GlamError.InvalidAssetsWeights,
    GlamError.InvalidAssetForSwap,
    GlamError.InvalidSwap {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> CloseNotEmptyError.INSTANCE;
      case 6001 -> NotAuthorizedError.INSTANCE;
      case 6002 -> InvalidFundName.INSTANCE;
      case 6003 -> InvalidFundSymbol.INSTANCE;
      case 6004 -> InvalidFundUri.INSTANCE;
      case 6005 -> InvalidAssetsLen.INSTANCE;
      case 6006 -> InvalidAssetsWeights.INSTANCE;
      case 6007 -> InvalidAssetForSwap.INSTANCE;
      case 6008 -> InvalidSwap.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record CloseNotEmptyError(int code, String msg) implements GlamError {

    public static final CloseNotEmptyError INSTANCE = new CloseNotEmptyError(
        6000, "Error closing account: not empty"
    );
  }

  record NotAuthorizedError(int code, String msg) implements GlamError {

    public static final NotAuthorizedError INSTANCE = new NotAuthorizedError(
        6001, "Error: not authorized"
    );
  }

  record InvalidFundName(int code, String msg) implements GlamError {

    public static final InvalidFundName INSTANCE = new InvalidFundName(
        6002, "Invalid fund name: max 30 chars"
    );
  }

  record InvalidFundSymbol(int code, String msg) implements GlamError {

    public static final InvalidFundSymbol INSTANCE = new InvalidFundSymbol(
        6003, "Too many assets: max 50"
    );
  }

  record InvalidFundUri(int code, String msg) implements GlamError {

    public static final InvalidFundUri INSTANCE = new InvalidFundUri(
        6004, "Too many assets: max 20"
    );
  }

  record InvalidAssetsLen(int code, String msg) implements GlamError {

    public static final InvalidAssetsLen INSTANCE = new InvalidAssetsLen(
        6005, "Too many assets: max 100"
    );
  }

  record InvalidAssetsWeights(int code, String msg) implements GlamError {

    public static final InvalidAssetsWeights INSTANCE = new InvalidAssetsWeights(
        6006, "Number of weights should match number of assets"
    );
  }

  record InvalidAssetForSwap(int code, String msg) implements GlamError {

    public static final InvalidAssetForSwap INSTANCE = new InvalidAssetForSwap(
        6007, "Asset cannot be swapped"
    );
  }

  record InvalidSwap(int code, String msg) implements GlamError {

    public static final InvalidSwap INSTANCE = new InvalidSwap(
        6008, "Swap failed"
    );
  }
}
