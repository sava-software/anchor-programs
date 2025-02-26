package software.sava.anchor.programs.metadao.amm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SwapEvent(CommonFields common,
                        long inputAmount,
                        long outputAmount,
                        SwapType swapType) implements Borsh {

  public static final int BYTES = 169;

  public static SwapEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var common = CommonFields.read(_data, i);
    i += Borsh.len(common);
    final var inputAmount = getInt64LE(_data, i);
    i += 8;
    final var outputAmount = getInt64LE(_data, i);
    i += 8;
    final var swapType = SwapType.read(_data, i);
    return new SwapEvent(common,
                         inputAmount,
                         outputAmount,
                         swapType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(common, _data, i);
    putInt64LE(_data, i, inputAmount);
    i += 8;
    putInt64LE(_data, i, outputAmount);
    i += 8;
    i += Borsh.write(swapType, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
