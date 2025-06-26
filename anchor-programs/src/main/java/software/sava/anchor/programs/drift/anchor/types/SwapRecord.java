package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapRecord(long ts,
                         PublicKey user,
                         long amountOut,
                         long amountIn,
                         int outMarketIndex,
                         int inMarketIndex,
                         long outOraclePrice,
                         long inOraclePrice,
                         long fee) implements Borsh {

  public static final int BYTES = 84;

  public static SwapRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var user = readPubKey(_data, i);
    i += 32;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var outMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var inMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var outOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var inOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var fee = getInt64LE(_data, i);
    return new SwapRecord(ts,
                          user,
                          amountOut,
                          amountIn,
                          outMarketIndex,
                          inMarketIndex,
                          outOraclePrice,
                          inOraclePrice,
                          fee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    user.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt16LE(_data, i, outMarketIndex);
    i += 2;
    putInt16LE(_data, i, inMarketIndex);
    i += 2;
    putInt64LE(_data, i, outOraclePrice);
    i += 8;
    putInt64LE(_data, i, inOraclePrice);
    i += 8;
    putInt64LE(_data, i, fee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
