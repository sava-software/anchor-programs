package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DecreaseSizeLogV3(PublicKey owner,
                                PublicKey market,
                                long entryPrice,
                                int entryPriceExponent,
                                long duration,
                                long exitPrice,
                                int exitPriceExponent,
                                long deltaSizeAmount,
                                long deltaSizeUsd,
                                long finalSizeAmount,
                                long finalSizeUsd,
                                long collateralPrice,
                                int collateralPriceExponent,
                                long deltaCollateralAmount,
                                long profitUsd,
                                long lossUsd,
                                long feeAmount,
                                long feeRebateAmount,
                                long exitFeeAmount,
                                long oracleAccountTime,
                                int oracleAccountType,
                                long oracleAccountPrice,
                                int oracleAccountPriceExponent) implements Borsh {

  public static final int BYTES = 209;

  public static DecreaseSizeLogV3 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
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
    final var deltaSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var finalSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var finalSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var deltaCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var profitUsd = getInt64LE(_data, i);
    i += 8;
    final var lossUsd = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var feeRebateAmount = getInt64LE(_data, i);
    i += 8;
    final var exitFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    return new DecreaseSizeLogV3(owner,
                                 market,
                                 entryPrice,
                                 entryPriceExponent,
                                 duration,
                                 exitPrice,
                                 exitPriceExponent,
                                 deltaSizeAmount,
                                 deltaSizeUsd,
                                 finalSizeAmount,
                                 finalSizeUsd,
                                 collateralPrice,
                                 collateralPriceExponent,
                                 deltaCollateralAmount,
                                 profitUsd,
                                 lossUsd,
                                 feeAmount,
                                 feeRebateAmount,
                                 exitFeeAmount,
                                 oracleAccountTime,
                                 oracleAccountType,
                                 oracleAccountPrice,
                                 oracleAccountPriceExponent);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
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
    putInt64LE(_data, i, deltaSizeAmount);
    i += 8;
    putInt64LE(_data, i, deltaSizeUsd);
    i += 8;
    putInt64LE(_data, i, finalSizeAmount);
    i += 8;
    putInt64LE(_data, i, finalSizeUsd);
    i += 8;
    putInt64LE(_data, i, collateralPrice);
    i += 8;
    putInt32LE(_data, i, collateralPriceExponent);
    i += 4;
    putInt64LE(_data, i, deltaCollateralAmount);
    i += 8;
    putInt64LE(_data, i, profitUsd);
    i += 8;
    putInt64LE(_data, i, lossUsd);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, feeRebateAmount);
    i += 8;
    putInt64LE(_data, i, exitFeeAmount);
    i += 8;
    putInt64LE(_data, i, oracleAccountTime);
    i += 8;
    _data[i] = (byte) oracleAccountType;
    ++i;
    putInt64LE(_data, i, oracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, oracleAccountPriceExponent);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
