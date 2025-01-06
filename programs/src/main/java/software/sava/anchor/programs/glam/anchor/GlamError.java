package software.sava.anchor.programs.glam.anchor;

import software.sava.anchor.ProgramError;

public sealed interface GlamError extends ProgramError permits
    GlamError.InvalidAssetForSwap,
    GlamError.InvalidSwap {

  static GlamError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidAssetForSwap.INSTANCE;
      case 6001 -> InvalidSwap.INSTANCE;
      default -> throw new IllegalStateException("Unexpected Glam error code: " + errorCode);
    };
  }

  record InvalidAssetForSwap(int code, String msg) implements GlamError {

    public static final InvalidAssetForSwap INSTANCE = new InvalidAssetForSwap(
        6000, "Asset cannot be swapped"
    );
  }

  record InvalidSwap(int code, String msg) implements GlamError {

    public static final InvalidSwap INSTANCE = new InvalidSwap(
        6001, "Swap failed"
    );
  }
}
