package software.sava.anchor.programs.pump.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TradeEvent(PublicKey mint,
                         long solAmount,
                         long tokenAmount,
                         boolean isBuy,
                         PublicKey user,
                         long timestamp,
                         long virtualSolReserves,
                         long virtualTokenReserves,
                         long realSolReserves,
                         long realTokenReserves) implements Borsh {

  public static final int BYTES = 121;

  public static TradeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var solAmount = getInt64LE(_data, i);
    i += 8;
    final var tokenAmount = getInt64LE(_data, i);
    i += 8;
    final var isBuy = _data[i] == 1;
    ++i;
    final var user = readPubKey(_data, i);
    i += 32;
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var virtualSolReserves = getInt64LE(_data, i);
    i += 8;
    final var virtualTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var realSolReserves = getInt64LE(_data, i);
    i += 8;
    final var realTokenReserves = getInt64LE(_data, i);
    return new TradeEvent(mint,
                          solAmount,
                          tokenAmount,
                          isBuy,
                          user,
                          timestamp,
                          virtualSolReserves,
                          virtualTokenReserves,
                          realSolReserves,
                          realTokenReserves);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, solAmount);
    i += 8;
    putInt64LE(_data, i, tokenAmount);
    i += 8;
    _data[i] = (byte) (isBuy ? 1 : 0);
    ++i;
    user.write(_data, i);
    i += 32;
    putInt64LE(_data, i, timestamp);
    i += 8;
    putInt64LE(_data, i, virtualSolReserves);
    i += 8;
    putInt64LE(_data, i, virtualTokenReserves);
    i += 8;
    putInt64LE(_data, i, realSolReserves);
    i += 8;
    putInt64LE(_data, i, realTokenReserves);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
