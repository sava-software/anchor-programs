package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record VaultRewardsSchedule(PublicKey rewardMint,
                                   PodDecimal totalWeightedStakeSupply,
                                   PodU64 rewardStartTime,
                                   PodU64 rewardEndTime,
                                   PodU64 totalEmissionsAmount,
                                   PodDecimal emissionsPerSecond,
                                   PodDecimal rewardIndex,
                                   PodU64 lastRewardIndexUpdateTime,
                                   PodU64 emissionsClaimed,
                                   PodU64 createdAt,
                                   PodU32CBPS[] durationStakeWeights) implements Borsh {

  public static final int BYTES = 172;
  public static final int DURATION_STAKE_WEIGHTS_LEN = 5;

  public static VaultRewardsSchedule read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var rewardMint = readPubKey(_data, i);
    i += 32;
    final var totalWeightedStakeSupply = PodDecimal.read(_data, i);
    i += Borsh.len(totalWeightedStakeSupply);
    final var rewardStartTime = PodU64.read(_data, i);
    i += Borsh.len(rewardStartTime);
    final var rewardEndTime = PodU64.read(_data, i);
    i += Borsh.len(rewardEndTime);
    final var totalEmissionsAmount = PodU64.read(_data, i);
    i += Borsh.len(totalEmissionsAmount);
    final var emissionsPerSecond = PodDecimal.read(_data, i);
    i += Borsh.len(emissionsPerSecond);
    final var rewardIndex = PodDecimal.read(_data, i);
    i += Borsh.len(rewardIndex);
    final var lastRewardIndexUpdateTime = PodU64.read(_data, i);
    i += Borsh.len(lastRewardIndexUpdateTime);
    final var emissionsClaimed = PodU64.read(_data, i);
    i += Borsh.len(emissionsClaimed);
    final var createdAt = PodU64.read(_data, i);
    i += Borsh.len(createdAt);
    final var durationStakeWeights = new PodU32CBPS[5];
    Borsh.readArray(durationStakeWeights, PodU32CBPS::read, _data, i);
    return new VaultRewardsSchedule(rewardMint,
                                    totalWeightedStakeSupply,
                                    rewardStartTime,
                                    rewardEndTime,
                                    totalEmissionsAmount,
                                    emissionsPerSecond,
                                    rewardIndex,
                                    lastRewardIndexUpdateTime,
                                    emissionsClaimed,
                                    createdAt,
                                    durationStakeWeights);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    rewardMint.write(_data, i);
    i += 32;
    i += Borsh.write(totalWeightedStakeSupply, _data, i);
    i += Borsh.write(rewardStartTime, _data, i);
    i += Borsh.write(rewardEndTime, _data, i);
    i += Borsh.write(totalEmissionsAmount, _data, i);
    i += Borsh.write(emissionsPerSecond, _data, i);
    i += Borsh.write(rewardIndex, _data, i);
    i += Borsh.write(lastRewardIndexUpdateTime, _data, i);
    i += Borsh.write(emissionsClaimed, _data, i);
    i += Borsh.write(createdAt, _data, i);
    i += Borsh.writeArray(durationStakeWeights, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
