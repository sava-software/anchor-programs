package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record ConfigUpdatedEvent(PublicKey authority) implements Borsh {

  public static final int BYTES = 32;

  public static ConfigUpdatedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var authority = readPubKey(_data, offset);
    return new ConfigUpdatedEvent(authority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    authority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
