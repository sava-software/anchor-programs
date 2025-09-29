package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record UserRewardsInfo(PublicKey _address,
                              Discriminator discriminator,
                              PublicKey vaultAddress,
                              int bump,
                              PublicKey stakeAccountAddress,
                              PodU64 stakeTime,
                              PublicKey user,
                              PodU64 lpAmount,
                              int durationIndex,
                              PodDecimal[] lastRewardIndexes,
                              PodU64[] pendingRewards,
                              PodU64[] lastRewardIndexUpdateTime) implements Borsh {

  public static final int BYTES = 322;
  public static final int LAST_REWARD_INDEXES_LEN = 5;
  public static final int PENDING_REWARDS_LEN = 5;
  public static final int LAST_REWARD_INDEX_UPDATE_TIME_LEN = 5;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(242, 27, 82, 180, 183, 119, 156, 237);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VAULT_ADDRESS_OFFSET = 8;
  public static final int BUMP_OFFSET = 40;
  public static final int STAKE_ACCOUNT_ADDRESS_OFFSET = 41;
  public static final int STAKE_TIME_OFFSET = 73;
  public static final int USER_OFFSET = 81;
  public static final int LP_AMOUNT_OFFSET = 113;
  public static final int DURATION_INDEX_OFFSET = 121;
  public static final int LAST_REWARD_INDEXES_OFFSET = 122;
  public static final int PENDING_REWARDS_OFFSET = 242;
  public static final int LAST_REWARD_INDEX_UPDATE_TIME_OFFSET = 282;

  public static Filter createVaultAddressFilter(final PublicKey vaultAddress) {
    return Filter.createMemCompFilter(VAULT_ADDRESS_OFFSET, vaultAddress);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createStakeAccountAddressFilter(final PublicKey stakeAccountAddress) {
    return Filter.createMemCompFilter(STAKE_ACCOUNT_ADDRESS_OFFSET, stakeAccountAddress);
  }

  public static Filter createStakeTimeFilter(final PodU64 stakeTime) {
    return Filter.createMemCompFilter(STAKE_TIME_OFFSET, stakeTime.write());
  }

  public static Filter createUserFilter(final PublicKey user) {
    return Filter.createMemCompFilter(USER_OFFSET, user);
  }

  public static Filter createLpAmountFilter(final PodU64 lpAmount) {
    return Filter.createMemCompFilter(LP_AMOUNT_OFFSET, lpAmount.write());
  }

  public static Filter createDurationIndexFilter(final int durationIndex) {
    return Filter.createMemCompFilter(DURATION_INDEX_OFFSET, new byte[]{(byte) durationIndex});
  }

  public static UserRewardsInfo read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UserRewardsInfo read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static UserRewardsInfo read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UserRewardsInfo> FACTORY = UserRewardsInfo::read;

  public static UserRewardsInfo read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vaultAddress = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var stakeAccountAddress = readPubKey(_data, i);
    i += 32;
    final var stakeTime = PodU64.read(_data, i);
    i += Borsh.len(stakeTime);
    final var user = readPubKey(_data, i);
    i += 32;
    final var lpAmount = PodU64.read(_data, i);
    i += Borsh.len(lpAmount);
    final var durationIndex = _data[i] & 0xFF;
    ++i;
    final var lastRewardIndexes = new PodDecimal[5];
    i += Borsh.readArray(lastRewardIndexes, PodDecimal::read, _data, i);
    final var pendingRewards = new PodU64[5];
    i += Borsh.readArray(pendingRewards, PodU64::read, _data, i);
    final var lastRewardIndexUpdateTime = new PodU64[5];
    Borsh.readArray(lastRewardIndexUpdateTime, PodU64::read, _data, i);
    return new UserRewardsInfo(_address,
                               discriminator,
                               vaultAddress,
                               bump,
                               stakeAccountAddress,
                               stakeTime,
                               user,
                               lpAmount,
                               durationIndex,
                               lastRewardIndexes,
                               pendingRewards,
                               lastRewardIndexUpdateTime);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vaultAddress.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    stakeAccountAddress.write(_data, i);
    i += 32;
    i += Borsh.write(stakeTime, _data, i);
    user.write(_data, i);
    i += 32;
    i += Borsh.write(lpAmount, _data, i);
    _data[i] = (byte) durationIndex;
    ++i;
    i += Borsh.writeArrayChecked(lastRewardIndexes, 5, _data, i);
    i += Borsh.writeArrayChecked(pendingRewards, 5, _data, i);
    i += Borsh.writeArrayChecked(lastRewardIndexUpdateTime, 5, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
