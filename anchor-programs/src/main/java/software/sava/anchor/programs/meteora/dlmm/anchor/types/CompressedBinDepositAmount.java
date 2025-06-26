package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record CompressedBinDepositAmount(int binId, int amount) implements Borsh {

  public static final int BYTES = 8;

  public static CompressedBinDepositAmount read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var binId = getInt32LE(_data, i);
    i += 4;
    final var amount = getInt32LE(_data, i);
    return new CompressedBinDepositAmount(binId, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, binId);
    i += 4;
    putInt32LE(_data, i, amount);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
