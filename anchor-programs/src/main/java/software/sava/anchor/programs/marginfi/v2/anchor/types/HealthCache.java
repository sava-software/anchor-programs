package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// A read-only cache of the internal risk engine's information. Only valid in borrow/withdraw if
// the tx does not fail. To see the state in any context, e.g. to figure out if the risk engine is
// failing due to some bad price information, use `pulse_health`.
public record HealthCache(// Internal risk engine asset value, using initial weight (e.g. what is used for borrowing
                          // purposes), with all confidence adjustments, and other discounts on price.
                          // * Uses EMA price
                          // * In dollars
                          WrappedI80F48 assetValue,
                          // Internal risk engine liability value, using initial weight (e.g. what is used for borrowing
                          // purposes), with all confidence adjustments, and other discounts on price.
                          // * Uses EMA price
                          // * In dollars
                          WrappedI80F48 liabilityValue,
                          // Internal risk engine asset value, using maintenance weight (e.g. what is used for
                          // liquidation purposes), with all confidence adjustments.
                          // * Zero if the risk engine failed to load
                          // * Uses SPOT price
                          // * In dollars
                          WrappedI80F48 assetValueMaint,
                          // Internal risk engine liability value, using maintenance weight (e.g. what is used for
                          // liquidation purposes), with all confidence adjustments.
                          // * Zero if the risk engine failed to load
                          // * Uses SPOT price
                          // * In dollars
                          WrappedI80F48 liabilityValueMaint,
                          // The "true" value of assets without any confidence or weight adjustments. Internally, used
                          // only for bankruptcies.
                          // * Zero if the risk engine failed to load
                          // * Uses EMA price
                          // * In dollars
                          WrappedI80F48 assetValueEquity,
                          // The "true" value of liabilities without any confidence or weight adjustments.
                          // Internally, used only for bankruptcies.
                          // * Zero if the risk engine failed to load
                          // * Uses EMA price
                          // * In dollars
                          WrappedI80F48 liabilityValueEquity,
                          // Unix timestamp from the system clock when this cache was last updated
                          long timestamp,
                          // The flags that indicate the state of the health cache. This is a u64 bitfield, where each
                          // bit represents a flag.
                          // 
                          // * HEALTHY = 1 - If set, the account cannot be liquidated. If 0, the account is unhealthy and
                          // can be liquidated.
                          // * ENGINE STATUS = 2 - If set, the engine did not error during the last health pulse. If 0,
                          // the engine would have errored and this cache is likely invalid. `RiskEngineInitRejected`
                          // is ignored and will allow the flag to be set anyways.
                          // * ORACLE OK = 4 - If set, the engine did not error due to an oracle issue. If 0, engine was
                          // passed a bad bank or oracle account, or an oracle was stale. Check the order in which
                          // accounts were passed and ensure each balance has the correct banks/oracles, and that
                          // oracle cranks ran recently enough. Check `internal_err` and `err_index` for more details
                          // in some circumstances. Invalid if generated after borrow/withdraw (these instructions will
                          // ignore oracle issues if health is still satisfactory with some balance zeroed out).
                          // * 8, 16, 32, 64, 128, etc - reserved for future use
                          int flags,
                          // If the engine errored, look here for the error code. If the engine returns ok, you may also
                          // check here to see if the risk engine rejected this tx (3009).
                          int mrgnErr,
                          // Each price corresponds to that index of Balances in the LendingAccount. Useful for debugging
                          // or liquidator consumption, to determine how a user's position is priced internally.
                          // * An f64 stored as bytes
                          byte[][] prices,
                          // Errors in asset oracles are ignored (with prices treated as zero). If you see a zero price
                          // and the `ORACLE_OK` flag is not set, check here to see what error was ignored internally.
                          int internalErr,
                          // Index in `balances` where `internal_err` appeared
                          int errIndex,
                          // Since 0.1.3, the version will be encoded here. See PROGRAM_VERSION.
                          int programVersion,
                          byte[] pad0,
                          int internalLiqErr,
                          int internalBankruptcyErr,
                          byte[] reserved0,
                          byte[] reserved1) implements Borsh {

  public static final int BYTES = 304;
  public static final int PRICES_LEN = 16;
  public static final int PAD_0_LEN = 2;
  public static final int RESERVED_0_LEN = 32;
  public static final int RESERVED_1_LEN = 16;

  public static HealthCache read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetValue = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetValue);
    final var liabilityValue = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityValue);
    final var assetValueMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetValueMaint);
    final var liabilityValueMaint = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityValueMaint);
    final var assetValueEquity = WrappedI80F48.read(_data, i);
    i += Borsh.len(assetValueEquity);
    final var liabilityValueEquity = WrappedI80F48.read(_data, i);
    i += Borsh.len(liabilityValueEquity);
    final var timestamp = getInt64LE(_data, i);
    i += 8;
    final var flags = getInt32LE(_data, i);
    i += 4;
    final var mrgnErr = getInt32LE(_data, i);
    i += 4;
    final var prices = new byte[16][8];
    i += Borsh.readArray(prices, _data, i);
    final var internalErr = getInt32LE(_data, i);
    i += 4;
    final var errIndex = _data[i] & 0xFF;
    ++i;
    final var programVersion = _data[i] & 0xFF;
    ++i;
    final var pad0 = new byte[2];
    i += Borsh.readArray(pad0, _data, i);
    final var internalLiqErr = getInt32LE(_data, i);
    i += 4;
    final var internalBankruptcyErr = getInt32LE(_data, i);
    i += 4;
    final var reserved0 = new byte[32];
    i += Borsh.readArray(reserved0, _data, i);
    final var reserved1 = new byte[16];
    Borsh.readArray(reserved1, _data, i);
    return new HealthCache(assetValue,
                           liabilityValue,
                           assetValueMaint,
                           liabilityValueMaint,
                           assetValueEquity,
                           liabilityValueEquity,
                           timestamp,
                           flags,
                           mrgnErr,
                           prices,
                           internalErr,
                           errIndex,
                           programVersion,
                           pad0,
                           internalLiqErr,
                           internalBankruptcyErr,
                           reserved0,
                           reserved1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(assetValue, _data, i);
    i += Borsh.write(liabilityValue, _data, i);
    i += Borsh.write(assetValueMaint, _data, i);
    i += Borsh.write(liabilityValueMaint, _data, i);
    i += Borsh.write(assetValueEquity, _data, i);
    i += Borsh.write(liabilityValueEquity, _data, i);
    putInt64LE(_data, i, timestamp);
    i += 8;
    putInt32LE(_data, i, flags);
    i += 4;
    putInt32LE(_data, i, mrgnErr);
    i += 4;
    i += Borsh.writeArray(prices, _data, i);
    putInt32LE(_data, i, internalErr);
    i += 4;
    _data[i] = (byte) errIndex;
    ++i;
    _data[i] = (byte) programVersion;
    ++i;
    i += Borsh.writeArray(pad0, _data, i);
    putInt32LE(_data, i, internalLiqErr);
    i += 4;
    putInt32LE(_data, i, internalBankruptcyErr);
    i += 4;
    i += Borsh.writeArray(reserved0, _data, i);
    i += Borsh.writeArray(reserved1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
