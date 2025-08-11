package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CollectStakeRewardLog(PublicKey owner,
                                    String poolName, byte[] _poolName,
                                    long rewardAmount,
                                    PublicKey rewardMint) implements Borsh {

  public static CollectStakeRewardLog createRecord(final PublicKey owner,
                                                   final String poolName,
                                                   final long rewardAmount,
                                                   final PublicKey rewardMint) {
    return new CollectStakeRewardLog(owner,
                                     poolName, poolName.getBytes(UTF_8),
                                     rewardAmount,
                                     rewardMint);
  }

  public static CollectStakeRewardLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var poolName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var rewardAmount = getInt64LE(_data, i);
    i += 8;
    final var rewardMint = readPubKey(_data, i);
    return new CollectStakeRewardLog(owner,
                                     poolName, poolName.getBytes(UTF_8),
                                     rewardAmount,
                                     rewardMint);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_poolName, _data, i);
    putInt64LE(_data, i, rewardAmount);
    i += 8;
    rewardMint.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(_poolName) + 8 + 32;
  }
}
