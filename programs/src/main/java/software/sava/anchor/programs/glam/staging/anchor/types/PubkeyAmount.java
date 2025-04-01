package software.sava.anchor.programs.glam.staging.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PubkeyAmount(PublicKey pubkey,
                           long amount,
                           int decimals) implements Borsh {

  public static final int BYTES = 41;

  public static PubkeyAmount read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var pubkey = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var decimals = _data[i] & 0xFF;
    return new PubkeyAmount(pubkey, amount, decimals);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    pubkey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) decimals;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
