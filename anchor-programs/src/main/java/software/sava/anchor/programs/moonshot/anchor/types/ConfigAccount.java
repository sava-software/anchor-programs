package software.sava.anchor.programs.moonshot.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record ConfigAccount(PublicKey _address,
                            Discriminator discriminator,
                            PublicKey migrationAuthority,
                            PublicKey backendAuthority,
                            PublicKey configAuthority,
                            PublicKey helioFee,
                            PublicKey dexFee,
                            int feeBps,
                            int dexFeeShare,
                            long migrationFee,
                            long marketcapThreshold,
                            Currency marketcapCurrency,
                            int minSupportedDecimalPlaces,
                            int maxSupportedDecimalPlaces,
                            long minSupportedTokenSupply,
                            long maxSupportedTokenSupply,
                            int bump,
                            int coefB) implements Borsh {

  public static final int BYTES = 211;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int MIGRATION_AUTHORITY_OFFSET = 8;
  public static final int BACKEND_AUTHORITY_OFFSET = 40;
  public static final int CONFIG_AUTHORITY_OFFSET = 72;
  public static final int HELIO_FEE_OFFSET = 104;
  public static final int DEX_FEE_OFFSET = 136;
  public static final int FEE_BPS_OFFSET = 168;
  public static final int DEX_FEE_SHARE_OFFSET = 170;
  public static final int MIGRATION_FEE_OFFSET = 171;
  public static final int MARKETCAP_THRESHOLD_OFFSET = 179;
  public static final int MARKETCAP_CURRENCY_OFFSET = 187;
  public static final int MIN_SUPPORTED_DECIMAL_PLACES_OFFSET = 188;
  public static final int MAX_SUPPORTED_DECIMAL_PLACES_OFFSET = 189;
  public static final int MIN_SUPPORTED_TOKEN_SUPPLY_OFFSET = 190;
  public static final int MAX_SUPPORTED_TOKEN_SUPPLY_OFFSET = 198;
  public static final int BUMP_OFFSET = 206;
  public static final int COEF_B_OFFSET = 207;

  public static Filter createMigrationAuthorityFilter(final PublicKey migrationAuthority) {
    return Filter.createMemCompFilter(MIGRATION_AUTHORITY_OFFSET, migrationAuthority);
  }

  public static Filter createBackendAuthorityFilter(final PublicKey backendAuthority) {
    return Filter.createMemCompFilter(BACKEND_AUTHORITY_OFFSET, backendAuthority);
  }

  public static Filter createConfigAuthorityFilter(final PublicKey configAuthority) {
    return Filter.createMemCompFilter(CONFIG_AUTHORITY_OFFSET, configAuthority);
  }

  public static Filter createHelioFeeFilter(final PublicKey helioFee) {
    return Filter.createMemCompFilter(HELIO_FEE_OFFSET, helioFee);
  }

  public static Filter createDexFeeFilter(final PublicKey dexFee) {
    return Filter.createMemCompFilter(DEX_FEE_OFFSET, dexFee);
  }

  public static Filter createFeeBpsFilter(final int feeBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, feeBps);
    return Filter.createMemCompFilter(FEE_BPS_OFFSET, _data);
  }

  public static Filter createDexFeeShareFilter(final int dexFeeShare) {
    return Filter.createMemCompFilter(DEX_FEE_SHARE_OFFSET, new byte[]{(byte) dexFeeShare});
  }

  public static Filter createMigrationFeeFilter(final long migrationFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, migrationFee);
    return Filter.createMemCompFilter(MIGRATION_FEE_OFFSET, _data);
  }

  public static Filter createMarketcapThresholdFilter(final long marketcapThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, marketcapThreshold);
    return Filter.createMemCompFilter(MARKETCAP_THRESHOLD_OFFSET, _data);
  }

  public static Filter createMarketcapCurrencyFilter(final Currency marketcapCurrency) {
    return Filter.createMemCompFilter(MARKETCAP_CURRENCY_OFFSET, marketcapCurrency.write());
  }

  public static Filter createMinSupportedDecimalPlacesFilter(final int minSupportedDecimalPlaces) {
    return Filter.createMemCompFilter(MIN_SUPPORTED_DECIMAL_PLACES_OFFSET, new byte[]{(byte) minSupportedDecimalPlaces});
  }

  public static Filter createMaxSupportedDecimalPlacesFilter(final int maxSupportedDecimalPlaces) {
    return Filter.createMemCompFilter(MAX_SUPPORTED_DECIMAL_PLACES_OFFSET, new byte[]{(byte) maxSupportedDecimalPlaces});
  }

  public static Filter createMinSupportedTokenSupplyFilter(final long minSupportedTokenSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minSupportedTokenSupply);
    return Filter.createMemCompFilter(MIN_SUPPORTED_TOKEN_SUPPLY_OFFSET, _data);
  }

  public static Filter createMaxSupportedTokenSupplyFilter(final long maxSupportedTokenSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxSupportedTokenSupply);
    return Filter.createMemCompFilter(MAX_SUPPORTED_TOKEN_SUPPLY_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createCoefBFilter(final int coefB) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, coefB);
    return Filter.createMemCompFilter(COEF_B_OFFSET, _data);
  }

  public static ConfigAccount read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ConfigAccount read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ConfigAccount read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ConfigAccount> FACTORY = ConfigAccount::read;

  public static ConfigAccount read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var migrationAuthority = readPubKey(_data, i);
    i += 32;
    final var backendAuthority = readPubKey(_data, i);
    i += 32;
    final var configAuthority = readPubKey(_data, i);
    i += 32;
    final var helioFee = readPubKey(_data, i);
    i += 32;
    final var dexFee = readPubKey(_data, i);
    i += 32;
    final var feeBps = getInt16LE(_data, i);
    i += 2;
    final var dexFeeShare = _data[i] & 0xFF;
    ++i;
    final var migrationFee = getInt64LE(_data, i);
    i += 8;
    final var marketcapThreshold = getInt64LE(_data, i);
    i += 8;
    final var marketcapCurrency = Currency.read(_data, i);
    i += Borsh.len(marketcapCurrency);
    final var minSupportedDecimalPlaces = _data[i] & 0xFF;
    ++i;
    final var maxSupportedDecimalPlaces = _data[i] & 0xFF;
    ++i;
    final var minSupportedTokenSupply = getInt64LE(_data, i);
    i += 8;
    final var maxSupportedTokenSupply = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var coefB = getInt32LE(_data, i);
    return new ConfigAccount(_address,
                             discriminator,
                             migrationAuthority,
                             backendAuthority,
                             configAuthority,
                             helioFee,
                             dexFee,
                             feeBps,
                             dexFeeShare,
                             migrationFee,
                             marketcapThreshold,
                             marketcapCurrency,
                             minSupportedDecimalPlaces,
                             maxSupportedDecimalPlaces,
                             minSupportedTokenSupply,
                             maxSupportedTokenSupply,
                             bump,
                             coefB);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    migrationAuthority.write(_data, i);
    i += 32;
    backendAuthority.write(_data, i);
    i += 32;
    configAuthority.write(_data, i);
    i += 32;
    helioFee.write(_data, i);
    i += 32;
    dexFee.write(_data, i);
    i += 32;
    putInt16LE(_data, i, feeBps);
    i += 2;
    _data[i] = (byte) dexFeeShare;
    ++i;
    putInt64LE(_data, i, migrationFee);
    i += 8;
    putInt64LE(_data, i, marketcapThreshold);
    i += 8;
    i += Borsh.write(marketcapCurrency, _data, i);
    _data[i] = (byte) minSupportedDecimalPlaces;
    ++i;
    _data[i] = (byte) maxSupportedDecimalPlaces;
    ++i;
    putInt64LE(_data, i, minSupportedTokenSupply);
    i += 8;
    putInt64LE(_data, i, maxSupportedTokenSupply);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt32LE(_data, i, coefB);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
