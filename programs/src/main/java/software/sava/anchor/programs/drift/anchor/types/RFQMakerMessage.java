package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public record RFQMakerMessage(RFQMakerOrderParams orderParams, byte[] signature) implements Borsh {

  public static final int BYTES = 134;

  public static RFQMakerMessage read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var orderParams = RFQMakerOrderParams.read(_data, i);
    i += Borsh.len(orderParams);
    final var signature = new byte[64];
    Borsh.readArray(signature, _data, i);
    return new RFQMakerMessage(orderParams, signature);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(orderParams, _data, i);
    i += Borsh.writeArray(signature, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
