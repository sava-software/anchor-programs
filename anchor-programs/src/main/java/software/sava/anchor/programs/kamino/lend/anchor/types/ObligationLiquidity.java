package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Obligation liquidity state
public record ObligationLiquidity(// Reserve liquidity is borrowed from
                                  PublicKey borrowReserve,
                                  // Borrow rate used for calculating interest (big scaled fraction)
                                  BigFractionBytes cumulativeBorrowRateBsf,
                                  long padding,
                                  // Amount of liquidity borrowed plus interest (scaled fraction)
                                  BigInteger borrowedAmountSf,
                                  // Liquidity market value in quote currency (scaled fraction)
                                  BigInteger marketValueSf,
                                  // Risk adjusted liquidity market value in quote currency - DEBUG ONLY - use market_value instead
                                  BigInteger borrowFactorAdjustedMarketValueSf,
                                  // Amount of liquidity borrowed outside of an elevation group
                                  long borrowedAmountOutsideElevationGroups,
                                  long[] padding2) implements Borsh {

  public static final int BYTES = 200;
  public static final int PADDING_2_LEN = 7;

  public static ObligationLiquidity read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var borrowReserve = readPubKey(_data, i);
    i += 32;
    final var cumulativeBorrowRateBsf = BigFractionBytes.read(_data, i);
    i += Borsh.len(cumulativeBorrowRateBsf);
    final var padding = getInt64LE(_data, i);
    i += 8;
    final var borrowedAmountSf = getInt128LE(_data, i);
    i += 16;
    final var marketValueSf = getInt128LE(_data, i);
    i += 16;
    final var borrowFactorAdjustedMarketValueSf = getInt128LE(_data, i);
    i += 16;
    final var borrowedAmountOutsideElevationGroups = getInt64LE(_data, i);
    i += 8;
    final var padding2 = new long[7];
    Borsh.readArray(padding2, _data, i);
    return new ObligationLiquidity(borrowReserve,
                                   cumulativeBorrowRateBsf,
                                   padding,
                                   borrowedAmountSf,
                                   marketValueSf,
                                   borrowFactorAdjustedMarketValueSf,
                                   borrowedAmountOutsideElevationGroups,
                                   padding2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    borrowReserve.write(_data, i);
    i += 32;
    i += Borsh.write(cumulativeBorrowRateBsf, _data, i);
    putInt64LE(_data, i, padding);
    i += 8;
    putInt128LE(_data, i, borrowedAmountSf);
    i += 16;
    putInt128LE(_data, i, marketValueSf);
    i += 16;
    putInt128LE(_data, i, borrowFactorAdjustedMarketValueSf);
    i += 16;
    putInt64LE(_data, i, borrowedAmountOutsideElevationGroups);
    i += 8;
    i += Borsh.writeArrayChecked(padding2, 7, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
