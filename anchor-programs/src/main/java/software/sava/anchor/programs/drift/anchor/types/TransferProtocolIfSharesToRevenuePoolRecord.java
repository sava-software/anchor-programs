package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TransferProtocolIfSharesToRevenuePoolRecord(long ts,
                                                          int marketIndex,
                                                          long amount,
                                                          BigInteger shares,
                                                          long ifVaultAmountBefore,
                                                          BigInteger protocolSharesBefore,
                                                          long currentInAmountSinceLastTransfer) implements Borsh {

  public static final int BYTES = 66;

  public static TransferProtocolIfSharesToRevenuePoolRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var shares = getInt128LE(_data, i);
    i += 16;
    final var ifVaultAmountBefore = getInt64LE(_data, i);
    i += 8;
    final var protocolSharesBefore = getInt128LE(_data, i);
    i += 16;
    final var currentInAmountSinceLastTransfer = getInt64LE(_data, i);
    return new TransferProtocolIfSharesToRevenuePoolRecord(ts,
                                                           marketIndex,
                                                           amount,
                                                           shares,
                                                           ifVaultAmountBefore,
                                                           protocolSharesBefore,
                                                           currentInAmountSinceLastTransfer);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt128LE(_data, i, shares);
    i += 16;
    putInt64LE(_data, i, ifVaultAmountBefore);
    i += 8;
    putInt128LE(_data, i, protocolSharesBefore);
    i += 16;
    putInt64LE(_data, i, currentInAmountSinceLastTransfer);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
