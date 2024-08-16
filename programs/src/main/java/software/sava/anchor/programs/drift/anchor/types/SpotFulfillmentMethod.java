package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.RustEnum;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public sealed interface SpotFulfillmentMethod extends RustEnum permits
  SpotFulfillmentMethod.ExternalMarket,
  SpotFulfillmentMethod.Match {

  static SpotFulfillmentMethod read(final byte[] _data, final int offset) {
    final int ordinal = _data[offset] & 0xFF;
    final int i = offset + 1;
    return switch (ordinal) {
      case 0 -> ExternalMarket.INSTANCE;
      case 1 -> Match.read(_data, i);
      default -> throw new IllegalStateException(java.lang.String.format(
          "Unexpected ordinal [%d] for enum [SpotFulfillmentMethod]", ordinal
      ));
    };
  }

  record ExternalMarket() implements EnumNone, SpotFulfillmentMethod {

    public static final ExternalMarket INSTANCE = new ExternalMarket();

    @Override
    public int ordinal() {
      return 0;
    }
  }

  record Match(PublicKey _publicKey, int _u16) implements SpotFulfillmentMethod {

    public static final int BYTES = 34;

    public static Match read(final byte[] _data, final int offset) {
      int i = offset;
      final var _publicKey = readPubKey(_data, i);
      i += 32;
      final var _u16 = getInt16LE(_data, i);
      return new Match(_publicKey, _u16);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = writeOrdinal(_data, offset);
      _publicKey.write(_data, i);
      i += 32;
      putInt16LE(_data, i, _u16);
      i += 2;
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
