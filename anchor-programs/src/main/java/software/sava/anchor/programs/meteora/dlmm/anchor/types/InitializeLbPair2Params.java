package software.sava.anchor.programs.meteora.dlmm.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record InitializeLbPair2Params(// Pool price
                                      int activeId,
                                      // Padding, for future use
                                      byte[] padding) implements Borsh {

  public static final int BYTES = 100;
  public static final int PADDING_LEN = 96;

  public static InitializeLbPair2Params read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var activeId = getInt32LE(_data, i);
    i += 4;
    final var padding = new byte[96];
    Borsh.readArray(padding, _data, i);
    return new InitializeLbPair2Params(activeId, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, activeId);
    i += 4;
    i += Borsh.writeArrayChecked(padding, 96, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
