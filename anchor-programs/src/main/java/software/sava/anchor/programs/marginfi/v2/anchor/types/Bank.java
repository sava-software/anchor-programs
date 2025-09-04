package software.sava.anchor.programs.marginfi.v2.anchor.types;

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
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Bank(PublicKey _address,
                   Discriminator discriminator,
                   PublicKey mint,
                   int mintDecimals,
                   PublicKey group,
                   byte[] pad0,
                   WrappedI80F48 assetShareValue,
                   WrappedI80F48 liabilityShareValue,
                   PublicKey liquidityVault,
                   int liquidityVaultBump,
                   int liquidityVaultAuthorityBump,
                   PublicKey insuranceVault,
                   int insuranceVaultBump,
                   int insuranceVaultAuthorityBump,
                   byte[] pad1,
                   // Fees collected and pending withdraw for the `insurance_vault`
                   WrappedI80F48 collectedInsuranceFeesOutstanding,
                   PublicKey feeVault,
                   int feeVaultBump,
                   int feeVaultAuthorityBump,
                   byte[] pad2,
                   // Fees collected and pending withdraw for the `fee_vault`
                   WrappedI80F48 collectedGroupFeesOutstanding,
                   WrappedI80F48 totalLiabilityShares,
                   WrappedI80F48 totalAssetShares,
                   long lastUpdate,
                   BankConfig config,
                   // Bank Config Flags
                   // 
                   // - EMISSIONS_FLAG_BORROW_ACTIVE: 1
                   // - EMISSIONS_FLAG_LENDING_ACTIVE: 2
                   // - PERMISSIONLESS_BAD_DEBT_SETTLEMENT: 4
                   // - FREEZE_SETTINGS: 8 - banks with this flag enabled can only update deposit/borrow caps
                   // - CLOSE_ENABLED_FLAG - banks with this flag were created after 0.1.4 and can be closed.
                   // Banks without this flag can never be closed.
                   // 
                   long flags,
                   // Emissions APR. Number of emitted tokens (emissions_mint) per 1e(bank.mint_decimal) tokens
                   // (bank mint) (native amount) per 1 YEAR.
                   long emissionsRate,
                   WrappedI80F48 emissionsRemaining,
                   PublicKey emissionsMint,
                   // Fees collected and pending withdraw for the `FeeState.global_fee_wallet`'s canonical ATA for `mint`
                   WrappedI80F48 collectedProgramFeesOutstanding,
                   // Controls this bank's emode configuration, which enables some banks to treat the assets of
                   // certain other banks more preferentially as collateral.
                   EmodeSettings emode,
                   // Set with `update_fees_destination_account`. This should be an ATA for the bank's mint. If
                   // pubkey default, the bank doesn't support this feature, and the fees must be collected
                   // manually (withdraw_fees).
                   PublicKey feesDestinationAccount,
                   BankCache cache,
                   // Number of user lending positions currently open in this bank
                   // * For banks created prior to 0.1.4, this is the number of positions opened/closed after
                   // 0.1.4 goes live, and may be negative.
                   // * For banks created in 0.1.4 or later, this is the number of positions open in total, and
                   // the bank may safely be closed if this is zero. Will never go negative.
                   int lendingPositionCount,
                   // Number of user borrowing positions currently open in this bank
                   // * For banks created prior to 0.1.4, this is the number of positions opened/closed after
                   // 0.1.4 goes live, and may be negative.
                   // * For banks created in 0.1.4 or later, this is the number of positions open in total, and
                   // the bank may safely be closed if this is zero. Will never go negative.
                   int borrowingPositionCount,
                   byte[] padding0,
                   long[][] padding1) implements Borsh {

  public static final int BYTES = 1864;
  public static final int PAD_0_LEN = 7;
  public static final int PAD_1_LEN = 4;
  public static final int PAD_2_LEN = 6;
  public static final int PADDING_0_LEN = 16;
  public static final int PADDING_1_LEN = 19;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(142, 49, 166, 242, 50, 66, 97, 188);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int MINT_OFFSET = 8;
  public static final int MINT_DECIMALS_OFFSET = 40;
  public static final int GROUP_OFFSET = 41;
  public static final int PAD_0_OFFSET = 73;
  public static final int ASSET_SHARE_VALUE_OFFSET = 80;
  public static final int LIABILITY_SHARE_VALUE_OFFSET = 96;
  public static final int LIQUIDITY_VAULT_OFFSET = 112;
  public static final int LIQUIDITY_VAULT_BUMP_OFFSET = 144;
  public static final int LIQUIDITY_VAULT_AUTHORITY_BUMP_OFFSET = 145;
  public static final int INSURANCE_VAULT_OFFSET = 146;
  public static final int INSURANCE_VAULT_BUMP_OFFSET = 178;
  public static final int INSURANCE_VAULT_AUTHORITY_BUMP_OFFSET = 179;
  public static final int PAD_1_OFFSET = 180;
  public static final int COLLECTED_INSURANCE_FEES_OUTSTANDING_OFFSET = 184;
  public static final int FEE_VAULT_OFFSET = 200;
  public static final int FEE_VAULT_BUMP_OFFSET = 232;
  public static final int FEE_VAULT_AUTHORITY_BUMP_OFFSET = 233;
  public static final int PAD_2_OFFSET = 234;
  public static final int COLLECTED_GROUP_FEES_OUTSTANDING_OFFSET = 240;
  public static final int TOTAL_LIABILITY_SHARES_OFFSET = 256;
  public static final int TOTAL_ASSET_SHARES_OFFSET = 272;
  public static final int LAST_UPDATE_OFFSET = 288;
  public static final int CONFIG_OFFSET = 296;
  public static final int FLAGS_OFFSET = 840;
  public static final int EMISSIONS_RATE_OFFSET = 848;
  public static final int EMISSIONS_REMAINING_OFFSET = 856;
  public static final int EMISSIONS_MINT_OFFSET = 872;
  public static final int COLLECTED_PROGRAM_FEES_OUTSTANDING_OFFSET = 904;
  public static final int EMODE_OFFSET = 920;
  public static final int FEES_DESTINATION_ACCOUNT_OFFSET = 1344;
  public static final int CACHE_OFFSET = 1376;
  public static final int LENDING_POSITION_COUNT_OFFSET = 1536;
  public static final int BORROWING_POSITION_COUNT_OFFSET = 1540;
  public static final int PADDING_0_OFFSET = 1544;
  public static final int PADDING_1_OFFSET = 1560;

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createMintDecimalsFilter(final int mintDecimals) {
    return Filter.createMemCompFilter(MINT_DECIMALS_OFFSET, new byte[]{(byte) mintDecimals});
  }

  public static Filter createGroupFilter(final PublicKey group) {
    return Filter.createMemCompFilter(GROUP_OFFSET, group);
  }

  public static Filter createAssetShareValueFilter(final WrappedI80F48 assetShareValue) {
    return Filter.createMemCompFilter(ASSET_SHARE_VALUE_OFFSET, assetShareValue.write());
  }

  public static Filter createLiabilityShareValueFilter(final WrappedI80F48 liabilityShareValue) {
    return Filter.createMemCompFilter(LIABILITY_SHARE_VALUE_OFFSET, liabilityShareValue.write());
  }

  public static Filter createLiquidityVaultFilter(final PublicKey liquidityVault) {
    return Filter.createMemCompFilter(LIQUIDITY_VAULT_OFFSET, liquidityVault);
  }

  public static Filter createLiquidityVaultBumpFilter(final int liquidityVaultBump) {
    return Filter.createMemCompFilter(LIQUIDITY_VAULT_BUMP_OFFSET, new byte[]{(byte) liquidityVaultBump});
  }

  public static Filter createLiquidityVaultAuthorityBumpFilter(final int liquidityVaultAuthorityBump) {
    return Filter.createMemCompFilter(LIQUIDITY_VAULT_AUTHORITY_BUMP_OFFSET, new byte[]{(byte) liquidityVaultAuthorityBump});
  }

  public static Filter createInsuranceVaultFilter(final PublicKey insuranceVault) {
    return Filter.createMemCompFilter(INSURANCE_VAULT_OFFSET, insuranceVault);
  }

  public static Filter createInsuranceVaultBumpFilter(final int insuranceVaultBump) {
    return Filter.createMemCompFilter(INSURANCE_VAULT_BUMP_OFFSET, new byte[]{(byte) insuranceVaultBump});
  }

  public static Filter createInsuranceVaultAuthorityBumpFilter(final int insuranceVaultAuthorityBump) {
    return Filter.createMemCompFilter(INSURANCE_VAULT_AUTHORITY_BUMP_OFFSET, new byte[]{(byte) insuranceVaultAuthorityBump});
  }

  public static Filter createCollectedInsuranceFeesOutstandingFilter(final WrappedI80F48 collectedInsuranceFeesOutstanding) {
    return Filter.createMemCompFilter(COLLECTED_INSURANCE_FEES_OUTSTANDING_OFFSET, collectedInsuranceFeesOutstanding.write());
  }

  public static Filter createFeeVaultFilter(final PublicKey feeVault) {
    return Filter.createMemCompFilter(FEE_VAULT_OFFSET, feeVault);
  }

  public static Filter createFeeVaultBumpFilter(final int feeVaultBump) {
    return Filter.createMemCompFilter(FEE_VAULT_BUMP_OFFSET, new byte[]{(byte) feeVaultBump});
  }

  public static Filter createFeeVaultAuthorityBumpFilter(final int feeVaultAuthorityBump) {
    return Filter.createMemCompFilter(FEE_VAULT_AUTHORITY_BUMP_OFFSET, new byte[]{(byte) feeVaultAuthorityBump});
  }

  public static Filter createCollectedGroupFeesOutstandingFilter(final WrappedI80F48 collectedGroupFeesOutstanding) {
    return Filter.createMemCompFilter(COLLECTED_GROUP_FEES_OUTSTANDING_OFFSET, collectedGroupFeesOutstanding.write());
  }

  public static Filter createTotalLiabilitySharesFilter(final WrappedI80F48 totalLiabilityShares) {
    return Filter.createMemCompFilter(TOTAL_LIABILITY_SHARES_OFFSET, totalLiabilityShares.write());
  }

  public static Filter createTotalAssetSharesFilter(final WrappedI80F48 totalAssetShares) {
    return Filter.createMemCompFilter(TOTAL_ASSET_SHARES_OFFSET, totalAssetShares.write());
  }

  public static Filter createLastUpdateFilter(final long lastUpdate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastUpdate);
    return Filter.createMemCompFilter(LAST_UPDATE_OFFSET, _data);
  }

  public static Filter createFlagsFilter(final long flags) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, flags);
    return Filter.createMemCompFilter(FLAGS_OFFSET, _data);
  }

  public static Filter createEmissionsRateFilter(final long emissionsRate) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, emissionsRate);
    return Filter.createMemCompFilter(EMISSIONS_RATE_OFFSET, _data);
  }

  public static Filter createEmissionsRemainingFilter(final WrappedI80F48 emissionsRemaining) {
    return Filter.createMemCompFilter(EMISSIONS_REMAINING_OFFSET, emissionsRemaining.write());
  }

  public static Filter createEmissionsMintFilter(final PublicKey emissionsMint) {
    return Filter.createMemCompFilter(EMISSIONS_MINT_OFFSET, emissionsMint);
  }

  public static Filter createCollectedProgramFeesOutstandingFilter(final WrappedI80F48 collectedProgramFeesOutstanding) {
    return Filter.createMemCompFilter(COLLECTED_PROGRAM_FEES_OUTSTANDING_OFFSET, collectedProgramFeesOutstanding.write());
  }

  public static Filter createFeesDestinationAccountFilter(final PublicKey feesDestinationAccount) {
    return Filter.createMemCompFilter(FEES_DESTINATION_ACCOUNT_OFFSET, feesDestinationAccount);
  }

  public static Filter createLendingPositionCountFilter(final int lendingPositionCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lendingPositionCount);
    return Filter.createMemCompFilter(LENDING_POSITION_COUNT_OFFSET, _data);
  }

  public static Filter createBorrowingPositionCountFilter(final int borrowingPositionCount) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, borrowingPositionCount);
    return Filter.createMemCompFilter(BORROWING_POSITION_COUNT_OFFSET, _data);
  }

  public static Bank read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Bank read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Bank read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Bank> FACTORY = Bank::read;

  public static Bank read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var mint = readPubKey(_data, i);
    i += 32;
    final var mintDecimals = _data[i] & 0xFF;
    ++i;
    final var group = readPubKey(_data, i);
    i += 32;
    final var pad0 = new byte[7];
    i += Borsh.readArray(pad0, _data, i);
    final var assetShareValue = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetShareValue);
    final var liabilityShareValue = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityShareValue);
    final var liquidityVault = readPubKey(_data, i);
    i += 32;
    final var liquidityVaultBump = _data[i] & 0xFF;
    ++i;
    final var liquidityVaultAuthorityBump = _data[i] & 0xFF;
    ++i;
    final var insuranceVault = readPubKey(_data, i);
    i += 32;
    final var insuranceVaultBump = _data[i] & 0xFF;
    ++i;
    final var insuranceVaultAuthorityBump = _data[i] & 0xFF;
    ++i;
    final var pad1 = new byte[4];
    i += Borsh.readArray(pad1, _data, i);
    final var collectedInsuranceFeesOutstanding = WrappedI80F48.read(_data, i);
    i += Borsh.len(collectedInsuranceFeesOutstanding);
    final var feeVault = readPubKey(_data, i);
    i += 32;
    final var feeVaultBump = _data[i] & 0xFF;
    ++i;
    final var feeVaultAuthorityBump = _data[i] & 0xFF;
    ++i;
    final var pad2 = new byte[6];
    i += Borsh.readArray(pad2, _data, i);
    final var collectedGroupFeesOutstanding = WrappedI80F48.read(_data, i);
    i += Borsh.len(collectedGroupFeesOutstanding);
    final var totalLiabilityShares = WrappedI80F48.read(_data, i);
    i += Borsh.len(totalLiabilityShares);
    final var totalAssetShares = WrappedI80F48.read(_data, i);
    i += Borsh.len(totalAssetShares);
    final var lastUpdate = getInt64LE(_data, i);
    i += 8;
    final var config = BankConfig.read(_data, i);
    i += Borsh.len(config);
    final var flags = getInt64LE(_data, i);
    i += 8;
    final var emissionsRate = getInt64LE(_data, i);
    i += 8;
    final var emissionsRemaining = WrappedI80F48.read(_data, i);
    i += Borsh.len(emissionsRemaining);
    final var emissionsMint = readPubKey(_data, i);
    i += 32;
    final var collectedProgramFeesOutstanding = WrappedI80F48.read(_data, i);
    i += Borsh.len(collectedProgramFeesOutstanding);
    final var emode = EmodeSettings.read(_data, i);
    i += Borsh.len(emode);
    final var feesDestinationAccount = readPubKey(_data, i);
    i += 32;
    final var cache = BankCache.read(_data, i);
    i += Borsh.len(cache);
    final var lendingPositionCount = getInt32LE(_data, i);
    i += 4;
    final var borrowingPositionCount = getInt32LE(_data, i);
    i += 4;
    final var padding0 = new byte[16];
    i += Borsh.readArray(padding0, _data, i);
    final var padding1 = new long[19][2];
    Borsh.readArray(padding1, _data, i);
    return new Bank(_address,
                    discriminator,
                    mint,
                    mintDecimals,
                    group,
                    pad0,
                    assetShareValue,
                    liabilityShareValue,
                    liquidityVault,
                    liquidityVaultBump,
                    liquidityVaultAuthorityBump,
                    insuranceVault,
                    insuranceVaultBump,
                    insuranceVaultAuthorityBump,
                    pad1,
                    collectedInsuranceFeesOutstanding,
                    feeVault,
                    feeVaultBump,
                    feeVaultAuthorityBump,
                    pad2,
                    collectedGroupFeesOutstanding,
                    totalLiabilityShares,
                    totalAssetShares,
                    lastUpdate,
                    config,
                    flags,
                    emissionsRate,
                    emissionsRemaining,
                    emissionsMint,
                    collectedProgramFeesOutstanding,
                    emode,
                    feesDestinationAccount,
                    cache,
                    lendingPositionCount,
                    borrowingPositionCount,
                    padding0,
                    padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    mint.write(_data, i);
    i += 32;
    _data[i] = (byte) mintDecimals;
    ++i;
    group.write(_data, i);
    i += 32;
    i += Borsh.writeArray(pad0, _data, i);
    i += Borsh.write(assetShareValue, _data, i);
    i += Borsh.write(liabilityShareValue, _data, i);
    liquidityVault.write(_data, i);
    i += 32;
    _data[i] = (byte) liquidityVaultBump;
    ++i;
    _data[i] = (byte) liquidityVaultAuthorityBump;
    ++i;
    insuranceVault.write(_data, i);
    i += 32;
    _data[i] = (byte) insuranceVaultBump;
    ++i;
    _data[i] = (byte) insuranceVaultAuthorityBump;
    ++i;
    i += Borsh.writeArray(pad1, _data, i);
    i += Borsh.write(collectedInsuranceFeesOutstanding, _data, i);
    feeVault.write(_data, i);
    i += 32;
    _data[i] = (byte) feeVaultBump;
    ++i;
    _data[i] = (byte) feeVaultAuthorityBump;
    ++i;
    i += Borsh.writeArray(pad2, _data, i);
    i += Borsh.write(collectedGroupFeesOutstanding, _data, i);
    i += Borsh.write(totalLiabilityShares, _data, i);
    i += Borsh.write(totalAssetShares, _data, i);
    putInt64LE(_data, i, lastUpdate);
    i += 8;
    i += Borsh.write(config, _data, i);
    putInt64LE(_data, i, flags);
    i += 8;
    putInt64LE(_data, i, emissionsRate);
    i += 8;
    i += Borsh.write(emissionsRemaining, _data, i);
    emissionsMint.write(_data, i);
    i += 32;
    i += Borsh.write(collectedProgramFeesOutstanding, _data, i);
    i += Borsh.write(emode, _data, i);
    feesDestinationAccount.write(_data, i);
    i += 32;
    i += Borsh.write(cache, _data, i);
    putInt32LE(_data, i, lendingPositionCount);
    i += 4;
    putInt32LE(_data, i, borrowingPositionCount);
    i += 4;
    i += Borsh.writeArray(padding0, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
