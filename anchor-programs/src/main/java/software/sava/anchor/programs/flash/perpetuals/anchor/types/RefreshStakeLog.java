package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RefreshStakeLog(String poolName, byte[] _poolName, long rewardPerLpStaked) implements Borsh {

  public static RefreshStakeLog createRecord(final String poolName, final long rewardPerLpStaked) {
    return new RefreshStakeLog(poolName, poolName.getBytes(UTF_8), rewardPerLpStaked);
  }

  public static RefreshStakeLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var rewardPerLpStaked = getInt64LE(_data, i);
    return new RefreshStakeLog(poolName, poolName.getBytes(UTF_8), rewardPerLpStaked);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_poolName, _data, i);
    putInt64LE(_data, i, rewardPerLpStaked);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_poolName) + 8;
  }
}
