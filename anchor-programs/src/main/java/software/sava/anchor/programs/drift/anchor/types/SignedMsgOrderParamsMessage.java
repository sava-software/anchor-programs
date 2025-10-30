package software.sava.anchor.programs.drift.anchor.types;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SignedMsgOrderParamsMessage(OrderParams signedMsgOrderParams,
                                          int subAccountId,
                                          long slot,
                                          byte[] uuid,
                                          SignedMsgTriggerOrderParams takeProfitOrderParams,
                                          SignedMsgTriggerOrderParams stopLossOrderParams,
                                          OptionalInt maxMarginRatio,
                                          OptionalInt builderIdx,
                                          OptionalInt builderFeeTenthBps,
                                          OptionalLong isolatedPositionDeposit) implements Borsh {

  public static final int UUID_LEN = 8;
  public static SignedMsgOrderParamsMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signedMsgOrderParams = OrderParams.read(_data, i);
    i += Borsh.len(signedMsgOrderParams);
    final var subAccountId = getInt16LE(_data, i);
    i += 2;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var uuid = new byte[8];
    i += Borsh.readArray(uuid, _data, i);
    final var takeProfitOrderParams = _data[i++] == 0 ? null : SignedMsgTriggerOrderParams.read(_data, i);
    if (takeProfitOrderParams != null) {
      i += Borsh.len(takeProfitOrderParams);
    }
    final var stopLossOrderParams = _data[i++] == 0 ? null : SignedMsgTriggerOrderParams.read(_data, i);
    if (stopLossOrderParams != null) {
      i += Borsh.len(stopLossOrderParams);
    }
    final var maxMarginRatio = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (maxMarginRatio.isPresent()) {
      i += 2;
    }
    final var builderIdx = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (builderIdx.isPresent()) {
      ++i;
    }
    final var builderFeeTenthBps = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt16LE(_data, i));
    if (builderFeeTenthBps.isPresent()) {
      i += 2;
    }
    final var isolatedPositionDeposit = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    return new SignedMsgOrderParamsMessage(signedMsgOrderParams,
                                           subAccountId,
                                           slot,
                                           uuid,
                                           takeProfitOrderParams,
                                           stopLossOrderParams,
                                           maxMarginRatio,
                                           builderIdx,
                                           builderFeeTenthBps,
                                           isolatedPositionDeposit);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(signedMsgOrderParams, _data, i);
    putInt16LE(_data, i, subAccountId);
    i += 2;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeArrayChecked(uuid, 8, _data, i);
    i += Borsh.writeOptional(takeProfitOrderParams, _data, i);
    i += Borsh.writeOptional(stopLossOrderParams, _data, i);
    i += Borsh.writeOptionalshort(maxMarginRatio, _data, i);
    i += Borsh.writeOptionalbyte(builderIdx, _data, i);
    i += Borsh.writeOptionalshort(builderFeeTenthBps, _data, i);
    i += Borsh.writeOptional(isolatedPositionDeposit, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(signedMsgOrderParams)
         + 2
         + 8
         + Borsh.lenArray(uuid)
         + (takeProfitOrderParams == null ? 1 : (1 + Borsh.len(takeProfitOrderParams)))
         + (stopLossOrderParams == null ? 1 : (1 + Borsh.len(stopLossOrderParams)))
         + (maxMarginRatio == null || maxMarginRatio.isEmpty() ? 1 : (1 + 2))
         + (builderIdx == null || builderIdx.isEmpty() ? 1 : (1 + 1))
         + (builderFeeTenthBps == null || builderFeeTenthBps.isEmpty() ? 1 : (1 + 2))
         + (isolatedPositionDeposit == null || isolatedPositionDeposit.isEmpty() ? 1 : (1 + 8));
  }
}
