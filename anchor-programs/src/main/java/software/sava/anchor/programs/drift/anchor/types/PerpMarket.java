package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

public record PerpMarket(PublicKey _address,
                         Discriminator discriminator,
                         // The perp market's address. It is a pda of the market index
                         PublicKey pubkey,
                         // The automated market maker
                         AMM amm,
                         // The market's pnl pool. When users settle negative pnl, the balance increases.
                         // When users settle positive pnl, the balance decreases. Can not go negative.
                         PoolBalance pnlPool,
                         // Encoded display name for the perp market e.g. SOL-PERP
                         byte[] name,
                         // The perp market's claim on the insurance fund
                         InsuranceClaim insuranceClaim,
                         // The max pnl imbalance before positive pnl asset weight is discounted
                         // pnl imbalance is the difference between long and short pnl. When it's greater than 0,
                         // the amm has negative pnl and the initial asset weight for positive pnl is discounted
                         // precision = QUOTE_PRECISION
                         long unrealizedPnlMaxImbalance,
                         // The ts when the market will be expired. Only set if market is in reduce only mode
                         long expiryTs,
                         // The price at which positions will be settled. Only set if market is expired
                         // precision = PRICE_PRECISION
                         long expiryPrice,
                         // Every trade has a fill record id. This is the next id to be used
                         long nextFillRecordId,
                         // Every funding rate update has a record id. This is the next id to be used
                         long nextFundingRateRecordId,
                         // Every amm k updated has a record id. This is the next id to be used
                         long nextCurveRecordId,
                         // The initial margin fraction factor. Used to increase margin ratio for large positions
                         // precision: MARGIN_PRECISION
                         int imfFactor,
                         // The imf factor for unrealized pnl. Used to discount asset weight for large positive pnl
                         // precision: MARGIN_PRECISION
                         int unrealizedPnlImfFactor,
                         // The fee the liquidator is paid for taking over perp position
                         // precision: LIQUIDATOR_FEE_PRECISION
                         int liquidatorFee,
                         // The fee the insurance fund receives from liquidation
                         // precision: LIQUIDATOR_FEE_PRECISION
                         int ifLiquidationFee,
                         // The margin ratio which determines how much collateral is required to open a position
                         // e.g. margin ratio of .1 means a user must have $100 of total collateral to open a $1000 position
                         // precision: MARGIN_PRECISION
                         int marginRatioInitial,
                         // The margin ratio which determines when a user will be liquidated
                         // e.g. margin ratio of .05 means a user must have $50 of total collateral to maintain a $1000 position
                         // else they will be liquidated
                         // precision: MARGIN_PRECISION
                         int marginRatioMaintenance,
                         // The initial asset weight for positive pnl. Negative pnl always has an asset weight of 1
                         // precision: SPOT_WEIGHT_PRECISION
                         int unrealizedPnlInitialAssetWeight,
                         // The maintenance asset weight for positive pnl. Negative pnl always has an asset weight of 1
                         // precision: SPOT_WEIGHT_PRECISION
                         int unrealizedPnlMaintenanceAssetWeight,
                         // number of users in a position (base)
                         int numberOfUsersWithBase,
                         // number of users in a position (pnl) or pnl (quote)
                         int numberOfUsers,
                         int marketIndex,
                         // Whether a market is active, reduce only, expired, etc
                         // Affects whether users can open/close positions
                         MarketStatus status,
                         // Currently only Perpetual markets are supported
                         ContractType contractType,
                         // The contract tier determines how much insurance a market can receive, with more speculative markets receiving less insurance
                         // It also influences the order perp markets can be liquidated, with less speculative markets being liquidated first
                         ContractTier contractTier,
                         int pausedOperations,
                         // The spot market that pnl is settled in
                         int quoteSpotMarketIndex,
                         // Between -100 and 100, represents what % to increase/decrease the fee by
                         // E.g. if this is -50 and the fee is 5bps, the new fee will be 2.5bps
                         // if this is 50 and the fee is 5bps, the new fee will be 7.5bps
                         int feeAdjustment,
                         // fuel multiplier for perp funding
                         // precision: 10
                         int fuelBoostPosition,
                         // fuel multiplier for perp taker
                         // precision: 10
                         int fuelBoostTaker,
                         // fuel multiplier for perp maker
                         // precision: 10
                         int fuelBoostMaker,
                         int poolId,
                         int highLeverageMarginRatioInitial,
                         int highLeverageMarginRatioMaintenance,
                         int protectedMakerLimitPriceDivisor,
                         int protectedMakerDynamicDivisor,
                         int padding1,
                         long lastFillPrice,
                         byte[] padding) implements Borsh {

  public static final int BYTES = 1216;
  public static final int NAME_LEN = 32;
  public static final int PADDING_LEN = 24;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int PUBKEY_OFFSET = 8;
  public static final int AMM_OFFSET = 40;
  public static final int PNL_POOL_OFFSET = 976;
  public static final int NAME_OFFSET = 1000;
  public static final int INSURANCE_CLAIM_OFFSET = 1032;
  public static final int UNREALIZED_PNL_MAX_IMBALANCE_OFFSET = 1072;
  public static final int EXPIRY_TS_OFFSET = 1080;
  public static final int EXPIRY_PRICE_OFFSET = 1088;
  public static final int NEXT_FILL_RECORD_ID_OFFSET = 1096;
  public static final int NEXT_FUNDING_RATE_RECORD_ID_OFFSET = 1104;
  public static final int NEXT_CURVE_RECORD_ID_OFFSET = 1112;
  public static final int IMF_FACTOR_OFFSET = 1120;
  public static final int UNREALIZED_PNL_IMF_FACTOR_OFFSET = 1124;
  public static final int LIQUIDATOR_FEE_OFFSET = 1128;
  public static final int IF_LIQUIDATION_FEE_OFFSET = 1132;
  public static final int MARGIN_RATIO_INITIAL_OFFSET = 1136;
  public static final int MARGIN_RATIO_MAINTENANCE_OFFSET = 1140;
  public static final int UNREALIZED_PNL_INITIAL_ASSET_WEIGHT_OFFSET = 1144;
  public static final int UNREALIZED_PNL_MAINTENANCE_ASSET_WEIGHT_OFFSET = 1148;
  public static final int NUMBER_OF_USERS_WITH_BASE_OFFSET = 1152;
  public static final int NUMBER_OF_USERS_OFFSET = 1156;
  public static final int MARKET_INDEX_OFFSET = 1160;
  public static final int STATUS_OFFSET = 1162;
  public static final int CONTRACT_TYPE_OFFSET = 1163;
  public static final int CONTRACT_TIER_OFFSET = 1164;
  public static final int PAUSED_OPERATIONS_OFFSET = 1165;
  public static final int QUOTE_SPOT_MARKET_INDEX_OFFSET = 1166;
  public static final int FEE_ADJUSTMENT_OFFSET = 1168;
  public static final int FUEL_BOOST_POSITION_OFFSET = 1170;
  public static final int FUEL_BOOST_TAKER_OFFSET = 1171;
  public static final int FUEL_BOOST_MAKER_OFFSET = 1172;
  public static final int POOL_ID_OFFSET = 1173;
  public static final int HIGH_LEVERAGE_MARGIN_RATIO_INITIAL_OFFSET = 1174;
  public static final int HIGH_LEVERAGE_MARGIN_RATIO_MAINTENANCE_OFFSET = 1176;
  public static final int PROTECTED_MAKER_LIMIT_PRICE_DIVISOR_OFFSET = 1178;
  public static final int PROTECTED_MAKER_DYNAMIC_DIVISOR_OFFSET = 1179;
  public static final int PADDING_1_OFFSET = 1180;
  public static final int LAST_FILL_PRICE_OFFSET = 1184;
  public static final int PADDING_OFFSET = 1192;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createPnlPoolFilter(final PoolBalance pnlPool) {
    return Filter.createMemCompFilter(PNL_POOL_OFFSET, pnlPool.write());
  }

  public static Filter createInsuranceClaimFilter(final InsuranceClaim insuranceClaim) {
    return Filter.createMemCompFilter(INSURANCE_CLAIM_OFFSET, insuranceClaim.write());
  }

  public static Filter createUnrealizedPnlMaxImbalanceFilter(final long unrealizedPnlMaxImbalance) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unrealizedPnlMaxImbalance);
    return Filter.createMemCompFilter(UNREALIZED_PNL_MAX_IMBALANCE_OFFSET, _data);
  }

  public static Filter createExpiryTsFilter(final long expiryTs) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, expiryTs);
    return Filter.createMemCompFilter(EXPIRY_TS_OFFSET, _data);
  }

  public static Filter createExpiryPriceFilter(final long expiryPrice) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, expiryPrice);
    return Filter.createMemCompFilter(EXPIRY_PRICE_OFFSET, _data);
  }

  public static Filter createNextFillRecordIdFilter(final long nextFillRecordId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextFillRecordId);
    return Filter.createMemCompFilter(NEXT_FILL_RECORD_ID_OFFSET, _data);
  }

  public static Filter createNextFundingRateRecordIdFilter(final long nextFundingRateRecordId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextFundingRateRecordId);
    return Filter.createMemCompFilter(NEXT_FUNDING_RATE_RECORD_ID_OFFSET, _data);
  }

  public static Filter createNextCurveRecordIdFilter(final long nextCurveRecordId) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, nextCurveRecordId);
    return Filter.createMemCompFilter(NEXT_CURVE_RECORD_ID_OFFSET, _data);
  }

  public static Filter createImfFactorFilter(final int imfFactor) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, imfFactor);
    return Filter.createMemCompFilter(IMF_FACTOR_OFFSET, _data);
  }

  public static Filter createUnrealizedPnlImfFactorFilter(final int unrealizedPnlImfFactor) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, unrealizedPnlImfFactor);
    return Filter.createMemCompFilter(UNREALIZED_PNL_IMF_FACTOR_OFFSET, _data);
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

  public static Filter createMarginRatioInitialFilter(final int marginRatioInitial) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, marginRatioInitial);
    return Filter.createMemCompFilter(MARGIN_RATIO_INITIAL_OFFSET, _data);
  }

  public static Filter createMarginRatioMaintenanceFilter(final int marginRatioMaintenance) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, marginRatioMaintenance);
    return Filter.createMemCompFilter(MARGIN_RATIO_MAINTENANCE_OFFSET, _data);
  }

  public static Filter createUnrealizedPnlInitialAssetWeightFilter(final int unrealizedPnlInitialAssetWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, unrealizedPnlInitialAssetWeight);
    return Filter.createMemCompFilter(UNREALIZED_PNL_INITIAL_ASSET_WEIGHT_OFFSET, _data);
  }

  public static Filter createUnrealizedPnlMaintenanceAssetWeightFilter(final int unrealizedPnlMaintenanceAssetWeight) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, unrealizedPnlMaintenanceAssetWeight);
    return Filter.createMemCompFilter(UNREALIZED_PNL_MAINTENANCE_ASSET_WEIGHT_OFFSET, _data);
  }

  public static Filter createNumberOfUsersWithBaseFilter(final int numberOfUsersWithBase) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, numberOfUsersWithBase);
    return Filter.createMemCompFilter(NUMBER_OF_USERS_WITH_BASE_OFFSET, _data);
  }

  public static Filter createNumberOfUsersFilter(final int numberOfUsers) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, numberOfUsers);
    return Filter.createMemCompFilter(NUMBER_OF_USERS_OFFSET, _data);
  }

  public static Filter createMarketIndexFilter(final int marketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, marketIndex);
    return Filter.createMemCompFilter(MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createStatusFilter(final MarketStatus status) {
    return Filter.createMemCompFilter(STATUS_OFFSET, status.write());
  }

  public static Filter createContractTypeFilter(final ContractType contractType) {
    return Filter.createMemCompFilter(CONTRACT_TYPE_OFFSET, contractType.write());
  }

  public static Filter createContractTierFilter(final ContractTier contractTier) {
    return Filter.createMemCompFilter(CONTRACT_TIER_OFFSET, contractTier.write());
  }

  public static Filter createPausedOperationsFilter(final int pausedOperations) {
    return Filter.createMemCompFilter(PAUSED_OPERATIONS_OFFSET, new byte[]{(byte) pausedOperations});
  }

  public static Filter createQuoteSpotMarketIndexFilter(final int quoteSpotMarketIndex) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, quoteSpotMarketIndex);
    return Filter.createMemCompFilter(QUOTE_SPOT_MARKET_INDEX_OFFSET, _data);
  }

  public static Filter createFeeAdjustmentFilter(final int feeAdjustment) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, feeAdjustment);
    return Filter.createMemCompFilter(FEE_ADJUSTMENT_OFFSET, _data);
  }

  public static Filter createFuelBoostPositionFilter(final int fuelBoostPosition) {
    return Filter.createMemCompFilter(FUEL_BOOST_POSITION_OFFSET, new byte[]{(byte) fuelBoostPosition});
  }

  public static Filter createFuelBoostTakerFilter(final int fuelBoostTaker) {
    return Filter.createMemCompFilter(FUEL_BOOST_TAKER_OFFSET, new byte[]{(byte) fuelBoostTaker});
  }

  public static Filter createFuelBoostMakerFilter(final int fuelBoostMaker) {
    return Filter.createMemCompFilter(FUEL_BOOST_MAKER_OFFSET, new byte[]{(byte) fuelBoostMaker});
  }

  public static Filter createPoolIdFilter(final int poolId) {
    return Filter.createMemCompFilter(POOL_ID_OFFSET, new byte[]{(byte) poolId});
  }

  public static Filter createHighLeverageMarginRatioInitialFilter(final int highLeverageMarginRatioInitial) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, highLeverageMarginRatioInitial);
    return Filter.createMemCompFilter(HIGH_LEVERAGE_MARGIN_RATIO_INITIAL_OFFSET, _data);
  }

  public static Filter createHighLeverageMarginRatioMaintenanceFilter(final int highLeverageMarginRatioMaintenance) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, highLeverageMarginRatioMaintenance);
    return Filter.createMemCompFilter(HIGH_LEVERAGE_MARGIN_RATIO_MAINTENANCE_OFFSET, _data);
  }

  public static Filter createProtectedMakerLimitPriceDivisorFilter(final int protectedMakerLimitPriceDivisor) {
    return Filter.createMemCompFilter(PROTECTED_MAKER_LIMIT_PRICE_DIVISOR_OFFSET, new byte[]{(byte) protectedMakerLimitPriceDivisor});
  }

  public static Filter createProtectedMakerDynamicDivisorFilter(final int protectedMakerDynamicDivisor) {
    return Filter.createMemCompFilter(PROTECTED_MAKER_DYNAMIC_DIVISOR_OFFSET, new byte[]{(byte) protectedMakerDynamicDivisor});
  }

  public static Filter createPadding1Filter(final int padding1) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, padding1);
    return Filter.createMemCompFilter(PADDING_1_OFFSET, _data);
  }

  public static Filter createLastFillPriceFilter(final long lastFillPrice) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lastFillPrice);
    return Filter.createMemCompFilter(LAST_FILL_PRICE_OFFSET, _data);
  }

  public static PerpMarket read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PerpMarket read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static PerpMarket read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PerpMarket> FACTORY = PerpMarket::read;

  public static PerpMarket read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var amm = AMM.read(_data, i);
    i += Borsh.len(amm);
    final var pnlPool = PoolBalance.read(_data, i);
    i += Borsh.len(pnlPool);
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var insuranceClaim = InsuranceClaim.read(_data, i);
    i += Borsh.len(insuranceClaim);
    final var unrealizedPnlMaxImbalance = getInt64LE(_data, i);
    i += 8;
    final var expiryTs = getInt64LE(_data, i);
    i += 8;
    final var expiryPrice = getInt64LE(_data, i);
    i += 8;
    final var nextFillRecordId = getInt64LE(_data, i);
    i += 8;
    final var nextFundingRateRecordId = getInt64LE(_data, i);
    i += 8;
    final var nextCurveRecordId = getInt64LE(_data, i);
    i += 8;
    final var imfFactor = getInt32LE(_data, i);
    i += 4;
    final var unrealizedPnlImfFactor = getInt32LE(_data, i);
    i += 4;
    final var liquidatorFee = getInt32LE(_data, i);
    i += 4;
    final var ifLiquidationFee = getInt32LE(_data, i);
    i += 4;
    final var marginRatioInitial = getInt32LE(_data, i);
    i += 4;
    final var marginRatioMaintenance = getInt32LE(_data, i);
    i += 4;
    final var unrealizedPnlInitialAssetWeight = getInt32LE(_data, i);
    i += 4;
    final var unrealizedPnlMaintenanceAssetWeight = getInt32LE(_data, i);
    i += 4;
    final var numberOfUsersWithBase = getInt32LE(_data, i);
    i += 4;
    final var numberOfUsers = getInt32LE(_data, i);
    i += 4;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var status = MarketStatus.read(_data, i);
    i += Borsh.len(status);
    final var contractType = ContractType.read(_data, i);
    i += Borsh.len(contractType);
    final var contractTier = ContractTier.read(_data, i);
    i += Borsh.len(contractTier);
    final var pausedOperations = _data[i] & 0xFF;
    ++i;
    final var quoteSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var feeAdjustment = getInt16LE(_data, i);
    i += 2;
    final var fuelBoostPosition = _data[i] & 0xFF;
    ++i;
    final var fuelBoostTaker = _data[i] & 0xFF;
    ++i;
    final var fuelBoostMaker = _data[i] & 0xFF;
    ++i;
    final var poolId = _data[i] & 0xFF;
    ++i;
    final var highLeverageMarginRatioInitial = getInt16LE(_data, i);
    i += 2;
    final var highLeverageMarginRatioMaintenance = getInt16LE(_data, i);
    i += 2;
    final var protectedMakerLimitPriceDivisor = _data[i] & 0xFF;
    ++i;
    final var protectedMakerDynamicDivisor = _data[i] & 0xFF;
    ++i;
    final var padding1 = getInt32LE(_data, i);
    i += 4;
    final var lastFillPrice = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[24];
    Borsh.readArray(padding, _data, i);
    return new PerpMarket(_address,
                          discriminator,
                          pubkey,
                          amm,
                          pnlPool,
                          name,
                          insuranceClaim,
                          unrealizedPnlMaxImbalance,
                          expiryTs,
                          expiryPrice,
                          nextFillRecordId,
                          nextFundingRateRecordId,
                          nextCurveRecordId,
                          imfFactor,
                          unrealizedPnlImfFactor,
                          liquidatorFee,
                          ifLiquidationFee,
                          marginRatioInitial,
                          marginRatioMaintenance,
                          unrealizedPnlInitialAssetWeight,
                          unrealizedPnlMaintenanceAssetWeight,
                          numberOfUsersWithBase,
                          numberOfUsers,
                          marketIndex,
                          status,
                          contractType,
                          contractTier,
                          pausedOperations,
                          quoteSpotMarketIndex,
                          feeAdjustment,
                          fuelBoostPosition,
                          fuelBoostTaker,
                          fuelBoostMaker,
                          poolId,
                          highLeverageMarginRatioInitial,
                          highLeverageMarginRatioMaintenance,
                          protectedMakerLimitPriceDivisor,
                          protectedMakerDynamicDivisor,
                          padding1,
                          lastFillPrice,
                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.write(amm, _data, i);
    i += Borsh.write(pnlPool, _data, i);
    i += Borsh.writeArrayChecked(name, 32, _data, i);
    i += Borsh.write(insuranceClaim, _data, i);
    putInt64LE(_data, i, unrealizedPnlMaxImbalance);
    i += 8;
    putInt64LE(_data, i, expiryTs);
    i += 8;
    putInt64LE(_data, i, expiryPrice);
    i += 8;
    putInt64LE(_data, i, nextFillRecordId);
    i += 8;
    putInt64LE(_data, i, nextFundingRateRecordId);
    i += 8;
    putInt64LE(_data, i, nextCurveRecordId);
    i += 8;
    putInt32LE(_data, i, imfFactor);
    i += 4;
    putInt32LE(_data, i, unrealizedPnlImfFactor);
    i += 4;
    putInt32LE(_data, i, liquidatorFee);
    i += 4;
    putInt32LE(_data, i, ifLiquidationFee);
    i += 4;
    putInt32LE(_data, i, marginRatioInitial);
    i += 4;
    putInt32LE(_data, i, marginRatioMaintenance);
    i += 4;
    putInt32LE(_data, i, unrealizedPnlInitialAssetWeight);
    i += 4;
    putInt32LE(_data, i, unrealizedPnlMaintenanceAssetWeight);
    i += 4;
    putInt32LE(_data, i, numberOfUsersWithBase);
    i += 4;
    putInt32LE(_data, i, numberOfUsers);
    i += 4;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(status, _data, i);
    i += Borsh.write(contractType, _data, i);
    i += Borsh.write(contractTier, _data, i);
    _data[i] = (byte) pausedOperations;
    ++i;
    putInt16LE(_data, i, quoteSpotMarketIndex);
    i += 2;
    putInt16LE(_data, i, feeAdjustment);
    i += 2;
    _data[i] = (byte) fuelBoostPosition;
    ++i;
    _data[i] = (byte) fuelBoostTaker;
    ++i;
    _data[i] = (byte) fuelBoostMaker;
    ++i;
    _data[i] = (byte) poolId;
    ++i;
    putInt16LE(_data, i, highLeverageMarginRatioInitial);
    i += 2;
    putInt16LE(_data, i, highLeverageMarginRatioMaintenance);
    i += 2;
    _data[i] = (byte) protectedMakerLimitPriceDivisor;
    ++i;
    _data[i] = (byte) protectedMakerDynamicDivisor;
    ++i;
    putInt32LE(_data, i, padding1);
    i += 4;
    putInt64LE(_data, i, lastFillPrice);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 24, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
