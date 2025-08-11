package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record RemoveCustodyParams(TokenRatios[] ratios) implements Borsh {

  public static RemoveCustodyParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var ratios = Borsh.readVector(TokenRatios.class, TokenRatios::read, _data, offset);
    return new RemoveCustodyParams(ratios);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(ratios, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(ratios);
  }
}
