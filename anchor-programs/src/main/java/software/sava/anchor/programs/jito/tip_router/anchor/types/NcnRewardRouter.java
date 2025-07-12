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

public record NcnRewardRouter(PublicKey _address,
                              Discriminator discriminator,
                              NcnFeeGroup ncnFeeGroup,
                              PublicKey operator,
                              PublicKey ncn,
                              long epoch,
                              int bump,
                              long slotCreated,
                              long ncnOperatorIndex,
                              long totalRewards,
                              long rewardPool,
                              long rewardsProcessed,
                              long operatorRewards,
                              byte[] reserved,
                              long lastRewardsToProcess,
                              int lastVaultOperatorDelegationIndex,
                              VaultRewardRoute[] vaultRewardRoutes) implements Borsh {

  public static final int BYTES = 2828;
  public static final int RESERVED_LEN = 128;
  public static final int VAULT_REWARD_ROUTES_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int NCN_FEE_GROUP_OFFSET = 8;
  public static final int OPERATOR_OFFSET = 9;
  public static final int NCN_OFFSET = 41;
  public static final int EPOCH_OFFSET = 73;
  public static final int BUMP_OFFSET = 81;
  public static final int SLOT_CREATED_OFFSET = 82;
  public static final int NCN_OPERATOR_INDEX_OFFSET = 90;
  public static final int TOTAL_REWARDS_OFFSET = 98;
  public static final int REWARD_POOL_OFFSET = 106;
  public static final int REWARDS_PROCESSED_OFFSET = 114;
  public static final int OPERATOR_REWARDS_OFFSET = 122;
  public static final int RESERVED_OFFSET = 130;
  public static final int LAST_REWARDS_TO_PROCESS_OFFSET = 258;
  public static final int LAST_VAULT_OPERATOR_DELEGATION_INDEX_OFFSET = 266;
  public static final int VAULT_REWARD_ROUTES_OFFSET = 268;

  public static Filter createNcnFeeGroupFilter(final NcnFeeGroup ncnFeeGroup) {
    return Filter.createMemCompFilter(NCN_FEE_GROUP_OFFSET, ncnFeeGroup.write());
  }

  public static Filter createOperatorFilter(final PublicKey operator) {
    return Filter.createMemCompFilter(OPERATOR_OFFSET, operator);
  }

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

  public static Filter createNcnOperatorIndexFilter(final long ncnOperatorIndex) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, ncnOperatorIndex);
    return Filter.createMemCompFilter(NCN_OPERATOR_INDEX_OFFSET, _data);
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

  public static Filter createOperatorRewardsFilter(final long operatorRewards) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, operatorRewards);
    return Filter.createMemCompFilter(OPERATOR_REWARDS_OFFSET, _data);
  }

  public static Filter createLastRewardsToProcessFilter(final long lastRewardsToProcess) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastRewardsToProcess);
    return Filter.createMemCompFilter(LAST_REWARDS_TO_PROCESS_OFFSET, _data);
  }

  public static Filter createLastVaultOperatorDelegationIndexFilter(final int lastVaultOperatorDelegationIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, lastVaultOperatorDelegationIndex);
    return Filter.createMemCompFilter(LAST_VAULT_OPERATOR_DELEGATION_INDEX_OFFSET, _data);
  }

  public static NcnRewardRouter read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static NcnRewardRouter read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static NcnRewardRouter read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], NcnRewardRouter> FACTORY = NcnRewardRouter::read;

  public static NcnRewardRouter read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var ncnFeeGroup = NcnFeeGroup.read(_data, i);
    i += Borsh.len(ncnFeeGroup);
    final var operator = readPubKey(_data, i);
    i += 32;
    final var ncn = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var slotCreated = getInt64LE(_data, i);
    i += 8;
    final var ncnOperatorIndex = getInt64LE(_data, i);
    i += 8;
    final var totalRewards = getInt64LE(_data, i);
    i += 8;
    final var rewardPool = getInt64LE(_data, i);
    i += 8;
    final var rewardsProcessed = getInt64LE(_data, i);
    i += 8;
    final var operatorRewards = getInt64LE(_data, i);
    i += 8;
    final var reserved = new byte[128];
    i += Borsh.readArray(reserved, _data, i);
    final var lastRewardsToProcess = getInt64LE(_data, i);
    i += 8;
    final var lastVaultOperatorDelegationIndex = getInt16LE(_data, i);
    i += 2;
    final var vaultRewardRoutes = new VaultRewardRoute[64];
    Borsh.readArray(vaultRewardRoutes, VaultRewardRoute::read, _data, i);
    return new NcnRewardRouter(_address,
                               discriminator,
                               ncnFeeGroup,
                               operator,
                               ncn,
                               epoch,
                               bump,
                               slotCreated,
                               ncnOperatorIndex,
                               totalRewards,
                               rewardPool,
                               rewardsProcessed,
                               operatorRewards,
                               reserved,
                               lastRewardsToProcess,
                               lastVaultOperatorDelegationIndex,
                               vaultRewardRoutes);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.write(ncnFeeGroup, _data, i);
    operator.write(_data, i);
    i += 32;
    ncn.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    _data[i] = (byte) bump;
    ++i;
    putInt64LE(_data, i, slotCreated);
    i += 8;
    putInt64LE(_data, i, ncnOperatorIndex);
    i += 8;
    putInt64LE(_data, i, totalRewards);
    i += 8;
    putInt64LE(_data, i, rewardPool);
    i += 8;
    putInt64LE(_data, i, rewardsProcessed);
    i += 8;
    putInt64LE(_data, i, operatorRewards);
    i += 8;
    i += Borsh.writeArray(reserved, _data, i);
    putInt64LE(_data, i, lastRewardsToProcess);
    i += 8;
    putInt16LE(_data, i, lastVaultOperatorDelegationIndex);
    i += 2;
    i += Borsh.writeArray(vaultRewardRoutes, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
