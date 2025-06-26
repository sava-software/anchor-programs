package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record QueueInitEvent(PublicKey queue) implements Borsh {

  public static final int BYTES = 32;

  public static QueueInitEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var queue = readPubKey(_data, offset);
    return new QueueInitEvent(queue);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    queue.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
