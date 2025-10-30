package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CacheInfo(PublicKey oracle,
                        BigInteger lastFeePoolTokenAmount,
                        BigInteger lastNetPnlPoolTokenAmount,
                        BigInteger lastExchangeFees,
                        BigInteger lastSettleAmmExFees,
                        BigInteger lastSettleAmmPnl,
                        // BASE PRECISION
                        long position,
                        long slot,
                        long lastSettleAmount,
                        long lastSettleSlot,
                        long lastSettleTs,
                        long quoteOwedFromLpPool,
                        long ammInventoryLimit,
                        long oraclePrice,
                        long oracleSlot,
                        int oracleSource,
                        int oracleValidity,
                        int lpStatusForPerpMarket,
                        int ammPositionScalar,
                        byte[] padding) implements Borsh {

  public static final int BYTES = 224;
  public static final int PADDING_LEN = 36;

  public static CacheInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var lastFeePoolTokenAmount = getInt128LE(_data, i);
    i += 16;
    final var lastNetPnlPoolTokenAmount = getInt128LE(_data, i);
    i += 16;
    final var lastExchangeFees = getInt128LE(_data, i);
    i += 16;
    final var lastSettleAmmExFees = getInt128LE(_data, i);
    i += 16;
    final var lastSettleAmmPnl = getInt128LE(_data, i);
    i += 16;
    final var position = getInt64LE(_data, i);
    i += 8;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var lastSettleAmount = getInt64LE(_data, i);
    i += 8;
    final var lastSettleSlot = getInt64LE(_data, i);
    i += 8;
    final var lastSettleTs = getInt64LE(_data, i);
    i += 8;
    final var quoteOwedFromLpPool = getInt64LE(_data, i);
    i += 8;
    final var ammInventoryLimit = getInt64LE(_data, i);
    i += 8;
    final var oraclePrice = getInt64LE(_data, i);
    i += 8;
    final var oracleSlot = getInt64LE(_data, i);
    i += 8;
    final var oracleSource = _data[i] & 0xFF;
    ++i;
    final var oracleValidity = _data[i] & 0xFF;
    ++i;
    final var lpStatusForPerpMarket = _data[i] & 0xFF;
    ++i;
    final var ammPositionScalar = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[36];
    Borsh.readArray(padding, _data, i);
    return new CacheInfo(oracle,
                         lastFeePoolTokenAmount,
                         lastNetPnlPoolTokenAmount,
                         lastExchangeFees,
                         lastSettleAmmExFees,
                         lastSettleAmmPnl,
                         position,
                         slot,
                         lastSettleAmount,
                         lastSettleSlot,
                         lastSettleTs,
                         quoteOwedFromLpPool,
                         ammInventoryLimit,
                         oraclePrice,
                         oracleSlot,
                         oracleSource,
                         oracleValidity,
                         lpStatusForPerpMarket,
                         ammPositionScalar,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oracle.write(_data, i);
    i += 32;
    putInt128LE(_data, i, lastFeePoolTokenAmount);
    i += 16;
    putInt128LE(_data, i, lastNetPnlPoolTokenAmount);
    i += 16;
    putInt128LE(_data, i, lastExchangeFees);
    i += 16;
    putInt128LE(_data, i, lastSettleAmmExFees);
    i += 16;
    putInt128LE(_data, i, lastSettleAmmPnl);
    i += 16;
    putInt64LE(_data, i, position);
    i += 8;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt64LE(_data, i, lastSettleAmount);
    i += 8;
    putInt64LE(_data, i, lastSettleSlot);
    i += 8;
    putInt64LE(_data, i, lastSettleTs);
    i += 8;
    putInt64LE(_data, i, quoteOwedFromLpPool);
    i += 8;
    putInt64LE(_data, i, ammInventoryLimit);
    i += 8;
    putInt64LE(_data, i, oraclePrice);
    i += 8;
    putInt64LE(_data, i, oracleSlot);
    i += 8;
    _data[i] = (byte) oracleSource;
    ++i;
    _data[i] = (byte) oracleValidity;
    ++i;
    _data[i] = (byte) lpStatusForPerpMarket;
    ++i;
    _data[i] = (byte) ammPositionScalar;
    ++i;
    i += Borsh.writeArrayChecked(padding, 36, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
