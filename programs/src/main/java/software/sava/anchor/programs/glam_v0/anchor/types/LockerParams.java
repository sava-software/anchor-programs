package software.sava.anchor.programs.glam_v0.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record LockerParams(int maxStakeVoteMultiplier,
                           long minStakeDuration,
                           long maxStakeDuration,
                           long proposalActivationMinVotes) implements Borsh {

  public static final int BYTES = 25;

  public static LockerParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxStakeVoteMultiplier = _data[i] & 0xFF;
    ++i;
    final var minStakeDuration = getInt64LE(_data, i);
    i += 8;
    final var maxStakeDuration = getInt64LE(_data, i);
    i += 8;
    final var proposalActivationMinVotes = getInt64LE(_data, i);
    return new LockerParams(maxStakeVoteMultiplier,
                            minStakeDuration,
                            maxStakeDuration,
                            proposalActivationMinVotes);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) maxStakeVoteMultiplier;
    ++i;
    putInt64LE(_data, i, minStakeDuration);
    i += 8;
    putInt64LE(_data, i, maxStakeDuration);
    i += 8;
    putInt64LE(_data, i, proposalActivationMinVotes);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
