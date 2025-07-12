package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SignedMsgOrderParamsDelegateMessage(OrderParams signedMsgOrderParams,
                                                  PublicKey takerPubkey,
                                                  long slot,
                                                  byte[] uuid,
                                                  SignedMsgTriggerOrderParams takeProfitOrderParams,
                                                  SignedMsgTriggerOrderParams stopLossOrderParams) implements Borsh {

  public static final int UUID_LEN = 8;
  public static SignedMsgOrderParamsDelegateMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var signedMsgOrderParams = OrderParams.read(_data, i);
    i += Borsh.len(signedMsgOrderParams);
    final var takerPubkey = readPubKey(_data, i);
    i += 32;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var uuid = new byte[8];
    i += Borsh.readArray(uuid, _data, i);
    final var takeProfitOrderParams = _data[i++] == 0 ? null : SignedMsgTriggerOrderParams.read(_data, i);
    if (takeProfitOrderParams != null) {
      i += Borsh.len(takeProfitOrderParams);
    }
    final var stopLossOrderParams = _data[i++] == 0 ? null : SignedMsgTriggerOrderParams.read(_data, i);
    return new SignedMsgOrderParamsDelegateMessage(signedMsgOrderParams,
                                                   takerPubkey,
                                                   slot,
                                                   uuid,
                                                   takeProfitOrderParams,
                                                   stopLossOrderParams);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(signedMsgOrderParams, _data, i);
    takerPubkey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeArray(uuid, _data, i);
    i += Borsh.writeOptional(takeProfitOrderParams, _data, i);
    i += Borsh.writeOptional(stopLossOrderParams, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(signedMsgOrderParams)
         + 32
         + 8
         + Borsh.lenArray(uuid)
         + (takeProfitOrderParams == null ? 1 : (1 + Borsh.len(takeProfitOrderParams)))
         + (stopLossOrderParams == null ? 1 : (1 + Borsh.len(stopLossOrderParams)));
  }
}
