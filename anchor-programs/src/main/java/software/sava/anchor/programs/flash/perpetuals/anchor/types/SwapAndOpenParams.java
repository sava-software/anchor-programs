package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapAndOpenParams(long amountIn,
                                long minCollateralAmountOut,
                                OraclePrice priceWithSlippage,
                                long sizeAmount,
                                Privilege privilege) implements Borsh {

  public static final int BYTES = 37;

  public static SwapAndOpenParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var minCollateralAmountOut = getInt64LE(_data, i);
    i += 8;
    final var priceWithSlippage = OraclePrice.read(_data, i);
    i += Borsh.len(priceWithSlippage);
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var privilege = Privilege.read(_data, i);
    return new SwapAndOpenParams(amountIn,
                                 minCollateralAmountOut,
                                 priceWithSlippage,
                                 sizeAmount,
                                 privilege);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minCollateralAmountOut);
    i += 8;
    i += Borsh.write(priceWithSlippage, _data, i);
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
