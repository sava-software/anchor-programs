package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface PrintArgs extends RustEnum permits
  PrintArgs.V1,
  PrintArgs.V2 {

  static PrintArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      case 1 -> V2.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [PrintArgs]", ordinal
      ));
    };
  }

  record V1(long val) implements EnumInt64, PrintArgs {

    public static V1 read(final byte[] _data, int i) {
      return new V1(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record V2(long val) implements EnumInt64, PrintArgs {

    public static V2 read(final byte[] _data, int i) {
      return new V2(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
