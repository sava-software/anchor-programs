package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.*;

import java.util.Set;

public final class DriftUtil {

  public static int bitFlags(final Set<OrderParamsBitFlag> orderParams) {
    if (orderParams == null) {
      return 0;
    }
    int bitFlags = 0;
    for (final var orderParam : orderParams) {
      bitFlags |= (1 << orderParam.ordinal());
    }
    return bitFlags;
  }

  public static OrderParams simpleOrder(final OrderType orderType,
                                        final MarketType marketType,
                                        final PositionDirection direction,
                                        final int userOrderId,
                                        final long baseAssetAmount,
                                        final long price,
                                        final int marketIndex,
                                        final boolean reduceOnly,
                                        final PostOnlyParam postOnly,
                                        final Set<OrderParamsBitFlag> orderParams) {
    return new OrderParams(
        orderType,
        marketType,
        direction,
        userOrderId,
        baseAssetAmount,
        price,
        marketIndex,
        reduceOnly,
        postOnly,
        bitFlags(orderParams),
        null,
        null,
        OrderTriggerCondition.Above,
        null,
        null,
        null,
        null
    );
  }

  private DriftUtil() {
  }
}
