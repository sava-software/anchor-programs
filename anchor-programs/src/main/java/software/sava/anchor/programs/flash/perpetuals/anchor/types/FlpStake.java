package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record FlpStake(PublicKey _address,
                       Discriminator discriminator,
                       PublicKey owner,
                       PublicKey pool,
                       StakeStats stakeStats,
                       BigInteger rewardSnapshot,
                       long unclaimedRewards,
                       long feeShareBps,
                       int isInitialized,
                       int bump) implements Borsh {

  public static final int BYTES = 138;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int OWNER_OFFSET = 8;
  public static final int POOL_OFFSET = 40;
  public static final int STAKE_STATS_OFFSET = 72;
  public static final int REWARD_SNAPSHOT_OFFSET = 104;
  public static final int UNCLAIMED_REWARDS_OFFSET = 120;
  public static final int FEE_SHARE_BPS_OFFSET = 128;
  public static final int IS_INITIALIZED_OFFSET = 136;
  public static final int BUMP_OFFSET = 137;

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createStakeStatsFilter(final StakeStats stakeStats) {
    return Filter.createMemCompFilter(STAKE_STATS_OFFSET, stakeStats.write());
  }

  public static Filter createRewardSnapshotFilter(final BigInteger rewardSnapshot) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, rewardSnapshot);
    return Filter.createMemCompFilter(REWARD_SNAPSHOT_OFFSET, _data);
  }

  public static Filter createUnclaimedRewardsFilter(final long unclaimedRewards) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unclaimedRewards);
    return Filter.createMemCompFilter(UNCLAIMED_REWARDS_OFFSET, _data);
  }

  public static Filter createFeeShareBpsFilter(final long feeShareBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeShareBps);
    return Filter.createMemCompFilter(FEE_SHARE_BPS_OFFSET, _data);
  }

  public static Filter createIsInitializedFilter(final int isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) isInitialized});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static FlpStake read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FlpStake read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FlpStake read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FlpStake> FACTORY = FlpStake::read;

  public static FlpStake read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var owner = readPubKey(_data, i);
    i += 32;
    final var pool = readPubKey(_data, i);
    i += 32;
    final var stakeStats = StakeStats.read(_data, i);
    i += Borsh.len(stakeStats);
    final var rewardSnapshot = getInt128LE(_data, i);
    i += 16;
    final var unclaimedRewards = getInt64LE(_data, i);
    i += 8;
    final var feeShareBps = getInt64LE(_data, i);
    i += 8;
    final var isInitialized = _data[i] & 0xFF;
    ++i;
    final var bump = _data[i] & 0xFF;
    return new FlpStake(_address,
                        discriminator,
                        owner,
                        pool,
                        stakeStats,
                        rewardSnapshot,
                        unclaimedRewards,
                        feeShareBps,
                        isInitialized,
                        bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    owner.write(_data, i);
    i += 32;
    pool.write(_data, i);
    i += 32;
    i += Borsh.write(stakeStats, _data, i);
    putInt128LE(_data, i, rewardSnapshot);
    i += 16;
    putInt64LE(_data, i, unclaimedRewards);
    i += 8;
    putInt64LE(_data, i, feeShareBps);
    i += 8;
    _data[i] = (byte) isInitialized;
    ++i;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
