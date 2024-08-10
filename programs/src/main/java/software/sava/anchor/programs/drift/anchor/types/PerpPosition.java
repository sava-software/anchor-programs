package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PerpPosition(// The perp market's last cumulative funding rate. Used to calculate the funding payment owed to user
                           // precision: FUNDING_RATE_PRECISION
                           long lastCumulativeFundingRate,
                           // the size of the users perp position
                           // precision: BASE_PRECISION
                           long baseAssetAmount,
                           // Used to calculate the users pnl. Upon entry, is equal to base_asset_amount * avg entry price - fees
                           // Updated when the user open/closes position or settles pnl. Includes fees/funding
                           // precision: QUOTE_PRECISION
                           long quoteAssetAmount,
                           // The amount of quote the user would need to exit their position at to break even
                           // Updated when the user open/closes position or settles pnl. Includes fees/funding
                           // precision: QUOTE_PRECISION
                           long quoteBreakEvenAmount,
                           // The amount quote the user entered the position with. Equal to base asset amount * avg entry price
                           // Updated when the user open/closes position. Excludes fees/funding
                           // precision: QUOTE_PRECISION
                           long quoteEntryAmount,
                           // The amount of open bids the user has in this perp market
                           // precision: BASE_PRECISION
                           long openBids,
                           // The amount of open asks the user has in this perp market
                           // precision: BASE_PRECISION
                           long openAsks,
                           // The amount of pnl settled in this market since opening the position
                           // precision: QUOTE_PRECISION
                           long settledPnl,
                           // The number of lp (liquidity provider) shares the user has in this perp market
                           // LP shares allow users to provide liquidity via the AMM
                           // precision: BASE_PRECISION
                           long lpShares,
                           // The last base asset amount per lp the amm had
                           // Used to settle the users lp position
                           // precision: BASE_PRECISION
                           long lastBaseAssetAmountPerLp,
                           // The last quote asset amount per lp the amm had
                           // Used to settle the users lp position
                           // precision: QUOTE_PRECISION
                           long lastQuoteAssetAmountPerLp,
                           // Settling LP position can lead to a small amount of base asset being left over smaller than step size
                           // This records that remainder so it can be settled later on
                           // precision: BASE_PRECISION
                           int remainderBaseAssetAmount,
                           // The market index for the perp market
                           int marketIndex,
                           // The number of open orders
                           int openOrders,
                           int perLpBase) implements Borsh {

  public static final int BYTES = 96;

  public static PerpPosition read(final byte[] _data, final int offset) {
    int i = offset;
    final var lastCumulativeFundingRate = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteBreakEvenAmount = getInt64LE(_data, i);
    i += 8;
    final var quoteEntryAmount = getInt64LE(_data, i);
    i += 8;
    final var openBids = getInt64LE(_data, i);
    i += 8;
    final var openAsks = getInt64LE(_data, i);
    i += 8;
    final var settledPnl = getInt64LE(_data, i);
    i += 8;
    final var lpShares = getInt64LE(_data, i);
    i += 8;
    final var lastBaseAssetAmountPerLp = getInt64LE(_data, i);
    i += 8;
    final var lastQuoteAssetAmountPerLp = getInt64LE(_data, i);
    i += 8;
    final var remainderBaseAssetAmount = getInt32LE(_data, i);
    i += 4;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var openOrders = _data[i] & 0xFF;
    ++i;
    final var perLpBase = _data[i];
    return new PerpPosition(lastCumulativeFundingRate,
                            baseAssetAmount,
                            quoteAssetAmount,
                            quoteBreakEvenAmount,
                            quoteEntryAmount,
                            openBids,
                            openAsks,
                            settledPnl,
                            lpShares,
                            lastBaseAssetAmountPerLp,
                            lastQuoteAssetAmountPerLp,
                            remainderBaseAssetAmount,
                            marketIndex,
                            openOrders,
                            perLpBase);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, lastCumulativeFundingRate);
    i += 8;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, quoteAssetAmount);
    i += 8;
    putInt64LE(_data, i, quoteBreakEvenAmount);
    i += 8;
    putInt64LE(_data, i, quoteEntryAmount);
    i += 8;
    putInt64LE(_data, i, openBids);
    i += 8;
    putInt64LE(_data, i, openAsks);
    i += 8;
    putInt64LE(_data, i, settledPnl);
    i += 8;
    putInt64LE(_data, i, lpShares);
    i += 8;
    putInt64LE(_data, i, lastBaseAssetAmountPerLp);
    i += 8;
    putInt64LE(_data, i, lastQuoteAssetAmountPerLp);
    i += 8;
    putInt32LE(_data, i, remainderBaseAssetAmount);
    i += 4;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    _data[i] = (byte) openOrders;
    ++i;
    _data[i] = (byte) perLpBase;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 4
         + 2
         + 1
         + 1;
  }
}