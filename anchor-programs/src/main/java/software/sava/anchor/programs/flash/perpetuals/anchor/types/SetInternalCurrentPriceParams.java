package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetInternalCurrentPriceParams(InternalPrice[] prices) implements Borsh {

  public static SetInternalCurrentPriceParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var prices = Borsh.readVector(InternalPrice.class, InternalPrice::read, _data, offset);
    return new SetInternalCurrentPriceParams(prices);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(prices, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(prices);
  }
}
