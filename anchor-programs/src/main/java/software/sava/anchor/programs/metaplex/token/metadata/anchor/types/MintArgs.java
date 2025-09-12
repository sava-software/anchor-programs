package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface MintArgs extends RustEnum permits
  MintArgs.V1 {

  static MintArgs read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> V1.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [MintArgs]", ordinal
      ));
    };
  }

  record V1(long amount, AuthorizationData authorizationData) implements MintArgs {

    public static V1 read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var authorizationData = _data[i++] == 0 ? null : AuthorizationData.read(_data, i);
      return new V1(amount, authorizationData);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      i += Borsh.writeOptional(authorizationData, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 1 + 8 + (authorizationData == null ? 1 : (1 + Borsh.len(authorizationData)));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }
}
