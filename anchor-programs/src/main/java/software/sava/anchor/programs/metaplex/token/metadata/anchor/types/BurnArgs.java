package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface BurnArgs extends RustEnum permits
  BurnArgs.V1 {

  static BurnArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [BurnArgs]", ordinal
      ));
    };
  }

  record V1(long val) implements EnumInt64, BurnArgs {

    public static V1 read(final byte[] _data, int i) {
      return new V1(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }
}
