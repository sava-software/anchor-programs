package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddCollateralLogUSDv1(PublicKey owner,
                                    PublicKey market,
                                    long tradeId,
                                    long collateralPrice,
                                    int collateralPriceExponent,
                                    long deltaCollateralAmount,
                                    long deltaCollateralUsd,
                                    long finalCollateralUsd,
                                    long finalSizeAmount,
                                    long finalSizeUsd,
                                    long oracleAccountTime,
                                    int oracleAccountType,
                                    long oracleAccountPrice,
                                    int oracleAccountPriceExponent,
                                    long[] padding) implements Borsh {

  public static final int BYTES = 177;
  public static final int PADDING_LEN = 4;

  public static AddCollateralLogUSDv1 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var tradeId = getInt64LE(_data, i);
    i += 8;
    final var collateralPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var deltaCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var finalSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var finalSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new AddCollateralLogUSDv1(owner,
                                     market,
                                     tradeId,
                                     collateralPrice,
                                     collateralPriceExponent,
                                     deltaCollateralAmount,
                                     deltaCollateralUsd,
                                     finalCollateralUsd,
                                     finalSizeAmount,
                                     finalSizeUsd,
                                     oracleAccountTime,
                                     oracleAccountType,
                                     oracleAccountPrice,
                                     oracleAccountPriceExponent,
                                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    putInt64LE(_data, i, tradeId);
    i += 8;
    putInt64LE(_data, i, collateralPrice);
    i += 8;
    putInt32LE(_data, i, collateralPriceExponent);
    i += 4;
    putInt64LE(_data, i, deltaCollateralAmount);
    i += 8;
    putInt64LE(_data, i, deltaCollateralUsd);
    i += 8;
    putInt64LE(_data, i, finalCollateralUsd);
    i += 8;
    putInt64LE(_data, i, finalSizeAmount);
    i += 8;
    putInt64LE(_data, i, finalSizeUsd);
    i += 8;
    putInt64LE(_data, i, oracleAccountTime);
    i += 8;
    _data[i] = (byte) oracleAccountType;
    ++i;
    putInt64LE(_data, i, oracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, oracleAccountPriceExponent);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
