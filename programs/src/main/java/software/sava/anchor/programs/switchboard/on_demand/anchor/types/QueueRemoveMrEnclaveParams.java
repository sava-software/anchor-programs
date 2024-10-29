package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record QueueRemoveMrEnclaveParams(byte[] mrEnclave) implements Borsh {

  public static final int BYTES = 32;

  public static QueueRemoveMrEnclaveParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var mrEnclave = new byte[32];
    Borsh.readArray(mrEnclave, _data, offset);
    return new QueueRemoveMrEnclaveParams(mrEnclave);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(mrEnclave, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
