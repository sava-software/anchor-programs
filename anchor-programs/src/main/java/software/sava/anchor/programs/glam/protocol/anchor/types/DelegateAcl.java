package software.sava.anchor.programs.glam.protocol.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DelegateAcl(PublicKey pubkey,
                          IntegrationPermissions[] integrationPermissions,
                          long expiresAt) implements Borsh {

  public static DelegateAcl read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var integrationPermissions = Borsh.readVector(IntegrationPermissions.class, IntegrationPermissions::read, _data, i);
    i += Borsh.lenVector(integrationPermissions);
    final var expiresAt = getInt64LE(_data, i);
    return new DelegateAcl(pubkey, integrationPermissions, expiresAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    i += Borsh.writeVector(integrationPermissions, _data, i);
    putInt64LE(_data, i, expiresAt);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(integrationPermissions) + 8;
  }
}
