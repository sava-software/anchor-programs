package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InternalEmaPrice(long emaPrice, long publishTime) implements Borsh {

  public static final int BYTES = 16;

  public static InternalEmaPrice read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var emaPrice = getInt64LE(_data, i);
    i += 8;
    final var publishTime = getInt64LE(_data, i);
    return new InternalEmaPrice(emaPrice, publishTime);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, emaPrice);
    i += 8;
    putInt64LE(_data, i, publishTime);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
