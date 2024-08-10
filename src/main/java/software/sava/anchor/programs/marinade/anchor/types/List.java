package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record List(PublicKey account,
                   int itemSize,
                   int count,
                   PublicKey reserved1,
                   int reserved2) implements Borsh {

  public static final int BYTES = 76;

  public static List read(final byte[] _data, final int offset) {
    int i = offset;
    final var account = readPubKey(_data, i);
    i += 32;
    final var itemSize = getInt32LE(_data, i);
    i += 4;
    final var count = getInt32LE(_data, i);
    i += 4;
    final var reserved1 = readPubKey(_data, i);
    i += 32;
    final var reserved2 = getInt32LE(_data, i);
    return new List(account,
                    itemSize,
                    count,
                    reserved1,
                    reserved2);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    account.write(_data, i);
    i += 32;
    putInt32LE(_data, i, itemSize);
    i += 4;
    putInt32LE(_data, i, count);
    i += 4;
    reserved1.write(_data, i);
    i += 32;
    putInt32LE(_data, i, reserved2);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 4
         + 4
         + 32
         + 4;
  }
}