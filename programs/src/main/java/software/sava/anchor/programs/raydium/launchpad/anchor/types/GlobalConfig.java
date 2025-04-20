package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Holds the current owner of the factory
public record GlobalConfig(PublicKey _address,
                           Discriminator discriminator,
                           // Account update epoch
                           long epoch,
                           // 0: Constant Product Curve
                           // 1: Fixed Price Curve
                           // 2: Linear Price Curve
                           int curveType,
                           // Config index
                           int index,
                           // The fee of migrate to amm
                           long migrateFee,
                           // The trade fee rate, denominated in hundredths of a bip (10^-6)
                           long tradeFeeRate,
                           // The maximum share fee rate, denominated in hundredths of a bip (10^-6)
                           long maxShareFeeRate,
                           // The minimum base supply, the value without decimals
                           long minBaseSupply,
                           // The maximum lock rate, denominated in hundredths of a bip (10^-6)
                           long maxLockRate,
                           // The minimum base sell rate, denominated in hundredths of a bip (10^-6)
                           long minBaseSellRate,
                           // The minimum base migrate rate, denominated in hundredths of a bip (10^-6)
                           long minBaseMigrateRate,
                           // The minimum quote fund raising, the value with decimals
                           long minQuoteFundRaising,
                           // Mint information for quote token
                           PublicKey quoteMint,
                           // Protocol Fee owner
                           PublicKey protocolFeeOwner,
                           // Migrate Fee owner
                           PublicKey migrateFeeOwner,
                           // Migrate to amm control wallet
                           PublicKey migrateToAmmWallet,
                           // Migrate to cpswap wallet
                           PublicKey migrateToCpswapWallet,
                           // padding for future updates
                           long[] padding) implements Borsh {

  public static final int BYTES = 371;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(149, 8, 156, 202, 160, 252, 176, 217);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int EPOCH_OFFSET = 8;
  public static final int CURVE_TYPE_OFFSET = 16;
  public static final int INDEX_OFFSET = 17;
  public static final int MIGRATE_FEE_OFFSET = 19;
  public static final int TRADE_FEE_RATE_OFFSET = 27;
  public static final int MAX_SHARE_FEE_RATE_OFFSET = 35;
  public static final int MIN_BASE_SUPPLY_OFFSET = 43;
  public static final int MAX_LOCK_RATE_OFFSET = 51;
  public static final int MIN_BASE_SELL_RATE_OFFSET = 59;
  public static final int MIN_BASE_MIGRATE_RATE_OFFSET = 67;
  public static final int MIN_QUOTE_FUND_RAISING_OFFSET = 75;
  public static final int QUOTE_MINT_OFFSET = 83;
  public static final int PROTOCOL_FEE_OWNER_OFFSET = 115;
  public static final int MIGRATE_FEE_OWNER_OFFSET = 147;
  public static final int MIGRATE_TO_AMM_WALLET_OFFSET = 179;
  public static final int MIGRATE_TO_CPSWAP_WALLET_OFFSET = 211;
  public static final int PADDING_OFFSET = 243;

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createCurveTypeFilter(final int curveType) {
    return Filter.createMemCompFilter(CURVE_TYPE_OFFSET, new byte[]{(byte) curveType});
  }

  public static Filter createIndexFilter(final int index) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, index);
    return Filter.createMemCompFilter(INDEX_OFFSET, _data);
  }

  public static Filter createMigrateFeeFilter(final long migrateFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, migrateFee);
    return Filter.createMemCompFilter(MIGRATE_FEE_OFFSET, _data);
  }

  public static Filter createTradeFeeRateFilter(final long tradeFeeRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tradeFeeRate);
    return Filter.createMemCompFilter(TRADE_FEE_RATE_OFFSET, _data);
  }

  public static Filter createMaxShareFeeRateFilter(final long maxShareFeeRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxShareFeeRate);
    return Filter.createMemCompFilter(MAX_SHARE_FEE_RATE_OFFSET, _data);
  }

  public static Filter createMinBaseSupplyFilter(final long minBaseSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minBaseSupply);
    return Filter.createMemCompFilter(MIN_BASE_SUPPLY_OFFSET, _data);
  }

  public static Filter createMaxLockRateFilter(final long maxLockRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxLockRate);
    return Filter.createMemCompFilter(MAX_LOCK_RATE_OFFSET, _data);
  }

  public static Filter createMinBaseSellRateFilter(final long minBaseSellRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minBaseSellRate);
    return Filter.createMemCompFilter(MIN_BASE_SELL_RATE_OFFSET, _data);
  }

  public static Filter createMinBaseMigrateRateFilter(final long minBaseMigrateRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minBaseMigrateRate);
    return Filter.createMemCompFilter(MIN_BASE_MIGRATE_RATE_OFFSET, _data);
  }

  public static Filter createMinQuoteFundRaisingFilter(final long minQuoteFundRaising) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minQuoteFundRaising);
    return Filter.createMemCompFilter(MIN_QUOTE_FUND_RAISING_OFFSET, _data);
  }

  public static Filter createQuoteMintFilter(final PublicKey quoteMint) {
    return Filter.createMemCompFilter(QUOTE_MINT_OFFSET, quoteMint);
  }

  public static Filter createProtocolFeeOwnerFilter(final PublicKey protocolFeeOwner) {
    return Filter.createMemCompFilter(PROTOCOL_FEE_OWNER_OFFSET, protocolFeeOwner);
  }

  public static Filter createMigrateFeeOwnerFilter(final PublicKey migrateFeeOwner) {
    return Filter.createMemCompFilter(MIGRATE_FEE_OWNER_OFFSET, migrateFeeOwner);
  }

  public static Filter createMigrateToAmmWalletFilter(final PublicKey migrateToAmmWallet) {
    return Filter.createMemCompFilter(MIGRATE_TO_AMM_WALLET_OFFSET, migrateToAmmWallet);
  }

  public static Filter createMigrateToCpswapWalletFilter(final PublicKey migrateToCpswapWallet) {
    return Filter.createMemCompFilter(MIGRATE_TO_CPSWAP_WALLET_OFFSET, migrateToCpswapWallet);
  }

  public static GlobalConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static GlobalConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static GlobalConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], GlobalConfig> FACTORY = GlobalConfig::read;

  public static GlobalConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var curveType = _data[i] & 0xFF;
    ++i;
    final var index = getInt16LE(_data, i);
    i += 2;
    final var migrateFee = getInt64LE(_data, i);
    i += 8;
    final var tradeFeeRate = getInt64LE(_data, i);
    i += 8;
    final var maxShareFeeRate = getInt64LE(_data, i);
    i += 8;
    final var minBaseSupply = getInt64LE(_data, i);
    i += 8;
    final var maxLockRate = getInt64LE(_data, i);
    i += 8;
    final var minBaseSellRate = getInt64LE(_data, i);
    i += 8;
    final var minBaseMigrateRate = getInt64LE(_data, i);
    i += 8;
    final var minQuoteFundRaising = getInt64LE(_data, i);
    i += 8;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var protocolFeeOwner = readPubKey(_data, i);
    i += 32;
    final var migrateFeeOwner = readPubKey(_data, i);
    i += 32;
    final var migrateToAmmWallet = readPubKey(_data, i);
    i += 32;
    final var migrateToCpswapWallet = readPubKey(_data, i);
    i += 32;
    final var padding = new long[16];
    Borsh.readArray(padding, _data, i);
    return new GlobalConfig(_address,
                            discriminator,
                            epoch,
                            curveType,
                            index,
                            migrateFee,
                            tradeFeeRate,
                            maxShareFeeRate,
                            minBaseSupply,
                            maxLockRate,
                            minBaseSellRate,
                            minBaseMigrateRate,
                            minQuoteFundRaising,
                            quoteMint,
                            protocolFeeOwner,
                            migrateFeeOwner,
                            migrateToAmmWallet,
                            migrateToCpswapWallet,
                            padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) curveType;
    ++i;
    putInt16LE(_data, i, index);
    i += 2;
    putInt64LE(_data, i, migrateFee);
    i += 8;
    putInt64LE(_data, i, tradeFeeRate);
    i += 8;
    putInt64LE(_data, i, maxShareFeeRate);
    i += 8;
    putInt64LE(_data, i, minBaseSupply);
    i += 8;
    putInt64LE(_data, i, maxLockRate);
    i += 8;
    putInt64LE(_data, i, minBaseSellRate);
    i += 8;
    putInt64LE(_data, i, minBaseMigrateRate);
    i += 8;
    putInt64LE(_data, i, minQuoteFundRaising);
    i += 8;
    quoteMint.write(_data, i);
    i += 32;
    protocolFeeOwner.write(_data, i);
    i += 32;
    migrateFeeOwner.write(_data, i);
    i += 32;
    migrateToAmmWallet.write(_data, i);
    i += 32;
    migrateToCpswapWallet.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
