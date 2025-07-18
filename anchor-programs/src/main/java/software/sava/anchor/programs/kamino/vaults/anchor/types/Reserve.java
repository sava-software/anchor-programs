package software.sava.anchor.programs.kamino.vaults.anchor.types;

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

public record Reserve(PublicKey _address,
                      Discriminator discriminator,
                      // Version of the reserve
                      long version,
                      // Last slot when supply and rates updated
                      LastUpdate lastUpdate,
                      // Lending market address
                      PublicKey lendingMarket,
                      PublicKey farmCollateral,
                      PublicKey farmDebt,
                      // Reserve liquidity
                      ReserveLiquidity liquidity,
                      long[] reserveLiquidityPadding,
                      // Reserve collateral
                      ReserveCollateral collateral,
                      long[] reserveCollateralPadding,
                      // Reserve configuration values
                      ReserveConfig config,
                      long[] configPadding,
                      long borrowedAmountOutsideElevationGroup,
                      // Amount of token borrowed in lamport of debt asset in the given
                      // elevation group when this reserve is part of the collaterals.
                      long[] borrowedAmountsAgainstThisReserveInElevationGroups,
                      long[] padding) implements Borsh {

  public static final int BYTES = 8624;
  public static final int RESERVE_LIQUIDITY_PADDING_LEN = 150;
  public static final int RESERVE_COLLATERAL_PADDING_LEN = 150;
  public static final int CONFIG_PADDING_LEN = 115;
  public static final int BORROWED_AMOUNTS_AGAINST_THIS_RESERVE_IN_ELEVATION_GROUPS_LEN = 32;
  public static final int PADDING_LEN = 207;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int VERSION_OFFSET = 8;
  public static final int LAST_UPDATE_OFFSET = 16;
  public static final int LENDING_MARKET_OFFSET = 32;
  public static final int FARM_COLLATERAL_OFFSET = 64;
  public static final int FARM_DEBT_OFFSET = 96;
  public static final int LIQUIDITY_OFFSET = 128;
  public static final int RESERVE_LIQUIDITY_PADDING_OFFSET = 1360;
  public static final int COLLATERAL_OFFSET = 2560;
  public static final int RESERVE_COLLATERAL_PADDING_OFFSET = 3656;
  public static final int CONFIG_OFFSET = 4856;
  public static final int CONFIG_PADDING_OFFSET = 5784;
  public static final int BORROWED_AMOUNT_OUTSIDE_ELEVATION_GROUP_OFFSET = 6704;
  public static final int BORROWED_AMOUNTS_AGAINST_THIS_RESERVE_IN_ELEVATION_GROUPS_OFFSET = 6712;
  public static final int PADDING_OFFSET = 6968;

  public static Filter createVersionFilter(final long version) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, version);
    return Filter.createMemCompFilter(VERSION_OFFSET, _data);
  }

  public static Filter createLastUpdateFilter(final LastUpdate lastUpdate) {
    return Filter.createMemCompFilter(LAST_UPDATE_OFFSET, lastUpdate.write());
  }

  public static Filter createLendingMarketFilter(final PublicKey lendingMarket) {
    return Filter.createMemCompFilter(LENDING_MARKET_OFFSET, lendingMarket);
  }

  public static Filter createFarmCollateralFilter(final PublicKey farmCollateral) {
    return Filter.createMemCompFilter(FARM_COLLATERAL_OFFSET, farmCollateral);
  }

  public static Filter createFarmDebtFilter(final PublicKey farmDebt) {
    return Filter.createMemCompFilter(FARM_DEBT_OFFSET, farmDebt);
  }

  public static Filter createBorrowedAmountOutsideElevationGroupFilter(final long borrowedAmountOutsideElevationGroup) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, borrowedAmountOutsideElevationGroup);
    return Filter.createMemCompFilter(BORROWED_AMOUNT_OUTSIDE_ELEVATION_GROUP_OFFSET, _data);
  }

  public static Reserve read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Reserve read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static Reserve read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Reserve> FACTORY = Reserve::read;

  public static Reserve read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var version = getInt64LE(_data, i);
    i += 8;
    final var lastUpdate = LastUpdate.read(_data, i);
    i += Borsh.len(lastUpdate);
    final var lendingMarket = readPubKey(_data, i);
    i += 32;
    final var farmCollateral = readPubKey(_data, i);
    i += 32;
    final var farmDebt = readPubKey(_data, i);
    i += 32;
    final var liquidity = ReserveLiquidity.read(_data, i);
    i += Borsh.len(liquidity);
    final var reserveLiquidityPadding = new long[150];
    i += Borsh.readArray(reserveLiquidityPadding, _data, i);
    final var collateral = ReserveCollateral.read(_data, i);
    i += Borsh.len(collateral);
    final var reserveCollateralPadding = new long[150];
    i += Borsh.readArray(reserveCollateralPadding, _data, i);
    final var config = ReserveConfig.read(_data, i);
    i += Borsh.len(config);
    final var configPadding = new long[115];
    i += Borsh.readArray(configPadding, _data, i);
    final var borrowedAmountOutsideElevationGroup = getInt64LE(_data, i);
    i += 8;
    final var borrowedAmountsAgainstThisReserveInElevationGroups = new long[32];
    i += Borsh.readArray(borrowedAmountsAgainstThisReserveInElevationGroups, _data, i);
    final var padding = new long[207];
    Borsh.readArray(padding, _data, i);
    return new Reserve(_address,
                       discriminator,
                       version,
                       lastUpdate,
                       lendingMarket,
                       farmCollateral,
                       farmDebt,
                       liquidity,
                       reserveLiquidityPadding,
                       collateral,
                       reserveCollateralPadding,
                       config,
                       configPadding,
                       borrowedAmountOutsideElevationGroup,
                       borrowedAmountsAgainstThisReserveInElevationGroups,
                       padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.write(lastUpdate, _data, i);
    lendingMarket.write(_data, i);
    i += 32;
    farmCollateral.write(_data, i);
    i += 32;
    farmDebt.write(_data, i);
    i += 32;
    i += Borsh.write(liquidity, _data, i);
    i += Borsh.writeArray(reserveLiquidityPadding, _data, i);
    i += Borsh.write(collateral, _data, i);
    i += Borsh.writeArray(reserveCollateralPadding, _data, i);
    i += Borsh.write(config, _data, i);
    i += Borsh.writeArray(configPadding, _data, i);
    putInt64LE(_data, i, borrowedAmountOutsideElevationGroup);
    i += 8;
    i += Borsh.writeArray(borrowedAmountsAgainstThisReserveInElevationGroups, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
