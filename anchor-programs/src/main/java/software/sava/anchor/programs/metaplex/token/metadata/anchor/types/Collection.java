package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Collection(boolean verified, PublicKey key) implements Borsh {

  public static final int BYTES = 33;

  public static Collection read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var verified = _data[i] == 1;
    ++i;
    final var key = readPubKey(_data, i);
    return new Collection(verified, key);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) (verified ? 1 : 0);
    ++i;
    key.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
