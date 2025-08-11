package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OpenPositionLogV4(PublicKey owner,
                                PublicKey market,
                                long entryPrice,
                                int entryPriceExponent,
                                long sizeAmount,
                                long sizeUsd,
                                long collateralAmount,
                                long collateralUsd,
                                long feeAmount,
                                long feeRebateAmount,
                                long entryFeeAmount,
                                long oracleAccountTime,
                                int oracleAccountType,
                                long oracleAccountPrice,
                                int oracleAccountPriceExponent,
                                boolean isDegen,
                                byte[] padding) implements Borsh {

  public static final int BYTES = 185;
  public static final int PADDING_LEN = 31;

  public static OpenPositionLogV4 read(final byte[] _data, final int offset) {
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
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    i += 8;
    final var feeRebateAmount = getInt64LE(_data, i);
    i += 8;
    final var entryFeeAmount = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountTime = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountType = _data[i] & 0xFF;
    ++i;
    final var oracleAccountPrice = getInt64LE(_data, i);
    i += 8;
    final var oracleAccountPriceExponent = getInt32LE(_data, i);
    i += 4;
    final var isDegen = _data[i] == 1;
    ++i;
    final var padding = new byte[31];
    Borsh.readArray(padding, _data, i);
    return new OpenPositionLogV4(owner,
                                 market,
                                 entryPrice,
                                 entryPriceExponent,
                                 sizeAmount,
                                 sizeUsd,
                                 collateralAmount,
                                 collateralUsd,
                                 feeAmount,
                                 feeRebateAmount,
                                 entryFeeAmount,
                                 oracleAccountTime,
                                 oracleAccountType,
                                 oracleAccountPrice,
                                 oracleAccountPriceExponent,
                                 isDegen,
                                 padding);
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
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    putInt64LE(_data, i, feeRebateAmount);
    i += 8;
    putInt64LE(_data, i, entryFeeAmount);
    i += 8;
    putInt64LE(_data, i, oracleAccountTime);
    i += 8;
    _data[i] = (byte) oracleAccountType;
    ++i;
    putInt64LE(_data, i, oracleAccountPrice);
    i += 8;
    putInt32LE(_data, i, oracleAccountPriceExponent);
    i += 4;
    _data[i] = (byte) (isDegen ? 1 : 0);
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
