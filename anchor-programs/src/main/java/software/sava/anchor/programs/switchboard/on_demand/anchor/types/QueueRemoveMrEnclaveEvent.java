package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record QueueRemoveMrEnclaveEvent(PublicKey queue, byte[] mrEnclave) implements Borsh {

  public static final int BYTES = 64;
  public static final int MR_ENCLAVE_LEN = 32;

  public static QueueRemoveMrEnclaveEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var queue = readPubKey(_data, i);
    i += 32;
    final var mrEnclave = new byte[32];
    Borsh.readArray(mrEnclave, _data, i);
    return new QueueRemoveMrEnclaveEvent(queue, mrEnclave);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    queue.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(mrEnclave, 32, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
