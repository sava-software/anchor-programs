package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SpotMarket(PublicKey _address,
                         byte[] discriminator,
                         // The address of the spot market. It is a pda of the market index
                         PublicKey pubkey,
                         // The oracle used to price the markets deposits/borrows
                         PublicKey oracle,
                         // The token mint of the market
                         PublicKey mint,
                         // The vault used to store the market's deposits
                         // The amount in the vault should be equal to or greater than deposits - borrows
                         PublicKey vault,
                         // The encoded display name for the market e.g. SOL
                         int[] name,
                         HistoricalOracleData historicalOracleData,
                         HistoricalIndexData historicalIndexData,
                         // Revenue the protocol has collected in this markets token
                         // e.g. for SOL-PERP, funds can be settled in usdc and will flow into the USDC revenue pool
                         PoolBalance revenuePool,
                         // The fees collected from swaps between this market and the quote market
                         // Is settled to the quote markets revenue pool
                         PoolBalance spotFeePool,
                         // Details on the insurance fund covering bankruptcies in this markets token
                         // Covers bankruptcies for borrows with this markets token and perps settling in this markets token
                         InsuranceFund insuranceFund,
                         // The total spot fees collected for this market
                         // precision: QUOTE_PRECISION
                         BigInteger totalSpotFee,
                         // The sum of the scaled balances for deposits across users and pool balances
                         // To convert to the deposit token amount, multiply by the cumulative deposit interest
                         // precision: SPOT_BALANCE_PRECISION
                         BigInteger depositBalance,
                         // The sum of the scaled balances for borrows across users and pool balances
                         // To convert to the borrow token amount, multiply by the cumulative borrow interest
                         // precision: SPOT_BALANCE_PRECISION
                         BigInteger borrowBalance,
                         // The cumulative interest earned by depositors
                         // Used to calculate the deposit token amount from the deposit balance
                         // precision: SPOT_CUMULATIVE_INTEREST_PRECISION
                         BigInteger cumulativeDepositInterest,
                         // The cumulative interest earned by borrowers
                         // Used to calculate the borrow token amount from the borrow balance
                         // precision: SPOT_CUMULATIVE_INTEREST_PRECISION
                         BigInteger cumulativeBorrowInterest,
                         // The total socialized loss from borrows, in the mint's token
                         // precision: token mint precision
                         BigInteger totalSocialLoss,
                         // The total socialized loss from borrows, in the quote market's token
                         // preicision: QUOTE_PRECISION
                         BigInteger totalQuoteSocialLoss,
                         // no withdraw limits/guards when deposits below this threshold
                         // precision: token mint precision
                         long withdrawGuardThreshold,
                         // The max amount of token deposits in this market
                         // 0 if there is no limit
                         // precision: token mint precision
                         long maxTokenDeposits,
                         // 24hr average of deposit token amount
                         // precision: token mint precision
                         long depositTokenTwap,
                         // 24hr average of borrow token amount
                         // precision: token mint precision
                         long borrowTokenTwap,
                         // 24hr average of utilization
                         // which is borrow amount over token amount
                         // precision: SPOT_UTILIZATION_PRECISION
                         long utilizationTwap,
                         // Last time the cumulative deposit and borrow interest was updated
                         long lastInterestTs,
                         // Last time the deposit/borrow/utilization averages were updated
                         long lastTwapTs,
                         // The time the market is set to expire. Only set if market is in reduce only mode
                         long expiryTs,
                         // Spot orders must be a multiple of the step size
                         // precision: token mint precision
                         long orderStepSize,
                         // Spot orders must be a multiple of the tick size
                         // precision: PRICE_PRECISION
                         long orderTickSize,
                         // The minimum order size
                         // precision: token mint precision
                         long minOrderSize,
                         // The maximum spot position size
                         // if the limit is 0, there is no limit
                         // precision: token mint precision
                         long maxPositionSize,
                         // Every spot trade has a fill record id. This is the next id to use
                         long nextFillRecordId,
                         // Every deposit has a deposit record id. This is the next id to use
                         long nextDepositRecordId,
                         // The initial asset weight used to calculate a deposits contribution to a users initial total collateral
                         // e.g. if the asset weight is .8, $100 of deposits contributes $80 to the users initial total collateral
                         // precision: SPOT_WEIGHT_PRECISION
                         int initialAssetWeight,
                         // The maintenance asset weight used to calculate a deposits contribution to a users maintenance total collateral
                         // e.g. if the asset weight is .9, $100 of deposits contributes $90 to the users maintenance total collateral
                         // precision: SPOT_WEIGHT_PRECISION
                         int maintenanceAssetWeight,
                         // The initial liability weight used to calculate a borrows contribution to a users initial margin requirement
                         // e.g. if the liability weight is .9, $100 of borrows contributes $90 to the users initial margin requirement
                         // precision: SPOT_WEIGHT_PRECISION
                         int initialLiabilityWeight,
                         // The maintenance liability weight used to calculate a borrows contribution to a users maintenance margin requirement
                         // e.g. if the liability weight is .8, $100 of borrows contributes $80 to the users maintenance margin requirement
                         // precision: SPOT_WEIGHT_PRECISION
                         int maintenanceLiabilityWeight,
                         // The initial margin fraction factor. Used to increase liability weight/decrease asset weight for large positions
                         // precision: MARGIN_PRECISION
                         int imfFactor,
                         // The fee the liquidator is paid for taking over borrow/deposit
                         // precision: LIQUIDATOR_FEE_PRECISION
                         int liquidatorFee,
                         // The fee the insurance fund receives from liquidation
                         // precision: LIQUIDATOR_FEE_PRECISION
                         int ifLiquidationFee,
                         // The optimal utilization rate for this market.
                         // Used to determine the markets borrow rate
                         // precision: SPOT_UTILIZATION_PRECISION
                         int optimalUtilization,
                         // The borrow rate for this market when the market has optimal utilization
                         // precision: SPOT_RATE_PRECISION
                         int optimalBorrowRate,
                         // The borrow rate for this market when the market has 1000 utilization
                         // precision: SPOT_RATE_PRECISION
                         int maxBorrowRate,
                         // The market's token mint's decimals. To from decimals to a precision, 10^decimals
                         int decimals,
                         int marketIndex,
                         // Whether or not spot trading is enabled
                         boolean ordersEnabled,
                         OracleSource oracleSource,
                         MarketStatus status,
                         // The asset tier affects how a deposit can be used as collateral and the priority for a borrow being liquidated
                         AssetTier assetTier,
                         int pausedOperations,
                         int ifPausedOperations,
                         int feeAdjustment,
                         // What fraction of max_token_deposits
                         // disabled when 0, 1 => 1/10000 => .01% of max_token_deposits
                         // precision: X/10000
                         int maxTokenBorrowsFraction,
                         // For swaps, the amount of token loaned out in the begin_swap ix
                         // precision: token mint precision
                         long flashLoanAmount,
                         // For swaps, the amount in the users token account in the begin_swap ix
                         // Used to calculate how much of the token left the system in end_swap ix
                         // precision: token mint precision
                         long flashLoanInitialTokenAmount,
                         // The total fees received from swaps
                         // precision: token mint precision
                         long totalSwapFee,
                         // When to begin scaling down the initial asset weight
                         // disabled when 0
                         // precision: QUOTE_PRECISION
                         long scaleInitialAssetWeightStart,
                         // The min borrow rate for this market when the market regardless of utilization
                         // 1 => 1/200 => .5%
                         // precision: X/200
                         int minBorrowRate,
                         int[] padding) implements Borsh {

  public static final int PUBKEY_OFFSET = 8;
  public static final int ORACLE_OFFSET = 40;
  public static final int MINT_OFFSET = 72;
  public static final int VAULT_OFFSET = 104;
  public static final int NAME_OFFSET = 136;
  public static final int HISTORICAL_ORACLE_DATA_OFFSET = 168;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createOracleFilter(final PublicKey oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createHistoricalOracleDataFilter(final HistoricalOracleData historicalOracleData) {
    return Filter.createMemCompFilter(HISTORICAL_ORACLE_DATA_OFFSET, historicalOracleData.write());
  }

  public static SpotMarket read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static SpotMarket read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], SpotMarket> FACTORY = SpotMarket::read;

  public static SpotMarket read(final PublicKey _address, final byte[] _data, final int offset) {
    final byte[] discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var name = Borsh.readArray(new int[32], _data, i);
    i += Borsh.fixedLen(name);
    final var historicalOracleData = HistoricalOracleData.read(_data, i);
    i += Borsh.len(historicalOracleData);
    final var historicalIndexData = HistoricalIndexData.read(_data, i);
    i += Borsh.len(historicalIndexData);
    final var revenuePool = PoolBalance.read(_data, i);
    i += Borsh.len(revenuePool);
    final var spotFeePool = PoolBalance.read(_data, i);
    i += Borsh.len(spotFeePool);
    final var insuranceFund = InsuranceFund.read(_data, i);
    i += Borsh.len(insuranceFund);
    final var totalSpotFee = getInt128LE(_data, i);
    i += 16;
    final var depositBalance = getInt128LE(_data, i);
    i += 16;
    final var borrowBalance = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDepositInterest = getInt128LE(_data, i);
    i += 16;
    final var cumulativeBorrowInterest = getInt128LE(_data, i);
    i += 16;
    final var totalSocialLoss = getInt128LE(_data, i);
    i += 16;
    final var totalQuoteSocialLoss = getInt128LE(_data, i);
    i += 16;
    final var withdrawGuardThreshold = getInt64LE(_data, i);
    i += 8;
    final var maxTokenDeposits = getInt64LE(_data, i);
    i += 8;
    final var depositTokenTwap = getInt64LE(_data, i);
    i += 8;
    final var borrowTokenTwap = getInt64LE(_data, i);
    i += 8;
    final var utilizationTwap = getInt64LE(_data, i);
    i += 8;
    final var lastInterestTs = getInt64LE(_data, i);
    i += 8;
    final var lastTwapTs = getInt64LE(_data, i);
    i += 8;
    final var expiryTs = getInt64LE(_data, i);
    i += 8;
    final var orderStepSize = getInt64LE(_data, i);
    i += 8;
    final var orderTickSize = getInt64LE(_data, i);
    i += 8;
    final var minOrderSize = getInt64LE(_data, i);
    i += 8;
    final var maxPositionSize = getInt64LE(_data, i);
    i += 8;
    final var nextFillRecordId = getInt64LE(_data, i);
    i += 8;
    final var nextDepositRecordId = getInt64LE(_data, i);
    i += 8;
    final var initialAssetWeight = getInt32LE(_data, i);
    i += 4;
    final var maintenanceAssetWeight = getInt32LE(_data, i);
    i += 4;
    final var initialLiabilityWeight = getInt32LE(_data, i);
    i += 4;
    final var maintenanceLiabilityWeight = getInt32LE(_data, i);
    i += 4;
    final var imfFactor = getInt32LE(_data, i);
    i += 4;
    final var liquidatorFee = getInt32LE(_data, i);
    i += 4;
    final var ifLiquidationFee = getInt32LE(_data, i);
    i += 4;
    final var optimalUtilization = getInt32LE(_data, i);
    i += 4;
    final var optimalBorrowRate = getInt32LE(_data, i);
    i += 4;
    final var maxBorrowRate = getInt32LE(_data, i);
    i += 4;
    final var decimals = getInt32LE(_data, i);
    i += 4;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var ordersEnabled = _data[i] == 1;
    ++i;
    final var oracleSource = OracleSource.read(_data, i);
    i += Borsh.len(oracleSource);
    final var status = MarketStatus.read(_data, i);
    i += Borsh.len(status);
    final var assetTier = AssetTier.read(_data, i);
    i += Borsh.len(assetTier);
    final var pausedOperations = _data[i] & 0xFF;
    ++i;
    final var ifPausedOperations = _data[i] & 0xFF;
    ++i;
    final var feeAdjustment = getInt16LE(_data, i);
    i += 2;
    final var maxTokenBorrowsFraction = getInt16LE(_data, i);
    i += 2;
    final var flashLoanAmount = getInt64LE(_data, i);
    i += 8;
    final var flashLoanInitialTokenAmount = getInt64LE(_data, i);
    i += 8;
    final var totalSwapFee = getInt64LE(_data, i);
    i += 8;
    final var scaleInitialAssetWeightStart = getInt64LE(_data, i);
    i += 8;
    final var minBorrowRate = _data[i] & 0xFF;
    ++i;
    final var padding = Borsh.readArray(new int[47], _data, i);
    return new SpotMarket(_address,
                          discriminator,
                          pubkey,
                          oracle,
                          mint,
                          vault,
                          name,
                          historicalOracleData,
                          historicalIndexData,
                          revenuePool,
                          spotFeePool,
                          insuranceFund,
                          totalSpotFee,
                          depositBalance,
                          borrowBalance,
                          cumulativeDepositInterest,
                          cumulativeBorrowInterest,
                          totalSocialLoss,
                          totalQuoteSocialLoss,
                          withdrawGuardThreshold,
                          maxTokenDeposits,
                          depositTokenTwap,
                          borrowTokenTwap,
                          utilizationTwap,
                          lastInterestTs,
                          lastTwapTs,
                          expiryTs,
                          orderStepSize,
                          orderTickSize,
                          minOrderSize,
                          maxPositionSize,
                          nextFillRecordId,
                          nextDepositRecordId,
                          initialAssetWeight,
                          maintenanceAssetWeight,
                          initialLiabilityWeight,
                          maintenanceLiabilityWeight,
                          imfFactor,
                          liquidatorFee,
                          ifLiquidationFee,
                          optimalUtilization,
                          optimalBorrowRate,
                          maxBorrowRate,
                          decimals,
                          marketIndex,
                          ordersEnabled,
                          oracleSource,
                          status,
                          assetTier,
                          pausedOperations,
                          ifPausedOperations,
                          feeAdjustment,
                          maxTokenBorrowsFraction,
                          flashLoanAmount,
                          flashLoanInitialTokenAmount,
                          totalSwapFee,
                          scaleInitialAssetWeightStart,
                          minBorrowRate,
                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    System.arraycopy(discriminator, 0, _data, offset, discriminator.length);
    int i = offset + discriminator.length;
    pubkey.write(_data, i);
    i += 32;
    oracle.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    i += Borsh.fixedWrite(name, _data, i);
    i += Borsh.write(historicalOracleData, _data, i);
    i += Borsh.write(historicalIndexData, _data, i);
    i += Borsh.write(revenuePool, _data, i);
    i += Borsh.write(spotFeePool, _data, i);
    i += Borsh.write(insuranceFund, _data, i);
    putInt128LE(_data, i, totalSpotFee);
    i += 16;
    putInt128LE(_data, i, depositBalance);
    i += 16;
    putInt128LE(_data, i, borrowBalance);
    i += 16;
    putInt128LE(_data, i, cumulativeDepositInterest);
    i += 16;
    putInt128LE(_data, i, cumulativeBorrowInterest);
    i += 16;
    putInt128LE(_data, i, totalSocialLoss);
    i += 16;
    putInt128LE(_data, i, totalQuoteSocialLoss);
    i += 16;
    putInt64LE(_data, i, withdrawGuardThreshold);
    i += 8;
    putInt64LE(_data, i, maxTokenDeposits);
    i += 8;
    putInt64LE(_data, i, depositTokenTwap);
    i += 8;
    putInt64LE(_data, i, borrowTokenTwap);
    i += 8;
    putInt64LE(_data, i, utilizationTwap);
    i += 8;
    putInt64LE(_data, i, lastInterestTs);
    i += 8;
    putInt64LE(_data, i, lastTwapTs);
    i += 8;
    putInt64LE(_data, i, expiryTs);
    i += 8;
    putInt64LE(_data, i, orderStepSize);
    i += 8;
    putInt64LE(_data, i, orderTickSize);
    i += 8;
    putInt64LE(_data, i, minOrderSize);
    i += 8;
    putInt64LE(_data, i, maxPositionSize);
    i += 8;
    putInt64LE(_data, i, nextFillRecordId);
    i += 8;
    putInt64LE(_data, i, nextDepositRecordId);
    i += 8;
    putInt32LE(_data, i, initialAssetWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceAssetWeight);
    i += 4;
    putInt32LE(_data, i, initialLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, maintenanceLiabilityWeight);
    i += 4;
    putInt32LE(_data, i, imfFactor);
    i += 4;
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);
    i += 4;
    putInt32LE(_data, i, optimalUtilization);
    i += 4;
    putInt32LE(_data, i, optimalBorrowRate);
    i += 4;
    putInt32LE(_data, i, maxBorrowRate);
    i += 4;
    putInt32LE(_data, i, decimals);
    i += 4;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    _data[i] = (byte) (ordersEnabled ? 1 : 0);
    ++i;
    i += Borsh.write(oracleSource, _data, i);
    i += Borsh.write(status, _data, i);
    i += Borsh.write(assetTier, _data, i);
    _data[i] = (byte) pausedOperations;
    ++i;
    _data[i] = (byte) ifPausedOperations;
    ++i;
    putInt16LE(_data, i, feeAdjustment);
    i += 2;
    putInt16LE(_data, i, maxTokenBorrowsFraction);
    i += 2;
    putInt64LE(_data, i, flashLoanAmount);
    i += 8;
    putInt64LE(_data, i, flashLoanInitialTokenAmount);
    i += 8;
    putInt64LE(_data, i, totalSwapFee);
    i += 8;
    putInt64LE(_data, i, scaleInitialAssetWeightStart);
    i += 8;
    _data[i] = (byte) minBorrowRate;
    ++i;
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + 32
         + 32
         + 32
         + Borsh.fixedLen(name)
         + Borsh.len(historicalOracleData)
         + Borsh.len(historicalIndexData)
         + Borsh.len(revenuePool)
         + Borsh.len(spotFeePool)
         + Borsh.len(insuranceFund)
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 16
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 4
         + 2
         + 1
         + Borsh.len(oracleSource)
         + Borsh.len(status)
         + Borsh.len(assetTier)
         + 1
         + 1
         + 2
         + 2
         + 8
         + 8
         + 8
         + 8
         + 1
         + Borsh.fixedLen(padding);
  }
}
