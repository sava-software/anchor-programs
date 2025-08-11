package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record IncreaseSizeLogV3(PublicKey owner,
                                PublicKey market,
                                long entryPrice,
                                int entryPriceExponent,
                                long deltaSizeAmount,
                                long deltaSizeUsd,
                                long finalSizeAmount,
                                long finalSizeUsd,
                                long finalCollateralAmount,
                                long finalCollateralUsd,
                                long feeAmount,
                                long feeRebateAmount,
                                long oracleAccountTime,
                                int oracleAccountType,
                                long oracleAccountPrice,
                                int oracleAccountPriceExponent) implements Borsh {

  public static final int BYTES = 161;

  public static IncreaseSizeLogV3 read(final byte[] _data, final int offset) {
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
    final var deltaSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var finalSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var finalSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var feeRebateAmount = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    return new IncreaseSizeLogV3(owner,
                                 market,
                                 entryPrice,
                                 entryPriceExponent,
                                 deltaSizeAmount,
                                 deltaSizeUsd,
                                 finalSizeAmount,
                                 finalSizeUsd,
                                 finalCollateralAmount,
                                 finalCollateralUsd,
                                 feeAmount,
                                 feeRebateAmount,
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
    putInt64LE(_data, i, deltaSizeAmount);
    i += 8;
    putInt64LE(_data, i, deltaSizeUsd);
    i += 8;
    putInt64LE(_data, i, finalSizeAmount);
    i += 8;
    putInt64LE(_data, i, finalSizeUsd);
    i += 8;
    putInt64LE(_data, i, finalCollateralAmount);
    i += 8;
    putInt64LE(_data, i, finalCollateralUsd);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, feeRebateAmount);
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
