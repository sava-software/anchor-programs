package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ExecuteLimitWithSwapLogUSDv1(PublicKey owner,
                                           PublicKey market,
                                           long tradeId,
                                           long entryPrice,
                                           int entryPriceExponent,
                                           long deltaSizeAmount,
                                           long deltaSizeUsd,
                                           int reserveCustodyUid,
                                           long reservePrice,
                                           int reservePriceExponent,
                                           long reserveAmount,
                                           long deltaCollateralUsd,
                                           long feeUsd,
                                           long rebateUsd,
                                           long discountUsd,
                                           long entryFeeUsd,
                                           long oracleAccountTime,
                                           int oracleAccountType,
                                           long oracleAccountPrice,
                                           int oracleAccountPriceExponent,
                                           long limitPrice,
                                           int limitPriceExponent,
                                           long[] padding) implements Borsh {

  public static final int BYTES = 226;
  public static final int PADDING_LEN = 4;

  public static ExecuteLimitWithSwapLogUSDv1 read(final byte[] _data, final int offset) {
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
    final var deltaSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var reserveCustodyUid = _data[i] & 0xFF;
    ++i;
    final var reservePrice = getInt64LE(_data, i);
    i += 8;
    final var reservePriceExponent = getInt32LE(_data, i);
    i += 4;
    final var reserveAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var feeUsd = getInt64LE(_data, i);
    i += 8;
    final var rebateUsd = getInt64LE(_data, i);
    i += 8;
    final var discountUsd = getInt64LE(_data, i);
    i += 8;
    final var entryFeeUsd = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var limitPrice = getInt64LE(_data, i);
    i += 8;
    final var limitPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new ExecuteLimitWithSwapLogUSDv1(owner,
                                            market,
                                            tradeId,
                                            entryPrice,
                                            entryPriceExponent,
                                            deltaSizeAmount,
                                            deltaSizeUsd,
                                            reserveCustodyUid,
                                            reservePrice,
                                            reservePriceExponent,
                                            reserveAmount,
                                            deltaCollateralUsd,
                                            feeUsd,
                                            rebateUsd,
                                            discountUsd,
                                            entryFeeUsd,
                                            oracleAccountTime,
                                            oracleAccountType,
                                            oracleAccountPrice,
                                            oracleAccountPriceExponent,
                                            limitPrice,
                                            limitPriceExponent,
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
    putInt64LE(_data, i, deltaSizeAmount);
    i += 8;
    putInt64LE(_data, i, deltaSizeUsd);
    i += 8;
    _data[i] = (byte) reserveCustodyUid;
    ++i;
    putInt64LE(_data, i, reservePrice);
    i += 8;
    putInt32LE(_data, i, reservePriceExponent);
    i += 4;
    putInt64LE(_data, i, reserveAmount);
    i += 8;
    putInt64LE(_data, i, deltaCollateralUsd);
    i += 8;
    putInt64LE(_data, i, feeUsd);
    i += 8;
    putInt64LE(_data, i, rebateUsd);
    i += 8;
    putInt64LE(_data, i, discountUsd);
    i += 8;
    putInt64LE(_data, i, entryFeeUsd);
    i += 8;
    putInt64LE(_data, i, oracleAccountTime);
    i += 8;
    _data[i] = (byte) oracleAccountType;
    ++i;
    putInt64LE(_data, i, oracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, oracleAccountPriceExponent);
    i += 4;
    putInt64LE(_data, i, limitPrice);
    i += 8;
    putInt32LE(_data, i, limitPriceExponent);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
