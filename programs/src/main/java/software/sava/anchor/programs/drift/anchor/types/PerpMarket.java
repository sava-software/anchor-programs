package software.sava.anchor.programs.drift.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

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
                         int[] name,
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
                         int[] padding) implements Borsh {

  public static final int PUBKEY_OFFSET = 8;
  public static final int AMM_OFFSET = 40;
  public static final int PNL_POOL_OFFSET = 1000;

  public static Filter createPubkeyFilter(final PublicKey pubkey) {
    return Filter.createMemCompFilter(PUBKEY_OFFSET, pubkey);
  }

  public static Filter createPnlPoolFilter(final PoolBalance pnlPool) {
    return Filter.createMemCompFilter(PNL_POOL_OFFSET, pnlPool.write());
  }

  public static PerpMarket read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static PerpMarket read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], PerpMarket> FACTORY = PerpMarket::read;

  public static PerpMarket read(final PublicKey _address, final byte[] _data, final int offset) {
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var amm = AMM.read(_data, i);
    i += Borsh.len(amm);
    final var pnlPool = PoolBalance.read(_data, i);
    i += Borsh.len(pnlPool);
    final var name = Borsh.readArray(new int[32], _data, i);
    i += Borsh.fixedLen(name);
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
    final var padding = Borsh.readArray(new int[43], _data, i);
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
                          padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.write(amm, _data, i);
    i += Borsh.write(pnlPool, _data, i);
    i += Borsh.fixedWrite(name, _data, i);
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
    i += Borsh.fixedWrite(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32
         + Borsh.len(amm)
         + Borsh.len(pnlPool)
         + Borsh.fixedLen(name)
         + Borsh.len(insuranceClaim)
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
         + 2
         + Borsh.len(status)
         + Borsh.len(contractType)
         + Borsh.len(contractTier)
         + 1
         + 2
         + 2
         + 1
         + 1
         + 1
         + Borsh.fixedLen(padding);
  }
}
