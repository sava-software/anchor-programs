package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record MerkleRootUploadAuthorityUpdatedEvent(PublicKey oldAuthority, PublicKey newAuthority) implements Borsh {

  public static final int BYTES = 64;

  public static MerkleRootUploadAuthorityUpdatedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var oldAuthority = readPubKey(_data, i);
    i += 32;
    final var newAuthority = readPubKey(_data, i);
    return new MerkleRootUploadAuthorityUpdatedEvent(oldAuthority, newAuthority);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    oldAuthority.write(_data, i);
    i += 32;
    newAuthority.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
