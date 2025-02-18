package software.sava.anchor.programs.drift.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SignedMsgOrderRecord(PublicKey user,
                                   String hash, byte[] _hash,
                                   OrderParams matchingOrderParams,
                                   int userOrderId,
                                   long signedMsgOrderMaxSlot,
                                   byte[] signedMsgOrderUuid,
                                   long ts) implements Borsh {

  public static SignedMsgOrderRecord createRecord(final PublicKey user,
                                                  final String hash,
                                                  final OrderParams matchingOrderParams,
                                                  final int userOrderId,
                                                  final long signedMsgOrderMaxSlot,
                                                  final byte[] signedMsgOrderUuid,
                                                  final long ts) {
    return new SignedMsgOrderRecord(user,
                                    hash, hash.getBytes(UTF_8),
                                    matchingOrderParams,
                                    userOrderId,
                                    signedMsgOrderMaxSlot,
                                    signedMsgOrderUuid,
                                    ts);
  }

  public static SignedMsgOrderRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var user = readPubKey(_data, i);
    i += 32;
    final var hash = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var matchingOrderParams = OrderParams.read(_data, i);
    i += Borsh.len(matchingOrderParams);
    final var userOrderId = getInt32LE(_data, i);
    i += 4;
    final var signedMsgOrderMaxSlot = getInt64LE(_data, i);
    i += 8;
    final var signedMsgOrderUuid = new byte[8];
    i += Borsh.readArray(signedMsgOrderUuid, _data, i);
    final var ts = getInt64LE(_data, i);
    return new SignedMsgOrderRecord(user,
                                    hash, hash.getBytes(UTF_8),
                                    matchingOrderParams,
                                    userOrderId,
                                    signedMsgOrderMaxSlot,
                                    signedMsgOrderUuid,
                                    ts);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    user.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_hash, _data, i);
    i += Borsh.write(matchingOrderParams, _data, i);
    putInt32LE(_data, i, userOrderId);
    i += 4;
    putInt64LE(_data, i, signedMsgOrderMaxSlot);
    i += 8;
    i += Borsh.writeArray(signedMsgOrderUuid, _data, i);
    putInt64LE(_data, i, ts);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + Borsh.lenVector(_hash)
         + Borsh.len(matchingOrderParams)
         + 4
         + 8
         + Borsh.lenArray(signedMsgOrderUuid)
         + 8;
  }
}
