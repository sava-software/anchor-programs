package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.borsh.Borsh;

public record RebalanceRaw(byte[] params,
                           byte[] state,
                           int referencePriceType) implements Borsh {

  public static final int BYTES = 385;
  public static final int PARAMS_LEN = 128;
  public static final int STATE_LEN = 256;

  public static RebalanceRaw read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var params = new byte[128];
    i += Borsh.readArray(params, _data, i);
    final var state = new byte[256];
    i += Borsh.readArray(state, _data, i);
    final var referencePriceType = _data[i] & 0xFF;
    return new RebalanceRaw(params, state, referencePriceType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(params, 128, _data, i);
    i += Borsh.writeArrayChecked(state, 256, _data, i);
    _data[i] = (byte) referencePriceType;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
