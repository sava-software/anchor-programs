package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RFQOrderId(byte[] uuid, long maxTs) implements Borsh {

  public static final int BYTES = 16;

  public static RFQOrderId read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var uuid = new byte[8];
    i += Borsh.readArray(uuid, _data, i);
    final var maxTs = getInt64LE(_data, i);
    return new RFQOrderId(uuid, maxTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(uuid, _data, i);
    putInt64LE(_data, i, maxTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
