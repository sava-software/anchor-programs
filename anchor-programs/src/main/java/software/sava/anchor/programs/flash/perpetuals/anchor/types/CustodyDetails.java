package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record CustodyDetails(OraclePrice minPrice, OraclePrice maxPrice) implements Borsh {

  public static final int BYTES = 24;

  public static CustodyDetails read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minPrice = OraclePrice.read(_data, i);
    i += Borsh.len(minPrice);
    final var maxPrice = OraclePrice.read(_data, i);
    return new CustodyDetails(minPrice, maxPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(minPrice, _data, i);
    i += Borsh.write(maxPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
