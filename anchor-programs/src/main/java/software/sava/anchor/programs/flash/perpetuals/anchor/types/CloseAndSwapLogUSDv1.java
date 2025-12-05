package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CloseAndSwapLogUSDv1(PublicKey owner,
                                   PublicKey market,
                                   long tradeId,
                                   long entryPrice,
                                   int entryPriceExponent,
                                   long duration,
                                   long exitPrice,
                                   int exitPriceExponent,
                                   long sizeAmount,
                                   long sizeUsd,
                                   long collateralPrice,
                                   int collateralPriceExponent,
                                   long collateralUsd,
                                   long profitUsd,
                                   long lossUsd,
                                   long feeUsd,
                                   long rebateUsd,
                                   long discountUsd,
                                   long exitFeeUsd,
                                   int outputCustodyUid,
                                   long outputAmount,
                                   long oracleAccountTime,
                                   int oracleAccountType,
                                   long oracleAccountPrice,
                                   int oracleAccountPriceExponent,
                                   long[] padding) implements Borsh {

  public static final int BYTES = 250;
  public static final int PADDING_LEN = 4;

  public static CloseAndSwapLogUSDv1 read(final byte[] _data, final int offset) {
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
    final var entryPrice = getInt64LE(_data, i);
    i += 8;
    final var entryPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var duration = getInt64LE(_data, i);
    i += 8;
    final var exitPrice = getInt64LE(_data, i);
    i += 8;
    final var exitPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var profitUsd = getInt64LE(_data, i);
    i += 8;
    final var lossUsd = getInt64LE(_data, i);
    i += 8;
    final var feeUsd = getInt64LE(_data, i);
    i += 8;
    final var rebateUsd = getInt64LE(_data, i);
    i += 8;
    final var discountUsd = getInt64LE(_data, i);
    i += 8;
    final var exitFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var outputCustodyUid = _data[i] & 0xFF;
    ++i;
    final var outputAmount = getInt64LE(_data, i);
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
    return new CloseAndSwapLogUSDv1(owner,
                                    market,
                                    tradeId,
                                    entryPrice,
                                    entryPriceExponent,
                                    duration,
                                    exitPrice,
                                    exitPriceExponent,
                                    sizeAmount,
                                    sizeUsd,
                                    collateralPrice,
                                    collateralPriceExponent,
                                    collateralUsd,
                                    profitUsd,
                                    lossUsd,
                                    feeUsd,
                                    rebateUsd,
                                    discountUsd,
                                    exitFeeUsd,
                                    outputCustodyUid,
                                    outputAmount,
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
    putInt64LE(_data, i, entryPrice);
    i += 8;
    putInt32LE(_data, i, entryPriceExponent);
    i += 4;
    putInt64LE(_data, i, duration);
    i += 8;
    putInt64LE(_data, i, exitPrice);
    i += 8;
    putInt32LE(_data, i, exitPriceExponent);
    i += 4;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, collateralPrice);
    i += 8;
    putInt32LE(_data, i, collateralPriceExponent);
    i += 4;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, profitUsd);
    i += 8;
    putInt64LE(_data, i, lossUsd);
    i += 8;
    putInt64LE(_data, i, feeUsd);
    i += 8;
    putInt64LE(_data, i, rebateUsd);
    i += 8;
    putInt64LE(_data, i, discountUsd);
    i += 8;
    putInt64LE(_data, i, exitFeeUsd);
    i += 8;
    _data[i] = (byte) outputCustodyUid;
    ++i;
    putInt64LE(_data, i, outputAmount);
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
