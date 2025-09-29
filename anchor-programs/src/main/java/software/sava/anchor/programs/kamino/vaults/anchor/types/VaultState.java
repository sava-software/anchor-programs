package software.sava.anchor.programs.kamino.vaults.anchor.types;

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

public record VaultState(PublicKey _address,
                         Discriminator discriminator,
                         PublicKey vaultAdminAuthority,
                         PublicKey baseVaultAuthority,
                         long baseVaultAuthorityBump,
                         PublicKey tokenMint,
                         long tokenMintDecimals,
                         PublicKey tokenVault,
                         PublicKey tokenProgram,
                         PublicKey sharesMint,
                         long sharesMintDecimals,
                         long tokenAvailable,
                         long sharesIssued,
                         long availableCrankFunds,
                         long unallocatedWeight,
                         long performanceFeeBps,
                         long managementFeeBps,
                         long lastFeeChargeTimestamp,
                         BigInteger prevAumSf,
                         BigInteger pendingFeesSf,
                         VaultAllocation[] vaultAllocationStrategy,
                         BigInteger[] padding1,
                         long minDepositAmount,
                         long minWithdrawAmount,
                         long minInvestAmount,
                         long minInvestDelaySlots,
                         long crankFundFeePerReserve,
                         PublicKey pendingAdmin,
                         BigInteger cumulativeEarnedInterestSf,
                         BigInteger cumulativeMgmtFeesSf,
                         BigInteger cumulativePerfFeesSf,
                         byte[] name,
                         PublicKey vaultLookupTable,
                         PublicKey vaultFarm,
                         long creationTimestamp,
                         long unallocatedTokensCap,
                         PublicKey allocationAdmin,
                         BigInteger[] padding3) implements Borsh {

  public static final int BYTES = 62552;
  public static final int VAULT_ALLOCATION_STRATEGY_LEN = 25;
  public static final int PADDING_1_LEN = 256;
  public static final int NAME_LEN = 40;
  public static final int PADDING_3_LEN = 242;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VAULT_ADMIN_AUTHORITY_OFFSET = 8;
  public static final int BASE_VAULT_AUTHORITY_OFFSET = 40;
  public static final int BASE_VAULT_AUTHORITY_BUMP_OFFSET = 72;
  public static final int TOKEN_MINT_OFFSET = 80;
  public static final int TOKEN_MINT_DECIMALS_OFFSET = 112;
  public static final int TOKEN_VAULT_OFFSET = 120;
  public static final int TOKEN_PROGRAM_OFFSET = 152;
  public static final int SHARES_MINT_OFFSET = 184;
  public static final int SHARES_MINT_DECIMALS_OFFSET = 216;
  public static final int TOKEN_AVAILABLE_OFFSET = 224;
  public static final int SHARES_ISSUED_OFFSET = 232;
  public static final int AVAILABLE_CRANK_FUNDS_OFFSET = 240;
  public static final int UNALLOCATED_WEIGHT_OFFSET = 248;
  public static final int PERFORMANCE_FEE_BPS_OFFSET = 256;
  public static final int MANAGEMENT_FEE_BPS_OFFSET = 264;
  public static final int LAST_FEE_CHARGE_TIMESTAMP_OFFSET = 272;
  public static final int PREV_AUM_SF_OFFSET = 280;
  public static final int PENDING_FEES_SF_OFFSET = 296;
  public static final int VAULT_ALLOCATION_STRATEGY_OFFSET = 312;
  public static final int PADDING_1_OFFSET = 54312;
  public static final int MIN_DEPOSIT_AMOUNT_OFFSET = 58408;
  public static final int MIN_WITHDRAW_AMOUNT_OFFSET = 58416;
  public static final int MIN_INVEST_AMOUNT_OFFSET = 58424;
  public static final int MIN_INVEST_DELAY_SLOTS_OFFSET = 58432;
  public static final int CRANK_FUND_FEE_PER_RESERVE_OFFSET = 58440;
  public static final int PENDING_ADMIN_OFFSET = 58448;
  public static final int CUMULATIVE_EARNED_INTEREST_SF_OFFSET = 58480;
  public static final int CUMULATIVE_MGMT_FEES_SF_OFFSET = 58496;
  public static final int CUMULATIVE_PERF_FEES_SF_OFFSET = 58512;
  public static final int NAME_OFFSET = 58528;
  public static final int VAULT_LOOKUP_TABLE_OFFSET = 58568;
  public static final int VAULT_FARM_OFFSET = 58600;
  public static final int CREATION_TIMESTAMP_OFFSET = 58632;
  public static final int UNALLOCATED_TOKENS_CAP_OFFSET = 58640;
  public static final int ALLOCATION_ADMIN_OFFSET = 58648;
  public static final int PADDING_3_OFFSET = 58680;

  public static Filter createVaultAdminAuthorityFilter(final PublicKey vaultAdminAuthority) {
    return Filter.createMemCompFilter(VAULT_ADMIN_AUTHORITY_OFFSET, vaultAdminAuthority);
  }

  public static Filter createBaseVaultAuthorityFilter(final PublicKey baseVaultAuthority) {
    return Filter.createMemCompFilter(BASE_VAULT_AUTHORITY_OFFSET, baseVaultAuthority);
  }

  public static Filter createBaseVaultAuthorityBumpFilter(final long baseVaultAuthorityBump) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, baseVaultAuthorityBump);
    return Filter.createMemCompFilter(BASE_VAULT_AUTHORITY_BUMP_OFFSET, _data);
  }

  public static Filter createTokenMintFilter(final PublicKey tokenMint) {
    return Filter.createMemCompFilter(TOKEN_MINT_OFFSET, tokenMint);
  }

  public static Filter createTokenMintDecimalsFilter(final long tokenMintDecimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenMintDecimals);
    return Filter.createMemCompFilter(TOKEN_MINT_DECIMALS_OFFSET, _data);
  }

  public static Filter createTokenVaultFilter(final PublicKey tokenVault) {
    return Filter.createMemCompFilter(TOKEN_VAULT_OFFSET, tokenVault);
  }

  public static Filter createTokenProgramFilter(final PublicKey tokenProgram) {
    return Filter.createMemCompFilter(TOKEN_PROGRAM_OFFSET, tokenProgram);
  }

  public static Filter createSharesMintFilter(final PublicKey sharesMint) {
    return Filter.createMemCompFilter(SHARES_MINT_OFFSET, sharesMint);
  }

  public static Filter createSharesMintDecimalsFilter(final long sharesMintDecimals) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sharesMintDecimals);
    return Filter.createMemCompFilter(SHARES_MINT_DECIMALS_OFFSET, _data);
  }

  public static Filter createTokenAvailableFilter(final long tokenAvailable) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenAvailable);
    return Filter.createMemCompFilter(TOKEN_AVAILABLE_OFFSET, _data);
  }

  public static Filter createSharesIssuedFilter(final long sharesIssued) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, sharesIssued);
    return Filter.createMemCompFilter(SHARES_ISSUED_OFFSET, _data);
  }

  public static Filter createAvailableCrankFundsFilter(final long availableCrankFunds) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, availableCrankFunds);
    return Filter.createMemCompFilter(AVAILABLE_CRANK_FUNDS_OFFSET, _data);
  }

  public static Filter createUnallocatedWeightFilter(final long unallocatedWeight) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unallocatedWeight);
    return Filter.createMemCompFilter(UNALLOCATED_WEIGHT_OFFSET, _data);
  }

  public static Filter createPerformanceFeeBpsFilter(final long performanceFeeBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, performanceFeeBps);
    return Filter.createMemCompFilter(PERFORMANCE_FEE_BPS_OFFSET, _data);
  }

  public static Filter createManagementFeeBpsFilter(final long managementFeeBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, managementFeeBps);
    return Filter.createMemCompFilter(MANAGEMENT_FEE_BPS_OFFSET, _data);
  }

  public static Filter createLastFeeChargeTimestampFilter(final long lastFeeChargeTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastFeeChargeTimestamp);
    return Filter.createMemCompFilter(LAST_FEE_CHARGE_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createPrevAumSfFilter(final BigInteger prevAumSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, prevAumSf);
    return Filter.createMemCompFilter(PREV_AUM_SF_OFFSET, _data);
  }

  public static Filter createPendingFeesSfFilter(final BigInteger pendingFeesSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, pendingFeesSf);
    return Filter.createMemCompFilter(PENDING_FEES_SF_OFFSET, _data);
  }

  public static Filter createMinDepositAmountFilter(final long minDepositAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minDepositAmount);
    return Filter.createMemCompFilter(MIN_DEPOSIT_AMOUNT_OFFSET, _data);
  }

  public static Filter createMinWithdrawAmountFilter(final long minWithdrawAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minWithdrawAmount);
    return Filter.createMemCompFilter(MIN_WITHDRAW_AMOUNT_OFFSET, _data);
  }

  public static Filter createMinInvestAmountFilter(final long minInvestAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minInvestAmount);
    return Filter.createMemCompFilter(MIN_INVEST_AMOUNT_OFFSET, _data);
  }

  public static Filter createMinInvestDelaySlotsFilter(final long minInvestDelaySlots) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minInvestDelaySlots);
    return Filter.createMemCompFilter(MIN_INVEST_DELAY_SLOTS_OFFSET, _data);
  }

  public static Filter createCrankFundFeePerReserveFilter(final long crankFundFeePerReserve) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, crankFundFeePerReserve);
    return Filter.createMemCompFilter(CRANK_FUND_FEE_PER_RESERVE_OFFSET, _data);
  }

  public static Filter createPendingAdminFilter(final PublicKey pendingAdmin) {
    return Filter.createMemCompFilter(PENDING_ADMIN_OFFSET, pendingAdmin);
  }

  public static Filter createCumulativeEarnedInterestSfFilter(final BigInteger cumulativeEarnedInterestSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeEarnedInterestSf);
    return Filter.createMemCompFilter(CUMULATIVE_EARNED_INTEREST_SF_OFFSET, _data);
  }

  public static Filter createCumulativeMgmtFeesSfFilter(final BigInteger cumulativeMgmtFeesSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeMgmtFeesSf);
    return Filter.createMemCompFilter(CUMULATIVE_MGMT_FEES_SF_OFFSET, _data);
  }

  public static Filter createCumulativePerfFeesSfFilter(final BigInteger cumulativePerfFeesSf) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativePerfFeesSf);
    return Filter.createMemCompFilter(CUMULATIVE_PERF_FEES_SF_OFFSET, _data);
  }

  public static Filter createVaultLookupTableFilter(final PublicKey vaultLookupTable) {
    return Filter.createMemCompFilter(VAULT_LOOKUP_TABLE_OFFSET, vaultLookupTable);
  }

  public static Filter createVaultFarmFilter(final PublicKey vaultFarm) {
    return Filter.createMemCompFilter(VAULT_FARM_OFFSET, vaultFarm);
  }

  public static Filter createCreationTimestampFilter(final long creationTimestamp) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, creationTimestamp);
    return Filter.createMemCompFilter(CREATION_TIMESTAMP_OFFSET, _data);
  }

  public static Filter createUnallocatedTokensCapFilter(final long unallocatedTokensCap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unallocatedTokensCap);
    return Filter.createMemCompFilter(UNALLOCATED_TOKENS_CAP_OFFSET, _data);
  }

  public static Filter createAllocationAdminFilter(final PublicKey allocationAdmin) {
    return Filter.createMemCompFilter(ALLOCATION_ADMIN_OFFSET, allocationAdmin);
  }

  public static VaultState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static VaultState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static VaultState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], VaultState> FACTORY = VaultState::read;

  public static VaultState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vaultAdminAuthority = readPubKey(_data, i);
    i += 32;
    final var baseVaultAuthority = readPubKey(_data, i);
    i += 32;
    final var baseVaultAuthorityBump = getInt64LE(_data, i);
    i += 8;
    final var tokenMint = readPubKey(_data, i);
    i += 32;
    final var tokenMintDecimals = getInt64LE(_data, i);
    i += 8;
    final var tokenVault = readPubKey(_data, i);
    i += 32;
    final var tokenProgram = readPubKey(_data, i);
    i += 32;
    final var sharesMint = readPubKey(_data, i);
    i += 32;
    final var sharesMintDecimals = getInt64LE(_data, i);
    i += 8;
    final var tokenAvailable = getInt64LE(_data, i);
    i += 8;
    final var sharesIssued = getInt64LE(_data, i);
    i += 8;
    final var availableCrankFunds = getInt64LE(_data, i);
    i += 8;
    final var unallocatedWeight = getInt64LE(_data, i);
    i += 8;
    final var performanceFeeBps = getInt64LE(_data, i);
    i += 8;
    final var managementFeeBps = getInt64LE(_data, i);
    i += 8;
    final var lastFeeChargeTimestamp = getInt64LE(_data, i);
    i += 8;
    final var prevAumSf = getInt128LE(_data, i);
    i += 16;
    final var pendingFeesSf = getInt128LE(_data, i);
    i += 16;
    final var vaultAllocationStrategy = new VaultAllocation[25];
    i += Borsh.readArray(vaultAllocationStrategy, VaultAllocation::read, _data, i);
    final var padding1 = new BigInteger[256];
    i += Borsh.read128Array(padding1, _data, i);
    final var minDepositAmount = getInt64LE(_data, i);
    i += 8;
    final var minWithdrawAmount = getInt64LE(_data, i);
    i += 8;
    final var minInvestAmount = getInt64LE(_data, i);
    i += 8;
    final var minInvestDelaySlots = getInt64LE(_data, i);
    i += 8;
    final var crankFundFeePerReserve = getInt64LE(_data, i);
    i += 8;
    final var pendingAdmin = readPubKey(_data, i);
    i += 32;
    final var cumulativeEarnedInterestSf = getInt128LE(_data, i);
    i += 16;
    final var cumulativeMgmtFeesSf = getInt128LE(_data, i);
    i += 16;
    final var cumulativePerfFeesSf = getInt128LE(_data, i);
    i += 16;
    final var name = new byte[40];
    i += Borsh.readArray(name, _data, i);
    final var vaultLookupTable = readPubKey(_data, i);
    i += 32;
    final var vaultFarm = readPubKey(_data, i);
    i += 32;
    final var creationTimestamp = getInt64LE(_data, i);
    i += 8;
    final var unallocatedTokensCap = getInt64LE(_data, i);
    i += 8;
    final var allocationAdmin = readPubKey(_data, i);
    i += 32;
    final var padding3 = new BigInteger[242];
    Borsh.read128Array(padding3, _data, i);
    return new VaultState(_address,
                          discriminator,
                          vaultAdminAuthority,
                          baseVaultAuthority,
                          baseVaultAuthorityBump,
                          tokenMint,
                          tokenMintDecimals,
                          tokenVault,
                          tokenProgram,
                          sharesMint,
                          sharesMintDecimals,
                          tokenAvailable,
                          sharesIssued,
                          availableCrankFunds,
                          unallocatedWeight,
                          performanceFeeBps,
                          managementFeeBps,
                          lastFeeChargeTimestamp,
                          prevAumSf,
                          pendingFeesSf,
                          vaultAllocationStrategy,
                          padding1,
                          minDepositAmount,
                          minWithdrawAmount,
                          minInvestAmount,
                          minInvestDelaySlots,
                          crankFundFeePerReserve,
                          pendingAdmin,
                          cumulativeEarnedInterestSf,
                          cumulativeMgmtFeesSf,
                          cumulativePerfFeesSf,
                          name,
                          vaultLookupTable,
                          vaultFarm,
                          creationTimestamp,
                          unallocatedTokensCap,
                          allocationAdmin,
                          padding3);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vaultAdminAuthority.write(_data, i);
    i += 32;
    baseVaultAuthority.write(_data, i);
    i += 32;
    putInt64LE(_data, i, baseVaultAuthorityBump);
    i += 8;
    tokenMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, tokenMintDecimals);
    i += 8;
    tokenVault.write(_data, i);
    i += 32;
    tokenProgram.write(_data, i);
    i += 32;
    sharesMint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, sharesMintDecimals);
    i += 8;
    putInt64LE(_data, i, tokenAvailable);
    i += 8;
    putInt64LE(_data, i, sharesIssued);
    i += 8;
    putInt64LE(_data, i, availableCrankFunds);
    i += 8;
    putInt64LE(_data, i, unallocatedWeight);
    i += 8;
    putInt64LE(_data, i, performanceFeeBps);
    i += 8;
    putInt64LE(_data, i, managementFeeBps);
    i += 8;
    putInt64LE(_data, i, lastFeeChargeTimestamp);
    i += 8;
    putInt128LE(_data, i, prevAumSf);
    i += 16;
    putInt128LE(_data, i, pendingFeesSf);
    i += 16;
    i += Borsh.writeArrayChecked(vaultAllocationStrategy, 25, _data, i);
    i += Borsh.write128ArrayChecked(padding1, 256, _data, i);
    putInt64LE(_data, i, minDepositAmount);
    i += 8;
    putInt64LE(_data, i, minWithdrawAmount);
    i += 8;
    putInt64LE(_data, i, minInvestAmount);
    i += 8;
    putInt64LE(_data, i, minInvestDelaySlots);
    i += 8;
    putInt64LE(_data, i, crankFundFeePerReserve);
    i += 8;
    pendingAdmin.write(_data, i);
    i += 32;
    putInt128LE(_data, i, cumulativeEarnedInterestSf);
    i += 16;
    putInt128LE(_data, i, cumulativeMgmtFeesSf);
    i += 16;
    putInt128LE(_data, i, cumulativePerfFeesSf);
    i += 16;
    i += Borsh.writeArrayChecked(name, 40, _data, i);
    vaultLookupTable.write(_data, i);
    i += 32;
    vaultFarm.write(_data, i);
    i += 32;
    putInt64LE(_data, i, creationTimestamp);
    i += 8;
    putInt64LE(_data, i, unallocatedTokensCap);
    i += 8;
    allocationAdmin.write(_data, i);
    i += 32;
    i += Borsh.write128ArrayChecked(padding3, 242, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
