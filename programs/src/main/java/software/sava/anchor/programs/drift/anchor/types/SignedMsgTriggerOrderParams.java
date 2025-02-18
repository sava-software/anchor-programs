package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SignedMsgTriggerOrderParams(long triggerPrice, long baseAssetAmount) implements Borsh {

  public static final int BYTES = 16;

  public static SignedMsgTriggerOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var triggerPrice = getInt64LE(_data, i);
    i += 8;
    final var baseAssetAmount = getInt64LE(_data, i);
    return new SignedMsgTriggerOrderParams(triggerPrice, baseAssetAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, triggerPrice);
    i += 8;
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
