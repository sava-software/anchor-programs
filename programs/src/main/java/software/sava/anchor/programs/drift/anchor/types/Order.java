package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Order(// The slot the order was placed
                    long slot,
                    // The limit price for the order (can be 0 for market orders)
                    // For orders with an auction, this price isn't used until the auction is complete
                    // precision: PRICE_PRECISION
                    long price,
                    // The size of the order
                    // precision for perps: BASE_PRECISION
                    // precision for spot: token mint precision
                    long baseAssetAmount,
                    // The amount of the order filled
                    // precision for perps: BASE_PRECISION
                    // precision for spot: token mint precision
                    long baseAssetAmountFilled,
                    // The amount of quote filled for the order
                    // precision: QUOTE_PRECISION
                    long quoteAssetAmountFilled,
                    // At what price the order will be triggered. Only relevant for trigger orders
                    // precision: PRICE_PRECISION
                    long triggerPrice,
                    // The start price for the auction. Only relevant for market/oracle orders
                    // precision: PRICE_PRECISION
                    long auctionStartPrice,
                    // The end price for the auction. Only relevant for market/oracle orders
                    // precision: PRICE_PRECISION
                    long auctionEndPrice,
                    // The time when the order will expire
                    long maxTs,
                    // If set, the order limit price is the oracle price + this offset
                    // precision: PRICE_PRECISION
                    int oraclePriceOffset,
                    // The id for the order. Each users has their own order id space
                    int orderId,
                    // The perp/spot market index
                    int marketIndex,
                    // Whether the order is open or unused
                    OrderStatus status,
                    // The type of order
                    OrderType orderType,
                    // Whether market is spot or perp
                    MarketType marketType,
                    // User generated order id. Can make it easier to place/cancel orders
                    int userOrderId,
                    // What the users position was when the order was placed
                    PositionDirection existingPositionDirection,
                    // Whether the user is going long or short. LONG = bid, SHORT = ask
                    PositionDirection direction,
                    // Whether the order is allowed to only reduce position size
                    boolean reduceOnly,
                    // Whether the order must be a maker
                    boolean postOnly,
                    // Whether the order must be canceled the same slot it is placed
                    boolean immediateOrCancel,
                    // Whether the order is triggered above or below the trigger price. Only relevant for trigger orders
                    OrderTriggerCondition triggerCondition,
                    // How many slots the auction lasts
                    int auctionDuration,
                    // Last 8 bits of the slot the order was posted on-chain (not order slot for signed msg orders)
                    int postedSlotTail,
                    byte[] padding) implements Borsh {

  public static final int BYTES = 96;

  public static Order read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmountFilled = getInt64LE(_data, i);
    i += 8;
    final var quoteAssetAmountFilled = getInt64LE(_data, i);
    i += 8;
    final var triggerPrice = getInt64LE(_data, i);
    i += 8;
    final var auctionStartPrice = getInt64LE(_data, i);
    i += 8;
    final var auctionEndPrice = getInt64LE(_data, i);
    i += 8;
    final var maxTs = getInt64LE(_data, i);
    i += 8;
    final var oraclePriceOffset = getInt32LE(_data, i);
    i += 4;
    final var orderId = getInt32LE(_data, i);
    i += 4;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var status = OrderStatus.read(_data, i);
    i += Borsh.len(status);
    final var orderType = OrderType.read(_data, i);
    i += Borsh.len(orderType);
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var userOrderId = _data[i] & 0xFF;
    ++i;
    final var existingPositionDirection = PositionDirection.read(_data, i);
    i += Borsh.len(existingPositionDirection);
    final var direction = PositionDirection.read(_data, i);
    i += Borsh.len(direction);
    final var reduceOnly = _data[i] == 1;
    ++i;
    final var postOnly = _data[i] == 1;
    ++i;
    final var immediateOrCancel = _data[i] == 1;
    ++i;
    final var triggerCondition = OrderTriggerCondition.read(_data, i);
    i += Borsh.len(triggerCondition);
    final var auctionDuration = _data[i] & 0xFF;
    ++i;
    final var postedSlotTail = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[2];
    Borsh.readArray(padding, _data, i);
    return new Order(slot,
                     price,
                     baseAssetAmount,
                     baseAssetAmountFilled,
                     quoteAssetAmountFilled,
                     triggerPrice,
                     auctionStartPrice,
                     auctionEndPrice,
                     maxTs,
                     oraclePriceOffset,
                     orderId,
                     marketIndex,
                     status,
                     orderType,
                     marketType,
                     userOrderId,
                     existingPositionDirection,
                     direction,
                     reduceOnly,
                     postOnly,
                     immediateOrCancel,
                     triggerCondition,
                     auctionDuration,
                     postedSlotTail,
                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt64LE(_data, i, price);
    i += 8;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, baseAssetAmountFilled);
    i += 8;
    putInt64LE(_data, i, quoteAssetAmountFilled);
    i += 8;
    putInt64LE(_data, i, triggerPrice);
    i += 8;
    putInt64LE(_data, i, auctionStartPrice);
    i += 8;
    putInt64LE(_data, i, auctionEndPrice);
    i += 8;
    putInt64LE(_data, i, maxTs);
    i += 8;
    putInt32LE(_data, i, oraclePriceOffset);
    i += 4;
    putInt32LE(_data, i, orderId);
    i += 4;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(status, _data, i);
    i += Borsh.write(orderType, _data, i);
    i += Borsh.write(marketType, _data, i);
    _data[i] = (byte) userOrderId;
    ++i;
    i += Borsh.write(existingPositionDirection, _data, i);
    i += Borsh.write(direction, _data, i);
    _data[i] = (byte) (reduceOnly ? 1 : 0);
    ++i;
    _data[i] = (byte) (postOnly ? 1 : 0);
    ++i;
    _data[i] = (byte) (immediateOrCancel ? 1 : 0);
    ++i;
    i += Borsh.write(triggerCondition, _data, i);
    _data[i] = (byte) auctionDuration;
    ++i;
    _data[i] = (byte) postedSlotTail;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
