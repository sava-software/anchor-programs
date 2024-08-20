package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Deposit(PublicKey dcaKey, long amount) implements Borsh {

  public static final int BYTES = 40;

  public static Deposit read(final byte[] _data, final int offset) {
    int i = offset;
    final var dcaKey = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    return new Deposit(dcaKey, amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    dcaKey.write(_data, i);
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
