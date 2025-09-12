package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;

public sealed interface EscrowAuthority extends RustEnum permits
  EscrowAuthority.TokenOwner,
  EscrowAuthority.Creator {

  static EscrowAuthority read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> TokenOwner.INSTANCE;
      case 1 -> Creator.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [EscrowAuthority]", ordinal
      ));
    };
  }

  record TokenOwner() implements EnumNone, EscrowAuthority {

    public static final TokenOwner INSTANCE = new TokenOwner();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Creator(PublicKey val) implements EnumPublicKey, EscrowAuthority {

    public static Creator read(final byte[] _data, int i) {
      return new Creator(readPubKey(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
