package software.sava.anchor.programs.drift.vaults.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ShareTransferRecord(long ts,
                                  PublicKey vault,
                                  PublicKey fromVaultDepositor,
                                  PublicKey toVaultDepositor,
                                  BigInteger shares,
                                  long value,
                                  BigInteger fromDepositorSharesBefore,
                                  BigInteger fromDepositorSharesAfter,
                                  BigInteger toDepositorSharesBefore,
                                  BigInteger toDepositorSharesAfter) implements Borsh {

  public static final int BYTES = 192;

  public static ShareTransferRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var fromVaultDepositor = readPubKey(_data, i);
    i += 32;
    final var toVaultDepositor = readPubKey(_data, i);
    i += 32;
    final var shares = getInt128LE(_data, i);
    i += 16;
    final var value = getInt64LE(_data, i);
    i += 8;
    final var fromDepositorSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var fromDepositorSharesAfter = getInt128LE(_data, i);
    i += 16;
    final var toDepositorSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var toDepositorSharesAfter = getInt128LE(_data, i);
    return new ShareTransferRecord(ts,
                                   vault,
                                   fromVaultDepositor,
                                   toVaultDepositor,
                                   shares,
                                   value,
                                   fromDepositorSharesBefore,
                                   fromDepositorSharesAfter,
                                   toDepositorSharesBefore,
                                   toDepositorSharesAfter);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    vault.write(_data, i);
    i += 32;
    fromVaultDepositor.write(_data, i);
    i += 32;
    toVaultDepositor.write(_data, i);
    i += 32;
    putInt128LE(_data, i, shares);
    i += 16;
    putInt64LE(_data, i, value);
    i += 8;
    putInt128LE(_data, i, fromDepositorSharesBefore);
    i += 16;
    putInt128LE(_data, i, fromDepositorSharesAfter);
    i += 16;
    putInt128LE(_data, i, toDepositorSharesBefore);
    i += 16;
    putInt128LE(_data, i, toDepositorSharesAfter);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
