package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DatedPrice(Price price,
                         long lastUpdatedSlot,
                         long unixTimestamp,
                         byte[] genericData) implements Borsh {

  public static final int BYTES = 56;
  public static final int GENERIC_DATA_LEN = 24;

  public static DatedPrice read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var price = Price.read(_data, i);
    i += Borsh.len(price);
    final var lastUpdatedSlot = getInt64LE(_data, i);
    i += 8;
    final var unixTimestamp = getInt64LE(_data, i);
    i += 8;
    final var genericData = new byte[24];
    Borsh.readArray(genericData, _data, i);
    return new DatedPrice(price,
                          lastUpdatedSlot,
                          unixTimestamp,
                          genericData);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(price, _data, i);
    putInt64LE(_data, i, lastUpdatedSlot);
    i += 8;
    putInt64LE(_data, i, unixTimestamp);
    i += 8;
    i += Borsh.writeArray(genericData, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
