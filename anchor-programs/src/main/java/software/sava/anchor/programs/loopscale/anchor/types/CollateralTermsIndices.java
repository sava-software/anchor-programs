package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record CollateralTermsIndices(int collateralIndex, int durationIndex) implements Borsh {

  public static final int BYTES = 2;

  public static CollateralTermsIndices read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralIndex = _data[i] & 0xFF;
    ++i;
    final var durationIndex = _data[i] & 0xFF;
    return new CollateralTermsIndices(collateralIndex, durationIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) collateralIndex;
    ++i;
    _data[i] = (byte) durationIndex;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
