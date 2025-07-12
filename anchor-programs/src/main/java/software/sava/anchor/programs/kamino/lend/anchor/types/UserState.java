package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record UserState(PublicKey _address,
                        Discriminator discriminator,
                        long userId,
                        PublicKey farmState,
                        PublicKey owner,
                        // Indicate if this user state is part of a delegated farm
                        int isFarmDelegated,
                        byte[] padding0,
                        // Rewards tally used for computation of gained rewards
                        // (scaled from `Decimal` representation).
                        BigInteger[] rewardsTallyScaled,
                        // Number of reward tokens ready for claim
                        long[] rewardsIssuedUnclaimed,
                        long[] lastClaimTs,
                        // User stake deposited and usable, generating rewards and fees.
                        // (scaled from `Decimal` representation).
                        BigInteger activeStakeScaled,
                        // User stake deposited but not usable and not generating rewards yet.
                        // (scaled from `Decimal` representation).
                        BigInteger pendingDepositStakeScaled,
                        // After this timestamp, pending user stake can be moved to user stake
                        // Initialized to now() + delayed user stake period
                        long pendingDepositStakeTs,
                        // User deposits unstaked, pending for withdrawal, not usable and not generating rewards.
                        // (scaled from `Decimal` representation).
                        BigInteger pendingWithdrawalUnstakeScaled,
                        // After this timestamp, user can withdraw their deposit.
                        long pendingWithdrawalUnstakeTs,
                        // User bump used for account address validation
                        long bump,
                        // Delegatee used for initialisation - useful to check against
                        PublicKey delegatee,
                        long lastStakeTs,
                        long[] padding1) implements Borsh {

  public static final int BYTES = 920;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int USER_ID_OFFSET = 8;
  public static final int FARM_STATE_OFFSET = 16;
  public static final int OWNER_OFFSET = 48;
  public static final int IS_FARM_DELEGATED_OFFSET = 80;
  public static final int PADDING0_OFFSET = 81;
  public static final int REWARDS_TALLY_SCALED_OFFSET = 88;
  public static final int REWARDS_ISSUED_UNCLAIMED_OFFSET = 248;
  public static final int LAST_CLAIM_TS_OFFSET = 328;
  public static final int ACTIVE_STAKE_SCALED_OFFSET = 408;
  public static final int PENDING_DEPOSIT_STAKE_SCALED_OFFSET = 424;
  public static final int PENDING_DEPOSIT_STAKE_TS_OFFSET = 440;
  public static final int PENDING_WITHDRAWAL_UNSTAKE_SCALED_OFFSET = 448;
  public static final int PENDING_WITHDRAWAL_UNSTAKE_TS_OFFSET = 464;
  public static final int BUMP_OFFSET = 472;
  public static final int DELEGATEE_OFFSET = 480;
  public static final int LAST_STAKE_TS_OFFSET = 512;
  public static final int PADDING1_OFFSET = 520;

  public static Filter createUserIdFilter(final long userId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, userId);
    return Filter.createMemCompFilter(USER_ID_OFFSET, _data);
  }

  public static Filter createFarmStateFilter(final PublicKey farmState) {
    return Filter.createMemCompFilter(FARM_STATE_OFFSET, farmState);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createIsFarmDelegatedFilter(final int isFarmDelegated) {
    return Filter.createMemCompFilter(IS_FARM_DELEGATED_OFFSET, new byte[]{(byte) isFarmDelegated});
  }

  public static Filter createActiveStakeScaledFilter(final BigInteger activeStakeScaled) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, activeStakeScaled);
    return Filter.createMemCompFilter(ACTIVE_STAKE_SCALED_OFFSET, _data);
  }

  public static Filter createPendingDepositStakeScaledFilter(final BigInteger pendingDepositStakeScaled) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, pendingDepositStakeScaled);
    return Filter.createMemCompFilter(PENDING_DEPOSIT_STAKE_SCALED_OFFSET, _data);
  }

  public static Filter createPendingDepositStakeTsFilter(final long pendingDepositStakeTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, pendingDepositStakeTs);
    return Filter.createMemCompFilter(PENDING_DEPOSIT_STAKE_TS_OFFSET, _data);
  }

  public static Filter createPendingWithdrawalUnstakeScaledFilter(final BigInteger pendingWithdrawalUnstakeScaled) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, pendingWithdrawalUnstakeScaled);
    return Filter.createMemCompFilter(PENDING_WITHDRAWAL_UNSTAKE_SCALED_OFFSET, _data);
  }

  public static Filter createPendingWithdrawalUnstakeTsFilter(final long pendingWithdrawalUnstakeTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, pendingWithdrawalUnstakeTs);
    return Filter.createMemCompFilter(PENDING_WITHDRAWAL_UNSTAKE_TS_OFFSET, _data);
  }

  public static Filter createBumpFilter(final long bump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, bump);
    return Filter.createMemCompFilter(BUMP_OFFSET, _data);
  }

  public static Filter createDelegateeFilter(final PublicKey delegatee) {
    return Filter.createMemCompFilter(DELEGATEE_OFFSET, delegatee);
  }

  public static Filter createLastStakeTsFilter(final long lastStakeTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastStakeTs);
    return Filter.createMemCompFilter(LAST_STAKE_TS_OFFSET, _data);
  }

  public static UserState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static UserState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static UserState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], UserState> FACTORY = UserState::read;

  public static UserState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var userId = getInt64LE(_data, i);
    i += 8;
    final var farmState = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var isFarmDelegated = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[7];
    i += Borsh.readArray(padding0, _data, i);
    final var rewardsTallyScaled = new BigInteger[10];
    i += Borsh.read128Array(rewardsTallyScaled, _data, i);
    final var rewardsIssuedUnclaimed = new long[10];
    i += Borsh.readArray(rewardsIssuedUnclaimed, _data, i);
    final var lastClaimTs = new long[10];
    i += Borsh.readArray(lastClaimTs, _data, i);
    final var activeStakeScaled = getInt128LE(_data, i);
    i += 16;
    final var pendingDepositStakeScaled = getInt128LE(_data, i);
    i += 16;
    final var pendingDepositStakeTs = getInt64LE(_data, i);
    i += 8;
    final var pendingWithdrawalUnstakeScaled = getInt128LE(_data, i);
    i += 16;
    final var pendingWithdrawalUnstakeTs = getInt64LE(_data, i);
    i += 8;
    final var bump = getInt64LE(_data, i);
    i += 8;
    final var delegatee = readPubKey(_data, i);
    i += 32;
    final var lastStakeTs = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new long[50];
    Borsh.readArray(padding1, _data, i);
    return new UserState(_address,
                         discriminator,
                         userId,
                         farmState,
                         owner,
                         isFarmDelegated,
                         padding0,
                         rewardsTallyScaled,
                         rewardsIssuedUnclaimed,
                         lastClaimTs,
                         activeStakeScaled,
                         pendingDepositStakeScaled,
                         pendingDepositStakeTs,
                         pendingWithdrawalUnstakeScaled,
                         pendingWithdrawalUnstakeTs,
                         bump,
                         delegatee,
                         lastStakeTs,
                         padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, userId);
    i += 8;
    farmState.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    _data[i] = (byte) isFarmDelegated;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    i += Borsh.write128Array(rewardsTallyScaled, _data, i);
    i += Borsh.writeArray(rewardsIssuedUnclaimed, _data, i);
    i += Borsh.writeArray(lastClaimTs, _data, i);
    putInt128LE(_data, i, activeStakeScaled);
    i += 16;
    putInt128LE(_data, i, pendingDepositStakeScaled);
    i += 16;
    putInt64LE(_data, i, pendingDepositStakeTs);
    i += 8;
    putInt128LE(_data, i, pendingWithdrawalUnstakeScaled);
    i += 16;
    putInt64LE(_data, i, pendingWithdrawalUnstakeTs);
    i += 8;
    putInt64LE(_data, i, bump);
    i += 8;
    delegatee.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastStakeTs);
    i += 8;
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
