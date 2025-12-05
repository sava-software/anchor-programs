package software.sava.anchor.programs.drift.anchor.types;

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

public record UserStats(PublicKey _address,
                        Discriminator discriminator,
                        // The authority for all of a users sub accounts
                        PublicKey authority,
                        // The address that referred this user
                        PublicKey referrer,
                        // Stats on the fees paid by the user
                        UserFees fees,
                        // The timestamp of the next epoch
                        // Epoch is used to limit referrer rewards earned in single epoch
                        long nextEpochTs,
                        // Rolling 30day maker volume for user
                        // precision: QUOTE_PRECISION
                        long makerVolume30d,
                        // Rolling 30day taker volume for user
                        // precision: QUOTE_PRECISION
                        long takerVolume30d,
                        // Rolling 30day filler volume for user
                        // precision: QUOTE_PRECISION
                        long fillerVolume30d,
                        // last time the maker volume was updated
                        long lastMakerVolume30dTs,
                        // last time the taker volume was updated
                        long lastTakerVolume30dTs,
                        // last time the filler volume was updated
                        long lastFillerVolume30dTs,
                        // The amount of tokens staked in the quote spot markets if
                        long ifStakedQuoteAssetAmount,
                        // The current number of sub accounts
                        int numberOfSubAccounts,
                        // The number of sub accounts created. Can be greater than the number of sub accounts if user
                        // has deleted sub accounts
                        int numberOfSubAccountsCreated,
                        // Flags for referrer status:
                        // First bit (LSB): 1 if user is a referrer, 0 otherwise
                        // Second bit: 1 if user was referred, 0 otherwise
                        int referrerStatus,
                        boolean disableUpdatePerpBidAskTwap,
                        int pausedOperations,
                        // whether the user has a FuelOverflow account
                        int fuelOverflowStatus,
                        // accumulated fuel for token amounts of insurance
                        int fuelInsurance,
                        // accumulated fuel for notional of deposits
                        int fuelDeposits,
                        // accumulate fuel bonus for notional of borrows
                        int fuelBorrows,
                        // accumulated fuel for perp open interest
                        int fuelPositions,
                        // accumulate fuel bonus for taker volume
                        int fuelTaker,
                        // accumulate fuel bonus for maker volume
                        int fuelMaker,
                        // The amount of tokens staked in the governance spot markets if
                        long ifStakedGovTokenAmount,
                        // last unix ts user stats data was used to update if fuel (u32 to save space)
                        int lastFuelIfBonusUpdateTs,
                        byte[] padding) implements Borsh {

  public static final int BYTES = 240;
  public static final int PADDING_LEN = 12;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int AUTHORITY_OFFSET = 8;
  public static final int REFERRER_OFFSET = 40;
  public static final int FEES_OFFSET = 72;
  public static final int NEXT_EPOCH_TS_OFFSET = 120;
  public static final int MAKER_VOLUME_33D_OFFSET = 128;
  public static final int TAKER_VOLUME_33D_OFFSET = 136;
  public static final int FILLER_VOLUME_33D_OFFSET = 144;
  public static final int LAST_MAKER_VOLUME_33D_TS_OFFSET = 152;
  public static final int LAST_TAKER_VOLUME_33D_TS_OFFSET = 160;
  public static final int LAST_FILLER_VOLUME_33D_TS_OFFSET = 168;
  public static final int IF_STAKED_QUOTE_ASSET_AMOUNT_OFFSET = 176;
  public static final int NUMBER_OF_SUB_ACCOUNTS_OFFSET = 184;
  public static final int NUMBER_OF_SUB_ACCOUNTS_CREATED_OFFSET = 186;
  public static final int REFERRER_STATUS_OFFSET = 188;
  public static final int DISABLE_UPDATE_PERP_BID_ASK_TWAP_OFFSET = 189;
  public static final int PAUSED_OPERATIONS_OFFSET = 190;
  public static final int FUEL_OVERFLOW_STATUS_OFFSET = 191;
  public static final int FUEL_INSURANCE_OFFSET = 192;
  public static final int FUEL_DEPOSITS_OFFSET = 196;
  public static final int FUEL_BORROWS_OFFSET = 200;
  public static final int FUEL_POSITIONS_OFFSET = 204;
  public static final int FUEL_TAKER_OFFSET = 208;
  public static final int FUEL_MAKER_OFFSET = 212;
  public static final int IF_STAKED_GOV_TOKEN_AMOUNT_OFFSET = 216;
  public static final int LAST_FUEL_IF_BONUS_UPDATE_TS_OFFSET = 224;
  public static final int PADDING_OFFSET = 228;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createReferrerFilter(final PublicKey referrer) {
    return Filter.createMemCompFilter(REFERRER_OFFSET, referrer);
  }

  public static Filter createFeesFilter(final UserFees fees) {
    return Filter.createMemCompFilter(FEES_OFFSET, fees.write());
  }

  public static Filter createNextEpochTsFilter(final long nextEpochTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextEpochTs);
    return Filter.createMemCompFilter(NEXT_EPOCH_TS_OFFSET, _data);
  }

  public static Filter createMakerVolume30dFilter(final long makerVolume30d) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, makerVolume30d);
    return Filter.createMemCompFilter(MAKER_VOLUME_33D_OFFSET, _data);
  }

  public static Filter createTakerVolume30dFilter(final long takerVolume30d) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, takerVolume30d);
    return Filter.createMemCompFilter(TAKER_VOLUME_33D_OFFSET, _data);
  }

  public static Filter createFillerVolume30dFilter(final long fillerVolume30d) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, fillerVolume30d);
    return Filter.createMemCompFilter(FILLER_VOLUME_33D_OFFSET, _data);
  }

  public static Filter createLastMakerVolume30dTsFilter(final long lastMakerVolume30dTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastMakerVolume30dTs);
    return Filter.createMemCompFilter(LAST_MAKER_VOLUME_33D_TS_OFFSET, _data);
  }

  public static Filter createLastTakerVolume30dTsFilter(final long lastTakerVolume30dTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastTakerVolume30dTs);
    return Filter.createMemCompFilter(LAST_TAKER_VOLUME_33D_TS_OFFSET, _data);
  }

  public static Filter createLastFillerVolume30dTsFilter(final long lastFillerVolume30dTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastFillerVolume30dTs);
    return Filter.createMemCompFilter(LAST_FILLER_VOLUME_33D_TS_OFFSET, _data);
  }

  public static Filter createIfStakedQuoteAssetAmountFilter(final long ifStakedQuoteAssetAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ifStakedQuoteAssetAmount);
    return Filter.createMemCompFilter(IF_STAKED_QUOTE_ASSET_AMOUNT_OFFSET, _data);
  }

  public static Filter createNumberOfSubAccountsFilter(final int numberOfSubAccounts) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, numberOfSubAccounts);
    return Filter.createMemCompFilter(NUMBER_OF_SUB_ACCOUNTS_OFFSET, _data);
  }

  public static Filter createNumberOfSubAccountsCreatedFilter(final int numberOfSubAccountsCreated) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, numberOfSubAccountsCreated);
    return Filter.createMemCompFilter(NUMBER_OF_SUB_ACCOUNTS_CREATED_OFFSET, _data);
  }

  public static Filter createReferrerStatusFilter(final int referrerStatus) {
    return Filter.createMemCompFilter(REFERRER_STATUS_OFFSET, new byte[]{(byte) referrerStatus});
  }

  public static Filter createDisableUpdatePerpBidAskTwapFilter(final boolean disableUpdatePerpBidAskTwap) {
    return Filter.createMemCompFilter(DISABLE_UPDATE_PERP_BID_ASK_TWAP_OFFSET, new byte[]{(byte) (disableUpdatePerpBidAskTwap ? 1 : 0)});
  }

  public static Filter createPausedOperationsFilter(final int pausedOperations) {
    return Filter.createMemCompFilter(PAUSED_OPERATIONS_OFFSET, new byte[]{(byte) pausedOperations});
  }

  public static Filter createFuelOverflowStatusFilter(final int fuelOverflowStatus) {
    return Filter.createMemCompFilter(FUEL_OVERFLOW_STATUS_OFFSET, new byte[]{(byte) fuelOverflowStatus});
  }

  public static Filter createFuelInsuranceFilter(final int fuelInsurance) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelInsurance);
    return Filter.createMemCompFilter(FUEL_INSURANCE_OFFSET, _data);
  }

  public static Filter createFuelDepositsFilter(final int fuelDeposits) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelDeposits);
    return Filter.createMemCompFilter(FUEL_DEPOSITS_OFFSET, _data);
  }

  public static Filter createFuelBorrowsFilter(final int fuelBorrows) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelBorrows);
    return Filter.createMemCompFilter(FUEL_BORROWS_OFFSET, _data);
  }

  public static Filter createFuelPositionsFilter(final int fuelPositions) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelPositions);
    return Filter.createMemCompFilter(FUEL_POSITIONS_OFFSET, _data);
  }

  public static Filter createFuelTakerFilter(final int fuelTaker) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelTaker);
    return Filter.createMemCompFilter(FUEL_TAKER_OFFSET, _data);
  }

  public static Filter createFuelMakerFilter(final int fuelMaker) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, fuelMaker);
    return Filter.createMemCompFilter(FUEL_MAKER_OFFSET, _data);
  }

  public static Filter createIfStakedGovTokenAmountFilter(final long ifStakedGovTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ifStakedGovTokenAmount);
    return Filter.createMemCompFilter(IF_STAKED_GOV_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createLastFuelIfBonusUpdateTsFilter(final int lastFuelIfBonusUpdateTs) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastFuelIfBonusUpdateTs);
    return Filter.createMemCompFilter(LAST_FUEL_IF_BONUS_UPDATE_TS_OFFSET, _data);
  }

  public static UserStats read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UserStats read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static UserStats read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UserStats> FACTORY = UserStats::read;

  public static UserStats read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var referrer = readPubKey(_data, i);
    i += 32;
    final var fees = UserFees.read(_data, i);
    i += Borsh.len(fees);
    final var nextEpochTs = getInt64LE(_data, i);
    i += 8;
    final var makerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var takerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var fillerVolume30d = getInt64LE(_data, i);
    i += 8;
    final var lastMakerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var lastTakerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var lastFillerVolume30dTs = getInt64LE(_data, i);
    i += 8;
    final var ifStakedQuoteAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var numberOfSubAccounts = getInt16LE(_data, i);
    i += 2;
    final var numberOfSubAccountsCreated = getInt16LE(_data, i);
    i += 2;
    final var referrerStatus = _data[i] & 0xFF;
    ++i;
    final var disableUpdatePerpBidAskTwap = _data[i] == 1;
    ++i;
    final var pausedOperations = _data[i] & 0xFF;
    ++i;
    final var fuelOverflowStatus = _data[i] & 0xFF;
    ++i;
    final var fuelInsurance = getInt32LE(_data, i);
    i += 4;
    final var fuelDeposits = getInt32LE(_data, i);
    i += 4;
    final var fuelBorrows = getInt32LE(_data, i);
    i += 4;
    final var fuelPositions = getInt32LE(_data, i);
    i += 4;
    final var fuelTaker = getInt32LE(_data, i);
    i += 4;
    final var fuelMaker = getInt32LE(_data, i);
    i += 4;
    final var ifStakedGovTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var lastFuelIfBonusUpdateTs = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[12];
    Borsh.readArray(padding, _data, i);
    return new UserStats(_address,
                         discriminator,
                         authority,
                         referrer,
                         fees,
                         nextEpochTs,
                         makerVolume30d,
                         takerVolume30d,
                         fillerVolume30d,
                         lastMakerVolume30dTs,
                         lastTakerVolume30dTs,
                         lastFillerVolume30dTs,
                         ifStakedQuoteAssetAmount,
                         numberOfSubAccounts,
                         numberOfSubAccountsCreated,
                         referrerStatus,
                         disableUpdatePerpBidAskTwap,
                         pausedOperations,
                         fuelOverflowStatus,
                         fuelInsurance,
                         fuelDeposits,
                         fuelBorrows,
                         fuelPositions,
                         fuelTaker,
                         fuelMaker,
                         ifStakedGovTokenAmount,
                         lastFuelIfBonusUpdateTs,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    referrer.write(_data, i);
    i += 32;
    i += Borsh.write(fees, _data, i);
    putInt64LE(_data, i, nextEpochTs);
    i += 8;
    putInt64LE(_data, i, makerVolume30d);
    i += 8;
    putInt64LE(_data, i, takerVolume30d);
    i += 8;
    putInt64LE(_data, i, fillerVolume30d);
    i += 8;
    putInt64LE(_data, i, lastMakerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, lastTakerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, lastFillerVolume30dTs);
    i += 8;
    putInt64LE(_data, i, ifStakedQuoteAssetAmount);
    i += 8;
    putInt16LE(_data, i, numberOfSubAccounts);
    i += 2;
    putInt16LE(_data, i, numberOfSubAccountsCreated);
    i += 2;
    _data[i] = (byte) referrerStatus;
    ++i;
    _data[i] = (byte) (disableUpdatePerpBidAskTwap ? 1 : 0);
    ++i;
    _data[i] = (byte) pausedOperations;
    ++i;
    _data[i] = (byte) fuelOverflowStatus;
    ++i;
    putInt32LE(_data, i, fuelInsurance);
    i += 4;
    putInt32LE(_data, i, fuelDeposits);
    i += 4;
    putInt32LE(_data, i, fuelBorrows);
    i += 4;
    putInt32LE(_data, i, fuelPositions);
    i += 4;
    putInt32LE(_data, i, fuelTaker);
    i += 4;
    putInt32LE(_data, i, fuelMaker);
    i += 4;
    putInt64LE(_data, i, ifStakedGovTokenAmount);
    i += 8;
    putInt32LE(_data, i, lastFuelIfBonusUpdateTs);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 12, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
