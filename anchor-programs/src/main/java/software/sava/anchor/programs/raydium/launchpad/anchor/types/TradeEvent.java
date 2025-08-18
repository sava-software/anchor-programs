package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Emitted when trade process
public record TradeEvent(PublicKey poolState,
                         long totalBaseSell,
                         long virtualBase,
                         long virtualQuote,
                         long realBaseBefore,
                         long realQuoteBefore,
                         long realBaseAfter,
                         long realQuoteAfter,
                         long amountIn,
                         long amountOut,
                         long protocolFee,
                         long platformFee,
                         long creatorFee,
                         long shareFee,
                         TradeDirection tradeDirection,
                         PoolStatus poolStatus,
                         boolean exactIn) implements Borsh {

  public static final int BYTES = 139;

  public static TradeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolState = readPubKey(_data, i);
    i += 32;
    final var totalBaseSell = getInt64LE(_data, i);
    i += 8;
    final var virtualBase = getInt64LE(_data, i);
    i += 8;
    final var virtualQuote = getInt64LE(_data, i);
    i += 8;
    final var realBaseBefore = getInt64LE(_data, i);
    i += 8;
    final var realQuoteBefore = getInt64LE(_data, i);
    i += 8;
    final var realBaseAfter = getInt64LE(_data, i);
    i += 8;
    final var realQuoteAfter = getInt64LE(_data, i);
    i += 8;
    final var amountIn = getInt64LE(_data, i);
    i += 8;
    final var amountOut = getInt64LE(_data, i);
    i += 8;
    final var protocolFee = getInt64LE(_data, i);
    i += 8;
    final var platformFee = getInt64LE(_data, i);
    i += 8;
    final var creatorFee = getInt64LE(_data, i);
    i += 8;
    final var shareFee = getInt64LE(_data, i);
    i += 8;
    final var tradeDirection = TradeDirection.read(_data, i);
    i += Borsh.len(tradeDirection);
    final var poolStatus = PoolStatus.read(_data, i);
    i += Borsh.len(poolStatus);
    final var exactIn = _data[i] == 1;
    return new TradeEvent(poolState,
                          totalBaseSell,
                          virtualBase,
                          virtualQuote,
                          realBaseBefore,
                          realQuoteBefore,
                          realBaseAfter,
                          realQuoteAfter,
                          amountIn,
                          amountOut,
                          protocolFee,
                          platformFee,
                          creatorFee,
                          shareFee,
                          tradeDirection,
                          poolStatus,
                          exactIn);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    poolState.write(_data, i);
    i += 32;
    putInt64LE(_data, i, totalBaseSell);
    i += 8;
    putInt64LE(_data, i, virtualBase);
    i += 8;
    putInt64LE(_data, i, virtualQuote);
    i += 8;
    putInt64LE(_data, i, realBaseBefore);
    i += 8;
    putInt64LE(_data, i, realQuoteBefore);
    i += 8;
    putInt64LE(_data, i, realBaseAfter);
    i += 8;
    putInt64LE(_data, i, realQuoteAfter);
    i += 8;
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    putInt64LE(_data, i, platformFee);
    i += 8;
    putInt64LE(_data, i, creatorFee);
    i += 8;
    putInt64LE(_data, i, shareFee);
    i += 8;
    i += Borsh.write(tradeDirection, _data, i);
    i += Borsh.write(poolStatus, _data, i);
    _data[i] = (byte) (exactIn ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
