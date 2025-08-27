package software.sava.anchor.programs.glam.protocol.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OrderParams(OrderType orderType,
                          MarketType marketType,
                          PositionDirection direction,
                          int userOrderId,
                          long baseAssetAmount,
                          long price,
                          int marketIndex,
                          boolean reduceOnly,
                          PostOnlyParam postOnly,
                          int bitFlags,
                          OptionalLong maxTs,
                          OptionalLong triggerPrice,
                          OrderTriggerCondition triggerCondition,
                          OptionalInt oraclePriceOffset,
                          OptionalInt auctionDuration,
                          OptionalLong auctionStartPrice,
                          OptionalLong auctionEndPrice) implements Borsh {

  public static OrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var orderType = OrderType.read(_data, i);
    i += Borsh.len(orderType);
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var direction = PositionDirection.read(_data, i);
    i += Borsh.len(direction);
    final var userOrderId = _data[i] & 0xFF;
    ++i;
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var reduceOnly = _data[i] == 1;
    ++i;
    final var postOnly = PostOnlyParam.read(_data, i);
    i += Borsh.len(postOnly);
    final var bitFlags = _data[i] & 0xFF;
    ++i;
    final var maxTs = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxTs.isPresent()) {
      i += 8;
    }
    final var triggerPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (triggerPrice.isPresent()) {
      i += 8;
    }
    final var triggerCondition = OrderTriggerCondition.read(_data, i);
    i += Borsh.len(triggerCondition);
    final var oraclePriceOffset = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (oraclePriceOffset.isPresent()) {
      i += 4;
    }
    final var auctionDuration = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (auctionDuration.isPresent()) {
      ++i;
    }
    final var auctionStartPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (auctionStartPrice.isPresent()) {
      i += 8;
    }
    final var auctionEndPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new OrderParams(orderType,
                           marketType,
                           direction,
                           userOrderId,
                           baseAssetAmount,
                           price,
                           marketIndex,
                           reduceOnly,
                           postOnly,
                           bitFlags,
                           maxTs,
                           triggerPrice,
                           triggerCondition,
                           oraclePriceOffset,
                           auctionDuration,
                           auctionStartPrice,
                           auctionEndPrice);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(orderType, _data, i);
    i += Borsh.write(marketType, _data, i);
    i += Borsh.write(direction, _data, i);
    _data[i] = (byte) userOrderId;
    ++i;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, price);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    _data[i] = (byte) (reduceOnly ? 1 : 0);
    ++i;
    i += Borsh.write(postOnly, _data, i);
    _data[i] = (byte) bitFlags;
    ++i;
    i += Borsh.writeOptional(maxTs, _data, i);
    i += Borsh.writeOptional(triggerPrice, _data, i);
    i += Borsh.write(triggerCondition, _data, i);
    i += Borsh.writeOptional(oraclePriceOffset, _data, i);
    i += Borsh.writeOptionalbyte(auctionDuration, _data, i);
    i += Borsh.writeOptional(auctionStartPrice, _data, i);
    i += Borsh.writeOptional(auctionEndPrice, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(orderType)
         + Borsh.len(marketType)
         + Borsh.len(direction)
         + 1
         + 8
         + 8
         + 2
         + 1
         + Borsh.len(postOnly)
         + 1
         + (maxTs == null || maxTs.isEmpty() ? 1 : (1 + 8))
         + (triggerPrice == null || triggerPrice.isEmpty() ? 1 : (1 + 8))
         + Borsh.len(triggerCondition)
         + (oraclePriceOffset == null || oraclePriceOffset.isEmpty() ? 1 : (1 + 4))
         + (auctionDuration == null || auctionDuration.isEmpty() ? 1 : (1 + 1))
         + (auctionStartPrice == null || auctionStartPrice.isEmpty() ? 1 : (1 + 8))
         + (auctionEndPrice == null || auctionEndPrice.isEmpty() ? 1 : (1 + 8));
  }
}
