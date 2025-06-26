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

public record SettlePnlRecord(long ts,
                              PublicKey user,
                              int marketIndex,
                              BigInteger pnl,
                              long baseAssetAmount,
                              long quoteAssetAmountAfter,
                              long quoteEntryAmount,
                              long settlePrice,
                              SettlePnlExplanation explanation) implements Borsh {

  public static final int BYTES = 91;

  public static SettlePnlRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var user = readPubKey(_data, i);
    i += 32;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var pnl = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteAssetAmountAfter = getInt64LE(_data, i);
    i += 8;
    final var quoteEntryAmount = getInt64LE(_data, i);
    i += 8;
    final var settlePrice = getInt64LE(_data, i);
    i += 8;
    final var explanation = SettlePnlExplanation.read(_data, i);
    return new SettlePnlRecord(ts,
                               user,
                               marketIndex,
                               pnl,
                               baseAssetAmount,
                               quoteAssetAmountAfter,
                               quoteEntryAmount,
                               settlePrice,
                               explanation);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    user.write(_data, i);
    i += 32;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt128LE(_data, i, pnl);
    i += 16;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, quoteAssetAmountAfter);
    i += 8;
    putInt64LE(_data, i, quoteEntryAmount);
    i += 8;
    putInt64LE(_data, i, settlePrice);
    i += 8;
    i += Borsh.write(explanation, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
