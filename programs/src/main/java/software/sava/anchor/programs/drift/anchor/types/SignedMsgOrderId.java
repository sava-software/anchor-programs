package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SignedMsgOrderId(byte[] uuid,
                               long maxSlot,
                               int orderId,
                               int padding) implements Borsh {

  public static final int BYTES = 24;

  public static SignedMsgOrderId read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var uuid = new byte[8];
    i += Borsh.readArray(uuid, _data, i);
    final var maxSlot = getInt64LE(_data, i);
    i += 8;
    final var orderId = getInt32LE(_data, i);
    i += 4;
    final var padding = getInt32LE(_data, i);
    return new SignedMsgOrderId(uuid,
                                maxSlot,
                                orderId,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(uuid, _data, i);
    putInt64LE(_data, i, maxSlot);
    i += 8;
    putInt32LE(_data, i, orderId);
    i += 4;
    putInt32LE(_data, i, padding);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
