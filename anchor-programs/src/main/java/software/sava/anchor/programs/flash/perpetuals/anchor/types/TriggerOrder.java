package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TriggerOrder(OraclePrice triggerPrice,
                           long triggerSize,
                           int receiveCustodyUid) implements Borsh {

  public static final int BYTES = 21;

  public static TriggerOrder read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var triggerPrice = OraclePrice.read(_data, i);
    i += Borsh.len(triggerPrice);
    final var triggerSize = getInt64LE(_data, i);
    i += 8;
    final var receiveCustodyUid = _data[i] & 0xFF;
    return new TriggerOrder(triggerPrice, triggerSize, receiveCustodyUid);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(triggerPrice, _data, i);
    putInt64LE(_data, i, triggerSize);
    i += 8;
    _data[i] = (byte) receiveCustodyUid;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
