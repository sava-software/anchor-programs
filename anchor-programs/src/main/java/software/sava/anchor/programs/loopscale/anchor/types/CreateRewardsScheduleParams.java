package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record CreateRewardsScheduleParams(PodDecimal totalWeightedStakeSupply,
                                          PodU64 rewardStartTime,
                                          PodU64 rewardEndTime,
                                          PodU32CBPS[] durationStakeWeights) implements Borsh {

  public static final int BYTES = 60;
  public static final int DURATION_STAKE_WEIGHTS_LEN = 5;

  public static CreateRewardsScheduleParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var totalWeightedStakeSupply = PodDecimal.read(_data, i);
    i += Borsh.len(totalWeightedStakeSupply);
    final var rewardStartTime = PodU64.read(_data, i);
    i += Borsh.len(rewardStartTime);
    final var rewardEndTime = PodU64.read(_data, i);
    i += Borsh.len(rewardEndTime);
    final var durationStakeWeights = new PodU32CBPS[5];
    Borsh.readArray(durationStakeWeights, PodU32CBPS::read, _data, i);
    return new CreateRewardsScheduleParams(totalWeightedStakeSupply,
                                           rewardStartTime,
                                           rewardEndTime,
                                           durationStakeWeights);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(totalWeightedStakeSupply, _data, i);
    i += Borsh.write(rewardStartTime, _data, i);
    i += Borsh.write(rewardEndTime, _data, i);
    i += Borsh.writeArray(durationStakeWeights, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
