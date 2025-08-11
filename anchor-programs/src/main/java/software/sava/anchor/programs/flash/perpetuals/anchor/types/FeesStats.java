package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeesStats(BigInteger accrued,
                        BigInteger distributed,
                        BigInteger paid,
                        long rewardPerLpStaked,
                        long protocolFee) implements Borsh {

  public static final int BYTES = 64;

  public static FeesStats read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var accrued = getInt128LE(_data, i);
    i += 16;
    final var distributed = getInt128LE(_data, i);
    i += 16;
    final var paid = getInt128LE(_data, i);
    i += 16;
    final var rewardPerLpStaked = getInt64LE(_data, i);
    i += 8;
    final var protocolFee = getInt64LE(_data, i);
    return new FeesStats(accrued,
                         distributed,
                         paid,
                         rewardPerLpStaked,
                         protocolFee);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, accrued);
    i += 16;
    putInt128LE(_data, i, distributed);
    i += 16;
    putInt128LE(_data, i, paid);
    i += 16;
    putInt64LE(_data, i, rewardPerLpStaked);
    i += 8;
    putInt64LE(_data, i, protocolFee);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
