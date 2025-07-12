package software.sava.anchor.programs.meteora.alpha_vault.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CreateMerkleRootConfigParams(// The 256-bit merkle root.
                                           byte[] root,
                                           // version
                                           long version) implements Borsh {

  public static final int BYTES = 40;
  public static final int ROOT_LEN = 32;

  public static CreateMerkleRootConfigParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var root = new byte[32];
    i += Borsh.readArray(root, _data, i);
    final var version = getInt64LE(_data, i);
    return new CreateMerkleRootConfigParams(root, version);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(root, _data, i);
    putInt64LE(_data, i, version);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
