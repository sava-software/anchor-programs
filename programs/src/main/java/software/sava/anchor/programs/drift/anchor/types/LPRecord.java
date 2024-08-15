package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LPRecord(long ts,
                       PublicKey user,
                       LPAction action,
                       long nShares,
                       int marketIndex,
                       long deltaBaseAssetAmount,
                       long deltaQuoteAssetAmount,
                       long pnl) implements Borsh {

  public static final int BYTES = 75;

  public static LPRecord read(final byte[] _data, final int offset) {
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var user = readPubKey(_data, i);
    i += 32;
    final var action = LPAction.read(_data, i);
    i += Borsh.len(action);
    final var nShares = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var deltaBaseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var deltaQuoteAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var pnl = getInt64LE(_data, i);
    return new LPRecord(ts,
                        user,
                        action,
                        nShares,
                        marketIndex,
                        deltaBaseAssetAmount,
                        deltaQuoteAssetAmount,
                        pnl);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    user.write(_data, i);
    i += 32;
    i += Borsh.write(action, _data, i);
    putInt64LE(_data, i, nShares);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, deltaBaseAssetAmount);
    i += 8;
    putInt64LE(_data, i, deltaQuoteAssetAmount);
    i += 8;
    putInt64LE(_data, i, pnl);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
