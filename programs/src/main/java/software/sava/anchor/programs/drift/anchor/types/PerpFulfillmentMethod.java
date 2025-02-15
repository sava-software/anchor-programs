package software.sava.anchor.programs.drift.anchor.types;

import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public sealed interface PerpFulfillmentMethod extends RustEnum permits
  PerpFulfillmentMethod.AMM,
  PerpFulfillmentMethod.Match {

  static PerpFulfillmentMethod read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> AMM.read(_data, i);
      case 1 -> Match.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [PerpFulfillmentMethod]", ordinal
      ));
    };
  }

  record AMM(OptionalLong val) implements OptionalEnumInt64, PerpFulfillmentMethod {

    public static AMM read(final byte[] _data, int i) {
      return new AMM(_data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i)));
    }

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Match(PublicKey _publicKey,
               int _u16,
               long _u64) implements PerpFulfillmentMethod {

    public static final int BYTES = 42;

    public static Match read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      int i = offset;
      final var _publicKey = readPubKey(_data, i);
      i += 32;
      final var _u16 = getInt16LE(_data, i);
      i += 2;
      final var _u64 = getInt64LE(_data, i);
      return new Match(_publicKey, _u16, _u64);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _publicKey.write(_data, i);
      i += 32;
      putInt16LE(_data, i, _u16);
      i += 2;
      putInt64LE(_data, i, _u64);
      i += 8;
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
