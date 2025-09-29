package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.borsh.Borsh;

public record QueueAddMrEnclaveParams(byte[] mrEnclave) implements Borsh {

  public static final int BYTES = 32;
  public static final int MR_ENCLAVE_LEN = 32;

  public static QueueAddMrEnclaveParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var mrEnclave = new byte[32];
    Borsh.readArray(mrEnclave, _data, offset);
    return new QueueAddMrEnclaveParams(mrEnclave);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArrayChecked(mrEnclave, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
