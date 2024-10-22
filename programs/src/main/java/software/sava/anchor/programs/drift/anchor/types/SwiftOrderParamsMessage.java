package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SwiftOrderParamsMessage(OrderParams swiftOrderParams,
                                      int expectedOrderId,
                                      int subAccountId,
                                      SwiftTriggerOrderParams takeProfitOrderParams,
                                      SwiftTriggerOrderParams stopLossOrderParams) implements Borsh {

  public static SwiftOrderParamsMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var swiftOrderParams = OrderParams.read(_data, i);
    i += Borsh.len(swiftOrderParams);
    final var expectedOrderId = getInt32LE(_data, i);
    i += 4;
    final var subAccountId = getInt16LE(_data, i);
    i += 2;
    final var takeProfitOrderParams = _data[i++] == 0 ? null : SwiftTriggerOrderParams.read(_data, i);
    if (takeProfitOrderParams != null) {
      i += Borsh.len(takeProfitOrderParams);
    }
    final var stopLossOrderParams = _data[i++] == 0 ? null : SwiftTriggerOrderParams.read(_data, i);
    return new SwiftOrderParamsMessage(swiftOrderParams,
                                       expectedOrderId,
                                       subAccountId,
                                       takeProfitOrderParams,
                                       stopLossOrderParams);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(swiftOrderParams, _data, i);
    putInt32LE(_data, i, expectedOrderId);
    i += 4;
    putInt16LE(_data, i, subAccountId);
    i += 2;
    i += Borsh.writeOptional(takeProfitOrderParams, _data, i);
    i += Borsh.writeOptional(stopLossOrderParams, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(swiftOrderParams)
         + 4
         + 2
         + (takeProfitOrderParams == null ? 1 : (1 + Borsh.len(takeProfitOrderParams)))
         + (stopLossOrderParams == null ? 1 : (1 + Borsh.len(stopLossOrderParams)));
  }
}
