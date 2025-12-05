package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record InitRebateVaultParams(boolean allowRebatePayout) implements Borsh {

  public static final int BYTES = 1;

  public static InitRebateVaultParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var allowRebatePayout = _data[offset] == 1;
    return new InitRebateVaultParams(allowRebatePayout);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (allowRebatePayout ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
