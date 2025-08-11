package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BackupOracle(long price,
                           int expo,
                           long conf,
                           long emaPrice,
                           long publishTime) implements Borsh {

  public static final int BYTES = 36;

  public static BackupOracle read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var expo = getInt32LE(_data, i);
    i += 4;
    final var conf = getInt64LE(_data, i);
    i += 8;
    final var emaPrice = getInt64LE(_data, i);
    i += 8;
    final var publishTime = getInt64LE(_data, i);
    return new BackupOracle(price,
                            expo,
                            conf,
                            emaPrice,
                            publishTime);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, price);
    i += 8;
    putInt32LE(_data, i, expo);
    i += 4;
    putInt64LE(_data, i, conf);
    i += 8;
    putInt64LE(_data, i, emaPrice);
    i += 8;
    putInt64LE(_data, i, publishTime);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
