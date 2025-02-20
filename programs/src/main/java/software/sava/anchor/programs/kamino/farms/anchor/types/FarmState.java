package software.sava.anchor.programs.kamino.farms.anchor.types;

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
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FarmState(PublicKey _address,
                        Discriminator discriminator,
                        PublicKey farmAdmin,
                        PublicKey globalConfig,
                        TokenInfo token,
                        RewardInfo[] rewardInfos,
                        long numRewardTokens,
                        // Data used to calculate the rewards of the user
                        long numUsers,
                        // The number of token in the `farm_vault` staked (getting rewards and fees)
                        // Set such as `farm_vault.amount = total_staked_amount + total_pending_amount`
                        long totalStakedAmount,
                        PublicKey farmVault,
                        PublicKey farmVaultsAuthority,
                        long farmVaultsAuthorityBump,
                        // Only used for delegate farms
                        // Set to `default()` otherwise
                        PublicKey delegateAuthority,
                        // Raw representation of a `TimeUnit`
                        // Seconds = 0, Slots = 1
                        int timeUnit,
                        // Automatically set to true in case of a full authority withdrawal
                        // If true, the farm is frozen and no more deposits are allowed
                        int isFarmFrozen,
                        // Indicates if the farm is a delegate farm
                        // If true, the farm is a delegate farm and the `delegate_authority` is set*
                        int isFarmDelegated,
                        byte[] padding0,
                        // Withdraw authority for the farm, allowed to lock deposited funds and withdraw them
                        // Set to `default()` if unused (only the depositors can withdraw their funds)
                        PublicKey withdrawAuthority,
                        // Delay between a user deposit and the moment it is considered as staked
                        // 0 if unused
                        int depositWarmupPeriod,
                        // Delay between a user unstake and the ability to withdraw his deposit.
                        int withdrawalCooldownPeriod,
                        // Total active stake of tokens in the farm (scaled from `Decimal` representation).
                        BigInteger totalActiveStakeScaled,
                        // Total pending stake of tokens in the farm (scaled from `Decimal` representation).
                        // (can be used by `withdraw_authority` but don't get rewards or fees)
                        BigInteger totalPendingStakeScaled,
                        // Total pending amount of tokens in the farm
                        long totalPendingAmount,
                        // Slashed amounts from early withdrawal
                        long slashedAmountCurrent,
                        long slashedAmountCumulative,
                        PublicKey slashedAmountSpillAddress,
                        // Locking stake
                        long lockingMode,
                        long lockingStartTimestamp,
                        long lockingDuration,
                        long lockingEarlyWithdrawalPenaltyBps,
                        long depositCapAmount,
                        PublicKey scopePrices,
                        long scopeOraclePriceId,
                        long scopeOracleMaxAge,
                        PublicKey pendingFarmAdmin,
                        PublicKey strategyId,
                        PublicKey delegatedRpsAdmin,
                        PublicKey vaultId,
                        long[] padding) implements Borsh {

  public static final int BYTES = 8336;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int FARM_ADMIN_OFFSET = 8;
  public static final int GLOBAL_CONFIG_OFFSET = 40;
  public static final int TOKEN_OFFSET = 72;
  public static final int REWARD_INFOS_OFFSET = 192;
  public static final int NUM_REWARD_TOKENS_OFFSET = 7232;
  public static final int NUM_USERS_OFFSET = 7240;
  public static final int TOTAL_STAKED_AMOUNT_OFFSET = 7248;
  public static final int FARM_VAULT_OFFSET = 7256;
  public static final int FARM_VAULTS_AUTHORITY_OFFSET = 7288;
  public static final int FARM_VAULTS_AUTHORITY_BUMP_OFFSET = 7320;
  public static final int DELEGATE_AUTHORITY_OFFSET = 7328;
  public static final int TIME_UNIT_OFFSET = 7360;
  public static final int IS_FARM_FROZEN_OFFSET = 7361;
  public static final int IS_FARM_DELEGATED_OFFSET = 7362;
  public static final int PADDING0_OFFSET = 7363;
  public static final int WITHDRAW_AUTHORITY_OFFSET = 7368;
  public static final int DEPOSIT_WARMUP_PERIOD_OFFSET = 7400;
  public static final int WITHDRAWAL_COOLDOWN_PERIOD_OFFSET = 7404;
  public static final int TOTAL_ACTIVE_STAKE_SCALED_OFFSET = 7408;
  public static final int TOTAL_PENDING_STAKE_SCALED_OFFSET = 7424;
  public static final int TOTAL_PENDING_AMOUNT_OFFSET = 7440;
  public static final int SLASHED_AMOUNT_CURRENT_OFFSET = 7448;
  public static final int SLASHED_AMOUNT_CUMULATIVE_OFFSET = 7456;
  public static final int SLASHED_AMOUNT_SPILL_ADDRESS_OFFSET = 7464;
  public static final int LOCKING_MODE_OFFSET = 7496;
  public static final int LOCKING_START_TIMESTAMP_OFFSET = 7504;
  public static final int LOCKING_DURATION_OFFSET = 7512;
  public static final int LOCKING_EARLY_WITHDRAWAL_PENALTY_BPS_OFFSET = 7520;
  public static final int DEPOSIT_CAP_AMOUNT_OFFSET = 7528;
  public static final int SCOPE_PRICES_OFFSET = 7536;
  public static final int SCOPE_ORACLE_PRICE_ID_OFFSET = 7568;
  public static final int SCOPE_ORACLE_MAX_AGE_OFFSET = 7576;
  public static final int PENDING_FARM_ADMIN_OFFSET = 7584;
  public static final int STRATEGY_ID_OFFSET = 7616;
  public static final int DELEGATED_RPS_ADMIN_OFFSET = 7648;
  public static final int VAULT_ID_OFFSET = 7680;
  public static final int PADDING_OFFSET = 7712;

  public static Filter createFarmAdminFilter(final PublicKey farmAdmin) {
    return Filter.createMemCompFilter(FARM_ADMIN_OFFSET, farmAdmin);
  }

  public static Filter createGlobalConfigFilter(final PublicKey globalConfig) {
    return Filter.createMemCompFilter(GLOBAL_CONFIG_OFFSET, globalConfig);
  }

  public static Filter createTokenFilter(final TokenInfo token) {
    return Filter.createMemCompFilter(TOKEN_OFFSET, token.write());
  }

  public static Filter createNumRewardTokensFilter(final long numRewardTokens) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numRewardTokens);
    return Filter.createMemCompFilter(NUM_REWARD_TOKENS_OFFSET, _data);
  }

  public static Filter createNumUsersFilter(final long numUsers) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, numUsers);
    return Filter.createMemCompFilter(NUM_USERS_OFFSET, _data);
  }

  public static Filter createTotalStakedAmountFilter(final long totalStakedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalStakedAmount);
    return Filter.createMemCompFilter(TOTAL_STAKED_AMOUNT_OFFSET, _data);
  }

  public static Filter createFarmVaultFilter(final PublicKey farmVault) {
    return Filter.createMemCompFilter(FARM_VAULT_OFFSET, farmVault);
  }

  public static Filter createFarmVaultsAuthorityFilter(final PublicKey farmVaultsAuthority) {
    return Filter.createMemCompFilter(FARM_VAULTS_AUTHORITY_OFFSET, farmVaultsAuthority);
  }

  public static Filter createFarmVaultsAuthorityBumpFilter(final long farmVaultsAuthorityBump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, farmVaultsAuthorityBump);
    return Filter.createMemCompFilter(FARM_VAULTS_AUTHORITY_BUMP_OFFSET, _data);
  }

  public static Filter createDelegateAuthorityFilter(final PublicKey delegateAuthority) {
    return Filter.createMemCompFilter(DELEGATE_AUTHORITY_OFFSET, delegateAuthority);
  }

  public static Filter createTimeUnitFilter(final int timeUnit) {
    return Filter.createMemCompFilter(TIME_UNIT_OFFSET, new byte[]{(byte) timeUnit});
  }

  public static Filter createIsFarmFrozenFilter(final int isFarmFrozen) {
    return Filter.createMemCompFilter(IS_FARM_FROZEN_OFFSET, new byte[]{(byte) isFarmFrozen});
  }

  public static Filter createIsFarmDelegatedFilter(final int isFarmDelegated) {
    return Filter.createMemCompFilter(IS_FARM_DELEGATED_OFFSET, new byte[]{(byte) isFarmDelegated});
  }

  public static Filter createWithdrawAuthorityFilter(final PublicKey withdrawAuthority) {
    return Filter.createMemCompFilter(WITHDRAW_AUTHORITY_OFFSET, withdrawAuthority);
  }

  public static Filter createDepositWarmupPeriodFilter(final int depositWarmupPeriod) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, depositWarmupPeriod);
    return Filter.createMemCompFilter(DEPOSIT_WARMUP_PERIOD_OFFSET, _data);
  }

  public static Filter createWithdrawalCooldownPeriodFilter(final int withdrawalCooldownPeriod) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, withdrawalCooldownPeriod);
    return Filter.createMemCompFilter(WITHDRAWAL_COOLDOWN_PERIOD_OFFSET, _data);
  }

  public static Filter createTotalActiveStakeScaledFilter(final BigInteger totalActiveStakeScaled) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalActiveStakeScaled);
    return Filter.createMemCompFilter(TOTAL_ACTIVE_STAKE_SCALED_OFFSET, _data);
  }

  public static Filter createTotalPendingStakeScaledFilter(final BigInteger totalPendingStakeScaled) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalPendingStakeScaled);
    return Filter.createMemCompFilter(TOTAL_PENDING_STAKE_SCALED_OFFSET, _data);
  }

  public static Filter createTotalPendingAmountFilter(final long totalPendingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalPendingAmount);
    return Filter.createMemCompFilter(TOTAL_PENDING_AMOUNT_OFFSET, _data);
  }

  public static Filter createSlashedAmountCurrentFilter(final long slashedAmountCurrent) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slashedAmountCurrent);
    return Filter.createMemCompFilter(SLASHED_AMOUNT_CURRENT_OFFSET, _data);
  }

  public static Filter createSlashedAmountCumulativeFilter(final long slashedAmountCumulative) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, slashedAmountCumulative);
    return Filter.createMemCompFilter(SLASHED_AMOUNT_CUMULATIVE_OFFSET, _data);
  }

  public static Filter createSlashedAmountSpillAddressFilter(final PublicKey slashedAmountSpillAddress) {
    return Filter.createMemCompFilter(SLASHED_AMOUNT_SPILL_ADDRESS_OFFSET, slashedAmountSpillAddress);
  }

  public static Filter createLockingModeFilter(final long lockingMode) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockingMode);
    return Filter.createMemCompFilter(LOCKING_MODE_OFFSET, _data);
  }

  public static Filter createLockingStartTimestampFilter(final long lockingStartTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockingStartTimestamp);
    return Filter.createMemCompFilter(LOCKING_START_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createLockingDurationFilter(final long lockingDuration) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockingDuration);
    return Filter.createMemCompFilter(LOCKING_DURATION_OFFSET, _data);
  }

  public static Filter createLockingEarlyWithdrawalPenaltyBpsFilter(final long lockingEarlyWithdrawalPenaltyBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockingEarlyWithdrawalPenaltyBps);
    return Filter.createMemCompFilter(LOCKING_EARLY_WITHDRAWAL_PENALTY_BPS_OFFSET, _data);
  }

  public static Filter createDepositCapAmountFilter(final long depositCapAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositCapAmount);
    return Filter.createMemCompFilter(DEPOSIT_CAP_AMOUNT_OFFSET, _data);
  }

  public static Filter createScopePricesFilter(final PublicKey scopePrices) {
    return Filter.createMemCompFilter(SCOPE_PRICES_OFFSET, scopePrices);
  }

  public static Filter createScopeOraclePriceIdFilter(final long scopeOraclePriceId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, scopeOraclePriceId);
    return Filter.createMemCompFilter(SCOPE_ORACLE_PRICE_ID_OFFSET, _data);
  }

  public static Filter createScopeOracleMaxAgeFilter(final long scopeOracleMaxAge) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, scopeOracleMaxAge);
    return Filter.createMemCompFilter(SCOPE_ORACLE_MAX_AGE_OFFSET, _data);
  }

  public static Filter createPendingFarmAdminFilter(final PublicKey pendingFarmAdmin) {
    return Filter.createMemCompFilter(PENDING_FARM_ADMIN_OFFSET, pendingFarmAdmin);
  }

  public static Filter createStrategyIdFilter(final PublicKey strategyId) {
    return Filter.createMemCompFilter(STRATEGY_ID_OFFSET, strategyId);
  }

  public static Filter createDelegatedRpsAdminFilter(final PublicKey delegatedRpsAdmin) {
    return Filter.createMemCompFilter(DELEGATED_RPS_ADMIN_OFFSET, delegatedRpsAdmin);
  }

  public static Filter createVaultIdFilter(final PublicKey vaultId) {
    return Filter.createMemCompFilter(VAULT_ID_OFFSET, vaultId);
  }

  public static FarmState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FarmState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FarmState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FarmState> FACTORY = FarmState::read;

  public static FarmState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var farmAdmin = readPubKey(_data, i);
    i += 32;
    final var globalConfig = readPubKey(_data, i);
    i += 32;
    final var token = TokenInfo.read(_data, i);
    i += Borsh.len(token);
    final var rewardInfos = new RewardInfo[10];
    i += Borsh.readArray(rewardInfos, RewardInfo::read, _data, i);
    final var numRewardTokens = getInt64LE(_data, i);
    i += 8;
    final var numUsers = getInt64LE(_data, i);
    i += 8;
    final var totalStakedAmount = getInt64LE(_data, i);
    i += 8;
    final var farmVault = readPubKey(_data, i);
    i += 32;
    final var farmVaultsAuthority = readPubKey(_data, i);
    i += 32;
    final var farmVaultsAuthorityBump = getInt64LE(_data, i);
    i += 8;
    final var delegateAuthority = readPubKey(_data, i);
    i += 32;
    final var timeUnit = _data[i] & 0xFF;
    ++i;
    final var isFarmFrozen = _data[i] & 0xFF;
    ++i;
    final var isFarmDelegated = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[5];
    i += Borsh.readArray(padding0, _data, i);
    final var withdrawAuthority = readPubKey(_data, i);
    i += 32;
    final var depositWarmupPeriod = getInt32LE(_data, i);
    i += 4;
    final var withdrawalCooldownPeriod = getInt32LE(_data, i);
    i += 4;
    final var totalActiveStakeScaled = getInt128LE(_data, i);
    i += 16;
    final var totalPendingStakeScaled = getInt128LE(_data, i);
    i += 16;
    final var totalPendingAmount = getInt64LE(_data, i);
    i += 8;
    final var slashedAmountCurrent = getInt64LE(_data, i);
    i += 8;
    final var slashedAmountCumulative = getInt64LE(_data, i);
    i += 8;
    final var slashedAmountSpillAddress = readPubKey(_data, i);
    i += 32;
    final var lockingMode = getInt64LE(_data, i);
    i += 8;
    final var lockingStartTimestamp = getInt64LE(_data, i);
    i += 8;
    final var lockingDuration = getInt64LE(_data, i);
    i += 8;
    final var lockingEarlyWithdrawalPenaltyBps = getInt64LE(_data, i);
    i += 8;
    final var depositCapAmount = getInt64LE(_data, i);
    i += 8;
    final var scopePrices = readPubKey(_data, i);
    i += 32;
    final var scopeOraclePriceId = getInt64LE(_data, i);
    i += 8;
    final var scopeOracleMaxAge = getInt64LE(_data, i);
    i += 8;
    final var pendingFarmAdmin = readPubKey(_data, i);
    i += 32;
    final var strategyId = readPubKey(_data, i);
    i += 32;
    final var delegatedRpsAdmin = readPubKey(_data, i);
    i += 32;
    final var vaultId = readPubKey(_data, i);
    i += 32;
    final var padding = new long[78];
    Borsh.readArray(padding, _data, i);
    return new FarmState(_address,
                         discriminator,
                         farmAdmin,
                         globalConfig,
                         token,
                         rewardInfos,
                         numRewardTokens,
                         numUsers,
                         totalStakedAmount,
                         farmVault,
                         farmVaultsAuthority,
                         farmVaultsAuthorityBump,
                         delegateAuthority,
                         timeUnit,
                         isFarmFrozen,
                         isFarmDelegated,
                         padding0,
                         withdrawAuthority,
                         depositWarmupPeriod,
                         withdrawalCooldownPeriod,
                         totalActiveStakeScaled,
                         totalPendingStakeScaled,
                         totalPendingAmount,
                         slashedAmountCurrent,
                         slashedAmountCumulative,
                         slashedAmountSpillAddress,
                         lockingMode,
                         lockingStartTimestamp,
                         lockingDuration,
                         lockingEarlyWithdrawalPenaltyBps,
                         depositCapAmount,
                         scopePrices,
                         scopeOraclePriceId,
                         scopeOracleMaxAge,
                         pendingFarmAdmin,
                         strategyId,
                         delegatedRpsAdmin,
                         vaultId,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    farmAdmin.write(_data, i);
    i += 32;
    globalConfig.write(_data, i);
    i += 32;
    i += Borsh.write(token, _data, i);
    i += Borsh.writeArray(rewardInfos, _data, i);
    putInt64LE(_data, i, numRewardTokens);
    i += 8;
    putInt64LE(_data, i, numUsers);
    i += 8;
    putInt64LE(_data, i, totalStakedAmount);
    i += 8;
    farmVault.write(_data, i);
    i += 32;
    farmVaultsAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, farmVaultsAuthorityBump);
    i += 8;
    delegateAuthority.write(_data, i);
    i += 32;
    _data[i] = (byte) timeUnit;
    ++i;
    _data[i] = (byte) isFarmFrozen;
    ++i;
    _data[i] = (byte) isFarmDelegated;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    withdrawAuthority.write(_data, i);
    i += 32;
    putInt32LE(_data, i, depositWarmupPeriod);
    i += 4;
    putInt32LE(_data, i, withdrawalCooldownPeriod);
    i += 4;
    putInt128LE(_data, i, totalActiveStakeScaled);
    i += 16;
    putInt128LE(_data, i, totalPendingStakeScaled);
    i += 16;
    putInt64LE(_data, i, totalPendingAmount);
    i += 8;
    putInt64LE(_data, i, slashedAmountCurrent);
    i += 8;
    putInt64LE(_data, i, slashedAmountCumulative);
    i += 8;
    slashedAmountSpillAddress.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockingMode);
    i += 8;
    putInt64LE(_data, i, lockingStartTimestamp);
    i += 8;
    putInt64LE(_data, i, lockingDuration);
    i += 8;
    putInt64LE(_data, i, lockingEarlyWithdrawalPenaltyBps);
    i += 8;
    putInt64LE(_data, i, depositCapAmount);
    i += 8;
    scopePrices.write(_data, i);
    i += 32;
    putInt64LE(_data, i, scopeOraclePriceId);
    i += 8;
    putInt64LE(_data, i, scopeOracleMaxAge);
    i += 8;
    pendingFarmAdmin.write(_data, i);
    i += 32;
    strategyId.write(_data, i);
    i += 32;
    delegatedRpsAdmin.write(_data, i);
    i += 32;
    vaultId.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
