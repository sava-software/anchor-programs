package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UpdateRewardDuration(PublicKey lbPair,
                                   long rewardIndex,
                                   long oldRewardDuration,
                                   long newRewardDuration) implements Borsh {

  public static final int BYTES = 56;

  public static UpdateRewardDuration read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var lbPair = readPubKey(_data, i);
    i += 32;
    final var rewardIndex = getInt64LE(_data, i);
    i += 8;
    final var oldRewardDuration = getInt64LE(_data, i);
    i += 8;
    final var newRewardDuration = getInt64LE(_data, i);
    return new UpdateRewardDuration(lbPair,
                                    rewardIndex,
                                    oldRewardDuration,
                                    newRewardDuration);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    lbPair.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewardIndex);
    i += 8;
    putInt64LE(_data, i, oldRewardDuration);
    i += 8;
    putInt64LE(_data, i, newRewardDuration);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
