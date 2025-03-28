package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Parameters that changes based on dynamic of the market
public record VariableParameters(// Volatility accumulator measure the number of bin crossed since reference bin ID. Normally (without filter period taken into consideration), reference bin ID is the active bin of last swap.
                                 // It affects the variable fee rate
                                 int volatilityAccumulator,
                                 // Volatility reference is decayed volatility accumulator. It is always <= volatility_accumulator
                                 int volatilityReference,
                                 // Active bin id of last swap.
                                 int indexReference,
                                 // Padding for bytemuck safe alignment
                                 byte[] padding,
                                 // Last timestamp the variable parameters was updated
                                 long lastUpdateTimestamp,
                                 // Padding for bytemuck safe alignment
                                 byte[] padding1) implements Borsh {

  public static final int BYTES = 32;

  public static VariableParameters read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var volatilityAccumulator = getInt32LE(_data, i);
    i += 4;
    final var volatilityReference = getInt32LE(_data, i);
    i += 4;
    final var indexReference = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[4];
    i += Borsh.readArray(padding, _data, i);
    final var lastUpdateTimestamp = getInt64LE(_data, i);
    i += 8;
    final var padding1 = new byte[8];
    Borsh.readArray(padding1, _data, i);
    return new VariableParameters(volatilityAccumulator,
                                  volatilityReference,
                                  indexReference,
                                  padding,
                                  lastUpdateTimestamp,
                                  padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, volatilityAccumulator);
    i += 4;
    putInt32LE(_data, i, volatilityReference);
    i += 4;
    putInt32LE(_data, i, indexReference);
    i += 4;
    i += Borsh.writeArray(padding, _data, i);
    putInt64LE(_data, i, lastUpdateTimestamp);
    i += 8;
    i += Borsh.writeArray(padding1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
