package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SplitStakeAccountInfo(PublicKey account, int index) implements Borsh {

  public static final int BYTES = 36;

  public static SplitStakeAccountInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var account = readPubKey(_data, i);
    i += 32;
    final var index = getInt32LE(_data, i);
    return new SplitStakeAccountInfo(account, index);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    account.write(_data, i);
    i += 32;
    putInt32LE(_data, i, index);
    i += 4;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
