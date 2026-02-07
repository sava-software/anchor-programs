package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record MerkleProofMetadataCreated(PublicKey vault, String proofUrl, byte[] _proofUrl) implements Borsh {

  public static MerkleProofMetadataCreated createRecord(final PublicKey vault, final String proofUrl) {
    return new MerkleProofMetadataCreated(vault, proofUrl, proofUrl.getBytes(UTF_8));
  }

  public static MerkleProofMetadataCreated read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var proofUrl = Borsh.string(_data, i);
    return new MerkleProofMetadataCreated(vault, proofUrl, proofUrl.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    i += Borsh.writeVector(_proofUrl, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 32 + Borsh.lenVector(_proofUrl);
  }
}
