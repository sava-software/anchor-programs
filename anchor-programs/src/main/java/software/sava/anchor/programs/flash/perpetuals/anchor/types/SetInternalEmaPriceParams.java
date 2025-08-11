package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record SetInternalEmaPriceParams(InternalEmaPrice[] prices) implements Borsh {

  public static SetInternalEmaPriceParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var prices = Borsh.readVector(InternalEmaPrice.class, InternalEmaPrice::read, _data, offset);
    return new SetInternalEmaPriceParams(prices);
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
