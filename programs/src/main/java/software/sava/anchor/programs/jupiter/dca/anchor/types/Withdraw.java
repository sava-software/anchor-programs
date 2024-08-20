package software.sava.anchor.programs.jupiter.dca.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Withdraw(PublicKey dcaKey,
                       long inAmount,
                       long outAmount,
                       boolean userWithdraw) implements Borsh {

  public static final int BYTES = 49;

  public static Withdraw read(final byte[] _data, final int offset) {
    int i = offset;
    final var dcaKey = readPubKey(_data, i);
    i += 32;
    final var inAmount = getInt64LE(_data, i);
    i += 8;
    final var outAmount = getInt64LE(_data, i);
    i += 8;
    final var userWithdraw = _data[i] == 1;
    return new Withdraw(dcaKey,
                        inAmount,
                        outAmount,
                        userWithdraw);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    dcaKey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, inAmount);
    i += 8;
    putInt64LE(_data, i, outAmount);
    i += 8;
    _data[i] = (byte) (userWithdraw ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
