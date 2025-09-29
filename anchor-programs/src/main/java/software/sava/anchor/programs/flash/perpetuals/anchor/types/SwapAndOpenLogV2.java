package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapAndOpenLogV2(String poolName, byte[] _poolName,
                               PublicKey owner,
                               long receivingCustodyUid,
                               long collateralCustodyUid,
                               long targetCustodyUid,
                               long amountIn,
                               long swapAmountOut,
                               long swapFeeAmount,
                               PublicKey market,
                               long entryPrice,
                               int entryPriceExponent,
                               long sizeAmount,
                               long sizeUsd,
                               long collateralPrice,
                               int collateralPriceExponent,
                               long collateralAmount,
                               long collateralUsd,
                               long positionFeeAmount,
                               long feeRebateAmount,
                               long entryFeeAmount,
                               long receivingOracleAccountTime,
                               int receivingOracleAccountType,
                               long receivingOracleAccountPrice,
                               int receivingOracleAccountPriceExponent,
                               long collateralOracleAccountTime,
                               int collateralOracleAccountType,
                               long collateralOracleAccountPrice,
                               int collateralOracleAccountPriceExponent,
                               long targetOracleAccountTime,
                               int targetOracleAccountType,
                               long targetOracleAccountPrice,
                               int targetOracleAccountPriceExponent,
                               boolean isDegen,
                               byte[] padding) implements Borsh {

  public static final int PADDING_LEN = 31;
  public static SwapAndOpenLogV2 createRecord(final String poolName,
                                              final PublicKey owner,
                                              final long receivingCustodyUid,
                                              final long collateralCustodyUid,
                                              final long targetCustodyUid,
                                              final long amountIn,
                                              final long swapAmountOut,
                                              final long swapFeeAmount,
                                              final PublicKey market,
                                              final long entryPrice,
                                              final int entryPriceExponent,
                                              final long sizeAmount,
                                              final long sizeUsd,
                                              final long collateralPrice,
                                              final int collateralPriceExponent,
                                              final long collateralAmount,
                                              final long collateralUsd,
                                              final long positionFeeAmount,
                                              final long feeRebateAmount,
                                              final long entryFeeAmount,
                                              final long receivingOracleAccountTime,
                                              final int receivingOracleAccountType,
                                              final long receivingOracleAccountPrice,
                                              final int receivingOracleAccountPriceExponent,
                                              final long collateralOracleAccountTime,
                                              final int collateralOracleAccountType,
                                              final long collateralOracleAccountPrice,
                                              final int collateralOracleAccountPriceExponent,
                                              final long targetOracleAccountTime,
                                              final int targetOracleAccountType,
                                              final long targetOracleAccountPrice,
                                              final int targetOracleAccountPriceExponent,
                                              final boolean isDegen,
                                              final byte[] padding) {
    return new SwapAndOpenLogV2(poolName, poolName.getBytes(UTF_8),
                                owner,
                                receivingCustodyUid,
                                collateralCustodyUid,
                                targetCustodyUid,
                                amountIn,
                                swapAmountOut,
                                swapFeeAmount,
                                market,
                                entryPrice,
                                entryPriceExponent,
                                sizeAmount,
                                sizeUsd,
                                collateralPrice,
                                collateralPriceExponent,
                                collateralAmount,
                                collateralUsd,
                                positionFeeAmount,
                                feeRebateAmount,
                                entryFeeAmount,
                                receivingOracleAccountTime,
                                receivingOracleAccountType,
                                receivingOracleAccountPrice,
                                receivingOracleAccountPriceExponent,
                                collateralOracleAccountTime,
                                collateralOracleAccountType,
                                collateralOracleAccountPrice,
                                collateralOracleAccountPriceExponent,
                                targetOracleAccountTime,
                                targetOracleAccountType,
                                targetOracleAccountPrice,
                                targetOracleAccountPriceExponent,
                                isDegen,
                                padding);
  }

  public static SwapAndOpenLogV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var owner = readPubKey(_data, i);
    i += 32;
    final var receivingCustodyUid = getInt64LE(_data, i);
    i += 8;
    final var collateralCustodyUid = getInt64LE(_data, i);
    i += 8;
    final var targetCustodyUid = getInt64LE(_data, i);
    i += 8;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var swapAmountOut = getInt64LE(_data, i);
    i += 8;
    final var swapFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var market = readPubKey(_data, i);
    i += 32;
    final var entryPrice = getInt64LE(_data, i);
    i += 8;
    final var entryPriceExponent = getInt32LE(_data, i);
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
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var positionFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var feeRebateAmount = getInt64LE(_data, i);
    i += 8;
    final var entryFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var receivingOracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var receivingOracleAccountType = _data[i] & 0xFF;
    ++i;
    final var receivingOracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var receivingOracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var collateralOracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var collateralOracleAccountType = _data[i] & 0xFF;
    ++i;
    final var collateralOracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralOracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var targetOracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var targetOracleAccountType = _data[i] & 0xFF;
    ++i;
    final var targetOracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var targetOracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var isDegen = _data[i] == 1;
    ++i;
    final var padding = new byte[31];
    Borsh.readArray(padding, _data, i);
    return new SwapAndOpenLogV2(poolName, poolName.getBytes(UTF_8),
                                owner,
                                receivingCustodyUid,
                                collateralCustodyUid,
                                targetCustodyUid,
                                amountIn,
                                swapAmountOut,
                                swapFeeAmount,
                                market,
                                entryPrice,
                                entryPriceExponent,
                                sizeAmount,
                                sizeUsd,
                                collateralPrice,
                                collateralPriceExponent,
                                collateralAmount,
                                collateralUsd,
                                positionFeeAmount,
                                feeRebateAmount,
                                entryFeeAmount,
                                receivingOracleAccountTime,
                                receivingOracleAccountType,
                                receivingOracleAccountPrice,
                                receivingOracleAccountPriceExponent,
                                collateralOracleAccountTime,
                                collateralOracleAccountType,
                                collateralOracleAccountPrice,
                                collateralOracleAccountPriceExponent,
                                targetOracleAccountTime,
                                targetOracleAccountType,
                                targetOracleAccountPrice,
                                targetOracleAccountPriceExponent,
                                isDegen,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    owner.write(_data, i);
    i += 32;
    putInt64LE(_data, i, receivingCustodyUid);
    i += 8;
    putInt64LE(_data, i, collateralCustodyUid);
    i += 8;
    putInt64LE(_data, i, targetCustodyUid);
    i += 8;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, swapAmountOut);
    i += 8;
    putInt64LE(_data, i, swapFeeAmount);
    i += 8;
    market.write(_data, i);
    i += 32;
    putInt64LE(_data, i, entryPrice);
    i += 8;
    putInt32LE(_data, i, entryPriceExponent);
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
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, positionFeeAmount);
    i += 8;
    putInt64LE(_data, i, feeRebateAmount);
    i += 8;
    putInt64LE(_data, i, entryFeeAmount);
    i += 8;
    putInt64LE(_data, i, receivingOracleAccountTime);
    i += 8;
    _data[i] = (byte) receivingOracleAccountType;
    ++i;
    putInt64LE(_data, i, receivingOracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, receivingOracleAccountPriceExponent);
    i += 4;
    putInt64LE(_data, i, collateralOracleAccountTime);
    i += 8;
    _data[i] = (byte) collateralOracleAccountType;
    ++i;
    putInt64LE(_data, i, collateralOracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, collateralOracleAccountPriceExponent);
    i += 4;
    putInt64LE(_data, i, targetOracleAccountTime);
    i += 8;
    _data[i] = (byte) targetOracleAccountType;
    ++i;
    putInt64LE(_data, i, targetOracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, targetOracleAccountPriceExponent);
    i += 4;
    _data[i] = (byte) (isDegen ? 1 : 0);
    ++i;
    i += Borsh.writeArrayChecked(padding, 31, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName)
         + 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 32
         + 8
         + 4
         + 8
         + 8
         + 8
         + 4
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 1
         + 8
         + 4
         + 8
         + 1
         + 8
         + 4
         + 8
         + 1
         + 8
         + 4
         + 1
         + Borsh.lenArray(padding);
  }
}
