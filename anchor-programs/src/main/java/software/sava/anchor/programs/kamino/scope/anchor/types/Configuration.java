package software.sava.anchor.programs.kamino.scope.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Configuration(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey admin,
                            PublicKey oracleMappings,
                            PublicKey oraclePrices,
                            PublicKey tokensMetadata,
                            PublicKey oracleTwaps,
                            PublicKey adminCached,
                            long[] padding) implements Borsh {

  public static final int BYTES = 10240;
  public static final int PADDING_LEN = 1255;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ADMIN_OFFSET = 8;
  public static final int ORACLE_MAPPINGS_OFFSET = 40;
  public static final int ORACLE_PRICES_OFFSET = 72;
  public static final int TOKENS_METADATA_OFFSET = 104;
  public static final int ORACLE_TWAPS_OFFSET = 136;
  public static final int ADMIN_CACHED_OFFSET = 168;
  public static final int PADDING_OFFSET = 200;

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createOracleMappingsFilter(final PublicKey oracleMappings) {
    return Filter.createMemCompFilter(ORACLE_MAPPINGS_OFFSET, oracleMappings);
  }

  public static Filter createOraclePricesFilter(final PublicKey oraclePrices) {
    return Filter.createMemCompFilter(ORACLE_PRICES_OFFSET, oraclePrices);
  }

  public static Filter createTokensMetadataFilter(final PublicKey tokensMetadata) {
    return Filter.createMemCompFilter(TOKENS_METADATA_OFFSET, tokensMetadata);
  }

  public static Filter createOracleTwapsFilter(final PublicKey oracleTwaps) {
    return Filter.createMemCompFilter(ORACLE_TWAPS_OFFSET, oracleTwaps);
  }

  public static Filter createAdminCachedFilter(final PublicKey adminCached) {
    return Filter.createMemCompFilter(ADMIN_CACHED_OFFSET, adminCached);
  }

  public static Configuration read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Configuration read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Configuration read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Configuration> FACTORY = Configuration::read;

  public static Configuration read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var admin = readPubKey(_data, i);
    i += 32;
    final var oracleMappings = readPubKey(_data, i);
    i += 32;
    final var oraclePrices = readPubKey(_data, i);
    i += 32;
    final var tokensMetadata = readPubKey(_data, i);
    i += 32;
    final var oracleTwaps = readPubKey(_data, i);
    i += 32;
    final var adminCached = readPubKey(_data, i);
    i += 32;
    final var padding = new long[1255];
    Borsh.readArray(padding, _data, i);
    return new Configuration(_address,
                             discriminator,
                             admin,
                             oracleMappings,
                             oraclePrices,
                             tokensMetadata,
                             oracleTwaps,
                             adminCached,
                             padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    admin.write(_data, i);
    i += 32;
    oracleMappings.write(_data, i);
    i += 32;
    oraclePrices.write(_data, i);
    i += 32;
    tokensMetadata.write(_data, i);
    i += 32;
    oracleTwaps.write(_data, i);
    i += 32;
    adminCached.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(padding, 1255, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
