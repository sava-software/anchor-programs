package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record LockLoanParams(int unlockIdx) implements Borsh {

  public static final int BYTES = 1;

  public static LockLoanParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var unlockIdx = _data[offset] & 0xFF;
    return new LockLoanParams(unlockIdx);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) unlockIdx;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
