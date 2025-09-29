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
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record TokenVault(PublicKey _address,
                         Discriminator discriminator,
                         boolean isInitialized,
                         int bump,
                         int tokenAccountBump,
                         PublicKey tokenMint,
                         PublicKey tokenVaultTokenAccount,
                         TokenPermissions tokenPermissions,
                         long withdrawTimeLimit,
                         long withdrawInstantFee,
                         long withdrawInstantFeeEarned,
                         long[] stakeLevel,
                         StakeStats tokensStaked,
                         BigInteger rewardTokensToDistribute,
                         BigInteger rewardTokensPaid,
                         BigInteger tokensToDistribute,
                         BigInteger tokensDistributed,
                         int lastRewardEpochCount,
                         BigInteger rewardTokensDistributed,
                         int[] padding,
                         int revenueTokenAccountBump,
                         long revenuePerFafStaked,
                         BigInteger revenueAccrued,
                         BigInteger revenueDistributed,
                         BigInteger revenuePaid,
                         long[] padding2) implements Borsh {

  public static final int BYTES = 367;
  public static final int STAKE_LEVEL_LEN = 6;
  public static final int PADDING_LEN = 3;
  public static final int PADDING_2_LEN = 4;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int IS_INITIALIZED_OFFSET = 8;
  public static final int BUMP_OFFSET = 9;
  public static final int TOKEN_ACCOUNT_BUMP_OFFSET = 10;
  public static final int TOKEN_MINT_OFFSET = 11;
  public static final int TOKEN_VAULT_TOKEN_ACCOUNT_OFFSET = 43;
  public static final int TOKEN_PERMISSIONS_OFFSET = 75;
  public static final int WITHDRAW_TIME_LIMIT_OFFSET = 78;
  public static final int WITHDRAW_INSTANT_FEE_OFFSET = 86;
  public static final int WITHDRAW_INSTANT_FEE_EARNED_OFFSET = 94;
  public static final int STAKE_LEVEL_OFFSET = 102;
  public static final int TOKENS_STAKED_OFFSET = 150;
  public static final int REWARD_TOKENS_TO_DISTRIBUTE_OFFSET = 182;
  public static final int REWARD_TOKENS_PAID_OFFSET = 198;
  public static final int TOKENS_TO_DISTRIBUTE_OFFSET = 214;
  public static final int TOKENS_DISTRIBUTED_OFFSET = 230;
  public static final int LAST_REWARD_EPOCH_COUNT_OFFSET = 246;
  public static final int REWARD_TOKENS_DISTRIBUTED_OFFSET = 250;
  public static final int PADDING_OFFSET = 266;
  public static final int REVENUE_TOKEN_ACCOUNT_BUMP_OFFSET = 278;
  public static final int REVENUE_PER_FAF_STAKED_OFFSET = 279;
  public static final int REVENUE_ACCRUED_OFFSET = 287;
  public static final int REVENUE_DISTRIBUTED_OFFSET = 303;
  public static final int REVENUE_PAID_OFFSET = 319;
  public static final int PADDING_2_OFFSET = 335;

  public static Filter createIsInitializedFilter(final boolean isInitialized) {
    return Filter.createMemCompFilter(IS_INITIALIZED_OFFSET, new byte[]{(byte) (isInitialized ? 1 : 0)});
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createTokenAccountBumpFilter(final int tokenAccountBump) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_BUMP_OFFSET, new byte[]{(byte) tokenAccountBump});
  }

  public static Filter createTokenMintFilter(final PublicKey tokenMint) {
    return Filter.createMemCompFilter(TOKEN_MINT_OFFSET, tokenMint);
  }

  public static Filter createTokenVaultTokenAccountFilter(final PublicKey tokenVaultTokenAccount) {
    return Filter.createMemCompFilter(TOKEN_VAULT_TOKEN_ACCOUNT_OFFSET, tokenVaultTokenAccount);
  }

  public static Filter createTokenPermissionsFilter(final TokenPermissions tokenPermissions) {
    return Filter.createMemCompFilter(TOKEN_PERMISSIONS_OFFSET, tokenPermissions.write());
  }

  public static Filter createWithdrawTimeLimitFilter(final long withdrawTimeLimit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, withdrawTimeLimit);
    return Filter.createMemCompFilter(WITHDRAW_TIME_LIMIT_OFFSET, _data);
  }

  public static Filter createWithdrawInstantFeeFilter(final long withdrawInstantFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, withdrawInstantFee);
    return Filter.createMemCompFilter(WITHDRAW_INSTANT_FEE_OFFSET, _data);
  }

  public static Filter createWithdrawInstantFeeEarnedFilter(final long withdrawInstantFeeEarned) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, withdrawInstantFeeEarned);
    return Filter.createMemCompFilter(WITHDRAW_INSTANT_FEE_EARNED_OFFSET, _data);
  }

  public static Filter createTokensStakedFilter(final StakeStats tokensStaked) {
    return Filter.createMemCompFilter(TOKENS_STAKED_OFFSET, tokensStaked.write());
  }

  public static Filter createRewardTokensToDistributeFilter(final BigInteger rewardTokensToDistribute) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, rewardTokensToDistribute);
    return Filter.createMemCompFilter(REWARD_TOKENS_TO_DISTRIBUTE_OFFSET, _data);
  }

  public static Filter createRewardTokensPaidFilter(final BigInteger rewardTokensPaid) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, rewardTokensPaid);
    return Filter.createMemCompFilter(REWARD_TOKENS_PAID_OFFSET, _data);
  }

  public static Filter createTokensToDistributeFilter(final BigInteger tokensToDistribute) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, tokensToDistribute);
    return Filter.createMemCompFilter(TOKENS_TO_DISTRIBUTE_OFFSET, _data);
  }

  public static Filter createTokensDistributedFilter(final BigInteger tokensDistributed) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, tokensDistributed);
    return Filter.createMemCompFilter(TOKENS_DISTRIBUTED_OFFSET, _data);
  }

  public static Filter createLastRewardEpochCountFilter(final int lastRewardEpochCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastRewardEpochCount);
    return Filter.createMemCompFilter(LAST_REWARD_EPOCH_COUNT_OFFSET, _data);
  }

  public static Filter createRewardTokensDistributedFilter(final BigInteger rewardTokensDistributed) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, rewardTokensDistributed);
    return Filter.createMemCompFilter(REWARD_TOKENS_DISTRIBUTED_OFFSET, _data);
  }

  public static Filter createRevenueTokenAccountBumpFilter(final int revenueTokenAccountBump) {
    return Filter.createMemCompFilter(REVENUE_TOKEN_ACCOUNT_BUMP_OFFSET, new byte[]{(byte) revenueTokenAccountBump});
  }

  public static Filter createRevenuePerFafStakedFilter(final long revenuePerFafStaked) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, revenuePerFafStaked);
    return Filter.createMemCompFilter(REVENUE_PER_FAF_STAKED_OFFSET, _data);
  }

  public static Filter createRevenueAccruedFilter(final BigInteger revenueAccrued) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, revenueAccrued);
    return Filter.createMemCompFilter(REVENUE_ACCRUED_OFFSET, _data);
  }

  public static Filter createRevenueDistributedFilter(final BigInteger revenueDistributed) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, revenueDistributed);
    return Filter.createMemCompFilter(REVENUE_DISTRIBUTED_OFFSET, _data);
  }

  public static Filter createRevenuePaidFilter(final BigInteger revenuePaid) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, revenuePaid);
    return Filter.createMemCompFilter(REVENUE_PAID_OFFSET, _data);
  }

  public static TokenVault read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static TokenVault read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static TokenVault read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], TokenVault> FACTORY = TokenVault::read;

  public static TokenVault read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var isInitialized = _data[i] == 1;
    ++i;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var tokenAccountBump = _data[i] & 0xFF;
    ++i;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var tokenVaultTokenAccount = readPubKey(_data, i);
    i += 32;
    final var tokenPermissions = TokenPermissions.read(_data, i);
    i += Borsh.len(tokenPermissions);
    final var withdrawTimeLimit = getInt64LE(_data, i);
    i += 8;
    final var withdrawInstantFee = getInt64LE(_data, i);
    i += 8;
    final var withdrawInstantFeeEarned = getInt64LE(_data, i);
    i += 8;
    final var stakeLevel = new long[6];
    i += Borsh.readArray(stakeLevel, _data, i);
    final var tokensStaked = StakeStats.read(_data, i);
    i += Borsh.len(tokensStaked);
    final var rewardTokensToDistribute = getInt128LE(_data, i);
    i += 16;
    final var rewardTokensPaid = getInt128LE(_data, i);
    i += 16;
    final var tokensToDistribute = getInt128LE(_data, i);
    i += 16;
    final var tokensDistributed = getInt128LE(_data, i);
    i += 16;
    final var lastRewardEpochCount = getInt32LE(_data, i);
    i += 4;
    final var rewardTokensDistributed = getInt128LE(_data, i);
    i += 16;
    final var padding = new int[3];
    i += Borsh.readArray(padding, _data, i);
    final var revenueTokenAccountBump = _data[i] & 0xFF;
    ++i;
    final var revenuePerFafStaked = getInt64LE(_data, i);
    i += 8;
    final var revenueAccrued = getInt128LE(_data, i);
    i += 16;
    final var revenueDistributed = getInt128LE(_data, i);
    i += 16;
    final var revenuePaid = getInt128LE(_data, i);
    i += 16;
    final var padding2 = new long[4];
    Borsh.readArray(padding2, _data, i);
    return new TokenVault(_address,
                          discriminator,
                          isInitialized,
                          bump,
                          tokenAccountBump,
                          tokenMint,
                          tokenVaultTokenAccount,
                          tokenPermissions,
                          withdrawTimeLimit,
                          withdrawInstantFee,
                          withdrawInstantFeeEarned,
                          stakeLevel,
                          tokensStaked,
                          rewardTokensToDistribute,
                          rewardTokensPaid,
                          tokensToDistribute,
                          tokensDistributed,
                          lastRewardEpochCount,
                          rewardTokensDistributed,
                          padding,
                          revenueTokenAccountBump,
                          revenuePerFafStaked,
                          revenueAccrued,
                          revenueDistributed,
                          revenuePaid,
                          padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) (isInitialized ? 1 : 0);
    ++i;
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) tokenAccountBump;
    ++i;
    tokenMint.write(_data, i);
    i += 32;
    tokenVaultTokenAccount.write(_data, i);
    i += 32;
    i += Borsh.write(tokenPermissions, _data, i);
    putInt64LE(_data, i, withdrawTimeLimit);
    i += 8;
    putInt64LE(_data, i, withdrawInstantFee);
    i += 8;
    putInt64LE(_data, i, withdrawInstantFeeEarned);
    i += 8;
    i += Borsh.writeArrayChecked(stakeLevel, 6, _data, i);
    i += Borsh.write(tokensStaked, _data, i);
    putInt128LE(_data, i, rewardTokensToDistribute);
    i += 16;
    putInt128LE(_data, i, rewardTokensPaid);
    i += 16;
    putInt128LE(_data, i, tokensToDistribute);
    i += 16;
    putInt128LE(_data, i, tokensDistributed);
    i += 16;
    putInt32LE(_data, i, lastRewardEpochCount);
    i += 4;
    putInt128LE(_data, i, rewardTokensDistributed);
    i += 16;
    i += Borsh.writeArrayChecked(padding, 3, _data, i);
    _data[i] = (byte) revenueTokenAccountBump;
    ++i;
    putInt64LE(_data, i, revenuePerFafStaked);
    i += 8;
    putInt128LE(_data, i, revenueAccrued);
    i += 16;
    putInt128LE(_data, i, revenueDistributed);
    i += 16;
    putInt128LE(_data, i, revenuePaid);
    i += 16;
    i += Borsh.writeArrayChecked(padding2, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
