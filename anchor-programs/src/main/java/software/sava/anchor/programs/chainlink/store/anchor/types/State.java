package software.sava.anchor.programs.chainlink.store.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record State(PublicKey _address,
                    Discriminator discriminator,
                    int version,
                    int vaultNonce,
                    int padding0,
                    int padding1,
                    PublicKey feed,
                    Config config,
                    OffchainConfig offchainConfig,
                    Oracles oracles) implements Borsh {

  public static final int BYTES = 6920;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VERSION_OFFSET = 8;
  public static final int VAULT_NONCE_OFFSET = 9;
  public static final int PADDING_0_OFFSET = 10;
  public static final int PADDING_1_OFFSET = 12;
  public static final int FEED_OFFSET = 16;
  public static final int CONFIG_OFFSET = 48;
  public static final int OFFCHAIN_CONFIG_OFFSET = 368;
  public static final int ORACLES_OFFSET = 4480;

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createVaultNonceFilter(final int vaultNonce) {
    return Filter.createMemCompFilter(VAULT_NONCE_OFFSET, new byte[]{(byte) vaultNonce});
  }

  public static Filter createPadding0Filter(final int padding0) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, padding0);
    return Filter.createMemCompFilter(PADDING_0_OFFSET, _data);
  }

  public static Filter createPadding1Filter(final int padding1) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding1);
    return Filter.createMemCompFilter(PADDING_1_OFFSET, _data);
  }

  public static Filter createFeedFilter(final PublicKey feed) {
    return Filter.createMemCompFilter(FEED_OFFSET, feed);
  }

  public static State read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static State read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static State read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], State> FACTORY = State::read;

  public static State read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = _data[i] & 0xFF;
    ++i;
    final var vaultNonce = _data[i] & 0xFF;
    ++i;
    final var padding0 = getInt16LE(_data, i);
    i += 2;
    final var padding1 = getInt32LE(_data, i);
    i += 4;
    final var feed = readPubKey(_data, i);
    i += 32;
    final var config = Config.read(_data, i);
    i += Borsh.len(config);
    final var offchainConfig = OffchainConfig.read(_data, i);
    i += Borsh.len(offchainConfig);
    final var oracles = Oracles.read(_data, i);
    return new State(_address,
                     discriminator,
                     version,
                     vaultNonce,
                     padding0,
                     padding1,
                     feed,
                     config,
                     offchainConfig,
                     oracles);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) version;
    ++i;
    _data[i] = (byte) vaultNonce;
    ++i;
    putInt16LE(_data, i, padding0);
    i += 2;
    putInt32LE(_data, i, padding1);
    i += 4;
    feed.write(_data, i);
    i += 32;
    i += Borsh.write(config, _data, i);
    i += Borsh.write(offchainConfig, _data, i);
    i += Borsh.write(oracles, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
