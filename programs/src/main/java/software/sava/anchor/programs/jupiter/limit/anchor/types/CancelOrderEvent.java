package software.sava.anchor.programs.jupiter.limit.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CancelOrderEvent(PublicKey orderKey) implements Borsh {

  public static final int BYTES = 32;

  public static CancelOrderEvent read(final byte[] _data, final int offset) {
    final var orderKey = readPubKey(_data, offset);
    return new CancelOrderEvent(orderKey);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    orderKey.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
