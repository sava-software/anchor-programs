package software.sava.anchor.programs.drift;

import software.sava.anchor.programs.drift.anchor.types.*;

public final class DriftUtil {

  public static OrderParams simpleOrder(final OrderType orderType,
                                        final MarketType marketType,
                                        final PositionDirection direction,
                                        final int userOrderId,
                                        final long baseAssetAmount,
                                        final long price,
                                        final int marketIndex,
                                        final boolean reduceOnly,
                                        final PostOnlyParam postOnly,
                                        final boolean immediateOrCancel) {
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
        immediateOrCancel,
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
