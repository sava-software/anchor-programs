package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ExecuteTriggerWithSwapLog(PublicKey owner,
                                        PublicKey market,
                                        long entryPrice,
                                        int entryPriceExponent,
                                        long duration,
                                        long exitPrice,
                                        int exitPriceExponent,
                                        long sizeAmount,
                                        long sizeUsd,
                                        long collateralPrice,
                                        int collateralPriceExponent,
                                        long collateralAmount,
                                        long profitUsd,
                                        long lossUsd,
                                        long feeAmount,
                                        long feeRebateAmount,
                                        long exitFeeAmount,
                                        boolean isStopLoss,
                                        long receiveCustodyUid,
                                        long receiveAmount,
                                        long swapFeeAmount,
                                        long oracleAccountTime,
                                        int oracleAccountType,
                                        long oracleAccountPrice,
                                        int oracleAccountPriceExponent,
                                        long triggerPrice,
                                        int triggerPriceExponent) implements Borsh {

  public static final int BYTES = 230;

  public static ExecuteTriggerWithSwapLog read(final byte[] _data, final int offset) {
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
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var collateralAmount = getInt64LE(_data, i);
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
    final var isStopLoss = _data[i] == 1;
    ++i;
    final var receiveCustodyUid = getInt64LE(_data, i);
    i += 8;
    final var receiveAmount = getInt64LE(_data, i);
    i += 8;
    final var swapFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var triggerPrice = getInt64LE(_data, i);
    i += 8;
    final var triggerPriceExponent = getInt32LE(_data, i);
    return new ExecuteTriggerWithSwapLog(owner,
                                         market,
                                         entryPrice,
                                         entryPriceExponent,
                                         duration,
                                         exitPrice,
                                         exitPriceExponent,
                                         sizeAmount,
                                         sizeUsd,
                                         collateralPrice,
                                         collateralPriceExponent,
                                         collateralAmount,
                                         profitUsd,
                                         lossUsd,
                                         feeAmount,
                                         feeRebateAmount,
                                         exitFeeAmount,
                                         isStopLoss,
                                         receiveCustodyUid,
                                         receiveAmount,
                                         swapFeeAmount,
                                         oracleAccountTime,
                                         oracleAccountType,
                                         oracleAccountPrice,
                                         oracleAccountPriceExponent,
                                         triggerPrice,
                                         triggerPriceExponent);
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
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, collateralPrice);
    i += 8;
    putInt32LE(_data, i, collateralPriceExponent);
    i += 4;
    putInt64LE(_data, i, collateralAmount);
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
    _data[i] = (byte) (isStopLoss ? 1 : 0);
    ++i;
    putInt64LE(_data, i, receiveCustodyUid);
    i += 8;
    putInt64LE(_data, i, receiveAmount);
    i += 8;
    putInt64LE(_data, i, swapFeeAmount);
    i += 8;
    putInt64LE(_data, i, oracleAccountTime);
    i += 8;
    _data[i] = (byte) oracleAccountType;
    ++i;
    putInt64LE(_data, i, oracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, oracleAccountPriceExponent);
    i += 4;
    putInt64LE(_data, i, triggerPrice);
    i += 8;
    putInt32LE(_data, i, triggerPriceExponent);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
