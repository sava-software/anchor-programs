package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record VaultInfo(PublicKey vaultKey, long lastRewardEpoch) implements Borsh {

  public static final int BYTES = 40;

  public static VaultInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vaultKey = readPubKey(_data, i);
    i += 32;
    final var lastRewardEpoch = getInt64LE(_data, i);
    return new VaultInfo(vaultKey, lastRewardEpoch);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    vaultKey.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastRewardEpoch);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
