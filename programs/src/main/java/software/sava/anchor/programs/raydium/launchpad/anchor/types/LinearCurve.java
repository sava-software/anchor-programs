package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LinearCurve(long supply,
                          long totalQuoteFundRaising,
                          int migrateType) implements Borsh {

  public static final int BYTES = 17;

  public static LinearCurve read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var supply = getInt64LE(_data, i);
    i += 8;
    final var totalQuoteFundRaising = getInt64LE(_data, i);
    i += 8;
    final var migrateType = _data[i] & 0xFF;
    return new LinearCurve(supply, totalQuoteFundRaising, migrateType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, supply);
    i += 8;
    putInt64LE(_data, i, totalQuoteFundRaising);
    i += 8;
    _data[i] = (byte) migrateType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
