package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;

public sealed interface CollectionDetails extends RustEnum permits
  CollectionDetails.V1,
  CollectionDetails.V2 {

  static CollectionDetails read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      case 1 -> V2.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [CollectionDetails]", ordinal
      ));
    };
  }

  record V1(long val) implements EnumInt64, CollectionDetails {

    public static V1 read(final byte[] _data, int i) {
      return new V1(getInt64LE(_data, i));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record V2(byte[] val) implements CollectionDetails {

    public static final int BYTES = 8;
    public static final int VAL_LEN = 8;

    public static V2 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var val = new byte[8];
      Borsh.readArray(val, _data, offset);
      return new V2(val);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      i += Borsh.writeArray(val, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }

    @Override
    public int ordinal() {
      return 1;
    }
  }
}
