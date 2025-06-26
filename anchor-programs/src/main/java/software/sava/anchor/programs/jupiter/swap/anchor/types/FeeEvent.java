package software.sava.anchor.programs.jupiter.swap.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record FeeEvent(PublicKey account,
                       PublicKey mint,
                       long amount) implements Borsh {

  public static final int BYTES = 72;

  public static FeeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var account = readPubKey(_data, i);
    i += 32;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new FeeEvent(account, mint, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    account.write(_data, i);
    i += 32;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
