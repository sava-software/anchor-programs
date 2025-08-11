package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

public record ClosePositionParams(OraclePrice priceWithSlippage, Privilege privilege) implements Borsh {

  public static final int BYTES = 13;

  public static ClosePositionParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var priceWithSlippage = OraclePrice.read(_data, i);
    i += Borsh.len(priceWithSlippage);
    final var privilege = Privilege.read(_data, i);
    return new ClosePositionParams(priceWithSlippage, privilege);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(priceWithSlippage, _data, i);
    i += Borsh.write(privilege, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
