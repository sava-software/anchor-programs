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

public record LPMintRedeemRecord(long ts,
                                 long slot,
                                 PublicKey authority,
                                 int description,
                                 BigInteger amount,
                                 BigInteger fee,
                                 int spotMarketIndex,
                                 int constituentIndex,
                                 long oraclePrice,
                                 PublicKey mint,
                                 long lpAmount,
                                 long lpFee,
                                 BigInteger lpPrice,
                                 long mintRedeemId,
                                 BigInteger lastAum,
                                 long lastAumSlot,
                                 long inMarketCurrentWeight,
                                 long inMarketTargetWeight,
                                 PublicKey lpPool) implements Borsh {

  public static final int BYTES = 237;

  public static LPMintRedeemRecord read(final byte[] _data, final int offset) {
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
    final var description = _data[i] & 0xFF;
    ++i;
    final var amount = getInt128LE(_data, i);
    i += 16;
    final var fee = getInt128LE(_data, i);
    i += 16;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var constituentIndex = getInt16LE(_data, i);
    i += 2;
    final var oraclePrice = getInt64LE(_data, i);
    i += 8;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var lpAmount = getInt64LE(_data, i);
    i += 8;
    final var lpFee = getInt64LE(_data, i);
    i += 8;
    final var lpPrice = getInt128LE(_data, i);
    i += 16;
    final var mintRedeemId = getInt64LE(_data, i);
    i += 8;
    final var lastAum = getInt128LE(_data, i);
    i += 16;
    final var lastAumSlot = getInt64LE(_data, i);
    i += 8;
    final var inMarketCurrentWeight = getInt64LE(_data, i);
    i += 8;
    final var inMarketTargetWeight = getInt64LE(_data, i);
    i += 8;
    final var lpPool = readPubKey(_data, i);
    return new LPMintRedeemRecord(ts,
                                  slot,
                                  authority,
                                  description,
                                  amount,
                                  fee,
                                  spotMarketIndex,
                                  constituentIndex,
                                  oraclePrice,
                                  mint,
                                  lpAmount,
                                  lpFee,
                                  lpPrice,
                                  mintRedeemId,
                                  lastAum,
                                  lastAumSlot,
                                  inMarketCurrentWeight,
                                  inMarketTargetWeight,
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
    _data[i] = (byte) description;
    ++i;
    putInt128LE(_data, i, amount);
    i += 16;
    putInt128LE(_data, i, fee);
    i += 16;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt16LE(_data, i, constituentIndex);
    i += 2;
    putInt64LE(_data, i, oraclePrice);
    i += 8;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lpAmount);
    i += 8;
    putInt64LE(_data, i, lpFee);
    i += 8;
    putInt128LE(_data, i, lpPrice);
    i += 16;
    putInt64LE(_data, i, mintRedeemId);
    i += 8;
    putInt128LE(_data, i, lastAum);
    i += 16;
    putInt64LE(_data, i, lastAumSlot);
    i += 8;
    putInt64LE(_data, i, inMarketCurrentWeight);
    i += 8;
    putInt64LE(_data, i, inMarketTargetWeight);
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
