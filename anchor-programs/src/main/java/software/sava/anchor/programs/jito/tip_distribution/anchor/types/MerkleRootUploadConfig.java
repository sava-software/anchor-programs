package software.sava.anchor.programs.jito.tip_distribution.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Singleton account that allows overriding TDA's merkle upload authority
public record MerkleRootUploadConfig(PublicKey _address,
                                     Discriminator discriminator,
                                     // The authority that overrides the TipDistributionAccount merkle_root_upload_authority
                                     PublicKey overrideAuthority,
                                     // The original merkle root upload authority that can be changed to the new overrided
                                     // authority. E.g. Jito Labs authority GZctHpWXmsZC1YHACTGGcHhYxjdRqQvTpYkb9LMvxDib
                                     PublicKey originalUploadAuthority,
                                     // The bump used to generate this account
                                     int bump) implements Borsh {

  public static final int BYTES = 73;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(213, 125, 30, 192, 25, 121, 87, 33);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int OVERRIDE_AUTHORITY_OFFSET = 8;
  public static final int ORIGINAL_UPLOAD_AUTHORITY_OFFSET = 40;
  public static final int BUMP_OFFSET = 72;

  public static Filter createOverrideAuthorityFilter(final PublicKey overrideAuthority) {
    return Filter.createMemCompFilter(OVERRIDE_AUTHORITY_OFFSET, overrideAuthority);
  }

  public static Filter createOriginalUploadAuthorityFilter(final PublicKey originalUploadAuthority) {
    return Filter.createMemCompFilter(ORIGINAL_UPLOAD_AUTHORITY_OFFSET, originalUploadAuthority);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static MerkleRootUploadConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MerkleRootUploadConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MerkleRootUploadConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MerkleRootUploadConfig> FACTORY = MerkleRootUploadConfig::read;

  public static MerkleRootUploadConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var overrideAuthority = readPubKey(_data, i);
    i += 32;
    final var originalUploadAuthority = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    return new MerkleRootUploadConfig(_address, discriminator, overrideAuthority, originalUploadAuthority, bump);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    overrideAuthority.write(_data, i);
    i += 32;
    originalUploadAuthority.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
