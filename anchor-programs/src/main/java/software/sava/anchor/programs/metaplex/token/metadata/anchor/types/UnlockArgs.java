package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.RustEnum;

public sealed interface UnlockArgs extends RustEnum permits
  UnlockArgs.V1 {

  static UnlockArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [UnlockArgs]", ordinal
      ));
    };
  }

  record V1(AuthorizationData val) implements BorshEnum, UnlockArgs {

    public static V1 read(final byte[] _data, final int offset) {
      return new V1(AuthorizationData.read(_data, offset));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }
}
