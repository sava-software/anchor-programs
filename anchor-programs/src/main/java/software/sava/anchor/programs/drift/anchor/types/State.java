package software.sava.anchor.programs.drift.anchor.types;

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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record State(PublicKey _address,
                    Discriminator discriminator,
                    PublicKey admin,
                    PublicKey whitelistMint,
                    PublicKey discountMint,
                    PublicKey signer,
                    PublicKey srmVault,
                    FeeStructure perpFeeStructure,
                    FeeStructure spotFeeStructure,
                    OracleGuardRails oracleGuardRails,
                    long numberOfAuthorities,
                    long numberOfSubAccounts,
                    long lpCooldownTime,
                    int liquidationMarginBufferRatio,
                    int settlementDuration,
                    int numberOfMarkets,
                    int numberOfSpotMarkets,
                    int signerNonce,
                    int minPerpAuctionDuration,
                    int defaultMarketOrderTimeInForce,
                    int defaultSpotAuctionDuration,
                    int exchangeStatus,
                    int liquidationDuration,
                    int initialPctToLiquidate,
                    int maxNumberOfSubAccounts,
                    int maxInitializeUserFee,
                    byte[] padding) implements Borsh {

  public static final int BYTES = 992;
  public static final int PADDING_LEN = 10;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ADMIN_OFFSET = 8;
  public static final int WHITELIST_MINT_OFFSET = 40;
  public static final int DISCOUNT_MINT_OFFSET = 72;
  public static final int SIGNER_OFFSET = 104;
  public static final int SRM_VAULT_OFFSET = 136;
  public static final int PERP_FEE_STRUCTURE_OFFSET = 168;
  public static final int SPOT_FEE_STRUCTURE_OFFSET = 528;
  public static final int ORACLE_GUARD_RAILS_OFFSET = 888;
  public static final int NUMBER_OF_AUTHORITIES_OFFSET = 936;
  public static final int NUMBER_OF_SUB_ACCOUNTS_OFFSET = 944;
  public static final int LP_COOLDOWN_TIME_OFFSET = 952;
  public static final int LIQUIDATION_MARGIN_BUFFER_RATIO_OFFSET = 960;
  public static final int SETTLEMENT_DURATION_OFFSET = 964;
  public static final int NUMBER_OF_MARKETS_OFFSET = 966;
  public static final int NUMBER_OF_SPOT_MARKETS_OFFSET = 968;
  public static final int SIGNER_NONCE_OFFSET = 970;
  public static final int MIN_PERP_AUCTION_DURATION_OFFSET = 971;
  public static final int DEFAULT_MARKET_ORDER_TIME_IN_FORCE_OFFSET = 972;
  public static final int DEFAULT_SPOT_AUCTION_DURATION_OFFSET = 973;
  public static final int EXCHANGE_STATUS_OFFSET = 974;
  public static final int LIQUIDATION_DURATION_OFFSET = 975;
  public static final int INITIAL_PCT_TO_LIQUIDATE_OFFSET = 976;
  public static final int MAX_NUMBER_OF_SUB_ACCOUNTS_OFFSET = 978;
  public static final int MAX_INITIALIZE_USER_FEE_OFFSET = 980;
  public static final int PADDING_OFFSET = 982;

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createWhitelistMintFilter(final PublicKey whitelistMint) {
    return Filter.createMemCompFilter(WHITELIST_MINT_OFFSET, whitelistMint);
  }

  public static Filter createDiscountMintFilter(final PublicKey discountMint) {
    return Filter.createMemCompFilter(DISCOUNT_MINT_OFFSET, discountMint);
  }

  public static Filter createSignerFilter(final PublicKey signer) {
    return Filter.createMemCompFilter(SIGNER_OFFSET, signer);
  }

  public static Filter createSrmVaultFilter(final PublicKey srmVault) {
    return Filter.createMemCompFilter(SRM_VAULT_OFFSET, srmVault);
  }

  public static Filter createOracleGuardRailsFilter(final OracleGuardRails oracleGuardRails) {
    return Filter.createMemCompFilter(ORACLE_GUARD_RAILS_OFFSET, oracleGuardRails.write());
  }

  public static Filter createNumberOfAuthoritiesFilter(final long numberOfAuthorities) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numberOfAuthorities);
    return Filter.createMemCompFilter(NUMBER_OF_AUTHORITIES_OFFSET, _data);
  }

  public static Filter createNumberOfSubAccountsFilter(final long numberOfSubAccounts) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numberOfSubAccounts);
    return Filter.createMemCompFilter(NUMBER_OF_SUB_ACCOUNTS_OFFSET, _data);
  }

  public static Filter createLpCooldownTimeFilter(final long lpCooldownTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lpCooldownTime);
    return Filter.createMemCompFilter(LP_COOLDOWN_TIME_OFFSET, _data);
  }

  public static Filter createLiquidationMarginBufferRatioFilter(final int liquidationMarginBufferRatio) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, liquidationMarginBufferRatio);
    return Filter.createMemCompFilter(LIQUIDATION_MARGIN_BUFFER_RATIO_OFFSET, _data);
  }

  public static Filter createSettlementDurationFilter(final int settlementDuration) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, settlementDuration);
    return Filter.createMemCompFilter(SETTLEMENT_DURATION_OFFSET, _data);
  }

  public static Filter createNumberOfMarketsFilter(final int numberOfMarkets) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, numberOfMarkets);
    return Filter.createMemCompFilter(NUMBER_OF_MARKETS_OFFSET, _data);
  }

  public static Filter createNumberOfSpotMarketsFilter(final int numberOfSpotMarkets) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, numberOfSpotMarkets);
    return Filter.createMemCompFilter(NUMBER_OF_SPOT_MARKETS_OFFSET, _data);
  }

  public static Filter createSignerNonceFilter(final int signerNonce) {
    return Filter.createMemCompFilter(SIGNER_NONCE_OFFSET, new byte[]{(byte) signerNonce});
  }

  public static Filter createMinPerpAuctionDurationFilter(final int minPerpAuctionDuration) {
    return Filter.createMemCompFilter(MIN_PERP_AUCTION_DURATION_OFFSET, new byte[]{(byte) minPerpAuctionDuration});
  }

  public static Filter createDefaultMarketOrderTimeInForceFilter(final int defaultMarketOrderTimeInForce) {
    return Filter.createMemCompFilter(DEFAULT_MARKET_ORDER_TIME_IN_FORCE_OFFSET, new byte[]{(byte) defaultMarketOrderTimeInForce});
  }

  public static Filter createDefaultSpotAuctionDurationFilter(final int defaultSpotAuctionDuration) {
    return Filter.createMemCompFilter(DEFAULT_SPOT_AUCTION_DURATION_OFFSET, new byte[]{(byte) defaultSpotAuctionDuration});
  }

  public static Filter createExchangeStatusFilter(final int exchangeStatus) {
    return Filter.createMemCompFilter(EXCHANGE_STATUS_OFFSET, new byte[]{(byte) exchangeStatus});
  }

  public static Filter createLiquidationDurationFilter(final int liquidationDuration) {
    return Filter.createMemCompFilter(LIQUIDATION_DURATION_OFFSET, new byte[]{(byte) liquidationDuration});
  }

  public static Filter createInitialPctToLiquidateFilter(final int initialPctToLiquidate) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, initialPctToLiquidate);
    return Filter.createMemCompFilter(INITIAL_PCT_TO_LIQUIDATE_OFFSET, _data);
  }

  public static Filter createMaxNumberOfSubAccountsFilter(final int maxNumberOfSubAccounts) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, maxNumberOfSubAccounts);
    return Filter.createMemCompFilter(MAX_NUMBER_OF_SUB_ACCOUNTS_OFFSET, _data);
  }

  public static Filter createMaxInitializeUserFeeFilter(final int maxInitializeUserFee) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, maxInitializeUserFee);
    return Filter.createMemCompFilter(MAX_INITIALIZE_USER_FEE_OFFSET, _data);
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
    final var admin = readPubKey(_data, i);
    i += 32;
    final var whitelistMint = readPubKey(_data, i);
    i += 32;
    final var discountMint = readPubKey(_data, i);
    i += 32;
    final var signer = readPubKey(_data, i);
    i += 32;
    final var srmVault = readPubKey(_data, i);
    i += 32;
    final var perpFeeStructure = FeeStructure.read(_data, i);
    i += Borsh.len(perpFeeStructure);
    final var spotFeeStructure = FeeStructure.read(_data, i);
    i += Borsh.len(spotFeeStructure);
    final var oracleGuardRails = OracleGuardRails.read(_data, i);
    i += Borsh.len(oracleGuardRails);
    final var numberOfAuthorities = getInt64LE(_data, i);
    i += 8;
    final var numberOfSubAccounts = getInt64LE(_data, i);
    i += 8;
    final var lpCooldownTime = getInt64LE(_data, i);
    i += 8;
    final var liquidationMarginBufferRatio = getInt32LE(_data, i);
    i += 4;
    final var settlementDuration = getInt16LE(_data, i);
    i += 2;
    final var numberOfMarkets = getInt16LE(_data, i);
    i += 2;
    final var numberOfSpotMarkets = getInt16LE(_data, i);
    i += 2;
    final var signerNonce = _data[i] & 0xFF;
    ++i;
    final var minPerpAuctionDuration = _data[i] & 0xFF;
    ++i;
    final var defaultMarketOrderTimeInForce = _data[i] & 0xFF;
    ++i;
    final var defaultSpotAuctionDuration = _data[i] & 0xFF;
    ++i;
    final var exchangeStatus = _data[i] & 0xFF;
    ++i;
    final var liquidationDuration = _data[i] & 0xFF;
    ++i;
    final var initialPctToLiquidate = getInt16LE(_data, i);
    i += 2;
    final var maxNumberOfSubAccounts = getInt16LE(_data, i);
    i += 2;
    final var maxInitializeUserFee = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[10];
    Borsh.readArray(padding, _data, i);
    return new State(_address,
                     discriminator,
                     admin,
                     whitelistMint,
                     discountMint,
                     signer,
                     srmVault,
                     perpFeeStructure,
                     spotFeeStructure,
                     oracleGuardRails,
                     numberOfAuthorities,
                     numberOfSubAccounts,
                     lpCooldownTime,
                     liquidationMarginBufferRatio,
                     settlementDuration,
                     numberOfMarkets,
                     numberOfSpotMarkets,
                     signerNonce,
                     minPerpAuctionDuration,
                     defaultMarketOrderTimeInForce,
                     defaultSpotAuctionDuration,
                     exchangeStatus,
                     liquidationDuration,
                     initialPctToLiquidate,
                     maxNumberOfSubAccounts,
                     maxInitializeUserFee,
                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    admin.write(_data, i);
    i += 32;
    whitelistMint.write(_data, i);
    i += 32;
    discountMint.write(_data, i);
    i += 32;
    signer.write(_data, i);
    i += 32;
    srmVault.write(_data, i);
    i += 32;
    i += Borsh.write(perpFeeStructure, _data, i);
    i += Borsh.write(spotFeeStructure, _data, i);
    i += Borsh.write(oracleGuardRails, _data, i);
    putInt64LE(_data, i, numberOfAuthorities);
    i += 8;
    putInt64LE(_data, i, numberOfSubAccounts);
    i += 8;
    putInt64LE(_data, i, lpCooldownTime);
    i += 8;
    putInt32LE(_data, i, liquidationMarginBufferRatio);
    i += 4;
    putInt16LE(_data, i, settlementDuration);
    i += 2;
    putInt16LE(_data, i, numberOfMarkets);
    i += 2;
    putInt16LE(_data, i, numberOfSpotMarkets);
    i += 2;
    _data[i] = (byte) signerNonce;
    ++i;
    _data[i] = (byte) minPerpAuctionDuration;
    ++i;
    _data[i] = (byte) defaultMarketOrderTimeInForce;
    ++i;
    _data[i] = (byte) defaultSpotAuctionDuration;
    ++i;
    _data[i] = (byte) exchangeStatus;
    ++i;
    _data[i] = (byte) liquidationDuration;
    ++i;
    putInt16LE(_data, i, initialPctToLiquidate);
    i += 2;
    putInt16LE(_data, i, maxNumberOfSubAccounts);
    i += 2;
    putInt16LE(_data, i, maxInitializeUserFee);
    i += 2;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
