package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MerkleRootConfigCreated(PublicKey admin,
                                      PublicKey config,
                                      PublicKey vault,
                                      long version,
                                      byte[] root) implements Borsh {

  public static final int BYTES = 136;
  public static final int ROOT_LEN = 32;

  public static MerkleRootConfigCreated read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var admin = readPubKey(_data, i);
    i += 32;
    final var config = readPubKey(_data, i);
    i += 32;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var version = getInt64LE(_data, i);
    i += 8;
    final var root = new byte[32];
    Borsh.readArray(root, _data, i);
    return new MerkleRootConfigCreated(admin,
                                       config,
                                       vault,
                                       version,
                                       root);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    admin.write(_data, i);
    i += 32;
    config.write(_data, i);
    i += 32;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, version);
    i += 8;
    i += Borsh.writeArray(root, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
