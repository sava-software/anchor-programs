package software.sava.anchor.programs.pump.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record Global(PublicKey _address,
                     Discriminator discriminator,
                     boolean initialized,
                     PublicKey authority,
                     PublicKey feeRecipient,
                     long initialVirtualTokenReserves,
                     long initialVirtualSolReserves,
                     long initialRealTokenReserves,
                     long tokenTotalSupply,
                     long feeBasisPoints) implements Borsh {

  public static final int BYTES = 113;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(167, 232, 232, 177, 200, 108, 114, 127);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int INITIALIZED_OFFSET = 8;
  public static final int AUTHORITY_OFFSET = 9;
  public static final int FEE_RECIPIENT_OFFSET = 41;
  public static final int INITIAL_VIRTUAL_TOKEN_RESERVES_OFFSET = 73;
  public static final int INITIAL_VIRTUAL_SOL_RESERVES_OFFSET = 81;
  public static final int INITIAL_REAL_TOKEN_RESERVES_OFFSET = 89;
  public static final int TOKEN_TOTAL_SUPPLY_OFFSET = 97;
  public static final int FEE_BASIS_POINTS_OFFSET = 105;

  public static Filter createInitializedFilter(final boolean initialized) {
    return Filter.createMemCompFilter(INITIALIZED_OFFSET, new byte[]{(byte) (initialized ? 1 : 0)});
  }

  public static Filter createAuthorityFilter(final PublicKey authority) {
    return Filter.createMemCompFilter(AUTHORITY_OFFSET, authority);
  }

  public static Filter createFeeRecipientFilter(final PublicKey feeRecipient) {
    return Filter.createMemCompFilter(FEE_RECIPIENT_OFFSET, feeRecipient);
  }

  public static Filter createInitialVirtualTokenReservesFilter(final long initialVirtualTokenReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initialVirtualTokenReserves);
    return Filter.createMemCompFilter(INITIAL_VIRTUAL_TOKEN_RESERVES_OFFSET, _data);
  }

  public static Filter createInitialVirtualSolReservesFilter(final long initialVirtualSolReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initialVirtualSolReserves);
    return Filter.createMemCompFilter(INITIAL_VIRTUAL_SOL_RESERVES_OFFSET, _data);
  }

  public static Filter createInitialRealTokenReservesFilter(final long initialRealTokenReserves) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, initialRealTokenReserves);
    return Filter.createMemCompFilter(INITIAL_REAL_TOKEN_RESERVES_OFFSET, _data);
  }

  public static Filter createTokenTotalSupplyFilter(final long tokenTotalSupply) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, tokenTotalSupply);
    return Filter.createMemCompFilter(TOKEN_TOTAL_SUPPLY_OFFSET, _data);
  }

  public static Filter createFeeBasisPointsFilter(final long feeBasisPoints) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, feeBasisPoints);
    return Filter.createMemCompFilter(FEE_BASIS_POINTS_OFFSET, _data);
  }

  public static Global read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Global read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Global> FACTORY = Global::read;

  public static Global read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var initialized = _data[i] == 1;
    ++i;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var feeRecipient = readPubKey(_data, i);
    i += 32;
    final var initialVirtualTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var initialVirtualSolReserves = getInt64LE(_data, i);
    i += 8;
    final var initialRealTokenReserves = getInt64LE(_data, i);
    i += 8;
    final var tokenTotalSupply = getInt64LE(_data, i);
    i += 8;
    final var feeBasisPoints = getInt64LE(_data, i);
    return new Global(_address,
                      discriminator,
                      initialized,
                      authority,
                      feeRecipient,
                      initialVirtualTokenReserves,
                      initialVirtualSolReserves,
                      initialRealTokenReserves,
                      tokenTotalSupply,
                      feeBasisPoints);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    _data[i] = (byte) (initialized ? 1 : 0);
    ++i;
    authority.write(_data, i);
    i += 32;
    feeRecipient.write(_data, i);
    i += 32;
    putInt64LE(_data, i, initialVirtualTokenReserves);
    i += 8;
    putInt64LE(_data, i, initialVirtualSolReserves);
    i += 8;
    putInt64LE(_data, i, initialRealTokenReserves);
    i += 8;
    putInt64LE(_data, i, tokenTotalSupply);
    i += 8;
    putInt64LE(_data, i, feeBasisPoints);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
