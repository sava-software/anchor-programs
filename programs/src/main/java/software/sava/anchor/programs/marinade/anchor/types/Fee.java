package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record Fee(int basisPoints) implements Borsh {

  public static final int BYTES = 4;

  public static Fee read(final byte[] _data, final int offset) {
    final var basisPoints = getInt32LE(_data, offset);
    return new Fee(basisPoints);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, basisPoints);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
