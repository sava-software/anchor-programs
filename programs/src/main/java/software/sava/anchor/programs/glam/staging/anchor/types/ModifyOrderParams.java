package software.sava.anchor.programs.glam.staging.anchor.types;

import java.lang.Boolean;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record ModifyOrderParams(PositionDirection direction,
                                OptionalLong baseAssetAmount,
                                OptionalLong price,
                                Boolean reduceOnly,
                                PostOnlyParam postOnly,
                                Boolean immediateOrCancel,
                                OptionalLong maxTs,
                                OptionalLong triggerPrice,
                                OrderTriggerCondition triggerCondition,
                                OptionalInt oraclePriceOffset,
                                OptionalInt auctionDuration,
                                OptionalLong auctionStartPrice,
                                OptionalLong auctionEndPrice,
                                OptionalInt policy) implements Borsh {

  public static ModifyOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var direction = _data[i++] == 0 ? null : PositionDirection.read(_data, i);
    if (direction != null) {
      i += Borsh.len(direction);
    }
    final var baseAssetAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (baseAssetAmount.isPresent()) {
      i += 8;
    }
    final var price = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (price.isPresent()) {
      i += 8;
    }
    final var reduceOnly = _data[i++] == 0 ? null : _data[i] == 1;
    if (reduceOnly != null) {
      ++i;
    }
    final var postOnly = _data[i++] == 0 ? null : PostOnlyParam.read(_data, i);
    if (postOnly != null) {
      i += Borsh.len(postOnly);
    }
    final var immediateOrCancel = _data[i++] == 0 ? null : _data[i] == 1;
    if (immediateOrCancel != null) {
      ++i;
    }
    final var maxTs = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxTs.isPresent()) {
      i += 8;
    }
    final var triggerPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (triggerPrice.isPresent()) {
      i += 8;
    }
    final var triggerCondition = _data[i++] == 0 ? null : OrderTriggerCondition.read(_data, i);
    if (triggerCondition != null) {
      i += Borsh.len(triggerCondition);
    }
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
    if (auctionEndPrice.isPresent()) {
      i += 8;
    }
    final var policy = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    return new ModifyOrderParams(direction,
                                 baseAssetAmount,
                                 price,
                                 reduceOnly,
                                 postOnly,
                                 immediateOrCancel,
                                 maxTs,
                                 triggerPrice,
                                 triggerCondition,
                                 oraclePriceOffset,
                                 auctionDuration,
                                 auctionStartPrice,
                                 auctionEndPrice,
                                 policy);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(direction, _data, i);
    i += Borsh.writeOptional(baseAssetAmount, _data, i);
    i += Borsh.writeOptional(price, _data, i);
    i += Borsh.writeOptional(reduceOnly, _data, i);
    i += Borsh.writeOptional(postOnly, _data, i);
    i += Borsh.writeOptional(immediateOrCancel, _data, i);
    i += Borsh.writeOptional(maxTs, _data, i);
    i += Borsh.writeOptional(triggerPrice, _data, i);
    i += Borsh.writeOptional(triggerCondition, _data, i);
    i += Borsh.writeOptional(oraclePriceOffset, _data, i);
    i += Borsh.writeOptionalbyte(auctionDuration, _data, i);
    i += Borsh.writeOptional(auctionStartPrice, _data, i);
    i += Borsh.writeOptional(auctionEndPrice, _data, i);
    i += Borsh.writeOptionalbyte(policy, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (direction == null ? 1 : (1 + Borsh.len(direction)))
         + (baseAssetAmount == null || baseAssetAmount.isEmpty() ? 1 : (1 + 8))
         + (price == null || price.isEmpty() ? 1 : (1 + 8))
         + (reduceOnly == null ? 1 : (1 + 1))
         + (postOnly == null ? 1 : (1 + Borsh.len(postOnly)))
         + (immediateOrCancel == null ? 1 : (1 + 1))
         + (maxTs == null || maxTs.isEmpty() ? 1 : (1 + 8))
         + (triggerPrice == null || triggerPrice.isEmpty() ? 1 : (1 + 8))
         + (triggerCondition == null ? 1 : (1 + Borsh.len(triggerCondition)))
         + (oraclePriceOffset == null || oraclePriceOffset.isEmpty() ? 1 : (1 + 4))
         + (auctionDuration == null || auctionDuration.isEmpty() ? 1 : (1 + 1))
         + (auctionStartPrice == null || auctionStartPrice.isEmpty() ? 1 : (1 + 8))
         + (auctionEndPrice == null || auctionEndPrice.isEmpty() ? 1 : (1 + 8))
         + (policy == null || policy.isEmpty() ? 1 : (1 + 1));
  }
}
