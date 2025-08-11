package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Custody(PublicKey _address,
                      Discriminator discriminator,
                      PublicKey pool,
                      PublicKey mint,
                      PublicKey tokenAccount,
                      int decimals,
                      boolean isStable,
                      boolean depegAdjustment,
                      boolean isVirtual,
                      boolean distributeRewards,
                      OracleParams oracle,
                      PricingParams pricing,
                      Permissions permissions,
                      Fees fees,
                      BorrowRateParams borrowRate,
                      long rewardThreshold,
                      Assets assets,
                      FeesStats feesStats,
                      BorrowRateState borrowRateState,
                      int bump,
                      int tokenAccountBump,
                      boolean token22,
                      int uid,
                      long reservedAmount,
                      long minReserveUsd,
                      long limitPriceBufferBps,
                      byte[] padding) implements Borsh {

  public static final int BYTES = 688;
  public static final int PADDING_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int POOL_OFFSET = 8;
  public static final int MINT_OFFSET = 40;
  public static final int TOKEN_ACCOUNT_OFFSET = 72;
  public static final int DECIMALS_OFFSET = 104;
  public static final int IS_STABLE_OFFSET = 105;
  public static final int DEPEG_ADJUSTMENT_OFFSET = 106;
  public static final int IS_VIRTUAL_OFFSET = 107;
  public static final int DISTRIBUTE_REWARDS_OFFSET = 108;
  public static final int ORACLE_OFFSET = 109;
  public static final int PRICING_OFFSET = 198;
  public static final int PERMISSIONS_OFFSET = 286;
  public static final int FEES_OFFSET = 299;
  public static final int BORROW_RATE_OFFSET = 468;
  public static final int REWARD_THRESHOLD_OFFSET = 500;
  public static final int ASSETS_OFFSET = 508;
  public static final int FEES_STATS_OFFSET = 532;
  public static final int BORROW_RATE_STATE_OFFSET = 596;
  public static final int BUMP_OFFSET = 628;
  public static final int TOKEN_ACCOUNT_BUMP_OFFSET = 629;
  public static final int TOKEN_22_OFFSET = 630;
  public static final int UID_OFFSET = 631;
  public static final int RESERVED_AMOUNT_OFFSET = 632;
  public static final int MIN_RESERVE_USD_OFFSET = 640;
  public static final int LIMIT_PRICE_BUFFER_BPS_OFFSET = 648;
  public static final int PADDING_OFFSET = 656;

  public static Filter createPoolFilter(final PublicKey pool) {
    return Filter.createMemCompFilter(POOL_OFFSET, pool);
  }

  public static Filter createMintFilter(final PublicKey mint) {
    return Filter.createMemCompFilter(MINT_OFFSET, mint);
  }

  public static Filter createTokenAccountFilter(final PublicKey tokenAccount) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_OFFSET, tokenAccount);
  }

  public static Filter createDecimalsFilter(final int decimals) {
    return Filter.createMemCompFilter(DECIMALS_OFFSET, new byte[]{(byte) decimals});
  }

  public static Filter createIsStableFilter(final boolean isStable) {
    return Filter.createMemCompFilter(IS_STABLE_OFFSET, new byte[]{(byte) (isStable ? 1 : 0)});
  }

  public static Filter createDepegAdjustmentFilter(final boolean depegAdjustment) {
    return Filter.createMemCompFilter(DEPEG_ADJUSTMENT_OFFSET, new byte[]{(byte) (depegAdjustment ? 1 : 0)});
  }

  public static Filter createIsVirtualFilter(final boolean isVirtual) {
    return Filter.createMemCompFilter(IS_VIRTUAL_OFFSET, new byte[]{(byte) (isVirtual ? 1 : 0)});
  }

  public static Filter createDistributeRewardsFilter(final boolean distributeRewards) {
    return Filter.createMemCompFilter(DISTRIBUTE_REWARDS_OFFSET, new byte[]{(byte) (distributeRewards ? 1 : 0)});
  }

  public static Filter createOracleFilter(final OracleParams oracle) {
    return Filter.createMemCompFilter(ORACLE_OFFSET, oracle.write());
  }

  public static Filter createPricingFilter(final PricingParams pricing) {
    return Filter.createMemCompFilter(PRICING_OFFSET, pricing.write());
  }

  public static Filter createPermissionsFilter(final Permissions permissions) {
    return Filter.createMemCompFilter(PERMISSIONS_OFFSET, permissions.write());
  }

  public static Filter createBorrowRateFilter(final BorrowRateParams borrowRate) {
    return Filter.createMemCompFilter(BORROW_RATE_OFFSET, borrowRate.write());
  }

  public static Filter createRewardThresholdFilter(final long rewardThreshold) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, rewardThreshold);
    return Filter.createMemCompFilter(REWARD_THRESHOLD_OFFSET, _data);
  }

  public static Filter createAssetsFilter(final Assets assets) {
    return Filter.createMemCompFilter(ASSETS_OFFSET, assets.write());
  }

  public static Filter createFeesStatsFilter(final FeesStats feesStats) {
    return Filter.createMemCompFilter(FEES_STATS_OFFSET, feesStats.write());
  }

  public static Filter createBorrowRateStateFilter(final BorrowRateState borrowRateState) {
    return Filter.createMemCompFilter(BORROW_RATE_STATE_OFFSET, borrowRateState.write());
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createTokenAccountBumpFilter(final int tokenAccountBump) {
    return Filter.createMemCompFilter(TOKEN_ACCOUNT_BUMP_OFFSET, new byte[]{(byte) tokenAccountBump});
  }

  public static Filter createToken22Filter(final boolean token22) {
    return Filter.createMemCompFilter(TOKEN_22_OFFSET, new byte[]{(byte) (token22 ? 1 : 0)});
  }

  public static Filter createUidFilter(final int uid) {
    return Filter.createMemCompFilter(UID_OFFSET, new byte[]{(byte) uid});
  }

  public static Filter createReservedAmountFilter(final long reservedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, reservedAmount);
    return Filter.createMemCompFilter(RESERVED_AMOUNT_OFFSET, _data);
  }

  public static Filter createMinReserveUsdFilter(final long minReserveUsd) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, minReserveUsd);
    return Filter.createMemCompFilter(MIN_RESERVE_USD_OFFSET, _data);
  }

  public static Filter createLimitPriceBufferBpsFilter(final long limitPriceBufferBps) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, limitPriceBufferBps);
    return Filter.createMemCompFilter(LIMIT_PRICE_BUFFER_BPS_OFFSET, _data);
  }

  public static Custody read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Custody read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Custody read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Custody> FACTORY = Custody::read;

  public static Custody read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var pool = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var tokenAccount = readPubKey(_data, i);
    i += 32;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var isStable = _data[i] == 1;
    ++i;
    final var depegAdjustment = _data[i] == 1;
    ++i;
    final var isVirtual = _data[i] == 1;
    ++i;
    final var distributeRewards = _data[i] == 1;
    ++i;
    final var oracle = OracleParams.read(_data, i);
    i += Borsh.len(oracle);
    final var pricing = PricingParams.read(_data, i);
    i += Borsh.len(pricing);
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var fees = Fees.read(_data, i);
    i += Borsh.len(fees);
    final var borrowRate = BorrowRateParams.read(_data, i);
    i += Borsh.len(borrowRate);
    final var rewardThreshold = getInt64LE(_data, i);
    i += 8;
    final var assets = Assets.read(_data, i);
    i += Borsh.len(assets);
    final var feesStats = FeesStats.read(_data, i);
    i += Borsh.len(feesStats);
    final var borrowRateState = BorrowRateState.read(_data, i);
    i += Borsh.len(borrowRateState);
    final var bump = _data[i] & 0xFF;
    ++i;
    final var tokenAccountBump = _data[i] & 0xFF;
    ++i;
    final var token22 = _data[i] == 1;
    ++i;
    final var uid = _data[i] & 0xFF;
    ++i;
    final var reservedAmount = getInt64LE(_data, i);
    i += 8;
    final var minReserveUsd = getInt64LE(_data, i);
    i += 8;
    final var limitPriceBufferBps = getInt64LE(_data, i);
    i += 8;
    final var padding = new byte[32];
    Borsh.readArray(padding, _data, i);
    return new Custody(_address,
                       discriminator,
                       pool,
                       mint,
                       tokenAccount,
                       decimals,
                       isStable,
                       depegAdjustment,
                       isVirtual,
                       distributeRewards,
                       oracle,
                       pricing,
                       permissions,
                       fees,
                       borrowRate,
                       rewardThreshold,
                       assets,
                       feesStats,
                       borrowRateState,
                       bump,
                       tokenAccountBump,
                       token22,
                       uid,
                       reservedAmount,
                       minReserveUsd,
                       limitPriceBufferBps,
                       padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    pool.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    tokenAccount.write(_data, i);
    i += 32;
    _data[i] = (byte) decimals;
    ++i;
    _data[i] = (byte) (isStable ? 1 : 0);
    ++i;
    _data[i] = (byte) (depegAdjustment ? 1 : 0);
    ++i;
    _data[i] = (byte) (isVirtual ? 1 : 0);
    ++i;
    _data[i] = (byte) (distributeRewards ? 1 : 0);
    ++i;
    i += Borsh.write(oracle, _data, i);
    i += Borsh.write(pricing, _data, i);
    i += Borsh.write(permissions, _data, i);
    i += Borsh.write(fees, _data, i);
    i += Borsh.write(borrowRate, _data, i);
    putInt64LE(_data, i, rewardThreshold);
    i += 8;
    i += Borsh.write(assets, _data, i);
    i += Borsh.write(feesStats, _data, i);
    i += Borsh.write(borrowRateState, _data, i);
    _data[i] = (byte) bump;
    ++i;
    _data[i] = (byte) tokenAccountBump;
    ++i;
    _data[i] = (byte) (token22 ? 1 : 0);
    ++i;
    _data[i] = (byte) uid;
    ++i;
    putInt64LE(_data, i, reservedAmount);
    i += 8;
    putInt64LE(_data, i, minReserveUsd);
    i += 8;
    putInt64LE(_data, i, limitPriceBufferBps);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
