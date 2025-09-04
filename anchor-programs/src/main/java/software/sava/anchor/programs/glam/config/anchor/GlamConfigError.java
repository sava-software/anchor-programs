package software.sava.anchor.programs.glam.config.anchor;

import software.sava.anchor.programs._commons.ProgramError;

public sealed interface GlamConfigError extends ProgramError permits
    GlamConfigError.InvalidAuthority,
    GlamConfigError.InvalidAssetMeta,
    GlamConfigError.AssetMetaAlreadyExists,
    GlamConfigError.InvalidParameters {

  static GlamConfigError getInstance(final int errorCode) {
    return switch (errorCode) {
      case 6000 -> InvalidAuthority.INSTANCE;
      case 6001 -> InvalidAssetMeta.INSTANCE;
      case 6002 -> AssetMetaAlreadyExists.INSTANCE;
      case 6003 -> InvalidParameters.INSTANCE;
      default -> throw new IllegalStateException("Unexpected GlamConfig error code: " + errorCode);
    };
  }

  record InvalidAuthority(int code, String msg) implements GlamConfigError {

    public static final InvalidAuthority INSTANCE = new InvalidAuthority(
        6000, "Invalid authority"
    );
  }

  record InvalidAssetMeta(int code, String msg) implements GlamConfigError {

    public static final InvalidAssetMeta INSTANCE = new InvalidAssetMeta(
        6001, "Invalid asset meta"
    );
  }

  record AssetMetaAlreadyExists(int code, String msg) implements GlamConfigError {

    public static final AssetMetaAlreadyExists INSTANCE = new AssetMetaAlreadyExists(
        6002, "Asset meta already exists"
    );
  }

  record InvalidParameters(int code, String msg) implements GlamConfigError {

    public static final InvalidParameters INSTANCE = new InvalidParameters(
        6003, "Invalid parameters"
    );
  }
}
