package software.sava.anchor.programs.metadao.autocrat.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CommonFields(long slot, long unixTimestamp) implements Borsh {

  public static final int BYTES = 16;

  public static CommonFields read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var unixTimestamp = getInt64LE(_data, i);
    return new CommonFields(slot, unixTimestamp);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt64LE(_data, i, unixTimestamp);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
