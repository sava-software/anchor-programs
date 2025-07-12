package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record FuelOverflow(PublicKey _address,
                           Discriminator discriminator,
                           // The authority of this overflow account
                           PublicKey authority,
                           BigInteger fuelInsurance,
                           BigInteger fuelDeposits,
                           BigInteger fuelBorrows,
                           BigInteger fuelPositions,
                           BigInteger fuelTaker,
                           BigInteger fuelMaker,
                           int lastFuelSweepTs,
                           int lastResetTs,
                           BigInteger[] padding) implements Borsh {

  public static final int BYTES = 240;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int AUTHORITY_OFFSET = 8;
  public static final int FUEL_INSURANCE_OFFSET = 40;
  public static final int FUEL_DEPOSITS_OFFSET = 56;
  public static final int FUEL_BORROWS_OFFSET = 72;
  public static final int FUEL_POSITIONS_OFFSET = 88;
  public static final int FUEL_TAKER_OFFSET = 104;
  public static final int FUEL_MAKER_OFFSET = 120;
  public static final int LAST_FUEL_SWEEP_TS_OFFSET = 136;
  public static final int LAST_RESET_TS_OFFSET = 140;
  public static final int PADDING_OFFSET = 144;

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createFuelInsuranceFilter(final BigInteger fuelInsurance) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelInsurance);
    return Filter.createMemCompFilter(FUEL_INSURANCE_OFFSET, _data);
  }

  public static Filter createFuelDepositsFilter(final BigInteger fuelDeposits) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelDeposits);
    return Filter.createMemCompFilter(FUEL_DEPOSITS_OFFSET, _data);
  }

  public static Filter createFuelBorrowsFilter(final BigInteger fuelBorrows) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelBorrows);
    return Filter.createMemCompFilter(FUEL_BORROWS_OFFSET, _data);
  }

  public static Filter createFuelPositionsFilter(final BigInteger fuelPositions) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelPositions);
    return Filter.createMemCompFilter(FUEL_POSITIONS_OFFSET, _data);
  }

  public static Filter createFuelTakerFilter(final BigInteger fuelTaker) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelTaker);
    return Filter.createMemCompFilter(FUEL_TAKER_OFFSET, _data);
  }

  public static Filter createFuelMakerFilter(final BigInteger fuelMaker) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, fuelMaker);
    return Filter.createMemCompFilter(FUEL_MAKER_OFFSET, _data);
  }

  public static Filter createLastFuelSweepTsFilter(final int lastFuelSweepTs) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastFuelSweepTs);
    return Filter.createMemCompFilter(LAST_FUEL_SWEEP_TS_OFFSET, _data);
  }

  public static Filter createLastResetTsFilter(final int lastResetTs) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, lastResetTs);
    return Filter.createMemCompFilter(LAST_RESET_TS_OFFSET, _data);
  }

  public static FuelOverflow read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FuelOverflow read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FuelOverflow read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FuelOverflow> FACTORY = FuelOverflow::read;

  public static FuelOverflow read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var authority = readPubKey(_data, i);
    i += 32;
    final var fuelInsurance = getInt128LE(_data, i);
    i += 16;
    final var fuelDeposits = getInt128LE(_data, i);
    i += 16;
    final var fuelBorrows = getInt128LE(_data, i);
    i += 16;
    final var fuelPositions = getInt128LE(_data, i);
    i += 16;
    final var fuelTaker = getInt128LE(_data, i);
    i += 16;
    final var fuelMaker = getInt128LE(_data, i);
    i += 16;
    final var lastFuelSweepTs = getInt32LE(_data, i);
    i += 4;
    final var lastResetTs = getInt32LE(_data, i);
    i += 4;
    final var padding = new BigInteger[6];
    Borsh.read128Array(padding, _data, i);
    return new FuelOverflow(_address,
                            discriminator,
                            authority,
                            fuelInsurance,
                            fuelDeposits,
                            fuelBorrows,
                            fuelPositions,
                            fuelTaker,
                            fuelMaker,
                            lastFuelSweepTs,
                            lastResetTs,
                            padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    authority.write(_data, i);
    i += 32;
    putInt128LE(_data, i, fuelInsurance);
    i += 16;
    putInt128LE(_data, i, fuelDeposits);
    i += 16;
    putInt128LE(_data, i, fuelBorrows);
    i += 16;
    putInt128LE(_data, i, fuelPositions);
    i += 16;
    putInt128LE(_data, i, fuelTaker);
    i += 16;
    putInt128LE(_data, i, fuelMaker);
    i += 16;
    putInt32LE(_data, i, lastFuelSweepTs);
    i += 4;
    putInt32LE(_data, i, lastResetTs);
    i += 4;
    i += Borsh.write128Array(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
