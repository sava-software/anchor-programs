package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PositionData(long collateralUsd,
                           long profitUsd,
                           long lossUsd,
                           long feeUsd,
                           long leverage,
                           OraclePrice liquidationPrice) implements Borsh {

  public static final int BYTES = 52;

  public static PositionData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var profitUsd = getInt64LE(_data, i);
    i += 8;
    final var lossUsd = getInt64LE(_data, i);
    i += 8;
    final var feeUsd = getInt64LE(_data, i);
    i += 8;
    final var leverage = getInt64LE(_data, i);
    i += 8;
    final var liquidationPrice = OraclePrice.read(_data, i);
    return new PositionData(collateralUsd,
                            profitUsd,
                            lossUsd,
                            feeUsd,
                            leverage,
                            liquidationPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, profitUsd);
    i += 8;
    putInt64LE(_data, i, lossUsd);
    i += 8;
    putInt64LE(_data, i, feeUsd);
    i += 8;
    putInt64LE(_data, i, leverage);
    i += 8;
    i += Borsh.write(liquidationPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
