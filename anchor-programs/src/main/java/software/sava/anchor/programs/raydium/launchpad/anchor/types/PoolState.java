package software.sava.anchor.programs.raydium.launchpad.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Represents the state of a trading pool in the protocol
// Stores all essential information about pool balances, fees, and configuration
public record PoolState(PublicKey _address,
                        Discriminator discriminator,
                        // Account update epoch
                        long epoch,
                        // Bump seed used for PDA address derivation
                        int authBump,
                        // Current status of the pool
                        // * 0: Pool is funding
                        // * 1: Pool funding is end, waiting for migration
                        // * 2: Pool migration is done
                        int status,
                        // Decimals of the pool base token
                        int baseDecimals,
                        // Decimals of the pool quote token
                        int quoteDecimals,
                        // Migrate to AMM or CpSwap, 0: amm��� 1: cpswap
                        int migrateType,
                        // Supply of the pool base token
                        long supply,
                        // Total sell amount of the base token
                        long totalBaseSell,
                        // For different curves, virtual_base and virtual_quote have different meanings
                        // For constant product curve, virtual_base and virtual_quote are virtual liquidity, virtual_quote/virtual_base is the initial price
                        // For linear price curve, virtual_base is the price slope parameter a, virtual_quote has no effect
                        // For fixed price curve, virtual_quote/virtual_base is the initial price
                        long virtualBase,
                        long virtualQuote,
                        // Actual base token amount in the pool
                        // Represents the real tokens available for trading
                        long realBase,
                        // Actual quote token amount in the pool
                        // Represents the real tokens available for trading
                        long realQuote,
                        // The total quote fund raising of the pool
                        long totalQuoteFundRaising,
                        // Accumulated trading fees in quote tokens
                        // Can be collected by the protocol fee owner
                        long quoteProtocolFee,
                        // Accumulated platform fees in quote tokens
                        // Can be collected by the platform wallet stored in platform config
                        long platformFee,
                        // The fee of migrate to amm
                        long migrateFee,
                        // Vesting schedule for the base token
                        VestingSchedule vestingSchedule,
                        // Public key of the global configuration account
                        // Contains protocol-wide settings this pool adheres to
                        PublicKey globalConfig,
                        // Public key of the platform configuration account
                        // Contains platform-wide settings this pool adheres to
                        PublicKey platformConfig,
                        // Public key of the base mint address
                        PublicKey baseMint,
                        // Public key of the quote mint address
                        PublicKey quoteMint,
                        // Public key of the base token vault
                        // Holds the actual base tokens owned by the pool
                        PublicKey baseVault,
                        // Public key of the quote token vault
                        // Holds the actual quote tokens owned by the pool
                        PublicKey quoteVault,
                        // The creator of base token
                        PublicKey creator,
                        // token program bits
                        // bit0: base token program flag
                        // 0: spl_token_program
                        // 1: token_program_2022
                        // 
                        // bit1: quote token program flag
                        // 0: spl_token_program
                        // 1: token_program_2022
                        int tokenProgramFlag,
                        // migrate to cpmm, creator fee on quote token or both token
                        AmmCreatorFeeOn ammCreatorFeeOn,
                        // padding for future updates
                        byte[] padding) implements Borsh {

  public static final int BYTES = 429;
  public static final int PADDING_LEN = 62;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(247, 237, 227, 245, 215, 195, 222, 70);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int EPOCH_OFFSET = 8;
  public static final int AUTH_BUMP_OFFSET = 16;
  public static final int STATUS_OFFSET = 17;
  public static final int BASE_DECIMALS_OFFSET = 18;
  public static final int QUOTE_DECIMALS_OFFSET = 19;
  public static final int MIGRATE_TYPE_OFFSET = 20;
  public static final int SUPPLY_OFFSET = 21;
  public static final int TOTAL_BASE_SELL_OFFSET = 29;
  public static final int VIRTUAL_BASE_OFFSET = 37;
  public static final int VIRTUAL_QUOTE_OFFSET = 45;
  public static final int REAL_BASE_OFFSET = 53;
  public static final int REAL_QUOTE_OFFSET = 61;
  public static final int TOTAL_QUOTE_FUND_RAISING_OFFSET = 69;
  public static final int QUOTE_PROTOCOL_FEE_OFFSET = 77;
  public static final int PLATFORM_FEE_OFFSET = 85;
  public static final int MIGRATE_FEE_OFFSET = 93;
  public static final int VESTING_SCHEDULE_OFFSET = 101;
  public static final int GLOBAL_CONFIG_OFFSET = 141;
  public static final int PLATFORM_CONFIG_OFFSET = 173;
  public static final int BASE_MINT_OFFSET = 205;
  public static final int QUOTE_MINT_OFFSET = 237;
  public static final int BASE_VAULT_OFFSET = 269;
  public static final int QUOTE_VAULT_OFFSET = 301;
  public static final int CREATOR_OFFSET = 333;
  public static final int TOKEN_PROGRAM_FLAG_OFFSET = 365;
  public static final int AMM_CREATOR_FEE_ON_OFFSET = 366;
  public static final int PADDING_OFFSET = 367;

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createAuthBumpFilter(final int authBump) {
    return Filter.createMemCompFilter(AUTH_BUMP_OFFSET, new byte[]{(byte) authBump});
  }

  public static Filter createStatusFilter(final int status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, new byte[]{(byte) status});
  }

  public static Filter createBaseDecimalsFilter(final int baseDecimals) {
    return Filter.createMemCompFilter(BASE_DECIMALS_OFFSET, new byte[]{(byte) baseDecimals});
  }

  public static Filter createQuoteDecimalsFilter(final int quoteDecimals) {
    return Filter.createMemCompFilter(QUOTE_DECIMALS_OFFSET, new byte[]{(byte) quoteDecimals});
  }

  public static Filter createMigrateTypeFilter(final int migrateType) {
    return Filter.createMemCompFilter(MIGRATE_TYPE_OFFSET, new byte[]{(byte) migrateType});
  }

  public static Filter createSupplyFilter(final long supply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, supply);
    return Filter.createMemCompFilter(SUPPLY_OFFSET, _data);
  }

  public static Filter createTotalBaseSellFilter(final long totalBaseSell) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalBaseSell);
    return Filter.createMemCompFilter(TOTAL_BASE_SELL_OFFSET, _data);
  }

  public static Filter createVirtualBaseFilter(final long virtualBase) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, virtualBase);
    return Filter.createMemCompFilter(VIRTUAL_BASE_OFFSET, _data);
  }

  public static Filter createVirtualQuoteFilter(final long virtualQuote) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, virtualQuote);
    return Filter.createMemCompFilter(VIRTUAL_QUOTE_OFFSET, _data);
  }

  public static Filter createRealBaseFilter(final long realBase) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, realBase);
    return Filter.createMemCompFilter(REAL_BASE_OFFSET, _data);
  }

  public static Filter createRealQuoteFilter(final long realQuote) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, realQuote);
    return Filter.createMemCompFilter(REAL_QUOTE_OFFSET, _data);
  }

  public static Filter createTotalQuoteFundRaisingFilter(final long totalQuoteFundRaising) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalQuoteFundRaising);
    return Filter.createMemCompFilter(TOTAL_QUOTE_FUND_RAISING_OFFSET, _data);
  }

  public static Filter createQuoteProtocolFeeFilter(final long quoteProtocolFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, quoteProtocolFee);
    return Filter.createMemCompFilter(QUOTE_PROTOCOL_FEE_OFFSET, _data);
  }

  public static Filter createPlatformFeeFilter(final long platformFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, platformFee);
    return Filter.createMemCompFilter(PLATFORM_FEE_OFFSET, _data);
  }

  public static Filter createMigrateFeeFilter(final long migrateFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, migrateFee);
    return Filter.createMemCompFilter(MIGRATE_FEE_OFFSET, _data);
  }

  public static Filter createVestingScheduleFilter(final VestingSchedule vestingSchedule) {
    return Filter.createMemCompFilter(VESTING_SCHEDULE_OFFSET, vestingSchedule.write());
  }

  public static Filter createGlobalConfigFilter(final PublicKey globalConfig) {
    return Filter.createMemCompFilter(GLOBAL_CONFIG_OFFSET, globalConfig);
  }

  public static Filter createPlatformConfigFilter(final PublicKey platformConfig) {
    return Filter.createMemCompFilter(PLATFORM_CONFIG_OFFSET, platformConfig);
  }

  public static Filter createBaseMintFilter(final PublicKey baseMint) {
    return Filter.createMemCompFilter(BASE_MINT_OFFSET, baseMint);
  }

  public static Filter createQuoteMintFilter(final PublicKey quoteMint) {
    return Filter.createMemCompFilter(QUOTE_MINT_OFFSET, quoteMint);
  }

  public static Filter createBaseVaultFilter(final PublicKey baseVault) {
    return Filter.createMemCompFilter(BASE_VAULT_OFFSET, baseVault);
  }

  public static Filter createQuoteVaultFilter(final PublicKey quoteVault) {
    return Filter.createMemCompFilter(QUOTE_VAULT_OFFSET, quoteVault);
  }

  public static Filter createCreatorFilter(final PublicKey creator) {
    return Filter.createMemCompFilter(CREATOR_OFFSET, creator);
  }

  public static Filter createTokenProgramFlagFilter(final int tokenProgramFlag) {
    return Filter.createMemCompFilter(TOKEN_PROGRAM_FLAG_OFFSET, new byte[]{(byte) tokenProgramFlag});
  }

  public static Filter createAmmCreatorFeeOnFilter(final AmmCreatorFeeOn ammCreatorFeeOn) {
    return Filter.createMemCompFilter(AMM_CREATOR_FEE_ON_OFFSET, ammCreatorFeeOn.write());
  }

  public static PoolState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PoolState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PoolState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PoolState> FACTORY = PoolState::read;

  public static PoolState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var authBump = _data[i] & 0xFF;
    ++i;
    final var status = _data[i] & 0xFF;
    ++i;
    final var baseDecimals = _data[i] & 0xFF;
    ++i;
    final var quoteDecimals = _data[i] & 0xFF;
    ++i;
    final var migrateType = _data[i] & 0xFF;
    ++i;
    final var supply = getInt64LE(_data, i);
    i += 8;
    final var totalBaseSell = getInt64LE(_data, i);
    i += 8;
    final var virtualBase = getInt64LE(_data, i);
    i += 8;
    final var virtualQuote = getInt64LE(_data, i);
    i += 8;
    final var realBase = getInt64LE(_data, i);
    i += 8;
    final var realQuote = getInt64LE(_data, i);
    i += 8;
    final var totalQuoteFundRaising = getInt64LE(_data, i);
    i += 8;
    final var quoteProtocolFee = getInt64LE(_data, i);
    i += 8;
    final var platformFee = getInt64LE(_data, i);
    i += 8;
    final var migrateFee = getInt64LE(_data, i);
    i += 8;
    final var vestingSchedule = VestingSchedule.read(_data, i);
    i += Borsh.len(vestingSchedule);
    final var globalConfig = readPubKey(_data, i);
    i += 32;
    final var platformConfig = readPubKey(_data, i);
    i += 32;
    final var baseMint = readPubKey(_data, i);
    i += 32;
    final var quoteMint = readPubKey(_data, i);
    i += 32;
    final var baseVault = readPubKey(_data, i);
    i += 32;
    final var quoteVault = readPubKey(_data, i);
    i += 32;
    final var creator = readPubKey(_data, i);
    i += 32;
    final var tokenProgramFlag = _data[i] & 0xFF;
    ++i;
    final var ammCreatorFeeOn = AmmCreatorFeeOn.read(_data, i);
    i += Borsh.len(ammCreatorFeeOn);
    final var padding = new byte[62];
    Borsh.readArray(padding, _data, i);
    return new PoolState(_address,
                         discriminator,
                         epoch,
                         authBump,
                         status,
                         baseDecimals,
                         quoteDecimals,
                         migrateType,
                         supply,
                         totalBaseSell,
                         virtualBase,
                         virtualQuote,
                         realBase,
                         realQuote,
                         totalQuoteFundRaising,
                         quoteProtocolFee,
                         platformFee,
                         migrateFee,
                         vestingSchedule,
                         globalConfig,
                         platformConfig,
                         baseMint,
                         quoteMint,
                         baseVault,
                         quoteVault,
                         creator,
                         tokenProgramFlag,
                         ammCreatorFeeOn,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) authBump;
    ++i;
    _data[i] = (byte) status;
    ++i;
    _data[i] = (byte) baseDecimals;
    ++i;
    _data[i] = (byte) quoteDecimals;
    ++i;
    _data[i] = (byte) migrateType;
    ++i;
    putInt64LE(_data, i, supply);
    i += 8;
    putInt64LE(_data, i, totalBaseSell);
    i += 8;
    putInt64LE(_data, i, virtualBase);
    i += 8;
    putInt64LE(_data, i, virtualQuote);
    i += 8;
    putInt64LE(_data, i, realBase);
    i += 8;
    putInt64LE(_data, i, realQuote);
    i += 8;
    putInt64LE(_data, i, totalQuoteFundRaising);
    i += 8;
    putInt64LE(_data, i, quoteProtocolFee);
    i += 8;
    putInt64LE(_data, i, platformFee);
    i += 8;
    putInt64LE(_data, i, migrateFee);
    i += 8;
    i += Borsh.write(vestingSchedule, _data, i);
    globalConfig.write(_data, i);
    i += 32;
    platformConfig.write(_data, i);
    i += 32;
    baseMint.write(_data, i);
    i += 32;
    quoteMint.write(_data, i);
    i += 32;
    baseVault.write(_data, i);
    i += 32;
    quoteVault.write(_data, i);
    i += 32;
    creator.write(_data, i);
    i += 32;
    _data[i] = (byte) tokenProgramFlag;
    ++i;
    i += Borsh.write(ammCreatorFeeOn, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
