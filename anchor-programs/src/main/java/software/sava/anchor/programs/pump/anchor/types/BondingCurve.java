package software.sava.anchor.programs.pump.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record BondingCurve(PublicKey _address,
                           Discriminator discriminator,
                           long virtualTokenReserves,
                           long virtualSolReserves,
                           long realTokenReserves,
                           long realSolReserves,
                           long tokenTotalSupply,
                           boolean complete) implements Borsh {

  public static final int BYTES = 49;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(23, 183, 248, 55, 96, 216, 172, 96);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VIRTUAL_TOKEN_RESERVES_OFFSET = 8;
  public static final int VIRTUAL_SOL_RESERVES_OFFSET = 16;
  public static final int REAL_TOKEN_RESERVES_OFFSET = 24;
  public static final int REAL_SOL_RESERVES_OFFSET = 32;
  public static final int TOKEN_TOTAL_SUPPLY_OFFSET = 40;
  public static final int COMPLETE_OFFSET = 48;

  public static Filter createVirtualTokenReservesFilter(final long virtualTokenReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, virtualTokenReserves);
    return Filter.createMemCompFilter(VIRTUAL_TOKEN_RESERVES_OFFSET, _data);
  }

  public static Filter createVirtualSolReservesFilter(final long virtualSolReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, virtualSolReserves);
    return Filter.createMemCompFilter(VIRTUAL_SOL_RESERVES_OFFSET, _data);
  }

  public static Filter createRealTokenReservesFilter(final long realTokenReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, realTokenReserves);
    return Filter.createMemCompFilter(REAL_TOKEN_RESERVES_OFFSET, _data);
  }

  public static Filter createRealSolReservesFilter(final long realSolReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, realSolReserves);
    return Filter.createMemCompFilter(REAL_SOL_RESERVES_OFFSET, _data);
  }

  public static Filter createTokenTotalSupplyFilter(final long tokenTotalSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenTotalSupply);
    return Filter.createMemCompFilter(TOKEN_TOTAL_SUPPLY_OFFSET, _data);
  }

  public static Filter createCompleteFilter(final boolean complete) {
    return Filter.createMemCompFilter(COMPLETE_OFFSET, new byte[]{(byte) (complete ? 1 : 0)});
  }

  public static BondingCurve read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static BondingCurve read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], BondingCurve> FACTORY = BondingCurve::read;

  public static BondingCurve read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var virtualTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var virtualSolReserves = getInt64LE(_data, i);
    i += 8;
    final var realTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var realSolReserves = getInt64LE(_data, i);
    i += 8;
    final var tokenTotalSupply = getInt64LE(_data, i);
    i += 8;
    final var complete = _data[i] == 1;
    return new BondingCurve(_address,
                            discriminator,
                            virtualTokenReserves,
                            virtualSolReserves,
                            realTokenReserves,
                            realSolReserves,
                            tokenTotalSupply,
                            complete);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    putInt64LE(_data, i, virtualTokenReserves);
    i += 8;
    putInt64LE(_data, i, virtualSolReserves);
    i += 8;
    putInt64LE(_data, i, realTokenReserves);
    i += 8;
    putInt64LE(_data, i, realSolReserves);
    i += 8;
    putInt64LE(_data, i, tokenTotalSupply);
    i += 8;
    _data[i] = (byte) (complete ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
