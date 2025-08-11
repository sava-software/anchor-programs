package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record CancelUnstakeTokenRequestParams(int withdrawRequestId) implements Borsh {

  public static final int BYTES = 1;

  public static CancelUnstakeTokenRequestParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var withdrawRequestId = _data[offset] & 0xFF;
    return new CancelUnstakeTokenRequestParams(withdrawRequestId);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) withdrawRequestId;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
