package software.sava.anchor.programs.pyth.receiver.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PriceFeedMessage(byte[] feedId,
                               long price,
                               long conf,
                               int exponent,
                               long publishTime,
                               long prevPublishTime,
                               long emaPrice,
                               long emaConf) implements Borsh {

  public static final int BYTES = 84;
  public static final int FEED_ID_LEN = 32;

  public static PriceFeedMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feedId = new byte[32];
    i += Borsh.readArray(feedId, _data, i);
    final var price = getInt64LE(_data, i);
    i += 8;
    final var conf = getInt64LE(_data, i);
    i += 8;
    final var exponent = getInt32LE(_data, i);
    i += 4;
    final var publishTime = getInt64LE(_data, i);
    i += 8;
    final var prevPublishTime = getInt64LE(_data, i);
    i += 8;
    final var emaPrice = getInt64LE(_data, i);
    i += 8;
    final var emaConf = getInt64LE(_data, i);
    return new PriceFeedMessage(feedId,
                                price,
                                conf,
                                exponent,
                                publishTime,
                                prevPublishTime,
                                emaPrice,
                                emaConf);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(feedId, _data, i);
    putInt64LE(_data, i, price);
    i += 8;
    putInt64LE(_data, i, conf);
    i += 8;
    putInt32LE(_data, i, exponent);
    i += 4;
    putInt64LE(_data, i, publishTime);
    i += 8;
    putInt64LE(_data, i, prevPublishTime);
    i += 8;
    putInt64LE(_data, i, emaPrice);
    i += 8;
    putInt64LE(_data, i, emaConf);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
