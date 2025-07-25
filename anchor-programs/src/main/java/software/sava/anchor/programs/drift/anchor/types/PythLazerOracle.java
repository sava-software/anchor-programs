package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PythLazerOracle(PublicKey _address,
                              Discriminator discriminator,
                              long price,
                              long publishTime,
                              long postedSlot,
                              int exponent,
                              byte[] padding,
                              long conf) implements Borsh {

  public static final int BYTES = 48;
  public static final int PADDING_LEN = 4;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PRICE_OFFSET = 8;
  public static final int PUBLISH_TIME_OFFSET = 16;
  public static final int POSTED_SLOT_OFFSET = 24;
  public static final int EXPONENT_OFFSET = 32;
  public static final int PADDING_OFFSET = 36;
  public static final int CONF_OFFSET = 40;

  public static Filter createPriceFilter(final long price) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, price);
    return Filter.createMemCompFilter(PRICE_OFFSET, _data);
  }

  public static Filter createPublishTimeFilter(final long publishTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, publishTime);
    return Filter.createMemCompFilter(PUBLISH_TIME_OFFSET, _data);
  }

  public static Filter createPostedSlotFilter(final long postedSlot) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, postedSlot);
    return Filter.createMemCompFilter(POSTED_SLOT_OFFSET, _data);
  }

  public static Filter createExponentFilter(final int exponent) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, exponent);
    return Filter.createMemCompFilter(EXPONENT_OFFSET, _data);
  }

  public static Filter createConfFilter(final long conf) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, conf);
    return Filter.createMemCompFilter(CONF_OFFSET, _data);
  }

  public static PythLazerOracle read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PythLazerOracle read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PythLazerOracle read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PythLazerOracle> FACTORY = PythLazerOracle::read;

  public static PythLazerOracle read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var price = getInt64LE(_data, i);
    i += 8;
    final var publishTime = getInt64LE(_data, i);
    i += 8;
    final var postedSlot = getInt64LE(_data, i);
    i += 8;
    final var exponent = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[4];
    i += Borsh.readArray(padding, _data, i);
    final var conf = getInt64LE(_data, i);
    return new PythLazerOracle(_address,
                               discriminator,
                               price,
                               publishTime,
                               postedSlot,
                               exponent,
                               padding,
                               conf);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, price);
    i += 8;
    putInt64LE(_data, i, publishTime);
    i += 8;
    putInt64LE(_data, i, postedSlot);
    i += 8;
    putInt32LE(_data, i, exponent);
    i += 4;
    i += Borsh.writeArray(padding, _data, i);
    putInt64LE(_data, i, conf);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
