package software.sava.anchor.programs.glam.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ManagementFee(int feeBps) implements Borsh {

  public static final int BYTES = 2;

  public static ManagementFee read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var feeBps = getInt16LE(_data, offset);
    return new ManagementFee(feeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, feeBps);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
