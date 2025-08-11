package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Assets(long collateral,
                     long owned,
                     long locked) implements Borsh {

  public static final int BYTES = 24;

  public static Assets read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateral = getInt64LE(_data, i);
    i += 8;
    final var owned = getInt64LE(_data, i);
    i += 8;
    final var locked = getInt64LE(_data, i);
    return new Assets(collateral, owned, locked);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, collateral);
    i += 8;
    putInt64LE(_data, i, owned);
    i += 8;
    putInt64LE(_data, i, locked);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
