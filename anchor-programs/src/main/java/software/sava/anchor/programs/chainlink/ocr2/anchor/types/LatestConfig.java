package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

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

public record LatestConfig(PublicKey _address,
                           Discriminator discriminator,
                           int configCount,
                           byte[] configDigest,
                           long blockNumber) implements Borsh {

  public static final int BYTES = 52;
  public static final int CONFIG_DIGEST_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int CONFIG_COUNT_OFFSET = 8;
  public static final int CONFIG_DIGEST_OFFSET = 12;
  public static final int BLOCK_NUMBER_OFFSET = 44;

  public static Filter createConfigCountFilter(final int configCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, configCount);
    return Filter.createMemCompFilter(CONFIG_COUNT_OFFSET, _data);
  }

  public static Filter createBlockNumberFilter(final long blockNumber) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockNumber);
    return Filter.createMemCompFilter(BLOCK_NUMBER_OFFSET, _data);
  }

  public static LatestConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static LatestConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static LatestConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], LatestConfig> FACTORY = LatestConfig::read;

  public static LatestConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var configCount = getInt32LE(_data, i);
    i += 4;
    final var configDigest = new byte[32];
    i += Borsh.readArray(configDigest, _data, i);
    final var blockNumber = getInt64LE(_data, i);
    return new LatestConfig(_address, discriminator, configCount, configDigest, blockNumber);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt32LE(_data, i, configCount);
    i += 4;
    i += Borsh.writeArray(configDigest, _data, i);
    putInt64LE(_data, i, blockNumber);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
