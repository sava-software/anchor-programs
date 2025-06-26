package software.sava.anchor.programs.drift.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultRecord(long ts,
                          int spotMarketIndex,
                          long vaultEquityBefore) implements Borsh {

  public static final int BYTES = 18;

  public static VaultRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var spotMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var vaultEquityBefore = getInt64LE(_data, i);
    return new VaultRecord(ts, spotMarketIndex, vaultEquityBefore);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    putInt16LE(_data, i, spotMarketIndex);
    i += 2;
    putInt64LE(_data, i, vaultEquityBefore);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
