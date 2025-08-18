package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

// A read-only cache of the bank's key metrics, e.g. spot interest/fee rates.
public record BankCache(// Actual (spot) interest/fee rates of the bank, based on utilization
                        // * APR (annual percentage rate) values
                        // * From 0-1000%, as u32, e.g. u32::MAX = 1000%, u:32::MAX/2 = 500%, etc
                        int baseRate,
                        // Equivalent to `base_rate` * utilization
                        // * From 0-1000%, as u32, e.g. u32::MAX = 1000%, u:32::MAX/2 = 500%, etc
                        int lendingRate,
                        // Equivalent to `base_rate` * (1 + ir_fees) + fixed_fees
                        // * From 0-1000%, as u32, e.g. u32::MAX = 1000%, u:32::MAX/2 = 500%, etc
                        int borrowingRate,
                        // * in seconds
                        int interestAccumulatedFor,
                        // equivalent to (share value increase in the last `interest_accumulated_for` seconds *
                        // shares), i.e. the delta in `asset_share_value`, in token.
                        // * Note: if the tx that triggered this cache update increased or decreased the net shares,
                        // this value still reports using the PRE-CHANGE share amount, since interest is always
                        // earned on that amount.
                        // * in token, in native decimals, as I80F48
                        WrappedI80F48 accumulatedSinceLastUpdate,
                        byte[] reserved0) implements Borsh {

  public static final int BYTES = 160;
  public static final int RESERVED_0_LEN = 128;

  public static BankCache read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var baseRate = getInt32LE(_data, i);
    i += 4;
    final var lendingRate = getInt32LE(_data, i);
    i += 4;
    final var borrowingRate = getInt32LE(_data, i);
    i += 4;
    final var interestAccumulatedFor = getInt32LE(_data, i);
    i += 4;
    final var accumulatedSinceLastUpdate = WrappedI80F48.read(_data, i);
    i += Borsh.len(accumulatedSinceLastUpdate);
    final var reserved0 = new byte[128];
    Borsh.readArray(reserved0, _data, i);
    return new BankCache(baseRate,
                         lendingRate,
                         borrowingRate,
                         interestAccumulatedFor,
                         accumulatedSinceLastUpdate,
                         reserved0);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, baseRate);
    i += 4;
    putInt32LE(_data, i, lendingRate);
    i += 4;
    putInt32LE(_data, i, borrowingRate);
    i += 4;
    putInt32LE(_data, i, interestAccumulatedFor);
    i += 4;
    i += Borsh.write(accumulatedSinceLastUpdate, _data, i);
    i += Borsh.writeArray(reserved0, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
