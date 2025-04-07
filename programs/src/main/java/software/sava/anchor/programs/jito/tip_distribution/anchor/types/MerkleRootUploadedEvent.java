package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record MerkleRootUploadedEvent(PublicKey merkleRootUploadAuthority, PublicKey tipDistributionAccount) implements Borsh {

  public static final int BYTES = 64;

  public static MerkleRootUploadedEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var merkleRootUploadAuthority = readPubKey(_data, i);
    i += 32;
    final var tipDistributionAccount = readPubKey(_data, i);
    return new MerkleRootUploadedEvent(merkleRootUploadAuthority, tipDistributionAccount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    merkleRootUploadAuthority.write(_data, i);
    i += 32;
    tipDistributionAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
