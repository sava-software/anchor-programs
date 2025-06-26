package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record PermissionSetEvent(PublicKey permission) implements Borsh {

  public static final int BYTES = 32;

  public static PermissionSetEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var permission = readPubKey(_data, offset);
    return new PermissionSetEvent(permission);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    permission.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
