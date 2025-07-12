package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Lending market obligation state
public record Obligation(PublicKey _address,
                         Discriminator discriminator,
                         // Version of the struct
                         long tag,
                         // Last update to collateral, liquidity, or their market values
                         LastUpdate lastUpdate,
                         // Lending market address
                         PublicKey lendingMarket,
                         // Owner authority which can borrow liquidity
                         PublicKey owner,
                         // Deposited collateral for the obligation, unique by deposit reserve address
                         ObligationCollateral[] deposits,
                         // Worst LTV for the collaterals backing the loan, represented as a percentage
                         long lowestReserveDepositLiquidationLtv,
                         // Market value of deposits (scaled fraction)
                         BigInteger depositedValueSf,
                         // Borrowed liquidity for the obligation, unique by borrow reserve address
                         ObligationLiquidity[] borrows,
                         // Risk adjusted market value of borrows/debt (sum of price * borrowed_amount * borrow_factor) (scaled fraction)
                         BigInteger borrowFactorAdjustedDebtValueSf,
                         // Market value of borrows - used for max_liquidatable_borrowed_amount (scaled fraction)
                         BigInteger borrowedAssetsMarketValueSf,
                         // The maximum borrow value at the weighted average loan to value ratio (scaled fraction)
                         BigInteger allowedBorrowValueSf,
                         // The dangerous borrow value at the weighted average liquidation threshold (scaled fraction)
                         BigInteger unhealthyBorrowValueSf,
                         // The asset tier of the deposits
                         byte[] depositsAssetTiers,
                         // The asset tier of the borrows
                         byte[] borrowsAssetTiers,
                         // The elevation group id the obligation opted into.
                         int elevationGroup,
                         // The number of obsolete reserves the obligation has a deposit in
                         int numOfObsoleteDepositReserves,
                         // Marked = 1 if borrows array is not empty, 0 = borrows empty
                         int hasDebt,
                         // Wallet address of the referrer
                         PublicKey referrer,
                         // Marked = 1 if borrowing disabled, 0 = borrowing enabled
                         int borrowingDisabled,
                         // A target LTV set by the risk council when marking this obligation for deleveraging.
                         // Only effective when `deleveraging_margin_call_started_slot != 0`.
                         int autodeleverageTargetLtvPct,
                         // The lowest max LTV found amongst the collateral deposits
                         int lowestReserveDepositMaxLtvPct,
                         // The number of obsolete reserves the obligation has a borrow in
                         int numOfObsoleteBorrowReserves,
                         byte[] reserved,
                         long highestBorrowFactorPct,
                         // A timestamp at which the risk council most-recently marked this obligation for deleveraging.
                         // Zero if not currently subject to deleveraging.
                         long autodeleverageMarginCallStartedTimestamp,
                         // Owner-defined, liquidator-executed orders applicable to this obligation.
                         // Typical use-cases would be a stop-loss and a take-profit (possibly co-existing).
                         ObligationOrder[] orders,
                         long[] padding3) implements Borsh {

  public static final int BYTES = 3344;
  public static final int DEPOSITS_LEN = 8;
  public static final int BORROWS_LEN = 5;
  public static final int DEPOSITS_ASSET_TIERS_LEN = 8;
  public static final int BORROWS_ASSET_TIERS_LEN = 5;
  public static final int RESERVED_LEN = 4;
  public static final int ORDERS_LEN = 2;
  public static final int PADDING_3_LEN = 93;
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
  public static final int NUM_OF_OBSOLETE_DEPOSIT_RESERVES_OFFSET = 2286;
  public static final int HAS_DEBT_OFFSET = 2287;
  public static final int REFERRER_OFFSET = 2288;
  public static final int BORROWING_DISABLED_OFFSET = 2320;
  public static final int AUTODELEVERAGE_TARGET_LTV_PCT_OFFSET = 2321;
  public static final int LOWEST_RESERVE_DEPOSIT_MAX_LTV_PCT_OFFSET = 2322;
  public static final int NUM_OF_OBSOLETE_BORROW_RESERVES_OFFSET = 2323;
  public static final int RESERVED_OFFSET = 2324;
  public static final int HIGHEST_BORROW_FACTOR_PCT_OFFSET = 2328;
  public static final int AUTODELEVERAGE_MARGIN_CALL_STARTED_TIMESTAMP_OFFSET = 2336;
  public static final int ORDERS_OFFSET = 2344;
  public static final int PADDING_3_OFFSET = 2600;

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

  public static Filter createNumOfObsoleteDepositReservesFilter(final int numOfObsoleteDepositReserves) {
    return Filter.createMemCompFilter(NUM_OF_OBSOLETE_DEPOSIT_RESERVES_OFFSET, new byte[]{(byte) numOfObsoleteDepositReserves});
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

  public static Filter createAutodeleverageTargetLtvPctFilter(final int autodeleverageTargetLtvPct) {
    return Filter.createMemCompFilter(AUTODELEVERAGE_TARGET_LTV_PCT_OFFSET, new byte[]{(byte) autodeleverageTargetLtvPct});
  }

  public static Filter createLowestReserveDepositMaxLtvPctFilter(final int lowestReserveDepositMaxLtvPct) {
    return Filter.createMemCompFilter(LOWEST_RESERVE_DEPOSIT_MAX_LTV_PCT_OFFSET, new byte[]{(byte) lowestReserveDepositMaxLtvPct});
  }

  public static Filter createNumOfObsoleteBorrowReservesFilter(final int numOfObsoleteBorrowReserves) {
    return Filter.createMemCompFilter(NUM_OF_OBSOLETE_BORROW_RESERVES_OFFSET, new byte[]{(byte) numOfObsoleteBorrowReserves});
  }

  public static Filter createHighestBorrowFactorPctFilter(final long highestBorrowFactorPct) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, highestBorrowFactorPct);
    return Filter.createMemCompFilter(HIGHEST_BORROW_FACTOR_PCT_OFFSET, _data);
  }

  public static Filter createAutodeleverageMarginCallStartedTimestampFilter(final long autodeleverageMarginCallStartedTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, autodeleverageMarginCallStartedTimestamp);
    return Filter.createMemCompFilter(AUTODELEVERAGE_MARGIN_CALL_STARTED_TIMESTAMP_OFFSET, _data);
  }

  public static Obligation read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Obligation read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
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
    final var numOfObsoleteDepositReserves = _data[i] & 0xFF;
    ++i;
    final var hasDebt = _data[i] & 0xFF;
    ++i;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var borrowingDisabled = _data[i] & 0xFF;
    ++i;
    final var autodeleverageTargetLtvPct = _data[i] & 0xFF;
    ++i;
    final var lowestReserveDepositMaxLtvPct = _data[i] & 0xFF;
    ++i;
    final var numOfObsoleteBorrowReserves = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[4];
    i += Borsh.readArray(reserved, _data, i);
    final var highestBorrowFactorPct = getInt64LE(_data, i);
    i += 8;
    final var autodeleverageMarginCallStartedTimestamp = getInt64LE(_data, i);
    i += 8;
    final var orders = new ObligationOrder[2];
    i += Borsh.readArray(orders, ObligationOrder::read, _data, i);
    final var padding3 = new long[93];
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
                          numOfObsoleteDepositReserves,
                          hasDebt,
                          referrer,
                          borrowingDisabled,
                          autodeleverageTargetLtvPct,
                          lowestReserveDepositMaxLtvPct,
                          numOfObsoleteBorrowReserves,
                          reserved,
                          highestBorrowFactorPct,
                          autodeleverageMarginCallStartedTimestamp,
                          orders,
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
    _data[i] = (byte) numOfObsoleteDepositReserves;
    ++i;
    _data[i] = (byte) hasDebt;
    ++i;
    referrer.write(_data, i);
    i += 32;
    _data[i] = (byte) borrowingDisabled;
    ++i;
    _data[i] = (byte) autodeleverageTargetLtvPct;
    ++i;
    _data[i] = (byte) lowestReserveDepositMaxLtvPct;
    ++i;
    _data[i] = (byte) numOfObsoleteBorrowReserves;
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    putInt64LE(_data, i, highestBorrowFactorPct);
    i += 8;
    putInt64LE(_data, i, autodeleverageMarginCallStartedTimestamp);
    i += 8;
    i += Borsh.writeArray(orders, _data, i);
    i += Borsh.writeArray(padding3, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
