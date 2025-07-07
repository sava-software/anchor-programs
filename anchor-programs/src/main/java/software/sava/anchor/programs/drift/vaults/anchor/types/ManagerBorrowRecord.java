package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ManagerBorrowRecord(long ts,
                                  PublicKey vault,
                                  PublicKey manager,
                                  long borrowAmount,
                                  long borrowValue,
                                  int borrowSpotMarketIndex,
                                  long borrowOraclePrice,
                                  int depositSpotMarketIndex,
                                  long depositOraclePrice,
                                  long vaultEquity) implements Borsh {

  public static final int BYTES = 116;

  public static ManagerBorrowRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var manager = readPubKey(_data, i);
    i += 32;
    final var borrowAmount = getInt64LE(_data, i);
    i += 8;
    final var borrowValue = getInt64LE(_data, i);
    i += 8;
    final var borrowSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var borrowOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var depositSpotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var depositOraclePrice = getInt64LE(_data, i);
    i += 8;
    final var vaultEquity = getInt64LE(_data, i);
    return new ManagerBorrowRecord(ts,
                                   vault,
                                   manager,
                                   borrowAmount,
                                   borrowValue,
                                   borrowSpotMarketIndex,
                                   borrowOraclePrice,
                                   depositSpotMarketIndex,
                                   depositOraclePrice,
                                   vaultEquity);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    vault.write(_data, i);
    i += 32;
    manager.write(_data, i);
    i += 32;
    putInt64LE(_data, i, borrowAmount);
    i += 8;
    putInt64LE(_data, i, borrowValue);
    i += 8;
    putInt16LE(_data, i, borrowSpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, borrowOraclePrice);
    i += 8;
    putInt16LE(_data, i, depositSpotMarketIndex);
    i += 2;
    putInt64LE(_data, i, depositOraclePrice);
    i += 8;
    putInt64LE(_data, i, vaultEquity);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
