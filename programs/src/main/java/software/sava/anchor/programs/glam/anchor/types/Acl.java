package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Acl(PublicKey pubkey, Permission[] permissions) implements Borsh {


  public static Acl read(final byte[] _data, final int offset) {
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var permissions = Borsh.readVector(Permission.class, Permission::read, _data, i);
    return new Acl(pubkey, permissions);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.write(permissions, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.len(permissions);
  }
}
