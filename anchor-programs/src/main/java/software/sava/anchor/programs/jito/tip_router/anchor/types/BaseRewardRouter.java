package software.sava.anchor.programs.jito.tip_router.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record BaseRewardRouter(PublicKey _address,
                               Discriminator discriminator,
                               PublicKey ncn,
                               long epoch,
                               int bump,
                               long slotCreated,
                               long totalRewards,
                               long rewardPool,
                               long rewardsProcessed,
                               byte[] reserved,
                               int lastNcnGroupIndex,
                               int lastVoteIndex,
                               long lastRewardsToProcess,
                               BaseRewardRouterRewards[] baseFeeGroupRewards,
                               BaseRewardRouterRewards[] ncnFeeGroupRewards,
                               NcnRewardRoute[] ncnFeeGroupRewardRoutes) implements Borsh {

  public static final int BYTES = 24924;
  public static final int RESERVED_LEN = 128;
  public static final int BASE_FEE_GROUP_REWARDS_LEN = 8;
  public static final int NCN_FEE_GROUP_REWARDS_LEN = 8;
  public static final int NCN_FEE_GROUP_REWARD_ROUTES_LEN = 256;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_OFFSET = 8;
  public static final int EPOCH_OFFSET = 40;
  public static final int BUMP_OFFSET = 48;
  public static final int SLOT_CREATED_OFFSET = 49;
  public static final int TOTAL_REWARDS_OFFSET = 57;
  public static final int REWARD_POOL_OFFSET = 65;
  public static final int REWARDS_PROCESSED_OFFSET = 73;
  public static final int RESERVED_OFFSET = 81;
  public static final int LAST_NCN_GROUP_INDEX_OFFSET = 209;
  public static final int LAST_VOTE_INDEX_OFFSET = 210;
  public static final int LAST_REWARDS_TO_PROCESS_OFFSET = 212;
  public static final int BASE_FEE_GROUP_REWARDS_OFFSET = 220;
  public static final int NCN_FEE_GROUP_REWARDS_OFFSET = 284;
  public static final int NCN_FEE_GROUP_REWARD_ROUTES_OFFSET = 348;

  public static Filter createNcnFilter(final PublicKey ncn) {
    return Filter.createMemCompFilter(NCN_OFFSET, ncn);
  }

  public static Filter createEpochFilter(final long epoch) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, epoch);
    return Filter.createMemCompFilter(EPOCH_OFFSET, _data);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createSlotCreatedFilter(final long slotCreated) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slotCreated);
    return Filter.createMemCompFilter(SLOT_CREATED_OFFSET, _data);
  }

  public static Filter createTotalRewardsFilter(final long totalRewards) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalRewards);
    return Filter.createMemCompFilter(TOTAL_REWARDS_OFFSET, _data);
  }

  public static Filter createRewardPoolFilter(final long rewardPool) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rewardPool);
    return Filter.createMemCompFilter(REWARD_POOL_OFFSET, _data);
  }

  public static Filter createRewardsProcessedFilter(final long rewardsProcessed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rewardsProcessed);
    return Filter.createMemCompFilter(REWARDS_PROCESSED_OFFSET, _data);
  }

  public static Filter createLastNcnGroupIndexFilter(final int lastNcnGroupIndex) {
    return Filter.createMemCompFilter(LAST_NCN_GROUP_INDEX_OFFSET, new byte[]{(byte) lastNcnGroupIndex});
  }

  public static Filter createLastVoteIndexFilter(final int lastVoteIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, lastVoteIndex);
    return Filter.createMemCompFilter(LAST_VOTE_INDEX_OFFSET, _data);
  }

  public static Filter createLastRewardsToProcessFilter(final long lastRewardsToProcess) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastRewardsToProcess);
    return Filter.createMemCompFilter(LAST_REWARDS_TO_PROCESS_OFFSET, _data);
  }

  public static BaseRewardRouter read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static BaseRewardRouter read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static BaseRewardRouter read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], BaseRewardRouter> FACTORY = BaseRewardRouter::read;

  public static BaseRewardRouter read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var totalRewards = getInt64LE(_data, i);
    i += 8;
    final var rewardPool = getInt64LE(_data, i);
    i += 8;
    final var rewardsProcessed = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[128];
    i += Borsh.readArray(reserved, _data, i);
    final var lastNcnGroupIndex = _data[i] & 0xFF;
    ++i;
    final var lastVoteIndex = getInt16LE(_data, i);
    i += 2;
    final var lastRewardsToProcess = getInt64LE(_data, i);
    i += 8;
    final var baseFeeGroupRewards = new BaseRewardRouterRewards[8];
    i += Borsh.readArray(baseFeeGroupRewards, BaseRewardRouterRewards::read, _data, i);
    final var ncnFeeGroupRewards = new BaseRewardRouterRewards[8];
    i += Borsh.readArray(ncnFeeGroupRewards, BaseRewardRouterRewards::read, _data, i);
    final var ncnFeeGroupRewardRoutes = new NcnRewardRoute[256];
    Borsh.readArray(ncnFeeGroupRewardRoutes, NcnRewardRoute::read, _data, i);
    return new BaseRewardRouter(_address,
                                discriminator,
                                ncn,
                                epoch,
                                bump,
                                slotCreated,
                                totalRewards,
                                rewardPool,
                                rewardsProcessed,
                                reserved,
                                lastNcnGroupIndex,
                                lastVoteIndex,
                                lastRewardsToProcess,
                                baseFeeGroupRewards,
                                ncnFeeGroupRewards,
                                ncnFeeGroupRewardRoutes);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, totalRewards);
    i += 8;
    putInt64LE(_data, i, rewardPool);
    i += 8;
    putInt64LE(_data, i, rewardsProcessed);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    _data[i] = (byte) lastNcnGroupIndex;
    ++i;
    putInt16LE(_data, i, lastVoteIndex);
    i += 2;
    putInt64LE(_data, i, lastRewardsToProcess);
    i += 8;
    i += Borsh.writeArray(baseFeeGroupRewards, _data, i);
    i += Borsh.writeArray(ncnFeeGroupRewards, _data, i);
    i += Borsh.writeArray(ncnFeeGroupRewardRoutes, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
