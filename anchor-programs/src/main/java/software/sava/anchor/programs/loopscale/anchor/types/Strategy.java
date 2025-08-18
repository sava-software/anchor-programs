package software.sava.anchor.programs.loopscale.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Strategy(PublicKey _address,
                       Discriminator discriminator,
                       int version,
                       PublicKey nonce,
                       int bump,
                       PublicKey principalMint,
                       PublicKey lender,
                       PodBool originationsEnabled,
                       int externalYieldSource,
                       PodDecimal interestPerSecond,
                       // timestamp interest per second's interest was last accrued
                       PodU64 lastAccruedTimestamp,
                       // the is the amount of liquidity % that always needs to be in the strategy
                       PodU64CBPS liquidityBuffer,
                       // amount of principal in the strategy
                       PodU64 tokenBalance,
                       // this is the fee charged by and accrued to the manager on the interest accrued via external yield and loans
                       PodU64CBPS interestFee,
                       // this is the fee charged by and accrued to the manager on the origination fee
                       PodU64CBPS principalFee,
                       // fee charged on origination of new loans
                       PodU64CBPS originationFee,
                       // the maximum size of a loan that can be originated
                       PodU64 originationCap,
                       // this is the amount of principal currently in external yield. has to always be updated on any new nav action
                       PodU64 externalYieldAmount,
                       // this is the amount of principal currently deployed in loans
                       PodU64 currentDeployedAmount,
                       // this is the interest that has not been repaid yet but accrued
                       PodU64 outstandingInterestAmount,
                       // this is the amount that has accrued to the manager
                       PodU64 feeClaimable,
                       PodU128 cumulativePrincipalOriginated,
                       PodU128 cumulativeInterestAccrued,
                       PodU64 cumulativeLoanCount,
                       PodU64 activeLoanCount,
                       PublicKey marketInformation,
                       PodU64[][] collateralMap,
                       ExternalYieldAccounts externalYieldAccounts,
                       CapMonitor supplyMonitor,
                       CapMonitor withdrawMonitor,
                       CapMonitor borrowMonitor) implements Borsh {

  public static final int BYTES = 8460;
  public static final int COLLATERAL_MAP_LEN = 200;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(174, 110, 39, 119, 82, 106, 169, 102);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VERSION_OFFSET = 8;
  public static final int NONCE_OFFSET = 9;
  public static final int BUMP_OFFSET = 41;
  public static final int PRINCIPAL_MINT_OFFSET = 42;
  public static final int LENDER_OFFSET = 74;
  public static final int ORIGINATIONS_ENABLED_OFFSET = 106;
  public static final int EXTERNAL_YIELD_SOURCE_OFFSET = 107;
  public static final int INTEREST_PER_SECOND_OFFSET = 108;
  public static final int LAST_ACCRUED_TIMESTAMP_OFFSET = 132;
  public static final int LIQUIDITY_BUFFER_OFFSET = 140;
  public static final int TOKEN_BALANCE_OFFSET = 148;
  public static final int INTEREST_FEE_OFFSET = 156;
  public static final int PRINCIPAL_FEE_OFFSET = 164;
  public static final int ORIGINATION_FEE_OFFSET = 172;
  public static final int ORIGINATION_CAP_OFFSET = 180;
  public static final int EXTERNAL_YIELD_AMOUNT_OFFSET = 188;
  public static final int CURRENT_DEPLOYED_AMOUNT_OFFSET = 196;
  public static final int OUTSTANDING_INTEREST_AMOUNT_OFFSET = 204;
  public static final int FEE_CLAIMABLE_OFFSET = 212;
  public static final int CUMULATIVE_PRINCIPAL_ORIGINATED_OFFSET = 220;
  public static final int CUMULATIVE_INTEREST_ACCRUED_OFFSET = 236;
  public static final int CUMULATIVE_LOAN_COUNT_OFFSET = 252;
  public static final int ACTIVE_LOAN_COUNT_OFFSET = 260;
  public static final int MARKET_INFORMATION_OFFSET = 268;
  public static final int COLLATERAL_MAP_OFFSET = 300;
  public static final int EXTERNAL_YIELD_ACCOUNTS_OFFSET = 8300;
  public static final int SUPPLY_MONITOR_OFFSET = 8364;
  public static final int WITHDRAW_MONITOR_OFFSET = 8396;
  public static final int BORROW_MONITOR_OFFSET = 8428;

  public static Filter createVersionFilter(final int version) {
    return Filter.createMemCompFilter(VERSION_OFFSET, new byte[]{(byte) version});
  }

  public static Filter createNonceFilter(final PublicKey nonce) {
    return Filter.createMemCompFilter(NONCE_OFFSET, nonce);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createPrincipalMintFilter(final PublicKey principalMint) {
    return Filter.createMemCompFilter(PRINCIPAL_MINT_OFFSET, principalMint);
  }

  public static Filter createLenderFilter(final PublicKey lender) {
    return Filter.createMemCompFilter(LENDER_OFFSET, lender);
  }

  public static Filter createOriginationsEnabledFilter(final PodBool originationsEnabled) {
    return Filter.createMemCompFilter(ORIGINATIONS_ENABLED_OFFSET, originationsEnabled.write());
  }

  public static Filter createExternalYieldSourceFilter(final int externalYieldSource) {
    return Filter.createMemCompFilter(EXTERNAL_YIELD_SOURCE_OFFSET, new byte[]{(byte) externalYieldSource});
  }

  public static Filter createInterestPerSecondFilter(final PodDecimal interestPerSecond) {
    return Filter.createMemCompFilter(INTEREST_PER_SECOND_OFFSET, interestPerSecond.write());
  }

  public static Filter createLastAccruedTimestampFilter(final PodU64 lastAccruedTimestamp) {
    return Filter.createMemCompFilter(LAST_ACCRUED_TIMESTAMP_OFFSET, lastAccruedTimestamp.write());
  }

  public static Filter createLiquidityBufferFilter(final PodU64CBPS liquidityBuffer) {
    return Filter.createMemCompFilter(LIQUIDITY_BUFFER_OFFSET, liquidityBuffer.write());
  }

  public static Filter createTokenBalanceFilter(final PodU64 tokenBalance) {
    return Filter.createMemCompFilter(TOKEN_BALANCE_OFFSET, tokenBalance.write());
  }

  public static Filter createInterestFeeFilter(final PodU64CBPS interestFee) {
    return Filter.createMemCompFilter(INTEREST_FEE_OFFSET, interestFee.write());
  }

  public static Filter createPrincipalFeeFilter(final PodU64CBPS principalFee) {
    return Filter.createMemCompFilter(PRINCIPAL_FEE_OFFSET, principalFee.write());
  }

  public static Filter createOriginationFeeFilter(final PodU64CBPS originationFee) {
    return Filter.createMemCompFilter(ORIGINATION_FEE_OFFSET, originationFee.write());
  }

  public static Filter createOriginationCapFilter(final PodU64 originationCap) {
    return Filter.createMemCompFilter(ORIGINATION_CAP_OFFSET, originationCap.write());
  }

  public static Filter createExternalYieldAmountFilter(final PodU64 externalYieldAmount) {
    return Filter.createMemCompFilter(EXTERNAL_YIELD_AMOUNT_OFFSET, externalYieldAmount.write());
  }

  public static Filter createCurrentDeployedAmountFilter(final PodU64 currentDeployedAmount) {
    return Filter.createMemCompFilter(CURRENT_DEPLOYED_AMOUNT_OFFSET, currentDeployedAmount.write());
  }

  public static Filter createOutstandingInterestAmountFilter(final PodU64 outstandingInterestAmount) {
    return Filter.createMemCompFilter(OUTSTANDING_INTEREST_AMOUNT_OFFSET, outstandingInterestAmount.write());
  }

  public static Filter createFeeClaimableFilter(final PodU64 feeClaimable) {
    return Filter.createMemCompFilter(FEE_CLAIMABLE_OFFSET, feeClaimable.write());
  }

  public static Filter createCumulativePrincipalOriginatedFilter(final PodU128 cumulativePrincipalOriginated) {
    return Filter.createMemCompFilter(CUMULATIVE_PRINCIPAL_ORIGINATED_OFFSET, cumulativePrincipalOriginated.write());
  }

  public static Filter createCumulativeInterestAccruedFilter(final PodU128 cumulativeInterestAccrued) {
    return Filter.createMemCompFilter(CUMULATIVE_INTEREST_ACCRUED_OFFSET, cumulativeInterestAccrued.write());
  }

  public static Filter createCumulativeLoanCountFilter(final PodU64 cumulativeLoanCount) {
    return Filter.createMemCompFilter(CUMULATIVE_LOAN_COUNT_OFFSET, cumulativeLoanCount.write());
  }

  public static Filter createActiveLoanCountFilter(final PodU64 activeLoanCount) {
    return Filter.createMemCompFilter(ACTIVE_LOAN_COUNT_OFFSET, activeLoanCount.write());
  }

  public static Filter createMarketInformationFilter(final PublicKey marketInformation) {
    return Filter.createMemCompFilter(MARKET_INFORMATION_OFFSET, marketInformation);
  }

  public static Filter createExternalYieldAccountsFilter(final ExternalYieldAccounts externalYieldAccounts) {
    return Filter.createMemCompFilter(EXTERNAL_YIELD_ACCOUNTS_OFFSET, externalYieldAccounts.write());
  }

  public static Filter createSupplyMonitorFilter(final CapMonitor supplyMonitor) {
    return Filter.createMemCompFilter(SUPPLY_MONITOR_OFFSET, supplyMonitor.write());
  }

  public static Filter createWithdrawMonitorFilter(final CapMonitor withdrawMonitor) {
    return Filter.createMemCompFilter(WITHDRAW_MONITOR_OFFSET, withdrawMonitor.write());
  }

  public static Filter createBorrowMonitorFilter(final CapMonitor borrowMonitor) {
    return Filter.createMemCompFilter(BORROW_MONITOR_OFFSET, borrowMonitor.write());
  }

  public static Strategy read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Strategy read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Strategy read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Strategy> FACTORY = Strategy::read;

  public static Strategy read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = _data[i] & 0xFF;
    ++i;
    final var nonce = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var principalMint = readPubKey(_data, i);
    i += 32;
    final var lender = readPubKey(_data, i);
    i += 32;
    final var originationsEnabled = PodBool.read(_data, i);
    i += Borsh.len(originationsEnabled);
    final var externalYieldSource = _data[i] & 0xFF;
    ++i;
    final var interestPerSecond = PodDecimal.read(_data, i);
    i += Borsh.len(interestPerSecond);
    final var lastAccruedTimestamp = PodU64.read(_data, i);
    i += Borsh.len(lastAccruedTimestamp);
    final var liquidityBuffer = PodU64CBPS.read(_data, i);
    i += Borsh.len(liquidityBuffer);
    final var tokenBalance = PodU64.read(_data, i);
    i += Borsh.len(tokenBalance);
    final var interestFee = PodU64CBPS.read(_data, i);
    i += Borsh.len(interestFee);
    final var principalFee = PodU64CBPS.read(_data, i);
    i += Borsh.len(principalFee);
    final var originationFee = PodU64CBPS.read(_data, i);
    i += Borsh.len(originationFee);
    final var originationCap = PodU64.read(_data, i);
    i += Borsh.len(originationCap);
    final var externalYieldAmount = PodU64.read(_data, i);
    i += Borsh.len(externalYieldAmount);
    final var currentDeployedAmount = PodU64.read(_data, i);
    i += Borsh.len(currentDeployedAmount);
    final var outstandingInterestAmount = PodU64.read(_data, i);
    i += Borsh.len(outstandingInterestAmount);
    final var feeClaimable = PodU64.read(_data, i);
    i += Borsh.len(feeClaimable);
    final var cumulativePrincipalOriginated = PodU128.read(_data, i);
    i += Borsh.len(cumulativePrincipalOriginated);
    final var cumulativeInterestAccrued = PodU128.read(_data, i);
    i += Borsh.len(cumulativeInterestAccrued);
    final var cumulativeLoanCount = PodU64.read(_data, i);
    i += Borsh.len(cumulativeLoanCount);
    final var activeLoanCount = PodU64.read(_data, i);
    i += Borsh.len(activeLoanCount);
    final var marketInformation = readPubKey(_data, i);
    i += 32;
    final var collateralMap = new PodU64[200][5];
    i += Borsh.readArray(collateralMap, PodU64::read, _data, i);
    final var externalYieldAccounts = ExternalYieldAccounts.read(_data, i);
    i += Borsh.len(externalYieldAccounts);
    final var supplyMonitor = CapMonitor.read(_data, i);
    i += Borsh.len(supplyMonitor);
    final var withdrawMonitor = CapMonitor.read(_data, i);
    i += Borsh.len(withdrawMonitor);
    final var borrowMonitor = CapMonitor.read(_data, i);
    return new Strategy(_address,
                        discriminator,
                        version,
                        nonce,
                        bump,
                        principalMint,
                        lender,
                        originationsEnabled,
                        externalYieldSource,
                        interestPerSecond,
                        lastAccruedTimestamp,
                        liquidityBuffer,
                        tokenBalance,
                        interestFee,
                        principalFee,
                        originationFee,
                        originationCap,
                        externalYieldAmount,
                        currentDeployedAmount,
                        outstandingInterestAmount,
                        feeClaimable,
                        cumulativePrincipalOriginated,
                        cumulativeInterestAccrued,
                        cumulativeLoanCount,
                        activeLoanCount,
                        marketInformation,
                        collateralMap,
                        externalYieldAccounts,
                        supplyMonitor,
                        withdrawMonitor,
                        borrowMonitor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) version;
    ++i;
    nonce.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    principalMint.write(_data, i);
    i += 32;
    lender.write(_data, i);
    i += 32;
    i += Borsh.write(originationsEnabled, _data, i);
    _data[i] = (byte) externalYieldSource;
    ++i;
    i += Borsh.write(interestPerSecond, _data, i);
    i += Borsh.write(lastAccruedTimestamp, _data, i);
    i += Borsh.write(liquidityBuffer, _data, i);
    i += Borsh.write(tokenBalance, _data, i);
    i += Borsh.write(interestFee, _data, i);
    i += Borsh.write(principalFee, _data, i);
    i += Borsh.write(originationFee, _data, i);
    i += Borsh.write(originationCap, _data, i);
    i += Borsh.write(externalYieldAmount, _data, i);
    i += Borsh.write(currentDeployedAmount, _data, i);
    i += Borsh.write(outstandingInterestAmount, _data, i);
    i += Borsh.write(feeClaimable, _data, i);
    i += Borsh.write(cumulativePrincipalOriginated, _data, i);
    i += Borsh.write(cumulativeInterestAccrued, _data, i);
    i += Borsh.write(cumulativeLoanCount, _data, i);
    i += Borsh.write(activeLoanCount, _data, i);
    marketInformation.write(_data, i);
    i += 32;
    i += Borsh.writeArray(collateralMap, _data, i);
    i += Borsh.write(externalYieldAccounts, _data, i);
    i += Borsh.write(supplyMonitor, _data, i);
    i += Borsh.write(withdrawMonitor, _data, i);
    i += Borsh.write(borrowMonitor, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
