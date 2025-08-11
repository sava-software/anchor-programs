package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveCollateralAndSwapLog(PublicKey owner,
                                         PublicKey market,
                                         long collateralPrice,
                                         int collateralPriceExponent,
                                         long deltaCollateralAmount,
                                         long finalCollateralAmount,
                                         long finalCollateralUsd,
                                         long finalSizeAmount,
                                         long finalSizeUsd,
                                         long receiveCustodyUid,
                                         long receiveAmount,
                                         long swapFeeAmount,
                                         long oracleAccountTime,
                                         int oracleAccountType,
                                         long oracleAccountPrice,
                                         int oracleAccountPriceExponent) implements Borsh {

  public static final int BYTES = 161;

  public static RemoveCollateralAndSwapLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var collateralPrice = getInt64LE(_data, i);
    i += 8;
    final var collateralPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var deltaCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralAmount = getInt64LE(_data, i);
    i += 8;
    final var finalCollateralUsd = getInt64LE(_data, i);
    i += 8;
    final var finalSizeAmount = getInt64LE(_data, i);
    i += 8;
    final var finalSizeUsd = getInt64LE(_data, i);
    i += 8;
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
    return new RemoveCollateralAndSwapLog(owner,
                                          market,
                                          collateralPrice,
                                          collateralPriceExponent,
                                          deltaCollateralAmount,
                                          finalCollateralAmount,
                                          finalCollateralUsd,
                                          finalSizeAmount,
                                          finalSizeUsd,
                                          receiveCustodyUid,
                                          receiveAmount,
                                          swapFeeAmount,
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
    putInt64LE(_data, i, collateralPrice);
    i += 8;
    putInt32LE(_data, i, collateralPriceExponent);
    i += 4;
    putInt64LE(_data, i, deltaCollateralAmount);
    i += 8;
    putInt64LE(_data, i, finalCollateralAmount);
    i += 8;
    putInt64LE(_data, i, finalCollateralUsd);
    i += 8;
    putInt64LE(_data, i, finalSizeAmount);
    i += 8;
    putInt64LE(_data, i, finalSizeUsd);
    i += 8;
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
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
