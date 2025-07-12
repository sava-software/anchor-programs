package software.sava.anchor.programs.kamino.lend.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Obligation collateral state
public record ObligationCollateral(// Reserve collateral is deposited to
                                   PublicKey depositReserve,
                                   // Amount of collateral deposited
                                   long depositedAmount,
                                   // Collateral market value in quote currency (scaled fraction)
                                   BigInteger marketValueSf,
                                   // Debt amount (lamport) taken against this collateral.
                                   // (only meaningful if this obligation is part of an elevation group, otherwise 0)
                                   // This is only indicative of the debt computed on the last refresh obligation.
                                   // If the obligation have multiple collateral this value is the same for all of them.
                                   long borrowedAmountAgainstThisCollateralInElevationGroup,
                                   long[] padding) implements Borsh {

  public static final int BYTES = 136;
  public static final int PADDING_LEN = 9;

  public static ObligationCollateral read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var depositReserve = readPubKey(_data, i);
    i += 32;
    final var depositedAmount = getInt64LE(_data, i);
    i += 8;
    final var marketValueSf = getInt128LE(_data, i);
    i += 16;
    final var borrowedAmountAgainstThisCollateralInElevationGroup = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[9];
    Borsh.readArray(padding, _data, i);
    return new ObligationCollateral(depositReserve,
                                    depositedAmount,
                                    marketValueSf,
                                    borrowedAmountAgainstThisCollateralInElevationGroup,
                                    padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    depositReserve.write(_data, i);
    i += 32;
    putInt64LE(_data, i, depositedAmount);
    i += 8;
    putInt128LE(_data, i, marketValueSf);
    i += 16;
    putInt64LE(_data, i, borrowedAmountAgainstThisCollateralInElevationGroup);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
