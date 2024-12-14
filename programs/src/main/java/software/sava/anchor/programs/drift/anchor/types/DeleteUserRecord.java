package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DeleteUserRecord(long ts,
                               PublicKey userAuthority,
                               PublicKey user,
                               int subAccountId,
                               PublicKey keeper) implements Borsh {

  public static DeleteUserRecord read(final byte[] _data, final int offset) {
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
    final var keeper = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new DeleteUserRecord(ts,
                                userAuthority,
                                user,
                                subAccountId,
                                keeper);
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
    i += Borsh.writeOptional(keeper, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 32
         + 32
         + 2
         + (keeper == null ? 1 : (1 + 32));
  }
}
