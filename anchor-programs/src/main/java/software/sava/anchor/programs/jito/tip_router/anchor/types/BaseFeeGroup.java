package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

public record BaseFeeGroup(int group) implements Borsh {

  public static final int BYTES = 1;

  public static BaseFeeGroup read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var group = _data[offset] & 0xFF;
    return new BaseFeeGroup(group);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) group;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
