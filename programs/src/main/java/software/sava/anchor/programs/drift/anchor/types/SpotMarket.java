package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
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
                         Discriminator discriminator,
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
                         byte[] name,
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
                         // fuel multiplier for spot deposits
                         // precision: 10
                         int fuelBoostDeposits,
                         // fuel multiplier for spot borrows
                         // precision: 10
                         int fuelBoostBorrows,
                         // fuel multiplier for spot taker
                         // precision: 10
                         int fuelBoostTaker,
                         // fuel multiplier for spot maker
                         // precision: 10
                         int fuelBoostMaker,
                         // fuel multiplier for spot insurance stake
                         // precision: 10
                         int fuelBoostInsurance,
                         int tokenProgram,
                         byte[] padding) implements Borsh {

  public static final int BYTES = 816;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int ORACLE_OFFSET = 40;
  public static final int MINT_OFFSET = 72;
  public static final int VAULT_OFFSET = 104;
  public static final int NAME_OFFSET = 136;
  public static final int HISTORICAL_ORACLE_DATA_OFFSET = 168;
  public static final int HISTORICAL_INDEX_DATA_OFFSET = 224;
  public static final int REVENUE_POOL_OFFSET = 272;
  public static final int SPOT_FEE_POOL_OFFSET = 304;
  public static final int INSURANCE_FUND_OFFSET = 336;
  public static final int TOTAL_SPOT_FEE_OFFSET = 456;
  public static final int DEPOSIT_BALANCE_OFFSET = 472;
  public static final int BORROW_BALANCE_OFFSET = 488;
  public static final int CUMULATIVE_DEPOSIT_INTEREST_OFFSET = 504;
  public static final int CUMULATIVE_BORROW_INTEREST_OFFSET = 520;
  public static final int TOTAL_SOCIAL_LOSS_OFFSET = 536;
  public static final int TOTAL_QUOTE_SOCIAL_LOSS_OFFSET = 552;
  public static final int WITHDRAW_GUARD_THRESHOLD_OFFSET = 568;
  public static final int MAX_TOKEN_DEPOSITS_OFFSET = 576;
  public static final int DEPOSIT_TOKEN_TWAP_OFFSET = 584;
  public static final int BORROW_TOKEN_TWAP_OFFSET = 592;
  public static final int UTILIZATION_TWAP_OFFSET = 600;
  public static final int LAST_INTEREST_TS_OFFSET = 608;
  public static final int LAST_TWAP_TS_OFFSET = 616;
  public static final int EXPIRY_TS_OFFSET = 624;
  public static final int ORDER_STEP_SIZE_OFFSET = 632;
  public static final int ORDER_TICK_SIZE_OFFSET = 640;
  public static final int MIN_ORDER_SIZE_OFFSET = 648;
  public static final int MAX_POSITION_SIZE_OFFSET = 656;
  public static final int NEXT_FILL_RECORD_ID_OFFSET = 664;
  public static final int NEXT_DEPOSIT_RECORD_ID_OFFSET = 672;
  public static final int INITIAL_ASSET_WEIGHT_OFFSET = 680;
  public static final int MAINTENANCE_ASSET_WEIGHT_OFFSET = 684;
  public static final int INITIAL_LIABILITY_WEIGHT_OFFSET = 688;
  public static final int MAINTENANCE_LIABILITY_WEIGHT_OFFSET = 692;
  public static final int IMF_FACTOR_OFFSET = 696;
  public static final int LIQUIDATOR_FEE_OFFSET = 700;
  public static final int IF_LIQUIDATION_FEE_OFFSET = 704;
  public static final int OPTIMAL_UTILIZATION_OFFSET = 708;
  public static final int OPTIMAL_BORROW_RATE_OFFSET = 712;
  public static final int MAX_BORROW_RATE_OFFSET = 716;
  public static final int DECIMALS_OFFSET = 720;
  public static final int MARKET_INDEX_OFFSET = 724;
  public static final int ORDERS_ENABLED_OFFSET = 726;
  public static final int ORACLE_SOURCE_OFFSET = 727;
  public static final int STATUS_OFFSET = 728;
  public static final int ASSET_TIER_OFFSET = 729;
  public static final int PAUSED_OPERATIONS_OFFSET = 730;
  public static final int IF_PAUSED_OPERATIONS_OFFSET = 731;
  public static final int FEE_ADJUSTMENT_OFFSET = 732;
  public static final int MAX_TOKEN_BORROWS_FRACTION_OFFSET = 734;
  public static final int FLASH_LOAN_AMOUNT_OFFSET = 736;
  public static final int FLASH_LOAN_INITIAL_TOKEN_AMOUNT_OFFSET = 744;
  public static final int TOTAL_SWAP_FEE_OFFSET = 752;
  public static final int SCALE_INITIAL_ASSET_WEIGHT_START_OFFSET = 760;
  public static final int MIN_BORROW_RATE_OFFSET = 768;
  public static final int FUEL_BOOST_DEPOSITS_OFFSET = 769;
  public static final int FUEL_BOOST_BORROWS_OFFSET = 770;
  public static final int FUEL_BOOST_TAKER_OFFSET = 771;
  public static final int FUEL_BOOST_MAKER_OFFSET = 772;
  public static final int FUEL_BOOST_INSURANCE_OFFSET = 773;
  public static final int TOKEN_PROGRAM_OFFSET = 774;
  public static final int PADDING_OFFSET = 775;

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

  public static Filter createHistoricalIndexDataFilter(final HistoricalIndexData historicalIndexData) {
    return Filter.createMemCompFilter(HISTORICAL_INDEX_DATA_OFFSET, historicalIndexData.write());
  }

  public static Filter createRevenuePoolFilter(final PoolBalance revenuePool) {
    return Filter.createMemCompFilter(REVENUE_POOL_OFFSET, revenuePool.write());
  }

  public static Filter createSpotFeePoolFilter(final PoolBalance spotFeePool) {
    return Filter.createMemCompFilter(SPOT_FEE_POOL_OFFSET, spotFeePool.write());
  }

  public static Filter createInsuranceFundFilter(final InsuranceFund insuranceFund) {
    return Filter.createMemCompFilter(INSURANCE_FUND_OFFSET, insuranceFund.write());
  }

  public static Filter createTotalSpotFeeFilter(final BigInteger totalSpotFee) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalSpotFee);
    return Filter.createMemCompFilter(TOTAL_SPOT_FEE_OFFSET, _data);
  }

  public static Filter createDepositBalanceFilter(final BigInteger depositBalance) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, depositBalance);
    return Filter.createMemCompFilter(DEPOSIT_BALANCE_OFFSET, _data);
  }

  public static Filter createBorrowBalanceFilter(final BigInteger borrowBalance) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, borrowBalance);
    return Filter.createMemCompFilter(BORROW_BALANCE_OFFSET, _data);
  }

  public static Filter createCumulativeDepositInterestFilter(final BigInteger cumulativeDepositInterest) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeDepositInterest);
    return Filter.createMemCompFilter(CUMULATIVE_DEPOSIT_INTEREST_OFFSET, _data);
  }

  public static Filter createCumulativeBorrowInterestFilter(final BigInteger cumulativeBorrowInterest) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, cumulativeBorrowInterest);
    return Filter.createMemCompFilter(CUMULATIVE_BORROW_INTEREST_OFFSET, _data);
  }

  public static Filter createTotalSocialLossFilter(final BigInteger totalSocialLoss) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalSocialLoss);
    return Filter.createMemCompFilter(TOTAL_SOCIAL_LOSS_OFFSET, _data);
  }

  public static Filter createTotalQuoteSocialLossFilter(final BigInteger totalQuoteSocialLoss) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, totalQuoteSocialLoss);
    return Filter.createMemCompFilter(TOTAL_QUOTE_SOCIAL_LOSS_OFFSET, _data);
  }

  public static Filter createWithdrawGuardThresholdFilter(final long withdrawGuardThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, withdrawGuardThreshold);
    return Filter.createMemCompFilter(WITHDRAW_GUARD_THRESHOLD_OFFSET, _data);
  }

  public static Filter createMaxTokenDepositsFilter(final long maxTokenDeposits) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxTokenDeposits);
    return Filter.createMemCompFilter(MAX_TOKEN_DEPOSITS_OFFSET, _data);
  }

  public static Filter createDepositTokenTwapFilter(final long depositTokenTwap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, depositTokenTwap);
    return Filter.createMemCompFilter(DEPOSIT_TOKEN_TWAP_OFFSET, _data);
  }

  public static Filter createBorrowTokenTwapFilter(final long borrowTokenTwap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, borrowTokenTwap);
    return Filter.createMemCompFilter(BORROW_TOKEN_TWAP_OFFSET, _data);
  }

  public static Filter createUtilizationTwapFilter(final long utilizationTwap) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, utilizationTwap);
    return Filter.createMemCompFilter(UTILIZATION_TWAP_OFFSET, _data);
  }

  public static Filter createLastInterestTsFilter(final long lastInterestTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastInterestTs);
    return Filter.createMemCompFilter(LAST_INTEREST_TS_OFFSET, _data);
  }

  public static Filter createLastTwapTsFilter(final long lastTwapTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastTwapTs);
    return Filter.createMemCompFilter(LAST_TWAP_TS_OFFSET, _data);
  }

  public static Filter createExpiryTsFilter(final long expiryTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, expiryTs);
    return Filter.createMemCompFilter(EXPIRY_TS_OFFSET, _data);
  }

  public static Filter createOrderStepSizeFilter(final long orderStepSize) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, orderStepSize);
    return Filter.createMemCompFilter(ORDER_STEP_SIZE_OFFSET, _data);
  }

  public static Filter createOrderTickSizeFilter(final long orderTickSize) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, orderTickSize);
    return Filter.createMemCompFilter(ORDER_TICK_SIZE_OFFSET, _data);
  }

  public static Filter createMinOrderSizeFilter(final long minOrderSize) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minOrderSize);
    return Filter.createMemCompFilter(MIN_ORDER_SIZE_OFFSET, _data);
  }

  public static Filter createMaxPositionSizeFilter(final long maxPositionSize) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, maxPositionSize);
    return Filter.createMemCompFilter(MAX_POSITION_SIZE_OFFSET, _data);
  }

  public static Filter createNextFillRecordIdFilter(final long nextFillRecordId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextFillRecordId);
    return Filter.createMemCompFilter(NEXT_FILL_RECORD_ID_OFFSET, _data);
  }

  public static Filter createNextDepositRecordIdFilter(final long nextDepositRecordId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextDepositRecordId);
    return Filter.createMemCompFilter(NEXT_DEPOSIT_RECORD_ID_OFFSET, _data);
  }

  public static Filter createInitialAssetWeightFilter(final int initialAssetWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, initialAssetWeight);
    return Filter.createMemCompFilter(INITIAL_ASSET_WEIGHT_OFFSET, _data);
  }

  public static Filter createMaintenanceAssetWeightFilter(final int maintenanceAssetWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maintenanceAssetWeight);
    return Filter.createMemCompFilter(MAINTENANCE_ASSET_WEIGHT_OFFSET, _data);
  }

  public static Filter createInitialLiabilityWeightFilter(final int initialLiabilityWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, initialLiabilityWeight);
    return Filter.createMemCompFilter(INITIAL_LIABILITY_WEIGHT_OFFSET, _data);
  }

  public static Filter createMaintenanceLiabilityWeightFilter(final int maintenanceLiabilityWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maintenanceLiabilityWeight);
    return Filter.createMemCompFilter(MAINTENANCE_LIABILITY_WEIGHT_OFFSET, _data);
  }

  public static Filter createImfFactorFilter(final int imfFactor) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, imfFactor);
    return Filter.createMemCompFilter(IMF_FACTOR_OFFSET, _data);
  }

  public static Filter createLiquidatorFeeFilter(final int liquidatorFee) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, liquidatorFee);
    return Filter.createMemCompFilter(LIQUIDATOR_FEE_OFFSET, _data);
  }

  public static Filter createIfLiquidationFeeFilter(final int ifLiquidationFee) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, ifLiquidationFee);
    return Filter.createMemCompFilter(IF_LIQUIDATION_FEE_OFFSET, _data);
  }

  public static Filter createOptimalUtilizationFilter(final int optimalUtilization) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, optimalUtilization);
    return Filter.createMemCompFilter(OPTIMAL_UTILIZATION_OFFSET, _data);
  }

  public static Filter createOptimalBorrowRateFilter(final int optimalBorrowRate) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, optimalBorrowRate);
    return Filter.createMemCompFilter(OPTIMAL_BORROW_RATE_OFFSET, _data);
  }

  public static Filter createMaxBorrowRateFilter(final int maxBorrowRate) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, maxBorrowRate);
    return Filter.createMemCompFilter(MAX_BORROW_RATE_OFFSET, _data);
  }

  public static Filter createDecimalsFilter(final int decimals) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, decimals);
    return Filter.createMemCompFilter(DECIMALS_OFFSET, _data);
  }

  public static Filter createMarketIndexFilter(final int marketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, marketIndex);
    return Filter.createMemCompFilter(MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createOrdersEnabledFilter(final boolean ordersEnabled) {
    return Filter.createMemCompFilter(ORDERS_ENABLED_OFFSET, new byte[]{(byte) (ordersEnabled ? 1 : 0)});
  }

  public static Filter createOracleSourceFilter(final OracleSource oracleSource) {
    return Filter.createMemCompFilter(ORACLE_SOURCE_OFFSET, oracleSource.write());
  }

  public static Filter createStatusFilter(final MarketStatus status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, status.write());
  }

  public static Filter createAssetTierFilter(final AssetTier assetTier) {
    return Filter.createMemCompFilter(ASSET_TIER_OFFSET, assetTier.write());
  }

  public static Filter createPausedOperationsFilter(final int pausedOperations) {
    return Filter.createMemCompFilter(PAUSED_OPERATIONS_OFFSET, new byte[]{(byte) pausedOperations});
  }

  public static Filter createIfPausedOperationsFilter(final int ifPausedOperations) {
    return Filter.createMemCompFilter(IF_PAUSED_OPERATIONS_OFFSET, new byte[]{(byte) ifPausedOperations});
  }

  public static Filter createFeeAdjustmentFilter(final int feeAdjustment) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, feeAdjustment);
    return Filter.createMemCompFilter(FEE_ADJUSTMENT_OFFSET, _data);
  }

  public static Filter createMaxTokenBorrowsFractionFilter(final int maxTokenBorrowsFraction) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, maxTokenBorrowsFraction);
    return Filter.createMemCompFilter(MAX_TOKEN_BORROWS_FRACTION_OFFSET, _data);
  }

  public static Filter createFlashLoanAmountFilter(final long flashLoanAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, flashLoanAmount);
    return Filter.createMemCompFilter(FLASH_LOAN_AMOUNT_OFFSET, _data);
  }

  public static Filter createFlashLoanInitialTokenAmountFilter(final long flashLoanInitialTokenAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, flashLoanInitialTokenAmount);
    return Filter.createMemCompFilter(FLASH_LOAN_INITIAL_TOKEN_AMOUNT_OFFSET, _data);
  }

  public static Filter createTotalSwapFeeFilter(final long totalSwapFee) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, totalSwapFee);
    return Filter.createMemCompFilter(TOTAL_SWAP_FEE_OFFSET, _data);
  }

  public static Filter createScaleInitialAssetWeightStartFilter(final long scaleInitialAssetWeightStart) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, scaleInitialAssetWeightStart);
    return Filter.createMemCompFilter(SCALE_INITIAL_ASSET_WEIGHT_START_OFFSET, _data);
  }

  public static Filter createMinBorrowRateFilter(final int minBorrowRate) {
    return Filter.createMemCompFilter(MIN_BORROW_RATE_OFFSET, new byte[]{(byte) minBorrowRate});
  }

  public static Filter createFuelBoostDepositsFilter(final int fuelBoostDeposits) {
    return Filter.createMemCompFilter(FUEL_BOOST_DEPOSITS_OFFSET, new byte[]{(byte) fuelBoostDeposits});
  }

  public static Filter createFuelBoostBorrowsFilter(final int fuelBoostBorrows) {
    return Filter.createMemCompFilter(FUEL_BOOST_BORROWS_OFFSET, new byte[]{(byte) fuelBoostBorrows});
  }

  public static Filter createFuelBoostTakerFilter(final int fuelBoostTaker) {
    return Filter.createMemCompFilter(FUEL_BOOST_TAKER_OFFSET, new byte[]{(byte) fuelBoostTaker});
  }

  public static Filter createFuelBoostMakerFilter(final int fuelBoostMaker) {
    return Filter.createMemCompFilter(FUEL_BOOST_MAKER_OFFSET, new byte[]{(byte) fuelBoostMaker});
  }

  public static Filter createFuelBoostInsuranceFilter(final int fuelBoostInsurance) {
    return Filter.createMemCompFilter(FUEL_BOOST_INSURANCE_OFFSET, new byte[]{(byte) fuelBoostInsurance});
  }

  public static Filter createTokenProgramFilter(final int tokenProgram) {
    return Filter.createMemCompFilter(TOKEN_PROGRAM_OFFSET, new byte[]{(byte) tokenProgram});
  }

  public static SpotMarket read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static SpotMarket read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], SpotMarket> FACTORY = SpotMarket::read;

  public static SpotMarket read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var name = Borsh.readArray(new byte[32], _data, i);
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
    final var fuelBoostDeposits = _data[i] & 0xFF;
    ++i;
    final var fuelBoostBorrows = _data[i] & 0xFF;
    ++i;
    final var fuelBoostTaker = _data[i] & 0xFF;
    ++i;
    final var fuelBoostMaker = _data[i] & 0xFF;
    ++i;
    final var fuelBoostInsurance = _data[i] & 0xFF;
    ++i;
    final var tokenProgram = _data[i] & 0xFF;
    ++i;
    final var padding = Borsh.readArray(new byte[41], _data, i);
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
                          fuelBoostDeposits,
                          fuelBoostBorrows,
                          fuelBoostTaker,
                          fuelBoostMaker,
                          fuelBoostInsurance,
                          tokenProgram,
                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
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
    _data[i] = (byte) fuelBoostDeposits;
    ++i;
    _data[i] = (byte) fuelBoostBorrows;
    ++i;
    _data[i] = (byte) fuelBoostTaker;
    ++i;
    _data[i] = (byte) fuelBoostMaker;
    ++i;
    _data[i] = (byte) fuelBoostInsurance;
    ++i;
    _data[i] = (byte) tokenProgram;
    ++i;
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
