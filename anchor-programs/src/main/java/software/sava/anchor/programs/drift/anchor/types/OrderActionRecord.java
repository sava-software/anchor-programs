package software.sava.anchor.programs.drift.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record OrderActionRecord(long ts,
                                OrderAction action,
                                OrderActionExplanation actionExplanation,
                                int marketIndex,
                                MarketType marketType,
                                PublicKey filler,
                                OptionalLong fillerReward,
                                OptionalLong fillRecordId,
                                OptionalLong baseAssetAmountFilled,
                                OptionalLong quoteAssetAmountFilled,
                                OptionalLong takerFee,
                                OptionalLong makerFee,
                                OptionalInt referrerReward,
                                OptionalLong quoteAssetAmountSurplus,
                                OptionalLong spotFulfillmentMethodFee,
                                PublicKey taker,
                                OptionalInt takerOrderId,
                                PositionDirection takerOrderDirection,
                                OptionalLong takerOrderBaseAssetAmount,
                                OptionalLong takerOrderCumulativeBaseAssetAmountFilled,
                                OptionalLong takerOrderCumulativeQuoteAssetAmountFilled,
                                PublicKey maker,
                                OptionalInt makerOrderId,
                                PositionDirection makerOrderDirection,
                                OptionalLong makerOrderBaseAssetAmount,
                                OptionalLong makerOrderCumulativeBaseAssetAmountFilled,
                                OptionalLong makerOrderCumulativeQuoteAssetAmountFilled,
                                long oraclePrice,
                                int bitFlags,
                                OptionalLong takerExistingQuoteEntryAmount,
                                OptionalLong takerExistingBaseAssetAmount,
                                OptionalLong makerExistingQuoteEntryAmount,
                                OptionalLong makerExistingBaseAssetAmount,
                                OptionalLong triggerPrice,
                                OptionalInt builderIdx,
                                OptionalLong builderFee) implements Borsh {

  public static OrderActionRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var action = OrderAction.read(_data, i);
    i += Borsh.len(action);
    final var actionExplanation = OrderActionExplanation.read(_data, i);
    i += Borsh.len(actionExplanation);
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var filler = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (filler != null) {
      i += 32;
    }
    final var fillerReward = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (fillerReward.isPresent()) {
      i += 8;
    }
    final var fillRecordId = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (fillRecordId.isPresent()) {
      i += 8;
    }
    final var baseAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (baseAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var quoteAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (quoteAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var takerFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerFee.isPresent()) {
      i += 8;
    }
    final var makerFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerFee.isPresent()) {
      i += 8;
    }
    final var referrerReward = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (referrerReward.isPresent()) {
      i += 4;
    }
    final var quoteAssetAmountSurplus = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (quoteAssetAmountSurplus.isPresent()) {
      i += 8;
    }
    final var spotFulfillmentMethodFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (spotFulfillmentMethodFee.isPresent()) {
      i += 8;
    }
    final var taker = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (taker != null) {
      i += 32;
    }
    final var takerOrderId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (takerOrderId.isPresent()) {
      i += 4;
    }
    final var takerOrderDirection = _data[i++] == 0 ? null : PositionDirection.read(_data, i);
    if (takerOrderDirection != null) {
      i += Borsh.len(takerOrderDirection);
    }
    final var takerOrderBaseAssetAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerOrderBaseAssetAmount.isPresent()) {
      i += 8;
    }
    final var takerOrderCumulativeBaseAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerOrderCumulativeBaseAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var takerOrderCumulativeQuoteAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerOrderCumulativeQuoteAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var maker = _data[i++] == 0 ? null : readPubKey(_data, i);
    if (maker != null) {
      i += 32;
    }
    final var makerOrderId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (makerOrderId.isPresent()) {
      i += 4;
    }
    final var makerOrderDirection = _data[i++] == 0 ? null : PositionDirection.read(_data, i);
    if (makerOrderDirection != null) {
      i += Borsh.len(makerOrderDirection);
    }
    final var makerOrderBaseAssetAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerOrderBaseAssetAmount.isPresent()) {
      i += 8;
    }
    final var makerOrderCumulativeBaseAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerOrderCumulativeBaseAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var makerOrderCumulativeQuoteAssetAmountFilled = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerOrderCumulativeQuoteAssetAmountFilled.isPresent()) {
      i += 8;
    }
    final var oraclePrice = getInt64LE(_data, i);
    i += 8;
    final var bitFlags = _data[i] & 0xFF;
    ++i;
    final var takerExistingQuoteEntryAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerExistingQuoteEntryAmount.isPresent()) {
      i += 8;
    }
    final var takerExistingBaseAssetAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (takerExistingBaseAssetAmount.isPresent()) {
      i += 8;
    }
    final var makerExistingQuoteEntryAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerExistingQuoteEntryAmount.isPresent()) {
      i += 8;
    }
    final var makerExistingBaseAssetAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (makerExistingBaseAssetAmount.isPresent()) {
      i += 8;
    }
    final var triggerPrice = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (triggerPrice.isPresent()) {
      i += 8;
    }
    final var builderIdx = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (builderIdx.isPresent()) {
      ++i;
    }
    final var builderFee = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new OrderActionRecord(ts,
                                 action,
                                 actionExplanation,
                                 marketIndex,
                                 marketType,
                                 filler,
                                 fillerReward,
                                 fillRecordId,
                                 baseAssetAmountFilled,
                                 quoteAssetAmountFilled,
                                 takerFee,
                                 makerFee,
                                 referrerReward,
                                 quoteAssetAmountSurplus,
                                 spotFulfillmentMethodFee,
                                 taker,
                                 takerOrderId,
                                 takerOrderDirection,
                                 takerOrderBaseAssetAmount,
                                 takerOrderCumulativeBaseAssetAmountFilled,
                                 takerOrderCumulativeQuoteAssetAmountFilled,
                                 maker,
                                 makerOrderId,
                                 makerOrderDirection,
                                 makerOrderBaseAssetAmount,
                                 makerOrderCumulativeBaseAssetAmountFilled,
                                 makerOrderCumulativeQuoteAssetAmountFilled,
                                 oraclePrice,
                                 bitFlags,
                                 takerExistingQuoteEntryAmount,
                                 takerExistingBaseAssetAmount,
                                 makerExistingQuoteEntryAmount,
                                 makerExistingBaseAssetAmount,
                                 triggerPrice,
                                 builderIdx,
                                 builderFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    i += Borsh.write(action, _data, i);
    i += Borsh.write(actionExplanation, _data, i);
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(marketType, _data, i);
    i += Borsh.writeOptional(filler, _data, i);
    i += Borsh.writeOptional(fillerReward, _data, i);
    i += Borsh.writeOptional(fillRecordId, _data, i);
    i += Borsh.writeOptional(baseAssetAmountFilled, _data, i);
    i += Borsh.writeOptional(quoteAssetAmountFilled, _data, i);
    i += Borsh.writeOptional(takerFee, _data, i);
    i += Borsh.writeOptional(makerFee, _data, i);
    i += Borsh.writeOptional(referrerReward, _data, i);
    i += Borsh.writeOptional(quoteAssetAmountSurplus, _data, i);
    i += Borsh.writeOptional(spotFulfillmentMethodFee, _data, i);
    i += Borsh.writeOptional(taker, _data, i);
    i += Borsh.writeOptional(takerOrderId, _data, i);
    i += Borsh.writeOptional(takerOrderDirection, _data, i);
    i += Borsh.writeOptional(takerOrderBaseAssetAmount, _data, i);
    i += Borsh.writeOptional(takerOrderCumulativeBaseAssetAmountFilled, _data, i);
    i += Borsh.writeOptional(takerOrderCumulativeQuoteAssetAmountFilled, _data, i);
    i += Borsh.writeOptional(maker, _data, i);
    i += Borsh.writeOptional(makerOrderId, _data, i);
    i += Borsh.writeOptional(makerOrderDirection, _data, i);
    i += Borsh.writeOptional(makerOrderBaseAssetAmount, _data, i);
    i += Borsh.writeOptional(makerOrderCumulativeBaseAssetAmountFilled, _data, i);
    i += Borsh.writeOptional(makerOrderCumulativeQuoteAssetAmountFilled, _data, i);
    putInt64LE(_data, i, oraclePrice);
    i += 8;
    _data[i] = (byte) bitFlags;
    ++i;
    i += Borsh.writeOptional(takerExistingQuoteEntryAmount, _data, i);
    i += Borsh.writeOptional(takerExistingBaseAssetAmount, _data, i);
    i += Borsh.writeOptional(makerExistingQuoteEntryAmount, _data, i);
    i += Borsh.writeOptional(makerExistingBaseAssetAmount, _data, i);
    i += Borsh.writeOptional(triggerPrice, _data, i);
    i += Borsh.writeOptionalbyte(builderIdx, _data, i);
    i += Borsh.writeOptional(builderFee, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + Borsh.len(action)
         + Borsh.len(actionExplanation)
         + 2
         + Borsh.len(marketType)
         + (filler == null ? 1 : (1 + 32))
         + (fillerReward == null || fillerReward.isEmpty() ? 1 : (1 + 8))
         + (fillRecordId == null || fillRecordId.isEmpty() ? 1 : (1 + 8))
         + (baseAssetAmountFilled == null || baseAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + (quoteAssetAmountFilled == null || quoteAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + (takerFee == null || takerFee.isEmpty() ? 1 : (1 + 8))
         + (makerFee == null || makerFee.isEmpty() ? 1 : (1 + 8))
         + (referrerReward == null || referrerReward.isEmpty() ? 1 : (1 + 4))
         + (quoteAssetAmountSurplus == null || quoteAssetAmountSurplus.isEmpty() ? 1 : (1 + 8))
         + (spotFulfillmentMethodFee == null || spotFulfillmentMethodFee.isEmpty() ? 1 : (1 + 8))
         + (taker == null ? 1 : (1 + 32))
         + (takerOrderId == null || takerOrderId.isEmpty() ? 1 : (1 + 4))
         + (takerOrderDirection == null ? 1 : (1 + Borsh.len(takerOrderDirection)))
         + (takerOrderBaseAssetAmount == null || takerOrderBaseAssetAmount.isEmpty() ? 1 : (1 + 8))
         + (takerOrderCumulativeBaseAssetAmountFilled == null || takerOrderCumulativeBaseAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + (takerOrderCumulativeQuoteAssetAmountFilled == null || takerOrderCumulativeQuoteAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + (maker == null ? 1 : (1 + 32))
         + (makerOrderId == null || makerOrderId.isEmpty() ? 1 : (1 + 4))
         + (makerOrderDirection == null ? 1 : (1 + Borsh.len(makerOrderDirection)))
         + (makerOrderBaseAssetAmount == null || makerOrderBaseAssetAmount.isEmpty() ? 1 : (1 + 8))
         + (makerOrderCumulativeBaseAssetAmountFilled == null || makerOrderCumulativeBaseAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + (makerOrderCumulativeQuoteAssetAmountFilled == null || makerOrderCumulativeQuoteAssetAmountFilled.isEmpty() ? 1 : (1 + 8))
         + 8
         + 1
         + (takerExistingQuoteEntryAmount == null || takerExistingQuoteEntryAmount.isEmpty() ? 1 : (1 + 8))
         + (takerExistingBaseAssetAmount == null || takerExistingBaseAssetAmount.isEmpty() ? 1 : (1 + 8))
         + (makerExistingQuoteEntryAmount == null || makerExistingQuoteEntryAmount.isEmpty() ? 1 : (1 + 8))
         + (makerExistingBaseAssetAmount == null || makerExistingBaseAssetAmount.isEmpty() ? 1 : (1 + 8))
         + (triggerPrice == null || triggerPrice.isEmpty() ? 1 : (1 + 8))
         + (builderIdx == null || builderIdx.isEmpty() ? 1 : (1 + 1))
         + (builderFee == null || builderFee.isEmpty() ? 1 : (1 + 8));
  }
}
