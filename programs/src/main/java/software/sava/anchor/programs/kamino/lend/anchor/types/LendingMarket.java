package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LendingMarket(PublicKey _address,
                            Discriminator discriminator,
                            // Version of lending market
                            long version,
                            // Bump seed for derived authority address
                            long bumpSeed,
                            // Owner authority which can add new reserves
                            PublicKey lendingMarketOwner,
                            // Temporary cache of the lending market owner, used in update_lending_market_owner
                            PublicKey lendingMarketOwnerCached,
                            // Currency market prices are quoted in
                            // e.g. "USD" null padded (`*b"USD\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0\0"`) or a SPL token mint pubkey
                            byte[] quoteCurrency,
                            // Referral fee for the lending market, as bps out of the total protocol fee
                            int referralFeeBps,
                            int emergencyMode,
                            int autodeleverageEnabled,
                            int borrowDisabled,
                            // Refresh price from oracle only if it's older than this percentage of the price max age.
                            // e.g. if the max age is set to 100s and this is set to 80%, the price will be refreshed if it's older than 80s.
                            // Price is always refreshed if this set to 0.
                            int priceRefreshTriggerToMaxAgePct,
                            // Percentage of the total borrowed value in an obligation available for liquidation
                            int liquidationMaxDebtCloseFactorPct,
                            // Minimum acceptable unhealthy LTV before max_debt_close_factor_pct becomes 100%
                            int insolvencyRiskUnhealthyLtvPct,
                            // Minimum liquidation value threshold triggering full liquidation for an obligation
                            long minFullLiquidationValueThreshold,
                            // Max allowed liquidation value in one ix call
                            long maxLiquidatableDebtMarketValueAtOnce,
                            // Global maximum unhealthy borrow value allowed for any obligation
                            long globalUnhealthyBorrowValue,
                            // Global maximum allowed borrow value allowed for any obligation
                            long globalAllowedBorrowValue,
                            // The address of the risk council, in charge of making parameter and risk decisions on behalf of the protocol
                            PublicKey riskCouncil,
                            // [DEPRECATED] Reward points multiplier per obligation type
                            byte[] reserved1,
                            // Elevation groups are used to group together reserves that have the same risk parameters and can bump the ltv and liquidation threshold
                            ElevationGroup[] elevationGroups,
                            long[] elevationGroupPadding,
                            // Min net value accepted to be found in a position after any lending action in an obligation (scaled by quote currency decimals)
                            BigInteger minNetValueInObligationSf,
                            long minValueSkipLiquidationLtvBfChecks,
                            // Market name, zero-padded.
                            byte[] name,
                            long[] padding1) implements Borsh {

  public static final int BYTES = 4664;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VERSION_OFFSET = 8;
  public static final int BUMP_SEED_OFFSET = 16;
  public static final int LENDING_MARKET_OWNER_OFFSET = 24;
  public static final int LENDING_MARKET_OWNER_CACHED_OFFSET = 56;
  public static final int QUOTE_CURRENCY_OFFSET = 88;
  public static final int REFERRAL_FEE_BPS_OFFSET = 120;
  public static final int EMERGENCY_MODE_OFFSET = 122;
  public static final int AUTODELEVERAGE_ENABLED_OFFSET = 123;
  public static final int BORROW_DISABLED_OFFSET = 124;
  public static final int PRICE_REFRESH_TRIGGER_TO_MAX_AGE_PCT_OFFSET = 125;
  public static final int LIQUIDATION_MAX_DEBT_CLOSE_FACTOR_PCT_OFFSET = 126;
  public static final int INSOLVENCY_RISK_UNHEALTHY_LTV_PCT_OFFSET = 127;
  public static final int MIN_FULL_LIQUIDATION_VALUE_THRESHOLD_OFFSET = 128;
  public static final int MAX_LIQUIDATABLE_DEBT_MARKET_VALUE_AT_ONCE_OFFSET = 136;
  public static final int GLOBAL_UNHEALTHY_BORROW_VALUE_OFFSET = 144;
  public static final int GLOBAL_ALLOWED_BORROW_VALUE_OFFSET = 152;
  public static final int RISK_COUNCIL_OFFSET = 160;
  public static final int RESERVED1_OFFSET = 192;
  public static final int ELEVATION_GROUPS_OFFSET = 200;
  public static final int ELEVATION_GROUP_PADDING_OFFSET = 2504;
  public static final int MIN_NET_VALUE_IN_OBLIGATION_SF_OFFSET = 3224;
  public static final int MIN_VALUE_SKIP_LIQUIDATION_LTV_BF_CHECKS_OFFSET = 3240;
  public static final int NAME_OFFSET = 3248;
  public static final int PADDING1_OFFSET = 3280;

  public static Filter createVersionFilter(final long version) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, version);
    return Filter.createMemCompFilter(VERSION_OFFSET, _data);
  }

  public static Filter createBumpSeedFilter(final long bumpSeed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, bumpSeed);
    return Filter.createMemCompFilter(BUMP_SEED_OFFSET, _data);
  }

  public static Filter createLendingMarketOwnerFilter(final PublicKey lendingMarketOwner) {
    return Filter.createMemCompFilter(LENDING_MARKET_OWNER_OFFSET, lendingMarketOwner);
  }

  public static Filter createLendingMarketOwnerCachedFilter(final PublicKey lendingMarketOwnerCached) {
    return Filter.createMemCompFilter(LENDING_MARKET_OWNER_CACHED_OFFSET, lendingMarketOwnerCached);
  }

  public static Filter createReferralFeeBpsFilter(final int referralFeeBps) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, referralFeeBps);
    return Filter.createMemCompFilter(REFERRAL_FEE_BPS_OFFSET, _data);
  }

  public static Filter createEmergencyModeFilter(final int emergencyMode) {
    return Filter.createMemCompFilter(EMERGENCY_MODE_OFFSET, new byte[]{(byte) emergencyMode});
  }

  public static Filter createAutodeleverageEnabledFilter(final int autodeleverageEnabled) {
    return Filter.createMemCompFilter(AUTODELEVERAGE_ENABLED_OFFSET, new byte[]{(byte) autodeleverageEnabled});
  }

  public static Filter createBorrowDisabledFilter(final int borrowDisabled) {
    return Filter.createMemCompFilter(BORROW_DISABLED_OFFSET, new byte[]{(byte) borrowDisabled});
  }

  public static Filter createPriceRefreshTriggerToMaxAgePctFilter(final int priceRefreshTriggerToMaxAgePct) {
    return Filter.createMemCompFilter(PRICE_REFRESH_TRIGGER_TO_MAX_AGE_PCT_OFFSET, new byte[]{(byte) priceRefreshTriggerToMaxAgePct});
  }

  public static Filter createLiquidationMaxDebtCloseFactorPctFilter(final int liquidationMaxDebtCloseFactorPct) {
    return Filter.createMemCompFilter(LIQUIDATION_MAX_DEBT_CLOSE_FACTOR_PCT_OFFSET, new byte[]{(byte) liquidationMaxDebtCloseFactorPct});
  }

  public static Filter createInsolvencyRiskUnhealthyLtvPctFilter(final int insolvencyRiskUnhealthyLtvPct) {
    return Filter.createMemCompFilter(INSOLVENCY_RISK_UNHEALTHY_LTV_PCT_OFFSET, new byte[]{(byte) insolvencyRiskUnhealthyLtvPct});
  }

  public static Filter createMinFullLiquidationValueThresholdFilter(final long minFullLiquidationValueThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minFullLiquidationValueThreshold);
    return Filter.createMemCompFilter(MIN_FULL_LIQUIDATION_VALUE_THRESHOLD_OFFSET, _data);
  }

  public static Filter createMaxLiquidatableDebtMarketValueAtOnceFilter(final long maxLiquidatableDebtMarketValueAtOnce) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxLiquidatableDebtMarketValueAtOnce);
    return Filter.createMemCompFilter(MAX_LIQUIDATABLE_DEBT_MARKET_VALUE_AT_ONCE_OFFSET, _data);
  }

  public static Filter createGlobalUnhealthyBorrowValueFilter(final long globalUnhealthyBorrowValue) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, globalUnhealthyBorrowValue);
    return Filter.createMemCompFilter(GLOBAL_UNHEALTHY_BORROW_VALUE_OFFSET, _data);
  }

  public static Filter createGlobalAllowedBorrowValueFilter(final long globalAllowedBorrowValue) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, globalAllowedBorrowValue);
    return Filter.createMemCompFilter(GLOBAL_ALLOWED_BORROW_VALUE_OFFSET, _data);
  }

  public static Filter createRiskCouncilFilter(final PublicKey riskCouncil) {
    return Filter.createMemCompFilter(RISK_COUNCIL_OFFSET, riskCouncil);
  }

  public static Filter createMinNetValueInObligationSfFilter(final BigInteger minNetValueInObligationSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, minNetValueInObligationSf);
    return Filter.createMemCompFilter(MIN_NET_VALUE_IN_OBLIGATION_SF_OFFSET, _data);
  }

  public static Filter createMinValueSkipLiquidationLtvBfChecksFilter(final long minValueSkipLiquidationLtvBfChecks) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minValueSkipLiquidationLtvBfChecks);
    return Filter.createMemCompFilter(MIN_VALUE_SKIP_LIQUIDATION_LTV_BF_CHECKS_OFFSET, _data);
  }

  public static LendingMarket read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static LendingMarket read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], LendingMarket> FACTORY = LendingMarket::read;

  public static LendingMarket read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = getInt64LE(_data, i);
    i += 8;
    final var bumpSeed = getInt64LE(_data, i);
    i += 8;
    final var lendingMarketOwner = readPubKey(_data, i);
    i += 32;
    final var lendingMarketOwnerCached = readPubKey(_data, i);
    i += 32;
    final var quoteCurrency = new byte[32];
    i += Borsh.readArray(quoteCurrency, _data, i);
    final var referralFeeBps = getInt16LE(_data, i);
    i += 2;
    final var emergencyMode = _data[i] & 0xFF;
    ++i;
    final var autodeleverageEnabled = _data[i] & 0xFF;
    ++i;
    final var borrowDisabled = _data[i] & 0xFF;
    ++i;
    final var priceRefreshTriggerToMaxAgePct = _data[i] & 0xFF;
    ++i;
    final var liquidationMaxDebtCloseFactorPct = _data[i] & 0xFF;
    ++i;
    final var insolvencyRiskUnhealthyLtvPct = _data[i] & 0xFF;
    ++i;
    final var minFullLiquidationValueThreshold = getInt64LE(_data, i);
    i += 8;
    final var maxLiquidatableDebtMarketValueAtOnce = getInt64LE(_data, i);
    i += 8;
    final var globalUnhealthyBorrowValue = getInt64LE(_data, i);
    i += 8;
    final var globalAllowedBorrowValue = getInt64LE(_data, i);
    i += 8;
    final var riskCouncil = readPubKey(_data, i);
    i += 32;
    final var reserved1 = new byte[8];
    i += Borsh.readArray(reserved1, _data, i);
    final var elevationGroups = new ElevationGroup[32];
    i += Borsh.readArray(elevationGroups, ElevationGroup::read, _data, i);
    final var elevationGroupPadding = new long[90];
    i += Borsh.readArray(elevationGroupPadding, _data, i);
    final var minNetValueInObligationSf = getInt128LE(_data, i);
    i += 16;
    final var minValueSkipLiquidationLtvBfChecks = getInt64LE(_data, i);
    i += 8;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var padding1 = new long[173];
    Borsh.readArray(padding1, _data, i);
    return new LendingMarket(_address,
                             discriminator,
                             version,
                             bumpSeed,
                             lendingMarketOwner,
                             lendingMarketOwnerCached,
                             quoteCurrency,
                             referralFeeBps,
                             emergencyMode,
                             autodeleverageEnabled,
                             borrowDisabled,
                             priceRefreshTriggerToMaxAgePct,
                             liquidationMaxDebtCloseFactorPct,
                             insolvencyRiskUnhealthyLtvPct,
                             minFullLiquidationValueThreshold,
                             maxLiquidatableDebtMarketValueAtOnce,
                             globalUnhealthyBorrowValue,
                             globalAllowedBorrowValue,
                             riskCouncil,
                             reserved1,
                             elevationGroups,
                             elevationGroupPadding,
                             minNetValueInObligationSf,
                             minValueSkipLiquidationLtvBfChecks,
                             name,
                             padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, version);
    i += 8;
    putInt64LE(_data, i, bumpSeed);
    i += 8;
    lendingMarketOwner.write(_data, i);
    i += 32;
    lendingMarketOwnerCached.write(_data, i);
    i += 32;
    i += Borsh.writeArray(quoteCurrency, _data, i);
    putInt16LE(_data, i, referralFeeBps);
    i += 2;
    _data[i] = (byte) emergencyMode;
    ++i;
    _data[i] = (byte) autodeleverageEnabled;
    ++i;
    _data[i] = (byte) borrowDisabled;
    ++i;
    _data[i] = (byte) priceRefreshTriggerToMaxAgePct;
    ++i;
    _data[i] = (byte) liquidationMaxDebtCloseFactorPct;
    ++i;
    _data[i] = (byte) insolvencyRiskUnhealthyLtvPct;
    ++i;
    putInt64LE(_data, i, minFullLiquidationValueThreshold);
    i += 8;
    putInt64LE(_data, i, maxLiquidatableDebtMarketValueAtOnce);
    i += 8;
    putInt64LE(_data, i, globalUnhealthyBorrowValue);
    i += 8;
    putInt64LE(_data, i, globalAllowedBorrowValue);
    i += 8;
    riskCouncil.write(_data, i);
    i += 32;
    i += Borsh.writeArray(reserved1, _data, i);
    i += Borsh.writeArray(elevationGroups, _data, i);
    i += Borsh.writeArray(elevationGroupPadding, _data, i);
    putInt128LE(_data, i, minNetValueInObligationSf);
    i += 16;
    putInt64LE(_data, i, minValueSkipLiquidationLtvBfChecks);
    i += 8;
    i += Borsh.writeArray(name, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
