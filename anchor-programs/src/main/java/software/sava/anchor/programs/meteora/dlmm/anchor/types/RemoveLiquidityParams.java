package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import java.util.OptionalInt;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record RemoveLiquidityParams(OptionalInt minBinId,
                                    OptionalInt maxBinId,
                                    int bps,
                                    byte[] padding) implements Borsh {

  public static final int PADDING_LEN = 16;
  public static RemoveLiquidityParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minBinId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (minBinId.isPresent()) {
      i += 4;
    }
    final var maxBinId = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(getInt32LE(_data, i));
    if (maxBinId.isPresent()) {
      i += 4;
    }
    final var bps = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[16];
    Borsh.readArray(padding, _data, i);
    return new RemoveLiquidityParams(minBinId,
                                     maxBinId,
                                     bps,
                                     padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(minBinId, _data, i);
    i += Borsh.writeOptional(maxBinId, _data, i);
    putInt16LE(_data, i, bps);
    i += 2;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (minBinId == null || minBinId.isEmpty() ? 1 : (1 + 4)) + (maxBinId == null || maxBinId.isEmpty() ? 1 : (1 + 4)) + 2 + Borsh.lenArray(padding);
  }
}
