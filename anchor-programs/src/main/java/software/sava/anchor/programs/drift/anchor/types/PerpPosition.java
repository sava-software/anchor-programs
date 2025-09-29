package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
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
                           // The amount of non reduce only trigger orders the user has open
                           // precision: BASE_PRECISION
                           long openBids,
                           // The amount of non reduce only trigger orders the user has open
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
                           byte[] padding,
                           int maxMarginRatio,
                           // The market index for the perp market
                           int marketIndex,
                           // The number of open orders
                           int openOrders,
                           int perLpBase) implements Borsh {

  public static final int BYTES = 96;
  public static final int PADDING_LEN = 2;

  public static PerpPosition read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
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
    final var padding = new byte[2];
    i += Borsh.readArray(padding, _data, i);
    final var maxMarginRatio = getInt16LE(_data, i);
    i += 2;
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
                            padding,
                            maxMarginRatio,
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
    i += Borsh.writeArrayChecked(padding, 2, _data, i);
    putInt16LE(_data, i, maxMarginRatio);
    i += 2;
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
    return BYTES;
  }
}
