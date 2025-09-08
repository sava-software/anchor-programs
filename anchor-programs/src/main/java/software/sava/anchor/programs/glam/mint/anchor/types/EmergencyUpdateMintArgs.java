package software.sava.anchor.programs.glam.mint.anchor.types;

import software.sava.core.borsh.Borsh;

public record EmergencyUpdateMintArgs(RequestType requestType, boolean setPaused) implements Borsh {

  public static final int BYTES = 2;

  public static EmergencyUpdateMintArgs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var requestType = RequestType.read(_data, i);
    i += Borsh.len(requestType);
    final var setPaused = _data[i] == 1;
    return new EmergencyUpdateMintArgs(requestType, setPaused);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(requestType, _data, i);
    _data[i] = (byte) (setPaused ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
