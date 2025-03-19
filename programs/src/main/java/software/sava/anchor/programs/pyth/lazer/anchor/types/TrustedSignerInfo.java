package software.sava.anchor.programs.pyth.lazer.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TrustedSignerInfo(PublicKey pubkey, long expiresAt) implements Borsh {

  public static final int BYTES = 40;

  public static TrustedSignerInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var expiresAt = getInt64LE(_data, i);
    return new TrustedSignerInfo(pubkey, expiresAt);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, expiresAt);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
