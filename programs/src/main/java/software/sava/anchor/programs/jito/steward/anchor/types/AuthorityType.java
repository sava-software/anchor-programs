package software.sava.anchor.programs.jito.steward.anchor.types;

import java.util.OptionalInt;

import software.sava.core.borsh.RustEnum;

public sealed interface AuthorityType extends RustEnum permits
  AuthorityType.SetAdmin,
  AuthorityType.SetBlacklistAuthority,
  AuthorityType.SetParameterAuthority {

  static AuthorityType read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> SetAdmin.read(_data, i);
      case 1 -> SetBlacklistAuthority.read(_data, i);
      case 2 -> SetParameterAuthority.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [AuthorityType]", ordinal
      ));
    };
  }

  record SetAdmin(OptionalInt val) implements OptionalEnumInt8, AuthorityType {

    public static SetAdmin read(final byte[] _data, int i) {
      return new SetAdmin(_data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record SetBlacklistAuthority(OptionalInt val) implements OptionalEnumInt8, AuthorityType {

    public static SetBlacklistAuthority read(final byte[] _data, int i) {
      return new SetBlacklistAuthority(_data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }

  record SetParameterAuthority(OptionalInt val) implements OptionalEnumInt8, AuthorityType {

    public static SetParameterAuthority read(final byte[] _data, int i) {
      return new SetParameterAuthority(_data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF));
    }

    @Override
    public int ordinal() {
      return 2;
    }
  }
}
