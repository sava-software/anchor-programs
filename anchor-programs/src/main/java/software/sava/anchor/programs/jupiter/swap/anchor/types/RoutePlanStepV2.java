package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record RoutePlanStepV2(Swap swap,
                              int bps,
                              int inputIndex,
                              int outputIndex) implements Borsh {

  public static RoutePlanStepV2 read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var swap = Swap.read(_data, i);
    i += Borsh.len(swap);
    final var bps = getInt16LE(_data, i);
    i += 2;
    final var inputIndex = _data[i] & 0xFF;
    ++i;
    final var outputIndex = _data[i] & 0xFF;
    return new RoutePlanStepV2(swap,
                               bps,
                               inputIndex,
                               outputIndex);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(swap, _data, i);
    putInt16LE(_data, i, bps);
    i += 2;
    _data[i] = (byte) inputIndex;
    ++i;
    _data[i] = (byte) outputIndex;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(swap) + 2 + 1 + 1;
  }
}
