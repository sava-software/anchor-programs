package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FuelSeasonRecord(long ts,
                               PublicKey authority,
                               BigInteger fuelInsurance,
                               BigInteger fuelDeposits,
                               BigInteger fuelBorrows,
                               BigInteger fuelPositions,
                               BigInteger fuelTaker,
                               BigInteger fuelMaker,
                               BigInteger fuelTotal) implements Borsh {

  public static final int BYTES = 152;

  public static FuelSeasonRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
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
    final var fuelTotal = getInt128LE(_data, i);
    return new FuelSeasonRecord(ts,
                                authority,
                                fuelInsurance,
                                fuelDeposits,
                                fuelBorrows,
                                fuelPositions,
                                fuelTaker,
                                fuelMaker,
                                fuelTotal);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
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
    putInt128LE(_data, i, fuelTotal);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
