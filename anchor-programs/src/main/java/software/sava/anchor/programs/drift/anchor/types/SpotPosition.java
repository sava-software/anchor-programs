package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SpotPosition(// The scaled balance of the position. To get the token amount, multiply by the cumulative deposit/borrow
                           // interest of corresponding market.
                           // precision: SPOT_BALANCE_PRECISION
                           long scaledBalance,
                           // How many spot non reduce only trigger orders the user has open
                           // precision: token mint precision
                           long openBids,
                           // How many spot non reduce only trigger orders the user has open
                           // precision: token mint precision
                           long openAsks,
                           // The cumulative deposits/borrows a user has made into a market
                           // precision: token mint precision
                           long cumulativeDeposits,
                           // The market index of the corresponding spot market
                           int marketIndex,
                           // Whether the position is deposit or borrow
                           SpotBalanceType balanceType,
                           // Number of open orders
                           int openOrders,
                           byte[] padding) implements Borsh {

  public static final int BYTES = 40;
  public static final int PADDING_LEN = 4;

  public static SpotPosition read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var scaledBalance = getInt64LE(_data, i);
    i += 8;
    final var openBids = getInt64LE(_data, i);
    i += 8;
    final var openAsks = getInt64LE(_data, i);
    i += 8;
    final var cumulativeDeposits = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var balanceType = SpotBalanceType.read(_data, i);
    i += Borsh.len(balanceType);
    final var openOrders = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[4];
    Borsh.readArray(padding, _data, i);
    return new SpotPosition(scaledBalance,
                            openBids,
                            openAsks,
                            cumulativeDeposits,
                            marketIndex,
                            balanceType,
                            openOrders,
                            padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, scaledBalance);
    i += 8;
    putInt64LE(_data, i, openBids);
    i += 8;
    putInt64LE(_data, i, openAsks);
    i += 8;
    putInt64LE(_data, i, cumulativeDeposits);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(balanceType, _data, i);
    _data[i] = (byte) openOrders;
    ++i;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
