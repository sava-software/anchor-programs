package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ManagerUpdateBorrowRecord(long ts,
                                        PublicKey vault,
                                        PublicKey manager,
                                        long previousBorrowValue,
                                        long newBorrowValue,
                                        long vaultEquityBefore,
                                        long vaultEquityAfter) implements Borsh {

  public static final int BYTES = 104;

  public static ManagerUpdateBorrowRecord read(final byte[] _data, final int offset) {
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
    final var previousBorrowValue = getInt64LE(_data, i);
    i += 8;
    final var newBorrowValue = getInt64LE(_data, i);
    i += 8;
    final var vaultEquityBefore = getInt64LE(_data, i);
    i += 8;
    final var vaultEquityAfter = getInt64LE(_data, i);
    return new ManagerUpdateBorrowRecord(ts,
                                         vault,
                                         manager,
                                         previousBorrowValue,
                                         newBorrowValue,
                                         vaultEquityBefore,
                                         vaultEquityAfter);
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
    putInt64LE(_data, i, previousBorrowValue);
    i += 8;
    putInt64LE(_data, i, newBorrowValue);
    i += 8;
    putInt64LE(_data, i, vaultEquityBefore);
    i += 8;
    putInt64LE(_data, i, vaultEquityAfter);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
