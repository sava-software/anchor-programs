package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.lang.String;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record MerkleProofMetadata(PublicKey _address,
                                  Discriminator discriminator,
                                  // vault pubkey that config is belong
                                  PublicKey vault,
                                  long[] padding,
                                  // proof url
                                  String proofUrl, byte[] _proofUrl) implements Borsh {

  public static final int PADDING_LEN = 16;
  public static final Discriminator DISCRIMINATOR = toDiscriminator(133, 24, 30, 217, 240, 20, 222, 100);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int VAULT_OFFSET = 8;
  public static final int PADDING_OFFSET = 40;
  public static final int PROOF_URL_OFFSET = 168;

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static MerkleProofMetadata createRecord(final PublicKey _address,
                                                 final Discriminator discriminator,
                                                 final PublicKey vault,
                                                 final long[] padding,
                                                 final String proofUrl) {
    return new MerkleProofMetadata(_address, discriminator, vault, padding, proofUrl, proofUrl.getBytes(UTF_8));
  }

  public static MerkleProofMetadata read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MerkleProofMetadata read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MerkleProofMetadata read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MerkleProofMetadata> FACTORY = MerkleProofMetadata::read;

  public static MerkleProofMetadata read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var vault = readPubKey(_data, i);
    i += 32;
    final var padding = new long[16];
    i += Borsh.readArray(padding, _data, i);
    final var proofUrl = Borsh.string(_data, i);
    return new MerkleProofMetadata(_address, discriminator, vault, padding, proofUrl, proofUrl.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    vault.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(padding, 16, _data, i);
    i += Borsh.writeVector(_proofUrl, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 32 + Borsh.lenArray(padding) + Borsh.lenVector(_proofUrl);
  }
}
