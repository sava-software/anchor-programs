package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record DelegateAcl(PublicKey pubkey, Permission[] permissions) implements Borsh {

  public static DelegateAcl read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var permissions = Borsh.readVector(Permission.class, Permission::read, _data, i);
    return new DelegateAcl(pubkey, permissions);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.writeVector(permissions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(permissions);
  }
}
