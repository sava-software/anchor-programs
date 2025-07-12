package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record MerkleRootConfig(PublicKey _address,
                               Discriminator discriminator,
                               // The 256-bit merkle root.
                               byte[] root,
                               // vault pubkey that config is belong
                               PublicKey vault,
                               // version
                               long version,
                               // padding for further use
                               long[] padding) implements Borsh {

  public static final int BYTES = 144;
  public static final int ROOT_LEN = 32;
  public static final int PADDING_LEN = 8;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(103, 2, 222, 217, 73, 50, 187, 39);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ROOT_OFFSET = 8;
  public static final int VAULT_OFFSET = 40;
  public static final int VERSION_OFFSET = 72;
  public static final int PADDING_OFFSET = 80;

  public static Filter createVaultFilter(final PublicKey vault) {
    return Filter.createMemCompFilter(VAULT_OFFSET, vault);
  }

  public static Filter createVersionFilter(final long version) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, version);
    return Filter.createMemCompFilter(VERSION_OFFSET, _data);
  }

  public static MerkleRootConfig read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MerkleRootConfig read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MerkleRootConfig read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MerkleRootConfig> FACTORY = MerkleRootConfig::read;

  public static MerkleRootConfig read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var root = new byte[32];
    i += Borsh.readArray(root, _data, i);
    final var vault = readPubKey(_data, i);
    i += 32;
    final var version = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[8];
    Borsh.readArray(padding, _data, i);
    return new MerkleRootConfig(_address,
                                discriminator,
                                root,
                                vault,
                                version,
                                padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    i += Borsh.writeArray(root, _data, i);
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
