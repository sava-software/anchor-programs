package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AMM(// oracle price data public key
                  PublicKey oracle,
                  // stores historically witnessed oracle data
                  HistoricalOracleData historicalOracleData,
                  // accumulated base asset amount since inception per lp share
                  // precision: QUOTE_PRECISION
                  BigInteger baseAssetAmountPerLp,
                  // accumulated quote asset amount since inception per lp share
                  // precision: QUOTE_PRECISION
                  BigInteger quoteAssetAmountPerLp,
                  // partition of fees from perp market trading moved from pnl settlements
                  PoolBalance feePool,
                  // `x` reserves for constant product mm formula (x * y = k)
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger baseAssetReserve,
                  // `y` reserves for constant product mm formula (x * y = k)
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger quoteAssetReserve,
                  // determines how close the min/max base asset reserve sit vs base reserves
                  // allow for decreasing slippage without increasing liquidity and v.v.
                  // precision: PERCENTAGE_PRECISION
                  BigInteger concentrationCoef,
                  // minimum base_asset_reserve allowed before AMM is unavailable
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger minBaseAssetReserve,
                  // maximum base_asset_reserve allowed before AMM is unavailable
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger maxBaseAssetReserve,
                  // `sqrt(k)` in constant product mm formula (x * y = k). stored to avoid drift caused by integer math issues
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger sqrtK,
                  // normalizing numerical factor for y, its use offers lowest slippage in cp-curve when market is balanced
                  // precision: PEG_PRECISION
                  BigInteger pegMultiplier,
                  // y when market is balanced. stored to save computation
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger terminalQuoteAssetReserve,
                  // always non-negative. tracks number of total longs in market (regardless of counterparty)
                  // precision: BASE_PRECISION
                  BigInteger baseAssetAmountLong,
                  // always non-positive. tracks number of total shorts in market (regardless of counterparty)
                  // precision: BASE_PRECISION
                  BigInteger baseAssetAmountShort,
                  // tracks net position (longs-shorts) in market with AMM as counterparty
                  // precision: BASE_PRECISION
                  BigInteger baseAssetAmountWithAmm,
                  // tracks net position (longs-shorts) in market with LPs as counterparty
                  // precision: BASE_PRECISION
                  BigInteger baseAssetAmountWithUnsettledLp,
                  // max allowed open interest, blocks trades that breach this value
                  // precision: BASE_PRECISION
                  BigInteger maxOpenInterest,
                  // sum of all user's perp quote_asset_amount in market
                  // precision: QUOTE_PRECISION
                  BigInteger quoteAssetAmount,
                  // sum of all long user's quote_entry_amount in market
                  // precision: QUOTE_PRECISION
                  BigInteger quoteEntryAmountLong,
                  // sum of all short user's quote_entry_amount in market
                  // precision: QUOTE_PRECISION
                  BigInteger quoteEntryAmountShort,
                  // sum of all long user's quote_break_even_amount in market
                  // precision: QUOTE_PRECISION
                  BigInteger quoteBreakEvenAmountLong,
                  // sum of all short user's quote_break_even_amount in market
                  // precision: QUOTE_PRECISION
                  BigInteger quoteBreakEvenAmountShort,
                  // total user lp shares of sqrt_k (protocol owned liquidity = sqrt_k - last_funding_rate)
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger userLpShares,
                  // last funding rate in this perp market (unit is quote per base)
                  // precision: QUOTE_PRECISION
                  long lastFundingRate,
                  // last funding rate for longs in this perp market (unit is quote per base)
                  // precision: QUOTE_PRECISION
                  long lastFundingRateLong,
                  // last funding rate for shorts in this perp market (unit is quote per base)
                  // precision: QUOTE_PRECISION
                  long lastFundingRateShort,
                  // estimate of last 24h of funding rate perp market (unit is quote per base)
                  // precision: QUOTE_PRECISION
                  long last24hAvgFundingRate,
                  // total fees collected by this perp market
                  // precision: QUOTE_PRECISION
                  BigInteger totalFee,
                  // total fees collected by the vAMM's bid/ask spread
                  // precision: QUOTE_PRECISION
                  BigInteger totalMmFee,
                  // total fees collected by exchange fee schedule
                  // precision: QUOTE_PRECISION
                  BigInteger totalExchangeFee,
                  // total fees minus any recognized upnl and pool withdraws
                  // precision: QUOTE_PRECISION
                  BigInteger totalFeeMinusDistributions,
                  // sum of all fees from fee pool withdrawn to revenue pool
                  // precision: QUOTE_PRECISION
                  BigInteger totalFeeWithdrawn,
                  // all fees collected by market for liquidations
                  // precision: QUOTE_PRECISION
                  BigInteger totalLiquidationFee,
                  // accumulated funding rate for longs since inception in market
                  BigInteger cumulativeFundingRateLong,
                  // accumulated funding rate for shorts since inception in market
                  BigInteger cumulativeFundingRateShort,
                  // accumulated social loss paid by users since inception in market
                  BigInteger totalSocialLoss,
                  // transformed base_asset_reserve for users going long
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger askBaseAssetReserve,
                  // transformed quote_asset_reserve for users going long
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger askQuoteAssetReserve,
                  // transformed base_asset_reserve for users going short
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger bidBaseAssetReserve,
                  // transformed quote_asset_reserve for users going short
                  // precision: AMM_RESERVE_PRECISION
                  BigInteger bidQuoteAssetReserve,
                  // the last seen oracle price partially shrunk toward the amm reserve price
                  // precision: PRICE_PRECISION
                  long lastOracleNormalisedPrice,
                  // the gap between the oracle price and the reserve price = y * peg_multiplier / x
                  long lastOracleReservePriceSpreadPct,
                  // average estimate of bid price over funding_period
                  // precision: PRICE_PRECISION
                  long lastBidPriceTwap,
                  // average estimate of ask price over funding_period
                  // precision: PRICE_PRECISION
                  long lastAskPriceTwap,
                  // average estimate of (bid+ask)/2 price over funding_period
                  // precision: PRICE_PRECISION
                  long lastMarkPriceTwap,
                  // average estimate of (bid+ask)/2 price over FIVE_MINUTES
                  long lastMarkPriceTwap5min,
                  // the last blockchain slot the amm was updated
                  long lastUpdateSlot,
                  // the pct size of the oracle confidence interval
                  // precision: PERCENTAGE_PRECISION
                  long lastOracleConfPct,
                  // the total_fee_minus_distribution change since the last funding update
                  // precision: QUOTE_PRECISION
                  long netRevenueSinceLastFunding,
                  // the last funding rate update unix_timestamp
                  long lastFundingRateTs,
                  // the peridocity of the funding rate updates
                  long fundingPeriod,
                  // the base step size (increment) of orders
                  // precision: BASE_PRECISION
                  long orderStepSize,
                  // the price tick size of orders
                  // precision: PRICE_PRECISION
                  long orderTickSize,
                  // the minimum base size of an order
                  // precision: BASE_PRECISION
                  long minOrderSize,
                  // the max base size a single user can have
                  // precision: BASE_PRECISION
                  long maxPositionSize,
                  // estimated total of volume in market
                  // QUOTE_PRECISION
                  long volume24h,
                  // the volume intensity of long fills against AMM
                  long longIntensityVolume,
                  // the volume intensity of short fills against AMM
                  long shortIntensityVolume,
                  // the blockchain unix timestamp at the time of the last trade
                  long lastTradeTs,
                  // estimate of standard deviation of the fill (mark) prices
                  // precision: PRICE_PRECISION
                  long markStd,
                  // estimate of standard deviation of the oracle price at each update
                  // precision: PRICE_PRECISION
                  long oracleStd,
                  // the last unix_timestamp the mark twap was updated
                  long lastMarkPriceTwapTs,
                  // the minimum spread the AMM can quote. also used as step size for some spread logic increases.
                  int baseSpread,
                  // the maximum spread the AMM can quote
                  int maxSpread,
                  // the spread for asks vs the reserve price
                  int longSpread,
                  // the spread for bids vs the reserve price
                  int shortSpread,
                  // the count intensity of long fills against AMM
                  int longIntensityCount,
                  // the count intensity of short fills against AMM
                  int shortIntensityCount,
                  // the fraction of total available liquidity a single fill on the AMM can consume
                  int maxFillReserveFraction,
                  // the maximum slippage a single fill on the AMM can push
                  int maxSlippageRatio,
                  // the update intensity of AMM formulaic updates (adjusting k). 0-100
                  int curveUpdateIntensity,
                  // the jit intensity of AMM. larger intensity means larger participation in jit. 0 means no jit participation.
                  // (0, 100] is intensity for protocol-owned AMM. (100, 200] is intensity for user LP-owned AMM.
                  int ammJitIntensity,
                  // the oracle provider information. used to decode/scale the oracle public key
                  OracleSource oracleSource,
                  // tracks whether the oracle was considered valid at the last AMM update
                  boolean lastOracleValid,
                  // the target value for `base_asset_amount_per_lp`, used during AMM JIT with LP split
                  // precision: BASE_PRECISION
                  int targetBaseAssetAmountPerLp,
                  // expo for unit of per_lp, base 10 (if per_lp_base=X, then per_lp unit is 10^X)
                  int perLpBase,
                  int padding1,
                  int padding2,
                  long totalFeeEarnedPerLp,
                  long netUnsettledFundingPnl,
                  long quoteAssetAmountWithUnsettledLp,
                  int referencePriceOffset,
                  byte[] padding) implements Borsh {

  public static final int BYTES = 936;

  public static AMM read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var historicalOracleData = HistoricalOracleData.read(_data, i);
    i += Borsh.len(historicalOracleData);
    final var baseAssetAmountPerLp = getInt128LE(_data, i);
    i += 16;
    final var quoteAssetAmountPerLp = getInt128LE(_data, i);
    i += 16;
    final var feePool = PoolBalance.read(_data, i);
    i += Borsh.len(feePool);
    final var baseAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var quoteAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var concentrationCoef = getInt128LE(_data, i);
    i += 16;
    final var minBaseAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var maxBaseAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var sqrtK = getInt128LE(_data, i);
    i += 16;
    final var pegMultiplier = getInt128LE(_data, i);
    i += 16;
    final var terminalQuoteAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountLong = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountShort = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountWithAmm = getInt128LE(_data, i);
    i += 16;
    final var baseAssetAmountWithUnsettledLp = getInt128LE(_data, i);
    i += 16;
    final var maxOpenInterest = getInt128LE(_data, i);
    i += 16;
    final var quoteAssetAmount = getInt128LE(_data, i);
    i += 16;
    final var quoteEntryAmountLong = getInt128LE(_data, i);
    i += 16;
    final var quoteEntryAmountShort = getInt128LE(_data, i);
    i += 16;
    final var quoteBreakEvenAmountLong = getInt128LE(_data, i);
    i += 16;
    final var quoteBreakEvenAmountShort = getInt128LE(_data, i);
    i += 16;
    final var userLpShares = getInt128LE(_data, i);
    i += 16;
    final var lastFundingRate = getInt64LE(_data, i);
    i += 8;
    final var lastFundingRateLong = getInt64LE(_data, i);
    i += 8;
    final var lastFundingRateShort = getInt64LE(_data, i);
    i += 8;
    final var last24hAvgFundingRate = getInt64LE(_data, i);
    i += 8;
    final var totalFee = getInt128LE(_data, i);
    i += 16;
    final var totalMmFee = getInt128LE(_data, i);
    i += 16;
    final var totalExchangeFee = getInt128LE(_data, i);
    i += 16;
    final var totalFeeMinusDistributions = getInt128LE(_data, i);
    i += 16;
    final var totalFeeWithdrawn = getInt128LE(_data, i);
    i += 16;
    final var totalLiquidationFee = getInt128LE(_data, i);
    i += 16;
    final var cumulativeFundingRateLong = getInt128LE(_data, i);
    i += 16;
    final var cumulativeFundingRateShort = getInt128LE(_data, i);
    i += 16;
    final var totalSocialLoss = getInt128LE(_data, i);
    i += 16;
    final var askBaseAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var askQuoteAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var bidBaseAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var bidQuoteAssetReserve = getInt128LE(_data, i);
    i += 16;
    final var lastOracleNormalisedPrice = getInt64LE(_data, i);
    i += 8;
    final var lastOracleReservePriceSpreadPct = getInt64LE(_data, i);
    i += 8;
    final var lastBidPriceTwap = getInt64LE(_data, i);
    i += 8;
    final var lastAskPriceTwap = getInt64LE(_data, i);
    i += 8;
    final var lastMarkPriceTwap = getInt64LE(_data, i);
    i += 8;
    final var lastMarkPriceTwap5min = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateSlot = getInt64LE(_data, i);
    i += 8;
    final var lastOracleConfPct = getInt64LE(_data, i);
    i += 8;
    final var netRevenueSinceLastFunding = getInt64LE(_data, i);
    i += 8;
    final var lastFundingRateTs = getInt64LE(_data, i);
    i += 8;
    final var fundingPeriod = getInt64LE(_data, i);
    i += 8;
    final var orderStepSize = getInt64LE(_data, i);
    i += 8;
    final var orderTickSize = getInt64LE(_data, i);
    i += 8;
    final var minOrderSize = getInt64LE(_data, i);
    i += 8;
    final var maxPositionSize = getInt64LE(_data, i);
    i += 8;
    final var volume24h = getInt64LE(_data, i);
    i += 8;
    final var longIntensityVolume = getInt64LE(_data, i);
    i += 8;
    final var shortIntensityVolume = getInt64LE(_data, i);
    i += 8;
    final var lastTradeTs = getInt64LE(_data, i);
    i += 8;
    final var markStd = getInt64LE(_data, i);
    i += 8;
    final var oracleStd = getInt64LE(_data, i);
    i += 8;
    final var lastMarkPriceTwapTs = getInt64LE(_data, i);
    i += 8;
    final var baseSpread = getInt32LE(_data, i);
    i += 4;
    final var maxSpread = getInt32LE(_data, i);
    i += 4;
    final var longSpread = getInt32LE(_data, i);
    i += 4;
    final var shortSpread = getInt32LE(_data, i);
    i += 4;
    final var longIntensityCount = getInt32LE(_data, i);
    i += 4;
    final var shortIntensityCount = getInt32LE(_data, i);
    i += 4;
    final var maxFillReserveFraction = getInt16LE(_data, i);
    i += 2;
    final var maxSlippageRatio = getInt16LE(_data, i);
    i += 2;
    final var curveUpdateIntensity = _data[i] & 0xFF;
    ++i;
    final var ammJitIntensity = _data[i] & 0xFF;
    ++i;
    final var oracleSource = OracleSource.read(_data, i);
    i += Borsh.len(oracleSource);
    final var lastOracleValid = _data[i] == 1;
    ++i;
    final var targetBaseAssetAmountPerLp = getInt32LE(_data, i);
    i += 4;
    final var perLpBase = _data[i];
    ++i;
    final var padding1 = _data[i] & 0xFF;
    ++i;
    final var padding2 = getInt16LE(_data, i);
    i += 2;
    final var totalFeeEarnedPerLp = getInt64LE(_data, i);
    i += 8;
    final var netUnsettledFundingPnl = getInt64LE(_data, i);
    i += 8;
    final var quoteAssetAmountWithUnsettledLp = getInt64LE(_data, i);
    i += 8;
    final var referencePriceOffset = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[12];
    Borsh.readArray(padding, _data, i);
    return new AMM(oracle,
                   historicalOracleData,
                   baseAssetAmountPerLp,
                   quoteAssetAmountPerLp,
                   feePool,
                   baseAssetReserve,
                   quoteAssetReserve,
                   concentrationCoef,
                   minBaseAssetReserve,
                   maxBaseAssetReserve,
                   sqrtK,
                   pegMultiplier,
                   terminalQuoteAssetReserve,
                   baseAssetAmountLong,
                   baseAssetAmountShort,
                   baseAssetAmountWithAmm,
                   baseAssetAmountWithUnsettledLp,
                   maxOpenInterest,
                   quoteAssetAmount,
                   quoteEntryAmountLong,
                   quoteEntryAmountShort,
                   quoteBreakEvenAmountLong,
                   quoteBreakEvenAmountShort,
                   userLpShares,
                   lastFundingRate,
                   lastFundingRateLong,
                   lastFundingRateShort,
                   last24hAvgFundingRate,
                   totalFee,
                   totalMmFee,
                   totalExchangeFee,
                   totalFeeMinusDistributions,
                   totalFeeWithdrawn,
                   totalLiquidationFee,
                   cumulativeFundingRateLong,
                   cumulativeFundingRateShort,
                   totalSocialLoss,
                   askBaseAssetReserve,
                   askQuoteAssetReserve,
                   bidBaseAssetReserve,
                   bidQuoteAssetReserve,
                   lastOracleNormalisedPrice,
                   lastOracleReservePriceSpreadPct,
                   lastBidPriceTwap,
                   lastAskPriceTwap,
                   lastMarkPriceTwap,
                   lastMarkPriceTwap5min,
                   lastUpdateSlot,
                   lastOracleConfPct,
                   netRevenueSinceLastFunding,
                   lastFundingRateTs,
                   fundingPeriod,
                   orderStepSize,
                   orderTickSize,
                   minOrderSize,
                   maxPositionSize,
                   volume24h,
                   longIntensityVolume,
                   shortIntensityVolume,
                   lastTradeTs,
                   markStd,
                   oracleStd,
                   lastMarkPriceTwapTs,
                   baseSpread,
                   maxSpread,
                   longSpread,
                   shortSpread,
                   longIntensityCount,
                   shortIntensityCount,
                   maxFillReserveFraction,
                   maxSlippageRatio,
                   curveUpdateIntensity,
                   ammJitIntensity,
                   oracleSource,
                   lastOracleValid,
                   targetBaseAssetAmountPerLp,
                   perLpBase,
                   padding1,
                   padding2,
                   totalFeeEarnedPerLp,
                   netUnsettledFundingPnl,
                   quoteAssetAmountWithUnsettledLp,
                   referencePriceOffset,
                   padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oracle.write(_data, i);
    i += 32;
    i += Borsh.write(historicalOracleData, _data, i);
    putInt128LE(_data, i, baseAssetAmountPerLp);
    i += 16;
    putInt128LE(_data, i, quoteAssetAmountPerLp);
    i += 16;
    i += Borsh.write(feePool, _data, i);
    putInt128LE(_data, i, baseAssetReserve);
    i += 16;
    putInt128LE(_data, i, quoteAssetReserve);
    i += 16;
    putInt128LE(_data, i, concentrationCoef);
    i += 16;
    putInt128LE(_data, i, minBaseAssetReserve);
    i += 16;
    putInt128LE(_data, i, maxBaseAssetReserve);
    i += 16;
    putInt128LE(_data, i, sqrtK);
    i += 16;
    putInt128LE(_data, i, pegMultiplier);
    i += 16;
    putInt128LE(_data, i, terminalQuoteAssetReserve);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountLong);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountShort);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountWithAmm);
    i += 16;
    putInt128LE(_data, i, baseAssetAmountWithUnsettledLp);
    i += 16;
    putInt128LE(_data, i, maxOpenInterest);
    i += 16;
    putInt128LE(_data, i, quoteAssetAmount);
    i += 16;
    putInt128LE(_data, i, quoteEntryAmountLong);
    i += 16;
    putInt128LE(_data, i, quoteEntryAmountShort);
    i += 16;
    putInt128LE(_data, i, quoteBreakEvenAmountLong);
    i += 16;
    putInt128LE(_data, i, quoteBreakEvenAmountShort);
    i += 16;
    putInt128LE(_data, i, userLpShares);
    i += 16;
    putInt64LE(_data, i, lastFundingRate);
    i += 8;
    putInt64LE(_data, i, lastFundingRateLong);
    i += 8;
    putInt64LE(_data, i, lastFundingRateShort);
    i += 8;
    putInt64LE(_data, i, last24hAvgFundingRate);
    i += 8;
    putInt128LE(_data, i, totalFee);
    i += 16;
    putInt128LE(_data, i, totalMmFee);
    i += 16;
    putInt128LE(_data, i, totalExchangeFee);
    i += 16;
    putInt128LE(_data, i, totalFeeMinusDistributions);
    i += 16;
    putInt128LE(_data, i, totalFeeWithdrawn);
    i += 16;
    putInt128LE(_data, i, totalLiquidationFee);
    i += 16;
    putInt128LE(_data, i, cumulativeFundingRateLong);
    i += 16;
    putInt128LE(_data, i, cumulativeFundingRateShort);
    i += 16;
    putInt128LE(_data, i, totalSocialLoss);
    i += 16;
    putInt128LE(_data, i, askBaseAssetReserve);
    i += 16;
    putInt128LE(_data, i, askQuoteAssetReserve);
    i += 16;
    putInt128LE(_data, i, bidBaseAssetReserve);
    i += 16;
    putInt128LE(_data, i, bidQuoteAssetReserve);
    i += 16;
    putInt64LE(_data, i, lastOracleNormalisedPrice);
    i += 8;
    putInt64LE(_data, i, lastOracleReservePriceSpreadPct);
    i += 8;
    putInt64LE(_data, i, lastBidPriceTwap);
    i += 8;
    putInt64LE(_data, i, lastAskPriceTwap);
    i += 8;
    putInt64LE(_data, i, lastMarkPriceTwap);
    i += 8;
    putInt64LE(_data, i, lastMarkPriceTwap5min);
    i += 8;
    putInt64LE(_data, i, lastUpdateSlot);
    i += 8;
    putInt64LE(_data, i, lastOracleConfPct);
    i += 8;
    putInt64LE(_data, i, netRevenueSinceLastFunding);
    i += 8;
    putInt64LE(_data, i, lastFundingRateTs);
    i += 8;
    putInt64LE(_data, i, fundingPeriod);
    i += 8;
    putInt64LE(_data, i, orderStepSize);
    i += 8;
    putInt64LE(_data, i, orderTickSize);
    i += 8;
    putInt64LE(_data, i, minOrderSize);
    i += 8;
    putInt64LE(_data, i, maxPositionSize);
    i += 8;
    putInt64LE(_data, i, volume24h);
    i += 8;
    putInt64LE(_data, i, longIntensityVolume);
    i += 8;
    putInt64LE(_data, i, shortIntensityVolume);
    i += 8;
    putInt64LE(_data, i, lastTradeTs);
    i += 8;
    putInt64LE(_data, i, markStd);
    i += 8;
    putInt64LE(_data, i, oracleStd);
    i += 8;
    putInt64LE(_data, i, lastMarkPriceTwapTs);
    i += 8;
    putInt32LE(_data, i, baseSpread);
    i += 4;
    putInt32LE(_data, i, maxSpread);
    i += 4;
    putInt32LE(_data, i, longSpread);
    i += 4;
    putInt32LE(_data, i, shortSpread);
    i += 4;
    putInt32LE(_data, i, longIntensityCount);
    i += 4;
    putInt32LE(_data, i, shortIntensityCount);
    i += 4;
    putInt16LE(_data, i, maxFillReserveFraction);
    i += 2;
    putInt16LE(_data, i, maxSlippageRatio);
    i += 2;
    _data[i] = (byte) curveUpdateIntensity;
    ++i;
    _data[i] = (byte) ammJitIntensity;
    ++i;
    i += Borsh.write(oracleSource, _data, i);
    _data[i] = (byte) (lastOracleValid ? 1 : 0);
    ++i;
    putInt32LE(_data, i, targetBaseAssetAmountPerLp);
    i += 4;
    _data[i] = (byte) perLpBase;
    ++i;
    _data[i] = (byte) padding1;
    ++i;
    putInt16LE(_data, i, padding2);
    i += 2;
    putInt64LE(_data, i, totalFeeEarnedPerLp);
    i += 8;
    putInt64LE(_data, i, netUnsettledFundingPnl);
    i += 8;
    putInt64LE(_data, i, quoteAssetAmountWithUnsettledLp);
    i += 8;
    putInt32LE(_data, i, referencePriceOffset);
    i += 4;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
