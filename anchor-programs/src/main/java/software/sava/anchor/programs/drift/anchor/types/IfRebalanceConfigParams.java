package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record IfRebalanceConfigParams(long totalInAmount,
                                      long epochMaxInAmount,
                                      long epochDuration,
                                      int outMarketIndex,
                                      int inMarketIndex,
                                      int maxSlippageBps,
                                      int swapMode,
                                      int status) implements Borsh {

  public static final int BYTES = 32;

  public static IfRebalanceConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var totalInAmount = getInt64LE(_data, i);
    i += 8;
    final var epochMaxInAmount = getInt64LE(_data, i);
    i += 8;
    final var epochDuration = getInt64LE(_data, i);
    i += 8;
    final var outMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var inMarketIndex = getInt16LE(_data, i);
    i += 2;
    final var maxSlippageBps = getInt16LE(_data, i);
    i += 2;
    final var swapMode = _data[i] & 0xFF;
    ++i;
    final var status = _data[i] & 0xFF;
    return new IfRebalanceConfigParams(totalInAmount,
                                       epochMaxInAmount,
                                       epochDuration,
                                       outMarketIndex,
                                       inMarketIndex,
                                       maxSlippageBps,
                                       swapMode,
                                       status);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, totalInAmount);
    i += 8;
    putInt64LE(_data, i, epochMaxInAmount);
    i += 8;
    putInt64LE(_data, i, epochDuration);
    i += 8;
    putInt16LE(_data, i, outMarketIndex);
    i += 2;
    putInt16LE(_data, i, inMarketIndex);
    i += 2;
    putInt16LE(_data, i, maxSlippageBps);
    i += 2;
    _data[i] = (byte) swapMode;
    ++i;
    _data[i] = (byte) status;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
