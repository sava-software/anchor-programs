package software.sava.anchor.programs.jupiter.limit.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TradeEvent(PublicKey orderKey,
                         PublicKey taker,
                         long remainingInAmount,
                         long remainingOutAmount,
                         long inAmount,
                         long outAmount) implements Borsh {

  public static final int BYTES = 96;

  public static TradeEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var orderKey = readPubKey(_data, i);
    i += 32;
    final var taker = readPubKey(_data, i);
    i += 32;
    final var remainingInAmount = getInt64LE(_data, i);
    i += 8;
    final var remainingOutAmount = getInt64LE(_data, i);
    i += 8;
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var outAmount = getInt64LE(_data, i);
    return new TradeEvent(orderKey,
                          taker,
                          remainingInAmount,
                          remainingOutAmount,
                          inAmount,
                          outAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    orderKey.write(_data, i);
    i += 32;
    taker.write(_data, i);
    i += 32;
    putInt64LE(_data, i, remainingInAmount);
    i += 8;
    putInt64LE(_data, i, remainingOutAmount);
    i += 8;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
