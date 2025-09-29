package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record GlobalConfig(PublicKey _address,
                           Discriminator discriminator,
                           long emergencyMode,
                           long blockDeposit,
                           long blockInvest,
                           long blockWithdraw,
                           long blockCollectFees,
                           long blockCollectRewards,
                           long blockSwapRewards,
                           int blockSwapUnevenVaults,
                           int blockEmergencySwap,
                           long minWithdrawalFeeBps,
                           PublicKey scopeProgramId,
                           PublicKey scopePriceId,
                           long[] swapRewardsDiscountBps,
                           PublicKey actionsAuthority,
                           PublicKey adminAuthority,
                           PublicKey[] treasuryFeeVaults,
                           PublicKey tokenInfos,
                           long blockLocalAdmin,
                           long minPerformanceFeeBps,
                           long minSwapUnevenSlippageToleranceBps,
                           long minReferencePriceSlippageToleranceBps,
                           long actionsAfterRebalanceDelaySeconds,
                           PublicKey treasuryFeeVaultReceiver,
                           long[] padding) implements Borsh {

  public static final int BYTES = 26832;
  public static final int SWAP_REWARDS_DISCOUNT_BPS_LEN = 256;
  public static final int TREASURY_FEE_VAULTS_LEN = 256;
  public static final int PADDING_LEN = 2035;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int EMERGENCY_MODE_OFFSET = 8;
  public static final int BLOCK_DEPOSIT_OFFSET = 16;
  public static final int BLOCK_INVEST_OFFSET = 24;
  public static final int BLOCK_WITHDRAW_OFFSET = 32;
  public static final int BLOCK_COLLECT_FEES_OFFSET = 40;
  public static final int BLOCK_COLLECT_REWARDS_OFFSET = 48;
  public static final int BLOCK_SWAP_REWARDS_OFFSET = 56;
  public static final int BLOCK_SWAP_UNEVEN_VAULTS_OFFSET = 64;
  public static final int BLOCK_EMERGENCY_SWAP_OFFSET = 68;
  public static final int MIN_WITHDRAWAL_FEE_BPS_OFFSET = 72;
  public static final int SCOPE_PROGRAM_ID_OFFSET = 80;
  public static final int SCOPE_PRICE_ID_OFFSET = 112;
  public static final int SWAP_REWARDS_DISCOUNT_BPS_OFFSET = 144;
  public static final int ACTIONS_AUTHORITY_OFFSET = 2192;
  public static final int ADMIN_AUTHORITY_OFFSET = 2224;
  public static final int TREASURY_FEE_VAULTS_OFFSET = 2256;
  public static final int TOKEN_INFOS_OFFSET = 10448;
  public static final int BLOCK_LOCAL_ADMIN_OFFSET = 10480;
  public static final int MIN_PERFORMANCE_FEE_BPS_OFFSET = 10488;
  public static final int MIN_SWAP_UNEVEN_SLIPPAGE_TOLERANCE_BPS_OFFSET = 10496;
  public static final int MIN_REFERENCE_PRICE_SLIPPAGE_TOLERANCE_BPS_OFFSET = 10504;
  public static final int ACTIONS_AFTER_REBALANCE_DELAY_SECONDS_OFFSET = 10512;
  public static final int TREASURY_FEE_VAULT_RECEIVER_OFFSET = 10520;
  public static final int PADDING_OFFSET = 10552;

  public static Filter createEmergencyModeFilter(final long emergencyMode) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, emergencyMode);
    return Filter.createMemCompFilter(EMERGENCY_MODE_OFFSET, _data);
  }

  public static Filter createBlockDepositFilter(final long blockDeposit) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockDeposit);
    return Filter.createMemCompFilter(BLOCK_DEPOSIT_OFFSET, _data);
  }

  public static Filter createBlockInvestFilter(final long blockInvest) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockInvest);
    return Filter.createMemCompFilter(BLOCK_INVEST_OFFSET, _data);
  }

  public static Filter createBlockWithdrawFilter(final long blockWithdraw) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockWithdraw);
    return Filter.createMemCompFilter(BLOCK_WITHDRAW_OFFSET, _data);
  }

  public static Filter createBlockCollectFeesFilter(final long blockCollectFees) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockCollectFees);
    return Filter.createMemCompFilter(BLOCK_COLLECT_FEES_OFFSET, _data);
  }

  public static Filter createBlockCollectRewardsFilter(final long blockCollectRewards) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockCollectRewards);
    return Filter.createMemCompFilter(BLOCK_COLLECT_REWARDS_OFFSET, _data);
  }

  public static Filter createBlockSwapRewardsFilter(final long blockSwapRewards) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockSwapRewards);
    return Filter.createMemCompFilter(BLOCK_SWAP_REWARDS_OFFSET, _data);
  }

  public static Filter createBlockSwapUnevenVaultsFilter(final int blockSwapUnevenVaults) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, blockSwapUnevenVaults);
    return Filter.createMemCompFilter(BLOCK_SWAP_UNEVEN_VAULTS_OFFSET, _data);
  }

  public static Filter createBlockEmergencySwapFilter(final int blockEmergencySwap) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, blockEmergencySwap);
    return Filter.createMemCompFilter(BLOCK_EMERGENCY_SWAP_OFFSET, _data);
  }

  public static Filter createMinWithdrawalFeeBpsFilter(final long minWithdrawalFeeBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minWithdrawalFeeBps);
    return Filter.createMemCompFilter(MIN_WITHDRAWAL_FEE_BPS_OFFSET, _data);
  }

  public static Filter createScopeProgramIdFilter(final PublicKey scopeProgramId) {
    return Filter.createMemCompFilter(SCOPE_PROGRAM_ID_OFFSET, scopeProgramId);
  }

  public static Filter createScopePriceIdFilter(final PublicKey scopePriceId) {
    return Filter.createMemCompFilter(SCOPE_PRICE_ID_OFFSET, scopePriceId);
  }

  public static Filter createActionsAuthorityFilter(final PublicKey actionsAuthority) {
    return Filter.createMemCompFilter(ACTIONS_AUTHORITY_OFFSET, actionsAuthority);
  }

  public static Filter createAdminAuthorityFilter(final PublicKey adminAuthority) {
    return Filter.createMemCompFilter(ADMIN_AUTHORITY_OFFSET, adminAuthority);
  }

  public static Filter createTokenInfosFilter(final PublicKey tokenInfos) {
    return Filter.createMemCompFilter(TOKEN_INFOS_OFFSET, tokenInfos);
  }

  public static Filter createBlockLocalAdminFilter(final long blockLocalAdmin) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, blockLocalAdmin);
    return Filter.createMemCompFilter(BLOCK_LOCAL_ADMIN_OFFSET, _data);
  }

  public static Filter createMinPerformanceFeeBpsFilter(final long minPerformanceFeeBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minPerformanceFeeBps);
    return Filter.createMemCompFilter(MIN_PERFORMANCE_FEE_BPS_OFFSET, _data);
  }

  public static Filter createMinSwapUnevenSlippageToleranceBpsFilter(final long minSwapUnevenSlippageToleranceBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minSwapUnevenSlippageToleranceBps);
    return Filter.createMemCompFilter(MIN_SWAP_UNEVEN_SLIPPAGE_TOLERANCE_BPS_OFFSET, _data);
  }

  public static Filter createMinReferencePriceSlippageToleranceBpsFilter(final long minReferencePriceSlippageToleranceBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minReferencePriceSlippageToleranceBps);
    return Filter.createMemCompFilter(MIN_REFERENCE_PRICE_SLIPPAGE_TOLERANCE_BPS_OFFSET, _data);
  }

  public static Filter createActionsAfterRebalanceDelaySecondsFilter(final long actionsAfterRebalanceDelaySeconds) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, actionsAfterRebalanceDelaySeconds);
    return Filter.createMemCompFilter(ACTIONS_AFTER_REBALANCE_DELAY_SECONDS_OFFSET, _data);
  }

  public static Filter createTreasuryFeeVaultReceiverFilter(final PublicKey treasuryFeeVaultReceiver) {
    return Filter.createMemCompFilter(TREASURY_FEE_VAULT_RECEIVER_OFFSET, treasuryFeeVaultReceiver);
  }

  public static GlobalConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static GlobalConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static GlobalConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], GlobalConfig> FACTORY = GlobalConfig::read;

  public static GlobalConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var emergencyMode = getInt64LE(_data, i);
    i += 8;
    final var blockDeposit = getInt64LE(_data, i);
    i += 8;
    final var blockInvest = getInt64LE(_data, i);
    i += 8;
    final var blockWithdraw = getInt64LE(_data, i);
    i += 8;
    final var blockCollectFees = getInt64LE(_data, i);
    i += 8;
    final var blockCollectRewards = getInt64LE(_data, i);
    i += 8;
    final var blockSwapRewards = getInt64LE(_data, i);
    i += 8;
    final var blockSwapUnevenVaults = getInt32LE(_data, i);
    i += 4;
    final var blockEmergencySwap = getInt32LE(_data, i);
    i += 4;
    final var minWithdrawalFeeBps = getInt64LE(_data, i);
    i += 8;
    final var scopeProgramId = readPubKey(_data, i);
    i += 32;
    final var scopePriceId = readPubKey(_data, i);
    i += 32;
    final var swapRewardsDiscountBps = new long[256];
    i += Borsh.readArray(swapRewardsDiscountBps, _data, i);
    final var actionsAuthority = readPubKey(_data, i);
    i += 32;
    final var adminAuthority = readPubKey(_data, i);
    i += 32;
    final var treasuryFeeVaults = new PublicKey[256];
    i += Borsh.readArray(treasuryFeeVaults, _data, i);
    final var tokenInfos = readPubKey(_data, i);
    i += 32;
    final var blockLocalAdmin = getInt64LE(_data, i);
    i += 8;
    final var minPerformanceFeeBps = getInt64LE(_data, i);
    i += 8;
    final var minSwapUnevenSlippageToleranceBps = getInt64LE(_data, i);
    i += 8;
    final var minReferencePriceSlippageToleranceBps = getInt64LE(_data, i);
    i += 8;
    final var actionsAfterRebalanceDelaySeconds = getInt64LE(_data, i);
    i += 8;
    final var treasuryFeeVaultReceiver = readPubKey(_data, i);
    i += 32;
    final var padding = new long[2035];
    Borsh.readArray(padding, _data, i);
    return new GlobalConfig(_address,
                            discriminator,
                            emergencyMode,
                            blockDeposit,
                            blockInvest,
                            blockWithdraw,
                            blockCollectFees,
                            blockCollectRewards,
                            blockSwapRewards,
                            blockSwapUnevenVaults,
                            blockEmergencySwap,
                            minWithdrawalFeeBps,
                            scopeProgramId,
                            scopePriceId,
                            swapRewardsDiscountBps,
                            actionsAuthority,
                            adminAuthority,
                            treasuryFeeVaults,
                            tokenInfos,
                            blockLocalAdmin,
                            minPerformanceFeeBps,
                            minSwapUnevenSlippageToleranceBps,
                            minReferencePriceSlippageToleranceBps,
                            actionsAfterRebalanceDelaySeconds,
                            treasuryFeeVaultReceiver,
                            padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, emergencyMode);
    i += 8;
    putInt64LE(_data, i, blockDeposit);
    i += 8;
    putInt64LE(_data, i, blockInvest);
    i += 8;
    putInt64LE(_data, i, blockWithdraw);
    i += 8;
    putInt64LE(_data, i, blockCollectFees);
    i += 8;
    putInt64LE(_data, i, blockCollectRewards);
    i += 8;
    putInt64LE(_data, i, blockSwapRewards);
    i += 8;
    putInt32LE(_data, i, blockSwapUnevenVaults);
    i += 4;
    putInt32LE(_data, i, blockEmergencySwap);
    i += 4;
    putInt64LE(_data, i, minWithdrawalFeeBps);
    i += 8;
    scopeProgramId.write(_data, i);
    i += 32;
    scopePriceId.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(swapRewardsDiscountBps, 256, _data, i);
    actionsAuthority.write(_data, i);
    i += 32;
    adminAuthority.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(treasuryFeeVaults, 256, _data, i);
    tokenInfos.write(_data, i);
    i += 32;
    putInt64LE(_data, i, blockLocalAdmin);
    i += 8;
    putInt64LE(_data, i, minPerformanceFeeBps);
    i += 8;
    putInt64LE(_data, i, minSwapUnevenSlippageToleranceBps);
    i += 8;
    putInt64LE(_data, i, minReferencePriceSlippageToleranceBps);
    i += 8;
    putInt64LE(_data, i, actionsAfterRebalanceDelaySeconds);
    i += 8;
    treasuryFeeVaultReceiver.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(padding, 2035, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
