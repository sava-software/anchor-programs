package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record Duration(PodU32 duration, int durationType) implements Borsh {

  public static final int BYTES = 5;

  public static Duration read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var duration = PodU32.read(_data, i);
    i += Borsh.len(duration);
    final var durationType = _data[i] & 0xFF;
    return new Duration(duration, durationType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(duration, _data, i);
    _data[i] = (byte) durationType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
