package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SettleRebatesLog(String poolName, byte[] _poolName,
                               long rebateUsd,
                               long rebateAmount,
                               long[] padding) implements Borsh {

  public static final int PADDING_LEN = 2;
  public static SettleRebatesLog createRecord(final String poolName,
                                              final long rebateUsd,
                                              final long rebateAmount,
                                              final long[] padding) {
    return new SettleRebatesLog(poolName, poolName.getBytes(UTF_8),
                                rebateUsd,
                                rebateAmount,
                                padding);
  }

  public static SettleRebatesLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var rebateUsd = getInt64LE(_data, i);
    i += 8;
    final var rebateAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[2];
    Borsh.readArray(padding, _data, i);
    return new SettleRebatesLog(poolName, poolName.getBytes(UTF_8),
                                rebateUsd,
                                rebateAmount,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    putInt64LE(_data, i, rebateUsd);
    i += 8;
    putInt64LE(_data, i, rebateAmount);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 2, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName) + 8 + 8 + Borsh.lenArray(padding);
  }
}
