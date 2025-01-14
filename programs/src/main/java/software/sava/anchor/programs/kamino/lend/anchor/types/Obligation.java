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
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Obligation(PublicKey _address,
                         Discriminator discriminator,
                         long tag,
                         LastUpdate lastUpdate,
                         PublicKey lendingMarket,
                         PublicKey owner,
                         ObligationCollateral[] deposits,
                         long lowestReserveDepositLiquidationLtv,
                         BigInteger depositedValueSf,
                         ObligationLiquidity[] borrows,
                         BigInteger borrowFactorAdjustedDebtValueSf,
                         BigInteger borrowedAssetsMarketValueSf,
                         BigInteger allowedBorrowValueSf,
                         BigInteger unhealthyBorrowValueSf,
                         byte[] depositsAssetTiers,
                         byte[] borrowsAssetTiers,
                         int elevationGroup,
                         int numOfObsoleteReserves,
                         int hasDebt,
                         PublicKey referrer,
                         int borrowingDisabled,
                         byte[] reserved,
                         long highestBorrowFactorPct,
                         long[] padding3) implements Borsh {

  public static final int BYTES = 3344;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int TAG_OFFSET = 8;
  public static final int LAST_UPDATE_OFFSET = 16;
  public static final int LENDING_MARKET_OFFSET = 32;
  public static final int OWNER_OFFSET = 64;
  public static final int DEPOSITS_OFFSET = 96;
  public static final int LOWEST_RESERVE_DEPOSIT_LIQUIDATION_LTV_OFFSET = 1184;
  public static final int DEPOSITED_VALUE_SF_OFFSET = 1192;
  public static final int BORROWS_OFFSET = 1208;
  public static final int BORROW_FACTOR_ADJUSTED_DEBT_VALUE_SF_OFFSET = 2208;
  public static final int BORROWED_ASSETS_MARKET_VALUE_SF_OFFSET = 2224;
  public static final int ALLOWED_BORROW_VALUE_SF_OFFSET = 2240;
  public static final int UNHEALTHY_BORROW_VALUE_SF_OFFSET = 2256;
  public static final int DEPOSITS_ASSET_TIERS_OFFSET = 2272;
  public static final int BORROWS_ASSET_TIERS_OFFSET = 2280;
  public static final int ELEVATION_GROUP_OFFSET = 2285;
  public static final int NUM_OF_OBSOLETE_RESERVES_OFFSET = 2286;
  public static final int HAS_DEBT_OFFSET = 2287;
  public static final int REFERRER_OFFSET = 2288;
  public static final int BORROWING_DISABLED_OFFSET = 2320;
  public static final int RESERVED_OFFSET = 2321;
  public static final int HIGHEST_BORROW_FACTOR_PCT_OFFSET = 2328;
  public static final int PADDING3_OFFSET = 2336;

  public static Filter createTagFilter(final long tag) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tag);
    return Filter.createMemCompFilter(TAG_OFFSET, _data);
  }

  public static Filter createLastUpdateFilter(final LastUpdate lastUpdate) {
    return Filter.createMemCompFilter(LAST_UPDATE_OFFSET, lastUpdate.write());
  }

  public static Filter createLendingMarketFilter(final PublicKey lendingMarket) {
    return Filter.createMemCompFilter(LENDING_MARKET_OFFSET, lendingMarket);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createLowestReserveDepositLiquidationLtvFilter(final long lowestReserveDepositLiquidationLtv) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lowestReserveDepositLiquidationLtv);
    return Filter.createMemCompFilter(LOWEST_RESERVE_DEPOSIT_LIQUIDATION_LTV_OFFSET, _data);
  }

  public static Filter createDepositedValueSfFilter(final BigInteger depositedValueSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, depositedValueSf);
    return Filter.createMemCompFilter(DEPOSITED_VALUE_SF_OFFSET, _data);
  }

  public static Filter createBorrowFactorAdjustedDebtValueSfFilter(final BigInteger borrowFactorAdjustedDebtValueSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, borrowFactorAdjustedDebtValueSf);
    return Filter.createMemCompFilter(BORROW_FACTOR_ADJUSTED_DEBT_VALUE_SF_OFFSET, _data);
  }

  public static Filter createBorrowedAssetsMarketValueSfFilter(final BigInteger borrowedAssetsMarketValueSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, borrowedAssetsMarketValueSf);
    return Filter.createMemCompFilter(BORROWED_ASSETS_MARKET_VALUE_SF_OFFSET, _data);
  }

  public static Filter createAllowedBorrowValueSfFilter(final BigInteger allowedBorrowValueSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, allowedBorrowValueSf);
    return Filter.createMemCompFilter(ALLOWED_BORROW_VALUE_SF_OFFSET, _data);
  }

  public static Filter createUnhealthyBorrowValueSfFilter(final BigInteger unhealthyBorrowValueSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, unhealthyBorrowValueSf);
    return Filter.createMemCompFilter(UNHEALTHY_BORROW_VALUE_SF_OFFSET, _data);
  }

  public static Filter createElevationGroupFilter(final int elevationGroup) {
    return Filter.createMemCompFilter(ELEVATION_GROUP_OFFSET, new byte[]{(byte) elevationGroup});
  }

  public static Filter createNumOfObsoleteReservesFilter(final int numOfObsoleteReserves) {
    return Filter.createMemCompFilter(NUM_OF_OBSOLETE_RESERVES_OFFSET, new byte[]{(byte) numOfObsoleteReserves});
  }

  public static Filter createHasDebtFilter(final int hasDebt) {
    return Filter.createMemCompFilter(HAS_DEBT_OFFSET, new byte[]{(byte) hasDebt});
  }

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createBorrowingDisabledFilter(final int borrowingDisabled) {
    return Filter.createMemCompFilter(BORROWING_DISABLED_OFFSET, new byte[]{(byte) borrowingDisabled});
  }

  public static Filter createHighestBorrowFactorPctFilter(final long highestBorrowFactorPct) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, highestBorrowFactorPct);
    return Filter.createMemCompFilter(HIGHEST_BORROW_FACTOR_PCT_OFFSET, _data);
  }

  public static Obligation read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Obligation read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Obligation> FACTORY = Obligation::read;

  public static Obligation read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var tag = getInt64LE(_data, i);
    i += 8;
    final var lastUpdate = LastUpdate.read(_data, i);
    i += Borsh.len(lastUpdate);
    final var lendingMarket = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var deposits = new ObligationCollateral[8];
    i += Borsh.readArray(deposits, ObligationCollateral::read, _data, i);
    final var lowestReserveDepositLiquidationLtv = getInt64LE(_data, i);
    i += 8;
    final var depositedValueSf = getInt128LE(_data, i);
    i += 16;
    final var borrows = new ObligationLiquidity[5];
    i += Borsh.readArray(borrows, ObligationLiquidity::read, _data, i);
    final var borrowFactorAdjustedDebtValueSf = getInt128LE(_data, i);
    i += 16;
    final var borrowedAssetsMarketValueSf = getInt128LE(_data, i);
    i += 16;
    final var allowedBorrowValueSf = getInt128LE(_data, i);
    i += 16;
    final var unhealthyBorrowValueSf = getInt128LE(_data, i);
    i += 16;
    final var depositsAssetTiers = new byte[8];
    i += Borsh.readArray(depositsAssetTiers, _data, i);
    final var borrowsAssetTiers = new byte[5];
    i += Borsh.readArray(borrowsAssetTiers, _data, i);
    final var elevationGroup = _data[i] & 0xFF;
    ++i;
    final var numOfObsoleteReserves = _data[i] & 0xFF;
    ++i;
    final var hasDebt = _data[i] & 0xFF;
    ++i;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var borrowingDisabled = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[7];
    i += Borsh.readArray(reserved, _data, i);
    final var highestBorrowFactorPct = getInt64LE(_data, i);
    i += 8;
    final var padding3 = new long[126];
    Borsh.readArray(padding3, _data, i);
    return new Obligation(_address,
                          discriminator,
                          tag,
                          lastUpdate,
                          lendingMarket,
                          owner,
                          deposits,
                          lowestReserveDepositLiquidationLtv,
                          depositedValueSf,
                          borrows,
                          borrowFactorAdjustedDebtValueSf,
                          borrowedAssetsMarketValueSf,
                          allowedBorrowValueSf,
                          unhealthyBorrowValueSf,
                          depositsAssetTiers,
                          borrowsAssetTiers,
                          elevationGroup,
                          numOfObsoleteReserves,
                          hasDebt,
                          referrer,
                          borrowingDisabled,
                          reserved,
                          highestBorrowFactorPct,
                          padding3);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, tag);
    i += 8;
    i += Borsh.write(lastUpdate, _data, i);
    lendingMarket.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    i += Borsh.writeArray(deposits, _data, i);
    putInt64LE(_data, i, lowestReserveDepositLiquidationLtv);
    i += 8;
    putInt128LE(_data, i, depositedValueSf);
    i += 16;
    i += Borsh.writeArray(borrows, _data, i);
    putInt128LE(_data, i, borrowFactorAdjustedDebtValueSf);
    i += 16;
    putInt128LE(_data, i, borrowedAssetsMarketValueSf);
    i += 16;
    putInt128LE(_data, i, allowedBorrowValueSf);
    i += 16;
    putInt128LE(_data, i, unhealthyBorrowValueSf);
    i += 16;
    i += Borsh.writeArray(depositsAssetTiers, _data, i);
    i += Borsh.writeArray(borrowsAssetTiers, _data, i);
    _data[i] = (byte) elevationGroup;
    ++i;
    _data[i] = (byte) numOfObsoleteReserves;
    ++i;
    _data[i] = (byte) hasDebt;
    ++i;
    referrer.write(_data, i);
    i += 32;
    _data[i] = (byte) borrowingDisabled;
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    putInt64LE(_data, i, highestBorrowFactorPct);
    i += 8;
    i += Borsh.writeArray(padding3, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
