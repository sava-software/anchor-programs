package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CompoundingStats(long activeAmount,
                               long totalSupply,
                               BigInteger rewardSnapshot,
                               long feeShareBps,
                               long lastCompoundTime) implements Borsh {

  public static final int BYTES = 48;

  public static CompoundingStats read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activeAmount = getInt64LE(_data, i);
    i += 8;
    final var totalSupply = getInt64LE(_data, i);
    i += 8;
    final var rewardSnapshot = getInt128LE(_data, i);
    i += 16;
    final var feeShareBps = getInt64LE(_data, i);
    i += 8;
    final var lastCompoundTime = getInt64LE(_data, i);
    return new CompoundingStats(activeAmount,
                                totalSupply,
                                rewardSnapshot,
                                feeShareBps,
                                lastCompoundTime);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, activeAmount);
    i += 8;
    putInt64LE(_data, i, totalSupply);
    i += 8;
    putInt128LE(_data, i, rewardSnapshot);
    i += 16;
    putInt64LE(_data, i, feeShareBps);
    i += 8;
    putInt64LE(_data, i, lastCompoundTime);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
