package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultRewardRoute(PublicKey vault, long rewards) implements Borsh {

  public static final int BYTES = 40;

  public static VaultRewardRoute read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = readPubKey(_data, i);
    i += 32;
    final var rewards = getInt64LE(_data, i);
    return new VaultRewardRoute(vault, rewards);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vault.write(_data, i);
    i += 32;
    putInt64LE(_data, i, rewards);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
