package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StakeRecord(PublicKey stakeAccount,
                          long lastUpdateDelegatedLamports,
                          long lastUpdateEpoch,
                          int isEmergencyUnstaking) implements Borsh {

  public static final int BYTES = 49;

  public static StakeRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var stakeAccount = readPubKey(_data, i);
    i += 32;
    final var lastUpdateDelegatedLamports = getInt64LE(_data, i);
    i += 8;
    final var lastUpdateEpoch = getInt64LE(_data, i);
    i += 8;
    final var isEmergencyUnstaking = _data[i] & 0xFF;
    return new StakeRecord(stakeAccount,
                           lastUpdateDelegatedLamports,
                           lastUpdateEpoch,
                           isEmergencyUnstaking);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    stakeAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastUpdateDelegatedLamports);
    i += 8;
    putInt64LE(_data, i, lastUpdateEpoch);
    i += 8;
    _data[i] = (byte) isEmergencyUnstaking;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
