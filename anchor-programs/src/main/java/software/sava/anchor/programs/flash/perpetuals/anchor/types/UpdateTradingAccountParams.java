package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record UpdateTradingAccountParams(boolean updateReferer, boolean updateBooster) implements Borsh {

  public static final int BYTES = 2;

  public static UpdateTradingAccountParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var updateReferer = _data[i] == 1;
    ++i;
    final var updateBooster = _data[i] == 1;
    return new UpdateTradingAccountParams(updateReferer, updateBooster);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (updateReferer ? 1 : 0);
    ++i;
    _data[i] = (byte) (updateBooster ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
