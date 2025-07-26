package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TokenMetadata(byte[] name,
                            long maxAgePriceSlots,
                            long groupIdsBitset,
                            long[] reserved) implements Borsh {

  public static final int BYTES = 168;
  public static final int NAME_LEN = 32;
  public static final int RESERVED_LEN = 15;

  public static TokenMetadata read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var maxAgePriceSlots = getInt64LE(_data, i);
    i += 8;
    final var groupIdsBitset = getInt64LE(_data, i);
    i += 8;
    final var reserved = new long[15];
    Borsh.readArray(reserved, _data, i);
    return new TokenMetadata(name,
                             maxAgePriceSlots,
                             groupIdsBitset,
                             reserved);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(name, _data, i);
    putInt64LE(_data, i, maxAgePriceSlots);
    i += 8;
    putInt64LE(_data, i, groupIdsBitset);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
