package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.*;

import java.util.EnumSet;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.OptionalLong;

public final class OrderParamsBuilder {

  private OrderType orderType = OrderType.Limit;
  private MarketType marketType = MarketType.Perp;
  private PositionDirection direction;
  private int userOrderId;
  private long baseAssetAmount;
  private long price;
  private int marketIndex = -1;
  private boolean reduceOnly;
  private PostOnlyParam postOnly = PostOnlyParam.MustPostOnly;
  private EnumSet<OrderParamsBitFlag> orderParams;
  private OptionalLong maxTs;
  private OptionalLong triggerPrice;
  private OrderTriggerCondition triggerCondition;
  private OptionalInt oraclePriceOffset;
  private OptionalInt auctionDuration;
  private OptionalLong auctionStartPrice;
  private OptionalLong auctionEndPrice;

  public OrderParamsBuilder(final PositionDirection direction,
                            final long baseAssetAmount,
                            final long price,
                            final int marketIndex,
                            final OrderTriggerCondition triggerCondition) {
    this.direction = direction;
    this.baseAssetAmount = baseAssetAmount;
    this.price = price;
    this.marketIndex = marketIndex;
    this.triggerCondition = triggerCondition;
    this.orderParams = EnumSet.noneOf(OrderParamsBitFlag.class);
  }

  public OrderParams createParams() {
    if (price <= 0) {
      throw new IllegalStateException("Must initialize price.");
    }
    if (marketIndex < 0) {
      throw new IllegalStateException("Must initialize market index.");
    }
    return new OrderParams(
        orderType,
        marketType,
        Objects.requireNonNull(direction),
        userOrderId,
        baseAssetAmount,
        price,
        marketIndex,
        reduceOnly,
        postOnly,
        DriftUtil.bitFlags(orderParams),
        maxTs,
        triggerPrice,
        Objects.requireNonNull(triggerCondition),
        oraclePriceOffset,
        auctionDuration,
        auctionStartPrice,
        auctionEndPrice
    );
  }

  public OrderType orderType() {
    return orderType;
  }

  public OrderParamsBuilder orderType(final OrderType orderType) {
    this.orderType = orderType;
    return this;
  }

  public MarketType marketType() {
    return marketType;
  }

  public OrderParamsBuilder marketType(final MarketType marketType) {
    this.marketType = marketType;
    return this;
  }

  public PositionDirection direction() {
    return direction;
  }

  public OrderParamsBuilder direction(final PositionDirection direction) {
    this.direction = direction;
    return this;
  }

  public int userOrderId() {
    return userOrderId;
  }

  public OrderParamsBuilder userOrderId(final int userOrderId) {
    this.userOrderId = userOrderId;
    return this;
  }

  public long baseAssetAmount() {
    return baseAssetAmount;
  }

  public OrderParamsBuilder baseAssetAmount(final long baseAssetAmount) {
    this.baseAssetAmount = baseAssetAmount;
    return this;
  }

  public long price() {
    return price;
  }

  public OrderParamsBuilder price(final long price) {
    this.price = price;
    return this;
  }

  public int marketIndex() {
    return marketIndex;
  }

  public OrderParamsBuilder marketIndex(final int marketIndex) {
    this.marketIndex = marketIndex;
    return this;
  }

  public boolean reduceOnly() {
    return reduceOnly;
  }

  public OrderParamsBuilder reduceOnly(final boolean reduceOnly) {
    this.reduceOnly = reduceOnly;
    return this;
  }

  public PostOnlyParam postOnly() {
    return postOnly;
  }

  public OrderParamsBuilder postOnly(final PostOnlyParam postOnly) {
    this.postOnly = postOnly;
    return this;
  }

  public boolean immediateOrCancel() {
    return orderParams.contains(OrderParamsBitFlag.ImmediateOrCancel);
  }

  public OrderParamsBuilder immediateOrCancel(final boolean immediateOrCancel) {
    if (immediateOrCancel) {
      this.orderParams.add(OrderParamsBitFlag.ImmediateOrCancel);
    } else {
      this.orderParams.remove(OrderParamsBitFlag.ImmediateOrCancel);
    }
    return this;
  }

  public OptionalLong maxTs() {
    return maxTs;
  }

  public OrderParamsBuilder maxTs(final long maxTs) {
    this.maxTs = OptionalLong.of(maxTs);
    return this;
  }

  public OptionalLong triggerPrice() {
    return triggerPrice;
  }

  public OrderParamsBuilder triggerPrice(final long triggerPrice) {
    this.triggerPrice = OptionalLong.of(triggerPrice);
    return this;
  }

  public OrderTriggerCondition triggerCondition() {
    return triggerCondition;
  }

  public OrderParamsBuilder triggerCondition(final OrderTriggerCondition triggerCondition) {
    this.triggerCondition = triggerCondition;
    return this;
  }

  public OptionalInt oraclePriceOffset() {
    return oraclePriceOffset;
  }

  public OrderParamsBuilder oraclePriceOffset(final int oraclePriceOffset) {
    this.oraclePriceOffset = OptionalInt.of(oraclePriceOffset);
    return this;
  }

  public OptionalInt auctionDuration() {
    return auctionDuration;
  }

  public OrderParamsBuilder auctionDuration(final int auctionDuration) {
    this.auctionDuration = OptionalInt.of(auctionDuration);
    return this;
  }

  public OptionalLong auctionStartPrice() {
    return auctionStartPrice;
  }

  public OrderParamsBuilder auctionStartPrice(final long auctionStartPrice) {
    this.auctionStartPrice = OptionalLong.of(auctionStartPrice);
    return this;
  }

  public OptionalLong auctionEndPrice() {
    return auctionEndPrice;
  }

  public OrderParamsBuilder auctionEndPrice(final long auctionEndPrice) {
    this.auctionEndPrice = OptionalLong.of(auctionEndPrice);
    return this;
  }
}
