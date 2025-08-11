package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record EditLimitOrderLog(PublicKey owner,
                                PublicKey market,
                                long limitPrice,
                                int limitPriceExponent,
                                long sizeAmount,
                                long sizeUsd,
                                long reservePrice,
                                int reservePriceExponent,
                                long reserveAmount,
                                long reserveUsd,
                                long stopLossPrice,
                                int stopLossPriceExponent,
                                long takeProfitPrice,
                                int takeProfitPriceExponent,
                                int receiveCustodyUid,
                                long oracleAccountTime,
                                int oracleAccountType,
                                long oracleAccountPrice,
                                int oracleAccountPriceExponent) implements Borsh {

  public static final int BYTES = 166;

  public static EditLimitOrderLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var limitPrice = getInt64LE(_data, i);
    i += 8;
    final var limitPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var reservePrice = getInt64LE(_data, i);
    i += 8;
    final var reservePriceExponent = getInt32LE(_data, i);
    i += 4;
    final var reserveAmount = getInt64LE(_data, i);
    i += 8;
    final var reserveUsd = getInt64LE(_data, i);
    i += 8;
    final var stopLossPrice = getInt64LE(_data, i);
    i += 8;
    final var stopLossPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var takeProfitPrice = getInt64LE(_data, i);
    i += 8;
    final var takeProfitPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var receiveCustodyUid = _data[i] & 0xFF;
    ++i;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    return new EditLimitOrderLog(owner,
                                 market,
                                 limitPrice,
                                 limitPriceExponent,
                                 sizeAmount,
                                 sizeUsd,
                                 reservePrice,
                                 reservePriceExponent,
                                 reserveAmount,
                                 reserveUsd,
                                 stopLossPrice,
                                 stopLossPriceExponent,
                                 takeProfitPrice,
                                 takeProfitPriceExponent,
                                 receiveCustodyUid,
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
    putInt64LE(_data, i, limitPrice);
    i += 8;
    putInt32LE(_data, i, limitPriceExponent);
    i += 4;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, reservePrice);
    i += 8;
    putInt32LE(_data, i, reservePriceExponent);
    i += 4;
    putInt64LE(_data, i, reserveAmount);
    i += 8;
    putInt64LE(_data, i, reserveUsd);
    i += 8;
    putInt64LE(_data, i, stopLossPrice);
    i += 8;
    putInt32LE(_data, i, stopLossPriceExponent);
    i += 4;
    putInt64LE(_data, i, takeProfitPrice);
    i += 8;
    putInt32LE(_data, i, takeProfitPriceExponent);
    i += 4;
    _data[i] = (byte) receiveCustodyUid;
    ++i;
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
