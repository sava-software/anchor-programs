package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RevenueShareOrder(// fees accrued so far for this order slot. This is not exclusively fees from this order_id
                                // and may include fees from other orders in the same market. This may be swept to the
                                // builder's SpotPosition during settle_pnl.
                                long feesAccrued,
                                // the order_id of the current active order in this slot. It's only relevant while bit_flag = Open
                                int orderId,
                                // the builder fee on this order, in tenths of a bps, e.g. 100 = 0.01%
                                int feeTenthBps,
                                int marketIndex,
                                // the subaccount_id of the user who created this order. It's only relevant while bit_flag = Open
                                int subAccountId,
                                // the index of the RevenueShareEscrow.approved_builders list, that this order's fee will settle to. Ignored
                                // if bit_flag = Referral.
                                int builderIdx,
                                // bitflags that describe the state of the order.
                                // [`RevenueShareOrderBitFlag::Init`]: this order slot is available for use.
                                // [`RevenueShareOrderBitFlag::Open`]: this order slot is occupied, `order_id` is the `sub_account_id`'s active order.
                                // [`RevenueShareOrderBitFlag::Completed`]: this order has been filled or canceled, and is waiting to be settled into.
                                // the builder's account order_id and sub_account_id are no longer relevant, it may be merged with other orders.
                                // [`RevenueShareOrderBitFlag::Referral`]: this order stores referral rewards waiting to be settled for this market.
                                // If it is set, no other bitflag should be set.
                                int bitFlags,
                                // the index into the User's orders list when this RevenueShareOrder was created, make sure to verify that order_id matches.
                                int userOrderIndex,
                                MarketType marketType,
                                byte[] padding) implements Borsh {

  public static final int BYTES = 32;
  public static final int PADDING_LEN = 10;

  public static RevenueShareOrder read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feesAccrued = getInt64LE(_data, i);
    i += 8;
    final var orderId = getInt32LE(_data, i);
    i += 4;
    final var feeTenthBps = getInt16LE(_data, i);
    i += 2;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var subAccountId = getInt16LE(_data, i);
    i += 2;
    final var builderIdx = _data[i] & 0xFF;
    ++i;
    final var bitFlags = _data[i] & 0xFF;
    ++i;
    final var userOrderIndex = _data[i] & 0xFF;
    ++i;
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var padding = new byte[10];
    Borsh.readArray(padding, _data, i);
    return new RevenueShareOrder(feesAccrued,
                                 orderId,
                                 feeTenthBps,
                                 marketIndex,
                                 subAccountId,
                                 builderIdx,
                                 bitFlags,
                                 userOrderIndex,
                                 marketType,
                                 padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, feesAccrued);
    i += 8;
    putInt32LE(_data, i, orderId);
    i += 4;
    putInt16LE(_data, i, feeTenthBps);
    i += 2;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt16LE(_data, i, subAccountId);
    i += 2;
    _data[i] = (byte) builderIdx;
    ++i;
    _data[i] = (byte) bitFlags;
    ++i;
    _data[i] = (byte) userOrderIndex;
    ++i;
    i += Borsh.write(marketType, _data, i);
    i += Borsh.writeArrayChecked(padding, 10, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
