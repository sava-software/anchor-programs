package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record NewUserRecord(long ts,
                            PublicKey userAuthority,
                            PublicKey user,
                            int subAccountId,
                            byte[] name,
                            PublicKey referrer) implements Borsh {

  public static final int BYTES = 138;

  public static NewUserRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ts = getInt64LE(_data, i);
    i += 8;
    final var userAuthority = readPubKey(_data, i);
    i += 32;
    final var user = readPubKey(_data, i);
    i += 32;
    final var subAccountId = getInt16LE(_data, i);
    i += 2;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var referrer = readPubKey(_data, i);
    return new NewUserRecord(ts,
                             userAuthority,
                             user,
                             subAccountId,
                             name,
                             referrer);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, ts);
    i += 8;
    userAuthority.write(_data, i);
    i += 32;
    user.write(_data, i);
    i += 32;
    putInt16LE(_data, i, subAccountId);
    i += 2;
    i += Borsh.writeArray(name, _data, i);
    referrer.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
