package software.sava.anchor.programs.pyth.receiver.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// The time weighted average price & conf for a feed over the window [start_time, end_time].
// This type is used to persist the calculated TWAP in TwapUpdate accounts on Solana.
public record TwapPrice(byte[] feedId,
                        long startTime,
                        long endTime,
                        long price,
                        long conf,
                        int exponent,
                        // Ratio out of 1_000_000, where a value of 1_000_000 represents
                        // all slots were missed and 0 represents no slots were missed.
                        int downSlotsRatio) implements Borsh {

  public static final int BYTES = 72;
  public static final int FEED_ID_LEN = 32;

  public static TwapPrice read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feedId = new byte[32];
    i += Borsh.readArray(feedId, _data, i);
    final var startTime = getInt64LE(_data, i);
    i += 8;
    final var endTime = getInt64LE(_data, i);
    i += 8;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var conf = getInt64LE(_data, i);
    i += 8;
    final var exponent = getInt32LE(_data, i);
    i += 4;
    final var downSlotsRatio = getInt32LE(_data, i);
    return new TwapPrice(feedId,
                         startTime,
                         endTime,
                         price,
                         conf,
                         exponent,
                         downSlotsRatio);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(feedId, 32, _data, i);
    putInt64LE(_data, i, startTime);
    i += 8;
    putInt64LE(_data, i, endTime);
    i += 8;
    putInt64LE(_data, i, price);
    i += 8;
    putInt64LE(_data, i, conf);
    i += 8;
    putInt32LE(_data, i, exponent);
    i += 4;
    putInt32LE(_data, i, downSlotsRatio);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
