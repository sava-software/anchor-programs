package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record Position(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey owner,
                       PublicKey market,
                       PublicKey delegate,
                       long openTime,
                       long updateTime,
                       OraclePrice entryPrice,
                       long sizeAmount,
                       long sizeUsd,
                       long lockedAmount,
                       long lockedUsd,
                       long collateralAmount,
                       long collateralUsd,
                       long unsettledAmount,
                       long unsettledFeesUsd,
                       BigInteger cumulativeLockFeeSnapshot,
                       long degenSizeUsd,
                       BigInteger buffer,
                       int sizeDecimals,
                       int lockedDecimals,
                       int collateralDecimals,
                       int bump,
                       byte[] padding) implements Borsh {

  public static final int BYTES = 252;
  public static final int PADDING_LEN = 12;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int MARKET_OFFSET = 40;
  public static final int DELEGATE_OFFSET = 72;
  public static final int OPEN_TIME_OFFSET = 104;
  public static final int UPDATE_TIME_OFFSET = 112;
  public static final int ENTRY_PRICE_OFFSET = 120;
  public static final int SIZE_AMOUNT_OFFSET = 132;
  public static final int SIZE_USD_OFFSET = 140;
  public static final int LOCKED_AMOUNT_OFFSET = 148;
  public static final int LOCKED_USD_OFFSET = 156;
  public static final int COLLATERAL_AMOUNT_OFFSET = 164;
  public static final int COLLATERAL_USD_OFFSET = 172;
  public static final int UNSETTLED_AMOUNT_OFFSET = 180;
  public static final int UNSETTLED_FEES_USD_OFFSET = 188;
  public static final int CUMULATIVE_LOCK_FEE_SNAPSHOT_OFFSET = 196;
  public static final int DEGEN_SIZE_USD_OFFSET = 212;
  public static final int BUFFER_OFFSET = 220;
  public static final int SIZE_DECIMALS_OFFSET = 236;
  public static final int LOCKED_DECIMALS_OFFSET = 237;
  public static final int COLLATERAL_DECIMALS_OFFSET = 238;
  public static final int BUMP_OFFSET = 239;
  public static final int PADDING_OFFSET = 240;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createMarketFilter(final PublicKey market) {
    return Filter.createMemCompFilter(MARKET_OFFSET, market);
  }

  public static Filter createDelegateFilter(final PublicKey delegate) {
    return Filter.createMemCompFilter(DELEGATE_OFFSET, delegate);
  }

  public static Filter createOpenTimeFilter(final long openTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, openTime);
    return Filter.createMemCompFilter(OPEN_TIME_OFFSET, _data);
  }

  public static Filter createUpdateTimeFilter(final long updateTime) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, updateTime);
    return Filter.createMemCompFilter(UPDATE_TIME_OFFSET, _data);
  }

  public static Filter createEntryPriceFilter(final OraclePrice entryPrice) {
    return Filter.createMemCompFilter(ENTRY_PRICE_OFFSET, entryPrice.write());
  }

  public static Filter createSizeAmountFilter(final long sizeAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sizeAmount);
    return Filter.createMemCompFilter(SIZE_AMOUNT_OFFSET, _data);
  }

  public static Filter createSizeUsdFilter(final long sizeUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sizeUsd);
    return Filter.createMemCompFilter(SIZE_USD_OFFSET, _data);
  }

  public static Filter createLockedAmountFilter(final long lockedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedAmount);
    return Filter.createMemCompFilter(LOCKED_AMOUNT_OFFSET, _data);
  }

  public static Filter createLockedUsdFilter(final long lockedUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedUsd);
    return Filter.createMemCompFilter(LOCKED_USD_OFFSET, _data);
  }

  public static Filter createCollateralAmountFilter(final long collateralAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, collateralAmount);
    return Filter.createMemCompFilter(COLLATERAL_AMOUNT_OFFSET, _data);
  }

  public static Filter createCollateralUsdFilter(final long collateralUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, collateralUsd);
    return Filter.createMemCompFilter(COLLATERAL_USD_OFFSET, _data);
  }

  public static Filter createUnsettledAmountFilter(final long unsettledAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unsettledAmount);
    return Filter.createMemCompFilter(UNSETTLED_AMOUNT_OFFSET, _data);
  }

  public static Filter createUnsettledFeesUsdFilter(final long unsettledFeesUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unsettledFeesUsd);
    return Filter.createMemCompFilter(UNSETTLED_FEES_USD_OFFSET, _data);
  }

  public static Filter createCumulativeLockFeeSnapshotFilter(final BigInteger cumulativeLockFeeSnapshot) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeLockFeeSnapshot);
    return Filter.createMemCompFilter(CUMULATIVE_LOCK_FEE_SNAPSHOT_OFFSET, _data);
  }

  public static Filter createDegenSizeUsdFilter(final long degenSizeUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, degenSizeUsd);
    return Filter.createMemCompFilter(DEGEN_SIZE_USD_OFFSET, _data);
  }

  public static Filter createBufferFilter(final BigInteger buffer) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, buffer);
    return Filter.createMemCompFilter(BUFFER_OFFSET, _data);
  }

  public static Filter createSizeDecimalsFilter(final int sizeDecimals) {
    return Filter.createMemCompFilter(SIZE_DECIMALS_OFFSET, new byte[]{(byte) sizeDecimals});
  }

  public static Filter createLockedDecimalsFilter(final int lockedDecimals) {
    return Filter.createMemCompFilter(LOCKED_DECIMALS_OFFSET, new byte[]{(byte) lockedDecimals});
  }

  public static Filter createCollateralDecimalsFilter(final int collateralDecimals) {
    return Filter.createMemCompFilter(COLLATERAL_DECIMALS_OFFSET, new byte[]{(byte) collateralDecimals});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Position read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Position read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Position read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Position> FACTORY = Position::read;

  public static Position read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var delegate = readPubKey(_data, i);
    i += 32;
    final var openTime = getInt64LE(_data, i);
    i += 8;
    final var updateTime = getInt64LE(_data, i);
    i += 8;
    final var entryPrice = OraclePrice.read(_data, i);
    i += Borsh.len(entryPrice);
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var sizeUsd = getInt64LE(_data, i);
    i += 8;
    final var lockedAmount = getInt64LE(_data, i);
    i += 8;
    final var lockedUsd = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var collateralUsd = getInt64LE(_data, i);
    i += 8;
    final var unsettledAmount = getInt64LE(_data, i);
    i += 8;
    final var unsettledFeesUsd = getInt64LE(_data, i);
    i += 8;
    final var cumulativeLockFeeSnapshot = getInt128LE(_data, i);
    i += 16;
    final var degenSizeUsd = getInt64LE(_data, i);
    i += 8;
    final var buffer = getInt128LE(_data, i);
    i += 16;
    final var sizeDecimals = _data[i] & 0xFF;
    ++i;
    final var lockedDecimals = _data[i] & 0xFF;
    ++i;
    final var collateralDecimals = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[12];
    Borsh.readArray(padding, _data, i);
    return new Position(_address,
                        discriminator,
                        owner,
                        market,
                        delegate,
                        openTime,
                        updateTime,
                        entryPrice,
                        sizeAmount,
                        sizeUsd,
                        lockedAmount,
                        lockedUsd,
                        collateralAmount,
                        collateralUsd,
                        unsettledAmount,
                        unsettledFeesUsd,
                        cumulativeLockFeeSnapshot,
                        degenSizeUsd,
                        buffer,
                        sizeDecimals,
                        lockedDecimals,
                        collateralDecimals,
                        bump,
                        padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    delegate.write(_data, i);
    i += 32;
    putInt64LE(_data, i, openTime);
    i += 8;
    putInt64LE(_data, i, updateTime);
    i += 8;
    i += Borsh.write(entryPrice, _data, i);
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    putInt64LE(_data, i, sizeUsd);
    i += 8;
    putInt64LE(_data, i, lockedAmount);
    i += 8;
    putInt64LE(_data, i, lockedUsd);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, collateralUsd);
    i += 8;
    putInt64LE(_data, i, unsettledAmount);
    i += 8;
    putInt64LE(_data, i, unsettledFeesUsd);
    i += 8;
    putInt128LE(_data, i, cumulativeLockFeeSnapshot);
    i += 16;
    putInt64LE(_data, i, degenSizeUsd);
    i += 8;
    putInt128LE(_data, i, buffer);
    i += 16;
    _data[i] = (byte) sizeDecimals;
    ++i;
    _data[i] = (byte) lockedDecimals;
    ++i;
    _data[i] = (byte) collateralDecimals;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    i += Borsh.writeArrayChecked(padding, 12, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
