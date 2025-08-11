package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;

public record VoltageStats(BigInteger volumeUsd,
                           BigInteger lpRewardsUsd,
                           BigInteger referralRebateUsd) implements Borsh {

  public static final int BYTES = 48;

  public static VoltageStats read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var volumeUsd = getInt128LE(_data, i);
    i += 16;
    final var lpRewardsUsd = getInt128LE(_data, i);
    i += 16;
    final var referralRebateUsd = getInt128LE(_data, i);
    return new VoltageStats(volumeUsd, lpRewardsUsd, referralRebateUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, volumeUsd);
    i += 16;
    putInt128LE(_data, i, lpRewardsUsd);
    i += 16;
    putInt128LE(_data, i, referralRebateUsd);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
