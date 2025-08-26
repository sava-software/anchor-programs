package software.sava.anchor.programs.glam.mint.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CreatedModel(byte[] key,
                           PublicKey createdBy,
                           long createdAt) implements Borsh {

  public static final int BYTES = 48;
  public static final int KEY_LEN = 8;

  public static CreatedModel read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = new byte[8];
    i += Borsh.readArray(key, _data, i);
    final var createdBy = readPubKey(_data, i);
    i += 32;
    final var createdAt = getInt64LE(_data, i);
    return new CreatedModel(key, createdBy, createdAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(key, _data, i);
    createdBy.write(_data, i);
    i += 32;
    putInt64LE(_data, i, createdAt);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
