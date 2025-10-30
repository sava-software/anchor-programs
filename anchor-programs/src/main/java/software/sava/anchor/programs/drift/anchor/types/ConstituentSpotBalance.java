package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ConstituentSpotBalance(// The scaled balance of the position. To get the token amount, multiply by the cumulative deposit/borrow
                                     // interest of corresponding market.
                                     // precision: token precision
                                     BigInteger scaledBalance,
                                     // The cumulative deposits/borrows a user has made into a market
                                     // precision: token mint precision
                                     long cumulativeDeposits,
                                     // The market index of the corresponding spot market
                                     int marketIndex,
                                     // Whether the position is deposit or borrow
                                     SpotBalanceType balanceType,
                                     byte[] padding) implements Borsh {

  public static final int BYTES = 32;
  public static final int PADDING_LEN = 5;

  public static ConstituentSpotBalance read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var scaledBalance = getInt128LE(_data, i);
    i += 16;
    final var cumulativeDeposits = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var balanceType = SpotBalanceType.read(_data, i);
    i += Borsh.len(balanceType);
    final var padding = new byte[5];
    Borsh.readArray(padding, _data, i);
    return new ConstituentSpotBalance(scaledBalance,
                                      cumulativeDeposits,
                                      marketIndex,
                                      balanceType,
                                      padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt128LE(_data, i, scaledBalance);
    i += 16;
    putInt64LE(_data, i, cumulativeDeposits);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(balanceType, _data, i);
    i += Borsh.writeArrayChecked(padding, 5, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
