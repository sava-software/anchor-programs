package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record StrategyParameters(// min bin id
                                 int minBinId,
                                 // max bin id
                                 int maxBinId,
                                 // strategy type
                                 StrategyType strategyType,
                                 // parameters
                                 byte[] parameteres) implements Borsh {

  public static final int BYTES = 73;

  public static StrategyParameters read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var minBinId = getInt32LE(_data, i);
    i += 4;
    final var maxBinId = getInt32LE(_data, i);
    i += 4;
    final var strategyType = StrategyType.read(_data, i);
    i += Borsh.len(strategyType);
    final var parameteres = Borsh.readArray(new byte[64], _data, i);
    return new StrategyParameters(minBinId,
                                  maxBinId,
                                  strategyType,
                                  parameteres);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, minBinId);
    i += 4;
    putInt32LE(_data, i, maxBinId);
    i += 4;
    i += Borsh.write(strategyType, _data, i);
    i += Borsh.fixedWrite(parameteres, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
