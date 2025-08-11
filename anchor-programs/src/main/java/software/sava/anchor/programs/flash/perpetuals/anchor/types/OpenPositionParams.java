package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OpenPositionParams(OraclePrice priceWithSlippage,
                                 long collateralAmount,
                                 long sizeAmount,
                                 Privilege privilege) implements Borsh {

  public static final int BYTES = 29;

  public static OpenPositionParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var priceWithSlippage = OraclePrice.read(_data, i);
    i += Borsh.len(priceWithSlippage);
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var privilege = Privilege.read(_data, i);
    return new OpenPositionParams(priceWithSlippage,
                                  collateralAmount,
                                  sizeAmount,
                                  privilege);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(priceWithSlippage, _data, i);
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    i += Borsh.write(privilege, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
