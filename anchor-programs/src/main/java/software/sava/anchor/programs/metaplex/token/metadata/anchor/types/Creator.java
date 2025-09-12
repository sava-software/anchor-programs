package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record Creator(PublicKey address,
                      boolean verified,
                      int share) implements Borsh {

  public static final int BYTES = 34;

  public static Creator read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var address = readPubKey(_data, i);
    i += 32;
    final var verified = _data[i] == 1;
    ++i;
    final var share = _data[i] & 0xFF;
    return new Creator(address, verified, share);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    address.write(_data, i);
    i += 32;
    _data[i] = (byte) (verified ? 1 : 0);
    ++i;
    _data[i] = (byte) share;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
