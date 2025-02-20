package software.sava.anchor.programs.kamino.farms.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TokenInfo(PublicKey mint,
                        long decimals,
                        PublicKey tokenProgram,
                        long[] padding) implements Borsh {

  public static final int BYTES = 120;

  public static TokenInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var decimals = getInt64LE(_data, i);
    i += 8;
    final var tokenProgram = readPubKey(_data, i);
    i += 32;
    final var padding = new long[6];
    Borsh.readArray(padding, _data, i);
    return new TokenInfo(mint,
                         decimals,
                         tokenProgram,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, decimals);
    i += 8;
    tokenProgram.write(_data, i);
    i += 32;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
