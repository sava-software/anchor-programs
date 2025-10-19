package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LPSwapRecord(long ts,
                           long slot,
                           PublicKey authority,
                           BigInteger outAmount,
                           BigInteger inAmount,
                           BigInteger outFee,
                           BigInteger inFee,
                           int outSpotMarketIndex,
                           int inSpotMarketIndex,
                           int outConstituentIndex,
                           int inConstituentIndex,
                           long outOraclePrice,
                           long inOraclePrice,
                           BigInteger lastAum,
                           long lastAumSlot,
                           long inMarketCurrentWeight,
                           long outMarketCurrentWeight,
                           long inMarketTargetWeight,
                           long outMarketTargetWeight,
                           long inSwapId,
                           long outSwapId,
                           PublicKey lpPool) implements Borsh {

  public static final int BYTES = 240;

  public static LPSwapRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var outAmount = getInt128LE(_data, i);
    i += 16;
    final var inAmount = getInt128LE(_data, i);
    i += 16;
    final var outFee = getInt128LE(_data, i);
    i += 16;
    final var inFee = getInt128LE(_data, i);
    i += 16;
    final var outSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var inSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var outConstituentIndex = getInt16LE(_data, i);
    i += 2;
    final var inConstituentIndex = getInt16LE(_data, i);
    i += 2;
    final var outOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var inOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var lastAum = getInt128LE(_data, i);
    i += 16;
    final var lastAumSlot = getInt64LE(_data, i);
    i += 8;
    final var inMarketCurrentWeight = getInt64LE(_data, i);
    i += 8;
    final var outMarketCurrentWeight = getInt64LE(_data, i);
    i += 8;
    final var inMarketTargetWeight = getInt64LE(_data, i);
    i += 8;
    final var outMarketTargetWeight = getInt64LE(_data, i);
    i += 8;
    final var inSwapId = getInt64LE(_data, i);
    i += 8;
    final var outSwapId = getInt64LE(_data, i);
    i += 8;
    final var lpPool = readPubKey(_data, i);
    return new LPSwapRecord(ts,
                            slot,
                            authority,
                            outAmount,
                            inAmount,
                            outFee,
                            inFee,
                            outSpotMarketIndex,
                            inSpotMarketIndex,
                            outConstituentIndex,
                            inConstituentIndex,
                            outOraclePrice,
                            inOraclePrice,
                            lastAum,
                            lastAumSlot,
                            inMarketCurrentWeight,
                            outMarketCurrentWeight,
                            inMarketTargetWeight,
                            outMarketTargetWeight,
                            inSwapId,
                            outSwapId,
                            lpPool);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt64LE(_data, i, slot);
    i += 8;
    authority.write(_data, i);
    i += 32;
    putInt128LE(_data, i, outAmount);
    i += 16;
    putInt128LE(_data, i, inAmount);
    i += 16;
    putInt128LE(_data, i, outFee);
    i += 16;
    putInt128LE(_data, i, inFee);
    i += 16;
    putInt16LE(_data, i, outSpotMarketIndex);
    i += 2;
    putInt16LE(_data, i, inSpotMarketIndex);
    i += 2;
    putInt16LE(_data, i, outConstituentIndex);
    i += 2;
    putInt16LE(_data, i, inConstituentIndex);
    i += 2;
    putInt64LE(_data, i, outOraclePrice);
    i += 8;
    putInt64LE(_data, i, inOraclePrice);
    i += 8;
    putInt128LE(_data, i, lastAum);
    i += 16;
    putInt64LE(_data, i, lastAumSlot);
    i += 8;
    putInt64LE(_data, i, inMarketCurrentWeight);
    i += 8;
    putInt64LE(_data, i, outMarketCurrentWeight);
    i += 8;
    putInt64LE(_data, i, inMarketTargetWeight);
    i += 8;
    putInt64LE(_data, i, outMarketTargetWeight);
    i += 8;
    putInt64LE(_data, i, inSwapId);
    i += 8;
    putInt64LE(_data, i, outSwapId);
    i += 8;
    lpPool.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
