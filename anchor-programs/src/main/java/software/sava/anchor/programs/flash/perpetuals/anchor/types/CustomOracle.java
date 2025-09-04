package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record CustomOracle(PublicKey _address,
                           Discriminator discriminator,
                           long price,
                           int expo,
                           long conf,
                           long ema,
                           long publishTime) implements Borsh {

  public static final int BYTES = 44;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PRICE_OFFSET = 8;
  public static final int EXPO_OFFSET = 16;
  public static final int CONF_OFFSET = 20;
  public static final int EMA_OFFSET = 28;
  public static final int PUBLISH_TIME_OFFSET = 36;

  public static Filter createPriceFilter(final long price) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, price);
    return Filter.createMemCompFilter(PRICE_OFFSET, _data);
  }

  public static Filter createExpoFilter(final int expo) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, expo);
    return Filter.createMemCompFilter(EXPO_OFFSET, _data);
  }

  public static Filter createConfFilter(final long conf) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, conf);
    return Filter.createMemCompFilter(CONF_OFFSET, _data);
  }

  public static Filter createEmaFilter(final long ema) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ema);
    return Filter.createMemCompFilter(EMA_OFFSET, _data);
  }

  public static Filter createPublishTimeFilter(final long publishTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, publishTime);
    return Filter.createMemCompFilter(PUBLISH_TIME_OFFSET, _data);
  }

  public static CustomOracle read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static CustomOracle read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static CustomOracle read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], CustomOracle> FACTORY = CustomOracle::read;

  public static CustomOracle read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var price = getInt64LE(_data, i);
    i += 8;
    final var expo = getInt32LE(_data, i);
    i += 4;
    final var conf = getInt64LE(_data, i);
    i += 8;
    final var ema = getInt64LE(_data, i);
    i += 8;
    final var publishTime = getInt64LE(_data, i);
    return new CustomOracle(_address,
                            discriminator,
                            price,
                            expo,
                            conf,
                            ema,
                            publishTime);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, price);
    i += 8;
    putInt32LE(_data, i, expo);
    i += 4;
    putInt64LE(_data, i, conf);
    i += 8;
    putInt64LE(_data, i, ema);
    i += 8;
    putInt64LE(_data, i, publishTime);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
