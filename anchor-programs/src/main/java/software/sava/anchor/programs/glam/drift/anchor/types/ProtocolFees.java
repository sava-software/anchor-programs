package software.sava.anchor.programs.glam.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record ProtocolFees(int baseFeeBps, int flowFeeBps) implements Borsh {

  public static final int BYTES = 4;

  public static ProtocolFees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var baseFeeBps = getInt16LE(_data, i);
    i += 2;
    final var flowFeeBps = getInt16LE(_data, i);
    return new ProtocolFees(baseFeeBps, flowFeeBps);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, baseFeeBps);
    i += 2;
    putInt16LE(_data, i, flowFeeBps);
    i += 2;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
