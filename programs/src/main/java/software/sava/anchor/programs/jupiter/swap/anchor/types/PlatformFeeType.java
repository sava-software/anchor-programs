package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;

public sealed interface PlatformFeeType extends RustEnum permits
  PlatformFeeType.SourceMint,
  PlatformFeeType.DestinationMint,
  PlatformFeeType.Zero {

  static PlatformFeeType read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> SourceMint.read(_data, i);
      case 1 -> DestinationMint.read(_data, i);
      case 2 -> Zero.INSTANCE;
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [PlatformFeeType]", ordinal
      ));
    };
  }

  record SourceMint(PublicKey val) implements EnumPublicKey, PlatformFeeType {

    public static SourceMint read(final byte[] _data, int i) {
      return new SourceMint(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record DestinationMint(PublicKey val) implements EnumPublicKey, PlatformFeeType {

    public static DestinationMint read(final byte[] _data, int i) {
      return new DestinationMint(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record Zero() implements EnumNone, PlatformFeeType {

    public static final Zero INSTANCE = new Zero();

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
