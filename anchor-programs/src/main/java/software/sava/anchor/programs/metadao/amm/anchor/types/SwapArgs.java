package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapArgs(SwapType swapType,
                       long inputAmount,
                       long outputAmountMin) implements Borsh {

  public static final int BYTES = 17;

  public static SwapArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var swapType = SwapType.read(_data, i);
    i += Borsh.len(swapType);
    final var inputAmount = getInt64LE(_data, i);
    i += 8;
    final var outputAmountMin = getInt64LE(_data, i);
    return new SwapArgs(swapType, inputAmount, outputAmountMin);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(swapType, _data, i);
    putInt64LE(_data, i, inputAmount);
    i += 8;
    putInt64LE(_data, i, outputAmountMin);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
