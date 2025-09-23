package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetInternalCurrentPriceParams(int useCurrentTime, InternalPrice[] prices) implements Borsh {

  public static SetInternalCurrentPriceParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var useCurrentTime = _data[i] & 0xFF;
    ++i;
    final var prices = Borsh.readVector(InternalPrice.class, InternalPrice::read, _data, i);
    return new SetInternalCurrentPriceParams(useCurrentTime, prices);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) useCurrentTime;
    ++i;
    i += Borsh.writeVector(prices, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + Borsh.lenVector(prices);
  }
}
